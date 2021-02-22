package dev.zhen.serviceTest;

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
    }

    @Test
    @Order(1)
    void update_account() {
        double oldBalance = account.getBalance();
        Account updateAccount = new Account(0,0,oldBalance + 11, true,0);
        accountService.updateAccount(account.getAccountId(), updateAccount);
        Assertions.assertEquals(11 + oldBalance, account.getBalance());
    }

//    @Test
//    @Order(2)
//    void get_all_account() {
//        Set<Account> allAccount = new HashSet<>(accountService.getAllAccountsByBalance(1, , ));
//        Assertions.assertEquals(1, allAccount.size());
//    }

    @Test
    @Order(3)
    void delete_account_by_id() {
        Set<Account> allAccount = new HashSet<>(accountService.getAllAccounts());
        int sizeBeforeDelete = allAccount.size();
        boolean isDeleted = accountService.deleteAccountById(account.getAccountId());
        int sizeAfterDelete = accountService.getAllAccounts().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeAfterDelete + 1, sizeBeforeDelete);
    }


}
