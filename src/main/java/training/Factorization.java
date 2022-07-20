package training;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Factorization {

    public static String factorize(long n) {
        long k=2;
        StringBuilder result= new StringBuilder();
        while(n % k == 0){
            result.append(n % k);
            k++;
        }
        return result.toString() +k;
    }

    public static void main(String[] args) {
        System.out.println(factorize(15));
    }
}
