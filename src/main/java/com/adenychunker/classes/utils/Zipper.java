package com.adenychunker.classes.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;


public class Zipper {
    private String path;
    public Zipper() {
//        Default Constructor
    }
    public Zipper(String path){
        this.path=path;
    }
    
    public void compress(String path, String...files) throws FileNotFoundException, IOException{
        ZipOutputStream zos=new ZipOutputStream(new FileOutputStream(path));
        for(String f: files){
            File file=new File(f);
            ZipEntry entry =new ZipEntry(file.getName());
            zos.putNextEntry(entry);
            FileInputStream fis=new FileInputStream(file);
            
            int len;
            byte[] data =new byte[1024];
            while( (len=fis.read(data)) !=-1){
                zos.write(data, 0, len);
            }
            fis.close();
            zos.closeEntry();
        }
        
        zos.close();
    }
    
    public void extract(String dir) throws FileNotFoundException, IOException{
        ZipInputStream zin=new ZipInputStream(new FileInputStream(this.path));
        ZipEntry entry=null;
        while( (entry=zin.getNextEntry())!=null){
            int len;
            byte[] data=new byte[1024];
            try (FileOutputStream fos = new FileOutputStream(dir+ File.separator + entry.getName())) {
                while((len=zin.read(data)) !=-1){
                    fos.write(data, 0, len);
                }
            }
            catch(FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error while unzipping the archive. Please specify the folder for destination to zip the files", "Error", JOptionPane.ERROR_MESSAGE);
            }
            zin.closeEntry();
        }
        zin.close();
    }
}
