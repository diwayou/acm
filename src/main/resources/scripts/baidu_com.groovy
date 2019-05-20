package scripts

import com.diwayou.web.crawl.handler.ScriptHelper
import org.jsoup.nodes.Document
/**
 * 脚本支持4个工具变量：
 * helper：帮助类，详细见com.diwayou.web.crawl.handler.ScriptHelper
 * doc：当前页面的html解析结果，详细见org.jsoup.nodes.Document
 * urls：当前页面解析出的所有url链接，详细见java.util.Set
 *
 * 该脚本可以对doc进行进一步解析并处理，通过helper类进行保存或者进行一些操作
 * 脚本通过返回urls表示进一步需要爬取的页面链接，脚本可以返回更多或者更少的链接数量
 */
ScriptHelper h = helper
Set u = urls
Document d = doc

if (h.isImage()) {
    if (h.contentLength() > 20 * 1000) {
        h.storeWithIndex()
    }
    return
}

return u