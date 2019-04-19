package com.diwayou.web.support;

import com.google.common.base.Preconditions;
import org.apache.commons.io.FilenameUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FilenameUtil {

    /**
     * 生成图片的路径，根据file的扩展名生成文件类型，路径包含日期信息
     * 例如prefix="image" file="/tmp/abc.png"，返回结果是panorama/2018/09/30/B8E36A0B-1798-4B63-9D0E-BE483DB5AEDB.png
     *
     * @param prefix 业务前缀
     * @param name 文件名
     * @return 图片的路径
     */
    public static String genPath(String prefix, String name) {
        return path(prefix, randFileName(FilenameUtils.getExtension(name)));
    }

    public static String genPathWithExt(String prefix, String ext) {
        return path(prefix, randFileName(ext));
    }

    public static String randFileName(String type) {
        return UUID.randomUUID().toString().toUpperCase() + "." + type;
    }

    private static String path(String prefix, String fileName) {
        Preconditions.checkNotNull(prefix);
        Preconditions.checkNotNull(fileName);

        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
        String datePath = sdf.format(new Date());

        return prefix + datePath + fileName;
    }
}
