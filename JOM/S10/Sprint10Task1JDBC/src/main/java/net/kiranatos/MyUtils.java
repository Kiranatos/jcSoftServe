package net.kiranatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.h2.Driver;

public class MyUtils {

    private Connection connection;
    private Statement statement;
    private String schemaName;

    private static final String JDBC_URL = "jdbc:h2:mem:test";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";

    public Connection createConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        return connection;
    }
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    public Statement createStatement() throws SQLException {
        statement = connection.createStatement();
        return statement;
    }
    public void closeStatement() throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }
    public void createSchema(String schemaName) throws SQLException {
        this.schemaName = schemaName;
        statement.executeUpdate("CREATE SCHEMA IF NOT EXISTS " + schemaName);
    }
    public void dropSchema() throws SQLException {
        statement.executeUpdate("DROP SCHEMA " + schemaName);
    }
    public void useSchema() throws SQLException {
        statement.executeUpdate("SET SCHEMA " + schemaName);
    }
    public void createTableRoles() throws SQLException {
        String sql = "CREATE TABLE Roles " +
                "(id BIGINT AUTO_INCREMENT, " +
                "roleName VARCHAR(255)," +
                "PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
    }
    public void createTableDirections() throws SQLException {
        String sql = "CREATE TABLE Directions " +
                "(id BIGINT AUTO_INCREMENT, " +
                "directionName VARCHAR(255)," +
                "PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
    }
    public void createTableProjects() throws SQLException {
        String sql = "CREATE TABLE Projects " +
                "(id BIGINT AUTO_INCREMENT, " +
                "projectName VARCHAR(255), " +
                "directionId INTEGER, " +
                "FOREIGN KEY (directionId) REFERENCES Directions(id), " +
                "PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
    }
    public void createTableEmployee() throws SQLException {
        String sql = "CREATE TABLE Employee " +
                "(id BIGINT AUTO_INCREMENT, " +
                "firstName VARCHAR(255)," +
                "roleId INTEGER, " +
                "projectId INTEGER, " +
                "FOREIGN KEY (roleId) REFERENCES Roles(id), " +
                "FOREIGN KEY (projectId) REFERENCES Projects(id), " +
                "PRIMARY KEY ( id ))";
        statement.executeUpdate(sql);
    }
    public void dropTable(String tableName) throws SQLException {
        statement.executeUpdate("DROP TABLE " + tableName);
    }
    public void insertTableRoles(String roleName) throws SQLException {
        statement.executeUpdate("INSERT INTO Roles(roleName) VALUES ('" + roleName + "')");
    }
    public void insertTableDirections(String directionName) throws SQLException {
        statement.executeUpdate("INSERT INTO Directions(directionName) VALUES ('" + directionName + "')");
    }
    public void insertTableProjects(String projectName, String directionName) throws SQLException {
        statement.executeUpdate("INSERT INTO Projects(projectName, directionId) VALUES ('" + projectName + "', '" + getDirectionId(directionName) + "')");
    }
    public void insertTableEmployee(String firstName, String roleName, String projectName) throws SQLException {
        statement.executeUpdate("INSERT INTO Employee(firstName, roleId, projectId) VALUES ('" + firstName + "', '" + getRoleId(roleName) + "', '" + getProjectId(projectName) + "')");
    }
    public int getRoleId(String roleName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT id FROM Roles WHERE roleName='" + roleName + "'");
        while (rs.next()) {
            return rs.getInt("id");
        }
        return 0;
    }
    public int getDirectionId(String directionName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT id FROM Directions WHERE directionName='" + directionName + "'");
        while (rs.next()) {
            return rs.getInt("id");
        }
        return 0;
    }
    public int getProjectId(String projectName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT id FROM Projects WHERE projectName='" + projectName + "'");
        while (rs.next()) {
            return rs.getInt("id");
        }
        return 0;
    }
    public int getEmployeeId(String firstName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT id FROM Employee WHERE firstName='" + firstName + "'");
        while (rs.next()) {
            return rs.getInt("id");
        }
        return 0;
    }
    public List<String> getAllRoles() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT roleName FROM Roles");
        while (rs.next()) {
            list.add(rs.getString("roleName"));
        }
        return list;
    }
    public List<String> getAllDirestion() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT directionName FROM Directions");
        while (rs.next()) {
            list.add(rs.getString("directionName"));
        }
        return list;
    }
    public List<String> getAllProjects() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT projectName FROM Projects");
        while (rs.next()) {
            list.add(rs.getString("projectName"));
        }
        return list;
    }
    public List<String> getAllEmployee() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT firstName FROM Employee");
        while (rs.next()) {
            list.add(rs.getString("firstName"));
        }
        return list;
    }
    public List<String> getAllDevelopers() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT firstName FROM Employee WHERE roleId=" + getRoleId("Developer"));
        while (rs.next()) {
            list.add(rs.getString("firstName"));
        }
        return list;
    }
    public List<String> getAllJavaProjects() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT projectName FROM Projects WHERE directionId=" + getDirectionId("Java"));
        while (rs.next()) {
            list.add(rs.getString("projectName"));
        }
        return list;
    }
    public List<String> getAllJavaDevelopers() throws SQLException {
        List<String> list = new ArrayList<String>();
        ResultSet rs = statement.executeQuery("SELECT firstName FROM Employee WHERE roleId=" +
                getRoleId("Developer") + " AND projectId=" + getProjectId("MoonLight") + " OR roleId=" +
                getRoleId("Developer") + " AND projectId=" + getProjectId("Sun"));
        while (rs.next()) {
            list.add(rs.getString("firstName"));
        }
        return list;
    }
}
