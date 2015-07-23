/*
 * IsValidIP.java
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
import org.apache.pig.PigException;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;

import java.io.IOException;

public class IsValidIP extends FilterFunc {

    @Override
    public Boolean exec(Tuple input) throws IOException {

        if (input == null || input.size() == 0 || input.get(0) == null) {
            return false;
        }

        String ip;
        try {
            Object values = input.get(0);
            if (values instanceof String)
                ip = (String) values;
            else {
                int errCode = 2102;
                String msg = "Cannot test a " + DataType.findTypeName(values) + " for a valid ip string.";
                throw new ExecException(msg, errCode, PigException.BUG);
            }
        } catch (ExecException ee) {
            throw ee;
        }
        return IP.isValidIP(ip);
    }
}
