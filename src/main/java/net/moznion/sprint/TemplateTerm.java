package net.moznion.sprint;

class TemplateTerm {
    String content;
    boolean isPlaceholder;

    TemplateTerm(final String content, final boolean isPlaceholder) {
        this.content = content;
        this.isPlaceholder = isPlaceholder;
    }
}
