package multithreading;

/**
 * @author Pavel Putrenkov
 */
public class MemoryLeek {

    public static void main(String[] args) {

        Object[] ref = new Object[1];

//        while (true){
//            Object[] a = new Object[1];
//            Object[] b = new Object[1];
//            a[0]=b;
//            b[0]=a;
//            ref[0] = a;
//            System.out.println(a);
//        }


        while (true) {
            ref = new Object[]{ref};
        }

    }
}
