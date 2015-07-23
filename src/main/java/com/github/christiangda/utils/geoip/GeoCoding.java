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
}
