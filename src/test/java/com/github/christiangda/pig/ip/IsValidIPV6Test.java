package com.github.christiangda.pig.ip;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsValidIPV6Test {

    @Test
    public void testIsValidIPV6WhenNull() throws Exception {
        Tuple tuple = Util.buildTuple();
        assertFalse("null object return false", new IsValidIPV6().exec(tuple));
    }

    @Test
    public void testIsValidIPV6WhenEmpty() throws Exception {
        Tuple tuple = Util.buildTuple("");
        assertFalse("empty string return false", new IsValidIPV6().exec(tuple));
    }

    @Test
    public void testIsValidIPV6WhenInvalidIPV6() throws Exception {
        Tuple tuple = Util.buildTuple("2001:4860:4860::8888:");
        assertFalse("invalid IPV6 string return false", new IsValidIPV6().exec(tuple));
    }

    @Test
    public void testIsValidIPV6WhenValidIPV6() throws Exception {
        Tuple tuple = Util.buildTuple("2001:4860:4860::8888");
        assertTrue("valid IPV6 string return true", new IsValidIPV6().exec(tuple));
    }
}