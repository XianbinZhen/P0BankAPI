package dev.zhen.daotest;

import dev.zhen.daos.ClientDAO;
import dev.zhen.daos.ClientDaoLocal;
import dev.zhen.entities.Client;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTest {
    private static ClientDAO clientDAO = new ClientDaoLocal();
    private static Client client = null;

    @Test
    @Order(0)
    void create_client() {
        Client client1 = new Client(0,"Michael","Jordan",0);
        this.clientDAO.createClient(client1);
        this.client = client1;
        Assertions.assertNotEquals(0, client1.getId());
        System.out.println("TEST 0 passed: create client");
    }

    @Test
    @Order(1)
    void get_all_client() {
        Client client2 = new Client(0,"Steve", "Curry", 0);
        this.clientDAO.createClient(client2);
        Assertions.assertNotEquals(1, this.clientDAO.getAllClients().size());
        System.out.println("TEST 1 passed: get all client");
    }

    @Test
    @Order(2)
    void get_client_by_id() {
        Client client3 = this.clientDAO.getClientById(1);
        Assertions.assertEquals(1, client3.getId());
        System.out.println("TEST 2 passed: get client by id");
    }

    @Test
    @Order(3)
    void delete_client_by_id() {
        int sizeBeforeDelete = this.clientDAO.getAllClients().size();
        boolean isDeleted = this.clientDAO.deleteClientById(this.client.getId());
        int sizeAfterDelete = this.clientDAO.getAllClients().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete + 1);
        System.out.println("TEST 3 passed: delete client by id");
    }
}
