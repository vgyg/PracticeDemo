package com.example.zheng.bbjg;

import android.util.Xml;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Demo {
    public static void main(String[] arg) throws Exception {

        byte[] test = new String("dex").getBytes();
        System.out.println("========源字符串==========");
//        for (int i = 0; i < test.length; i++) {
        System.out.println(toHex(test));

        byte[] digest = hash(test);
        byte[] aaa = new byte[]{digest[0], digest[digest.length / 2], digest[digest.length - 1]};
        System.out.println("key: " + toHex(aaa));
//        byte[] currKey = hash(new byte[]{digest[0], digest[digest.length / 2], digest[digest.length - 1]});
//        byte[] currPt = test;
//        for (int i = 0; i < 10; i++) {
//            currPt = encrypt(currPt, currKey);
//            currKey = hash(currKey);
//        }
//        System.out.println("currKey: " + toHex(currKey));
//        System.out.println("currPt: " + toHex(currPt));

        byte[][] key10 = new byte[10][];
        System.out.println("10次key");
        key10[0]= hash(aaa);
        for (int i = 1; i < 10; i++) {
            key10[i]= hash(key10[i-1]);
            System.out.println(toHex(key10[i]));
        }
        String a = "027520dff1db52179f2ceac858ad56edeb68e7212deeee343d934b77b333132ebf6a84aedff5063985a62184f03f938b891208b20c8c5ba1b7eebb1501feb63f09f22a89641b45dca6d3a6094832022011370701d605a70802754df7858218688384b024365a308a5c6190579128c398f8d7180fac2b271e6001b861753d81a1061e5a908419335fcbc16cd9332ab07a0ee487d6d257d89c82262a5f55ec0ff5";
        byte[] resDetoByte = hexToByte(a);
        byte[] curKey1;
        System.out.println("10次keyni逆向");
        for (int i = 9; i > -1; i--) {
            curKey1 = key10[i];
            resDetoByte = decrypt(resDetoByte, curKey1);
            System.out.println(toHex(curKey1));
        }
        System.out.println(toHex(resDetoByte));
//        }
//
//

//
//        for (int i = 0; i < 10; i++) {
//            currPt = decrypt(resDetoByte, currKey);
//            currKey = hash(currKey);
//        }
//        String a = "74f0b165db8a628716b53a9d4f6405980db2f833afa1ed5eeb4304c5220bdc0b541f857a7348074b2a7775d691e71b490402621e8a53bad4cf7ad4fcc15f20a8066e087fc1b2ffb21c27463b5737e34738a6244e1630d8fa1bf4f38b7e71d707425c8225f240f4bd2b03d6c2471e900b75154eb6f9dfbdf5a4eca9de5163f9b3ee82959f166924e8ad5f1d744c51416a1db89638bb4d1411aa1b1307d88c1fb5";
//        byte[] resDetoByte = hexToByte(a);
//        for (int i = 0; i <= 255; i++) {
//            for (int j = 0; j <= 255; j++) {
//                for (int k = 0; k <= 255; k++) {
//                    byte[] key = new byte[]{Byte.valueOf((byte) i), Byte.valueOf((byte) j), Byte.valueOf((byte) k)};
//                    System.out.println(toHex(key));
//                    key = hash(key);
//                    for (int ii = 0; ii < 10; ii++) {
//                        resDetoByte=decrypt(resDetoByte,key);
//                        key = hash(key);
//                    }
//                    String cur = new String(resDetoByte);
//                    System.out.println("cur:"+cur);
//                    if(cur.contains("flag")){
////                    System.out.println("key:"+toHex(key));
//                    System.out.println("ori:"+cur);
//                    }
//                }
//
//            }
//        }


        System.out.println("====结束====");
    }
//


    public static boolean checkFlag(String keyStr, String flagStr) throws Exception {
        byte[] digest = hash(keyStr.getBytes());
        byte[] currKey = hash(new byte[]{digest[0], digest[digest.length / 2], digest[digest.length - 1]});
        byte[] currPt = flagStr.getBytes();
        for (int i = 0; i < 10; i++) {
            currPt = encrypt(currPt, currKey);
            currKey = hash(currKey);
        }
        if (toHex(currPt).equals("74f0b165db8a628716b53a9d4f6405980db2f833afa1ed5eeb4304c5220bdc0b541f857a7348074b2a7775d691e71b490402621e8a53bad4cf7ad4fcc15f20a8066e087fc1b2ffb21c27463b5737e34738a6244e1630d8fa1bf4f38b7e71d707425c8225f240f4bd2b03d6c2471e900b75154eb6f9dfbdf5a4eca9de5163f9b3ee82959f166924e8ad5f1d744c51416a1db89638bb4d1411aa1b1307d88c1fb5")) {
            return true;
        }
        return false;
    }


    public static String toHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 255);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * hex转byte数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex) {
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte) intVal);
        }
        return ret;
    }

    public static byte[] antiToHex(String str) {
        char[] newchar = str.toCharArray();
        byte[] bytes = new byte[str.length()];
        int temp = 0;
        int j = 0;
        for (int i = 0; i < newchar.length; i++) {
            if (i % 2 == 1) {
                String a = str.substring(temp, i + 1);
                temp = i + 1;
                j++;
                byte jj = Byte.decode(a);
                bytes[j] = jj;
            }
        }
        return bytes;
    }


//    /**
//     * 十六进制转换字符串
//     *
//     * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])
//     * @return String 对应的字符串
//     */
//    public static String hexStr2Str(String hexStr) {
//        String str = "0123456789ABCDEF";
//        char[] hexs = hexStr.toCharArray();
//        byte[] bytes = new byte[hexStr.length() / 2];
//        int n;
//
//        for (int i = 0; i < bytes.length; i++) {
//            n = str.indexOf(hexs[2 * i]) * 16;
//            n += str.indexOf(hexs[2 * i + 1]);
//            bytes[i] = (byte) (n & 0xff);
//        }
//        return new String(bytes);
//    }


    public static byte[] encrypt(byte[] in, byte[] key) throws Exception {
        Key aesKey = new SecretKeySpec(key, "AES");
        Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        encryptCipher.init(1, aesKey);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);

        cipherOutputStream.write(in);
        cipherOutputStream.flush();
        cipherOutputStream.close();
        return outputStream.toByteArray();
    }

    public static byte[] decrypt(byte[] in, byte[] key) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        Key aesKey = new SecretKeySpec(key, "AES");
        encryptCipher.init(Cipher.DECRYPT_MODE, aesKey);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, encryptCipher);
        cipherOutputStream.write(in);
        cipherOutputStream.flush();
        cipherOutputStream.close();
        return outputStream.toByteArray();
    }


    /**
     * content: 解密内容(base64编码格式)
     * slatKey: 加密时使用的盐，16位字符串
     * vectorKey: 加密时使用的向量，16位字符串
     */
//    public String decrypt(String base64Content, String slatKey, String vectorKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
//        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
//        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//        byte[] content = Base64.decodeBase64(base64Content);
//        byte[] encrypted = cipher.doFinal(content);
//        return new String(encrypted);
//    }


    /**
     * content: 加密内容
     * slatKey: 加密的盐，16位字符串
     * vectorKey: 加密的向量，16位字符串
     */
//    public String encrypt(String content, String slatKey, String vectorKey) throws Exception {
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "AES");
//        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
//        byte[] encrypted = cipher.doFinal(content.getBytes());
//        return Base64.encodeBase64String(encrypted);
//    }
    public static byte[] hash(byte[] in) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(in);
        return md.digest();
    }
}
