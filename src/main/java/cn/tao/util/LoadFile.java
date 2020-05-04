package cn.tao.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoadFile {
    public static void main(String[] args) {
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
            URL url =new URL("http://img.dwhs.cn/BOSS/courseware/a37b2f38-82e4-4f04-b8e3-459b5bf1c2da-基础信息I1-U1-L10L11植物综合训练多肉植物.pptx");
            URLConnection urlConnection = url.openConnection();
            inputStream = urlConnection.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            String filePath ="E:\\dawei\\demo\\src\\main\\resources";
            String fileName = "基础信息I1-U1-L10L11植物综合训练多肉植物.ppt";
            if (!filePath.endsWith("/")) {
                filePath += "/";
            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            fileOut = new FileOutputStream(filePath + fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
