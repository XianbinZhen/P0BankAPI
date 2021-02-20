package dev.zhen.daos;

import dev.zhen.entities.Account;
import dev.zhen.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDaoPostgres implements AccountDAO{

    private static Logger logger = Logger.getLogger(AccountDaoPostgres.class.getName());

    @Override
    public Account createAccount(int clientId, Account account) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            account.setCreatedDate(System.currentTimeMillis()/1000);
            account.setClientId(clientId);
            String sql = "insert into account (client_id, balance, created_date, is_checking) values (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, clientId);
            preparedStatement.setDouble(2, account.getBalance());
            preparedStatement.setLong(3, account.getCreatedDate());
            preparedStatement.setBoolean(4, account.isCheckingAccount());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            account.setAccountId(resultSet.getInt("account_id"));
            return account;
        } catch (SQLException sqlException) {
            logger.error("Failed to create account", sqlException);
            return null;
        }
    }

    @Override
    public Set<Account> getAllAccounts() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Account> allAccounts = new HashSet<>();
            while (resultSet.next()) {
                Account account = new Account(0);
                int accountId = resultSet.getInt("account_id");
                int clientId = resultSet.getInt("client_id");
                double balance = resultSet.getDouble("balance");
                long createdDate =  resultSet.getLong("created_date");
                boolean isChecking = resultSet.getBoolean("is_checking");
                account.setAccountId(accountId);
                account.setClientId(clientId);
                account.setBalance(balance);
                account.setCreatedDate(createdDate);
                account.setCheckingAccount(isChecking);
                allAccounts.add(account);
            }
            if (allAccounts.size() > 0)
                return allAccounts;
            else
                return null;
        } catch (SQLException sqlException) {
            logger.error("Failed to get all accounts", sqlException);
            return null;
        }
    }

    @Override
    public Set<Account> getAllAccountsByClientId(int clientId) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from account where client_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Account> allAccounts = new HashSet<>();
            while (resultSet.next()) {
                Account account = new Account(clientId);
                int accountId = resultSet.getInt("account_id");
                double balance = resultSet.getDouble("balance");
                long createdDate =  resultSet.getLong("created_date");
                boolean isChecking = resultSet.getBoolean("is_checking");
                account.setAccountId(accountId);
                account.setBalance(balance);
                account.setCreatedDate(createdDate);
                account.setCheckingAccount(isChecking);
                allAccounts.add(account);
            }
            if (allAccounts.size() > 0)
                return allAccounts;
            else
                return null;
        } catch (SQLException sqlException) {
            logger.error("Failed to get all accounts by client id", sqlException);
            return null;
        }
    }

    @Override
    public Account getAccountById(int id) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql ="select * from account where account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account(0);
                int accountId = resultSet.getInt("account_id");
                int clientId = resultSet.getInt("client_id");
                double balance = resultSet.getDouble("balance");
                long createdDate =  resultSet.getLong("created_date");
                boolean isChecking = resultSet.getBoolean("is_checking");
                account.setAccountId(accountId);
                account.setClientId(clientId);
                account.setBalance(balance);
                account.setCreatedDate(createdDate);
                account.setCheckingAccount(isChecking);
                return account;
            } else
                return null;
        } catch (SQLException sqlException) {
            logger.error("Failed to get account", sqlException);
            return null;
        }
    }

    @Override
    public Account updateAccount(int id, Account updateAccount) {
        Account account = getAccountById(id);
        if (account == null || updateAccount == null) {
            return null;
        } else {
            double newBalance = updateAccount.getBalance();
            if (newBalance < 0) {
                return null;
            } else {
                try (Connection connection = ConnectionUtil.createConnection()) {
                    String sql = "update account set balance = ? where account_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setDouble(1,newBalance);
                    preparedStatement.setInt(2, id);
                    preparedStatement.execute();
                    account.setBalance(newBalance);
                    return account;
                } catch (SQLException sqlException) {
                    logger.error("Failed to update account", sqlException);
                    return null;
                }
            }
        }

    }

    @Override
    public boolean deleteAccountById(int id) {
        Account account = getAccountById(id);
        if (account != null) {
            try (Connection connection = ConnectionUtil.createConnection()) {
                String sql ="delete from account where account_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                return true;
            } catch (SQLException sqlException) {
                logger.error("Failed to delete account", sqlException);
                return false;
            }
        } else {
            return false;
        }

    }
}
