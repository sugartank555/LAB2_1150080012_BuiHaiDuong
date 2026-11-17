package week_02;

import javax.swing.SwingUtilities;
public final class frm_TripleDES extends CipherFrame {
    public frm_TripleDES(){super("TripleDES Cipher","TripleDES","123456789012345678901234","24 byte ASCII","tripledes_ciphertext.txt",(t,k,x)->TripleDESCipher.encrypt(t,k),(t,k,x)->TripleDESCipher.decrypt(t,k),null,null);}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_TripleDES().setVisible(true));}
}