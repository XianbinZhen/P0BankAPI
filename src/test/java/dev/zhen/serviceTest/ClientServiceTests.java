package dev.zhen.serviceTest;

import dev.zhen.daos.ClientDaoLocal;
import dev.zhen.daos.ClientDaoPostgres;
import dev.zhen.entities.Client;
import dev.zhen.service.ClientService;
import dev.zhen.service.ClientServiceImpl;
import org.junit.jupiter.api.*;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTests {

    private static ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private static Client client = null;


    @Test
    @Order(0)
    void register_client() {
        Client client1 = new Client(0, "John", "Stockton",0);
        clientService.createClient(client1);
        client = client1;
        Assertions.assertNotEquals(0, client1.getId());
        System.out.println("Test 0 passed: register client");
    }

    @Test
    @Order(1)
    void get_all_clients() {
        Set<Client> allClients = clientService.getAllClients();
        Assertions.assertNotEquals(0, allClients.size());
        System.out.println("Test 1 pass: get all clients");
    }

    @Test
    @Order(2)
    void get_client_by_id() {
        Client client2 = clientService.getClientById(client.getId());
        Assertions.assertEquals(client.getId(), client2.getId());
        System.out.println("Test 2 pass: get client by id");
    }

    @Test
    @Order(3)
    void update_client_by_id() {
        Client client3 = clientService.getClientById(client.getId());
        client3.setFirstName("Karl");
        client3.setLastName("Malone");
        Assertions.assertTrue(client3.getFirstName().equals("Karl"));
        System.out.println("Test 3 pass: update client by id");
    }

    @Test
    @Order(4)
    void delete_client_by_id() {
        Client client4 = new Client(0, "Scottie", "Pippen", 0);
        clientService.createClient(client4);
        int sizeBeforeDelete = clientService.getAllClients().size();
        boolean isDeleted = clientService.deleteClientById(client.getId());
        int sizeAfterDelete = clientService.getAllClients().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete + 1);
        System.out.println("Test 4 pass: delete client by id");
    }

}
