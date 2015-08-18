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

import com.github.christiangda.utils.geoip.GeoCoding;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCityName extends EvalFunc<String> {

    /** Value - {@value}, GeoCoding object to uses for get its methods.*/
    private GeoCoding geo;

    /** value - {@value},      */
    private HashMap<String, String> dbFilesPaths = new HashMap<String, String>();

    /**
     * Class constructor specifying the two MaxMind Database Files (IPV4 or IPV6)
     *
     * @param DBFilePath Path to the MaxMind GeoIP Database for IPV4
     */
    public GetCityName(final String DBFilePath) {

        this.dbFilesPaths.put("unknow", DBFilePath);

        try {
            this.geo = new GeoCoding(DBFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class constructor specifying the two MaxMind Database Files (IPV4 and IPV6)
     *
     * @param IPV4DBFilePath Path to the MaxMind GeoIP Database for IPV4
     * @param IPV6DBFilePath Path to the MaxMind GeoIP Database for IPV6
     */
    public GetCityName(final String IPV4DBFilePath, final String IPV6DBFilePath) {

        this.dbFilesPaths.put("ipv4", IPV4DBFilePath);
        this.dbFilesPaths.put("ipv6", IPV6DBFilePath);

        try {
            this.geo = new GeoCoding(IPV4DBFilePath, IPV4DBFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String exec(Tuple input) throws IOException {

        // validate input
        if (input == null || input.size() == 0 || input.get(0) == null) {
            return null;
        }

        // get the value of input
        String strAddress = (String) input.get(0);

        // Get geoip information
        try {
            String result = this.geo.getCityName(strAddress);

            // replace "--" and "N/A" to null, better for pig
            if (result == null || result.equals("--") || result.equals("N/A")) {
                return null;
            } else {
                return result;
            }

        } catch (Exception e) {
            // e.printStackTrace();
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

        //iterate over this.dbFilesPaths, to put file in cache
        for (Map.Entry<String, String> entry : this.dbFilesPaths.entrySet()) {
            cacheFiles.add(entry.getValue() + "#" + entry.getValue());
        }
        return cacheFiles;
    }
}