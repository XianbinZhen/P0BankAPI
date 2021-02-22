package dev.zhen.daotest;

import dev.zhen.daos.ClientDAO;
import dev.zhen.daos.ClientDaoPostgres;
import dev.zhen.entities.Client;
import org.junit.jupiter.api.*;
import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientDaoTest {

    private static ClientDAO clientDAO = new ClientDaoPostgres();
    private static Client client = null;

    @Test
    @Order(0)
    void create_client() {
        Client client1 = new Client(0,"Michael","Jordan",0);
        client1 = this.clientDAO.createClient(client1);
        this.client = client1;
        Client client2 = new Client(0,"Julius","Erving",0);
        client2 = clientDAO.createClient(client2);
        Assertions.assertNotEquals(client2.getId(), client1.getId());
        Assertions.assertEquals(client1.getId() + 1, client2.getId());
        Assertions.assertEquals(client1.getCreatedDate(), System.currentTimeMillis()/1000, 100);
    }

    @Test
    @Order(1)
    void get_all_client() {
        Set<Client> allClient = clientDAO.getAllClients();
        int sizeBefore = allClient.size();
        Client client2 = new Client(0,"Steve", "Curry", 0);
        this.clientDAO.createClient(client2);
        Assertions.assertNotEquals(sizeBefore, this.clientDAO.getAllClients().size());
        Assertions.assertEquals(sizeBefore + 1, this.clientDAO.getAllClients().size());
    }

    @Test
    @Order(2)
    void get_client_by_id() {
        Client client3 = this.clientDAO.getClientById(client.getId());
        Assertions.assertEquals(client.getId(), client3.getId());
    }

    @Test
    @Order(3)
    void get_client_by_name() {
        Set<Client> allClient = this.clientDAO.getClientByName(client.getFirstName());
        for (Client c : allClient) {
            Assertions.assertTrue(c.getFirstName().contains(client.getFirstName()));
        }
    }

    @Test
    @Order(4)
    void update_client_by_id() {
        Client client5 = new Client(0,"Isiah","Thomas",0);
        client5 = clientDAO.createClient(client5);
        Client returnClient = clientDAO.updateClient(client5);
        Assertions.assertEquals(client5.getId(), returnClient.getId());
        Assertions.assertTrue(client5.getFirstName().equals(returnClient.getFirstName()));
    }

    @Test
    @Order(5)
    void delete_client_by_id() {
        Client client4 = new Client(0,"Moses","Malone",0);
        clientDAO.createClient(client4);
        int sizeBeforeDelete = this.clientDAO.getAllClients().size();
        boolean isDeleted = this.clientDAO.deleteClientById(client4.getId());
        int sizeAfterDelete = this.clientDAO.getAllClients().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete + 1);
    }
}
