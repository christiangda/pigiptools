package com.github.christiangda.utils.ip.test;

import com.github.christiangda.utils.ip.IP;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IPTest {

//    @Test
//    public void testIsValidIPWhenIsNull() throws Exception {
//        assertFalse("null argument return false", IP.isValidIP(null));
//    }

    @Test
    public void testIsValidIPWhenIsEmpty() throws Exception {
        assertFalse("Empty argument return false", IP.isValidIP(""));
    }

    @Test
    public void testIsValidIPWhenIsBadIPV4() throws Exception {
        assertFalse("Bad IPV4 argument return false", IP.isValidIP("192.168.0.300"));
    }

    @Test
    public void testIsValidIPWhenIsBadIPV6() throws Exception {
        assertFalse("Bad IPV6 argument return false", IP.isValidIP("2001:4860:4860::8888:"));
    }

    @Test
    public void testIsValidIPWhenIsMalformed() throws Exception {
        assertFalse("Malformed IP argument return false", IP.isValidIP("192.168.0."));
    }

    //
    @Test
    public void testIsValidIPWhenIsGoodIPV4() throws Exception {
        assertTrue("Good IPV4 argument return true", IP.isValidIP("192.168.0.1"));
    }

    //
    @Test
    public void testIsValidIPWhenIsGoodIPV6() throws Exception {
        assertTrue("Good IPV6 argument return true", IP.isValidIP("2001:4860:4860::8888"));
    }

}