package net.moznion.sprint;

import java.util.ArrayList;
import java.util.List;

class AppendersBuilder {
    static List<Appender> buildAppenders(final List<TemplateTerm> terms) {
        final List<Appender> appenders = new ArrayList<>();
        for (final TemplateTerm term : terms) {
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
        return appenders;
    }
}
