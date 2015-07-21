package com.github.christiangda.pig.ip;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IsValidIPV4Test {

    @Test
    public void testIsValidIPV4WhenNull() throws Exception {
        Tuple tuple = Util.buildTuple();
        assertFalse("null object return false", new IsValidIPV4().exec(tuple));
    }

    @Test
    public void testIsValidIPV4WhenEmpty() throws Exception {
        Tuple tuple = Util.buildTuple("");
        assertFalse("empty string return false", new IsValidIPV4().exec(tuple));
    }

    @Test
    public void testIsValidIPV4WhenInvalidIPV4() throws Exception {
        Tuple tuple = Util.buildTuple("192.168.1.");
        assertFalse("invalid IPV4 string return false", new IsValidIPV4().exec(tuple));
    }

    @Test
    public void testIsValidIPV4WhenValidIPV4() throws Exception {
        Tuple tuple = Util.buildTuple("192.168.1.1");
        assertTrue("valid IPV4 string return true", new IsValidIPV4().exec(tuple));
    }
}