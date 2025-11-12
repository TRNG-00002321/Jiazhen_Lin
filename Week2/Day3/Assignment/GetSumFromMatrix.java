package Assignment;

public class GetSumFromMatrix {
    int sumOfElements(int[][] arr) {
        int sum = 0;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                sum += arr[i][j];
            }
        }
        return sum;
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

    public static void main(String[] args) {
        GetSumFromMatrix demo = new GetSumFromMatrix();
        int[][] temp = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Sum of elements in array:");
        System.out.println(demo.sumOfElements(temp));
        int[] sum = demo.sumOfRows(temp);
        System.out.println("Sum of each row:");
        for (int num : sum) {
            System.out.print(num + " ");
        }
        System.out.println();
        System.out.println("Sum of each column:");
        int[] sum2 = demo.sumOfColumns(temp);
        for (int num : sum2) {
            System.out.print(num + " ");
        }
    }
}
