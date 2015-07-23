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
