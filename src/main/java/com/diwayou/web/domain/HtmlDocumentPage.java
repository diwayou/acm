package com.diwayou.web.domain;

import com.diwayou.web.support.DocumentUtil;
import org.w3c.dom.html.HTMLDocument;

import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;

public class HtmlDocumentPage extends Page {

    private HTMLDocument document;

    private Set<String> resourceUrls;

    private String html;

    public HtmlDocumentPage(Request request, HTMLDocument document, Set<String> resourceUrls) {
        super(request);
        this.document = document;
        this.resourceUrls = resourceUrls;
        try {
            this.html = DocumentUtil.toString(document);
        } catch (Exception ignore) {
            this.html = "";
        }
    }

    @Override
    public String bodyAsString() {
        return html;
    }

    @Override
    public byte[] bodyAsByteArray() {
        return bodyAsString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public int statusCode() {
        return 200;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return HttpHeaders.of(Collections.emptyMap(), (a, b) -> true);
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public Set<String> getResourceUrls() {
        return resourceUrls;
    }
}
