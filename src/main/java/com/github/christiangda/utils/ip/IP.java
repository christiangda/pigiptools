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

        try {
            InetAddress ia = InetAddress.getByName(ip);
            return InetAddressUtils.isIPv4Address(ia.getHostAddress()) || InetAddressUtils.isIPv6Address(ia.getHostAddress());
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            return false;
        }
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

        try {
            InetAddress ia = InetAddress.getByName(ip);
            return InetAddressUtils.isIPv4Address(ia.getHostAddress());
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * @param ip InetAddress
     * @return boolean
     */
    public static boolean isValidIPV4(InetAddress ip) {
        return ip != null && InetAddressUtils.isIPv4Address(ip.getHostAddress());
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

        try {
            InetAddress ia = InetAddress.getByName(ip);
            return InetAddressUtils.isIPv6Address(ia.getHostAddress());
        } catch (UnknownHostException e) {
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * @param ip InetAddress
     * @return boolean
     */
    public static boolean isValidIPV6(InetAddress ip) {
        return ip != null && InetAddressUtils.isIPv6Address(ip.getHostAddress());
    }
}
