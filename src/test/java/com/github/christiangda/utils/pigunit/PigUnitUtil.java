package com.github.christiangda.utils.pigunit;

import org.apache.pig.ExecType;
import org.apache.pig.PigException;
import org.apache.pig.impl.util.PropertiesUtil;
import org.apache.pig.pigunit.Cluster;
import org.apache.pig.pigunit.PigTest;
import org.apache.pig.pigunit.pig.PigServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PigUnitUtil {

    public static PigTest createPigTest(String scriptFile, String[] args) throws PigException {
        try {
            PigServer pigServer = new PigServer(ExecType.LOCAL);
            Cluster pigCluster = new Cluster(pigServer.getPigContext());
            return new PigTest(scriptFile, args, pigServer, pigCluster);
        } catch (Exception ex) {
            throw new PigException("Failed to create PigTest instance. ", ex);
        }
    }

    protected List<String> readPropertiesFile(String[] filePaths) throws IOException {
        Properties properties = new Properties();
        List<String> togo = new ArrayList<String>();

        for (String filePath : filePaths) {
            PropertiesUtil.loadPropertiesFromFile(properties, filePath);
        }
        for (String propertyName : properties.stringPropertyNames()) {
            togo.add(propertyName + "=" + properties.getProperty(propertyName));
        }
        return togo;
    }
}