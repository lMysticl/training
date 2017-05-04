package training.BinarryTree;

public class Node<T extends Comparable<T>> {
    private T value;
    private Node<T> left, right;

    public Node(T value) {
        this.value = value;
    }

    public void add(T value) {
        if (left == null) {
            left = new Node<T>(value);
            return;
        }
        if (right == null) {
            right = new Node<T>(value);
            return;
        }
        if (left.getSize() < right.getSize()) {
            left.add(value);
        } else {
            right.add(value);
        }
    }
    public void addSort(T value) {
        if (this.value.compareTo(value) == -1) {
            if (left == null) {
                left = new Node<T>(value);
            } else {
                left.addSort(value);
            }
        } else {
            if (right == null) {
                right = new Node<T>(value);
            } else {
                right.addSort(value);
            }
        }
    }

    public int getSize() {
        return 1 + (left == null ? 0 : left.getSize())
                + (right == null ? 0 : right.getSize());
    }

    public Node<T> getNext() {
        if (!(right == null)) return right;
        if (!(left == null)) return left;
        return null;
    }

    public boolean isNormalBranch() {
        if (left == null && right != null) return true;
        if (left != null && right == null) return true;
        return false;
    }

    public boolean isLeaf(){
        return left == null && right == null;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public T getValue() {
        return value;
    }

}