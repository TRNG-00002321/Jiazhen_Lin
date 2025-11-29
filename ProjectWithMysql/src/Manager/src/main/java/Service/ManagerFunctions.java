package Service;

import Model.Approval;
import Model.Expense;
import DAO.ManagerDBFunctions;
import org.mindrot.jbcrypt.BCrypt;  //dependency in pom file
import java.sql.Date;
import java.util.List;

public class ManagerFunctions {
    ManagerDBFunctions managerDBFunctions;

    public ManagerFunctions(ManagerDBFunctions managerDBFunctions) {
        this.managerDBFunctions = managerDBFunctions;
    }

    public int login(String username, String password) {
        try {
            Object[] results = managerDBFunctions.login(username);
            if (BCrypt.checkpw(password, results[1].toString())) {
                return (int) results[0];
            } else {
                throw new RuntimeException("Incorrect username or password");
            }
        }catch(NullPointerException e){
            throw new RuntimeException("Incorrect username or password");

        }

    }

    public void newManager(String username, String password) {
        if(!managerDBFunctions.checkUsernameExists(username)) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            managerDBFunctions.newManager(username, hashedPassword);
        }
        else{
            throw new RuntimeException("Username already exists!");
        }
    }

    public List<Expense> viewPendingExpenses(){
        return managerDBFunctions.viewPendingExpenses();
    }

    public List<Integer> getEmployees(){
        return managerDBFunctions.getEmployees();
    }

    public List<Expense> generateReportForEmployee(int userId){
        return managerDBFunctions.generateReportForEmployee(userId);
    }

    public List<Expense> generateReportByDate(String date){
        try{
            Date date1 = Date.valueOf(date);
            return managerDBFunctions.generateReportByDate(date1);
        } catch (Exception e) {
            throw new IllegalArgumentException("Not a date or Incorrect format");
        }

    }

    public List<String> getCategories(){
        return managerDBFunctions.getCategories();
    }

    public List<Expense> generateReportByCategory(String category){
        return managerDBFunctions.generateReportByCategory(category);
    }

    public List<Approval> viewAllApprovals(){
        return managerDBFunctions.viewAllApprovals();
    }

    public List<Approval> viewUserApprovals(int userId){
        return managerDBFunctions.viewUserApprovals(userId);
    }

    public void approveExpenses(int expenseId,int userId){
        if(managerDBFunctions.checkExpenseIsPending(expenseId)) {
            managerDBFunctions.approveExpenses(expenseId,  userId);
        }
        else{
            throw new RuntimeException("Expense is not pending!");
        }
    }

    public void denyExpenses(int expenseId, int userId){
        if(managerDBFunctions.checkExpenseIsPending(expenseId)) {
            managerDBFunctions.denyExpenses(expenseId, userId);
        }
        else{
            throw new RuntimeException("Expense is not pending!");
        }
    }

    public void addComment(int expenseId, String comment){
        managerDBFunctions.addComment(expenseId, comment);
    }

}
