package dev.zhen.service;

import dev.zhen.daos.AccountDAO;
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
    public Set<Account> getAllAccountsByBalance(int clientId, double min, double max) {
        return accountDAO.getAllAccountsByBalance(clientId, min, max);
    }

    @Override
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    @Override
    public Account updateAccount(int id, Account updateAccount) {
        return accountDAO.updateAccount(id, updateAccount);
    }

    @Override
    public boolean deleteAccountById(int id) {
        return accountDAO.deleteAccountById(id);
    }
}
