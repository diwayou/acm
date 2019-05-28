package com.diwayou.web.store;

import com.diwayou.web.domain.Page;
import com.diwayou.web.log.AppLog;
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

    private static final Logger log = AppLog.getCrawl();

    public static final String DIR = "dir";

    private File dir;

    public FilePageStore(File dir) {
        this.dir = dir;
    }

    @Override
    public StoreResult store(Page page) {
        if (dir == null) {
            log.warning("dir为空，不能保存文件url=" + page.getRequest().getUrl());
            return StoreResult.empty;
        }

        try {
            String urlPath = UrlUtil.urlToFilename(page.getRequest().getParentUrl());

            Path fileDirPath = Path.of(dir.getAbsolutePath(), urlPath);
            if (!Files.exists(fileDirPath)) {
                Files.createDirectories(fileDirPath);
            }

            String ext = PageUtil.getExt(page);
            String name = FilenameUtil.randFileName(ext);

            Path path = fileDirPath.resolve(name).normalize();
            Files.copy(new ByteArrayInputStream(page.bodyAsByteArray()), path);

            return new StoreResult(path.toString());
        } catch (Exception e) {
            log.log(Level.WARNING, "保存失败!url=" + page.getRequest().getUrl(), e);
        }

        return StoreResult.empty;
    }
}
