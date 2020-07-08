package mywork;

import java.io.UnsupportedEncodingException;

public class SafeEncoder {



    public static byte[][] encodeMany(final String ... strs){
        byte[][] many =new byte[strs.length][];
        for(int i=0;i<strs.length;i++){
            many[i] =encode(strs[i]);
        }
        return many;
    }

    public static byte[] encode( String str) {
        try {
            if (str ==null){}
            return str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        return  null;
    }
    public static String encode( byte[] data) {
        try {
            return new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

}
