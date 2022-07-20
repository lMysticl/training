package quizzfull;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Quizzfull4 {
   public static boolean methodOne(){
       System.out.println("methodOne ");
       return false;
   }

    public static boolean methodTwo(){
        System.out.println("methodTwo ");
        return true;
    }

    public static boolean methodThree(){
        System.out.println("methodThree ");
        return true;
    }
    public static void main(String[] args) {
        System.out.println(methodOne()||methodTwo()||methodThree());
    }

}
