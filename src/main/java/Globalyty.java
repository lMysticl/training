/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Globalyty {
    /*
     * Click `Run` to execute the snippet below!
     */



    /*
     * we have an array of stock quotes [3, 2, 3, 9, 2, 10, 9, 1, 5] -> 8
     * find the transaction with the maximum profit
     */
    public static void main(String[] args) {
        int[] arr = {3, 2, 3, 9, 2, 10, 9, 1, 5};

        int max = 0;
        int indexMax = 0;
        int min = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            if (max < arr[i]) {
                max = arr[i];
                indexMax = i;
            }
        }
        for (int i = 0; i < indexMax; i++) {
            if (min > arr[i]) {
                min = arr[i];
            }
        }
        System.out.println(max);
        System.out.println(min);
        System.out.println(max - min);
    }

}
