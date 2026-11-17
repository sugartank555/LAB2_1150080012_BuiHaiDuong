package week_02;

import javax.swing.SwingUtilities;
public final class frm_DES extends CipherFrame {
    public frm_DES(){super("DES Cipher","DES","12345678","8 byte ASCII","des_ciphertext.txt",(t,k,x)->DESCipher.encrypt(t,k),(t,k,x)->DESCipher.decrypt(t,k),null,null);}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_DES().setVisible(true));}
}