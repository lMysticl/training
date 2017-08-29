package work_task;

/**
 * @author Pavel Putrenkov
 */
public class Palindrome {
    /**
     * A palindromic number reads the same both ways.
     * The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 * 99.
     * Find the largest palindrome made from the product of two 3-digit numbers.
     */

    private boolean isPalindrome(String product) {
        return product.length() == 0 || product.length() == 1 || product.charAt(0) == product.charAt(product.length() - 1) && isPalindrome(product.substring(1, product.length() - 1));
    }



    public static void main(String[] args)
    {
        long timeStart=System.currentTimeMillis() ;
        Palindrome e = new Palindrome();

        int biggest = 0;

        for (int i=100;i<1000;i++){
            for (int j=i;j < 1000;j++){
                int product = i * j;
                if ( product > biggest && e.isPalindrome(Long.toString(product)) )
                {
                    biggest = product;
                }
            }
        }

        System.out.println(biggest);
        long timeend=System.currentTimeMillis() ;
        System.out.println(timeend-timeStart);

    }


}


