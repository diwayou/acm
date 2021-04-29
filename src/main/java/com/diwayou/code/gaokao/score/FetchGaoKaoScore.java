package com.diwayou.code.gaokao.score;

import com.diwayou.util.Json;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.ReactorNetty;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author gaopeng 2021/4/29
 */
@Slf4j
public class FetchGaoKaoScore {

    private static final String MAC_NAME = "HmacSHA1";

    private static final String prefix = "https://";

    private static final String urlTemplate = "api.eol.cn/gkcx/api/?access_token=&local_batch_id=14&local_province_id=21&local_type_id=1&page={page}&school_id={schoolId}&signsafe={signsafe}&size={size}&special_group=&uri=apidata/api/gk/score/special&year={year}";

    // api.eol.cn/gkcx/api/?local_batch_id=14&local_province_id=21&local_type_id=1&page=2&school_id=225&size=10&special_group=&uri=apidata/api/gk/score/special&year=2020
    private static final String urlSafeTemplate = "api.eol.cn/gkcx/api/?local_batch_id=14&local_province_id=21&local_type_id=1&page=%d&school_id=%d&size=%d&special_group=&uri=apidata/api/gk/score/special&year=%d";

    // https://api.eol.cn/gkcx/api/?access_token=&local_batch_id=14&local_province_id=21&local_type_id=1&page=2&school_id=225&signsafe=61981026db85ba6c5514942d84667159&size=10&special_group=&uri=apidata/api/gk/score/special&year=2020
    private static final String scoreUrl = prefix + urlTemplate;

    private static WebClient client;

    static {
        init();
    }

    private static void init() {
        System.setProperty(ReactorNetty.IO_SELECT_COUNT, "" + Math.max(Runtime.getRuntime()
                .availableProcessors(), 4));
        System.setProperty(ReactorNetty.IO_WORKER_COUNT, "20");

        client = WebClient.builder()
                .build();
    }

    public static void main(String[] args) {
        Integer page = 1;
        Integer schoolId = 226;
        Integer size = 30;
        Integer year = 2020;

        String safeCode = encryptSafeCode(String.format(urlSafeTemplate, page, schoolId, size, year));

        String re = client.get()
                .uri(scoreUrl, page, schoolId, safeCode, size, year)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("{}", re);

        ScoreWrapper scoreWrapper = Json.fromJson(re, ScoreWrapper.class);

        String json = AesGaoKao.decrypt(scoreWrapper.getData().getText());

        ScoreResult data = Json.fromJson(json, ScoreResult.class);

        log.info("{}", data);
    }

    private static String encryptSafeCode(String url) {
        try {
            byte[] data = hmacSHA1Encrypt(url, "D23ABC@#56");
            String base64 = Base64.getEncoder().encodeToString(data);

            return Hashing.md5().hashString(base64, StandardCharsets.UTF_8).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] hmacSHA1Encrypt(String encryptText, String encryptKey) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = encryptKey.getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        Mac mac = Mac.getInstance(MAC_NAME);
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(StandardCharsets.UTF_8);
        return mac.doFinal(text);
    }
}
