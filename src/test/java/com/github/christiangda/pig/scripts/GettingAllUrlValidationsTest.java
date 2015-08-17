/*
 * GettingAllUrlValidationsTest.java
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

package com.github.christiangda.pig.scripts;

import com.github.christiangda.utils.pigunit.PigUnitUtil;
import org.apache.pig.pigunit.PigTest;
import org.junit.Test;

public class GettingAllUrlValidationsTest {

    private static final String PIG_SCRIPT = "src/test/resources/scripts/GettingAllUrlValidations.pig";

    @Test
    public void testPigScript() throws Exception {

        String[] args = {
                "LIBRARY_PATH=./target"
        };

        String[] input = {
                null,
                "",
                "a b",
                "/key=value,key2=value%202",
                "/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969"
        };

        String[] output = {
                "(null,��e,bnVsbA==,null,null,null,null,true,true)",
                "(,,,,,,,false,false)",
                "(a b,i,YSBi,a b,a+b,a b,a+b,true,true)",
                "(/key=value,key2=value%202,�G�,L2tleT12YWx1ZSxrZXkyPXZhbHVlJTIwMg==,/key=value,key2=value 2,%2Fkey%3Dvalue%2Ckey2%3Dvalue%25202,/key=value,key2=value 2,%2Fkey%3Dvalue%2Ckey2%3Dvalue%25202,true,true)",
                "(/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969,���Y\u0019����s\b�v�?o��\b,LzkzUldSbno5L21hY3dpbmRvdy9iLz9DbGlja0lEPTc5NzUwMjA4ODA0JlB1YklEPTIyNjk2OQ==,/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969,%2F93RWRnz9%2Fmacwindow%2Fb%2F%3FClickID%3D79750208804%26PubID%3D226969,/93RWRnz9/macwindow/b/?ClickID=79750208804&PubID=226969,%2F93RWRnz9%2Fmacwindow%2Fb%2F%3FClickID%3D79750208804%26PubID%3D226969,true,true)"
        };

        PigTest pigTest = PigUnitUtil.createPigTest(PIG_SCRIPT, args);

        pigTest.assertOutput("data", input, "uris", output);
    }
}
