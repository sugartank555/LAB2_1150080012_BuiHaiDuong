package week_02;

import java.awt.*;
import javax.swing.*;
public final class frm_Menu extends JFrame {
    public frm_Menu(){super("LAB 2 - Mã hóa đối xứng hiện đại");setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(760,420);setLocationRelativeTo(null);
        JPanel p=new JPanel(new BorderLayout(15,15));p.setBorder(BorderFactory.createEmptyBorder(18,20,18,20));
        JLabel title=new JLabel("LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI");title.setFont(new Font("Segoe UI",Font.BOLD,21));p.add(title,BorderLayout.NORTH);
        JPanel grid=new JPanel(new GridLayout(4,2,12,12));
        add(grid,"2.1 DES",()->new frm_DES());add(grid,"2.2 TripleDES",()->new frm_TripleDES());
        add(grid,"2.3 AES + Login",()->new frm_AES());add(grid,"2.4 RC4",()->new frm_RC4());
        add(grid,"2.5 Twofish",()->new frm_Twofish());add(grid,"2.6 Nâng cao - AES-CTR",()->new frm_AES_CTR());
        add(grid,"Kết nối SQL Server",()->new frm_KetNoiSQL());add(grid,"Xem lịch sử",()->new frm_LichSu());
        p.add(grid,BorderLayout.CENTER);p.add(new JLabel("Bài nâng cao 02: AES-CBC bằng Python trong thư mục LAB2_Python (chạy riêng)."),BorderLayout.SOUTH);setContentPane(p);
    }
    @FunctionalInterface private interface OpenForm {JFrame open();}
    private void add(JPanel grid,String title,OpenForm action){JButton b=new JButton(title);b.addActionListener(e->action.open().setVisible(true));grid.add(b);}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_Menu().setVisible(true));}
}