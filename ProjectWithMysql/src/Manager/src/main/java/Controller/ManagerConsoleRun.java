package Controller;
import java.util.*;
import java.sql.Connection;
import com.github.freva.asciitable.*;

import DAO.ManagerDBFunctions;
import Model.Approval;
import Model.Expense;
import Service.ManagerFunctions;
import Util.*;


public class ManagerConsoleRun {
    static Connection conn;
    static Scanner sc;
    static ManagerFunctions mf;
    int getUser(){
        System.out.println("1. Create new manager");
        System.out.println("2. Manage login");
        System.out.println("3. Exit");
        String option = sc.nextLine();
        if(option.equals("\n")){
            option = sc.nextLine();
        }
        String username;
        String password;
        switch(option){
            case "1":
                System.out.println("Create new manager:");
                System.out.println("Enter username:");
                username = sc.nextLine();
                System.out.println("Enter password:");
                password = sc.nextLine();
                try{
                    mf.newManager(username,password);
                    return mf.login(username, password);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return 0;
                }

            case "2":
                System.out.println("Manager login:");
                System.out.println("Enter username:");
                username = sc.nextLine();
                System.out.println("Enter password:");
                password = sc.nextLine();
                try{
                    return mf.login(username, password);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return 0;
                }
            case "3":
                return -1;
            default:
                System.out.println("Invalid option");
                return 0;
        }
    }

    int getUserAction(){
        System.out.println("1. View Pending Expenses");
        System.out.println("2. Approve Pending Expenses");
        System.out.println("3. Deny Pending Expenses");
        System.out.println("4. Add Comments to Expenses");
        System.out.println("5. Generate Reports");
        System.out.println("6. Logout");
        try {
            return sc.nextInt();
        }
        catch(Exception e) {
            return -1;
        }
    }

//    void showExpenses(List<Expense> expenses){
//        if (expenses.isEmpty()){
//            System.out.println("No expenses found");
//        }
//        else {
//            System.out.println("Expenses:");
//            System.out.printf("%6s | %5s | %5s | %12s | %25s | %25s | %10s%n",
//                    "Option", "ID", "User", "Amount", "Description", "Category", "Date");
//            String dashes = String.format("%110s", "").replace(' ', '-');
//            System.out.printf("%s%n", dashes);
//            int i = 1;
//            for (Expense e : expenses) {
//                System.out.printf("%6s | %5s | %5s | %,12.2f | %25.25s | %25.25s | %10s%n",
//                        i,
//                        e.getExpenseId(),
//                        e.getUserId(),
//                        e.getAmount(),
//                        e.getDescription(),
//                        e.getCategory(),
//                        e.getDate()
//                );
//                i++;
//            }
//        }
//    }

    String wrap(String text, int max){
        if(text != null) {
            String[] words = text.split(" ");
            StringBuilder sb = new StringBuilder();
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < words.length; i++) {
                if (line.length() + words[i].length() <= max) {
                    line.append(words[i]);
                    line.append(" ");
                } else {
                    sb.append(line).append("\n");
                    line.setLength(0);
                }
                if (i == words.length - 1 && !line.isEmpty()) {
                    sb.append(line);
                }
            }
            return sb.toString();
        }
        return "";
    }

    void showExpenses(List<Expense> expenses){
        if (expenses.isEmpty()){
            System.out.println("No expenses found");
        }
        else {
            String table = AsciiTable.getTable(expenses, Arrays.asList(
                    new Column().header("#").with(e -> String.valueOf(expenses.indexOf(e))),
                    new Column().header("ID").with(e -> String.valueOf(e.getExpenseId())),
                    new Column().header("User").with(e -> String.valueOf(e.getUserId())),
                    new Column().header("Amount").with(e -> String.valueOf(e.getAmount())),
                    new Column().header("Category").with(Expense::getCategory),
                    new Column().header("Date").with(Expense::getDateString),
                    new Column().header("Description").with(e -> wrap(e.getDescription(), 40))
            ));
            System.out.println(table);
        }
    }


//    void showApprovals(List<Approval> approvals){
//        if(approvals.isEmpty()){
//            System.out.println("No approvals found");
//        }
//        else {
//            System.out.println("Approvals:");
//            System.out.printf("%6s | %12s | %10s | %10s | %-10s%n", "Option", "Amount", "Comment", "Date", "Reviewer");
//            String dashes = String.format("%60s", "").replace(' ', '-');
//            System.out.printf("%s%n", dashes);
//            int i = 1;
//            for (Approval a : approvals) {
//                System.out.printf("%6s | %,12.2f | %10s | %10s | %10s%n",
//                        i,
//                        a.getExpenseAmount(),
//                        a.getComment(),
//                        a.getDate(),
//                        a.getUserId()
//                );
//                i++;
//            }
//        }
//    }
    void showApprovals(List<Approval> approvals){
        if(approvals.isEmpty()){
            System.out.println("No approvals found");
        }
        else {
            System.out.println("Approvals:");
            String table = AsciiTable.getTable(approvals, Arrays.asList(
                    new Column().header("#").with(a -> String.valueOf(approvals.indexOf(a))),
                    new Column().header("Amount").with(a -> String.valueOf(a.getExpenseAmount())),
                    new Column().header("Comment").with(a -> wrap(a.getComment(), 40)),
                    new Column().header("Reviewer").with(a->String.valueOf(a.getUserId())),
                    new Column().header("Date").with(Approval::getDateString)
            ));
            System.out.println(table);
        }
    }

    void viewPendingExpenses(){
        List<Expense> expenses = mf.viewPendingExpenses();
        showExpenses(expenses);
    }

    void approvePendingExpenses(boolean approve, int userId){
        List<Expense> expenses = mf.viewPendingExpenses();
        showExpenses(expenses);
        if(approve && !expenses.isEmpty()) {
            System.out.println("Please enter the option you would like to approve:");
        }
        else if(!approve && !expenses.isEmpty()) {
            System.out.println("Please enter the option you would like to deny:");
        }
        try {
            if(!expenses.isEmpty()) {
                int option = sc.nextInt();
                sc.nextLine(); // really need a buffer else loop
                if (option > expenses.size()) {
                    System.out.println("Invalid option. Please try again.");
                } else {
                    int id = expenses.get(option - 1).getExpenseId();
                    if (approve) {
                        mf.approveExpenses(id, userId);
                    } else {
                        mf.denyExpenses(id, userId);
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println("Please enter a number");
            sc.nextLine();
            approvePendingExpenses(approve, userId);
        }


    }

    void addComment(){
        List<Approval> approvals = mf.viewAllApprovals();
        showApprovals(approvals);
        System.out.println("Please select an expense to add comment on");
        try{
            int option = sc.nextInt();
            if(option > approvals.size()){
                throw new IllegalArgumentException("Please choose an listed option number");
            }
            sc.nextLine(); //read the \n away
            int id = approvals.get(option-1).getExpenseId();
            System.out.println("Comment");
            String comment = sc.nextLine();
            if(comment.isEmpty()){
                throw new IllegalArgumentException("Comment cannot be empty");
            }
            mf.addComment(id, comment);
        }
        catch(InputMismatchException e) {
            System.out.println("Please enter a number");
            sc.nextLine();
            addComment();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            addComment();
        }

    }

    void generateByCategory(){
        System.out.println("Please enter a number from the following:");
        List<String> categories =  mf.getCategories();
        for(int i=1; i<categories.size()+1; i++){
            System.out.println(i + ". " +  categories.get(i-1));
        }
        try {
            int cat = sc.nextInt();
            if (cat - 1 > categories.size() || cat < 0) {
                System.out.println("Invalid category. Please try again.");
                generateByCategory();
            } else {
                String category = categories.get(cat - 1);
                showExpenses(mf.generateReportByCategory(category));
            }
        }
        catch(InputMismatchException e) {
            System.out.println("Please enter a number");
            sc.nextLine();
            generateByCategory();
        }
    }

    void generateByUser(){
        System.out.println("Please enter an employee from the following:");
        List<Integer> employees = mf.getEmployees();
        for(int i=0; i<employees.size(); i++){
            System.out.printf("%5s.%5s%n", i+1, employees.get(i));
        }
        try {
            int employee = sc.nextInt();
            if (employee > employees.size()) {
                System.out.println("Please choose an listed option number");
                generateByUser();
            } else {
                showExpenses(mf.generateReportForEmployee(employee));
            }
        } catch(InputMismatchException e) {
            System.out.println("Please enter a number");
            sc.nextLine();
            generateByUser();
        }
    }

    void generateByDate(){
        System.out.println("Please enter the date in yyyy-mm-dd:");
        String date = sc.nextLine();
        try {
            showExpenses(mf.generateReportByDate(date));
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            generateByDate();
        }
    }

    void generateReports(){
        System.out.println("1. Generate Reports by Category");
        System.out.println("2. Generate Reports by Date");
        System.out.println("3. Generate Reports by Employee");

        try{
            int option = sc.nextInt();
            sc.nextLine(); // skip \n
            switch(option){
                case 1: generateByCategory(); break;
                case 2: generateByDate(); break;
                case 3: generateByUser(); break;
                default:
                    throw new IllegalArgumentException("Please enter a valid option");
            }
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            generateReports();
        }
    }

    public static void main(String[] args) {
        int user = 0;
        Properties prop;
        ManagerConsoleRun mcr = new ManagerConsoleRun();
        sc =  new Scanner(System.in);

        try {
            prop = PropertiesFile.createManagerDBProperties();
            //Properties prop = PropertiesFile.loadManagerDBProperties();\
            if(prop == null){ throw new Exception("Properties file could not be obtain."); }
            conn = new Connect().getConnection(prop);
            ManagerDBFunctions mdb = new ManagerDBFunctions(conn);
            mf = new ManagerFunctions(mdb);
        }catch(Exception e){
            System.out.println("Connection Failed!");
            user = -1;
        }

        while(user != -1){
            if(user == 0){
                System.out.println("Please select an option number");
                user = mcr.getUser();
            }
            else{
                System.out.println("Welcome Manager");
                int userActions = mcr.getUserAction();
                switch (userActions){
                    case 1: mcr.viewPendingExpenses(); break;
                    case 2: mcr.approvePendingExpenses(true, user); break;
                    case 3: mcr.approvePendingExpenses(false, user); break;
                    case 4: mcr.addComment(); break;
                    case 5: mcr.generateReports(); break;
                    case 6:
                        user=0;
                        sc.nextLine();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        sc.nextLine();
                        break;
                }

            }
        }

    }
}
