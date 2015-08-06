/*
 * GeoCodingTest.java
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

package com.github.christiangda.utils.geoip;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeoCodingTest {

    private static String DEFAULT_IPV4_DB_LOCATION = "/usr/share/GeoIP/GeoIP.dat";
    private static String DEFAULT_IPV6_DB_LOCATION = "/usr/share/GeoIP/GeoIPv6.dat";

    // getCountryName
    @Test
    public void testGetCountryNameWhenIsEmpty() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Empty argument return N/A", "N/A", tester.getCountryName(""));
    }

    @Test
    public void testGetCountryNameWhenIsNull() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Null argument return N/A", "N/A", tester.getCountryName(null));
    }

    @Test
    public void testGetCountryNameWhenIsGoodIPV4() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Good  Ip argument return United States", "United States", tester.getCountryName("8.8.8.8"));
    }

    @Test
    public void testGetCountryNameWhenIsGoodIPV6() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Good  Ip argument return United States", "United States", tester.getCountryName("2001:4860:4860::8888"));
    }

    @Test
    public void testGetCountryNameWhenIsBadIPV4() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Bad Ip argument return null", null, tester.getCountryName("192.168.1.300"));
    }

    @Test
    public void testGetCountryNameWhenIsBadIPV6() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Bad Ip argument return null", null, tester.getCountryName("2001:4860:4860::8888:"));
    }

    // getCountryCode
    @Test
    public void testGetCountryCodeWhenIsEmpty() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Empty argument return --", "--", tester.getCountryCode(""));
    }

    @Test
    public void testGetCountryCodeWhenIsNull() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Null argument return --", "--", tester.getCountryCode(null));
    }

    @Test
    public void testGetCountryCodeWhenIsGoodIPV4() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Good  Ip argument return US", "US", tester.getCountryCode("8.8.8.8"));
    }

    @Test
    public void testGetCountryCodeWhenIsGoodIPV6() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Good  Ip argument return US", "US", tester.getCountryCode("2001:4860:4860::8888"));
    }

    @Test
    public void testGetCountryCodeWhenIsBadIPV4() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Bad Ip argument return null", null, tester.getCountryCode("192.168.1.300"));
    }

    @Test
    public void testGetCountryCodeWhenIsBadIPV6() throws Exception {
        GeoCoding tester = new GeoCoding(DEFAULT_IPV4_DB_LOCATION, DEFAULT_IPV6_DB_LOCATION);
        assertEquals("Bad Ip argument return null", null, tester.getCountryCode("2001:4860:4860::8888:"));
    }

}