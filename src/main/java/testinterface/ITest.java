package testinterface;

import work_task.Test;

import java.util.function.Function;

/**
 * @author Pavel Putrenkov
 */
public interface ITest extends I1, I2 {
    //interface cann't be final because it must be implemented

    public static final Integer NUM = 10;

    static int sum() {
        return 10 + 5;
    }

    public abstract void method();

    default int mul() {
        return 10 * 5;
    }
}

interface I1 {
    static void ddd() {
        int d = 10;
        Integer dd = 2;
        Test ddd = new Test();
        ddd.hashCode();
        System.out.println("I1");
    }

    //functional interface
    void method1();


//   default void defMeth(){
//      System.out.println("I1"); //two method can't be default in the same class
//   }

}

interface I2 {
    //interface marker
    static void ddd() {
        System.out.println("I2");
    }

    void method1();

    default void defMeth() {
        System.out.println("I2");
    }
//    no private or protected method in interface
//    protected void testMethod();
//    private void testMethod();
}

abstract class A1 {
    void method1() {
        System.out.println("A1");
    }
}

abstract class A2 {
    protected void method1() {
        System.out.println("A2");
    }

    static void  method2() {
        System.out.println("A2");
    }


    static void  method3(int i) {
        System.out.println(i);
    }

    static void  method3() {
        System.out.println("A2");
    }
     Integer  method4() {
        System.out.println("A2");
        return null;
    }
    Integer  method5( Integer num) {
        System.out.println("A2");
        return num;
    }



}

class A3 implements TestClass.HelloIntrface{


    @Override
    public void hello() {

    }
}

class TestClass extends A2 implements I1, I2 {

    public static void main(String[] args) {

        TestClass testClass = new TestClass();
        testClass.defMeth();
        TestClass.method2();
        TestClass.method3();
        TestClass.method3(1);
        testClass.method4();
        System.out.println(testClass.method5( testClass.method4())+"5");

        Function<Integer,Integer> inc =e->e+1;
        Function<Integer,Integer> inc2 =e->e*2;
        System.out.println(inc.andThen(inc2));

    }

    interface HelloIntrface {
        void  hello();
    }
    /**
     * Сужать видимость нельзя расширять можно
     */
    @Override
    public void method1() {

    }




    class A3 extends TestClass implements HelloIntrface{
        @Override
        public void hello() {

        }
    }

}