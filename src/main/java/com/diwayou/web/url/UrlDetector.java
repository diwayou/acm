package com.diwayou.web.url;

import com.diwayou.web.domain.Request;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.URLTokenizer;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UrlDetector {
    public static Set<Request> extractUrls(String input, Request request) {
        if (StringUtils.isBlank(input)) {
            return Collections.emptySet();
        }

        List<Term> termList = URLTokenizer.segment(input);

        return termList.stream()
                .filter(t -> t.nature == Nature.xu)
                .map(t -> t.word)
                .map(u -> newRequest(u, request))
                .collect(Collectors.toSet());
    }

    private static Request newRequest(String newUrl, Request old) {
        return new Request(newUrl)
                .setParentUrl(old.getUrl())
                .setDepth(old.getDepth() + 1);
    }
}
