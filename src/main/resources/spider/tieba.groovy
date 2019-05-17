package spider

import com.diwayou.web.domain.FetcherType
import com.diwayou.web.domain.Request
import com.diwayou.web.http.robot.HttpRobot

Request request = new Request("https://tieba.baidu.com")
        .setFetcherType(FetcherType.FX_WEBVIEW)

HttpRobot httpRobot = robot

httpRobot.get("http://tieba.baidu.com/f?ie=utf-8&kw=%E8%AE%A1%E7%AE%97%E6%9C%BA%E7%BD%91%E7%BB%9C&fr=search&red_tag=p2630938194", 10)

// 抓取前10页结果
for (int i = 1; i < 10; i++) {
    spider.submitRequest(request.setUrl(httpRobot.getDocument().getURL()))

    // 清除url记录
    httpRobot.clear()

    httpRobot.executeScript("document.getElementsByClassName('next pagination-item')[0].click()", 6)
}