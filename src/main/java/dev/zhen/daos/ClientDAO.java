package dev.zhen.daos;

import dev.zhen.entities.Client;

import java.util.Set;

public interface ClientDAO {

    Client createClient(Client client);
    Set<Client> getAllClients();
    Client getClientById(int id);
    Client updateClient(Client client);
    boolean deleteClientById(int id);

}
