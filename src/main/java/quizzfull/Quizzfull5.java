package quizzfull;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Quizzfull5 {


    public static void main(String[] args) {
        for (int i = 0; i < 4;) {
            switch (new Integer(i++)){
                case 1:
                    System.out.println("one ");
                    break;
                case 3:
                    System.out.println("tree ");
                    break;
                case 4:
                    System.out.println("four ");
                    break;
                default:
                    System.out.println("def");
            }
        }
    }

}
