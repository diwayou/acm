package scripts


import java.nio.file.Path

if (helper.isImage()) {
    if (helper.contentLength() > 20 * 1000) {
        helper.store(Path.of("D:/tmp/image"))
    }
    return
}

helper.store(Path.of("D:/tmp/doc"))

return urls