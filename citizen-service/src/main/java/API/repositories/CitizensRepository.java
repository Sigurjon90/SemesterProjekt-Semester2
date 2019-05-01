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

    private Connection connection;

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
        List<Citizen> citizenList = new ArrayList();
        try (PreparedStatement getLimited = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens WHERE id = ANY(?) AND archived = false")) {
            java.sql.Array sqlArray = connection.createArrayOf("UUID", listOfCitizensIds.toArray());
            getLimited.setArray(1, sqlArray);
            ResultSet citizensResult = getLimited.executeQuery();
            citizenList = getCitizens(citizensResult);
        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return citizenList;
    }

    @Override
    public List<Citizen> getCareCenterCitizens(UUID careCenterId, List<UUID> listOfCitizensIds) {
        List<Citizen> citizenList = new ArrayList();
        try (PreparedStatement getLimited = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens WHERE care_center_id = ? AND archived = false AND NOT(id = ANY(?))")) {
            getLimited.setObject(1, careCenterId, Types.OTHER);
            java.sql.Array sqlArray = connection.createArrayOf("UUID", listOfCitizensIds.toArray());
            getLimited.setArray(2, sqlArray);
            ResultSet citizensResult = getLimited.executeQuery();
            citizenList = getCitizens(citizensResult);
        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return citizenList;
    }

    private List<Citizen> getCitizens(ResultSet citizensResult) throws SQLException {
        List<Citizen> citizenList = new ArrayList();
        while (citizensResult.next()) {
            Array sqlArrayOfDiagnoses = citizensResult.getArray("diagnoses");
            String[] stringArrayOfDiagnoses = sqlArrayOfDiagnoses != null ? (String[]) sqlArrayOfDiagnoses.getArray() : new String[0];
            List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);

            citizenList.add(new Citizen((UUID) citizensResult.getObject("id"),
                    citizensResult.getString("name"),
                    citizensResult.getString("address"),
                    citizensResult.getString("city"),
                    citizensResult.getInt("zip"),
                    citizensResult.getString("cpr"),
                    citizensResult.getInt("phone"),
                    listOfDiagnoses,
                    citizensResult.getBoolean("archived"),
                    citizensResult.getString("date_created"),
                    (UUID) citizensResult.getObject("author_id"),
                    (UUID) citizensResult.getObject("care_center_id")));
        }
        return citizenList;
    }

    @Override
    public List<Citizen> getCitizens() {
        try (PreparedStatement getCitizens = connection.prepareStatement("SELECT *, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = citizens.id)) AS diagnoses FROM citizens WHERE archived = false")) {
            ResultSet citizensResult = getCitizens.executeQuery();
            List<Citizen> citizensList = getCitizens(citizensResult);
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
                String[] stringArrayOfDiagnoses = sqlArrayOfDiagnoses != null ? (String[]) sqlArrayOfDiagnoses.getArray() : new String[0];
                List<String> listOfDiagnoses = Arrays.asList(stringArrayOfDiagnoses);

                return new Citizen((UUID) citizenResultSet.getObject("id"),
                        citizenResultSet.getString("name"),
                        citizenResultSet.getString("address"),
                        citizenResultSet.getString("city"),
                        citizenResultSet.getInt("zip"),
                        citizenResultSet.getString("cpr"),
                        citizenResultSet.getInt("phone"),
                        listOfDiagnoses,
                        citizenResultSet.getBoolean("archived"),
                        citizenResultSet.getString("date_created"),
                        (UUID) citizenResultSet.getObject("author_id"),
                        (UUID) citizenResultSet.getObject("care_center_id"));
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception....");
            System.out.println("Read the Exeption");
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean deleteCitizen(UUID id, UUID authorId) {
        try (PreparedStatement deleteCitizen = connection.prepareStatement("UPDATE citizens SET archived = false, author_id = ? WHERE id = ?")) {
            deleteCitizen.setObject(1, authorId);
            deleteCitizen.setObject(2, id);

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
    public List<Citizen> batchUpdate(List<Citizen> citizenList, UUID authorId) {
        List<Citizen> citizensListReturned = new ArrayList<>();
        // Deleting all diagnoses
        try {
            connection.setAutoCommit(false);
            for (Citizen citizen : citizenList) {
                PreparedStatement deleteDiagnoses = this.connection.prepareStatement("DELETE FROM diagnose WHERE citizens_id = ?");
                deleteDiagnoses.setObject(1, citizen.getId());
                deleteDiagnoses.execute();

                // Adding all new diagnoses
                if (citizen.getDiagnoses() != null) {
                    for (String diagnoseString : citizen.getDiagnoses()) {
                        PreparedStatement setDiagnoses = connection.prepareStatement("INSERT INTO diagnose(citizens_id, diagnose) VALUES (?, ?) RETURNING diagnose;");
                        setDiagnoses.setObject(1, citizen.getId(), Types.OTHER);
                        setDiagnoses.setString(2, diagnoseString);
                        setDiagnoses.execute();
                    }
                }

                PreparedStatement updateCitizen = this.connection.prepareStatement("UPDATE citizens SET name = ?,"
                        + "address = ?, city = ?, zip = ?, phone = ?, author_id = ? WHERE id = ? RETURNING id, name, address, city, zip, cpr, phone, archibed, date_created, author_id, (SELECT array(SELECT diagnose FROM diagnose WHERE diagnose.citizens_id = ?)) AS diagnoses");

                updateCitizen.setString(1, citizen.getName());
                updateCitizen.setString(2, citizen.getAddress());
                updateCitizen.setString(3, citizen.getCity());
                updateCitizen.setInt(4, citizen.getZip());
                updateCitizen.setInt(5, citizen.getPhoneNumber());
                updateCitizen.setObject(6, citizen.getAuthorId());
                updateCitizen.setObject(7, citizen.getId());

                ResultSet citizensChangedResult = updateCitizen.executeQuery();

                citizensListReturned.addAll(getCitizens(citizensChangedResult));
            }
            connection.commit();
            connection.setAutoCommit(true);
            return citizensListReturned;
        } catch (SQLException ex) {
            Logger.getLogger(CitizensRepository.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Citizen createCitizen(Citizen citizen, UUID authorId, UUID careCenterId) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement createCitizen = connection.prepareStatement("INSERT INTO citizens(id, name, address, city, zip, cpr, phone, archived, author_id, care_center_id) VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?, ?) RETURNING id, name, address, city, zip, cpr, phone, archived, date_created, author_id, care_center_id;");
            createCitizen.setObject(1, UUID.randomUUID(), Types.OTHER);
            createCitizen.setString(2, citizen.getName());
            createCitizen.setString(3, citizen.getAddress());
            createCitizen.setString(4, citizen.getCity());
            createCitizen.setInt(5, citizen.getZip());
            createCitizen.setString(6, citizen.getCpr());
            createCitizen.setInt(7, citizen.getPhoneNumber());
            createCitizen.setBoolean(8, false);
            createCitizen.setObject(9, authorId, Types.OTHER);
            createCitizen.setObject(10, careCenterId, Types.OTHER);

            ResultSet createCitizenResult = createCitizen.executeQuery();
            Citizen citizenCreated = null;

            // Most values could also be gotten from citizen (besides "id")
            while (createCitizenResult.next()) {
                citizenCreated = new Citizen((UUID) createCitizenResult.getObject("id"), createCitizenResult.getString("name"), createCitizenResult.getString("address"),
                        createCitizenResult.getString("city"), createCitizenResult.getInt("zip"), createCitizenResult.getString("cpr"),
                        createCitizenResult.getInt("phone"), citizen.getDiagnoses(), createCitizenResult.getBoolean("archived"), createCitizenResult.getString("date_created"), (UUID) createCitizenResult.getObject("author_id"), (UUID) createCitizenResult.getObject("care_center_id"));
            }

            if (citizen.getDiagnoses() != null) {
                for (String diagnoseString : citizen.getDiagnoses()) {
                    PreparedStatement setDiagnoses = connection.prepareStatement("INSERT INTO diagnose(citizens_id, diagnose) VALUES (?, ?) RETURNING diagnose;");
                    setDiagnoses.setObject(1, citizenCreated.getId(), Types.OTHER);
                    setDiagnoses.setString(2, diagnoseString);
                    setDiagnoses.execute();
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return citizenCreated;
        } catch (SQLException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
