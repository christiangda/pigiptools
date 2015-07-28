/*
 * IP.java
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

package com.github.christiangda.utils.ip;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class IP {

    /**
     * @param ip String
     * @return boolean
     */
    public static boolean isValidIP(String ip) {

        if (ip == null) {
            return false;
        }

        if (ip.isEmpty()) {
            return false;
        }

        return InetAddressUtils.isIPv4Address(ip) || InetAddressUtils.isIPv6Address(ip);
    }

    /**
     * @param ip InetAddress
     * @return boolean
     */
    public static boolean isValidIP(InetAddress ip) {
        return ip != null && (InetAddressUtils.isIPv4Address(ip.getHostAddress()) || InetAddressUtils.isIPv6Address(ip.getHostAddress()));
    }

    /**
     * @param ip String
     * @return boolean
     */
    public static boolean isValidIPV4(String ip) {
        if (ip == null) {
            return false;
        }

        if (ip.isEmpty()) {
            return false;
        }

        return InetAddressUtils.isIPv4Address(ip);
    }

    /**
     * @param ip InetAddress
     * @return boolean
     */
    public static boolean isValidIPV4(InetAddress ip) {
        if (ip == null) {
            return false;
        }

        return InetAddressUtils.isIPv4Address(ip.getHostAddress());
    }

    /**
     * @param ip String
     * @return boolean
     */
    public static boolean isValidIPV6(String ip) {
        if (ip == null) {
            return false;
        }

        if (ip.isEmpty()) {
            return false;
        }

        return InetAddressUtils.isIPv6Address(ip);
    }

    /**
     * @param ip InetAddress
     * @return boolean
     */
    public static boolean isValidIPV6(InetAddress ip) {
        if (ip == null) {
            return false;
        }
        return InetAddressUtils.isIPv6Address(ip.getHostAddress());
    }
}
