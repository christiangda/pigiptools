/*
 * EncodableTest.java
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

import com.github.christiangda.pig.geoip.GetCountryName;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.Tuple;
import org.apache.pig.test.Util;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;


public class EncodableTest {

    @Test
    public void testEncodableNull() throws IOException {
        Tuple tuple = Util.buildTuple(null,null);
        try {
            new Encodable().exec(tuple);
        }catch (Exception e){
            assertTrue("null object return Exception",true);
        }
    }

    @Test
    public void testEncodableEmpty() throws IOException {
        Tuple tuple = Util.buildTuple("","");
        try {
            new Encodable().exec(tuple);
        }catch (Exception e){
            assertTrue("empty object return Exception",true);
        }
    }

    @Test
    public void testEncodableMoreArguments() throws IOException {
        Tuple tuple = Util.buildTuple("","","");
        assertFalse("more arguments object return false", new Encodable().exec(tuple));
    }

    @Test
    public void testEncodableGoodParametersOne() throws IOException {
        Tuple tuple = Util.buildTuple("/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969","UTF-8");
        assertTrue("good arguments return true", new Encodable().exec(tuple));
    }

    @Test
    public void testEncodableBadParametersOne() throws IOException {
        Tuple tuple = Util.buildTuple("/0CwJZU7m/a/?utm_source=lee&utm_medium=Adv&utm_term=aw snap&utm_content=63960-1&utm_campaign=Beta&domain=", "UTF-8");
        assertFalse("bad arguments return false", new Encodable().exec(tuple));
    }

}