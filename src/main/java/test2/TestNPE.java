package test2;

/**
 * @author Pavel Putrenkov
 */
public class TestNPE {
    public static String handle(Test f, String s) {
        if (s.isEmpty()) {
            return "(none)";
        }
        return f.format(s.trim());
    }

    public static void main(String[] args) {
       Test test = new Test();
        TestNPE.handle(null,"d");
    }
}