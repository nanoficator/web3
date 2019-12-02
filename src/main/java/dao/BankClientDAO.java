package dao;

import com.sun.deploy.util.SessionState;
import model.BankClient;
import servlet.ResultServlet;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class BankClientDAO {

    private Connection connection;

    public BankClientDAO(Connection connection) {
        this.connection = connection;
    }

    public List<BankClient> getAllBankClient() throws SQLException {
        List<BankClient> allBankClient = new LinkedList<>();
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client");
        ResultSet result = stmt.getResultSet();
        while (result.next()) {
            long userId = result.getLong(1);
            String userName = result.getString(2);
            String userPassword = result.getString(3);
            Long userMoney = result.getLong(4);
            allBankClient.add(new BankClient(userId, userName, userPassword, userMoney));
        }
        result.close();
        stmt.close();
        return allBankClient;
    }

    public boolean validateClient(String name, String password) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();
        boolean validation = result.next();
        result.close();
        stmt.close();
        return validation;
    }

    public void updateClientsMoney(String name, String password, Long transactValue) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name = '" + name + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        Long userMoney = result.getLong(4);
        result.close();
        stmt.close();
        PreparedStatement preparedStatement = connection.prepareStatement("update bank_client set money = ? where name = ? and password = ?");
        preparedStatement.setLong(1, + transactValue);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, password);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public BankClient getClientById(long id) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where id='" + id + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        long userId = result.getLong(1);
        String userName = result.getString(2);
        String userPassword = result.getString(3);
        Long userMoney = result.getLong(4);
        result.close();
        stmt.close();
        return new BankClient(userId, userName, userPassword, userMoney);
    }

    public boolean isClientHasSum(String name, Long expectedSum) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        long userMoney = result.getLong(4);
        result.close();
        stmt.close();
        return expectedSum <= userMoney;
    }

    public long getClientIdByName(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        Long userId = result.getLong(1);
        result.close();
        stmt.close();
        return userId;
    }

    public BankClient getClientByName(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from bank_client where name='" + name + "'");
        ResultSet result = stmt.getResultSet();
        result.next();
        long userId = result.getLong(1);
        String userName = result.getString(2);
        String userPassword = result.getString(3);
        Long userMoney = result.getLong(4);
        result.close();
        stmt.close();
        return new BankClient(userId, userName, userPassword, userMoney);
    }

    public void addClient(BankClient client) throws SQLException {
        String clientName = client.getName();
        String clientPassword = client.getPassword();
        Long clientMoney = client.getMoney();
        Statement stmt = connection.createStatement();
        stmt.execute("insert into bank_client (name, password, money) values ('" + clientName + "', '" + clientPassword + "', '" + clientMoney + "')");
        stmt.close();
    }

    public void deleteClient(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("delete from bank_client where name='" + name + "'");
        stmt.close();
    }

    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists bank_client (id bigint auto_increment, name varchar(256), password varchar(256), money bigint, primary key (id))");
        stmt.close();
    }

    public void dropTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS bank_client");
        stmt.close();
    }
}
