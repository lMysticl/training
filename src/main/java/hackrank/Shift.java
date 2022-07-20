package hackrank;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Shift {

}
 class LeftShiftRightShiftString_x0 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(getShiftedString("abcd", 0, 0));
    }

    static String getShiftedString(String s, int leftShifts, int rightShifts) {
        // Write your code here.
        int len = s.length();
        if (len == 1) {
            return s;
        }
        int val = leftShifts - rightShifts;
        String result = "";

        if (val >= 0) {
            int shifts = val % len;
            String left = s.substring(shifts, len);

            String right = s.substring(0, shifts);

            result = left + right;

        }
        if (val <= 0) {
            val = val * -1;
            int shifts = val % len;
            String left = s.substring(len - shifts, len);
            String right = s.substring(0, len - shifts);
            result = left + right;

        }

        return result;
    }
}