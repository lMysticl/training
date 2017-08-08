package two_pointers;

/**
 * @author Pavel Putrenkov
 */
public class Palindrom {
    public static int recursion(int n) {
        if (n < 10) {
            return n;
        }
        else {
            System.out.print(n % 10 + " ");
            return recursion(n / 10);
        }
    }
    public static void main(String[] args) {
        System.out.println(recursion(123));
    }

    public static boolean isPalindrome(String text) {
        return text.replaceAll("\\W","")
                .equalsIgnoreCase(new StringBuilder(text.replaceAll("\\W",""))
                        .reverse().toString());
    }

    public static Boolean isPalindrome2(String s) {
        for (int i = 0; i < s.length() / 2; ++i) {
            // Сравниваем символ с начала и конца
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false; // символы не равны, не полиндром, возвращаем фальшь
            }
        }
        return true; // проверка не выявила несовпадающих символов, возвращаем истину
    }

}
