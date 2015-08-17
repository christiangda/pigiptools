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

    //
    private String ALTERNATIVE_ENCODING_TYPE = "ISO-8859-1";

    @Test
    public void testEncodeArgumentNull() throws IOException {
        Tuple tuple = Util.buildTuple();
        assertEquals("null argument return null", null, new Decode().exec(tuple));
    }

    @Test
    public void testEncodeArgumentEmpty() throws IOException {
        Tuple tuple = Util.buildTuple("");
        assertEquals("empty argument return empty", "", new Decode().exec(tuple));
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
        Tuple tuple = Util.buildTuple("%2F93RWRnz9%2Fmacwindow%2Fb%2F%3FClickID%3D79750208804%26PubID%3D226969");
        assertEquals("Goods arguments return the right value", "/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969", new Decode().exec(tuple));
    }
}