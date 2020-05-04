package cn.tao.util.test;

import cn.tao.util.ThreadPoolUtil;
import com.spire.presentation.*;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class PPTtoPNG {
    public static void test(){
        //创建Presentation对象
        Presentation ppt = new Presentation();
        List<String > list  = new ArrayList<String>();
        list.add("练十字圆锥的延展空间");
//        list.add("372");
        for (String s : list) {
            try {
//                URL url = new URL("http://img.dwhs.cn/BOSS/courseware/2d189e9f-8e3f-446c-9ad5-323b8f51a5d4-授课PPTZG-U1-L1枯萎的莲蓬.pptx");
//                URLConnection urlConnection = url.openConnection();
//                ppt.loadFromStream(urlConnection.getInputStream(),null);
                ppt.loadFromFile("C:\\Users\\Admin\\Desktop\\PPT\\" + s + ".pptx");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //遍历幻灯片
            for (int i = 0; i < ppt.getSlides().getCount(); i++) {
                System.err.println(Thread.currentThread().getName()+"目前在处理===>"+ s +"第："+(i + 1));
                ISlide slide = ppt.getSlides().get(i);
                IVideo video = null;
                IAudio iAudio = null;
                for(int j = 0; j< slide.getShapes().getCount(); j++) {
//                    System.err.println("目前在处理===>内部参数"+ s +"第："+(j + 1));
                    IShape shape = slide.getShapes().get(j);
                    if ((shape instanceof IVideo)) {
                        //保存视频
                        video = (IVideo) shape;
                        System.err.println(video.getTop());
                        System.err.println(video.getLeft());
                        try {
                            video.getEmbeddedVideoData().saveToFile("E:\\dawei\\demo\\src\\main\\resources\\"+s+"\\video/"+j+".mp4");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if ((shape instanceof IAudio)) {
                        //保存音频
                        iAudio = (IAudio) shape;
                        IAudioData data = iAudio.getData();
                        System.err.println(iAudio.getTop());
                        System.err.println(iAudio.getLeft());
                        try {
                            data.saveToFile("E:\\dawei\\demo\\src\\main\\resources\\"+s+"\\mp3/"+j+".mp3");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                //将幻灯片保存为BufferedImage对象
                BufferedImage image = ppt.getSlides().get(i).saveAsImage();
                //将BufferedImage保存为PNG格式文件
                String fileName =  String.format("%sImage.png", i);
                image.flush();
                try {
                    ImageIO.write(image, "PNG", new File("E:\\dawei\\demo\\src\\main\\resources\\" + s + "/" + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.err.println("success!");
            ppt.dispose();
        }
    }



}
