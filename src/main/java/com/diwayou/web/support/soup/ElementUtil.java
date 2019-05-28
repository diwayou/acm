package com.diwayou.web.support.soup;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.*;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
import java.net.URI;

public class ElementUtil {

    public static String text(Element element) {
        final StringBuilder accum = new StringBuilder();
        NodeTraversor.traverse(new NodeVisitor() {
            public void head(Node node, int depth) {
                if (node instanceof TextNode) {
                    TextNode textNode = (TextNode) node;
                    appendNormalisedText(accum, textNode);
                } else if (node instanceof Element) {
                    Element element = (Element) node;
                    if (accum.length() > 0 &&
                            (element.isBlock() || element.tag().getName().equals("br")) &&
                            !lastCharIsWhitespace(accum))
                        accum.append(' ');
                }
            }

            public void tail(Node node, int depth) {
                // make sure there is a space between block tags and immediately following text nodes <div>One</div>Two should be "One Two".
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (element.isBlock() && (node.nextSibling() instanceof TextNode) && !lastCharIsWhitespace(accum))
                        accum.append('\n');
                }

            }
        }, element);

        return accum.toString().trim();
    }

    private static void appendNormalisedText(StringBuilder accum, TextNode textNode) {
        String text = textNode.getWholeText();

        if (preserveWhitespace(textNode.parentNode()) || textNode instanceof CDataNode)
            accum.append(text);
        else
            StringUtil.appendNormalisedWhitespace(accum, text, lastCharIsWhitespace(accum));
    }

    private static boolean preserveWhitespace(Node node) {
        // looks only at this element and five levels up, to prevent recursion & needless stack searches
        if (node instanceof Element) {
            Element el = (Element) node;
            int i = 0;
            do {
                if (el.tag().preserveWhitespace())
                    return true;
                el = el.parent();
                i++;
            } while (i < 6 && el != null);
        }
        return false;
    }

    private static boolean lastCharIsWhitespace(StringBuilder sb) {
        return sb.length() == 0 || sb.charAt(sb.length() - 1) == ' ' || sb.charAt(sb.length() - 1) == '\n';
    }

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(URI.create("http://www.qq.com").toURL(), 3000);
        System.out.println(text(document));
    }
}
