package week_02;

import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/** Không dùng lại cặp (key, nonce). CTR không có cơ chế xác thực. */
public final class AESCTRCipher {
    private AESCTRCipher() {}
    public static String newKey(){return CipherUtil.b64(CipherUtil.randomBytes(32));}
    public static String newNonce(){return CipherUtil.b64(CipherUtil.randomBytes(12));}
    private static byte[] run(int mode,byte[] data,String key64,String nonce64)throws Exception {
        byte[] key=CipherUtil.fromB64(key64),nonce=CipherUtil.fromB64(nonce64);
        if(key.length!=32||nonce.length!=12)throw new IllegalArgumentException("Key cần 32 byte, nonce cần 12 byte (Base64).");
        byte[] iv=Arrays.copyOf(nonce,16);iv[15]=1;Cipher c=Cipher.getInstance("AES/CTR/NoPadding");c.init(mode,new SecretKeySpec(key,"AES"),new IvParameterSpec(iv));return c.doFinal(data);
    }
    public static String encrypt(String text,String key,String nonce)throws Exception{return CipherUtil.b64(run(Cipher.ENCRYPT_MODE,CipherUtil.utf8(text),key,nonce));}
    public static String decrypt(String text,String key,String nonce)throws Exception{return CipherUtil.text(run(Cipher.DECRYPT_MODE,CipherUtil.fromB64(text),key,nonce));}
    public static String toFile(String nonce,String c){return "NONCE="+nonce.trim()+"\n"+c.trim()+"\n";}
    public static String[] fromFile(String text){String[] lines=text.trim().split("\\R",2);if(lines.length!=2||!lines[0].startsWith("NONCE="))throw new IllegalArgumentException("Tệp không đúng định dạng NONCE=...");return new String[]{lines[0].substring(6).trim(),lines[1].trim()};}
}