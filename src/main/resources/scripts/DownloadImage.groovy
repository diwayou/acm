package scripts

import com.diwayou.web.domain.FetcherType
import com.diwayou.web.domain.HtmlDocumentPage
import com.diwayou.web.support.PageUtil

if (!PageUtil.isHtml(page)) {
    println("不是html");
    return;
}
util.submit(doc.select("a[href]"), "href", page, spider);

if (page.getRequest().getFetcherType().equals(FetcherType.JAVA_HTTP)) {
    util.submit(doc.select("img"), "src", page, spider);
} else if (page.getRequest().getFetcherType().equals(FetcherType.FX_WEBVIEW)) {
    Set<String> resourceUrls = ((HtmlDocumentPage) page).getResourceUrls();

    for (String resourceUrl : resourceUrls) {
        spider.submitRequest(util.newRequest(resourceUrl.replaceAll("[\\d]+x[\\d]+", "430x430"), page.getRequest()));
    }
}