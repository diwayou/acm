package com.diwayou.web.store;

public enum IndexFieldName {
    parentUrl("父链接"),
    url("链接"),
    type("类型"),
    ext("后缀"),
    content("内容"),
    /**
     * 该值目前只是展示用，索引没使用
     */
    image("图片");

    private String displayName;

    IndexFieldName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public IndexFieldName setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public static IndexFieldName from(String displayName) {
        for (IndexFieldName indexFieldName : values()) {
            if (indexFieldName.displayName.equalsIgnoreCase(displayName)) {
                return indexFieldName;
            }
        }

        throw new IllegalArgumentException("枚举值不正确!displayName=" + displayName);
    }
}
