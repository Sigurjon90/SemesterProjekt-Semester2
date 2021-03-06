package API.repositories;

import API.entities.Journal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author thinkbuntu
 */
// Repository gets and gives data from the database to the controller
@Repository
public class JournalRepository implements IJournalRepository {

    private Connection connection = null;

    // @Value("${database.connection}") String connector, @Value("${database.username}") String username, @Value("${database.password}") String password
    public JournalRepository(@Value("${database.connection}") String connector, @Value("${database.username}") String username, @Value("${database.password}") String password) {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(connector, username, password);
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
    public List<Journal> getJournals(List<UUID> listOfCitizensIds) {
        List<Journal> journalList = new ArrayList();
        try (PreparedStatement getJournals = connection.prepareStatement("SELECT * FROM journal_events JOIN journals ON journal_events.journal_ID = journals.id "
                + "WHERE (journal_ID, date_modified) IN (SELECT journal_ID, MAX(date_modified) "
                + "FROM journal_events GROUP BY journal_ID) AND journals.citizens_id = ANY(?);")) {
            java.sql.Array sqlArray = connection.createArrayOf("UUID", listOfCitizensIds.toArray());
            getJournals.setArray(1, sqlArray);
            ResultSet journalsResult = getJournals.executeQuery();

            while (journalsResult.next()) {
                if (journalsResult.getString("type").equals("deleted")) {
                    continue;
                }
                journalList.add(new Journal((UUID) journalsResult.getObject("journal_id"), 
                        journalsResult.getString("date_start"),
                        (UUID) journalsResult.getObject("citizens_id"),
                        journalsResult.getString("content"),
                        (UUID) journalsResult.getObject("author_id"), 
                        journalsResult.getString("date_modified")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return journalList;
    }

    @Override
    public Journal findJournal(UUID id) {
        try(PreparedStatement findJournal = connection.prepareStatement("SELECT * FROM journals join journal_events ON journal_events.id = "
                + "(SELECT id FROM journal_events "
                + "WHERE journal_events.journal_ID = journals.id order by date_modified desc limit 1) "
                + "WHERE journals.id = ? ")) {
            findJournal.setObject(1, id, Types.OTHER);
            ResultSet journalResultSet = findJournal.executeQuery();

            while (journalResultSet.next()) {
                if (journalResultSet.getString("type").equals("deleted")) {
                    return null;
                }
                return new Journal((UUID) journalResultSet.getObject("id"), 
                        journalResultSet.getString("date_start"), 
                        (UUID) journalResultSet.getObject("citizens_id"),
                        journalResultSet.getString("content"), 
                        (UUID) journalResultSet.getObject("author_id"), 
                        journalResultSet.getString("date_modified"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception...");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Journal findJournalByCitizen(UUID id) {
        try(PreparedStatement findJournal = connection.prepareStatement("SELECT * FROM journals JOIN journal_events ON journal_events.id = "
                + "(SELECT id FROM journal_events WHERE journal_events.journal_ID = journals.id "
                + "ORDER BY date_modified DESC limit 1) "
                + "WHERE journals.citizens_id = ? ORDER BY journals.date_start DESC LIMIT 1")) {
            findJournal.setObject(1, id, Types.OTHER);
            ResultSet journalResultSet = findJournal.executeQuery();
            while (journalResultSet.next()) {
                if (journalResultSet.getString("type").equals("deleted")) {
                    return null;
                }
                return new Journal((UUID) journalResultSet.getObject("id"), 
                        journalResultSet.getString("date_start"), 
                        (UUID) journalResultSet.getObject("citizens_id"), 
                        journalResultSet.getString("paragraph"),
                        journalResultSet.getString("municipality"),
                        journalResultSet.getBoolean("consent"),
                        journalResultSet.getString("content"), 
                        (UUID) journalResultSet.getObject("author_id"), 
                        journalResultSet.getString("date_modified"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception...");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Journal deleteJournal(UUID id, UUID authorID) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement checker = connection.prepareStatement("SELECT type FROM journal_events "
                    + "WHERE (journal_ID, date_modified) IN (SELECT journal_ID, MAX(date_modified) "
                    + "FROM journal_events GROUP BY journal_ID) AND journal_events.journal_ID = ?;");
            checker.setObject(1, id, Types.OTHER);
            ResultSet checkResultSet = checker.executeQuery();
            String stringType = "";

            while (checkResultSet.next()) {
                stringType = checkResultSet.getString("type");
            }

            if (stringType.equals("deleted")) {
                return null;
            }

            PreparedStatement deleteJournal = connection.prepareStatement("INSERT into journal_events(id, journal_ID, type, author_id) VALUES (?, ?, ?, ?)"
                    + "RETURNING id, journal_ID, type, author_id, date_modified, type"); // RETURN GENERATED KEYS
            deleteJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            deleteJournal.setObject(2, id, Types.OTHER);
            deleteJournal.setString(3, "deleted");
            deleteJournal.setObject(4, authorID, Types.OTHER);

            ResultSet deletedResult = deleteJournal.executeQuery();
            Journal journal = null;

            while (deletedResult.next()) {
                journal = new Journal((UUID) deletedResult.getObject("journal_ID"), null,
                        null, null,
                        (UUID) deletedResult.getObject("author_id"), deletedResult.getString("date_modified"));
            }
            connection.commit();
            connection.setAutoCommit(true);
            return journal;

        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } 

        return null;
    }

    @Override
    public Journal createJournal(Journal journal) {
        try {
            connection.setAutoCommit(false); // Transaction
            PreparedStatement createJournal = connection.prepareStatement("INSERT INTO journals(id, citizens_id, paragraph, municipality, consent) VALUES (?, ?, ?, ?, ?) RETURNING id, date_start, citizens_id, paragraph, municipality, consent ");
            createJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            createJournal.setObject(2, journal.getCitizensID(), Types.OTHER);
            createJournal.setString(3, journal.getParagraph());
            createJournal.setString(4, journal.getMunicipality());
            createJournal.setBoolean(5, journal.isConsent());
            ResultSet journalResultSet = createJournal.executeQuery();
            Journal createdJournal = null;
            while (journalResultSet.next()) {
                createdJournal = new Journal((UUID) journalResultSet.getObject("id"), journalResultSet.getString("date_start"), (UUID) journalResultSet.getObject("citizens_id"), journalResultSet.getString("paragraph"), journalResultSet.getString("municipality"), journalResultSet.getBoolean("consent"), null, null, null);
            }

            PreparedStatement createEvent = connection.prepareStatement("INSERT INTO journal_events(type, content, author_id, journal_id, id) VALUES (?, ?, ?, ?, ?) RETURNING content, author_id, date_modified");
            createEvent.setString(1, "created");
            createEvent.setString(2, journal.getContent());
            createEvent.setObject(3, journal.getAuthorID(), Types.OTHER);
            createEvent.setObject(4, createdJournal.getId(), Types.OTHER);
            createEvent.setObject(5, UUID.randomUUID(), Types.OTHER);

            ResultSet eventResultSet = createEvent.executeQuery();

            while (eventResultSet.next()) {
                createdJournal.setContent(eventResultSet.getString("content"));
                createdJournal.setAuthorID((UUID) eventResultSet.getObject("author_id"));
                createdJournal.setDateModified(eventResultSet.getString("date_modified"));
            }
            connection.commit();
            connection.setAutoCommit(true);
            return createdJournal;

        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } 
        return null;
    }

    /*
        Param: journal  
        Purpose: createsEvent from journal
        Returns: Event object 
     */
    @Override
    public Journal modifyJournal(Journal journal) {
        try {
            PreparedStatement checker = connection.prepareStatement("SELECT type FROM journal_events "
                    + "WHERE (journal_ID, date_modified) IN (SELECT journal_ID, MAX(date_modified) "
                    + "FROM journal_events GROUP BY journal_ID) AND journal_events.journal_ID = ?;");
            checker.setObject(1, journal.getId(), Types.OTHER);
            ResultSet checkResultSet = checker.executeQuery();
            String stringType = "";

            while (checkResultSet.next()) {
                stringType = checkResultSet.getString("type");
            }

            if (stringType.equals("deleted")) {
                return null;
            }

            PreparedStatement modifyJournal = connection.prepareStatement("INSERT INTO journal_events(id, type, content, author_id, journal_id) VALUES (?, ?, ?, ?, ?) "
                    + "RETURNING id, journal_id, content, type, date_modified, author_id");
            modifyJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            modifyJournal.setString(2, "modified");
            modifyJournal.setString(3, journal.getContent());
            modifyJournal.setObject(4, journal.getAuthorID(), Types.OTHER);
            modifyJournal.setObject(5, journal.getId(), Types.OTHER);

            ResultSet modifyResultSet = modifyJournal.executeQuery();
            while (modifyResultSet.next()) {
                if (modifyResultSet.getString("type").equals("deleted")) {
                    return null;
                }
                return new Journal((UUID) modifyResultSet.getObject("journal_id"), 
                        null, 
                        null, 
                        journal.getParagraph(),
                        journal.getMunicipality(),
                        journal.isConsent(),
                        modifyResultSet.getString("content"), 
                        (UUID) modifyResultSet.getObject("author_id"), 
                        modifyResultSet.getString("date_modified"));
            }
        } catch (SQLException ex) {
            System.out.println("Possible JournalID Error -> Custom SQL statement from code");
            ex.printStackTrace();
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    @Override
    public boolean deleteDiaryByCitizenId(UUID citizenId, UUID authorID) {
        try (PreparedStatement deleteJournal = connection.prepareStatement("INSERT INTO journal_events(id, journal_ID, type, author_id) select ?, (SELECT id FROM journals WHERE citizens_id = ?), 'deleted', ? where not exists (SELECT type FROM journal_events WHERE journal_id = (SELECT id FROM journals WHERE citizens_id = ?) AND type = 'deleted') RETURNING id, journal_ID, type, author_id, date_modified")) {
            deleteJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            deleteJournal.setObject(2, citizenId, Types.OTHER);
            deleteJournal.setObject(3, authorID, Types.OTHER);
            deleteJournal.setObject(4, citizenId, Types.OTHER);
            ResultSet deletedResult = deleteJournal.executeQuery();
            while(deletedResult.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

}
