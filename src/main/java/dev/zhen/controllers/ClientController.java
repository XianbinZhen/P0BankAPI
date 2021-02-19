package dev.zhen.controllers;

import com.google.gson.Gson;
import dev.zhen.entities.Client;
import dev.zhen.service.ClientService;
import io.javalin.http.Handler;

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
}
