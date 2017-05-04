package two_pointers;
/*Given an input string, reverse the string word by word. A word is defined as a sequence of non-space characters.

The input string does not contain leading or trailing spaces and the words are always separated by a single space.

For example,
Given s = "the sky is blue",
return "blue is sky the".

Could you do it in-place without allocating extra space?*/
public class ReverseWordsInString_2 {

    public static void main(String[] args) {
        reverseWord();
        reversWord2();

        System.out.println("\n"+recursiveMethod("MyJava"));

    }
    private static void reverseWord() {
        StringBuffer sbf = new StringBuffer("MyJava");
        System.out.println(sbf.reverse());
    }

    private static void reversWord2() {
        String str = "MyJava";

        char[] strArray = str.toCharArray();

        for (int i = strArray.length - 1; i >= 0; i--)
        {
            System.out.print(strArray[i]);     //Output : avaJyM
        }
    }



   private static String recursiveMethod(String str)
    {
        if ((null == str) || (str.length() <= 1))
        {
            return str;
        }

        return recursiveMethod(str.substring(1)) + str.charAt(0);
    }

}