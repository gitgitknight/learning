package com.ly.rep.escap;

import org.codehaus.stax2.io.EscapingWriterFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: fellon
 * Created Date: 2020/8/24 11:18
 **/
public class CustomizedXMLEscapingWriterFactory implements EscapingWriterFactory {
    private static Map<String, String> escape;

    private static Map<String, String> getEscapes() {
        if (escape == null) {
            escape = new HashMap<>();
            escape.put("&", "&amp;");
            escape.put("<", "&lt;");
            escape.put(">", "&gt;");
            escape.put("\r\n", "&#x2028;");
            escape.put("\t", "&#x00A0;");
        }

        return escape;
    }

    public Writer createEscapingWriterFor(final Writer out, String enc) {
        return new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                String s = new String(cbuf, off, len);
                String replace = s
                        .replace("&", "&amp;")
                        .replace("<", "&lt;")
                        .replace(">", "&gt;")
                        .replace("\r\n", "&#x2028;")
                        .replace("\t", "&#x00A0;");


//                StringBuffer result = new StringBuffer("");
//                for (int i = off; i < len; i++) {
//                    if (getEscapes().containsKey(cbuf[i])) {
//                        result.append(getEscapes().get(cbuf[i]));
//                    } else {
//                        result.append(cbuf[i]);
//                    }
//                }

                out.write(replace);
            }

            @Override
            public void flush() throws IOException {
                out.flush();
            }

            @Override
            public void close() throws IOException {
                out.close();
            }
        };
    }

    public Writer createEscapingWriterFor(OutputStream out, String enc) {
        throw new IllegalArgumentException("not supported");
    }
}
