package dev.zhen.serviceTest;

import dev.zhen.daos.AccountDAO;
import dev.zhen.daos.AccountDaoLoacal;
import dev.zhen.entities.Account;
import dev.zhen.service.AccountService;
import dev.zhen.service.AccountServiceImpl;
import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTests {

    private static AccountService accountService = new AccountServiceImpl(new AccountDaoLoacal());
    private static Account account = null;

    @Test
    @Order(0)
    void create_account() {
        Account account1 = new Account(0,0,0, true, 0);
        accountService.createAccount(1, account1);
        account = account1;
        Assertions.assertEquals(1, account1.getAccountId());
        Assertions.assertEquals(System.currentTimeMillis() / 1000, account1.getCreatedDate(), 100);
        System.out.println("TEST 0 passed: create account");
    }

    @Test
    @Order(1)
    void update_account() {
        double oldBalance = account.getBalance();
        accountService.updateAccountBalance(account.getAccountId(), 100);
        Assertions.assertEquals(100 + oldBalance, account.getBalance());
        System.out.println("TEST 1 passed: Update account");
    }

    @Test
    @Order(2)
    void get_all_account() {
        Set<Account> allAccount = new HashSet<>(accountService.getAllAccountsByClientId(1));
        Assertions.assertEquals(1, allAccount.size());
        System.out.println("TEST 2 passed: Get all account");
    }

    @Test
    @Order(3)
    void delete_account_by_id() {
        Set<Account> allAccount = new HashSet<>(accountService.getAllAccounts());
        int sizeBeforeDelete = allAccount.size();
        boolean isDeleted = accountService.deleteAccountById(account.getAccountId());
        int sizeAfterDelete = accountService.getAllAccounts().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeAfterDelete + 1, sizeBeforeDelete);
        System.out.println("TEST 3 passed: Delete account by id");

    }


}
