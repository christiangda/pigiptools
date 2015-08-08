/*
 * IsDecodableTest.java
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

import static org.junit.Assert.assertTrue;

/**
 * Created by christian on 8/8/15.
 */
public class IsDecodableTest {

    @Test
    public void testIsDecodableBothArgumentsNull() throws IOException {
        Tuple tuple = Util.buildTuple(null, null);
        try {
            new IsDecodable().exec(tuple);
        } catch (Exception e) {
            assertTrue("nulls arguments return Exception", true);
        }
    }

    @Test
    public void testIsDecodableBothArgumentsEmpty() throws IOException {
        Tuple tuple = Util.buildTuple("", "");
        try {
            new IsDecodable().exec(tuple);
        } catch (Exception e) {
            assertTrue("Empty arguments return Exception", true);
        }
    }

    @Test
    public void testIsDecodableMoreArguments() throws IOException {
        Tuple tuple = Util.buildTuple("one", "", "three");
        try {
            new IsDecodable().exec(tuple);
        } catch (Exception e) {
            assertTrue("Bad number of arguments return Exception", true);
        }
    }

    @Test
    public void testIsDecodableGoodParametersOne() throws IOException {
        Tuple tuple = Util.buildTuple("/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969", "UTF-8");
        assertTrue("good arguments return true", new IsDecodable().exec(tuple));
    }
}