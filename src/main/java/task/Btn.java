package task;

public class Btn implements Comparable<Btn> {
    private int val;
    private Btn left;
    private Btn right;

    public Btn(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }

    public Btn(int val, Btn left, Btn right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Btn o) {
        if (o == null) {
            return -1;
        }
        if (val != o.val) {
            return val - o.val;
        }
        int le = (left != null) ? left.compareTo(o.left) : o.left == null ? 0 : 1;
        int re = (right != null) ? right.compareTo(o.right) : o.right == null ? 0 : -1;

        return (le == 0 || re == 0) ? re + le : re * le;

    }
}

