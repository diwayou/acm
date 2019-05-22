package com.diwayou.db.lucene;

import com.diwayou.db.lucene.ik.IKTokenizer;
import com.diwayou.db.lucene.ik.cfg.Configuration;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

public class IKStudy {
    public static void main(String[] args) throws IOException {
        IKTokenizer tokenizer = new IKTokenizer(new Configuration());

        StringReader reader = new StringReader("大连一方今天赢球了!");
        tokenizer.setReader(reader);

        tokenizer.reset();

        while (tokenizer.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenizer.getAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute.toString());
        }
    }
}
