package com.github.christiangda.pig.geoip;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CountryNameTest {
    private static String DEFAULT_IPV4_DB_LOCATION = "/usr/share/GeoIP/GeoIP.dat";
    private static String DEFAULT_IPV6_DB_LOCATION = "/usr/share/GeoIP/GeoIPv6.dat";

    @Test
    public void testCountryNameNull() throws IOException {
        Tuple tuple = Util.buildTuple();
        assertNull("null object return null", new CountryName(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryNameEmpty() throws IOException {
        Tuple tuple = Util.buildTuple("");
        assertEquals("empty string return null", null, new CountryName(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryNameBadIPv4AOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.256");
        assertEquals("Bad IPv4 Private Address return null", null, new CountryName(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryNameBadIPv4ATwo() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.");
        assertEquals("Bad IPv4 Private Address return null", null, new CountryName(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryNameGoodIPv4PrivateOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.192");
        assertEquals("Good IPv4 Private Address return null", null, new CountryName(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryNameGoodIPv4PublicOne() throws IOException {
        //Test Google DNS IP
        //https://ipinfo.io/8.8.8.8
        Tuple tuple = Util.buildTuple("8.8.8.8");
        assertEquals("Good IPv4 Public Address return a Country Name", "United States", new CountryName(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testCountryNameGoodIPv6PublicOne() throws IOException {
        //Test Google DNS IP
        //https://ipinfo.io/2001:4860:4860::8888
        Tuple tuple = Util.buildTuple("2001:4860:4860::8888");
        assertEquals("Good IPv6 Public Address return a Country Name", "United States", new CountryName(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }
}

