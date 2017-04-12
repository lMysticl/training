package training2;

import java.util.Scanner;

public class Fibanachi {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        if(scanner.hasNext()){
            int i= scanner.nextInt();
            System.out.println(fibonachi(i));
        }
    }

    private static int fibonachi(int i) {
        if(i==0){ return 0;}
        else if (i == 1|i == 2) {
            return 1;}
        System.out.println(i);
        return fibonachi(i-1)+fibonachi(i-2);
    }
}
