package API.repositories;

import API.entities.Journal;
import API.entities.JournalDTO;
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
import org.apache.commons.dbutils.DbUtils;
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
    public List<Journal> getJournals() {
        List<Journal> journalList = new ArrayList();
        try (PreparedStatement getJournals = connection.prepareStatement("SELECT * FROM journal_events JOIN journals ON journal_events.journal_ID = journals.id WHERE (journal_ID, date_modified) IN (SELECT journal_ID, MAX(date_modified) FROM journal_events GROUP BY journal_ID);")) {
            /*
            PreparedStatement getJournals = connection.prepareStatement("SELECT * FROM (SELECT * FROM journals INNER JOIN journal_events ON journals.id = journal_events.journal_ID) "
                    + "as IJ INNER JOIN (SELECT journal_ID, max(date_modified) as MaxDate FROM journal_events GROUP BY journal_ID) "
                    + "AS Middle on Middle.journal_ID = IJ.journal_ID and IJ.date_modified = Middle.MaxDate;");
             */
            ResultSet journalsResult = getJournals.executeQuery();

            while (journalsResult.next()) {
                if (journalsResult.getString("type").equals("deleted")) {
                    continue;
                }
                journalList.add(new Journal((UUID) journalsResult.getObject("journal_id"), journalsResult.getString("date_start"),
                        (UUID) journalsResult.getObject("citizens_id"), journalsResult.getString("content"),
                        (UUID) journalsResult.getObject("author_id"), journalsResult.getString("date_modified")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return journalList;
    }

    @Override
    public Journal findJournal(UUID id) {
        try(PreparedStatement findJournal = connection.prepareStatement("SELECT * FROM journal_events JOIN journals ON journal_events.journal_ID = journals.id WHERE (journal_ID, date_modified) IN (SELECT journal_ID, MAX(date_modified) FROM journal_events GROUP BY journal_ID) AND journal_events.journal_ID = ?")) {
            findJournal.setObject(1, id, Types.OTHER);
            ResultSet journalResultSet = findJournal.executeQuery();
            Journal journal = null;
            while (journalResultSet.next()) {
                if (journalResultSet.getString("type").equals("deleted")) {
                    return null;
                }
                journal = new Journal((UUID) journalResultSet.getObject("id"), journalResultSet.getString("date_start"), (UUID) journalResultSet.getObject("citizens_id"), journalResultSet.getString("content"), (UUID) journalResultSet.getObject("author_id"), journalResultSet.getString("date_modified"));
            }

            if (journal != null) {
                return journal;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception...");
        }

        return null;
    }

    @Override
    public Journal findJournalByCitizen(UUID id) {
        try(PreparedStatement findJournal = connection.prepareStatement("select * from journals join journal_events on journal_events.id = (select id from journal_events where journal_events.journal_ID = journals.id order by date_modified desc limit 1) where journals.citizens_id = ? order by journals.date_start DESC LIMIT 1")) {
            findJournal.setObject(1, id, Types.OTHER);
            ResultSet journalResultSet = findJournal.executeQuery();
            Journal journal = null;
            while (journalResultSet.next()) {
                if (journalResultSet.getString("type").equals("deleted")) {
                    return null;
                }
                journal = new Journal((UUID) journalResultSet.getObject("id"), journalResultSet.getString("date_start"), (UUID) journalResultSet.getObject("citizens_id"), journalResultSet.getString("content"), (UUID) journalResultSet.getObject("author_id"), journalResultSet.getString("date_modified"));
            }

            if (journal != null) {
                return journal;
            }

        } catch (SQLException e) {
            System.out.println("SQL Exception...");
        }

        return null;
    }

    @Override
    public Journal deleteJournal(UUID id, UUID authorID) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement checker = connection.prepareStatement("SELECT type FROM journal_events WHERE (journal_ID, date_modified) IN (SELECT journal_ID, MAX(date_modified) FROM journal_events GROUP BY journal_ID) AND journal_events.journal_ID = ?;");
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
            deleteJournal.setObject(1, UUID.randomUUID());
            deleteJournal.setObject(2, id);
            deleteJournal.setString(3, "deleted");
            deleteJournal.setObject(4, authorID);

            ResultSet deletedResult = deleteJournal.executeQuery();
            Journal journal = null;

            while (deletedResult.next()) {
                journal = new Journal((UUID) deletedResult.getObject("journal_ID"), null,
                        null, null,
                        (UUID) deletedResult.getObject("author_id"), deletedResult.getString("date_modified"));
            }
            connection.commit();
            return journal;

        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(connection);
        }

        return null;

    }

    @Override
    public Journal createJournal(Journal journal) {
        try {
            connection.setAutoCommit(false); // Transaction
            PreparedStatement createJournal = connection.prepareStatement("INSERT INTO journals(id, citizens_id) VALUES (?, ?) RETURNING id, date_start, citizens_id");
            createJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            createJournal.setObject(2, journal.getCitizensID(), Types.OTHER);
            ResultSet journalResultSet = createJournal.executeQuery();
            Journal createdJournal = null;
            while (journalResultSet.next()) {
                createdJournal = new Journal((UUID) journalResultSet.getObject("id"), journalResultSet.getString("date_start"), (UUID) journalResultSet.getObject("citizens_id"), null, null, null);
            }

            PreparedStatement createEvent = connection.prepareStatement("INSERT INTO journal_events(type, content, author_id, journal_id, id) VALUES (?, ?, ?, ?, ?) RETURNING content, author_id");
            createEvent.setString(1, "created");
            createEvent.setString(2, journal.getContent());
            createEvent.setObject(3, journal.getAuthorID(), Types.OTHER);
            createEvent.setObject(4, createdJournal.getId(), Types.OTHER);
            createEvent.setObject(5, UUID.randomUUID(), Types.OTHER);

            ResultSet eventResultSet = createEvent.executeQuery();

            while (eventResultSet.next()) {
                createdJournal.setContent(eventResultSet.getString("content"));
                createdJournal.setAuthorID((UUID) eventResultSet.getObject("author_id"));
            }
            connection.commit();
            return createdJournal;

        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return null;
    }

    /*
        Param: journalDTO  
        Purpose: createsEvent from journalDTO
        Returns: Event object 
     */
    @Override
    public Journal modifyJournal(JournalDTO journalDTO) {
        try {
            PreparedStatement checker = connection.prepareStatement("SELECT type FROM journal_events WHERE (journal_ID, date_modified) IN (SELECT journal_ID, MAX(date_modified) FROM journal_events GROUP BY journal_ID) AND journal_events.journal_ID = ?;");
            checker.setObject(1, journalDTO.getId(), Types.OTHER);
            ResultSet checkResultSet = checker.executeQuery();
            String stringType = "";

            while (checkResultSet.next()) {
                stringType = checkResultSet.getString("type");
            }

            if (stringType.equals("deleted")) {
                return null;
            }

            PreparedStatement modifyJournal = connection.prepareStatement("INSERT INTO journal_events(id, type, content, author_id, journal_id) VALUES (?, ?, ?, ?, ?) RETURNING id, journal_id, content, type, date_modified, author_id");
            modifyJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            modifyJournal.setString(2, "modified");
            modifyJournal.setString(3, journalDTO.getContent());
            modifyJournal.setObject(4, journalDTO.getAuthorID(), Types.OTHER);
            modifyJournal.setObject(5, journalDTO.getId());
            Journal journal = null;
            ResultSet modifyResultSet = modifyJournal.executeQuery();
            while (modifyResultSet.next()) {
                if (modifyResultSet.getString("type").equals("deleted")) {
                    return null;
                }
                journal = new Journal((UUID) modifyResultSet.getObject("id"), null, null, modifyResultSet.getString("content"), (UUID) modifyResultSet.getObject("author_id"), modifyResultSet.getString("date_modified"));
            }

            return journal;

        } catch (SQLException ex) {
            System.out.println("Possible JournalID Error -> Custom SQL statement from code");
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(connection);
        }
        return null;
    }

}
