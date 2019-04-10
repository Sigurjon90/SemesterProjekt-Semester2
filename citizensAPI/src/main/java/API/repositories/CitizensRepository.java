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
import API.entities.Citizen;
import API.entities.CitizenDTO;
import API.entities.CreateDTO;

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

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connector,
                    username, password);
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

    public List<Citizen> getCitizens() {
        try {
            PreparedStatement getCitizens = connection.prepareStatement("SELECT * FROM Citizens");
            ResultSet citizensResult = getCitizens.executeQuery();
            Citizen citizens = null;
            List<Citizen> citizensList = new ArrayList<>();

            while (citizensResult.next()) {
                citizens = new Citizen((UUID) citizensResult.getObject("id"), (String) citizensResult.getObject("name"), (Integer) citizensResult.getObject("CPR_Number"),
                        (UUID) citizensResult.getObject("Address_ID"), (Long) citizensResult.getObject("Tlf_Number"));
                citizensList.add(citizens);
            }

            return citizensList;

        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Citizen findCitizen(UUID id) {
        try {
            PreparedStatement findCitizen = connection.prepareStatement("SELECT * FROM Citizens WHERE id = '" + id + "'");
            ResultSet citizenResultSet = findCitizen.executeQuery();
            Citizen citizen = null;
            while (citizenResultSet.next()) {
                citizen = new Citizen((UUID) citizenResultSet.getObject("id"), null, 0, null, 0);
            }

            return citizen;
        } catch (SQLException ex) {
            System.out.println("SQL Exception....");
            System.out.println("Read the Exeption");
            ex.printStackTrace();
        }

        return null;
    }

    public Citizen deleteCitizen(UUID id, UUID authorId) {
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

    public Citizen createCitizen(CreateDTO createDTO) {
        
        try {
        PreparedStatement createCitizen = connection.prepareStatement("INSERT INTO citizens(?, ?, ?, ?, ?, ?) RETURNING id, name, adress, city, zip, cpr, phoneNumber;");
        createCitizen.setObject(1, UUID.randomUUID(), Types.OTHER);
        createCitizen.setString(2, createDTO.getName());
        createCitizen.setString(3, createDTO.getAdress());
        createCitizen.setString(4, createDTO.getCity());
        createCitizen.setInt(5, createDTO.getZip());
        createCitizen.setInt(6, createDTO.getCpr());
        createCitizen.setInt(7, createDTO.getPhoneNumber());
        
        ResultSet createCitizenResult = createCitizen.executeQuery();
        Citizen citizen = null;
        
        while(createCitizenResult.next()) {
            citizen = new Citizen(UUID.randomUUID(), createCitizenResult.getString("name"), createCitizenResult.getString("adress"), 
                    createCitizenResult.getString("city"), createCitizenResult.getInt("zip"), createCitizenResult.getInt("cpr"), 
                    createCitizenResult.getInt("phone"), null);
        }
        
        
      
        
        
        } catch (SQLException e) {
            System.out.println("SQLError in ### createCitizen ###");
        }
        
        return
    }

    }
