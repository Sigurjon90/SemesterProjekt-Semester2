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
import API.entities.DeleteDTO;
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
import org.apache.commons.dbutils.DbUtils;

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
                String[] stringArrayOfDiagnoses = (String[]) sqlArrayOfDiagnoses.getArray();
                List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);

                // List<String> listString = Arrays.asList(arr);
                // Arrays.asList(citizensResult.getArray("diagnoses"))
                citizen = new Citizen((UUID) citizensResult.getObject("id"),
                        citizensResult.getString("name"),
                        citizensResult.getString("adress"),
                        citizensResult.getString("city"),
                        citizensResult.getInt("zip"),
                        citizensResult.getString("cpr"),
                        citizensResult.getInt("phone"),
                        listOfDiagnoses,
                        citizensResult.getBoolean("archived"),
                        citizensResult.getString("date_created"),
                        (UUID) citizensResult.getObject("author_id"));

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
            PreparedStatement findCitizen = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens WHERE id = ?");
            findCitizen.setObject(1, id);

            ResultSet citizenResultSet = findCitizen.executeQuery();
            Citizen citizen = null;

            while (citizenResultSet.next()) {

                Array sqlArrayOfDiagnoses = citizenResultSet.getArray("diagnoses");
                String[] stringArrayOfDiagnoses = (String[]) sqlArrayOfDiagnoses.getArray();
                List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);

                citizen = new Citizen((UUID) citizenResultSet.getObject("id"),
                        citizenResultSet.getString("name"),
                        citizenResultSet.getString("adress"),
                        citizenResultSet.getString("city"),
                        citizenResultSet.getInt("zip"),
                        citizenResultSet.getString("cpr"),
                        citizenResultSet.getInt("phone"),
                        listOfDiagnoses,
                        citizenResultSet.getBoolean("archived"),
                        citizenResultSet.getString("date_created"),
                        (UUID) citizenResultSet.getObject("author_id"));
            }

            return citizen;

        } catch (SQLException ex) {
            System.out.println("SQL Exception....");
            System.out.println("Read the Exeption");
            ex.printStackTrace();
        }

        return null;
    }

    public boolean deleteCitizen(DeleteDTO deleteDTO) {
        try (PreparedStatement deleteCitizen = connection.prepareStatement("UPDATE citizens SET archived = ?, author_id = ? WHERE id = ?")) {
            deleteCitizen.setBoolean(1, true);
            deleteCitizen.setObject(2, deleteDTO.getAuthorId());
            deleteCitizen.setObject(3, deleteDTO.getId());
            
            int affectedRows = deleteCitizen.executeUpdate();
            
            if(affectedRows == 0) {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public Citizen createCitizen(CreateDTO createDTO) {

        try {
            connection.setAutoCommit(false);
            PreparedStatement createCitizen = connection.prepareStatement("INSERT INTO citizens(id, name, adress, city, zip, cpr, phone, archived, author_id) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?) RETURNING id, name, adress, city, zip, cpr, phone, archived, date_created, author_id;");
            createCitizen.setObject(1, UUID.randomUUID(), Types.OTHER);
            createCitizen.setString(2, createDTO.getName());
            createCitizen.setString(3, createDTO.getAdress());
            createCitizen.setString(4, createDTO.getCity());
            createCitizen.setInt(5, createDTO.getZip());
            createCitizen.setString(6, createDTO.getCpr());
            createCitizen.setInt(7, createDTO.getPhoneNumber());
            createCitizen.setBoolean(8, false);
            createCitizen.setObject(9, createDTO.getAuthorId());

            ResultSet createCitizenResult = createCitizen.executeQuery();
            Citizen citizen = null;

            // Most values could also be gotten from createDTO (besides "id")
            while (createCitizenResult.next()) {
                citizen = new Citizen((UUID) createCitizenResult.getObject("id"), createCitizenResult.getString("name"), createCitizenResult.getString("adress"),
                        createCitizenResult.getString("city"), createCitizenResult.getInt("zip"), createCitizenResult.getString("cpr"),
                        createCitizenResult.getInt("phone"), createDTO.getDiagnoses(), createCitizenResult.getBoolean("archived"), createCitizenResult.getString("date_created"), (UUID) createCitizenResult.getObject("author_id"));
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
        } finally {
            DbUtils.closeQuietly(connection);
        }

        return null;
    }

}
