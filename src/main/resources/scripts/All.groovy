package scripts


import java.nio.file.Path

if (helper.isImage()) {
    if (helper.contentLength() > 20 * 1000) {
        helper.storeWithIndex(Path.of("D:/tmp/image"))
    }
    return
}

helper.storeWithIndex(Path.of("D:/tmp/doc"))

return urls