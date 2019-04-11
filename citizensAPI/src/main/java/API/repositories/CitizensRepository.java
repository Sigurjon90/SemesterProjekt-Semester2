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
import java.sql.Array;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Arrays;
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
            PreparedStatement getCitizens = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens;");
            ResultSet citizensResult = getCitizens.executeQuery();
            Citizen citizen = null;
            List<Citizen> citizensList = new ArrayList<>();

            while (citizensResult.next()) {
                
                Array sqlArrayOfDiagnoses = citizensResult.getArray("diagnoses");
                String[] stringArrayOfDiagnoses = (String[])sqlArrayOfDiagnoses.getArray();
                List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);
                
                // List<String> listString = Arrays.asList(arr);
                // Arrays.asList(citizensResult.getArray("diagnoses"))
                
                citizen = new Citizen((UUID)citizensResult.getObject("id"), citizensResult.getString("name"), citizensResult.getString("adress"),
                citizensResult.getString("city"), citizensResult.getInt("zip"), citizensResult.getString("cpr"), citizensResult.getInt("phone"), listOfDiagnoses, citizensResult.getBoolean("archived"));
                citizensList.add(citizen);
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
            connection.setAutoCommit(false);
            PreparedStatement createCitizen = connection.prepareStatement("INSERT INTO citizens(id, name, adress, city, zip, cpr, phone, archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id, name, adress, city, zip, cpr, phone, archived;");
            createCitizen.setObject(1, UUID.randomUUID(), Types.OTHER);
            createCitizen.setString(2, createDTO.getName());
            createCitizen.setString(3, createDTO.getAdress());
            createCitizen.setString(4, createDTO.getCity());
            createCitizen.setInt(5, createDTO.getZip());
            createCitizen.setString(6, createDTO.getCpr());
            createCitizen.setInt(7, createDTO.getPhoneNumber());
            createCitizen.setBoolean(8, false);

            ResultSet createCitizenResult = createCitizen.executeQuery();
            Citizen citizen = null;

            // Most values could also be gotten from createDTO (besides "id")
            while (createCitizenResult.next()) {
                citizen = new Citizen((UUID)createCitizenResult.getObject("id"), createCitizenResult.getString("name"), createCitizenResult.getString("adress"),
                        createCitizenResult.getString("city"), createCitizenResult.getInt("zip"), createCitizenResult.getString("cpr"),
                        createCitizenResult.getInt("phone"), createDTO.getDiagnoses(), createCitizenResult.getBoolean("archived"));
            }
            
            for (String diagnoseString : createDTO.getDiagnoses()) {
                PreparedStatement setDiagnoses = connection.prepareStatement("INSERT INTO diagnose(citizens_id, diagnose) VALUES (?, ?) RETURNING diagnose;");
                setDiagnoses.setObject(1, citizen.getId(), Types.OTHER);
                setDiagnoses.setString(2, diagnoseString);
                setDiagnoses.execute();
            }

            connection.commit();
            return citizen;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
