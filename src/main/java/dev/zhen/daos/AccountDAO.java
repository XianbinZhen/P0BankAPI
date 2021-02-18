package dev.zhen.daos;

import dev.zhen.entities.Account;
import dev.zhen.entities.Client;

import java.util.Set;

public interface AccountDAO {

    Account createAccount(int clientId, Account account);
    Set<Account> getAllAccounts();
    Set<Account> getAllAccountsByClientId(int clientId);
    Account getAccountById(int id);
    Account updateAccountBalance(int id, double amount);
    boolean deleteAccountById(int id);

}
