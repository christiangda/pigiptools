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
                "testing",
                "",
                "a b",
                "c%20d",
                "/hey1=valu1&key1=value 2"
        };

        String[] output = {
                "(testing,,dGVzdGluZw==,testing,testing,testing,testing,true,true)",
                "(,,,,,,,false,false)",
                "(a b,,YSBi,a b,a+b,a b,a+b,true,true)",
                "(c%20d,,YyUyMGQ=,c d,c%2520d,c d,c%2520d,true,true)",
                "(/hey1=valu1&key1=value 2,,L2hleTE9dmFsdTEma2V5MT12YWx1ZSAy,/hey1=valu1&key1=value 2,%2Fhey1%3Dvalu1%26key1%3Dvalue+2,/hey1=valu1&key1=value 2,%2Fhey1%3Dvalu1%26key1%3Dvalue+2,true,true)"
        };

        PigTest pigTest = PigUnitUtil.createPigTest(PIG_SCRIPT, args);

        pigTest.assertOutput("data", input, "uris", output);
    }
}
