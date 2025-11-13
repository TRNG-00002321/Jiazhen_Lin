package Assignment;
//import Assignment.Customer; //customer class is public

public class CustomerUses {

    public  static void main(String ...args){
        Customer a = new Customer(13);
        a.setAge(23);
        System.out.println("ID: " + a.id + "\nAge: " + a.age);

        Customer b = new Customer("21");
        b.setAge(22);
        System.out.println("ID: " + b.id + "\nAge: " + b.age);

        System.out.println(a.RANDOM_NUMBER);
        System.out.println(b.RANDOM_NUMBER);
    }
}
