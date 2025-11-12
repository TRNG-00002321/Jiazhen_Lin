package Assignment;

public class FindLimitsOfMatrix {
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

    public static void main(String[] args) {
        FindLimitsOfMatrix demo = new FindLimitsOfMatrix();
        int[][] temp = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        System.out.println("Min of elements in array:");
        System.out.println(demo.findMin(temp));
        System.out.println("Max of elements in array:");
        System.out.println(demo.findMax(temp));
    }
}
