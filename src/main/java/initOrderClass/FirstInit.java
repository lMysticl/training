package initOrderClass;

/**
 * @author Pavel Putrenkov
 */
public class FirstInit extends SecondInit {

    static {
        String a1 = "first";
        String a2 = "second";
        System.out.println("static FirstInit");
    }

    {
        String a3 = "third";
        String a4 = "forth";
        System.out.println("body FirstInit");

    }

    FirstInit() {
        String a3 = "third";
        String a4 = "forth";
        System.out.println("constructor FirstInit");

    }

    public static void main(String[] args) {
        FirstInit f = new FirstInit();
    }
    //result
//    static SecondInit
//    static FirstInit
//    body SecondInit
//    constructor SecondInit
//    body FirstInit
//    constructor FirstInit
}
