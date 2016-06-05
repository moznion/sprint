package net.moznion.sprint;

import java.util.ArrayList;
import java.util.List;

class TemplateParser {
    static List<TemplateTerm> parseTemplate(final String template) {
        final List<TemplateTerm> terms = new ArrayList<>();

        boolean isCurlOpened = false;
        StringBuilder sb = new StringBuilder();

        for (final char c : template.toCharArray()) {
            if (isCurlOpened) {
                if (c == '}') {
                    terms.add(new TemplateTerm("{}", true));
                    isCurlOpened = false;
                    continue;
                }
                sb.append('{');
            }

            if (c == '{') {
                terms.add(new TemplateTerm(sb.toString(), false));
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

        terms.add(new TemplateTerm(sb.toString(), false));

        return terms;
    }
}
