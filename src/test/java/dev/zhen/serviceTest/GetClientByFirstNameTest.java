package dev.zhen.serviceTest;

import dev.zhen.daos.ClientDAO;
import dev.zhen.entities.Client;
import dev.zhen.service.ClientService;
import dev.zhen.service.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class GetClientByFirstNameTest {

    @Mock
    ClientDAO clientDAO = null;

    private ClientService clientService = null;

    @BeforeEach
    void setUp() {
        Client client1 = new Client(1,"William", "Johnson", 10);
        Client client2 = new Client(1,"Bill", "Walton", 20);
        Client client3 = new Client(1,"George", "Gervin", 30);
        Client client4 = new Client(1,"Isiah", "Thomas", 40);
        Set<Client> clientSet = new HashSet<>();
        clientSet.add(client1);
        clientSet.add(client2);
        clientSet.add(client3);
        clientSet.add(client4);
        Mockito.when(clientDAO.getAllClients()).thenReturn(clientSet);
        this.clientService = new ClientServiceImpl(clientDAO);
    }

    @Test
    void get_client_by_first_name() {
        Set<Client> clients = clientService.getClientByFirstName("biLL");
        System.out.println(clients);
        Assertions.assertTrue(clients.size() == 1);
    }

}
