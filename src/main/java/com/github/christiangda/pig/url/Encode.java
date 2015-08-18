/*
 * Encode.java
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

import org.apache.pig.EvalFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.PigException;
import org.apache.pig.PigWarning;
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

/**
 * Translates a string into application/x-www-form-urlencoded format using a specific encoding scheme.
 * This method uses the supplied encoding scheme to obtain the bytes for unsafe characters.
 * <p>
 * see http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars
 * <p>
 * <pre>
 * Example:
 * {@code
 * -- Define function call
 * DEFINE EncodeUTF8 com.github.christiangda.pig.url.Encode();
 * DEFINE EncodeLatin1 com.github.christiangda.pig.url.Encode('ISO-8859-1');
 *
 * -- input is a TSV of Base64 Encoded strings
 * input = LOAD 'input_file' AS (line:chararray);
 * output = FOREACH input GENERATE
 *      EncodeUTF8(line) AS encoded_utf8_string,
 *      EncodeLatin1(line) AS encoded_latin1_string;
 * }
 * </pre>
 */
public class Encode extends EvalFunc<String> {

    // Default encoding type
    private String encoding = "UTF-8";

    public Encode() {
        this.encoding = "UTF-8";
    }

    /**
     * @param encoding Encoding Type
     */
    public Encode(final String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String exec(Tuple input) throws IOException {

        //
        if (input == null || input.size() == 0 || input.get(0) == null) {
            return null;
        }

        //
        if (input.get(0) == "") {
            return input.get(0).toString();
        }

        if (input.size() > 1)
            throw new ExecException("Wrong number of arguments > 1", PigException.ERROR);

        //
        String url;

        //Validating arguments
        Object arg0 = input.get(0);
        if (arg0 instanceof String)
            url = (String) arg0;
        else {
            String msg = "Invalid data type for argument 0 " + DataType.findTypeName(arg0);
            throw new ExecException(msg, PigException.ERROR);
        }

        //
        try {
            return URLEncoder.encode(url, this.encoding);
        } catch (UnsupportedEncodingException e) {
            // We overflowed. Give a warning, but do not throw an
            // exception.
            warn(e.toString(), PigWarning.UDF_WARNING_1);
            // Returning null will indicate to Pig that we failed but
            // we want to continue execution.
            return null;
        }
    }

    @Override
    public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
        List<FuncSpec> funcList = new ArrayList<FuncSpec>();
        funcList.add(new FuncSpec(this.getClass().getName(), new Schema(new Schema.FieldSchema(null, DataType.CHARARRAY))));
        return funcList;
    }
}