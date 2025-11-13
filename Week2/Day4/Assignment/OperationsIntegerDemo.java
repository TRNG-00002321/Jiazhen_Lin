package Assignment;

public class OperationsIntegerDemo {
    int a = 30;
    int b = 60;

    void printGreatestOf3(int a, int b, int c){
        if(a>b && a>c){
            System.out.println(a);
        }
        else if(b>a && b>c){
            System.out.println(b);
        }
        else{
            System.out.println(c);
        }
    }

    void booleanOperations(){
        boolean a = true;
        boolean b = false;

        System.out.println(!a);
        System.out.println(a|b);
        System.out.println((!a&b)|(!b&a));

    }


    public static void main(String[] args) {
        OperationsIntegerDemo obj = new OperationsIntegerDemo();
        System.out.println(obj.a + obj.b);
        System.out.println(obj.a - obj.b);
        System.out.println(obj.a * obj.b);
        System.out.println((float) obj.a / (float) obj.b);
        obj.printGreatestOf3(50, 70, 40);
        obj.booleanOperations();

    }

}
