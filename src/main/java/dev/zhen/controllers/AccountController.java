package dev.zhen.controllers;

import com.google.gson.Gson;
import dev.zhen.entities.Account;
import dev.zhen.service.AccountService;
import io.javalin.http.Handler;

import java.util.Set;


public class AccountController {

    private AccountService accountService = null;
    private static Gson gson = new Gson();
    private static final String STR_MIN = "STR_MIN";
    private static final String STR_MAX = "STR_MAX";

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Handler createAccountHandler = ctx -> {
        Account account = gson.fromJson(ctx.body(), Account.class);
        if (account != null) {
            int clientId = Integer.parseInt(ctx.pathParam("cid"));
            Account createdAccount = accountService.createAccount(clientId, account);
            if (createdAccount != null) {
                ctx.result(gson.toJson(account));
                ctx.status(201);
            } else {
                ctx.result("Failed to create account");
                ctx.status(400);
            }
        } else {
            ctx.result("Failed to create account: body can't be empty");
            ctx.status(400);
        }
    };

    public Handler getAllAccountsHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("cid"));
        String strMax = ctx.queryParam("amountLessThan", STR_MAX);
        String strMin = ctx.queryParam("amountGreaterThan", STR_MIN);
        double min, max;
        if (strMin.equals(STR_MIN))
            min = -Double.MAX_VALUE;
        else
            min = Double.parseDouble(strMin);
        if (strMax.equals(STR_MAX))
            max = Double.MAX_VALUE;
        else
            max = Double.parseDouble(strMax);
        Set<Account> allAccount;
        if (id == 0) {
            allAccount = accountService.getAllAccounts();
        } else {
            System.out.println("min: " + min + " max: " + max);
            allAccount = accountService.getAllAccountsByBalance(id, min, max);
        }
        if (allAccount != null) {
            ctx.result(gson.toJson(allAccount));
            ctx.status(200);
        } else {
            ctx.result("Account not found");
            ctx.status(404);
        }
    };

    public Handler getAccountByIdHandler = ctx -> {
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        int id = Integer.parseInt(ctx.pathParam("aid"));
        Account account = accountService.getAccountById(id);
        if (account != null) {
            if (account.getClientId() == cid) {
                ctx.result(gson.toJson(account));
                ctx.status(200);
            } else {
                ctx.result("Unmatched client and account");
                ctx.status(404);
            }

        } else {
            ctx.result("Account not found");
            ctx.status(404);
        }
    };
    public Handler updateAccountHandler = ctx -> {
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        int id = Integer.parseInt(ctx.pathParam("aid"));
        Account account = gson.fromJson(ctx.body(), Account.class);
        if (account != null) {
            account = accountService.getAccountById(id);
            if (account != null) {
                if (account.getClientId() == cid) {
                    account = accountService.updateAccount(id, account);
                    if (account == null) {
                        ctx.result("Account not found");
                        ctx.status(404);
                    } else {
                        ctx.result(gson.toJson(account));
                        ctx.status(200);
                    }
                } else {
                    ctx.result("Unmatched client and account");
                    ctx.status(404);
                }
            } else {
                ctx.result("Account not found");
                ctx.status(404);
            }
        } else {
            ctx.result("Bad request");
            ctx.status(400);
        }
    };

    public Handler deleteAccountByIdHandler = ctx -> {
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        int id = Integer.parseInt(ctx.pathParam("aid"));
        Account account = accountService.getAccountById(id);
        if (account != null) {
            if (account.getClientId() == cid) {
                boolean isDeleted = accountService.deleteAccountById(id);
                if (isDeleted) {
                    ctx.result("Account is deleted");
                    ctx.status(200);
                } else {
                    ctx.result("Failed to delete account");
                    ctx.status(404);
                }
            } else {
                ctx.result("Unmatched client and account");
                ctx.status(404);
            }
        } else {
            ctx.result("Account not found");
            ctx.status(404);
        }

    };
}
