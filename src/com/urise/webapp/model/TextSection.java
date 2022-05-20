package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
