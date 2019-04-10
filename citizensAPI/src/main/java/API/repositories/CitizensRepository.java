/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

/**
 *
 * @author sigur
 */
import API.entities.Citizens;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CitizensRepository {

    private Connection connection = null;
    
        
    public CitizensRepository(@Value("${database.connection}") String connector, @Value("${database.username}") String username, @Value("${database.password}") String password) {
        String url = "jdbc:postgresql://manny.db.elephantsql.com:5432/zwyyekwy";
        String user = "zwyyekwy";
        String password = "m9u0sQT5_oIbIkD-Y8PRsLnDO2dG8v5O";
        
        

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url,
                    user, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Database is all yours!");
        } else {
            System.out.println("No connection...");
        }
    }

    public List<Citizens> getCitizens() {
        try {
            PreparedStatement getCitizens = connection.prepareStatement("SELECT * FROM Citizens");
            ResultSet citizensResult = getCitizens.executeQuery();
            Citizens citizens = null;
            List<Citizens> citizensList = new ArrayList<>();

            while (citizensResult.next()) {
                citizens = new Citizens((UUID) citizensResult.getObject("id"), (String) citizensResult.getObject("name"), (Integer) citizensResult.getObject("CPR_Number"),
                        (UUID) citizensResult.getObject("Address_ID"), (Long) citizensResult.getObject("Tlf_Number"));
                citizensList.add(citizens);
            }

            return citizensList;

        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Citizens findCitizen(UUID id) {
        try {
            PreparedStatement findCitizen = connection.prepareStatement("SELECT * FROM Citizens WHERE id = '" + id + "'");
            ResultSet citizenResultSet = findCitizen.executeQuery();
            Citizens citizen = null;
            while (citizenResultSet.next()) {
                citizen = new Citizens((UUID) citizenResultSet.getObject("id"), null, 0, null, 0);
            }

            return citizen;
        } catch (SQLException ex) {
            System.out.println("SQL Exception....");
            System.out.println("Read the Exeption");
            ex.printStackTrace();
        }

        return null;
    }

    public Citizens deleteCitizen(UUID id, UUID authorId) {
        try {

            // PreparedStatement findJournal = connection.prepareStatement("SELECT * FROM journals WHERE id = '" + id + "'");
            PreparedStatement deletCitizen = connection.prepareStatement("DELETE FROM Citizens WHERE id = '" + id + "'");
            // ResultSet journalResultSet = findJournal.executeQuery();
            //deleteJournal.setObject(1, (UUID)id); // ----> SQL INJECTION MAAAAAN
            //Journal journal = null;
            /*while (journalResultSet.next()) { 
                journal = new Journal((UUID) journalResultSet.getObject("id"), null);
            } */

            deletCitizen.executeQuery();
            return null;

        } catch (SQLException e) {
            System.out.println("ID was not found in database");
        }
        
        return null;
    }

    public Citizens createCitizen(Citizens c) {
        Scanner scanner = new Scanner(System.in);
        String name, streetName, streetNumber, town;
        int zipCode;
        Long CPR;

        System.out.println("Enter name of Citizen > ");
        name = scanner.next();
        System.out.println("CPR number >");
        CPR = scanner.nextLong();
        System.out.println("Street name >");
        streetName = scanner.next();
        System.out.println("Street number");

        try {
            connection.setAutoCommit(false); // Transaction
            PreparedStatement createCitizen = connection.prepareStatement("INSERT INTO Citizens(id, name, CPR_Number, address, til_number )"
                    + "VALURE(?,?,?,?,?)");
            createCitizen.setObject(0, UUID.randomUUID(), Types.OTHER);
            createCitizen.setObject(1, Types.OTHER);
            ResultSet citizenResultSet = createCitizen.executeQuery();
            Citizens createdCitizen = null;
            while (citizenResultSet.next()) {
                createdCitizen = new Citizens();
            }

            return (Citizens) createCitizen;

        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return null;
    }

}