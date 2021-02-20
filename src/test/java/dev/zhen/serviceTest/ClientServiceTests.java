package dev.zhen.serviceTest;
import dev.zhen.daos.ClientDaoPostgres;
import dev.zhen.entities.Client;
import dev.zhen.service.ClientService;
import dev.zhen.service.ClientServiceImpl;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientServiceTests {

    private static ClientService clientService = new ClientServiceImpl(new ClientDaoPostgres());
    private static Client client = null;



    @Test
    @Order(0)
    void create_client() {
        Client client1 = new Client(0,"Michael","Jordan",0);
        this.clientService.createClient(client1);
        this.client = client1;
        Assertions.assertNotEquals(0, client1.getId());
    }

    @Test
    @Order(1)
    void get_all_client() {
        Client client2 = new Client(0,"Steve", "Curry", 0);
        this.clientService.createClient(client2);
        Assertions.assertNotEquals(1, this.clientService.getAllClients().size());
    }

    @Test
    @Order(2)
    void get_client_by_id() {
        Client client3 = this.clientService.getClientById(client.getId());
        Assertions.assertEquals(client.getId(), client3.getId());
    }

    @Test
    @Order(3)
    void update_client_by_id() {
        Client client5 = new Client(0,"New Michael","Jordan",0);
        client5 = clientService.createClient(client5);
        Client returnClient = clientService.updateClient(client5);
        Assertions.assertEquals(client5.getId(), returnClient.getId());
        Assertions.assertTrue(client5.getFirstName().equals(returnClient.getFirstName()));
    }

    @Test
    @Order(4)
    void delete_client_by_id() {
        Client client4 = new Client(0,"New Michael","Jordan",0);
        clientService.createClient(client4);
        int sizeBeforeDelete = this.clientService.getAllClients().size();
        boolean isDeleted = this.clientService.deleteClientById(client4.getId());
        int sizeAfterDelete = this.clientService.getAllClients().size();
        Assertions.assertTrue(isDeleted);
        Assertions.assertEquals(sizeBeforeDelete, sizeAfterDelete + 1);
    }

}
