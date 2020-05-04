package cn.tao.util;

import cn.tao.entity.PPT;
import com.alibaba.fastjson.JSON;
import com.spire.presentation.*;
import com.spire.presentation.collections.ParagraphCollection;
import com.spire.presentation.drawing.IImageData;
import org.apache.ibatis.annotations.Param;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PPTtoPNG {

    public List<PPT> ppt(){
        List<PPT> listPPt = new ArrayList<>();
        //创建Presentation对象
        Presentation ppt = new Presentation();
        List<String > list  = new ArrayList<String>();
            list.add("PPT ZC-U5-L18宠物小精灵");
            for (String s : list) {
            //加载示例文档
            try {
                ppt.loadFromFile("C:\\Users\\Admin\\Desktop\\PPT\\"+s+".pptx");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //遍历幻灯片
            for (int i = 0; i < ppt.getSlides().getCount(); i++) {
                ISlide slide = ppt.getSlides().get(i);
                IVideo video = null;
                IAudio iAudio = null;
                //将幻灯片保存为BufferedImage对象
                BufferedImage image = ppt.getSlides().get(i).saveAsImage();
                //将BufferedImage保存为PNG格式文件
                String fileName =  String.format("%sImage.png", i);
                try {
                    ImageIO.write(image, "PNG", new File("E:\\dawei\\demo\\src\\main\\resources\\" + s + "/" + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PPT ppt2 = new PPT();
                ppt2.setWidth(image.getWidth());
                ppt2.setHeight(image.getHeight());
                ppt2.setUrl("http://img.dwhs.cn/test/1Image.png");
                ppt2.setToBackground(true);
                ppt2.setIndex(i + 1);
                ppt2.setTheHierarchy(0);
                listPPt.add(ppt2);
                for(int j = 0; j< slide.getShapes().getCount(); j++) {
                    PPT ppt1 = null;
                    IShape shape = slide.getShapes().get(j);
                    if ((shape instanceof IVideo)) {
                        ppt1 = new PPT();
                        //保存视频
                        video = (IVideo) shape;
                        System.err.println(video.getTop());
                        System.err.println(video.getLeft());

                        ppt1.setWidth(video.getWidth());
                        ppt1.setHeight(video.getHeight());
                        ppt1.setTop(video.getTop());
                        ppt1.setLeft(video.getLeft());
                        ppt1.setIndex(i + 1);
                        ppt1.setTheHierarchy(j + 1);
                        ppt1.setType("video");
                        ppt1.setUrl("http://img.dwhs.cn/test/5.mp4");
                        try {
                            video.getEmbeddedVideoData().saveToFile("E:\\dawei\\demo\\src\\main\\resources\\"+s+"\\video/"+j+".mp4");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listPPt.add(ppt1);
                    }
                    if ((shape instanceof IAudio)) {
                        ppt1 = new PPT();
                        //保存音频
                        iAudio = (IAudio) shape;
                        IAudioData data = iAudio.getData();
                        System.err.println(iAudio.getTop());
                        System.err.println(iAudio.getWidth());
                        ppt1.setWidth(iAudio.getWidth());
                        ppt1.setHeight(iAudio.getHeight());
                        ppt1.setTop(iAudio.getTop());
                        ppt1.setLeft(iAudio.getLeft());
                        ppt1.setIndex(i + 1);
                        ppt1.setTheHierarchy(j + 1);
                        ppt1.setType("audio");
                        if (j + 1 == 3) {
                            ppt1.setUrl("http://img.dwhs.cn/test/%E5%B0%81%E8%8C%97%E5%9B%A7%E8%8F%8C%20-%20%E5%A6%82%E6%88%8F.mp3");
                        } else {
                            ppt1.setUrl("http://img.dwhs.cn/test/%E7%8E%8B%E6%A2%93%E9%92%B0%E9%99%88%E6%96%87%E8%BE%89%E5%A6%82%E6%9E%9C%E7%9A%84%E4%BA%8B.mp3");
                        }
                        try {
                            data.saveToFile("E:\\dawei\\demo\\src\\main\\resources\\"+s+"\\mp3/"+j+".mp3");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listPPt.add(ppt1);
                    }


//                    if(shape instanceof SlidePicture)
//                    {
//                        if (x == 1) {
//                            x = 0;
//                            continue;
//                        }
//                        SlidePicture pic = (SlidePicture) shape;
//                        try {
//                            ppt1 = new PPT();
//                            ppt1.setWidth(pic.getWidth());
//                            ppt1.setHeight(pic.getHeight());
//                            ppt1.setTop(pic.getTop());
//                            ppt1.setLeft(pic.getLeft());
//                            ppt1.setIndex(i + 1);
//                            ppt1.setTheHierarchy(j + 1);
//                            ppt1.setType("img");
//                            ppt1.setUrl("http://img.dwhs.cn/test/0.png");
//                            BufferedImage image1 = pic.getPictureFill().getPicture().getEmbedImage().getImage();
//                            ImageIO.write(image1, "PNG",  new File("E:\\dawei\\demo\\src\\main\\resources\\" + s + "/" + j + ".png"));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
////                        listPPt.add(ppt1);
//                    }
//
//                    if (shape instanceof IAutoShape) {
//                        StringBuilder buffer = new StringBuilder();
//                        IAutoShape shape1 = (IAutoShape) shape;
//                        ITextFrameProperties textFrame = shape1.getTextFrame();
//                        float fontHeight = textFrame.getParagraphs().get(0).getTextRanges().get(0).getFontHeight();
//
//                        for (Object tp : ((IAutoShape) shape).getTextFrame().getParagraphs()) {
//                            ParagraphEx ex = (ParagraphEx) tp;
//                            buffer.append(ex.getText());
//                        }
//                        ppt1 = new PPT();
//                        ppt1.setTextSize(fontHeight);
//                        ppt1.setWidth(shape1.getWidth());
//                        ppt1.setHeight(shape1.getHeight());
//                        ppt1.setTop(shape1.getTop());
//                        ppt1.setLeft(shape1.getLeft());
//                        ppt1.setIndex(i + 1);
//                        ppt1.setTheHierarchy(j + 1);
//                        ppt1.setType("text");
//                        ppt1.setText(buffer.toString());
//                        System.err.println(buffer.toString());
////                        listPPt.add(ppt1);
//                    }
                }

            }

            System.err.println("success!");
            ppt.dispose();
        }
            return listPPt;
    }
}
