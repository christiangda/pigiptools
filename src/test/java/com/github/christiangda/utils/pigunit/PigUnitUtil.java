/*
 * PigUnitUtil.java
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