package com.github.christiangda.pig.scripts;

import com.github.christiangda.utils.pigunit.PigUnitUtil;
import org.apache.pig.pigunit.PigTest;
import org.junit.Test;

public class GetCountryNameTest {

    private static final String PIG_SCRIPT = "src/test/resources/scripts/GetCountryName.pig";

    @Test
    public void testGetCountryNamePigScript() throws Exception {

        String[] args = {
                "LIBRARY_PATH=./target",
                "IPV4_GEODB_PATH=/usr/share/GeoIP/GeoIP.dat",
                "IPV6_GEODB_PATH=/usr/share/GeoIP/GeoIPv6.dat"
        };

        String[] input = {
                null,
                "",
                "192.168.1.256",
                "192.168.1.",
                "192.168.1.192",
                "8.8.8.8",
                "2001:4860:4860::8888",
                "2001:4860:4860::8888:"
        };

        String[] output = {
                "()",
                "()",
                "()",
                "()",
                "()",
                "(United States)",
                "(United States)",
                "()"
        };

        PigTest pigTest = PigUnitUtil.createPigTest(PIG_SCRIPT, args);

        pigTest.assertOutput("data", input, "ips", output);
    }
}

