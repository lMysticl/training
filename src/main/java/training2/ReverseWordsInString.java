package training2;

public class ReverseWordsInString {






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