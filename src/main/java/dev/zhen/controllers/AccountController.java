package dev.zhen.controllers;

import com.google.gson.Gson;
import dev.zhen.entities.Account;
import dev.zhen.service.AccountService;
import io.javalin.http.Handler;

import java.util.Set;


public class AccountController {

    private AccountService accountService = null;
    private static Gson gson = new Gson();

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
        Set<Account> allAccount = accountService.getAllAccounts();
        if (allAccount != null) {
            ctx.result(gson.toJson(allAccount));
            ctx.status(200);
        } else {
            ctx.result("Account not found");
            ctx.status(404);
        }
    };

    public Handler getAllAccountsByClientIdHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("cid"));
        Set<Account> allAccount = accountService.getAllAccountsByClientId(id);
        if (allAccount != null) {
            ctx.result(gson.toJson(allAccount));
            ctx.status(200);
        } else {
            ctx.result("Account not found");
            ctx.status(404);
        }
    };
    public Handler getAccountByIdHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("aid"));
        Account account = accountService.getAccountById(id);
        if (account != null) {
            ctx.result(gson.toJson(account));
            ctx.status(200);
        } else {
            ctx.result("Account not found");
            ctx.status(404);
        }
    };
    public Handler updateAccountHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("aid"));
        Account account = gson.fromJson(ctx.body(), Account.class);
        if (account != null) {
            account = accountService.updateAccount(id, account);
            if (account == null) {
                ctx.result("Account not found");
                ctx.status(404);
            } else {
                ctx.result(gson.toJson(account));
                ctx.status(200);
            }
        } else {
            ctx.result("Bad request");
            ctx.status(400);
        }
    };

    public Handler deleteAccountByIdHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("aid"));
        boolean isDeleted = accountService.deleteAccountById(id);
        if (isDeleted) {
            ctx.result("Account is deleted");
            ctx.status(200);
        } else {
            ctx.result("Failed to delete account");
            ctx.status(404);
        }
    };
}
