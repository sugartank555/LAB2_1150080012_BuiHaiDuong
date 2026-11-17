package week_02;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class CipherUtil {
    private static final SecureRandom RNG = new SecureRandom();
    private CipherUtil() {}
    public static byte[] utf8(String s) { return s.getBytes(StandardCharsets.UTF_8); }
    public static String text(byte[] bytes) { return new String(bytes, StandardCharsets.UTF_8); }
    public static byte[] randomBytes(int n) { byte[] bytes = new byte[n]; RNG.nextBytes(bytes); return bytes; }
    public static String b64(byte[] bytes) { return Base64.getEncoder().encodeToString(bytes); }
    public static byte[] fromB64(String s) { try { return Base64.getDecoder().decode(s.trim()); } catch (IllegalArgumentException e) { throw new IllegalArgumentException("Base64 không hợp lệ.",e); } }
    public static String hex(byte[] bytes) { return HexFormat.of().withUpperCase().formatHex(bytes); }
    public static byte[] fromHex(String s) { try { return HexFormat.of().parseHex(s.trim()); } catch (IllegalArgumentException e) { throw new IllegalArgumentException("Chuỗi HEX không hợp lệ.",e); } }
    public static byte[] concat(byte[] a,byte[] b) { byte[] r=java.util.Arrays.copyOf(a,a.length+b.length); System.arraycopy(b,0,r,a.length,b.length);return r; }
    public static void requireKey(String s) { if(s==null || s.isEmpty()) throw new IllegalArgumentException("Hãy nhập Key."); }
    public static byte[] pbkdf2(char[] password,byte[] salt,int iterations,int bits) {
        PBEKeySpec spec=new PBEKeySpec(password,salt,iterations,bits);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        catch(Exception e){ throw new IllegalStateException("Không tạo được PBKDF2.",e); }
        finally { spec.clearPassword(); }
    }
}