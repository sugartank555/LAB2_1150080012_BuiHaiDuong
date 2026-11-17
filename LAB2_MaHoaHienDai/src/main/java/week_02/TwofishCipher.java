package week_02;

import java.security.MessageDigest;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
/** Twofish/CBC/PKCS7Padding, SHA-256(Key), IV 16 byte HEX. Demo theo LAB. */
public final class TwofishCipher {
    static { if(Security.getProvider("BC")==null)Security.addProvider(new BouncyCastleProvider()); }
    private TwofishCipher() {}
    public static String newIv(){return CipherUtil.hex(CipherUtil.randomBytes(16));}
    private static byte[] run(int mode,byte[] data,String key,String ivHex) throws Exception {
        CipherUtil.requireKey(key);byte[] iv=CipherUtil.fromHex(ivHex);if(iv.length!=16)throw new IllegalArgumentException("IV phải đúng 32 ký tự HEX.");
        byte[] k=MessageDigest.getInstance("SHA-256").digest(CipherUtil.utf8(key));
        Cipher cipher=Cipher.getInstance("Twofish/CBC/PKCS7Padding","BC");cipher.init(mode,new SecretKeySpec(k,"Twofish"),new IvParameterSpec(iv));return cipher.doFinal(data);
    }
    public static String encrypt(String text,String key,String iv){return handle(true,text,key,iv);}
    public static String decrypt(String text,String key,String iv){return handle(false,text,key,iv);}
    private static String handle(boolean e,String text,String key,String iv){try{byte[] r=run(e?Cipher.ENCRYPT_MODE:Cipher.DECRYPT_MODE,e?CipherUtil.utf8(text):CipherUtil.fromB64(text),key,iv);return e?CipherUtil.b64(r):CipherUtil.text(r);}catch(IllegalArgumentException ex){throw ex;}catch(Exception ex){throw new IllegalStateException("Twofish thất bại: "+ex.getMessage(),ex);}}
    public static String toFile(String iv,String cipher){return "IV="+iv.trim()+"\n"+cipher.trim()+"\n";}
    public static String[] fromFile(String content){String[] lines=content.trim().split("\\R",2);if(lines.length!=2||!lines[0].startsWith("IV="))throw new IllegalArgumentException("Tệp cần IV= ở dòng đầu.");return new String[]{lines[0].substring(3).trim(),lines[1].trim()};}
}