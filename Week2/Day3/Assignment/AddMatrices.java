package Assignment;

public class AddMatrices {
    void print2DArray(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    int[][] add2Matrices(int[][] arr1, int[][] arr2) {
        int[][] result = new int[arr1.length][arr2.length];
        for(int i = 0; i < arr1.length; i++) {
            for(int j = 0; j < arr2.length; j++) {
                result[i][j] = arr1[i][j] + arr2[i][j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        AddMatrices demo = new AddMatrices();
        int[][] temp = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] temp2 = {
                {9, 8, 7},
                {1, 2, 3},
                {4, 5, 6},
        };
        System.out.println("Array 1 :");
        demo.print2DArray(temp);
        System.out.println("Array 2 :");
        demo.print2DArray(temp2);
        System.out.println("Sum of matrices:");
        int[][] result = demo.add2Matrices(temp2, temp);
        demo.print2DArray(result);
    }
}
