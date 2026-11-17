package week_02;

/** RC4 chỉ dùng học tập, không dùng cho dữ liệu thực. */
public final class RC4Cipher {
    private RC4Cipher() {}
    public static byte[] apply(byte[] data,byte[] key){
        if(key.length<1||key.length>256)throw new IllegalArgumentException("Khóa RC4 phải dài 1-256 byte.");
        int[] s=new int[256];for(int i=0;i<256;i++)s[i]=i;
        int j=0;for(int i=0;i<256;i++){j=(j+s[i]+(key[i%key.length]&255))&255;int t=s[i];s[i]=s[j];s[j]=t;}
        byte[] out=new byte[data.length];int i=0;j=0;
        for(int n=0;n<data.length;n++){i=(i+1)&255;j=(j+s[i])&255;int t=s[i];s[i]=s[j];s[j]=t;out[n]=(byte)(data[n]^s[(s[i]+s[j])&255]);}return out;
    }
    public static String encrypt(String text,String k){return CipherUtil.hex(apply(CipherUtil.utf8(text),CipherUtil.utf8(k)));}
    public static String decrypt(String text,String k){return CipherUtil.text(apply(CipherUtil.fromHex(text),CipherUtil.utf8(k)));}
}