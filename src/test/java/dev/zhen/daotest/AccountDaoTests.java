package dev.zhen.daotest;

import dev.zhen.daos.*;
import dev.zhen.entities.Account;
import dev.zhen.entities.Client;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountDaoTests {
//    private static Logger logger = Logger.getLogger(AccountDaoTests.class.getName());
    private static AccountDAO accountDAO = new AccountDaoPostgres();
    private static ClientDAO clientDAO = new ClientDaoPostgres();
    private static Account account = null;
    private static Client client = null;

    @Test
    @Order(0)
    void create_account() {
        client = clientDAO.createClient(new Client(0,"Larry", "Bird",0));
        Account account1 = new Account(0,0,0, true, 0);
        account = accountDAO.createAccount(client.getId(), account1);
        long createdDate = account.getCreatedDate();
        Assertions.assertEquals(System.currentTimeMillis() / 1000, account1.getCreatedDate(), 100);
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
        Account updateAccount = new Account(0,0,oldBalance + 11,true,0);
        Account account4 = accountDAO.updateAccount(account.getAccountId(), updateAccount);
        Assertions.assertEquals(oldBalance + 11, account4.getBalance());
    }

    @Test
    @Order(4)
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
        Account account4 =  new Account(0,0,120, true, 0);
        accountDAO.createAccount(client.getId(), account4);
        allAccount = accountDAO.getAllAccounts();
        Assertions.assertEquals(allAccount.size(), sizeBefore + 1);
    }


    @Test
    @Order(5)
    void delete_account_by_id() {
        Set<Account> allAccount = accountDAO.getAllAccounts();
        int sizeBeforeDelete = allAccount.size();
        boolean isDeleted = accountDAO.deleteAccountById(account.getAccountId());
        int sizeAfterDelete = accountDAO.getAllAccounts().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeAfterDelete + 1, sizeBeforeDelete);
    }
}
