package dev.zhen.controllers;

import com.google.gson.Gson;
import dev.zhen.entities.Client;
import dev.zhen.service.ClientService;
import io.javalin.http.Handler;

import java.util.Set;

public class ClientController {

    private ClientService clientService;
    private static Gson gson = new Gson();

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public Handler createClientHandler = ctx -> {
        Client client = gson.fromJson(ctx.body(), Client.class);
        if (client != null) {
            client = clientService.createClient(client);
            String clientJSON = gson.toJson(client);
            ctx.result(clientJSON);
            ctx.status(201);
        } else {
            ctx.result("Failed to create client: Body can't be empty.");
        }
    };

    public Handler getAllClientsHandler = ctx -> {
        String name = ctx.queryParam("name", "none");
        Set<Client> allClients;
        if (name.equals("none")) {
            allClients = clientService.getAllClients();
        } else {
            allClients = clientService.getClientByName(name);
        }
        String allClientJSON = gson.toJson(allClients);
        ctx.result(allClientJSON);
        ctx.status(201);
    };

    public Handler getClientByIdHandler = ctx -> {
        String id = ctx.pathParam("id");
        Client client = clientService.getClientById(Integer.parseInt(id));
        if (client == null) {
            ctx.result("Client not found");
            ctx.status(404);
        } else {
            String clientJSON = gson.toJson(client);
            ctx.result(clientJSON);
            ctx.status(201);
        }
    };

    public Handler updateClientHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        String body = ctx.body();
        Client client = gson.fromJson(body, Client.class);
        if (client != null) {
            client.setId(id);
            client = clientService.updateClient(client);
            if (client != null) {
                ctx.result(gson.toJson(client));
                ctx.status(202);
            } else {
                ctx.result("Client not found");
                ctx.status(404);
            }
        } else {
            ctx.result("Missing client info, update failed");
            // 400 Bad Request
            ctx.status(400);
        }
    };

    public Handler deleteClientByIdHandler = ctx -> {
        int id = Integer.parseInt(ctx.pathParam("id"));
        boolean isDeleted = clientService.deleteClientById(id);
        if (isDeleted) {
            ctx.result("Client with id: " + id + " is deleted");
            ctx.status(200);
        } else {
            ctx.result("Client not found");
            ctx.status(404);
        }
    };


}
