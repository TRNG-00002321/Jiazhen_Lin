package Model;

import java.util.Date;

public class Approval {
    private int id;
    private int expenseId;
    private double expenseAmount;
    private int userId;
    private String comment;
    private Date date;

    public Approval(int id, int expenseId, double expenseAmount, int userId, String comment, Date date) {
        this.id = id;
        this.expenseId = expenseId;
        this.expenseAmount = expenseAmount;
        this.userId = userId;
        this.comment = comment;
        this.date = date;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public String getComment() {
        return comment;
    }

    public Date getDate() {
        return date;
    }
    public String getDateString() {
        if (date == null) {
            return "";
        }
        return date.toString();
    }
    public void setExpenseId(int expenseId) {}

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }
}

