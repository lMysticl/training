package initOrderClass;

/**
 * @author Pavel Putrenkov
 */
public class SecondInit {
    String a1 ="firstParent";
    String a2 ="secondParent";
    static {
        System.out.println("static SecondInit");
    }

    {
        System.out.println("body SecondInit");
    }
    SecondInit(){
        System.out.println("constructor SecondInit");

    }
}
