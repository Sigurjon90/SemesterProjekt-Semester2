package API.repositories;

import API.entities.Event;
import API.entities.Journal;
import API.entities.JournalDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class JournalRepository {

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

    public List<Journal> getJournals() {
        try {
            PreparedStatement getJournals = connection.prepareStatement("SELECT * FROM (SELECT * FROM journals INNER JOIN journal_events ON journals.id = journal_events.journal_ID) "
                    + "as IJ INNER JOIN (SELECT journal_ID, max(date_modified) as MaxDate FROM journal_events GROUP BY journal_ID) " // modified = stamp
                    + "AS Middle on Middle.journal_ID = IJ.journal_ID and IJ.date_modified = Middle.MaxDate;");
            ResultSet journalsResult = getJournals.executeQuery();
            List<Journal> journalList = new ArrayList();
            
            while(journalsResult.next()) {
                journalList.add(new Journal((UUID) journalsResult.getObject("journal_id"), journalsResult.getDate("date_start"), 
                        (UUID) journalsResult.getObject("citizens_id"), journalsResult.getString("content"), 
                        (UUID) journalsResult.getObject("author_id"), journalsResult.getString("date_modified"))); // Modi
            }
            
            return journalList;
            
        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Journal findJournal(UUID id) {
        try {
            PreparedStatement findJournal = connection.prepareStatement("SELECT * FROM journals WHERE id = '" + id + "'");
            ResultSet journalResultSet = findJournal.executeQuery();
            Journal journal = null;
            while (journalResultSet.next()) {
                journal = new Journal((UUID) journalResultSet.getObject("id"), null, null, null, null, null);
            }

            return journal;

        } catch (SQLException e) {
            System.out.println("SQL Exception...");
        }

        return null;
    }

    public void deleteJournal(UUID id) {
        try {

            // PreparedStatement findJournal = connection.prepareStatement("SELECT * FROM journals WHERE id = '" + id + "'");
            PreparedStatement deleteJournal = connection.prepareStatement("DELETE FROM journals WHERE id = '" + id + "'");
            // ResultSet journalResultSet = findJournal.executeQuery();
            //deleteJournal.setObject(1, (UUID)id); // ----> SQL INJECTION MAAAAAN
            //Journal journal = null;
            /*while (journalResultSet.next()) { 
                journal = new Journal((UUID) journalResultSet.getObject("id"), null);
            } */

            deleteJournal.executeQuery();
            //return journal;

        } catch (SQLException e) {
            System.out.println("ID was not found in database");
        }
        //return null;
    }

    public Journal createJournal(Journal journal) {
        try {
            connection.setAutoCommit(false); // Transaction
            PreparedStatement createJournal = connection.prepareStatement("INSERT INTO journals(id, citizens_id) VALUES (?, ?) RETURNING id, date_start, citizens_id");
            createJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            createJournal.setObject(2, journal.getCitizensID(), Types.OTHER);
            ResultSet journalResultSet = createJournal.executeQuery();
            Journal createdJournal = null;
            while (journalResultSet.next()) {
                createdJournal = new Journal((UUID) journalResultSet.getObject("id"), journalResultSet.getDate("date_start"), (UUID) journalResultSet.getObject("citizens_id"), null, null, null);
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
        }
        return null;
    }

    /*
        Param: journalDTO  
        Purpose: createsEvent from journalDTO
        Returns: Event object 
     */
    public Event modifyJournal(JournalDTO journalDTO) {

        try {
            PreparedStatement modifyJournal = connection.prepareStatement("INSERT INTO journal_events(id, type, content, author_id, journal_id) VALUES (?, ?, ?, ?, ?) RETURNING id, type, content, author_id, journal_id, date_stamp");
            modifyJournal.setObject(1, UUID.randomUUID(), Types.OTHER);
            modifyJournal.setString(2, "modified");
            modifyJournal.setString(3, journalDTO.getContent());
            modifyJournal.setObject(4, journalDTO.getAuthorID(), Types.OTHER);
            modifyJournal.setObject(5, journalDTO.getId(), Types.OTHER);
            Event event = null;
            ResultSet eventResultSet = modifyJournal.executeQuery();
            while (eventResultSet.next()) {
                event = new Event((UUID) eventResultSet.getObject("id"), (UUID) eventResultSet.getObject("journal_id"), eventResultSet.getString("content"), eventResultSet.getString("type"), eventResultSet.getDate("date_modified"), (UUID) eventResultSet.getObject("author_id")); //stamp
            }

            return event;

        } catch (SQLException ex) {
            Logger.getLogger(JournalRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
