package com.diwayou.spring.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author gaopeng 2021/4/25
 */
@Slf4j
public class DownloadFile {

    public static void main(String[] args) throws IOException {
        WebClient client = WebClient.builder()
                .baseUrl("https://img1.doubanio.com/view/photo/l/public/")
                .build();

        Resource resource = client.get()
                .uri("p2635722489.webp")
                .retrieve()
                .bodyToMono(Resource.class)
                .block();

        if (resource != null) {
            Files.copy(resource.getInputStream(), Path.of("img.webp"));
            log.info("下载成功");
        } else {
            log.warn("下载文件失败");
        }
    }
}
