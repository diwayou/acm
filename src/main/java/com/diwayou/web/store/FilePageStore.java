package com.diwayou.web.store;

import com.diwayou.web.domain.Page;
import com.diwayou.web.support.FilenameUtil;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.UrlUtil;
import com.google.common.net.MediaType;
import org.apache.commons.io.FilenameUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilePageStore implements PageStore {

    private static final Logger log = Logger.getLogger(FilePageStore.class.getName());

    public static final String DIR = "dir";

    @Override
    public void store(Page page, PageStoreContext context) {
        File dir = (File) context.get(DIR);

        if (dir == null) {
            log.warning("dir为空，不能保存文件url=" + page.getRequest().getUrl());
            return;
        }

        try {
            String contentType = PageUtil.getContentType(page);
            String ext = "txt";
            if (contentType != null) {
                try {
                    MediaType mediaType = MediaType.parse(contentType);
                    ext = mediaType.subtype();
                    if (ext == null || ext.isBlank()) {
                        ext = FilenameUtils.getExtension(page.getRequest().getUrl());
                        if (ext == null || ext.isBlank()) {
                            ext = "txt";
                        }
                    }
                } catch (Exception e) {
                    log.log(Level.WARNING, "", e);
                }
            }

            if (!Files.exists(dir.toPath())) {
                Files.createDirectories(dir.toPath());
            }

            String name = FilenameUtil.randFileName(ext);

            String urlPath = UrlUtil.urlToFilename(page.getRequest().getParentUrl()) + "_" + name;

            Files.copy(new ByteArrayInputStream(page.bodyAsByteArray()), Path.of(dir.getAbsolutePath(), urlPath));
        } catch (Exception e) {
            log.log(Level.WARNING, "保存失败!url=" + page.getRequest().getUrl(), e);
        }
    }
}
