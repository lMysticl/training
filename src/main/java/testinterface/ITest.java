package testinterface;

/**
 * @author Pavel Putrenkov
 */
public interface ITest  extends I1,I2{
   //interface cann't be final because it must be implemented

   public static final Integer NUM =10;

   public abstract void method();


   static int sum(){
      return 10+5;
   }

   default  int mul(){
      return 10*5;
   }
}

interface I1{
   //functional interface
   void method1();
}

interface I2{
   //interface marker
}