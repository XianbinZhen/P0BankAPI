package dev.zhen.service;

import dev.zhen.entities.Client;

import java.util.Set;

public interface ClientService {

    Client createClient(Client client);
    Set<Client> getAllClients();
    Client getClientById(int id);
    Set<Client> getClientByName(String name);
    Set<Client> getClientByFirstName(String name);
    Client updateClient(Client client);
    boolean deleteClientById(int id);

}
