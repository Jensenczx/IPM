package com.example.chenjensen.ipm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by chenjensen on 16/3/4.
 */
public class IOUtils {
    public static void close(InputStream stream){
        try {
            if(stream!=null)
            stream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void close(OutputStream stream){
        try{
            if(stream!=null)
            stream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
