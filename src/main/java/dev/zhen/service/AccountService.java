package dev.zhen.service;

import dev.zhen.entities.Account;

import java.util.Set;

public interface AccountService {

    Account createAccount(int clientId, Account account);
    Set<Account> getAllAccounts();
    Set<Account> getAllAccountsByClientId(int clientId);
    Account getAccountById(int id);
    Account updateAccountBalance(int id, double amount);
    boolean deleteAccountById(int id);

}
