package dev.zhen.entities;

public class Account {
    private int accountId;
    private int clientId;
    private double balance;
    // true = checking, false = saving account
    private boolean isCheckingAccount;
    private long createdDate;

    public Account(int clientId) {
        this.setClientId(clientId);
    }

    public Account(int accountId, int clientId, double balance, boolean isCheckingAccount, long createdDate) {
        this.setAccountId(accountId);
        this.setClientId(clientId);
        this.setBalance(balance);
        this.setCheckingAccount(isCheckingAccount);
        this.setCreatedDate(createdDate);
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isCheckingAccount() {
        return isCheckingAccount;
    }

    public void setCheckingAccount(boolean checkingAccount) {
        isCheckingAccount = checkingAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + getAccountId() +
                ", clientId=" + getClientId() +
                ", balance=" + getBalance() +
                ", isCheckingAccount=" + isCheckingAccount() +
                ", createdDate=" + getCreatedDate() +
                '}';
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
