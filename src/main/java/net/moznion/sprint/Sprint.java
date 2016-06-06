package net.moznion.sprint;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sprint {
    private final Map<String, List<Appender>> appendersMap = new ConcurrentHashMap<>();

    /**
     * Flag value to decide to use cache or not.
     */
    public boolean useCache = true;

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
        List<Appender> appenders;
        if (useCache) {
            appenders = appendersMap.get(template);
            if (appenders == null) {
                appenders = AppendersBuilder.buildAppenders(TemplateParser.parseTemplate(template));
                appendersMap.put(template, appenders);
            }
        } else {
            appenders = AppendersBuilder.buildAppenders(TemplateParser.parseTemplate(template));
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
}
