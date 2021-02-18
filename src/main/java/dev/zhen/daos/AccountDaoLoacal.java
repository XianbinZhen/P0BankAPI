package dev.zhen.daos;

import dev.zhen.entities.Account;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccountDaoLoacal implements AccountDAO{

    private final Map<Integer, Account> accountTable = new HashMap<>();
    private int idCounter = 0;

    @Override
    public Account createAccount(int clientId, Account account) {
        account.setAccountId(++idCounter);
        account.setClientId(clientId);
        account.setCreatedDate(System.currentTimeMillis()/1000);
        accountTable.put(idCounter, account);
        return account;
    }

    @Override
    public Set<Account> getAllAccounts() {
        return new HashSet<Account>(accountTable.values());
    }

    @Override
    public Set<Account> getAllAccountsByClientId(int clientId) {
        HashSet<Account> temp = new HashSet<>(accountTable.values());
        HashSet<Account> result = new HashSet<>();
        for(Account a : temp) {
            if (a.getClientId() == clientId) {
                result.add(a);
            }
        }
        return result;
    }

    @Override
    public Account getAccountById(int id) {
        return accountTable.get(id);
    }

    @Override
    public Account updateAccountBalance(int id, double amount) {
        Account account = accountTable.get(id);
        account.setBalance(account.getBalance() + amount);
        return account;
    }

    @Override
    public boolean deleteAccountById(int id) {
        Account account = accountTable.remove(id);
        return account != null;
    }
}