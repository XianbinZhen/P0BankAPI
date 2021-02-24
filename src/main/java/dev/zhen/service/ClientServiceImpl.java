package dev.zhen.service;

import dev.zhen.daos.ClientDAO;
import dev.zhen.entities.Client;

import java.util.HashSet;
import java.util.Set;

public class ClientServiceImpl implements ClientService{

    private ClientDAO clientDAO = null;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public Client createClient(Client client) {
        return clientDAO.createClient(client);
    }

    @Override
    public Set<Client> getAllClients() {
        return clientDAO.getAllClients();
    }

    @Override
    public Client getClientById(int id) {
        return clientDAO.getClientById(id);
    }

    @Override
    public Set<Client> getClientByName(String name) {
        return clientDAO.getClientByName(name);
    }

    @Override
    public Set<Client> getClientByFirstName(String name) {
        Set<Client> allClients = getAllClients();
        Set<Client> result = new HashSet<>();
        for (Client client : allClients) {
            if (client.getFirstName().toLowerCase().contains(name.toLowerCase())) {
                result.add(client);
            }
        }
        if (result.size() > 0)
            return result;
        else {
            return null;
        }
    }

    @Override
    public Client updateClient(Client client) {
        return clientDAO.updateClient(client);
    }

    @Override
    public boolean deleteClientById(int id) {
        return clientDAO.deleteClientById(id);
    }
}
