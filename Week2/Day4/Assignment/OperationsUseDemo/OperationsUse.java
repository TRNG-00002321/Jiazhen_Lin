package Assignment.OperationsUseDemo;

public class OperationsUse {
    public static void main(String[] args) {
        int a =  1;
        int b = 2;
        OperationsWithFunctions op = new OperationsWithFunctions();
        System.out.println(op.add(a,b));
        System.out.println(op.sub(a,b));
        System.out.println(op.mul(a,b));
        System.out.println(op.div(a,b));
    }
}
