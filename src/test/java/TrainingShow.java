import org.junit.Test;

public class TrainingShow {

    @Test
    public void test1() {
    String str1 = new String("hi");
    String str2 ="hi";
    String str3 =str1;
    String str4 =str2;
        System.out.println(str1.equals(str2));
        System.out.println(str1==str2);
        System.out.println("*******");
        System.out.println(str1.equals(str3));
        System.out.println(str1==str3);
        System.out.println("*******");
        System.out.println(str3.equals(str4));
        System.out.println(str3==str4);
        System.out.println("*******");
        System.out.println(str2.equals(str4));
        System.out.println(str2==str4);
    }

    @Test
    public void test2(){
        int num1 = 2147483647;
        System.out.println(num1);
        System.out.println(Integer.toBinaryString(num1));
        System.out.println(++num1);
        System.out.println(Integer.toBinaryString(num1));
        System.out.println(num1);
        System.out.println(++num1);
        System.out.println(Integer.toBinaryString(num1));

    }

    @Test
    public void test3(){
        String s1 = new String("John");
        String s2 = new String("John");
        String s3 = s1; //references of the same object

        System.out.println("s1 == s2: "+(s1 == s2));
        System.out.println("s1.equals(s2): "+(s1.equals(s2)));
        System.out.println("s1 == s3: "+(s1 == s3));
        System.out.println("s1.equals(s3): "+(s1.equals(s3)));
    }

    @Test
    public void test4(){
        Integer n1 = 100;
        Integer n2 = 100;
        System.out.println("s1 == s2: "+(n1 == n2));
        System.out.println("s1.equals(s2): "+(n1.equals(n2)));

        Integer n3 = 128;//127 is lim for identical link in heap
        Integer n4 = 128;
        System.out.println("s1 == s3: "+(n3 == n4));
        System.out.println("s1.equals(s3): "+(n3.equals(n4)));
    }
    @Test
    public void test5(){
        System.out.print("abvg");
        System.out.write(13);
        System.out.print("dejz");
    }


}
