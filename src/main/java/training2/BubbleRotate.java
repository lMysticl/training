package training2;

public class BubbleRotate {


    public  static void rotate (int[] arr, int order){
        if(arr==null|| order<0){
            try {
                throw  new IllegalAccessException("Illegal argument!");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i<order;i++){
            for (int j = arr.length-1; j>0; j--){
            int temp = arr[j];
            arr[j] = arr[j-1];
            arr[j-1]=temp;
            }
        }
    }
}
