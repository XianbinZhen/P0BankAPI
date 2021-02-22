package dev.zhen.app;

import dev.zhen.controllers.AccountController;
import dev.zhen.controllers.ClientController;
import dev.zhen.daos.AccountDaoPostgres;
import dev.zhen.daos.ClientDAO;
import dev.zhen.daos.ClientDaoPostgres;
import dev.zhen.service.AccountServiceImpl;
import dev.zhen.service.ClientService;
import dev.zhen.service.ClientServiceImpl;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {

        Javalin app = Javalin.create();
        ClientDAO clientDAO = new ClientDaoPostgres();
        ClientService clientService = new ClientServiceImpl(clientDAO);
        ClientController clientController =  new ClientController(clientService);
        AccountController accountController = new AccountController(new AccountServiceImpl(new AccountDaoPostgres()));

        app.post("/clients", clientController.createClientHandler);
        app.get("/clients", clientController.getAllClientsHandler);
        app.get("/clients/:cid", clientController.getClientByIdHandler);
        app.put("/clients/:cid", clientController.updateClientHandler);
        app.delete("/clients/:cid", clientController.deleteClientByIdHandler);

        app.post("/clients/:cid/accounts", accountController.createAccountHandler);
        app.get("/clients/:cid/accounts", accountController.getAllAccountsHandler);
        app.get("/clients/:cid/accounts/:aid", accountController.getAccountByIdHandler);
        app.put("/clients/:cid/accounts/:aid", accountController.updateAccountHandler);
        app.delete("/clients/:cid/accounts/:aid", accountController.deleteAccountByIdHandler);

        app.start();
    }
}
