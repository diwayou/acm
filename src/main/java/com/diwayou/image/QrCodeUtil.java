package com.diwayou.image;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Hashtable;

public class QrCodeUtil {
    public static final String CHARSET = "utf-8";
    public static final String FORMAT_NAME = "JPG";
    public static final int THRESHOLD = 5;

    private static final int defaultSize = 400;

    public static void main(String args[]) throws Exception {
        File f = QrCodeUtil.createQrFileWithDefaultLogo("http://www.baidu.com",
                400, "百度首页", null, ErrorCorrectionLevel.M);

    }

    public static File createQrFileWithDashedLine(String url, Integer width, String bottomWord, String headWord, ErrorCorrectionLevel level,
                                                  int padding, int fontSize) throws Exception {
            InputStream defaultLogo = QrCodeUtil.class.getResourceAsStream("/data/images/go_white.jpg");
            BufferedImage image = createImage(url, defaultLogo, getSize(width), true, bottomWord, headWord, level, fontSize);

            BufferedImage imageWithDashedLine = new BufferedImage(image.getWidth() + 2*padding,
                    image.getHeight() + 2*padding, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imageWithDashedLine.createGraphics();
            g.setColor(Color.white);
            g.setBackground(Color.white);

            g.fillRect(0, 0, imageWithDashedLine.getWidth(), imageWithDashedLine.getHeight());

            g.setColor(Color.black);

            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g.setStroke(dashed);

            int dashLineLength = padding * 6;
            g.drawLine(0, 0, dashLineLength, 0);
            g.drawLine(0, 0, 0, dashLineLength);
            g.drawLine(imageWithDashedLine.getWidth(), 0,  imageWithDashedLine.getWidth() - dashLineLength, 0);
            g.drawLine(imageWithDashedLine.getWidth(), 0, imageWithDashedLine.getWidth(), dashLineLength);
            g.drawLine(0, imageWithDashedLine.getHeight(), 0, imageWithDashedLine.getHeight() - dashLineLength);
            g.drawLine(0, imageWithDashedLine.getHeight(), dashLineLength, imageWithDashedLine.getHeight());
            g.drawLine(imageWithDashedLine.getWidth(), imageWithDashedLine.getHeight(), imageWithDashedLine.getWidth(), imageWithDashedLine.getHeight() - dashLineLength);
            g.drawLine(imageWithDashedLine.getWidth(), imageWithDashedLine.getHeight(), imageWithDashedLine.getWidth() - dashLineLength, imageWithDashedLine.getHeight());

            g.drawImage(image, padding, padding, Color.white, null);
            g.dispose();

            File file = createFile(bottomWord + ".jpg");

            ImageIO.write(imageWithDashedLine, QrCodeUtil.FORMAT_NAME, file);
            return file;
    }

    public static File createQrFileWithDefaultLogo(String url, Integer width, String bottomWord, String headWord, ErrorCorrectionLevel level) throws Exception {
            InputStream defaultLogo = QrCodeUtil.class.getResourceAsStream("/img/logo.jpg");
            BufferedImage image = createImage(url, defaultLogo, getSize(width), true, bottomWord, headWord, level, 13);

            File file = createFile(bottomWord + ".jpg");

            ImageIO.write(image, QrCodeUtil.FORMAT_NAME, file);
            return file;
    }

    public static File createQrFileWithDefaultLogo(String url, Integer width, String bottomWord, ErrorCorrectionLevel level) throws Exception {
            InputStream defaultLogo = QrCodeUtil.class.getResourceAsStream("/logo.png");
            BufferedImage image = createImage(url, defaultLogo, getSize(width), true, bottomWord, level);

            File file = createFile(bottomWord + ".jpg");

            ImageIO.write(image, QrCodeUtil.FORMAT_NAME, file);
            return file;

    }

    private static BufferedImage createImage(String url,
                                             Object defaultLogo, Integer width, boolean b,
                                             String bottomWord, ErrorCorrectionLevel level) throws Exception {
        return createImage(url, defaultLogo, width, true, bottomWord, null, level, 13);
    }

    /**
     * @param url
     * @param logo
     * @param width
     * @param title
     * @param level L M Q H 越低出错率越高,但图片密度越小,方便识别,默认H
     * @return
     * @Title:createQrFile
     * @Description:指定二维码内容、中心背景图片、宽度和底部说明文字生成二维码文件
     * @return:File
     * @throws：
     */
    public static File createQrFile(String url, File logo, Integer width, String title, ErrorCorrectionLevel level) throws Exception {
            BufferedImage image = createImage(url, logo, getSize(width), true, title, level);

            File file = createFile(title + ".jpg");

            ImageIO.write(image, QrCodeUtil.FORMAT_NAME, file);
            return file;
    }

    /**
     * @param url
     * @param logo
     * @param width
     * @param title
     * @return
     * @Title:createQrFile
     * @Description:指定二维码内容、中心背景图片、宽度和底部说明文字生成二维码文件
     * @return:File
     * @throws：
     */
    public static File createQrFile(String url, File logo, Integer width, String title) throws Exception {
        return createQrFile(url, logo, getSize(width), title, ErrorCorrectionLevel.H);
    }

    private static int getSize(Integer width) {
        if (width == null || width <= 0) {
            return defaultSize;
        }
        return width;
    }

    public static BufferedImage createImage(String content, Object insertfile, int qrcodeSize,
                                            boolean needCompress, String title, String headWord, ErrorCorrectionLevel level,
                                            int fontSize) throws Exception {
        int extraHeight = 0;
        int headHeight = 0;
        if (StringUtils.isNotBlank(title)) {
            extraHeight += 40;
        }

        if (StringUtils.isNotBlank(headWord)) {
            headHeight += 40;
        }

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, level);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrcodeSize, qrcodeSize, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height + extraHeight + headHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = headHeight; y < height + headHeight; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y - headHeight) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }

        if (StringUtils.isNotBlank(title)) {
            for (int x = 0; x < width; x++) {
                for (int y = height + headHeight + 1; y < headHeight + height + extraHeight; y++) {
                    image.setRGB(x, y, 0xFFFFFFFF);
                }
            }

            Graphics2D g = image.createGraphics();
            g.setColor(Color.black);
            g.setBackground(Color.white);

            AttributedString ats = new AttributedString(title);
            Font f = new Font("Serif", Font.BOLD, fontSize);

            ats.addAttribute(TextAttribute.FONT, f, 0, title.length());
            AttributedCharacterIterator iter = ats.getIterator();
            g.drawString(iter, 22, height + headHeight + 22);

            if (StringUtils.isNotBlank(headWord)) {
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < headHeight; y++) {
                        image.setRGB(x, y, 0xFFFFFFFF);
                    }
                }

                AttributedString ats2 = new AttributedString(headWord);
                f = new Font("Serif", Font.BOLD, 30);
                ats2.addAttribute(TextAttribute.FONT, f, 0, headWord.length());
                AttributedCharacterIterator iter2 = ats2.getIterator();

                g.drawString(iter2, Math.max(width / 2 - (headWord.length()) * 7, 0), headHeight);
            }
            //添加水印的文字和设置水印文字出现的内容 ----位置
            g.dispose();//画笔结束
        }

        // 插入图片
        if (null != insertfile) {
            QrCodeUtil.insertImage(image, insertfile, qrcodeSize, headHeight, needCompress);
        }
        return image;
    }

    public static BufferedImage createImage(String content, File insertfile, int qrcodeSize,
                                            boolean needCompress) throws Exception {
        return createImage(content, insertfile, qrcodeSize, needCompress, null, ErrorCorrectionLevel.H);
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, Object insertfile, int qrcodeSize, int headHeight,
                                    boolean needCompress) throws Exception {
        Image src = null;
        if (insertfile instanceof File)
            src = ImageIO.read((File) insertfile);
        else
            src = ImageIO.read((InputStream) insertfile);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        /*默认log和二维码比例为1：5（THRESHOLD）*/
        int w = qrcodeSize / THRESHOLD;
        if (needCompress) { // 压缩LOGO
            width = height = w;
			/*if (width > w) {
				width = w;
			}
			if (height > w) {
				height = w;
			}*/
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (qrcodeSize - width) / 2;
        int y = (qrcodeSize - height) / 2;
        graph.drawImage(src, x, y + headHeight, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y + headHeight, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    private static File createFile(String name) {
        return new File("" + name);
    }

    public static String replaceHalfCharacters(String title) {
        title = StringUtils.replaceEach(title, new String[]{"\\", "/", ":",
                "*", "?", "\"", "<", ">", "|",}, new String[]{"＼", "／", "：",
                "x", "？", "“", "《", "》", "︱"});
        return title;
    }
}
