package dev.zhen.app;

import dev.zhen.controllers.ClientController;
import dev.zhen.daos.ClientDAO;
import dev.zhen.daos.ClientDaoPostgres;
import dev.zhen.service.ClientService;
import dev.zhen.service.ClientServiceImpl;
import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {

        Javalin app = Javalin.create();
        ClientDAO clientDAO = new ClientDaoPostgres();
        ClientService clientService = new ClientServiceImpl(clientDAO);
        ClientController clientController =  new ClientController(clientService);

        app.post("/clients", clientController.createClientHandler);
        app.get("/clients", clientController.getAllClientsHandler);
        app.get("/clients/:id", clientController.getClientByIdHandler);
        app.put("/clients/:id", clientController.updateClientHandler);
        app.delete("/clients/:id", clientController.deleteClientByIdHandler);

        app.start();
    }
}
