package week_02;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public final class AESCipher {
    private AESCipher() {}
    public static String encrypt(String text,String password) throws Exception {
        CipherUtil.requireKey(password);byte[] salt=CipherUtil.randomBytes(16), nonce=CipherUtil.randomBytes(12);
        byte[] k=CipherUtil.pbkdf2(password.toCharArray(),salt,120000,256);
        Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(k,"AES"),new GCMParameterSpec(128,nonce));
        Arrays.fill(k,(byte)0);return CipherUtil.b64(CipherUtil.concat(CipherUtil.concat(salt,nonce),c.doFinal(CipherUtil.utf8(text))));
    }
    public static String decrypt(String text,String password) throws Exception {
        CipherUtil.requireKey(password);byte[] all=CipherUtil.fromB64(text);if(all.length<44)throw new IllegalArgumentException("Bản mã AES quá ngắn.");
        byte[] k=CipherUtil.pbkdf2(password.toCharArray(),Arrays.copyOfRange(all,0,16),120000,256);
        Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.DECRYPT_MODE,new SecretKeySpec(k,"AES"),new GCMParameterSpec(128,Arrays.copyOfRange(all,16,28)));
        Arrays.fill(k,(byte)0);return CipherUtil.text(c.doFinal(Arrays.copyOfRange(all,28,all.length)));
    }
}