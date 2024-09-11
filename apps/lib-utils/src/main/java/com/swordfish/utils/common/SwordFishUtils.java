package com.swordfish.utils.common;

import com.google.gson.Gson;
import com.swordfish.utils.exception.CreateInstanceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SwordFishUtils {

    private static final Gson gson = new Gson();
    private static final Random rand = new Random();

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

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static int randomBetween(int min, int max) {
        return min + rand.nextInt(max - min + 1);
    }

    public static <T> T randomInList(List<T> list) {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(rand.nextInt(list.size()));
    }

    /**
     * @param utcTimeStr: "2024-09-07T12:30:00Z"
     */
    public static Date convertToUTCDate(String utcTimeStr) {
        Instant instant = Instant.parse(utcTimeStr);
        return Date.from(instant);
    }

    public static String convertToUTCStr(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime utcTime = instant.atZone(ZoneOffset.UTC);
        return utcTime.format(DateTimeFormatter.ISO_INSTANT);
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
