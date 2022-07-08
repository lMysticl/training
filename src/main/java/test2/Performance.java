package test2;


import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author Pavel Putrenkov
 */
@State(Scope.Benchmark)
public class Performance {

    private int size = 20_000;
    int maximum = 100;
    //    int[] values =randomValues(size,maximum);
//    int[] random = new Random().ints(size, 0, maximum).toArray();
    private Random r = new Random();

    private int[] randomIntsArray = IntStream.generate(r::nextInt).limit(size).toArray();

    @Benchmark @BenchmarkMode(Mode.Throughput) @OutputTimeUnit(TimeUnit.MINUTES)
    public void doSomething() {
        Arrays.sort(randomIntsArray);// !!!!!!!!!!Скорость увеличивется в двое и выше !!!(Speed increase)
//        System.out.println(Arrays.toString(randomIntsArray));

        int sum=0;
        for (int i = 0; i < 1_0000; i++) {
            for (int randomInt:randomIntsArray) {

                if (randomInt <= 50) {
                    sum-=randomInt;
                } else {
                    sum+=randomInt;
                }
            }
        }
//        System.out.println("Sum = "+sum);
    }

    public static void main(String[] args) {
        Performance performance = new Performance();
        long timeStart = System.nanoTime();
        performance.doSomething();
        long timeEnd = System.nanoTime();

        System.out.println("Took "+(timeEnd - timeStart)/(600000000.0)+" s");

    }

}
