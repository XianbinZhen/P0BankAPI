package dev.zhen.daotest;

import dev.zhen.daos.*;
import dev.zhen.entities.Account;
import dev.zhen.entities.Client;
import org.junit.jupiter.api.*;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTests {

    private static AccountDAO accountDAO = new AccountDaoPostgres();
    private static ClientDAO clientDAO = new ClientDaoPostgres();
    private static Account account = null;
    private static Client client = null;

    @Test
    @Order(0)
    void create_account() {
        client = clientDAO.createClient(new Client(0,"Larry", "Bird",0));
        Account account1 = new Account(0,0,100, true, 0);
        account = accountDAO.createAccount(client.getId(), account1);
        long createdDate = account.getCreatedDate();
        Assertions.assertEquals(System.currentTimeMillis() / 1000, createdDate, 100);
        int firstId = account.getAccountId();
        Account account2 = accountDAO.createAccount(client.getId(), account1);
        Assertions.assertEquals(firstId + 1, account2.getAccountId());
    }

    @Test
    @Order(1)
    void get_account_by_id() {
        Account account2 = accountDAO.getAccountById(account.getAccountId());
        Assertions.assertEquals(account2.getAccountId(), account.getAccountId());
        Assertions.assertEquals(account2.getCreatedDate(), account.getCreatedDate());
    }

    @Test
    @Order(2)
    void update_account() {
        double oldBalance = account.getBalance();
        Account updateAccount = new Account(0,client.getId(),oldBalance + 11,false,0);
        Account account4 = accountDAO.updateAccount(account.getAccountId(), updateAccount);
        Assertions.assertEquals(oldBalance + 11, account4.getBalance());
        Assertions.assertEquals(0, account4.getCreatedDate());
        Assertions.assertFalse(account4.isCheckingAccount());
    }

    @Test
    @Order(3)
    void get_all_accounts_by_client_id() {
        Set<Account> allAccount = accountDAO.getAllAccountsByClientId(client.getId());
        int sizeBefore = allAccount.size();
        Account account4 =  new Account(0,0,120, true, 0);
        accountDAO.createAccount(client.getId(), account4);
        allAccount = accountDAO.getAllAccountsByClientId(client.getId());
        Assertions.assertEquals(allAccount.size(), sizeBefore + 1);
    }
    @Test
    @Order(4)
    void get_all_accounts() {
        Set<Account> allAccount = accountDAO.getAllAccounts();
        int sizeBefore = allAccount.size();
        Account account4 =  new Account(0,0,110, true, 0);
        accountDAO.createAccount(client.getId(), account4);
        allAccount = accountDAO.getAllAccounts();
        Assertions.assertEquals(allAccount.size(), sizeBefore + 1);
    }

    @Test
    @Order(5)
    void get_all_accounts_by_balance() {
        Account account5 =  new Account(0,0,120, true, 0);
        Account account6 =  new Account(0,0,121, true, 0);
        Account account7 =  new Account(0,0,1, true, 0);
        Account account8 =  new Account(0,0,2, true, 0);
        account = accountDAO.createAccount(client.getId(), account5);
        accountDAO.createAccount(client.getId(), account6);
        accountDAO.createAccount(client.getId(), account7);
        accountDAO.createAccount(client.getId(), account8);
        Set<Account> allAccount = accountDAO.getAllAccountsByBalance(client.getId(), 2, 120);
        double min = Integer.MAX_VALUE;
        double max = Integer.MIN_VALUE;
        for (Account a : allAccount) {
            min = Math.min(min, a.getBalance());
            max = Math.max(max, a.getBalance());
        }
        Assertions.assertTrue(min >= 2);
        Assertions.assertTrue(max <= 120);
    }


    @Test
    @Order(6)
    void delete_account_by_id() {
        Set<Account> allAccount = accountDAO.getAllAccounts();
        int sizeBeforeDelete = allAccount.size();
        boolean isDeleted = accountDAO.deleteAccountById(account.getAccountId());
        int sizeAfterDelete = accountDAO.getAllAccounts().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeAfterDelete + 1, sizeBeforeDelete);
    }
}
