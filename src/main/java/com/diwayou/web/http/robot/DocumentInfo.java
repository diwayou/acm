package com.diwayou.web.http.robot;

import org.w3c.dom.html.HTMLDocument;

import java.util.Set;

public class DocumentInfo {

    private HTMLDocument htmlDocument;

    private Set<String> resourceUrls;

    public DocumentInfo(HTMLDocument htmlDocument, Set<String> resourceUrls) {
        this.htmlDocument = htmlDocument;
        this.resourceUrls = resourceUrls;
    }

    public HTMLDocument getHtmlDocument() {
        return htmlDocument;
    }

    public DocumentInfo setHtmlDocument(HTMLDocument htmlDocument) {
        this.htmlDocument = htmlDocument;
        return this;
    }

    public Set<String> getResourceUrls() {
        return resourceUrls;
    }

    public DocumentInfo setResourceUrls(Set<String> resourceUrls) {
        this.resourceUrls = resourceUrls;
        return this;
    }
}
