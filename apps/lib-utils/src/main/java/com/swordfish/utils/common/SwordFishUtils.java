package com.swordfish.utils.common;

import com.swordfish.utils.exception.CreateInstanceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SwordFishUtils {

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String hashMd5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static Object createInstance(String path) {
        try {
            Class<?> clazz = Class.forName(path);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new CreateInstanceException();
        }
    }
}
