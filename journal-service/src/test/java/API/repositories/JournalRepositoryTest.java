/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Journal;
import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.After;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;

/**
 *
 * @author Jonas
 */
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JournalRepositoryTest {

    public JournalRepositoryTest() {
    }
    @InjectMocks
    private static JournalRepository journalRepository;

    final static EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6);

    private static UUID identifier;

    private final List<Journal> journal = Arrays.asList(
            new Journal(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), null, UUID.fromString("3b532c39-fa61-494b-9537-5dde37ffc6b5"), "indhold", UUID.fromString("4f72e44c-76a6-4349-80b2-70a02fcca537"), null),
            new Journal(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), null, UUID.fromString("3b532c39-fa61-494b-9537-5dde37ffc7c4"), "indhold1", UUID.fromString("4f72e44c-76a6-4349-80b2-70a02fcca446"), null)
    );

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, SQLException {
        final String url = postgres.start("localhost", 5431, "dbName", "username", "password");
        try (Connection conn = DriverManager.getConnection(url)) {

        }
        journalRepository = new JournalRepository(url, "username", "password");
    }

    @AfterClass
    public static void afterClass() {
        postgres.stop();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getJournals method, of class JournalRepository.
     */
    @Test
    public void testGetJournals() {
        System.out.println("getJournals");
        List<UUID> listOfCitizensIds = null;
        JournalRepository instance = null;
        List<Journal> expResult = null;
        List<Journal> result = instance.getJournals(listOfCitizensIds);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findJournal method, of class JournalRepository.
     */
    @Test
    public void testFindJournal() {
        System.out.println("findJournal");
        UUID id = null;
        JournalRepository instance = null;
        Journal expResult = null;
        Journal result = instance.findJournal(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findJournalByCitizen method, of class JournalRepository.
     */
    @Test
    public void testFindJournalByCitizen() {
        System.out.println("findJournalByCitizen");
        UUID id = null;
        JournalRepository instance = null;
        Journal expResult = null;
        Journal result = instance.findJournalByCitizen(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteJournal method, of class JournalRepository.
     */
    @Test
    public void testDeleteJournal() {
        System.out.println("deleteJournal");
        UUID id = null;
        UUID authorID = null;
        JournalRepository instance = null;
        Journal expResult = null;
        Journal result = instance.deleteJournal(id, authorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createJournal method, of class JournalRepository.
     */
    @Test
    public void testCreateJournal() {
        System.out.println("createJournal");
        Journal journal = null;
        JournalRepository instance = null;
        Journal expResult = null;
        Journal result = instance.createJournal(journal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modifyJournal method, of class JournalRepository.
     */
    @Test
    public void testModifyJournal() {
        System.out.println("modifyJournal");
        Journal journal = null;
        JournalRepository instance = null;
        Journal expResult = null;
        Journal result = instance.modifyJournal(journal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDiaryByCitizenId method, of class JournalRepository.
     */
    @Test
    public void testDeleteDiaryByCitizenId() {
        System.out.println("deleteDiaryByCitizenId");
        UUID citizenId = null;
        UUID authorID = null;
        JournalRepository instance = null;
        boolean expResult = false;
        boolean result = instance.deleteDiaryByCitizenId(citizenId, authorID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
