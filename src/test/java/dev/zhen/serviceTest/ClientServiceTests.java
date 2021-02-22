package dev.zhen.serviceTest;

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
    void create_client() {
        Client client1 = new Client(0,"Michael","Jordan",0);
        client1 = this.clientService.createClient(client1);
        this.client = client1;
        Client client2 = new Client(0,"Julius","Erving",0);
        client2 = this.clientService.createClient(client2);
        Assertions.assertNotEquals(client2.getId(), client1.getId());
        Assertions.assertEquals(client1.getId() + 1, client2.getId());
        Assertions.assertEquals(client1.getCreatedDate(), System.currentTimeMillis()/1000, 100);
    }

    @Test
    @Order(1)
    void get_all_client() {
        Set<Client> allClient = this.clientService.getAllClients();
        int sizeBefore = allClient.size();
        Client client2 = new Client(0,"Steve", "Curry", 0);
        this.clientService.createClient(client2);
        Assertions.assertNotEquals(sizeBefore, this.clientService.getAllClients().size());
        Assertions.assertEquals(sizeBefore + 1, this.clientService.getAllClients().size());
    }

    @Test
    @Order(2)
    void get_client_by_id() {
        Client client3 = this.clientService.getClientById(client.getId());
        Assertions.assertEquals(this.client.getId(), client3.getId());
    }

    @Test
    @Order(3)
    void get_client_by_name() {
        Set<Client> allClient = this.clientService.getClientByName(this.client.getFirstName());
        for (Client c : allClient) {
            Assertions.assertTrue(c.getFirstName().contains(this.client.getFirstName()));
        }
    }

    @Test
    @Order(4)
    void update_client_by_id() {
        Client client5 = new Client(0,"Isiah","Thomas",0);
        client5 = this.clientService.createClient(client5);
        Client returnClient = this.clientService.updateClient(client5);
        Assertions.assertEquals(client5.getId(), returnClient.getId());
        Assertions.assertTrue(client5.getFirstName().equals(returnClient.getFirstName()));
    }

    @Test
    @Order(5)
    void delete_client_by_id() {
        Client client4 = new Client(0,"Moses","Malone",0);
        clientService.createClient(client4);
        int sizeBeforeDelete = this.clientService.getAllClients().size();
        boolean isDeleted = this.clientService.deleteClientById(client4.getId());
        int sizeAfterDelete = this.clientService.getAllClients().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete + 1);
    }
}
