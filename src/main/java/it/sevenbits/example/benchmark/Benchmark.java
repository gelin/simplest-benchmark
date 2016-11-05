package it.sevenbits.example.benchmark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Benchmark to test formatter algorithm.
 * Put call of your formatter to {@link #format()} method.
 */
public final class Benchmark {

    private static final int WARMUP_ITERATIONS = 1000;
    private static final int TEST_DURATION = 30000;  // 30 sec
    private static final double KILO = 1000.0;
    private static final double KIBI = 1024.0;

    private String data;

    /**
     * Starts benchmark
     * @param args first argument is the input filename
     * @throws IOException if cannot read file
     */
    public static void main(final String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java Benchmark input.file");
            System.exit(1);
        }

        Benchmark benchmark = new Benchmark();
        benchmark.readFile(args[0]);
        benchmark.warmup();
        benchmark.test();
    }

    private Benchmark() {
        // init formatter here
    }

    private void format() {
        // call format here
    }

    private void readFile(final String fileName) throws IOException {
        data = new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private void warmup() {
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            format();
        }
    }

    private void test() {
        System.out.println("size\t" + data.length());
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long ops = 0;
        long mem1 = runtime.totalMemory();
        long time1 = System.currentTimeMillis();
        long time2 = 0L;
        long mem2 = 0L;
        do {
            format();
            ops++;
            mem2 = Math.max(mem2, runtime.totalMemory());
            time2 = System.currentTimeMillis();
        } while ((time2 - time1) < TEST_DURATION);  // do it 30 seconds

        System.out.println("time1\t" + time1);
        System.out.println("time2\t" + time2);
        System.out.println("mem1\t" + mem1);
        System.out.println("mem2\t" + mem2);
        System.out.println("ops\t" + ops);
        System.out.println("ops/sec\t" + (ops / (time2 - time1) * KILO));
        System.out.println("memdif (KB)\t" + ((mem2 - mem1) / KIBI));
    }

}
