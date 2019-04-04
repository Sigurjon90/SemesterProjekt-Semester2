/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Diary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jonas
 */
@Repository
public class DiaryRepository implements IDiaryRepository {

    private Connection connection;

    public DiaryRepository(@Value("${database.connection}") String connection,
                           @Value("${database.username}") String username,
                           @Value("${database.password}") String password) {

        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(connection, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void saveDiary(Diary diary) {
    }

    @Override
    public Optional<Diary> findById(UUID Id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Diary> getDiaries() {
        List<Diary> diaries = new ArrayList<>();
        try {
            PreparedStatement diariesLookup = this.connection.prepareStatement("SELECT * FROM diaries");
            ResultSet dairiesResult = diariesLookup.executeQuery();
            while (dairiesResult.next()) {
                diaries.add(new Diary(
                        (UUID) dairiesResult.getObject("id"),
                        dairiesResult.getString("content"),
                        (UUID) dairiesResult.getObject("author_ID"),
                        (UUID) dairiesResult.getObject("citizen_ID"),
                        dairiesResult.getDate("date_created"),
                        dairiesResult.getDate("date_modified")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaries;

    }

}
