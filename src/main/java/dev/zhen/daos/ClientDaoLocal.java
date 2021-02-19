package dev.zhen.daos;

import dev.zhen.entities.Client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientDaoLocal implements ClientDAO{

    private final Map<Integer, Client> clientTable = new HashMap<>();
    private int idCounter = 0;

    @Override
    public Client createClient(Client client) {
        client.setId(++idCounter);
        client.setCreatedDate(System.currentTimeMillis()/1000);
        clientTable.put(idCounter, client);
        return client;
    }

    @Override
    public Set<Client> getAllClients() {
        return new HashSet<>(clientTable.values());
    }

    @Override
    public Client getClientById(int id) {
        return clientTable.get(id);
    }

    @Override
    public Set<Client> getClientByName(String name) {
        Set<Client> allClient = getAllClients();
        Set<Client> result = new HashSet<>();
        for (Client client : allClient) {
            if(client.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
               client.getLastName().toLowerCase().contains(name.toLowerCase())) {
                result.add(client);
            }
        }
        return result;
    }

    @Override
    public Client updateClient(Client client) {
        int id = client.getId();
        clientTable.put(id, client);
        return client;
    }

    @Override
    public boolean deleteClientById(int id) {
        Client client = clientTable.remove(id);
        return client != null;
    }
}
