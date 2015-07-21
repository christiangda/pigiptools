package com.github.christiangda.pig.geoip;

import com.github.christiangda.utils.geoip.GeoCoding;
import org.apache.commons.io.FilenameUtils;
import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCountryName extends EvalFunc<String> {

    private GeoCoding geo;
    private HashMap<String, String> dbFilesPaths = new HashMap<String, String>();
    private HashMap<String, String> dbFilesDistributedPaths = new HashMap<String, String>();

    /**
     * @param IPV4DBFilePath String
     * @param IPV6DBFilePath String
     */
    public GetCountryName(final String IPV4DBFilePath, final String IPV6DBFilePath) {
        this.dbFilesPaths.put("ipv4", IPV4DBFilePath);
        this.dbFilesPaths.put("ipv6", IPV6DBFilePath);

        String dbIPV4FileDistributed = FilenameUtils.getName(IPV4DBFilePath);
        String dbIPV6FileDistributed = FilenameUtils.getName(IPV6DBFilePath);

        this.dbFilesDistributedPaths.put("ipv4", dbIPV4FileDistributed);
        this.dbFilesDistributedPaths.put("ipv6", dbIPV6FileDistributed);

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
            String result = this.geo.getCountryName(strAddress);

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
            //cacheFiles.add(entry.getValue() + "#" + this.dbFilesDistributedPaths.get(entry.getKey()));
            cacheFiles.add(entry.getValue() + "#" + entry.getValue());
        }
        //cacheFiles.add(this.dbFilesPaths.get("ipv4") + "#" + this.dbFilesDistributedPaths.get("ipv4"));
        //cacheFiles.add(this.dbFilesPaths.get("ipv6") + "#" + this.dbFilesDistributedPaths.get("ipv6"));
        return cacheFiles;
    }
}