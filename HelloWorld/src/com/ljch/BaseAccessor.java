package com.rdf.cdk.util;

import android.net.Uri;
import com.rdf.cdk.jni.Common;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CodeUtil {
    private static String a() {
        MemoryCache instance = MemoryCache.getInstance();
        String crypt = MD5.crypt("CodeUtil.tzk");
        Common.tzk(crypt);
        String str = (String) instance.getValue(crypt);
        instance.removeValue(crypt);
        return str;
    }

    private static String b() {
        MemoryCache instance = MemoryCache.getInstance();
        String crypt = MD5.crypt("CodeUtil.tziv");
        Common.tziv(crypt);
        String str = (String) instance.getValue(crypt);
        instance.removeValue(crypt);
        return str;
    }

    public static String bytes2Hex(byte[] bArr) {
        String str = "";
        int i = 0;
        while (i < bArr.length) {
            String toHexString = Integer.toHexString(bArr[i] & 255);
            if (toHexString.length() == 1) {
                str = str + "0";
            }
            i++;
            str = str + toHexString;
        }
        return str;
    }

    private static String c() {
        MemoryCache instance = MemoryCache.getInstance();
        String crypt = MD5.crypt("CodeUtil.cack");
        Common.cack(crypt);
        String str = (String) instance.getValue(crypt);
        instance.removeValue(crypt);
        return str;
    }

    private static String d() {
        MemoryCache instance = MemoryCache.getInstance();
        String crypt = MD5.crypt("CodeUtil.caciv");
        Common.caciv(crypt);
        String str = (String) instance.getValue(crypt);
        instance.removeValue(crypt);
        return str;
    }

    public static String decode(String str) {
        String str2 = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            MemoryCache instance = MemoryCache.getInstance();
            String crypt = MD5.crypt("CodeUtil.dcdk");
            Common.dcdk(crypt);
            String str3 = (String) instance.getValue(crypt);
            instance.removeValue(crypt);
            MemoryCache instance2 = MemoryCache.getInstance();
            String crypt2 = MD5.crypt("CodeUtil.dcdiv");
            Common.dcdiv(crypt2);
            String str4 = (String) instance2.getValue(crypt2);
            instance2.removeValue(crypt2);
            byte[] bytes = str3.getBytes("utf-8");
            byte[] bytes2 = str4.getBytes("utf-8");
            Key secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance3 = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance3.init(2, secretKeySpec, new IvParameterSpec(bytes2));
            return new String(instance3.doFinal(Base64.decode(str.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            return str2;
        }
    }

    public static String decodeCache(String str) {
        String str2 = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            String c = c();
            String d = d();
            byte[] bytes = c.getBytes("utf-8");
            byte[] bytes2 = d.getBytes("utf-8");
            Key secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance.init(2, secretKeySpec, new IvParameterSpec(bytes2));
            return new String(instance.doFinal(Base64.decode(str.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            return str2;
        }
    }

    public static String decodeQrCode(String str) {
        String str2 = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            String a = a();
            String b = b();
            String decode = Uri.decode(str);
            byte[] bytes = a.getBytes("utf-8");
            byte[] bytes2 = b.getBytes("utf-8");
            Key secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance.init(2, secretKeySpec, new IvParameterSpec(bytes2));
            return new String(instance.doFinal(Base64.decode(decode.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            return str2;
        }
    }

    public static String encode(String str) {
        String str2 = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            MemoryCache instance = MemoryCache.getInstance();
            String crypt = MD5.crypt("CodeUtil.ecdk");
            Common.ecdk(crypt);
            String str3 = (String) instance.getValue(crypt);
            instance.removeValue(crypt);
            MemoryCache instance2 = MemoryCache.getInstance();
            String crypt2 = MD5.crypt("CodeUtil.ecdiv");
            Common.ecdiv(crypt2);
            String str4 = (String) instance2.getValue(crypt2);
            instance2.removeValue(crypt2);
            byte[] bytes = str3.getBytes("utf-8");
            byte[] bytes2 = str4.getBytes("utf-8");
            Key secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance3 = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance3.init(1, secretKeySpec, new IvParameterSpec(bytes2));
            return new String(Base64.encode(instance3.doFinal(str.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            return str2;
        }
    }

    public static String encodeCache(String str) {
        String str2 = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            String c = c();
            String d = d();
            byte[] bytes = c.getBytes("utf-8");
            byte[] bytes2 = d.getBytes("utf-8");
            Key secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance.init(1, secretKeySpec, new IvParameterSpec(bytes2));
            return new String(Base64.encode(instance.doFinal(str.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            return str2;
        }
    }

    public static String encodeMobile(String str) {
        String str2 = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            MemoryCache instance = MemoryCache.getInstance();
            String crypt = MD5.crypt("CodeUtil.mobk");
            Common.mobk(crypt);
            String str3 = (String) instance.getValue(crypt);
            instance.removeValue(crypt);
            MemoryCache instance2 = MemoryCache.getInstance();
            String crypt2 = MD5.crypt("CodeUtil.mobiv");
            Common.mobiv(crypt2);
            String str4 = (String) instance2.getValue(crypt2);
            instance2.removeValue(crypt2);
            byte[] bytes = str3.getBytes("utf-8");
            byte[] bytes2 = str4.getBytes("utf-8");
            Key secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance3 = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance3.init(1, secretKeySpec, new IvParameterSpec(bytes2));
            return new String(Base64.encode(instance3.doFinal(str.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            return str2;
        }
    }

    public static String encodeQrCode(String str) {
        String str2 = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            String a = a();
            String b = b();
            byte[] bytes = a.getBytes("utf-8");
            byte[] bytes2 = b.getBytes("utf-8");
            Key secretKeySpec = new SecretKeySpec(bytes, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS7Padding");
            instance.init(1, secretKeySpec, new IvParameterSpec(bytes2));
            return new String(Base64.encode(instance.doFinal(str.getBytes("utf-8"))), "utf-8");
        } catch (Exception e) {
            return str2;
        }
    }

    public static String sha256(String str) {
        String str2 = "";
        try {
            MemoryCache instance = MemoryCache.getInstance();
            String crypt = MD5.crypt("CodeUtil.salt");
            Common.salt(crypt);
            String str3 = (String) instance.getValue(crypt);
            instance.removeValue(crypt);brand
            int length = str3.length();
            crypt = str3.substring(0, length / 2);
            String str4 = crypt + str + str3.substring(length / 2);
            crypt = "HmacSHA256";
            Mac instance2 = Mac.getInstance(crypt);
            instance2.init(new SecretKeySpec(str3.getBytes(), crypt));
            return new String(Base64.encode(instance2.doFinal(str4.getBytes())), "utf-8");
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
            return str2;
        }
    }
}
