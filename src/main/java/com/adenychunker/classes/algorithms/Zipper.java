package lzw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class Zipper {
    private String path;
    public Zipper(String path){
        this.path=path;
    }
    
    public void compress(String...files) throws FileNotFoundException, IOException{
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
        ZipInputStream zin=new ZipInputStream(new FileInputStream(path));
        ZipEntry entry=null;
        while( (entry=zin.getNextEntry())!=null){
            int len;
            byte[] data=new byte[1024];
            FileOutputStream fos=new FileOutputStream(dir+"\\"+entry.getName());
            while((len=zin.read(data)) !=-1){
                fos.write(data, 0, len);
            }
            fos.close();
            zin.closeEntry();
        }
        zin.close();
    }
}
