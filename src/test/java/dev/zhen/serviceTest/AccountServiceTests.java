package dev.zhen.serviceTest;

import dev.zhen.daos.*;
import dev.zhen.entities.Account;
import dev.zhen.entities.Client;
import dev.zhen.service.AccountService;
import dev.zhen.service.AccountServiceImpl;
import dev.zhen.service.ClientService;
import dev.zhen.service.ClientServiceImpl;
import org.junit.jupiter.api.*;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTests {

    private static AccountService accountService = new AccountServiceImpl(new AccountDaoPostgres());
    private static ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private static Account account = null;
    private static Client client = null;



    @Test
    @Order(0)
    void create_account() {
        client = clientService.createClient(new Client(0,"Larry", "Bird",0));
        Account account1 = new Account(0,0,100, true, 0);
        account = accountService.createAccount(client.getId(), account1);
        long createdDate = account.getCreatedDate();
        Assertions.assertEquals(System.currentTimeMillis() / 1000, createdDate, 100);
        int firstId = account.getAccountId();
        Account account2 = accountService.createAccount(client.getId(), account1);
        Assertions.assertEquals(firstId + 1, account2.getAccountId());
    }

    @Test
    @Order(1)
    void get_account_by_id() {
        Account account2 = accountService.getAccountById(account.getAccountId());
        Assertions.assertEquals(account2.getAccountId(), account.getAccountId());
        Assertions.assertEquals(account2.getCreatedDate(), account.getCreatedDate());
    }

    @Test
    @Order(2)
    void update_account() {
        double oldBalance = account.getBalance();
        Account updateAccount = new Account(0,client.getId(),oldBalance + 11,false,0);
        Account account4 = accountService.updateAccount(account.getAccountId(), updateAccount);
        System.out.println(updateAccount);
        Assertions.assertEquals(oldBalance + 11, account4.getBalance());
        Assertions.assertEquals(0, account4.getCreatedDate());
        Assertions.assertFalse(account4.isCheckingAccount());
    }

    @Test
    @Order(3)
    void get_all_accounts_by_client_id() {
        Set<Account> allAccount = accountService.getAllAccountsByClientId(client.getId());
        int sizeBefore = allAccount.size();
        Account account4 =  new Account(0,0,120, true, 0);
        accountService.createAccount(client.getId(), account4);
        allAccount = accountService.getAllAccountsByClientId(client.getId());
        Assertions.assertEquals(allAccount.size(), sizeBefore + 1);
    }
    @Test
    @Order(4)
    void get_all_accounts() {
        Set<Account> allAccount = accountService.getAllAccounts();
        int sizeBefore = allAccount.size();
        Account account4 =  new Account(0,0,110, true, 0);
        accountService.createAccount(client.getId(), account4);
        allAccount = accountService.getAllAccounts();
        Assertions.assertEquals(allAccount.size(), sizeBefore + 1);
    }

    @Test
    @Order(5)
    void get_all_accounts_by_balance() {
        Account account5 =  new Account(0,0,120, true, 0);
        Account account6 =  new Account(0,0,121, true, 0);
        Account account7 =  new Account(0,0,1, true, 0);
        Account account8 =  new Account(0,0,2, true, 0);
        account = accountService.createAccount(client.getId(), account5);
        accountService.createAccount(client.getId(), account6);
        accountService.createAccount(client.getId(), account7);
        accountService.createAccount(client.getId(), account8);
        Set<Account> allAccount = accountService.getAllAccountsByBalance(client.getId(), 2, 120);
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
        Set<Account> allAccount = accountService.getAllAccounts();
        int sizeBeforeDelete = allAccount.size();
        boolean isDeleted = accountService.deleteAccountById(account.getAccountId());
        int sizeAfterDelete = accountService.getAllAccounts().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeAfterDelete + 1, sizeBeforeDelete);
    }

}
