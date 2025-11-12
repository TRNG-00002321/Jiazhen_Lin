/*
Print a 2D Array: Write a program to initialize and print the elements of a given 2D integer array (matrix).
Sum of Elements: Calculate the sum of all elements in a 2D array.
Find Maximum/Minimum: Find the maximum or minimum element within a 2D array.
Row/Column Sums: Calculate the sum of elements for each individual row and each individual column in a 2D array.
Transpose Matrix: Given a matrix, find its transpose (swap rows and columns).
Matrix Addition: Add two matrices of the same dimensions.
 */

package Assignment;

public class MatrixPractice {
    void print2DArray(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MatrixPractice demo = new MatrixPractice();
        int[][] temp = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Original Array:");
        demo.print2DArray(temp);
    }
}


