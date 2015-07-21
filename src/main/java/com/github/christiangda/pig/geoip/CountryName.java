package com.github.christiangda.pig.geoip;

import com.github.christiangda.utils.ip.IP;
import com.maxmind.geoip.LookupService;
import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountryName extends EvalFunc<String> {

    private LookupService ls;
    private String srcGeoIpDbFile;
    private String cacheGeoIpDbFile;
    private HashMap<String, String> dbFilesPaths = new HashMap<String, String>();

    /**
     * @param IPV4DBFilePath String
     * @param IPV6DBFilePath String
     */
    public CountryName(final String IPV4DBFilePath, final String IPV6DBFilePath) {
        this.ls = null;
        this.dbFilesPaths.put("ipv4", IPV4DBFilePath);
        this.dbFilesPaths.put("ipv6", IPV6DBFilePath);
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
        InetAddress address;

        if (IP.isValidIP(strAddress)) {
            address = InetAddress.getByName(strAddress);
        } else {
            return null;
        }

        if (IP.isValidIPV4(address)) {
            this.srcGeoIpDbFile = this.dbFilesPaths.get("ipv4");
        } else if (IP.isValidIPV6(address)) {
            this.srcGeoIpDbFile = this.dbFilesPaths.get("ipv6");
        }

        // create LookupService object
        if (this.ls == null) {
            try {
                this.ls = new LookupService(this.cacheGeoIpDbFile, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
            } catch (IOException ioe) {
                try {
                    // In local mode doesn't work getCacheFiles(), I don't know why, but this fix that.
                    this.cacheGeoIpDbFile = this.srcGeoIpDbFile;
                    this.ls = new LookupService(this.cacheGeoIpDbFile, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
                    //System.err.println("Data Base type  = " +this.ls.getDatabaseInfo().getType() + ", From file = " + this.ls.getDatabaseInfo().toString());
                } catch (Exception e) {
                    throw new IOException(this.getClass().getName() + " Unable to open file: " + this.cacheGeoIpDbFile, ioe);
                }
            }
        }

        // Get geoip information
        try {
            String result = this.ls.getCountry(address).getName();

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