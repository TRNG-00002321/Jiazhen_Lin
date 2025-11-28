package DAO;
import Model.Approval;
import Model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ManagerDBFunctions{

    private final Connection conn;
    public ManagerDBFunctions(Connection connection){
        this.conn = connection;
    }

    List<Expense> parseExpenseFromResult(ResultSet rs) throws SQLException {
        List<Expense> expenses = new ArrayList<>();
        while(rs.next()){
            Expense expense = new Expense(rs.getInt("id"),
                    rs.getInt("user_id"), rs.getDouble("amount"),
                    rs.getString("description"), rs.getDate("date"));
            expenses.add(expense);
        }
        return expenses;
    }

    List<Approval> parseApprovalFromResult(ResultSet rs) throws SQLException {
        List<Approval> approvals = new ArrayList<>();
        while(rs.next()){
            Approval approval = new Approval(rs.getInt("id"),
                        rs.getInt("expense_id"), rs.getDouble("amount"), rs.getInt("reviewer"),
                      rs.getString("comment"), rs.getDate("review_date") );
            approvals.add(approval);
        }
        return approvals;
    }

    public boolean checkUsernameExists(String username){
        String query = "SELECT * FROM users WHERE username = ?";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkExpenseIsPending(int expenseId){
        String query = "SELECT * FROM approvals WHERE expense_id = ? AND status = 'pending'";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, expenseId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Object[] login(String username){
        String query = "SELECT id, password FROM users WHERE username = ? AND role = 'manager'";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            Object[] results = new Object[2];
            if(rs.next()) {
                results[0] = rs.getInt(1);
                results[1] = rs.getString(2);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void newManager(String username, String password) {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, 'manager')";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Expense> viewPendingExpenses(){
        String query = "SELECT * FROM expenses WHERE id IN " +
                "(SELECT expense_id FROM approvals WHERE status = 'pending')";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return parseExpenseFromResult(rs);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Approval> viewAllApprovals(){
        String query = "SELECT approvals.*, expenses.amount FROM Approvals " +
                "JOIN expenses ON Approvals.expense_id = expenses.id ";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            return parseApprovalFromResult(rs);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Approval> viewUserApprovals(int userId){
        String query = "SELECT approvals.*, expenses.amount FROM Approvals join expenses " +
                "ON Approvals.expense_id = expenses.id  WHERE reviewer = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return parseApprovalFromResult(rs);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<String> getCategories(){
        String query = "SELECT DISTINCT description FROM expenses";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            List<String> cat =  new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                cat.add(rs.getString(1));
            }
            return cat;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Integer> getEmployees(){
        String query = "SELECT DISTINCT user_id FROM expenses";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            List<Integer> users =  new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                users.add(rs.getInt(1));
            }
            return users;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Expense> generateReportForEmployee(int userId){
        String query = "SELECT * FROM expenses WHERE user_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            return parseExpenseFromResult(ps.executeQuery());
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Expense> generateReportByDate(Date date){
        String query = "SELECT * FROM expenses WHERE date = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, String.valueOf(date));
            return parseExpenseFromResult(ps.executeQuery());
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Expense> generateReportByCategory(String category){
        String query = "SELECT * FROM expenses WHERE description = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, category);
            return parseExpenseFromResult(ps.executeQuery());
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void approveExpenses(int expenseId, int userId){
        String query = "UPDATE approvals SET status = 'approved', reviewer = ?, review_date = CURRENT_DATE  WHERE expense_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, expenseId);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void denyExpenses(int expenseId, int userId){
        String query = "UPDATE approvals SET status = 'denied', reviewer = ?, review_date = CURRENT_DATE WHERE expense_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, expenseId);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void addComment(int expenseId, String comment){
        String query = "UPDATE approvals SET comment = ? WHERE expense_id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, comment);
            ps.setInt(2, expenseId);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
