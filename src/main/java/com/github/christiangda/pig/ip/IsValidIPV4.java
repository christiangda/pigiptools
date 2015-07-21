package com.github.christiangda.pig.ip;

import com.github.christiangda.utils.ip.IP;
import org.apache.pig.FilterFunc;
import org.apache.pig.PigException;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;

import java.io.IOException;


public class IsValidIPV4 extends FilterFunc {

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
        return IP.isValidIPV4(ip);
    }
}
