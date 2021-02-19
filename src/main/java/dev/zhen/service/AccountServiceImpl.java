package dev.zhen.service;

import dev.zhen.daos.AccountDAO;
import dev.zhen.daos.AccountDaoLoacal;
import dev.zhen.entities.Account;

import java.util.Set;

public class AccountServiceImpl implements AccountService{

    private AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Account createAccount(int clientId, Account account) {
        return accountDAO.createAccount(clientId,account);
    }

    @Override
    public Set<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @Override
    public Set<Account> getAllAccountsByClientId(int clientId) {
        return accountDAO.getAllAccountsByClientId(clientId);
    }

    @Override
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    @Override
    public Account updateAccountBalance(int id, double amount) {
        return accountDAO.updateAccountBalance(id, amount);
    }

    @Override
    public boolean deleteAccountById(int id) {
        return accountDAO.deleteAccountById(id);
    }
}
