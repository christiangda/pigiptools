/*
 * IsValidIPV6Test.java
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