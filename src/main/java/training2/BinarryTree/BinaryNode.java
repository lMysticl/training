package training2.BinarryTree;

public class BinaryNode<T> {


        private T item;
        private BinaryNode<T> left;
        private BinaryNode<T> right;

        public BinaryNode() {
            item = null;
            left = null;
            right = null;
        }


        public BinaryNode(T value) {
            item = value;
            left = null;
            right = null;
        }


        public BinaryNode(T value, BinaryNode<T> leftSubtree,
                          BinaryNode<T> rightSubtree) {
            item = value;
            left = leftSubtree;
            right = rightSubtree;
        }

        public T getValue() {
            return item;
        }
        public BinaryNode<T> getLeft() {
            return left;
        }
        public BinaryNode<T> getRight() {
            return right;
        }


        public void setValue(T value) {
            item = value;
        }
        public void setLeft(BinaryNode<T> newLeft) {
            left = newLeft;
        }
        public void setRight(BinaryNode<T> newRight) {
            right = newRight;
        }


        public String toString() {
            return item + "";
        }


        public static void main(String[] arguments) {
            BinaryNode<Integer> x = new BinaryNode<Integer>();
            BinaryNode<Integer> y = new BinaryNode<Integer>(3);
            BinaryNode<Integer> z = new BinaryNode<Integer>(9, x, y);

            printXyz(x, y, z);
            z.setValue(65);
            x.setValue(new Integer(16));
            printXyz(x, y, z);
        }

        private static void printXyz(BinaryNode<Integer> x, BinaryNode<Integer> y,
                                     BinaryNode<Integer> z) {
            System.out.println("x is the node " + x +
                    ", with left " + x.getLeft() +
                    " and right " + x.getRight());
            System.out.println("y is the node " + y +
                    ", with left " + y.getLeft() +
                    " and right " + y.getRight());
            System.out.println("z is the node " + z +
                    ", with left " + z.getLeft() +
                    " and right " + z.getRight());
                    }

    }



