package Model;

import java.util.Date;

public class Expense {
    private int expenseId;
    private int userId;
    private double amount;
    private String category;
    private Date date;

    public Expense(int expenseId, int userId, double amount, String category, Date date){
        this.expenseId = expenseId;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expenseId=" + expenseId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getExpenseId() {
        return expenseId;
    }
    public int getUserId() {
        return userId;
    }
    public String getCategory() {
        return category;
    }
    public double getAmount() {
        return amount;
    }
    public Date getDate() {
        return date;
    }

}
