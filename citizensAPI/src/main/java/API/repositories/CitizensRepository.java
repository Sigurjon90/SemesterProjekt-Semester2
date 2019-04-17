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
import java.sql.Array;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class CitizensRepository implements ICitizensRepository {

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

    @Override
    public List<Citizen> getMyCitizens(List<UUID> listOfCitizensIds) {
        
        try (PreparedStatement getLimited = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens WHERE id = ANY(?) AND archived = false")) {
            java.sql.Array sqlArray = connection.createArrayOf("UUID", listOfCitizensIds.toArray());
            getLimited.setArray(1, sqlArray);
            ResultSet citizensResult = getLimited.executeQuery();
            List<Citizen> citizenList = new ArrayList();
            while (citizensResult.next()) {
                Array sqlArrayOfDiagnoses = citizensResult.getArray("diagnoses");
                String[] stringArrayOfDiagnoses = (String[]) sqlArrayOfDiagnoses.getArray();
                List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);
                
                citizenList.add(new Citizen((UUID) citizensResult.getObject("id"),
                        citizensResult.getString("name"),
                        citizensResult.getString("adress"),
                        citizensResult.getString("city"),
                        citizensResult.getInt("zip"),
                        citizensResult.getString("cpr"),
                        citizensResult.getInt("phone"),
                        listOfDiagnoses,
                        citizensResult.getBoolean("archived"),
                        citizensResult.getString("date_created"),
                        (UUID) citizensResult.getObject("author_id")));
            }
            return citizenList;
        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<Citizen> getCitizens() {
        try (PreparedStatement getCitizens = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens WHERE archived = false")) {
            ResultSet citizensResult = getCitizens.executeQuery();
            List<Citizen> citizensList = new ArrayList<>();

            while (citizensResult.next()) {
                Array sqlArrayOfDiagnoses = citizensResult.getArray("diagnoses");
                String[] stringArrayOfDiagnoses = (String[]) sqlArrayOfDiagnoses.getArray();
                List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);

                // List<String> listString = Arrays.asList(arr);
                // Arrays.asList(citizensChangedResult.getArray("diagnoses"))
                citizensList.add(new Citizen((UUID) citizensResult.getObject("id"),
                        citizensResult.getString("name"),
                        citizensResult.getString("adress"),
                        citizensResult.getString("city"),
                        citizensResult.getInt("zip"),
                        citizensResult.getString("cpr"),
                        citizensResult.getInt("phone"),
                        listOfDiagnoses,
                        citizensResult.getBoolean("archived"),
                        citizensResult.getString("date_created"),
                        (UUID) citizensResult.getObject("author_id")));
            }
            return citizensList;
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Citizen findCitizen(UUID id) {
        try (PreparedStatement findCitizen = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens WHERE id = ? AND archived = false")) {
            findCitizen.setObject(1, id);
            ResultSet citizenResultSet = findCitizen.executeQuery();

            while (citizenResultSet.next()) {
                Array sqlArrayOfDiagnoses = citizenResultSet.getArray("diagnoses");
                String[] stringArrayOfDiagnoses = (String[]) sqlArrayOfDiagnoses.getArray();
                List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);

                return new Citizen((UUID) citizenResultSet.getObject("id"),
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
        } catch (SQLException ex) {
            System.out.println("SQL Exception....");
            System.out.println("Read the Exeption");
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean deleteCitizen(Citizen citizen) {
        try (PreparedStatement deleteCitizen = connection.prepareStatement("UPDATE citizens SET archived = ?, author_id = ? WHERE id = ?")) {
            deleteCitizen.setBoolean(1, true);
            deleteCitizen.setObject(2, citizen.getAuthorId());
            deleteCitizen.setObject(3, citizen.getId());

            int affectedRows = deleteCitizen.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public List<Citizen> batchUpdate(List<Citizen> citizenList) {
        List<Citizen> citizensListReturned = new ArrayList<>();
        // Deleting all diagnoses
        try {
            connection.setAutoCommit(false);
            for (Citizen citizen : citizenList) {
                PreparedStatement deleteDiagnoses = this.connection.prepareStatement("DELETE FROM diagnose WHERE citizens_id = ?");
                deleteDiagnoses.setObject(1, citizen.getId());
                deleteDiagnoses.execute();
                
                // Adding all new diagnoses
                for (String diagnoseString : citizen.getDiagnoses()) {
                    PreparedStatement setDiagnoses = connection.prepareStatement("INSERT INTO diagnose(citizens_id, diagnose) VALUES (?, ?) RETURNING diagnose;");
                    setDiagnoses.setObject(1, citizen.getId(), Types.OTHER);
                    setDiagnoses.setString(2, diagnoseString);
                    setDiagnoses.execute();
                }

                PreparedStatement updateCitizen = this.connection.prepareStatement("UPDATE citizens SET name = ?,"
                        + "adress = ?, city = ?, zip = ?, phone = ?, author_id = ? WHERE id = ? RETURNING id, name, adress, city, zip, cpr, phone, archibed, date_created, author_id, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = ?)) AS diagnoses");

                updateCitizen.setString(1, citizen.getName());
                updateCitizen.setString(2, citizen.getAdress());
                updateCitizen.setString(3, citizen.getCity());
                updateCitizen.setInt(4, citizen.getZip());
                updateCitizen.setInt(5, citizen.getPhoneNumber());
                updateCitizen.setObject(6, citizen.getAuthorId());
                updateCitizen.setObject(7, citizen.getId());

                ResultSet citizensChangedResult = updateCitizen.executeQuery();

                while (citizensChangedResult.next()) {
                    Array sqlArrayOfDiagnoses = citizensChangedResult.getArray("diagnoses");
                    String[] stringArrayOfDiagnoses = (String[]) sqlArrayOfDiagnoses.getArray();
                    List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);

                    citizensListReturned.add(new Citizen((UUID) citizensChangedResult.getObject("id"),
                            citizensChangedResult.getString("name"),
                            citizensChangedResult.getString("adress"),
                            citizensChangedResult.getString("city"),
                            citizensChangedResult.getInt("zip"),
                            citizensChangedResult.getString("cpr"),
                            citizensChangedResult.getInt("phone"),
                            listOfDiagnoses,
                            citizensChangedResult.getBoolean("archived"),
                            citizensChangedResult.getString("date_created"),
                            (UUID) citizensChangedResult.getObject("author_id")));
                }
            }
            connection.commit();
            return citizensListReturned;
        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 

        return null;
    }

    @Override
    public Citizen createCitizen(Citizen citizen) {      
        try {
            connection.setAutoCommit(false);
            PreparedStatement createCitizen = connection.prepareStatement("INSERT INTO citizens(id, name, adress, city, zip, cpr, phone, archived, author_id) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?) RETURNING id, name, adress, city, zip, cpr, phone, archived, date_created, author_id;");
            createCitizen.setObject(1, UUID.randomUUID(), Types.OTHER);
            createCitizen.setString(2, citizen.getName());
            createCitizen.setString(3, citizen.getAdress());
            createCitizen.setString(4, citizen.getCity());
            createCitizen.setInt(5, citizen.getZip());
            createCitizen.setString(6, citizen.getCpr());
            createCitizen.setInt(7, citizen.getPhoneNumber());
            createCitizen.setBoolean(8, false);
            createCitizen.setObject(9, citizen.getAuthorId());

            ResultSet createCitizenResult = createCitizen.executeQuery();
            Citizen citizenCreated = null;

            // Most values could also be gotten from citizen (besides "id")
            while (createCitizenResult.next()) {
                citizenCreated = new Citizen((UUID) createCitizenResult.getObject("id"), createCitizenResult.getString("name"), createCitizenResult.getString("adress"),
                        createCitizenResult.getString("city"), createCitizenResult.getInt("zip"), createCitizenResult.getString("cpr"),
                        createCitizenResult.getInt("phone"), citizen.getDiagnoses(), createCitizenResult.getBoolean("archived"), createCitizenResult.getString("date_created"), (UUID) createCitizenResult.getObject("author_id"));
            }

            for (String diagnoseString : citizen.getDiagnoses()) {
                PreparedStatement setDiagnoses = connection.prepareStatement("INSERT INTO diagnose(citizens_id, diagnose) VALUES (?, ?) RETURNING diagnose;");
                setDiagnoses.setObject(1, citizenCreated.getId(), Types.OTHER);
                setDiagnoses.setString(2, diagnoseString);
                setDiagnoses.execute();
            }
            connection.commit();
            return citizenCreated;
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

}