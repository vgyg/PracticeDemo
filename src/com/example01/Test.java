package com.example01;

import sun.misc.VM;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.MessageDigest;

/**
 * Created by z on 2018/11/17.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        for (byte i = -128; i <= 127; i++) {
            for (byte j = -128; j <= 127; j++) {
                for (byte k = -128; k <= 127; k++) {
                    byte[] a = new byte[]{i, j, k};
//                        System.out.print(a[l]);
                    byte[] Key = hash(a);
                    String flagStr = "jhsjdfhaj";
                    byte[] currPt = flagStr.getBytes();
                    for (int m = 0; m < 10; m++) {
                        currPt = encrypt(currPt, Key);
                        Key = hash(Key);
                    }
                    String res = toHex(currPt);
                    if (res.equals("74f0b165db8a628716b53a9d4f6405980db2f833afa1ed5eeb4304c5220bdc0b541f857a7348074b2a7775d691e71b490402621e8a53bad4cf7ad4fcc15f20a8066e087fc1b2ffb21c27463b5737e34738a6244e1630d8fa1bf4f38b7e71d707425c8225f240f4bd2b03d6c2471e900b75154eb6f9dfbdf5a4eca9de5163f9b3ee82959f166924e8ad5f1d744c51416a1db89638bb4d1411aa1b1307d88c1fb5")) {
                        System.out.println("====================");
                        System.out.println(res);
                    }


//                    System.out.println();
                    if (k == 127) {
                        break;
                    }
                }
                if (j == 127) {
                    break;
                }
            }
            if (i == 127) {
                break;
            }
        }

        System.out.println("=========结束===========");
    }


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

    public static byte[] hash(byte[] in) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(in);
        return md.digest();
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


}
