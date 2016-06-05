package net.moznion.sprint;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sprint {
    private static Map<String, List<Appender>> appendersMap = new ConcurrentHashMap<>();

    /**
     * Format and build string according to template.
     * <p>
     * Template might be contain "{}" as placeholder.
     * This method fills given arguments to these placeholders.
     * <p>
     * example:
     * {@code sprint.ff("Hello: {}!", "John"); // => "Hello: John!"}
     *
     * @param template template string to build (e.g. "msg: {}")
     * @param args     arguments to fill to placeholders of template
     * @return Formatted string
     */
    public String ff(final String template, final Object... args) {
        List<Appender> appenders = appendersMap.get(template);
        if (appenders == null) {
            final List<Term> terms = parseTemplate(template);
            appenders = new ArrayList<>();
            for (final Term term : terms) {
                final Appender appender;
                if (term.isPlaceholder) {
                    appender = new Appender() {
                        @Override
                        public StringBuilder append(StringBuilder sb, Object obj) {
                            return sb.append(obj);
                        }

                        @Override
                        public boolean isPlaceholder() {
                            return true;
                        }
                    };
                } else {
                    appender = new Appender() {
                        @Override
                        public StringBuilder append(StringBuilder sb, Object obj) {
                            return sb.append(term.content);
                        }

                        @Override
                        public boolean isPlaceholder() {
                            return false;
                        }
                    };
                }
                appenders.add(appender);
            }
            appendersMap.put(template, appenders);
        }

        int argIndex = 0;

        final StringBuilder sb = new StringBuilder();
        for (Appender appender : appenders) {
            if (appender.isPlaceholder()) {
                appender.append(sb, args[argIndex]);
                argIndex++;
            } else {
                appender.append(sb, null);
            }
        }

        return sb.toString();
    }

    private List<Term> parseTemplate(final String template) {
        final List<Term> terms = new ArrayList<>();

        boolean isCurlOpened = false;
        StringBuilder sb = new StringBuilder();

        for (final char c : template.toCharArray()) {
            if (isCurlOpened) {
                if (c == '}') {
                    terms.add(new Term("{}", true));
                    isCurlOpened = false;
                    continue;
                }
                sb.append('{');
            }

            if (c == '{') {
                terms.add(new Term(sb.toString(), false));
                sb = new StringBuilder();

                isCurlOpened = true;
                continue;
            }

            isCurlOpened = false;
            sb.append(c);
        }

        if (isCurlOpened) {
            sb.append('{');
        }

        terms.add(new Term(sb.toString(), false));

        return terms;
    }

    @Data
    private static class Term {
        private String content;
        private boolean isPlaceholder;

        public Term(String content, boolean isPlaceholder) {
            this.content = content;
            this.isPlaceholder = isPlaceholder;
        }
    }

    private interface Appender {
        StringBuilder append(StringBuilder sb, Object obj);

        boolean isPlaceholder();
    }
}
