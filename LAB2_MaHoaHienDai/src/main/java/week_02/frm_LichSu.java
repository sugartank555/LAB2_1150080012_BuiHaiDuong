package week_02;

import java.awt.*;
import javax.swing.*;
public final class frm_LichSu extends JFrame {
    private final JTable table=new JTable(HistoryDao.emptyModel());private final JLabel status=new JLabel("Đang tải...");
    public frm_LichSu(){super("Lịch sử thao tác SQL Server");setDefaultCloseOperation(DISPOSE_ON_CLOSE);setSize(960,520);setLocationRelativeTo(null);
        JPanel p=new JPanel(new BorderLayout(8,8));p.setBorder(BorderFactory.createEmptyBorder(12,16,12,16));JLabel title=new JLabel("LỊCH SỬ - DBO.CRYPTO_HISTORY");title.setFont(new Font("Segoe UI",Font.BOLD,19));p.add(title,BorderLayout.NORTH);p.add(new JScrollPane(table),BorderLayout.CENTER);
        JPanel footer=new JPanel(new BorderLayout());JButton refresh=new JButton("Tải lại");footer.add(refresh,BorderLayout.WEST);footer.add(status,BorderLayout.CENTER);p.add(footer,BorderLayout.SOUTH);setContentPane(p);refresh.addActionListener(e->loadData());loadData();
    }
    private void loadData(){try{table.setModel(HistoryDao.loadLatest(50));status.setText("Đã tải "+table.getRowCount()+" dòng.");}catch(Exception e){status.setText("Chưa tải được lịch sử: "+e.getMessage());}}
    public static void main(String[] a){SwingUtilities.invokeLater(()->new frm_LichSu().setVisible(true));}
}