/*
 * GeoCoding.java
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

import com.github.christiangda.utils.ip.IP;
import com.maxmind.geoip.LookupService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class GeoCoding {

    private HashMap<String, LookupService> ls = new HashMap<String, LookupService>();

    public GeoCoding(String ipv4DbFilePath, String ipv6DbFilePath) throws IOException {
        this.ls.put("ipv4", new LookupService(ipv4DbFilePath, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE));
        this.ls.put("ipv6", new LookupService(ipv6DbFilePath, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE));
    }

    /**
     * @param IpAddress String Ip Address
     * @return String with Country Name
     */
    public String getCountryName(final String IpAddress) {

        InetAddress address;

        try {
            address = InetAddress.getByName(IpAddress);
        } catch (UnknownHostException e) {
            return null;
        }

        if (IP.isValidIPV4(address)) {
            return this.ls.get("ipv4").getCountry(address).getName();
        } else if (IP.isValidIPV6(address)) {
            return this.ls.get("ipv6").getCountry(address).getName();
        } else {
            return null;
        }
    }

    /**
     * Get the code of a country associated with the Ip address
     *
     * @param IpAddress String Ip Address
     * @return String with Country Name
     */
    public String getCountryCode(final String IpAddress) {

        InetAddress address;

        try {
            address = InetAddress.getByName(IpAddress);
        } catch (UnknownHostException e) {
            return null;
        }

        if (IP.isValidIPV4(address)) {
            return this.ls.get("ipv4").getCountry(address).getCode();
        } else if (IP.isValidIPV6(address)) {
            return this.ls.get("ipv6").getCountry(address).getCode();
        } else {
            return null;
        }
    }

    /**
     * Get the name of a city associated with the Ip address
     *
     * @param IpAddress String Ip Address
     * @return String with City Name
     */
    public String getCityName(final String IpAddress) {

        InetAddress address;

        try {
            address = InetAddress.getByName(IpAddress);
        } catch (UnknownHostException e) {
            return null;
        }

        if (IP.isValidIPV4(address)) {
            return this.ls.get("ipv4").getLocation(address).city;
        } else if (IP.isValidIPV6(address)) {
            return this.ls.get("ipv6").getLocation(address).city;
        } else {
            return null;
        }
    }
}
