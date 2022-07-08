package test2;

/**
 * @author Pavel Putrenkov
 */
class Mountain {

    String name = "Himalaya";

    static Mountain getMountain() {
        System.out.println("Getting Name ");
        return null;
    }

    public static void main(String[] args) {
        //System.out.println( getMountain().name );
        Integer a = 5600;
        Integer b = 2800 * 2;
        System.out.println(a == b);
    }
}
