package com.diwayou.image.share;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil {

    public static void main(String[] args) throws Exception {
        InputStream logo = ImageUtil.class.getResourceAsStream("/img/logo.jpg");
        String itemImage = "https://img1.tg-img.com/seller/201912/18/7ECFABB8-D51F-42B1-9E64-D889A4830A88.png!y";

        int count = 1;
        long[] time = new long[count];
        BufferedImage image = null;
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            image = createShareImage(600, 960, 26, "Serif", itemImage, "【预售品：1月5日后支持发货或到柜提货】兰蔻新清滢柔肤水400ml 口碑大粉水",
                    "麦凯乐-大连总店提供商品", "百货专柜", "420", "https://m.51tiangou.com/product/listing.html?id=14756067",
                    "/img/bottom.png");
            long end = System.currentTimeMillis();

            time[i] = end - start;
        }

        System.out.println(Arrays.toString(time));

        if (image != null) {
            ImageIO.write(image, "png", new File("baidu.png"));
        }
    }

    public static BufferedImage createShareImage(int width, int height, int fontSize, String fontName, String itemImageUrl, String itemName,
                                                 String storeName, String source, String price, String qrcodeUrl,
                                                 String bottomUrl) throws Exception {
        //Image itemImage = scaleImage(loadImage(itemImageUrl), width, width);
        Image itemImage = loadImage(itemImageUrl);
        BufferedImage qrCodeImage = createQrcode(qrcodeUrl, null, 160, true, ErrorCorrectionLevel.M);
        //Image bottomImage = scaleImage(loadImageResource(bottomUrl), width, 72);
        Image bottomImage = loadImageResource(bottomUrl);

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int curHeight = 0;

        Graphics2D g = result.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        g.drawImage(itemImage, 0, 0, width, width, null);
        curHeight += width;

        g.setColor(new Color(0x252525));

        Font f = new Font(fontName, Font.PLAIN, fontSize);
        g.setFont(f);

        curHeight += 50;

        curHeight = drawStringMultiLine(g, itemName, width - 20, 10, curHeight);

        g.drawImage(qrCodeImage, 420, curHeight - 10, null);
        f = new Font(fontName, Font.PLAIN, fontSize / 4 * 3);
        g.setFont(f);
        g.setColor(new Color(0x9a9a9a));
        drawStringMultiLine(g, "长按识别二维码购买", width - 20, 420, curHeight + 160);

        curHeight += 20;
        curHeight = drawStringMultiLine(g, storeName, width - 20, 10, curHeight);

        curHeight += 10;
        g.setColor(new Color(0xffeded));
        FontMetrics m = g.getFontMetrics();
        g.fillRoundRect(10, curHeight, m.stringWidth(source) + 40, m.getHeight() + 20, 40, m.getHeight() + 20);

        curHeight += m.getHeight() + 7;
        g.setColor(new Color(0xC4666D));
        curHeight = drawStringMultiLine(g, source, width - 20, 30, curHeight);

        curHeight += 60;
        g.setColor(new Color(0xff4848));
        f = new Font(fontName, Font.PLAIN, fontSize * 2);
        g.setFont(f);
        curHeight = drawStringMultiLine(g, "¥ " + price, width - 20, 25, curHeight);

        g.drawImage(bottomImage, 0, height - 72, width, 72, null);

        g.dispose();

        return result;
    }

    public static BufferedImage createQrcode(String content, Object logo, int qrcodeSize, boolean needCompress, ErrorCorrectionLevel level) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, level);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 插入图片
        if (null != logo) {
            insertImage(image, logo, qrcodeSize, needCompress);
        }

        return image;
    }

    public static int drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
        FontMetrics m = g.getFontMetrics();

        if (m.stringWidth(text) < lineWidth) {
            g.drawString(text, x, y);
        } else {
            char[] words = text.toCharArray();
            String currentLine = "" + words[0];
            for (int i = 1; i < words.length; i++) {
                if (m.stringWidth(currentLine + words[i]) < lineWidth) {
                    currentLine += words[i];
                } else {
                    g.drawString(currentLine, x, y);
                    y += m.getHeight() + 5;
                    currentLine = "" + words[i];
                }
            }
            if (currentLine.trim().length() > 0) {
                g.drawString(currentLine, x, y);
            }
        }

        return y + m.getHeight();
    }

    private static BufferedImage loadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        return ImageIO.read(url);
    }

    private static BufferedImage loadImageResource(String imagePath) throws IOException {
        InputStream stream = ImageUtil.class.getResourceAsStream(imagePath);
        return ImageIO.read(stream);
    }

    private static void insertImage(BufferedImage source, Object logo, int qrcodeSize, boolean needCompress) throws Exception {
        Image src;
        if (logo instanceof File)
            src = ImageIO.read((File) logo);
        else
            src = ImageIO.read((InputStream) logo);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        /*默认log和二维码比例为1：5（THRESHOLD）*/
        int w = qrcodeSize / 5;
        if (needCompress) { // 压缩LOGO
            width = height = w;

            src = scaleImage(src, width, height);
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (qrcodeSize - width) / 2;
        int y = (qrcodeSize - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    private static Image scaleImage(Image src, int width, int height) {
        if (src == null) {
            return src;
        }

        Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return image;
    }
}
