package Service;

import Model.Approval;
import Model.Expense;
import DAO.ManagerDBFunctions;
import org.mindrot.jbcrypt.BCrypt;  //dependency in pom file

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.*;

public class ManagerFunctions {
    static final Logger logger = Logger.getLogger(ManagerFunctions.class.getName());
    static {
        try {
            FileHandler fh = new FileHandler("ManagerActions.log", true);

            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.INFO);

            logger.setUseParentHandlers(false); //turn off print to console
            logger.addHandler(fh);
            logger.setLevel(Level.INFO);

        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

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
            logger.info("New manager: " + username + " has been successfully created");
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
            logger.info("Expense ID " + expenseId + " has been approved by user ID " + userId);
        }
        else{
            throw new RuntimeException("Expense is not pending!");
        }
    }

    public void denyExpenses(int expenseId, int userId){
        if(managerDBFunctions.checkExpenseIsPending(expenseId)) {
            managerDBFunctions.denyExpenses(expenseId, userId);
            logger.info("Expense ID " + expenseId + " has been denied by user ID " + userId);
        }
        else{
            throw new RuntimeException("Expense is not pending!");
        }
    }

    public void addComment(int expenseId, String comment){
        managerDBFunctions.addComment(expenseId, comment);
    }

}
