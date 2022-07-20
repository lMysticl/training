package factorization;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Factorization {

    public static String factorize(long n) {
        int coef = 2;
        long k = 0;
        int j = 0;
        StringBuilder result = new StringBuilder();


        do {
            if ((double) (n / coef) % coef == 0) {
                k = n / coef;
                result.append(coef);
                result.append(",");
                result.append(k);
            } else {
                coef++;
            }
            System.out.println((n / coef) % 2 == 0);
            System.out.println((n / coef) % 2);


        }

        while (n % coef == 0);

        return result.toString();
    }

    public static String primeFactorization(long num) { //method signature. takes long, returns string of factorization
        StringBuilder ans = new StringBuilder(); //creates the answer
        for (int i = 2; i <= num; i++) { //loops from 2 to the num
            if (num % i == 0) { //checks if i is a divisor of num
                ans.append(i).append(",");//writes i in prime factorization
                num = num / i; //since it is written down, num=num/i
                i--; //just in case their are multiple factors of same number. For example, 12=2*2*3
            }
        }
        return (num<0)?"":(ans.substring(0, ans.length() - 1)); //takes away last asterisk. Factorization of 4=2*2*=>2*2
    }

    public static void main(String[] args) {
//        System.out.println(factorize(15));
//        System.out.println(factorize(7));
        System.out.println(primeFactorization(420));
        System.out.println(primeFactorization(-69));
        System.out.println(primeFactorization(123456));
//        System.out.println(15 % 2);

    }
}
