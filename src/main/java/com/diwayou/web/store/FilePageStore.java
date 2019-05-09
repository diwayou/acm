package com.diwayou.web.store;

import com.diwayou.web.domain.Page;
import com.diwayou.web.support.FilenameUtil;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.UrlUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilePageStore implements PageStore {

    private static final Logger log = Logger.getLogger(FilePageStore.class.getName());

    public static final String DIR = "dir";

    private File dir;

    public FilePageStore(File dir) {
        this.dir = dir;
    }

    @Override
    public StoreResult store(Page page, PageStoreContext context) {
        File userDir = (File) context.get(DIR);
        if (userDir == null) {
            userDir = this.dir;
        }

        if (userDir == null) {
            log.warning("dir为空，不能保存文件url=" + page.getRequest().getUrl());
            return StoreResult.empty;
        }

        try {
            if (!Files.exists(userDir.toPath())) {
                Files.createDirectories(userDir.toPath());
            }

            String ext = PageUtil.getExt(page);
            String name = FilenameUtil.randFileName(ext);

            String urlPath = UrlUtil.urlToFilename(page.getRequest().getParentUrl()) + "_" + name;

            Path path = Path.of(userDir.getAbsolutePath(), urlPath);
            Files.copy(new ByteArrayInputStream(page.bodyAsByteArray()), path);

            return new StoreResult(path.toString());
        } catch (Exception e) {
            log.log(Level.WARNING, "保存失败!url=" + page.getRequest().getUrl(), e);
        }

        return StoreResult.empty;
    }
}
