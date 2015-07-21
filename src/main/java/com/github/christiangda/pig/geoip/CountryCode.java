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

public class CountryCode extends EvalFunc<String> {

    private static String DEFAULT_DB_LOCATION = "/usr/local/GeoIP/GeoIP.dat";
    private LookupService ls;
    private String srcGeoIpDbFile;
    private String cacheGeoIpDbFile;

    /**
     *
     */
    public CountryCode() {
        this(DEFAULT_DB_LOCATION);
    }

    /**
     *
     */
    public CountryCode(final String DB_FILE) {
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
            String result = this.ls.getCountry(address).getCode();

            // replace "--" for null, better for pig
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
        cacheFiles.add(this.srcGeoIpDbFile + "#" + this.cacheGeoIpDbFile);
        return cacheFiles;
    }
}