package net.moznion.sprint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SprintTest {
    @Test
    public void testff() {
        final Sprint sprint = new Sprint();
        assertThat(sprint.ff("t{}t", "es")).isEqualTo("test");
        assertThat(sprint.ff("t{}, {} sec, {}", "est", 123, new Exception("e")))
                .isEqualTo("test, 123 sec, java.lang.Exception: e");
        assertThat(sprint.ff("test {")).isEqualTo("test {");
        assertThat(sprint.ff("t{{{}}}", "est")).isEqualTo("t{{est}}");

        assertThat(sprint.ff("t{}t", "es")).isEqualTo("test");
        assertThat(sprint.ff("t{}, {} sec, {}", "est", 123, new Exception("e")))
                .isEqualTo("test, 123 sec, java.lang.Exception: e");
        assertThat(sprint.ff("test {")).isEqualTo("test {");
        assertThat(sprint.ff("t{{{}}}", "est")).isEqualTo("t{{est}}");
    }
}
