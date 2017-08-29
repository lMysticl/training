package work_task;

/**
 * @author Pavel Putrenkov
 */
public class work_task implements f{


    static int  F1(int a, int b) {
        if (a > b )
        return F1(a - b, b) + 1;
    else
        return 0;
    }

    static int F2(int a, int b) {
        int Result = 0;
        while (a > 0) {
            Result = Result + b;
            a = a - 1;
        }
        return Result;
    }

    public static void main(String[] args)  {

        System.out.println((F1(F2(5, 4) + 2, 7)));

        f.m();
    }

}


interface f{
   final static Integer d =10;
     static  void   m(){
        System.out.println("hello");
    }
}