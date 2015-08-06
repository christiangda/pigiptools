/*
 * Base64EncodeTest.java
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

import static org.junit.Assert.*;

public class Base64EncodeTest {

    @Test
    public void testBase64EncodeMoreArguments() throws IOException {
        Tuple tuple = Util.buildTuple(null, "string_value");
        try {
            new Base64Encode().exec(tuple);
        }catch (Exception e){
            assertTrue("bad number of arguments return Exception",true);
        }
    }

    @Test
    public void testBase64EncodeNullArg() throws IOException {
        Tuple tuple = Util.buildTuple();
        assertNull("null arguments return null", new Base64Encode().exec(tuple));
    }

    @Test
    public void testBase64EncodeGoodStringOne() throws IOException {
        Tuple tuple = Util.buildTuple("This is a test!");
        assertEquals("null arguments return null", "VGhpcyBpcyBhIHRlc3Qh", new Base64Encode().exec(tuple));
    }
}