package com.arm.encry.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaolangjing
 * @since 2021-3-24 11:21
 */
public class FileUtil {


    public static String readFile(String filePath) throws IOException {
        File file = new File( filePath );
        StringBuilder sb = new StringBuilder();
        if (file.exists() && file.isFile()) {
            String line = "";
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader( new FileReader( file ) );
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append( line );
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bufferedReader.close();
            }
        }
        return sb.toString();
    }

    /**
     * 获取特定目录下的特定后最名的文件
     *
     * @param directory
     * @param fileExt
     * @return
     */
    public static List<File> getFile(String directory, String fileExt) {
        return getFile( new File( directory ), fileExt );
    }

    public static List<File> getFile(File file, String fileExt) {
        List<File> files = new ArrayList<>();
        if (file.exists() && file.isDirectory()) {
            File[] fileArray = file.listFiles();
            for (File file1 : fileArray) {
                if (file1.isDirectory()) {
                    files.addAll( getFile( file1.getPath(), fileExt ) );
                } else if (file1.exists() && file1.getName().contains( fileExt )) {
                    files.add( file1 );
                }
            }
        } else if (file.exists() && file.getName().contains( fileExt )) {
            files.add( file );
        }
        return files;
    }

}
