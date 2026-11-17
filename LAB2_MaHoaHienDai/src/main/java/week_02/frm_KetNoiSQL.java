package week_02;

import java.awt.*;
import javax.swing.*;
public final class frm_KetNoiSQL extends JFrame {
    public frm_KetNoiSQL(){super("Kết nối Microsoft SQL Server");setDefaultCloseOperation(DISPOSE_ON_CLOSE);setSize(610,380);setLocationRelativeTo(null);
        JPanel main=new JPanel(new BorderLayout(10,10));main.setBorder(BorderFactory.createEmptyBorder(15,18,15,18));
        JLabel title=new JLabel("KẾT NỐI MICROSOFT SQL SERVER");title.setFont(new Font("Segoe UI",Font.BOLD,19));main.add(title,BorderLayout.NORTH);
        JPanel grid=new JPanel(new GridLayout(5,2,7,7));
        JTextField server=new JTextField("127.0.0.1"),port=new JTextField("14333"),db=new JTextField("LAB2_MaHoaHienDai"),user=new JTextField("lab2_user");JPasswordField pw=new JPasswordField();
        grid.add(new JLabel("Server (instance SUGAR chạy TCP):"));grid.add(server);grid.add(new JLabel("Port:"));grid.add(port);grid.add(new JLabel("Database:"));grid.add(db);grid.add(new JLabel("SQL User:"));grid.add(user);grid.add(new JLabel("Password:"));grid.add(pw);main.add(grid,BorderLayout.CENTER);
        JPanel footer=new JPanel(new BorderLayout());JPanel buttons=new JPanel();JButton test=new JButton("Test Connection"),history=new JButton("Xem lịch sử");buttons.add(test);buttons.add(history);footer.add(buttons,BorderLayout.NORTH);JLabel status=new JLabel("Chưa kết nối.");footer.add(status,BorderLayout.SOUTH);main.add(footer,BorderLayout.SOUTH);setContentPane(main);
        test.addActionListener(e->{char[] password=pw.getPassword();try{DbConfig.set(server.getText().trim(),Integer.parseInt(port.getText().trim()),db.getText().trim(),user.getText().trim(),password);status.setText(DbConfig.test());}
            catch(Exception ex){DbConfig.clear();status.setText("Kết nối thất bại.");JOptionPane.showMessageDialog(this,ex.getMessage(),"SQL Server",JOptionPane.ERROR_MESSAGE);}finally{java.util.Arrays.fill(password,'\0');}});
        history.addActionListener(e->new frm_LichSu().setVisible(true));
    }
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_KetNoiSQL().setVisible(true));}
}