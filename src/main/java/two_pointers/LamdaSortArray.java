package two_pointers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.sun.deploy.trace.Trace.print;

public class LamdaSortArray {
    public static void main(String[] args) {
//        List<Integer> integers = new ArrayList<>();
//        integers.add(3);
//        integers.add(2);
//        integers.add(1);
//        linkSort(integers);
//        System.out.println(integers);
//
//        randomForEach();
//        randomSortNumbers();
//        uniqueSquareNumbers();
//        minMaxAvarege();
//        getTime();
//        tryFinally();
    }

    //Sorting method, using linkMethod
    private static void linkSort(List<Integer> integers) {
        integers.sort(Integer::compareTo);
    }

    //get 10 random numbers using Foreach
    private static void randomForEach() {
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);
    }

    //unique square numbers using map
    private static void uniqueSquareNumbers() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        //get list of unique sqaures
        List<Integer> sqauresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("sqauresList =" + sqauresList);
    }

    //get 10 random numbers using Foreach
    private static void randomSortNumbers() {
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);
    }

    private static void minMaxAvarege() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Average of all numbers :" + stats.getAverage());
        System.out.println("Max of all numbers :" + stats.getMax());
        System.out.println("Min of all numbers :" + stats.getMin());
    }

    private static void getTime() {
        LocalDate today = LocalDate.now();
        System.out.println("CurentTime: " + today);
    }

    private static void tryFinally(){
        try {
            System.out.println("TRY");
        }finally {
            System.out.println("FINALLY");
        }
    }


    static class whatFirst{
//        В итоге в выводе получаем:
//        s1 [Class A] In static fields
//        s2 [Class A] In static fields
//        s3 [Class A] In static initialization block
//        s1B [Class B] In static fields
//        s2B [Class B] In static fields
//        s3B [Class B] In static initialization block
//        ns1 [Class A] In non-static fields
//        ns2 [Class A] In non-static fields
//        ns3 [Class A] In non-static initialization block
//        ns4 [Class A] In constructor
//        ns1B [Class B] In non-static fields
//        ns2B [Class B] In non-static fields
//        ns3B [Class B] In non-static initialization block
//        ns4B [Class B] In constructor
    }


     static class Insect {
            private int i = 9;                                                      // [9]
            protected int j;                                                        // [10]

            Insect() {                                                              // [8]
                print("i = " + i + ", j = " + j);                                   // [11]
                j = 39;                                                             // [12]
            }

            private static int x1 = printInit("static Insect.x1 initialized");   // [2]

            static int printInit(String s) {                                        // [3]
                print(s);
                return 47;
            }
        }

        public static class Beetle extends Insect {
            private int k = printInit("Beetle.k initialized");                   // [13]

            public Beetle() {                                                       // [7]
                print("k = " + k);                                     // [14]
                print("j = " + j);                                     // [15]
            }

            private static int x2 = printInit("static Beetle.x2 initialized");   // [4]

            public static void main(String[] args) {                                // [1]
                print("Beetle constructor");                                        // [5]
                Beetle b = new Beetle();                                            // [6]
            }
        }
          //  1)  Статические элементы родителя
          //  2)  Статические элементы наследника
          //  3)  Глобальные переменные родителя
          //  4)  Конструктор родителя
          //  5)  Глобальные переменные наследника
          //  6)  Конструктор наследника

}

