package week_02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
public final class FileTextIO {
    private FileTextIO() {}
    public static String openString(JFrame parent) {
        JFileChooser fc=new JFileChooser(); if(fc.showOpenDialog(parent)!=JFileChooser.APPROVE_OPTION)return null;
        try{return Files.readString(fc.getSelectedFile().toPath(),StandardCharsets.UTF_8);}catch(Exception e){JOptionPane.showMessageDialog(parent,e.getMessage(),"Lỗi đọc tệp",JOptionPane.ERROR_MESSAGE);return null;}
    }
    public static String saveString(JFrame parent,String content,String defaultName){
        JFileChooser fc=new JFileChooser();fc.setSelectedFile(new java.io.File(defaultName));
        if(fc.showSaveDialog(parent)!=JFileChooser.APPROVE_OPTION)return null;
        Path path=fc.getSelectedFile().toPath();
        if(!path.toString().toLowerCase().endsWith(".txt"))path=Path.of(path.toString()+".txt");
        try{if(Files.exists(path)&&JOptionPane.showConfirmDialog(parent,"Ghi đè tệp?","Xác nhận",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return null;
            Files.writeString(path,content,StandardCharsets.UTF_8);return path.toAbsolutePath().toString();}
        catch(Exception e){JOptionPane.showMessageDialog(parent,e.getMessage(),"Lỗi lưu tệp",JOptionPane.ERROR_MESSAGE);return null;}
    }
    public static String openText(JFrame parent,JTextArea target){String data=openString(parent);if(data!=null)target.setText(data);return data==null?null:"Đã mở tệp";}
    public static String saveText(JFrame parent,JTextArea source,String defaultName){if(source.getText().isEmpty()){JOptionPane.showMessageDialog(parent,"Chưa có nội dung để lưu.");return null;}return saveString(parent,source.getText(),defaultName);}
}