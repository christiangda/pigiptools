/*
 * UrlEncode.java
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

import org.apache.pig.FilterFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.PigException;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Encodable extends FilterFunc {

    @Override
    public Boolean exec(Tuple input) throws IOException {

        //
        if (input == null || input.size() == 0 || input.size() > 2 || input.get(0) == null) {
            return false;
        }

        //
        String url;
        String enc; // The name of a supported character encoding.

        //Validating arguments
        try {
            Object arg0 = input.get(0);
            if (arg0 instanceof String)
                url = (String) arg0;
            else {
                int errCode = 2102;
                String msg = "Invalid data type for argument 0 " + DataType.findTypeName(arg0);
                throw new ExecException(msg, errCode, PigException.BUG);
            }

            Object arg1 = input.get(1);
            if (arg1 instanceof String)
                enc = (String) arg1;
            else {
                int errCode = 2102;
                String msg = "Invalid data type for argument 1 " + DataType.findTypeName(arg1);
                throw new ExecException(msg, errCode, PigException.BUG);
            }
        } catch (ExecException ee) {
            throw ee;
        }

        //
        try {
            URLEncoder.encode(url, enc);
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    @Override
    public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
        List<FuncSpec> funcList = new ArrayList<FuncSpec>();
        funcList.add(new FuncSpec(this.getClass().getName(), new Schema(new Schema.FieldSchema(null, DataType.BOOLEAN))));
        return funcList;
    }
}
