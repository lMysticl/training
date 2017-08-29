package string_array;

/**
 * @author Pavel Putrenkov
 */
public class ReverseUsingXOR {
    public static void main(String[] args) {
        String str = "prateek";
        reverseUsingXOR(str.toCharArray());
    }

    /*Example:
     * str= prateek;
     * str[low]=p;
     * str[high]=k;
     * str[low]=p^k;
     * str[high]=(p^k)^k =p;
     * str[low]=(p^k)^p=k;
     *
     * */
    public static void reverseUsingXOR(char[] str) {
        int low = 0;
        int high = str.length - 1;

        while (low < high) {
            str[low] = (char) (str[low] ^ str[high]);
            str[high] = (char) (str[low] ^ str[high]);
            str[low] = (char) (str[low] ^ str[high]);
            low++;
            high--;
        }

        //display reversed string
        for (int i = 0; i < str.length; i++) {
            System.out.print(str[i]);
        }
    }
}
