package cn.tao.util;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.util.StringUtils;
import sun.net.www.http.HttpClient;

import javax.imageio.*;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.net.URL;

public class DrawingUtils {


    private static float jPEGcompression = 1f;// 图片清晰比率

    /**
     * @Description : 将二维码图片和文字生成到一张图片上
     * @Param : originalImg 原图
     * @Param : qrCodeImg 二维码地址
     * @Param : shareDesc 图片文字
     * @return : java.lang.String
     * @Author : houzhenghai
     * @Date : 2018/8/15
     */
    public static ByteArrayOutputStream generateImg(String originalImg, String qrCodeImg, String shareDesc) throws Exception {

        // 加载原图图片
        BufferedImage imageLocal = ImageIO.read(new URL(originalImg));
        // 加载用户的二维码
        BufferedImage imageCode = ImageIO.read(new URL(qrCodeImg));
        // 以原图片为模板
        Graphics2D g = imageLocal.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setComposite(ac);
        g.setBackground(Color.WHITE);
        // 在模板上添加用户二维码(地址,左边距,上边距,图片宽度,图片高度,未知)
        g.drawImage(imageCode, 250, 1500, 250, 250, null);
        // 设置文本样式
        g.setFont(new Font("微软雅黑", Font.PLAIN, 40));
        g.setColor(Color.red);
        // 计算文字长度，计算居中的x点坐标
        g.drawString(shareDesc, imageLocal.getWidth() - 330, imageLocal.getHeight() - 530);
        g.setColor(Color.cyan);
        g.drawString(shareDesc, imageLocal.getWidth() - 200, imageLocal.getHeight() - 530);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        saveAsJPEG(imageLocal, out);
        out.close();
        return out;
    }

    /**
     * 以JPEG编码保存图片
     *
     * @param fos
     *            文件输出流
     * @throws IOException
     */
    private static void saveAsJPEG(BufferedImage imageToSave, ByteArrayOutputStream fos) throws IOException {
        ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("png").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
//        if (jPEGcompression >= 0 && jPEGcompression <= 1f) {
//            // new Compression
//            JPEGImageWriteParam jpegParams = (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
//            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
//            jpegParams.setCompressionQuality(jPEGcompression);
//
//        }
        // new Write and clean up
        ImageIO.setUseCache(false);
        imageWriter.write(new IIOImage(imageToSave, null, null));
        ios.close();
        imageWriter.dispose();
    }

    /**
     * 比例缩放
     * @param
     */
    private static BufferedImage zoomImage(BufferedImage source, int w, int h) throws Exception {
        double wr = 0, hr = 0;
        Image Itemp = source.getScaledInstance(w, h, source.SCALE_SMOOTH);//设置缩放目标图片模板
        wr = w * 1.0 / source.getWidth();     //获取缩放比例
        hr = h * 1.0 / source.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(source, null);
        return (BufferedImage) Itemp;
    }

    private static ByteArrayOutputStream getHaiBao(String originalImg, String qrCodeImg, String title,String desc) throws Exception{
        // 加载原图图片
        BufferedImage imageLocal = ImageIO.read(new URL(originalImg));
        //加载二维码
        BufferedImage imageQu = ImageIO.read(new URL(qrCodeImg));
        imageQu =zoomImage(imageQu,35,26);
        imageLocal = zoomImage(imageLocal,842,596);
        Graphics2D d = imageLocal.createGraphics();
        d.drawImage(imageQu,340,30,imageQu.getWidth(),imageQu.getHeight(),null);

        if (title != null) {
            d.setFont(new Font("微软雅黑", Font.BOLD, 20));
            d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//设置抗锯齿
            d.setPaint(new Color(0, 0, 0, 64));//阴影颜色
            d.drawString(title, 383, 50);//先绘制阴影
            d.setPaint(Color.BLACK);//正文颜色
            Color color = new Color(82, 83, 83);
            d.setColor(color);
            d.drawString(title, 383, 50);//用正文颜色覆盖上去
        }
        if (desc != null) {
            d.setFont(new Font("微软雅黑",4, 12));
            d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//设置抗锯齿
            d.setPaint(new Color(0, 0, 0, 64));//阴影颜色
            d.drawString("注意事项：" + desc, 30, 500);//先绘制阴影
            d.setPaint(Color.BLACK);//正文颜色
            Color color = new Color(82, 83, 83);
            d.setColor(color);
            d.drawString("注意事项：" + desc, 30, 500);//用正文颜色覆盖上去
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        saveAsJPEG(imageLocal, out);
        out.close();
        return out;
    }

    public static void main(String[] args) {
        long starttime = System.currentTimeMillis();
        System.out.println("开始：" + starttime);
        String originalImg = "file:///C:\\Users\\Admin\\Desktop\\123.png";
        String codeImg = "file:///C:\\Users\\Admin\\Desktop\\2016大卫美术教育LOGO-02.png";
        String title = "大卫美术教育";
        String desc = "请注意安全哦！";
        try {
            ByteArrayOutputStream haiBao = getHaiBao(originalImg, codeImg, title,desc);
            byte[] bytes = haiBao.toByteArray();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File("C:\\Users\\Admin\\Desktop\\test.png");
            //创建输出流
            FileOutputStream fileOutStream = new FileOutputStream(imageFile);
            //写入数据
            fileOutStream .write(bytes);
            fileOutStream.close();
            haiBao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("结束：" + (System.currentTimeMillis() - starttime) / 1000);
    }
    
}
