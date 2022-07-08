package work_task;

import java.util.ArrayList;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Test4 {

//    public static void main(String[] args) {
//        String maString="A";
//        switch(maString) {
//            case "A":
//                System.out.println("A");
//            case "B":
//                System.out.println("B");
//                break;
//        }
//    }



//    public static void main(String[] args) {
//        int i = 2;
//        if (i = 2)
//            System.out.println("yes");
//        else
//            System.out.println("no");
//    }





        public static void main(String[] args) {
            int i=0;
            for(int j=0;j<3;j++){
                i++;             /* long comment *
            i+=j;             * long comment */
            }
            System.out.println(i);
//            Integer num=Integer.MAX_VALUE;

//            Java HotSpot(TM) 64-Bit Server VM 1.6.0_45 limit Integer.MAX_VALUE
//            Java HotSpot(TM) 64-Bit Server VM 1.7.0_72 limit Integer.MAX_VALUE-1
//            Java HotSpot(TM) 64-Bit Server VM 1.8.0_40 limit Integer.MAX_VALUE-2
//            The maximum "safe" number would be 2 147 483 639 (Integer.MAX_VALUE - 8)
            ArrayList<Long> arr = new ArrayList<Long>(Integer.MAX_VALUE - 1);

//            System.out.println(Integer.MAX_VALUE);
        }


//    public static void main(String[] args) {
//        Test4 test4=new Test4();
//        System.out.println(test4);
//        System.out.println(test4.hashCode());
//    }
    private static void method(){


    }
}
