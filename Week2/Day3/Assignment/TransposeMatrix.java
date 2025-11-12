package Assignment;

import java.util.Arrays;

public class TransposeMatrix {
    void print2DArray(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    int[][] transpose(int[][] arr) {
        int[][] transposeArray = new int[arr[0].length][arr.length];
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                transposeArray[j][i] = arr[i][j];
            }
        }
        return transposeArray;
    }

    public static void main(String[] args) {
        TransposeMatrix demo = new TransposeMatrix();
        int[][] temp = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] transposed = demo.transpose(temp);
        System.out.println("Original array:");
        demo.print2DArray(temp);
        System.out.println("Transpose of the array:");
        demo.print2DArray(transposed);
    }
}
