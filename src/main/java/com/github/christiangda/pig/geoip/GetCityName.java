/*
 * GetCityName.java
 *
 * Copyright (c) 2015  Christian González
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.christiangda.pig.geoip;

import com.maxmind.geoip.LookupService;
import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GetCityName extends EvalFunc<String> {

    private static String DEFAULT_DB_LOCATION = "/usr/local/GeoIP/GeoLiteCity.dat";
    private LookupService ls;
    private String srcGeoIpDbFile;
    private String cacheGeoIpDbFile;

    /**
     *
     */
    public GetCityName() {
        this(DEFAULT_DB_LOCATION);
    }

    /**
     *
     */
    public GetCityName(final String DB_FILE) {
        this.ls = null;
        this.srcGeoIpDbFile = DB_FILE;
        this.cacheGeoIpDbFile = this.getClass().getSimpleName() + "-slashdevops-GeoDataBase.dat";
    }

    @Override
    public String exec(Tuple input) throws IOException {

        // validate input
        if (input == null || input.size() == 0 || input.get(0) == null) {
            return null;
        }

        // get the value of input
        String strAddress = (String) input.get(0);

        // Validating ip address
        InetAddress address = null;
        try {
            address = InetAddress.getByName(strAddress);

        } catch (UnknownHostException uhe) {
            // System.err.println(this.getClass().getName() +
            // "Failed to process IP Address:  " + strAddress + " " +
            // uhe.getMessage());
            return null;
        }

        // create LookupService object
        if (this.ls == null) {
            try {

                this.ls = new LookupService(this.cacheGeoIpDbFile, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);

            } catch (IOException ioe) {
                try {
                    // In local mode dosen't work getCacheFiles(), I don't know
                    // why, but this fix that.
                    this.cacheGeoIpDbFile = this.srcGeoIpDbFile;

                    this.ls = new LookupService(this.cacheGeoIpDbFile, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);

                } catch (Exception e) {
                    throw new IOException(this.getClass().getName() + " Unable to open file: " + this.cacheGeoIpDbFile, ioe);
                }
            }
        }

        // Get geoip information
        try {
            String result = this.ls.getLocation(address).city;

            // replace string "--" from GeoIP DB to null, is better for pig
            if (result == null || result.equals("--") || result.equals("N/A")) {
                return null;
            } else {
                return result;
            }

        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
        List<FuncSpec> funcList = new ArrayList<FuncSpec>();
        funcList.add(new FuncSpec(this.getClass().getName(), new Schema(new Schema.FieldSchema(null, DataType.CHARARRAY))));
        return funcList;
    }

    @Override
    public final List<String> getCacheFiles() {
        List<String> cacheFiles = new ArrayList<String>();
        cacheFiles.add(this.srcGeoIpDbFile + "#" + this.cacheGeoIpDbFile);
        return cacheFiles;
    }
}