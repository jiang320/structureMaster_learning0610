package jia_mi_password;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static final String KEY_MD5 = "MD5";
    public static final String KEY_SHA = "SHA";

    public static String getResult_MD5(String inputStr) {
        System.out.println("=========加密前的而数据：" + inputStr);
        BigInteger bigInteger = null;
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        System.out.println("MD5加密后" + bigInteger.toString(16));
        return bigInteger.toString(16);
    }

    public static String getResult_SHA(String inputStr) {
        BigInteger sha = null;
        System.out.println("=======加密前的数据:" + inputStr);
        byte[] inputData = inputStr.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(inputData);
            sha = new BigInteger(messageDigest.digest());
            System.out.println("SHA加密后:" + sha.toString(32));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha.toString(32);
    }

    public static void main(String args[]) {
        try {
            String inputStr = "简单加密";
            getResult_MD5(inputStr);
            getResult_SHA(inputStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
