package cn.tao.servlet;

import cn.tao.util.QRCodeUtil;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;

@WebServlet("/image")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        String url = req.getParameter("url");
        String code = req.getParameter("code");
        try {
            BufferedImage small = QRCodeUtil.encode2(URLDecoder.decode(url), null, false);

            String filePath = "img/" +code+".jpg";
            BufferedImage finalImage = overlapImage(filePath, small);

            byte[] fimalByte = imageToBytes(finalImage, "jpg");
            resp.getOutputStream().write(fimalByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 转换BufferedImage 数据为byte数组
     * Image对象
     * @param format
     * image格式字符串.如"gif","png"
     * @return byte数组
     */
    public static byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static BufferedImage overlapImage(String filePath,BufferedImage small) {
        try {
            BufferedImage big = ImageIO.read(new ClassPathResource(filePath).getFile());
            Graphics2D g = big.createGraphics();
            int x = (big.getWidth() - small.getWidth())-30;
            int y = (big.getHeight()-small.getHeight())-105;
            g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
            g.dispose();
            return big;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
