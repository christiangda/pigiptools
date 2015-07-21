package com.github.christiangda.pig.geoip;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CountryCodeTest {
    private String DEFAULT_DB_LOCATION = "/usr/share/GeoIP/GeoIP.dat";

    @Test
    public void testCountryCodeNull() throws IOException {
        Tuple tuple = Util.buildTuple();
        assertNull("null object return null", new CountryCode(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryCodeEmpty() throws IOException {
        Tuple tuple = Util.buildTuple("");
        assertEquals("empty string return null", null, new CountryCode(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryCodeBadIPv4AOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.256");
        assertEquals("Bad IPv4 Private Address return null", null, new CountryCode(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryCodeBadIPv4ATwo() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.");
        assertEquals("Bad IPv4 Private Address return null", null, new CountryCode(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryCodeGoodIPv4PrivateOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.192");
        assertEquals("Good IPv4 Private Address return null", null, new CountryCode(DEFAULT_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryCodeGoodIPv4PublicOne() throws IOException {
        //Test Google DNS IP
        //https://ipinfo.io/8.8.8.8
        Tuple tuple = Util.buildTuple("8.8.8.8");
        assertEquals("Good IPv4 Public Address return a Two Letters Country Code", "US", new CountryCode(DEFAULT_DB_LOCATION).exec(tuple));
    }
}
