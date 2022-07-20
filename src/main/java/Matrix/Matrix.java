package Matrix;

import java.util.Arrays;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Matrix {
//    public static void main(String[] args) {
//        int[][] matrix = {{1, 2, 3,4,5,6}, {4, 5, 6,4,4,5}, {7, 8, 9},{2,4,5}};
//        int matrixSize = 0;
//        for (int i = 0; i < matrix[i].length; i++) {
//            matrixSize = matrixSize + matrix[i].length;
//            System.out.println(i);
//            if(i>=matrix[i].length-1){
//                break;
//            }
//        }
//        int[] matrix2 = new int[matrixSize];
//        int index = 0;
//        for (int[] mat1 : matrix) {
//            for (int value : mat1) {
//                matrix2[index] = value;
//                index++;
//            }
//        }
//        System.out.println(Arrays.toString(matrix2));
//
//    }


//    public static void main(String[] args) {
//        int[][] matrix = {{1, 2, 3},
//                {4, 5, 6},
//                {7, 8, 9}};
//
//
//        //3x3
////      1.1 , 1.2, 2.1, *1.3, 2.2, *3.1, 2.3, 3.2, 3.3
//
//        int matrixSize = 0;
//        int i = 0;
//        while (matrix.length > i) {
//            matrixSize = matrixSize + matrix[i].length;
////            if(i>=matrix[i].length-1){
////                break;
////            }
//            i++;
//        }
//
//        int[] matrix2 = new int[matrixSize];
//        int index = 0;
//
//        int[] temp = new int[5];
//        int index2 = 0;
//        for (int row = 0; row < matrix.length; row++) {
//            int[] mat1 = matrix[row];
//
//            for (int col = 0, mat1Length = mat1.length; col < mat1Length; col++) {
//
//                int value = mat1[col];
//
//                if ((row == 0 && col == 2) || (row == 1 && col == 0) || (row == 2 && col == 0)) {
//                    temp[index2] = value;
//                    matrix2[index] = 0;
//                    index2++;
//
//                } else if ((row == 1 && col == 1) || (row == 1 && col == 2)) {
//                    matrix2[index] = 0;
//                    temp[index2] = value;
//                    index2++;
//                } else {
//
//                    matrix2[index] = value;
//                }
//                index++;
//            }
//
//        }
//        index2 = 0;
//        for (int i1 = 0; i1 < matrix2.length; i1++) {
//            if (matrix2[i1] == 0 && matrix2[3]) {
//                matrix2[i1] = temp[index2];
//                index2++;
//            }
//        }
//        matrix2[2] = temp[1];
//        matrix2[3] = temp[4];
//        matrix2[4] = temp[2];
//        matrix2[5] = temp[0];
//        matrix2[6] = temp[3];
//        System.out.println(Arrays.toString(matrix2));
//        System.out.println(Arrays.toString(temp));
//
//    }
//}

//    public int[] findDiagonalOrder(int[][] matrix) {
//        int matrixSize = 0;
//        int i = 0;
//        while (matrix.length > i) {
//            matrixSize = matrixSize + matrix[i].length;
////            if(i>=matrix[i].length-1){
////                break;
////            }
//            i++;
//        }


//        return new int[0];
//    }
//public static void main(String[] args) {
//    int[][] matrix = {{1, 2, 3},
//            {4, 5, 6},
//            {7, 8, 9}};
//    findDiagonalOrder(matrix);
//}
//
//    public static int[]  findDiagonalOrder(int[][] matrix) {
//
//
//
//        if(matrix == null) { return null; }
//        else if(matrix.length == 0) { return new int[0]; }
//        else if(matrix[0].length == 0) { return matrix[0]; }
//
//        int num_rows = matrix.length;
//        int num_cols = matrix[0].length;
//        int[] ret = new int[num_rows*num_cols];
//
//        ret[0] = matrix[0][0];
//        int dir = 0;
//        int i = 0; int j = 0;
//        int count = 0;
//        while(i != (num_rows-1) || j !=(num_cols-1)) {
//            if(dir == 0) {
//                // dir = 0; dir-1(up-right, i--, j++)
//                if(i == 0 && j < (num_cols-1)) {
//                    j++; dir = 1;
//                } else if (i < (num_rows-1) && j == (num_cols-1)) {
//                    i++; dir = 1;
//                } else {
//                    i--; j++;
//                }
//            } else {
//                // dir = 1; dir-2(down-left, i++, j--)
//                if(i < (num_rows-1) && j == 0) {
//                    i++; dir = 0;
//                } else if (i == (num_rows-1) && j < (num_cols-1)) {
//                    j++; dir = 0;
//                } else {
//                    i++; j--;
//                }
//            }
//
//            count++;
//            ret[count] = matrix[i][j];
//        }
//        System.out.println(Arrays.toString(ret));
//        return ret;
//    }}


    /**
     * If out of bottom border (row >= m) then row = m - 1; col += 2; change walk direction.
     * if out of right border (col >= n) then col = n - 1; row += 2; change walk direction.
     * if out of top border (row < 0) then row = 0; change walk direction.
     * if out of left border (col < 0) then col = 0; change walk direction.
     * Otherwise, just go along with the current direction.
     *
     * @param matrix 2d array
     * @return diagonal traverse result
     */
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return new int[0];
        int m = matrix.length, n = matrix[0].length;
        int[] res = new int[m * n];
        int row = 0, col = 0, d = 1;
        for (int i = 0; i < m * n; i++) {
            res[i] = matrix[row][col];
            row -= d;
            col += d;

            if (row >= m) {row = m - 1; col += 2; d = -d;}
            if (col >= n) {col = n - 1; row += 2; d = -d;}
            if (row < 0) {row = 0; d = -d;}
            if (col < 0) {col = 0; d = -d;}
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Matrix traverse = new Matrix();
        int[] res = traverse.findDiagonalOrder(matrix);
        System.out.println(Arrays.toString(res));
    }
}