package dev.zhen.daos;

import dev.zhen.entities.Client;
import dev.zhen.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ClientDaoPostgres implements ClientDAO{

    static Logger logger = Logger.getLogger(ClientDaoPostgres.class.getName());

    @Override
    public Client createClient(Client client) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "insert into client (first_name, last_name, created_date) values ( ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            Long currentTime = System.currentTimeMillis()/1000;
            preparedStatement.setLong(3, currentTime);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            client.setCreatedDate(currentTime);
            client.setId(resultSet.getInt("client_id"));
            return client;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("Failed to create client", sqlException);
            return null;
        }
    }

    @Override
    public Set<Client> getAllClients() {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from client";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            Set<Client> clients = new HashSet<>();
            while(resultSet.next()) {
                int id = resultSet.getInt("client_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                long createdDate = resultSet.getLong("created_date");
                clients.add(new Client(id, firstName, lastName, createdDate));
            }
            return clients;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("Failed to retrieve all clients", sqlException);
            return null;
        }
    }

    @Override
    public Client getClientById(int id) {
        try (Connection connection = ConnectionUtil.createConnection()) {
            String sql = "select * from client where client_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int clientId = resultSet.getInt("client_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                long createdDate = resultSet.getLong("created_date");
                Client client = new Client(clientId, firstName,lastName, createdDate);
                return client;
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            logger.error("Failed to get client by id", sqlException);
            return null;
        }
    }

    @Override
    public Set<Client> getClientByName(String name) {
        Set<Client> allClients = getAllClients();
        Set<Client> result = new HashSet<>();
        for (Client client : allClients) {
            if (client.getLastName().toLowerCase().contains(name.toLowerCase()) ||
               client.getFirstName().toLowerCase().contains(name.toLowerCase())) {
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
        Client searchClient = getClientById(client.getId());
        if (searchClient != null) {
            try (Connection connection = ConnectionUtil.createConnection()) {
                String sql = "update client set first_name = ?, last_name = ?, created_date= ? where client_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, client.getFirstName());
                preparedStatement.setString(2, client.getLastName());
                preparedStatement.setLong(3, client.getCreatedDate());
                preparedStatement.setInt(4, client.getId());
                preparedStatement.execute();
                return client;
            } catch (SQLException sqlException) {
                logger.error("Failed to update client", sqlException);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteClientById(int id) {
        Client client = getClientById(id);
        if (client != null) {
            try (Connection connection = ConnectionUtil.createConnection()) {
                String sql = "delete from client where client_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                return true;
            } catch (SQLException sqlException) {
                logger.info("Failed to delete client", sqlException);
                return false;
            }
        } else {
            return false;
        }
    }
}
