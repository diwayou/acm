package spider

import com.diwayou.web.domain.FetcherType
import com.diwayou.web.domain.HtmlDocumentPage
import com.diwayou.web.domain.Request
import com.diwayou.web.fetcher.FetcherFactory
import com.diwayou.web.http.robot.HttpRobot

Request request = new Request("https://www.baidu.com")
        .setFetcherType(FetcherType.FX_WEBVIEW);
HttpRobot robot = FetcherFactory.one().getFxWebviewFetcher().getRobot()
robot.withCloseable {
    robot.get("https://www.baidu.com", 2);
    robot.executeScript("document.getElementById('kw').value = '计算机网络'", 1);
    robot.executeScript("document.getElementById('su').click()", 3);

    // 抓取前10页结果
    for (int i = 1; i < 10; i++) {
        spider.submitPage(new HtmlDocumentPage(request, robot.getDocument(), robot.getRequestUrls()));

        // 清除url记录
        robot.clear();

        robot.executeScript("a = document.getElementsByClassName('n'); a[a.length - 1].click()", 1);
    }
}