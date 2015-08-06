/*
 * IsValidIPV6.java
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

import com.github.christiangda.utils.ip.IP;
import org.apache.pig.FilterFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IsValidIPV6 extends FilterFunc {

    @Override
    public Boolean exec(Tuple input) throws IOException {

        if (input == null || input.size() == 0 || input.get(0) == null) {
            return false;
        }

        String ip;
        Object values = input.get(0);
        if (values instanceof String)
            ip = (String) values;
        else {
            return false;
        }

        return IP.isValidIPV6(ip);
    }
}