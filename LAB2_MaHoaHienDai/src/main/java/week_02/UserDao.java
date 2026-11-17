package week_02;

import java.sql.*;
import java.security.MessageDigest;
import java.util.Arrays;
public final class UserDao {
    private UserDao() {}
    public static void register(String user,char[] password)throws SQLException{
        if(!user.matches("[A-Za-z0-9_.]{3,40}"))throw new IllegalArgumentException("Username phải dài 3-40 ký tự [A-Za-z0-9_.]");
        if(password.length<8)throw new IllegalArgumentException("Mật khẩu ít nhất 8 ký tự.");
        byte[] salt=CipherUtil.randomBytes(16),hash=CipherUtil.pbkdf2(password,salt,120000,256);
        try(Connection c=DbConfig.open();PreparedStatement ps=c.prepareStatement("INSERT INTO dbo.app_users(username,salt,password_hash) VALUES (?,?,?)")){
            ps.setString(1,user);ps.setBytes(2,salt);ps.setBytes(3,hash);ps.executeUpdate();
        }catch(SQLException e){if(e.getErrorCode()==2627||e.getErrorCode()==2601)throw new IllegalArgumentException("Username đã tồn tại.");throw e;}
        finally {Arrays.fill(hash,(byte)0);}
    }
    public static boolean login(String user,char[] password)throws SQLException{
        try(Connection c=DbConfig.open();PreparedStatement ps=c.prepareStatement("SELECT salt,password_hash FROM dbo.app_users WHERE username=?")){
            ps.setString(1,user);try(ResultSet rs=ps.executeQuery()){
                if(!rs.next())return false;
                byte[] actual=CipherUtil.pbkdf2(password,rs.getBytes(1),120000,256);
                boolean ok=MessageDigest.isEqual(actual,rs.getBytes(2));Arrays.fill(actual,(byte)0);return ok;
            }
        }
    }
    public static String newTicket(){return CipherUtil.hex(CipherUtil.randomBytes(16));}
}