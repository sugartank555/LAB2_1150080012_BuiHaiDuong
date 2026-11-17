package week_02;

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;
public final class frm_AES extends JFrame {
    private String currentUser=null;private String ticket=null;
    private final JTextField username=new JTextField("sinhvien01"),ticketField=new JTextField(),key=new JTextField();
    private final JPasswordField password=new JPasswordField();
    private final JTextArea plaintext=new JTextArea(4,50),ciphertext=new JTextArea(4,50);
    private final JLabel status=new JLabel("Sẵn sàng. Kết nối SQL và đăng nhập trước khi mã hóa.");
    public frm_AES(){super("AES-GCM - Register / Login");setDefaultCloseOperation(DISPOSE_ON_CLOSE);setSize(850,720);setLocationRelativeTo(null);
        JPanel main=new JPanel(new BorderLayout(9,9));main.setBorder(BorderFactory.createEmptyBorder(14,18,14,18));
        JLabel title=new JLabel("AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)");title.setFont(new Font("Segoe UI",Font.BOLD,19));main.add(title,BorderLayout.NORTH);
        JPanel fields=new JPanel();fields.setLayout(new BoxLayout(fields,BoxLayout.Y_AXIS));
        JPanel userRow=new JPanel(new GridLayout(2,2,7,7));userRow.add(new JLabel("Username:"));userRow.add(username);userRow.add(new JLabel("Password (>=8 ký tự):"));userRow.add(password);fields.add(userRow);
        JPanel authButtons=new JPanel(new FlowLayout(FlowLayout.LEFT));JButton connect=new JButton("Kết nối SQL Server"),register=new JButton("Register"),login=new JButton("Login");authButtons.add(connect);authButtons.add(register);authButtons.add(login);fields.add(authButtons);
        JPanel trow=new JPanel(new BorderLayout(7,0));trow.add(new JLabel("Ticket:"),BorderLayout.WEST);ticketField.setEditable(false);trow.add(ticketField,BorderLayout.CENTER);fields.add(trow);
        plaintext.setLineWrap(true);ciphertext.setLineWrap(true);fields.add(new JLabel("Plaintext:"));fields.add(new JScrollPane(plaintext));
        JPanel keyRow=new JPanel(new BorderLayout(7,0));keyRow.add(new JLabel("Key AES (mật khẩu sinh khóa):"),BorderLayout.WEST);keyRow.add(key,BorderLayout.CENTER);fields.add(keyRow);
        fields.add(new JLabel("Ciphertext (Base64):"));fields.add(new JScrollPane(ciphertext));main.add(fields,BorderLayout.CENTER);
        JPanel footer=new JPanel(new BorderLayout());JPanel actions=new JPanel();JButton enc=new JButton("Encrypt"),dec=new JButton("Decrypt"),save=new JButton("Save File"),open=new JButton("Open File");for(JButton b:new JButton[]{enc,dec,save,open})actions.add(b);footer.add(actions,BorderLayout.NORTH);footer.add(status,BorderLayout.SOUTH);main.add(footer,BorderLayout.SOUTH);setContentPane(main);
        connect.addActionListener(e->new frm_KetNoiSQL().setVisible(true));
        register.addActionListener(e->{char[] pwd=password.getPassword();try{UserDao.register(username.getText().trim(),pwd);HistoryDao.tryInsert("AES","REGISTER",username.getText().trim(),0,0);status.setText("Register thành công; hãy Login.");}
            catch(Exception ex){error(ex);}finally{Arrays.fill(pwd,'\0');}});
        login.addActionListener(e->{char[] pwd=password.getPassword();try{String u=username.getText().trim();if(UserDao.login(u,pwd)){currentUser=u;ticket=UserDao.newTicket();ticketField.setText(ticket);HistoryDao.tryInsert("AES","LOGIN",u,0,0);status.setText("Login thành công: "+u);}else{currentUser=null;ticket=null;ticketField.setText("");JOptionPane.showMessageDialog(this,"Sai username hoặc mật khẩu.");}}
            catch(Exception ex){currentUser=null;ticket=null;ticketField.setText("");error(ex);}finally{Arrays.fill(pwd,'\0');}});
        enc.addActionListener(e->crypt(true));dec.addActionListener(e->crypt(false));
        save.addActionListener(e->{String path=FileTextIO.saveText(this,ciphertext,"aes_ciphertext.txt");if(path!=null)status.setText("Đã lưu: "+path);});
        open.addActionListener(e->{String path=FileTextIO.openText(this,ciphertext);if(path!=null)status.setText("Đã mở tệp.");});
    }
    private void crypt(boolean encrypt){if(currentUser==null||ticket==null){JOptionPane.showMessageDialog(this,"Hãy Login trước khi dùng AES.");return;}
        String src=encrypt?plaintext.getText():ciphertext.getText();if(src.isEmpty()){JOptionPane.showMessageDialog(this,"Hãy nhập dữ liệu.");return;}
        try{String out=encrypt?AESCipher.encrypt(src,key.getText()):AESCipher.decrypt(src,key.getText());if(encrypt)ciphertext.setText(out);else plaintext.setText(out);
            status.setText("Đã "+(encrypt?"mã hóa":"giải mã")+". "+HistoryDao.tryInsert("AES",encrypt?"ENCRYPT":"DECRYPT",currentUser,src.length(),out.length()));}
        catch(Exception ex){error(ex);}
    }
    private void error(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage(),"Thao tác thất bại",JOptionPane.ERROR_MESSAGE);}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_AES().setVisible(true));}
}