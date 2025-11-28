package Controller;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.sql.Connection;

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
                System.out.println("Manage login:");
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

    void showExpenses(List<Expense> expenses){
        System.out.println("Expenses:");
        System.out.printf("%6s | %5s | %5s | %12s | %10s | %-10s%n", "Option", "ID", "User", "Amount", "Category", "Date");
        String dashes = String.format("%60s", "").replace(' ', '-');
        System.out.printf("%s%n", dashes);
        int i=1;
        for (Expense e : expenses) {
            System.out.printf("%6s | %5s | %5s | %,12.2f | %10s | %10s%n",
                    i,
                    e.getExpenseId(),
                    e.getUserId(),
                    e.getAmount(),
                    e.getCategory(),
                    e.getDate()
            );
            i++;
        }
    }

    void showApprovals(List<Approval> approvals){
        System.out.println("Approvals:");
        System.out.printf("%6s | %12s | %10s | %10s | %-10s%n", "Option", "Amount", "Comment", "Date", "Reviewer");
        String dashes = String.format("%60s", "").replace(' ', '-');
        System.out.printf("%s%n", dashes);
        int i=1;
        for (Approval a : approvals) {
            System.out.printf("%6s | %,12.2f | %10s | %10s | %10s%n",
                    i,
                    a.getExpenseAmount(),
                    a.getComment(),
                    a.getDate(),
                    a.getUserId()
            );
            i++;
        }
    }

    void viewPendingExpenses(){
        List<Expense> expenses = mf.viewPendingExpenses();
        showExpenses(expenses);
    }

    void approvePendingExpenses(boolean approve, int userId){
        List<Expense> expenses = mf.viewPendingExpenses();
        showExpenses(expenses);
        if(approve) {
            System.out.println("Please enter the option you would like to approve:");
        }
        else {
            System.out.println("Please enter the option you would like to deny:");
        }
        try {
            int option = sc.nextInt();
            sc.nextLine(); // really need a buffer else loop
            if(option > expenses.size()){
                System.out.println("Invalid option. Please try again.");
            }
            else{
                int id = expenses.get(option-1).getExpenseId();
                if (approve){
                    mf.approveExpenses(id, userId);
                }
                else{
                    mf.denyExpenses(id, userId);
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
            sc.nextLine(); //read the \n away
            int id = approvals.get(option-1).getExpenseId();
            System.out.println("Comment");
            String comment = sc.nextLine();
            mf.addComment(id, comment);
        }
        catch(InputMismatchException e) {
            System.out.println("Please enter a number");
            sc.nextLine();
            addComment();
        } catch (Exception e) {
            System.out.println("Please add a comment");
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
                case 1:
                System.out.println("Please enter a category from the following:");
                for(String cat: mf.getCategories()){
                    System.out.println(cat);
                }
                String category = sc.nextLine();
                showExpenses(mf.generateReportByCategory(category)); break;
                case 2:

                System.out.println("Please enter the date:");
                String date = sc.nextLine();
                showExpenses(mf.generateReportByDate(date)); break;
                case 3:
                System.out.println("Please enter an employee from the following:");
                for(int id: mf.getEmployees()){
                    System.out.println(id);
                }
                int employee = sc.nextInt();
                showExpenses(mf.generateReportForEmployee(employee)); break;
                default:
            }
        }catch(Exception e){
            System.out.println("Please enter a valid option");
            sc.nextLine();
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
                        System.out.println("Invalid option. Please try again");
                        sc.nextLine();
                        break;
                }

            }
        }

    }
}
