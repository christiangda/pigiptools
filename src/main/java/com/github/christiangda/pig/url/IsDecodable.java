/*
 * isDecodable.java
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
import org.apache.pig.PigException;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Validate if a String is decodable like a application/x-www-form-urlencoded string using a specific encoding scheme. The supplied encoding is used
 * to determine what characters are represented by any consecutive sequences of the form "%xy".
 * <p/>
 * see http://www.w3.org/TR/html40/appendix/notes.html#non-ascii-chars
 * <p/>
 * <pre>
 * Example:
 * {@code
 * -- Define function call
 * DEFINE IsDecodable com.github.christiangda.pig.url.IsDecodable();
 *
 * -- input is a TSV of Base64 Encoded strings
 * input = LOAD 'input_file' AS (line:chararray);
 * output = FOREACH input GENERATE
 *      IsDecodable(line,'UTF-8') AS is_decodable_utf8_string,
 *      IsDecodable(line,'ISO-8859-1') AS is_decodable_latin1_string;
 * }
 * </pre>
 */
public class IsDecodable extends FilterFunc {

    // Default encoding type
    private String encoding = "UTF-8";

    public IsDecodable() {
        this.encoding = "UTF-8";
    }

    /**
     * @param encoding Encoding Type
     */
    public IsDecodable(final String encoding) {
        this.encoding = encoding;
    }

    @Override
    public Boolean exec(Tuple input) throws IOException {

        //
        if (input == null || input.size() == 0 || input.get(0) == null) {
            return false;
        }

        //
        if (input.get(0) == "") {
            return true;
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
            URLDecoder.decode(url, this.encoding);
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }
}
