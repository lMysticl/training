package codewars;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*https://www.codewars.com/kata/john-and-ann-sign-up-for-codewars/train/java
https://www.codewars.com/kata/57591ef494aba64d14000526/solutions/java

John and his wife Ann have decided to go to Codewars.

On day 0 Ann will do one kata and John - he wants to know how it is working - 0.

Let us call a(n) the number of katas done by Ann at day n we have a(0) = 1 and in the same manner j(0) = 0.

They have chosen the following rules:

On day n the number of katas done by Ann should be n minus the number of katas done by John at day t, t being equal to the number of katas done by Ann herself at day n - 1.
On day n the number of katas done by John should be n minus the number of katas done by Ann at day t, t being equal to the number of katas done by John himself at day n - 1.
Whoops! I think they need to lay out a little clearer exactly what there're getting themselves into!

Could you write:

1) two functions ann and john (parameter n) giving the list of the numbers of katas Ann and John should take on each day from day 0 to day n - 1 (n days - see first example below)?
2) The total number of katas taken by ann (function sum_ann(n)) and john (function sum_john(n)) from day 0 (inclusive) to day n (exclusive)?
Examples:

john(11) -->  [0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 6]
ann(6) -->  [1, 1, 2, 2, 3, 3]

sum_john(75) -->  1720
sum_ann(150) -->  6930*/
public class JohnAndAnn {

    private static Map<Long, Long> johnKatas = new LinkedHashMap<>();
    private static Map<Long, Long> annKatas = new LinkedHashMap<>();

    private static long maxN = 2L;

    static {
        johnKatas.put(0L, 0L);
        johnKatas.put(1L, 0L);
        annKatas.put(0L, 1L);
        annKatas.put(1L, 1L);
    }

    public static void calculateUpTo(long n){
        for (; maxN <= n; maxN++) {
            annKatas.putIfAbsent(maxN, maxN - johnKatas.get(annKatas.get(maxN - 1)));
            johnKatas.putIfAbsent(maxN, maxN - annKatas.get(johnKatas.get(maxN - 1)));
        }
    }

    public static List<Long> john(long n) {
        calculateUpTo(n);
        return johnKatas.values().stream().limit(n).collect(Collectors.toList());
    }
    public static List<Long> ann(long n) {
        calculateUpTo(n);
        return annKatas.values().stream().limit(n).collect(Collectors.toList());
    }

    public static long sumJohn(long n) {
        calculateUpTo(n);
        return johnKatas.values().stream().limit(n).reduce(0L, Long::sum);
    }

    public static long sumAnn(long n) {
        calculateUpTo(n);
        return annKatas.values().stream().limit(n).reduce(0L, Long::sum);
//        return annKatas.values().stream().limit(n).mapToLong(x -> x).sum();
    }

    public static void main(String[] args) {
        System.out.println(JohnAndAnn.sumJohn(75));
        System.out.println(JohnAndAnn.sumAnn(150));

        System.out.println(johnKatas.values().stream().limit(11).collect(Collectors.toList()));
    }
}
