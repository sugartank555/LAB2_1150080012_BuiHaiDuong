package week_02;

import java.awt.*;
import javax.swing.*;
/** Form cơ sở cho DES, 3DES, RC4, Twofish, AES-CTR. */
public class CipherFrame extends JFrame {
    @FunctionalInterface public interface Crypt {String apply(String text,String key,String extra)throws Exception;}
    protected final JTextArea plaintext=new JTextArea(4,50),ciphertext=new JTextArea(4,50);
    protected final JTextField keyField=new JTextField(), extraField=new JTextField();
    protected final JLabel status=new JLabel("Sẵn sàng.");
    private final String algorithm,filename;
    private final Crypt encryptor,decryptor;
    private final boolean extra;
    protected final JPanel additionalButtons=new JPanel(new FlowLayout(FlowLayout.LEFT));
    protected CipherFrame(String title,String algorithm,String initialKey,String hint,String filename,Crypt encryptor,Crypt decryptor,String extraLabel,String initialExtra){
        super(title);this.algorithm=algorithm;this.filename=filename;this.encryptor=encryptor;this.decryptor=decryptor;extra=extraLabel!=null;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);setSize(780,560);setLocationRelativeTo(null);
        JPanel main=new JPanel(new BorderLayout(8,8));main.setBorder(BorderFactory.createEmptyBorder(15,18,15,18));
        JLabel heading=new JLabel("MẬT MÃ "+algorithm.toUpperCase());heading.setFont(new Font("Segoe UI",Font.BOLD,21));main.add(heading,BorderLayout.NORTH);
        JPanel fields=new JPanel();fields.setLayout(new BoxLayout(fields,BoxLayout.Y_AXIS));
        plaintext.setLineWrap(true);plaintext.setWrapStyleWord(true);ciphertext.setLineWrap(true);ciphertext.setWrapStyleWord(true);
        fields.add(new JLabel("Plaintext:"));fields.add(new JScrollPane(plaintext));
        JPanel k=new JPanel(new BorderLayout(8,0));k.add(new JLabel("Key:"),BorderLayout.WEST);keyField.setText(initialKey);k.add(keyField,BorderLayout.CENTER);k.add(new JLabel(hint),BorderLayout.EAST);fields.add(k);
        if(extra){JPanel e=new JPanel(new BorderLayout(8,0));e.add(new JLabel(extraLabel),BorderLayout.WEST);extraField.setText(initialExtra);e.add(extraField,BorderLayout.CENTER);fields.add(e);}
        fields.add(new JLabel("Ciphertext:"));fields.add(new JScrollPane(ciphertext));main.add(fields,BorderLayout.CENTER);
        JPanel footer=new JPanel(new BorderLayout());JPanel buttons=new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton enc=new JButton("Encrypt"),dec=new JButton("Decrypt"),save=new JButton("Save File"),open=new JButton("Open File");
        for(JButton b:new JButton[]{enc,dec,save,open})buttons.add(b);
        footer.add(additionalButtons,BorderLayout.NORTH);footer.add(buttons,BorderLayout.CENTER);footer.add(status,BorderLayout.SOUTH);main.add(footer,BorderLayout.SOUTH);setContentPane(main);
        enc.addActionListener(e->process(true));dec.addActionListener(e->process(false));
        save.addActionListener(e->{try{String content=extra?fileContent():ciphertext.getText();String path=FileTextIO.saveString(this,content,filename);if(path!=null)status.setText("Đã lưu: "+path);}catch(Exception ex){error(ex);}});
        open.addActionListener(e->{try{String content=FileTextIO.openString(this);if(content!=null){if(extra)loadContent(content);else ciphertext.setText(content.trim());status.setText("Đã mở tệp.");}}catch(Exception ex){error(ex);}});
    }
    protected String fileContent(){return extra?extraField.getText().trim()+"\n"+ciphertext.getText().trim():ciphertext.getText();}
    protected void loadContent(String content){String[] v=content.split("\\R",2);if(v.length!=2)throw new IllegalArgumentException("Thiếu dòng IV/Nonce hoặc bản mã.");extraField.setText(v[0].trim());ciphertext.setText(v[1].trim());}
    private void process(boolean encryption){String text=encryption?plaintext.getText():ciphertext.getText();if(text.isEmpty()){JOptionPane.showMessageDialog(this,"Chưa nhập dữ liệu.");return;}
        try{String out=(encryption?encryptor:decryptor).apply(text,keyField.getText(),extraField.getText());if(encryption)ciphertext.setText(out);else plaintext.setText(out);
            status.setText((encryption?"Đã mã hóa. ":"Đã giải mã. ")+HistoryDao.tryInsert(algorithm,encryption?"ENCRYPT":"DECRYPT",null,text.length(),out.length()));
        }catch(Exception ex){error(ex);}}
    protected void error(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Thao tác thất bại",JOptionPane.ERROR_MESSAGE);}
}