/*
 * GetCountryNameTest.java
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

package com.github.christiangda.pig.scripts;

import com.github.christiangda.utils.pigunit.PigUnitUtil;
import org.apache.pig.pigunit.PigTest;
import org.junit.Test;

public class GettingAllIpValidationsTest {

    private static final String PIG_SCRIPT = "src/test/resources/scripts/GettingAllIpValidations.pig";

    @Test
    public void testPigScript() throws Exception {

        String[] args = {
                "LIBRARY_PATH=./target",
                "IPV4_GEOIPDB_COUNTRY_PATH=/usr/share/GeoIP/GeoIP.dat",
                "IPV6_GEOIPDB_COUNTRY_PATH=/usr/share/GeoIP/GeoIPv6.dat",
                "IPV4_GEOIPDB_CITY_PATH=/usr/share/GeoIP/GeoLiteCity.dat",
                "IPV6_GEOIPDB_CITY_PATH=/usr/share/GeoIP/GeoLiteCityv6.dat"
        };

        String[] input = {
                null,
                "",
                "192.168.1.256",
                "192.168.1.",
                "192.168.1.192",
                "8.8.8.8",
                "2001:4860:4860::8888",
                "2001:4860:4860::8888:"
        };

        String[] output = {
                "(null,false,false,false,,,)",
                "(,false,false,false,,,)",
                "(192.168.1.256,false,false,false,,,)",
                "(192.168.1.,false,false,false,,,)",
                "(192.168.1.192,true,true,false,,,)",
                "(8.8.8.8,true,true,false,United States,US,Mountain View)",
                "(2001:4860:4860::8888,true,false,true,United States,US,)",
                "(2001:4860:4860::8888:,false,false,false,,,)"
        };

        PigTest pigTest = PigUnitUtil.createPigTest(PIG_SCRIPT, args);

        pigTest.assertOutput("data", input, "ips", output);
    }
}

