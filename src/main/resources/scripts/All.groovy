package scripts

import com.diwayou.web.store.FilePageStore
import com.diwayou.web.store.PageStoreContext

if (helper.isImage()) {
    if (helper.contentLength() > 20 * 1000) {
        helper.store(PageStoreContext.create().put(FilePageStore.DIR, new File("D:/tmp/image")))
    }
    return
}
if (!helper.isHtml()) {
    //helper.store(PageStoreContext.create().put(FilePageStore.DIR, new File("D:/tmp/doc")))
    return
}

return urls