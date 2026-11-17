package week_02;
public class Lab2SelfTest {
  static void eq(String label,String expected,String actual){if(!expected.equals(actual))throw new AssertionError(label+" expected="+expected+" actual="+actual);System.out.println("PASS "+label);}
  public static void main(String[] args)throws Exception{
    String s="Xin chào Lab 2 - Mã hóa hiện đại!";
    String a=DESCipher.encrypt(s,"12345678");eq("DES",s,DESCipher.decrypt(a,"12345678"));
    String t=TripleDESCipher.encrypt(s,"123456789012345678901234");eq("3DES",s,TripleDESCipher.decrypt(t,"123456789012345678901234"));
    String b=AESCipher.encrypt(s,"MatKhauAES@2026");eq("AES-GCM",s,AESCipher.decrypt(b,"MatKhauAES@2026"));
    eq("RC4 vector","BBF316E8D940AF0AD3",RC4Cipher.encrypt("Plaintext","Key"));
    String iv=TwofishCipher.newIv();String f=TwofishCipher.encrypt(s,"Twofish@Lab2",iv);eq("Twofish",s,TwofishCipher.decrypt(f,"Twofish@Lab2",iv));
    String[] ff=TwofishCipher.fromFile(TwofishCipher.toFile(iv,f));eq("Twofish IV",iv,ff[0]);eq("Twofish ciphertext",f,ff[1]);
    String k=AESCTRCipher.newKey(), n=AESCTRCipher.newNonce();String c=AESCTRCipher.encrypt(s,k,n);eq("AES-CTR",s,AESCTRCipher.decrypt(c,k,n));
    String[] parts=AESCTRCipher.fromFile(AESCTRCipher.toFile(n,c));eq("CTR nonce",n,parts[0]);eq("CTR ciphertext",c,parts[1]);
  }
}
