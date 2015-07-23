/*
 * GetCityNameTest.java
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

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GetCityNameTest {

    private String DEFAULT_DB_LOCATION = "/usr/share/GeoIP/GeoLiteCity.dat";

    @Test
    public void testCityNameNull() throws IOException {
        Tuple tuple = Util.buildTuple();
        assertNull("null object return null", new GetCityName(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCityNameEmty() throws IOException {
        Tuple tuple = Util.buildTuple("");
        assertEquals("empty string return null", null, new GetCityName(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCityNameBadIPv4AOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.256");
        assertEquals("Bad IPv4 Private Address return null", null, new GetCityName(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCityNameBadIPv4ATwo() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.");
        assertEquals("Bad IPv4 Private Address return null", null, new GetCityName(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCityNameGoodIPv4PrivateOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.192");
        assertEquals("Good IPv4 Private Address return null", null, new GetCityName(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCityNameGoodIPv4PublicOne() throws IOException {
        //Test Google DNS IP
        //https://ipinfo.io/8.8.8.8
        Tuple tuple = Util.buildTuple("8.8.8.8");
        assertEquals("Good IPv4 Public Address return a City", "Mountain View", new GetCityName(DEFAULT_DB_LOCATION).exec(tuple));
    }
}
