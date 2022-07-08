package initOrderClass;

/**
 * @author Pavel Putrenkov
 */
public class SecondInit {
    static {
        System.out.println("static SecondInit");
    }

    String a1 = "firstParent";
    String a2 = "secondParent";

    {
        System.out.println("body SecondInit");
    }

    SecondInit() {
        System.out.println("constructor SecondInit");

    }
}
