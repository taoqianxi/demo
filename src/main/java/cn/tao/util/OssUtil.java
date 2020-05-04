package cn.tao.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OssUtil {

    public static void main(String[] args) {
        downLoadOss();
    }
   public static void downLoadOss(){
//       domain = "http://img.dwhs.cn";
//       endpoint = "http://oss-cn-zhangjiakou.aliyuncs.com";
    // Endpoint以杭州为例，其它Region请按实际情况填写。
       String endpoint = "http://oss-cn-zhangjiakou.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
       String accessKeyId = "LTAITB73t0NKMV88";
       String accessKeySecret = "9C3RJ5IBIJu4Y3yl4PhipJIGv4qC1J";
       String bucketName = "dwart-public";
       OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
       OSSObject object = ossClient.getObject(bucketName,"BOSS/courseware/673465f0-9a44-4e13-8d88-c6445126a274-PPTZC-U2-L6.pptx");
       InputStream content = object.getObjectContent();
       BufferedInputStream bis = new BufferedInputStream(content);
       FileOutputStream fileOut = null;
       BufferedOutputStream bos = null;
       if (content != null) {
           try {
               fileOut = new FileOutputStream("C:\\Users\\Admin\\Desktop\\test.pptx");
                bos = new BufferedOutputStream(fileOut);
               // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
               byte[] buf = new byte[4096];
               int length = bis.read(buf);
               //保存文件
               while(length != -1)
               {
                   bos.write(buf, 0, length);
                   length = bis.read(buf);
               }

           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               try {
                   fileOut.close();
                   bis.close();
                   content.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               // 关闭OSSClient。
               ossClient.shutdown();
           }
       }


   }

   public static void upLoadingOss() {
       // Endpoint以杭州为例，其它Region请按实际情况填写。
       String endpoint = "http://oss-cn-zhangjiakou.aliyuncs.com";
       // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
       String accessKeyId = "LTAITB73t0NKMV88";
       String accessKeySecret = "9C3RJ5IBIJu4Y3yl4PhipJIGv4qC1J";
       String bucketName = "dwart-public";
       OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
   }

}
