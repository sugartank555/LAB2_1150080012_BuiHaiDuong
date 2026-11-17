package week_02;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/** 3DES chỉ phục vụ học tập. */
public final class TripleDESCipher {
    private TripleDESCipher() {}
    private static SecretKeySpec key(String s){byte[] b=CipherUtil.utf8(s);if(b.length!=24)throw new IllegalArgumentException("Khóa TripleDES phải đúng 24 byte.");return new SecretKeySpec(b,"DESede");}
    public static String encrypt(String text,String k) throws Exception {byte[] iv=CipherUtil.randomBytes(8);Cipher c=Cipher.getInstance("DESede/CBC/PKCS5Padding");c.init(Cipher.ENCRYPT_MODE,key(k),new IvParameterSpec(iv));return CipherUtil.b64(CipherUtil.concat(iv,c.doFinal(CipherUtil.utf8(text))));}
    public static String decrypt(String text,String k) throws Exception {byte[] all=CipherUtil.fromB64(text);if(all.length<16)throw new IllegalArgumentException("Bản mã quá ngắn.");Cipher c=Cipher.getInstance("DESede/CBC/PKCS5Padding");c.init(Cipher.DECRYPT_MODE,key(k),new IvParameterSpec(Arrays.copyOfRange(all,0,8)));return CipherUtil.text(c.doFinal(all,8,all.length-8));}
}