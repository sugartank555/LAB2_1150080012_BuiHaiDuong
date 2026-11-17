package week_02;

import javax.swing.*;
public final class frm_Twofish extends CipherFrame {
    public frm_Twofish(){super("Twofish Cipher","Twofish","Twofish@Lab2","Khóa bất kỳ","twofish_ciphertext.txt",(t,k,iv)->TwofishCipher.encrypt(t,k,iv),(t,k,iv)->TwofishCipher.decrypt(t,k,iv),"IV (HEX):",TwofishCipher.newIv());
        JButton gen=new JButton("Generate IV");gen.addActionListener(e->extraField.setText(TwofishCipher.newIv()));additionalButtons.add(gen);
    }
    @Override protected String fileContent(){return TwofishCipher.toFile(extraField.getText(),ciphertext.getText());}
    @Override protected void loadContent(String content){String[] parts=TwofishCipher.fromFile(content);extraField.setText(parts[0]);ciphertext.setText(parts[1]);}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_Twofish().setVisible(true));}
}