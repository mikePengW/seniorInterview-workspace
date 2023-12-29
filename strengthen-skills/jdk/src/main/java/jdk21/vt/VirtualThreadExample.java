package jdk21.vt;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author wei.peng wrote on 2023-12-29
 * @version 1.0
 */
public class VirtualThreadExample {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        } // executor.close() is called implicitly, and waits
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
