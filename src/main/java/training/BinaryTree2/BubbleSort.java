package training.BinaryTree2;

import java.util.Arrays;

public class BubbleSort {


    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        BubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // Bubble Sort Method for Descending Order
    private static void BubbleSort(int[] num) {
        int j;
        boolean flag = true;   // set flag to true to begin first pass
        int temp;   //holding variable

     //   while (flag) {
            flag = false;    //set flag to false awaiting a possible swap
            for (j = 0; j < num.length - 1; j++) {
                if (num[j] < num[j + 1])   // change to > for ascending sort
                {
                    temp = num[j];                //swap elements
                    num[j] = num[j + 1];
                    num[j + 1] = temp;
                    flag = true;              //shows a swap occurred
             //   }
            }
        }
    }
}
