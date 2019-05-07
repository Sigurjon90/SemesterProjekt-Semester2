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
    public Optional<Diary> createDiary(Diary diary) {
        try (PreparedStatement diaryCreate = this.connection.prepareStatement(
                    "INSERT INTO Diaries(id, content, author_id, citizen_id, title) Values(?, ?, ?, ?, ?) returning id, title, content, author_ID, citizen_ID, date_created, date_modified")) {
            diaryCreate.setObject(1, UUID.randomUUID(), Types.OTHER);
            diaryCreate.setString(2, diary.getContent());
            diaryCreate.setObject(3, diary.getAuthorID(), Types.OTHER);
            diaryCreate.setObject(4, diary.getCitizenID(), Types.OTHER);
            diaryCreate.setString(5, diary.getTitle());

            ResultSet diaryResult = diaryCreate.executeQuery();
            while (diaryResult.next()) {
                return Optional.of(new Diary(
                        (UUID) diaryResult.getObject("id"),
                        diaryResult.getString("content"),
                        (UUID) diaryResult.getObject("author_id"),
                        (UUID) diaryResult.getObject("citizen_id"),
                        diaryResult.getDate("date_created"),
                        diaryResult.getString("date_modified"),
                        diaryResult.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Diary> findById(UUID id) {
        try (PreparedStatement diaryLookup = this.connection.prepareStatement(
                    "SELECT * FROM diaries WHERE id = ? AND archived = false")) {
            diaryLookup.setObject(1, id, Types.OTHER);
            ResultSet diaryResult = diaryLookup.executeQuery();
            while (diaryResult.next()) {
                return Optional.of(new Diary(
                        (UUID) diaryResult.getObject("id"),
                        diaryResult.getString("content"),
                        (UUID) diaryResult.getObject("author_id"),
                        (UUID) diaryResult.getObject("citizen_id"),
                        diaryResult.getDate("date_created"),
                        diaryResult.getString("date_modified"),
                        diaryResult.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Diary> findByCitizenID(UUID citizenID) {
        try (PreparedStatement diaryLookup = this.connection.prepareStatement(
                    "SELECT * FROM diaries WHERE citizen_id = ? AND archived = false")) { 
            diaryLookup.setObject(1, citizenID, Types.OTHER);
            ResultSet diaryResult = diaryLookup.executeQuery();
            while (diaryResult.next()) {
                return Optional.of(new Diary(
                        (UUID) diaryResult.getObject("id"),
                        diaryResult.getString("content"),
                        (UUID) diaryResult.getObject("author_id"),
                        (UUID) diaryResult.getObject("citizen_id"),
                        diaryResult.getDate("date_created"),
                        diaryResult.getString("date_modified"),
                        diaryResult.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Diary> getDiaries(List<UUID> listOfCitizensIds) {
        List<Diary> diaries = new ArrayList<>();
        try (PreparedStatement diariesLookup = this.connection.prepareStatement("SELECT * FROM diaries WHERE archived = false AND citizen_ID = ANY(?)")) {
            Array citizensArray = connection.createArrayOf("UUID", listOfCitizensIds.toArray());
            diariesLookup.setArray(1, citizensArray);
            ResultSet dairiesResult = diariesLookup.executeQuery();
            while (dairiesResult.next()) {
                diaries.add(new Diary(
                        (UUID) dairiesResult.getObject("id"),
                        dairiesResult.getString("content"),
                        (UUID) dairiesResult.getObject("author_ID"),
                        (UUID) dairiesResult.getObject("citizen_ID"),
                        dairiesResult.getDate("date_created"),
                        dairiesResult.getString("date_modified"),
                        dairiesResult.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaries;

    }

    @Override
    public Optional<Diary> updateDiary(Diary diary) {
        try (PreparedStatement diaryUpdate = this.connection.prepareStatement(
                    "UPDATE Diaries SET content = ?, author_ID = ?, date_modified = ? WHERE citizen_id = ? returning id, content, author_ID, citizen_ID, date_created, date_modified, title")) {
            diaryUpdate.setString(1, diary.getContent());
            diaryUpdate.setObject(2, diary.getAuthorID(), Types.OTHER);
            diaryUpdate.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            diaryUpdate.setObject(4, diary.getCitizenID(), Types.OTHER);
            ResultSet updateDiaryResult = diaryUpdate.executeQuery();
            while (updateDiaryResult.next()) {
                return Optional.of(new Diary(
                        (UUID) updateDiaryResult.getObject("id"),
                        updateDiaryResult.getString("content"),
                        (UUID) updateDiaryResult.getObject("author_id"),
                        (UUID) updateDiaryResult.getObject("citizen_id"),
                        updateDiaryResult.getDate("date_created"),
                        updateDiaryResult.getString("date_modified"),
                        updateDiaryResult.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteDiary(UUID id, UUID authorId) {
        try (PreparedStatement deleteDiary = this.connection.prepareStatement(
                "UPDATE diaries SET archived = true, author_id = ? WHERE citizen_id = ?")) {
            deleteDiary.setObject(2, id, Types.OTHER);
            deleteDiary.setObject(1, authorId, Types.OTHER);
            int affectedRows = deleteDiary.executeUpdate();
            if (affectedRows == 0) return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteDiaryByCitizenId(UUID id, UUID authorId) {
        try (PreparedStatement deleteDiary = this.connection.prepareStatement(
                "UPDATE diaries SET archived = true, author_id = ? WHERE citizen_ID = ?")) {
            deleteDiary.setObject(2, id, Types.OTHER);
            deleteDiary.setObject(1, authorId, Types.OTHER);
            int affectedRows = deleteDiary.executeUpdate();
            if (affectedRows == 0) return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return false;
        }
        return true;
    }
}
