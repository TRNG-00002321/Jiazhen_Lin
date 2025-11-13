package Assignment;

public class factoryList {
    // normal and recursion
    int factorial(int n){
        int result = 1;
        for (int i = 1; i <= n; i++){
            result *= i;
        }
        return result;
    }

    int factorial2(int n){
        if (n == 0) return 1;
        return n * factorial(n-1);

    }
    public static void main(String[] args) {
        factoryList list = new factoryList();
        System.out.println(list.factorial(5));
        System.out.println(list.factorial2(5));
    }
}
