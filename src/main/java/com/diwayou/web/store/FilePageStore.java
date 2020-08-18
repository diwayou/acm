package com.diwayou.web.store;

import com.diwayou.web.domain.Page;
import com.diwayou.web.support.FilenameUtil;
import com.diwayou.web.support.PageUtil;
import com.diwayou.web.url.UrlUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FilePageStore implements PageStore {

    public static final String DIR = "dir";

    private File dir;

    public FilePageStore(File dir) {
        this.dir = dir;
    }

    @Override
    public StoreResult store(Page page) {
        if (dir == null) {
            log.warn("dir为空，不能保存文件url=" + page.getRequest().getUrl());
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
            log.warn("保存失败!url=" + page.getRequest().getUrl(), e);
        }

        return StoreResult.empty;
    }
}
