public class Bench {
    public static void main() throws Exception {
        new Benchmark(new StringBuilderBenchmark()).warmup(10).run(100000).timethese().cmpthese();
    }

    public static class StringBuilderBenchmark {
        private final static Sprint SPRINT = new Sprint();
        private final static Exception EX = new Exception("e");

        @Benchmark.Bench
        public static void sprintff() {
            SPRINT.ff("{}, {} sec, err: {}", "test", 123, EX);
        }

        @Benchmark.Bench
        public static void stringFormat() {
            String.format("%s, %s sec, err: %s", "test", 123, EX);
        }
    }
}
