package spider

import com.diwayou.web.domain.FetcherType
import com.diwayou.web.domain.HtmlDocumentPage
import com.diwayou.web.domain.Request

Request request = new Request("https://tieba.baidu.com/index.html")
        .setFetcherType(FetcherType.FX_WEBVIEW)

robot.get("https://tieba.baidu.com/index.html", 2)
robot.executeScript("document.getElementsByClassName('j_search_input')[0].value = '计算机网络'", 1)
robot.executeScript("document.getElementsByClassName('search_btn_enter_ba')[0].click()", 3)

// 抓取前10页结果
for (int i = 1; i < 10; i++) {
    spider.submitPage(new HtmlDocumentPage(request, robot.getDocument(), robot.getRequestUrls()))

    // 清除url记录
    robot.clear()

    robot.executeScript("document.getElementsByClassName('next pagination-item')[0].click()", 3)
}