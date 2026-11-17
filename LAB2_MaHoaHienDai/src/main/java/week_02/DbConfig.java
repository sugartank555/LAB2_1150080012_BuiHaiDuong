package week_02;

import java.sql.*;
import java.util.Arrays;
/** Cấu hình JDBC giữ trong RAM, có thể thay cổng theo instance SUGAR. */
public final class DbConfig {
    private static String server, database, user; private static int port; private static char[] password;
    private static boolean verified;
    private DbConfig() {}
    public static synchronized void set(String s,int p,String db,String u,char[] pw){
        if(s.isBlank()||db.isBlank()||u.isBlank()||p<1||p>65535)throw new IllegalArgumentException("Thiếu server/database/user hoặc cổng không hợp lệ.");
        clear();server=s;port=p;database=db;user=u;password=pw.clone();
    }
    public static synchronized void clear(){if(password!=null)Arrays.fill(password,'\0');server=null;password=null;verified=false;}
    public static synchronized boolean isReady(){return verified;}
    public static synchronized Connection open()throws SQLException{
        if(server==null)throw new SQLException("Chưa cấu hình SQL Server.");
        String url="jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";encrypt=true;trustServerCertificate=true;loginTimeout=5";
        return DriverManager.getConnection(url,user,new String(password));
    }
    public static String test()throws SQLException{
        try(Connection con=open()){String result="Kết nối thành công: "+con.getMetaData().getDatabaseProductName()+" "+con.getCatalog();synchronized(DbConfig.class){verified=true;}return result;}
    }
}