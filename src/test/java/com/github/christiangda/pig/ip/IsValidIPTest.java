/*
 * IsValidIPTest.java
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