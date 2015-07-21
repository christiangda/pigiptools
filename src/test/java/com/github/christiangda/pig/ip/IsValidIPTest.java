package com.github.christiangda.pig.ip;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsValidIPTest {

    @Test
    public void testIsValidIPWhenNull() throws Exception {
        Tuple tuple = Util.buildTuple();
        assertFalse("null object return false", new IsValidIP().exec(tuple));
    }

    @Test
    public void testIsValidIPWhenEmpty() throws Exception {
        Tuple tuple = Util.buildTuple("");
        assertFalse("empty object return false", new IsValidIP().exec(tuple));
    }

    @Test
    public void testIsValidIPWhenValidIPV4() throws Exception {
        Tuple tuple = Util.buildTuple("192.168.1.1");
        assertTrue("Valid IPV4 object return true", new IsValidIP().exec(tuple));
    }

    @Test
    public void testIsValidIPWhenValidIPV6() throws Exception {
        Tuple tuple = Util.buildTuple("2001:4860:4860::8888");
        assertTrue("Valid IPV6 object return true", new IsValidIP().exec(tuple));
    }
}