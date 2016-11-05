package it.sevenbits.example.benchmark;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Benchmark to test formatter algorithm.
 * Put call of your formatter to {@link #format()} method.
 */
public class Benchmark {

    private String data;

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java Benchmark input.file");
            System.exit(1);
        }

        Benchmark benchmark = new Benchmark();
        benchmark.readFile(args[0]);
        benchmark.warmup();
        benchmark.test();
    }

    private void readFile(String fileName) throws IOException {
        data = new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private void warmup() {
        for (int i = 0; i < 1000; i++) {
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
        long time2;
        do {
            format();
            ops++;
            time2 = System.currentTimeMillis();
        } while ((time2 - time1) < 30000);  // do it 30 seconds
        long mem2 = runtime.totalMemory();

        System.out.println("time1\t" + time1);
        System.out.println("time2\t" + time2);
        System.out.println("mem1\t" + mem1);
        System.out.println("mem2\t" + mem2);
        System.out.println("ops\t" + ops);
        System.out.println("ops/sec\t" + (ops / (time2 - time1) * 1000.0));
        System.out.println("memdif (KB)\t" + ((mem2 - mem1) / 1024.0));
    }

    private void format() {

    }

}
