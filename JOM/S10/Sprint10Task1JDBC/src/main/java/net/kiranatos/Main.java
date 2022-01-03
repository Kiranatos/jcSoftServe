package net.kiranatos;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        MyUtils myUtils = new MyUtils();

        System.out.println(myUtils.createConnection());
        myUtils.createSchema("test");
        myUtils.useSchema();

        myUtils.createTableRoles();
        myUtils.insertTableRoles("Developer");
        myUtils.insertTableRoles("DevOps");
        myUtils.insertTableRoles("QC");

        myUtils.createTableDirections();
        myUtils.insertTableDirections("Java");
        myUtils.insertTableDirections("Python");
        myUtils.insertTableDirections(".Net");

        myUtils.createTableProjects();
        myUtils.insertTableProjects("MoonLight", "Java");
        myUtils.insertTableProjects("Sun", "Java");
        myUtils.insertTableProjects("Mars", "Python");

        myUtils.createTableEmployee();
        myUtils.insertTableEmployee("Ivan", "1", "1");
        myUtils.insertTableEmployee("Petro", "1", "2");
        myUtils.insertTableEmployee("Stepan", "1", "3");
        myUtils.insertTableEmployee("Andriy", "2", "1");
        myUtils.insertTableEmployee("Vasyl", "2", "3");
        myUtils.insertTableEmployee("Ira", "1", "1");
        myUtils.insertTableEmployee("Anna", "3", "1");
        myUtils.insertTableEmployee("Olya", "3", "2");
        myUtils.insertTableEmployee("Maria", "3", "3");

        System.out.println(myUtils.getRoleId("Developer"));
        System.out.println(myUtils.getDirectionId("Python"));
        System.out.println(myUtils.getProjectId("Sun"));
        System.out.println(myUtils.getEmployeeId("Anna"));

        System.out.println(myUtils.getAllRoles());
        System.out.println(myUtils.getAllDirestion());
        System.out.println(myUtils.getAllProjects());
        System.out.println(myUtils.getAllEmployee());

        System.out.println(myUtils.getAllDevelopers());
        System.out.println(myUtils.getAllJavaProjects());
        System.out.println(myUtils.getAllJavaDevelopers());
    }
}