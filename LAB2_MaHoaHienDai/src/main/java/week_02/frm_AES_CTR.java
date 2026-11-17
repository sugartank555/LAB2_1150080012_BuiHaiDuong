package week_02;

import javax.swing.*;
public final class frm_AES_CTR extends CipherFrame {
    public frm_AES_CTR(){super("AES-CTR Cipher","AES-CTR",AESCTRCipher.newKey(),"32 byte Base64","aes_ctr_ciphertext.txt",(t,k,n)->AESCTRCipher.encrypt(t,k,n),(t,k,n)->AESCTRCipher.decrypt(t,k,n),"Nonce (Base64):",AESCTRCipher.newNonce());
        JButton genKey=new JButton("Generate Key"),genNonce=new JButton("Generate Nonce");
        genKey.addActionListener(e->{keyField.setText(AESCTRCipher.newKey());extraField.setText(AESCTRCipher.newNonce());});
        genNonce.addActionListener(e->extraField.setText(AESCTRCipher.newNonce()));additionalButtons.add(genKey);additionalButtons.add(genNonce);
    }
    @Override protected String fileContent(){return AESCTRCipher.toFile(extraField.getText(),ciphertext.getText());}
    @Override protected void loadContent(String content){String[] parts=AESCTRCipher.fromFile(content);extraField.setText(parts[0]);ciphertext.setText(parts[1]);}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_AES_CTR().setVisible(true));}
}