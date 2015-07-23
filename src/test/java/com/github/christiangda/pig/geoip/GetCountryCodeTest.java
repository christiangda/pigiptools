package com.github.christiangda.pig.geoip;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GetCountryCodeTest {

    private static String DEFAULT_IPV4_DB_LOCATION = "/usr/share/GeoIP/GeoIP.dat";
    private static String DEFAULT_IPV6_DB_LOCATION = "/usr/share/GeoIP/GeoIPv6.dat";

    @Test
    public void testGetCountryCodeNull() throws IOException {
        Tuple tuple = Util.buildTuple();
        assertNull("null object return null", new GetCountryCode(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testGetCountryCodeEmpty() throws IOException {
        Tuple tuple = Util.buildTuple("");
        assertEquals("empty string return null", null, new GetCountryCode(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testGetCountryCodeBadIPv4AOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.256");
        assertEquals("Bad IPv4 Private Address return null", null, new GetCountryCode(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testGetCountryCodeBadIPv4ATwo() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.");
        assertEquals("Bad IPv4 Private Address return null", null, new GetCountryCode(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testGetCountryCodeGoodIPv4PrivateOne() throws IOException {
        Tuple tuple = Util.buildTuple("192.168.1.192");
        assertEquals("Good IPv4 Private Address return null", null, new GetCountryCode(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }

    @Test
    public void testGetCountryCodeGoodIPv4PublicOne() throws IOException {
        //Test Google DNS IP
        //https://ipinfo.io/8.8.8.8
        Tuple tuple = Util.buildTuple("8.8.8.8");
        assertEquals("Good IPv4 Public Address return a Two Letters Country Code", "US", new GetCountryCode(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION).exec(tuple));
    }
}
