package com.rdf.cdk.jni;

public class Common {
    static {
        System.loadLibrary("common");
    }

    public static native int add(int i, int i2);

    public static native int addError(int i, int i2);

    public static native String apkSign();

    public static native void caciv(String str);

    public static native void cack(String str);

    public static native int checkApkSign();

    public static native void dcdiv(String str);

    public static native void dcdk(String str);

    public static native void decryptFile(String str, String str2);

    public static native void decryptFileInMemory(String str);

    public static native void ecdiv(String str);

    public static native void ecdk(String str);

    public static native void encrypt(String str);

    public static native void encryptChannel(String str);

    public static native void encryptFile(String str, String str2);

    public static native void encryptN(String str, String str2);

    public static native void mobiv(String str);

    public static native void mobk(String str);

    public static native void salt(String str);

    public static native void tziv(String str);

    public static native void tzk(String str);
}
