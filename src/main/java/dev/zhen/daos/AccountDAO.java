package dev.zhen.daos;

import dev.zhen.entities.Account;

import java.util.Set;

public interface AccountDAO {

    Account createAccount(int clientId, Account account);
    Set<Account> getAllAccounts();
    Set<Account> getAllAccountsByClientId(int clientId);
    Set<Account> getAllAccountsByBalance(int clientId, double min, double max);
    Account getAccountById(int id);
    Account updateAccount(int id, Account account);
    boolean deleteAccountById(int id);

}
