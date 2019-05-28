/**
 *
 */
package com.diwayou.db.lucene.ik.cfg;

import com.diwayou.db.lucene.ik.dic.Dictionary;

public class Configuration {

    //是否启用智能分词
    private boolean useSmart;

    //是否启用小写处理
    private boolean enableLowercase;


    public Configuration() {
        this(true, true);
    }

    public Configuration(boolean useSmart, boolean enableLowercase) {
        this.useSmart = useSmart;
        this.enableLowercase = enableLowercase;

        Dictionary.initial(this);
    }

    public boolean isUseSmart() {
        return useSmart;
    }

    public Configuration setUseSmart(boolean useSmart) {
        this.useSmart = useSmart;
        return this;
    }

    public boolean isEnableLowercase() {
        return enableLowercase;
    }
}
