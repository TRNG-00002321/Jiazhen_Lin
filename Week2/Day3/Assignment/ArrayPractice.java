/*
Print a 2D Array: Write a program to initialize and print the elements of a given 2D integer array (matrix).
Sum of Elements: Calculate the sum of all elements in a 2D array.
Find Maximum/Minimum: Find the maximum or minimum element within a 2D array.
Row/Column Sums: Calculate the sum of elements for each individual row and each individual column in a 2D array.
Transpose Matrix: Given a matrix, find its transpose (swap rows and columns).
Matrix Addition: Add two matrices of the same dimensions.
 */

package Assignment;

public class ArrayPractice {
    void print2DArray(int[][] arr) {
        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    int sumOfElements(int[][] arr) {
        int sum = 0;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                sum += arr[i][j];
            }
        }
        return sum;
    }
    int findMin(int[][] arr) {
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                if(arr[i][j] < min) {
                    min = arr[i][j];
                }
            }
        }
        return min;
    }

    int findMax(int[][] arr) {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                if(arr[i][j] > max) {
                    max = arr[i][j];
                }
            }
        }
        return max;
    }

    int[] sumOfRows(int[][] arr) {
        int[] sum = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                sum[i] += arr[i][j];
            }
        }
        return sum;
    }

    int[] sumOfColumns(int[][] arr) {
        int[] sum = new int[arr[0].length];
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                sum[j] += arr[i][j];
            }
        }
        return sum;
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
        ArrayPractice demo = new ArrayPractice();
        int[][] temp = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Original Array:");
        demo.print2DArray(temp);

        System.out.println("Sum of elements in array:");
        System.out.println(demo.sumOfElements(temp));

        System.out.println("Min of elements in array:");
        System.out.println(demo.findMin(temp));
        System.out.println("Max of elements in array:");
        System.out.println(demo.findMax(temp));

        System.out.println();
        int[] sum =  demo.sumOfRows(temp);
        System.out.println("Sum of each row:");
        for(int num : sum) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println("Sum of each column:");
        int[] sum2 =  demo.sumOfColumns(temp);
        for(int num : sum2) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println();
        int[][] transposed =  demo.transpose(temp);
        System.out.println("Transpose of the array:");
        demo.print2DArray(transposed);

        System.out.println("Sum of matrices with the original array and transposed array:");
        int[][] result = demo.add2Matrices(transposed, temp);
        demo.print2DArray(result);

    }
}


