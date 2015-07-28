/*
 * IsValidIPV4Test.java
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

package com.github.christiangda.pig.ip;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import static org.junit.Assert.*;

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