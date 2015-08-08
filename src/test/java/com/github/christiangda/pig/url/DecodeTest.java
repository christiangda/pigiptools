/*
 * DecodeTest.java
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

package com.github.christiangda.pig.url;

import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DecodeTest {
    @Test
    public void testEncodeBothArgumentsNull() throws IOException {
        Tuple tuple = Util.buildTuple(null, null);
        try {
            new Decode().exec(tuple);
        } catch (Exception e) {
            assertTrue("nulls arguments return Exception", true);
        }
    }

    @Test
    public void testEncodeBothArgumentsEmpty() throws IOException {
        Tuple tuple = Util.buildTuple("", "");
        try {
            new Decode().exec(tuple);
        } catch (Exception e) {
            assertTrue("Empty arguments return Exception", true);
        }
    }

    @Test
    public void testEncodeMoreArguments() throws IOException {
        Tuple tuple = Util.buildTuple("one", "", "three");
        try {
            new Decode().exec(tuple);
        } catch (Exception e) {
            assertTrue("Bad number of arguments return Exception", true);
        }
    }

    @Test
    public void testEncodeGoodParametersOne() throws IOException {
        Tuple tuple = Util.buildTuple("%2F93RWRnz9%2Fmacwindow%2Fb%2F%3FClickID%3D79750208804%26PubID%3D226969", "UTF-8");
        assertEquals("Goods arguments return the right value", "/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969", new Decode().exec(tuple));
    }
}