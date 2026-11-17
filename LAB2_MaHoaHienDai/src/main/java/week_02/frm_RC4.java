package week_02;

import javax.swing.SwingUtilities;
public final class frm_RC4 extends CipherFrame {
    public frm_RC4(){super("RC4 Cipher","RC4","Key","1-256 byte; xuất HEX","rc4_ciphertext.txt",(t,k,x)->RC4Cipher.encrypt(t,k),(t,k,x)->RC4Cipher.decrypt(t,k),null,null);}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_RC4().setVisible(true));}
}