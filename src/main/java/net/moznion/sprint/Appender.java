package net.moznion.sprint;

interface Appender {
    StringBuilder append(StringBuilder sb, Object obj);

    boolean isPlaceholder();
}
