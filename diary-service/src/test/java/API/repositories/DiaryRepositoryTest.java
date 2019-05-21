/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Diary;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import static ru.yandex.qatools.embed.postgresql.distribution.Version.Main.V9_6;


/**
 *
 * @author sigur
 */
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiaryRepositoryTest {
    
    @InjectMocks
    private static DiaryRepository diaryRepository;
    
    final static EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6);
    
    private static UUID identifier;
    
    private final List<Diary> diaries = Arrays.asList(
            new Diary(UUID.fromString("e2a96b4d-edff-497b-a094-268e3b86ed28"), "Hallo", UUID.fromString("6a7a8cb3-6502-4acc-b302-72b9e30bdf8e"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), new Date(), null, "Titil"),
            new Diary(UUID.fromString("fa9af319-94fb-414a-a76a-2862808e3f1f"), "Hallo#2", UUID.fromString("ab50317c-f506-44cf-9861-a2b1c38a1f66"), UUID.fromString("aa05c216-10ac-4703-903a-c1b95c54ba05"), new Date(), null, "Titil#2")
    );
    
    public DiaryRepositoryTest() {
    }
    
    @BeforeClass
    public static void setUpBeforeClass() throws IOException, SQLException {
        final String url = postgres.start("localhost", 5431, "dbName", "username", "password");
        try(Connection conn = DriverManager.getConnection(url)){
             conn.createStatement().execute("CREATE TABLE diaries(id UUID PRIMARY KEY, content text, date_created timestamp default timezone('utc'::text, now()), author_id UUID, citizen_id UUID, date_modified timestamp, title text, archived boolean default false)");
             conn.createStatement().execute("INSERT INTO diaries(id, content, author_id, citizen_id, title, archived) VALUES('e2a96b4d-edff-497b-a094-268e3b86ed28', 'Hallo', '6a7a8cb3-6502-4acc-b302-72b9e30bdf8e', '06d0166d-56b6-4bb5-8572-9299fc87c3de', 'Titill', false)");
             conn.createStatement().execute("INSERT INTO diaries(id, content, author_id, citizen_id, title, archived) VALUES('fa9af319-94fb-414a-a76a-2862808e3f1f', 'Hallo#2', 'ab50317c-f506-44cf-9861-a2b1c38a1f66', 'aa05c216-10ac-4703-903a-c1b95c54ba05', 'Titill#2', false)");
        }
        diaryRepository = new DiaryRepository(url, "username", "password");
    }
    
    @AfterClass
    public static void afterClass(){
        postgres.stop();
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of createDiary method, of class DiaryRepository.
     */
    @Test
    public void testCreateDiary() {
//        UUID authorId = UUID.fromString("6a7a8cb3-6502-4acc-b302-72b9e30bdf8e");
//        UUID citizenId = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de");
//        
//        Diary diary = new Diary(null, "Hallo", authorId, citizenId, new Date(), null, "Titill");
//        
//        Diary actual = diaryRepository.createDiary(diary);
//        
//        identifier = actual.getCitizenID();
//                
        
    }

    /**
     * Test of findById method, of class DiaryRepository.
     */
    @Test
    public void testFindById() {
        
        identifier = UUID.fromString("e2a96b4d-edff-497b-a094-268e3b86ed28");
        
    }

    /**
     * Test of findByCitizenID method, of class DiaryRepository.
     */
    @Test
    public void testFindByCitizenID() {
        System.out.println("findByCitizenID");
        UUID citizenID = null;
        DiaryRepository instance = null;
        Optional<Diary> expResult = null;
        Optional<Diary> result = instance.findByCitizenID(citizenID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDiaries method, of class DiaryRepository.
     */
    @Test
    public void testGetDiaries() {
        
        List<UUID> listOfDiaryId = Arrays.asList(UUID.fromString("e2a96b4d-edff-497b-a094-268e3b86ed28"));
        List<Diary> actuals = diaryRepository.getDiaries(listOfDiaryId);
        
        Diary diary = diaries.get(0);
        Diary actual = actuals.get(0);
        assertThat(actual, hasProperty("id", equalTo(diary.getCitizenID())));
        assertThat(actual, hasProperty("content", equalTo(diary.getContent())));
        assertThat(actual, hasProperty("author_id", equalTo(diary.getAuthorID())));
        assertThat(actual, hasProperty("citizen_id", equalTo(diary.getCitizenID())));
        assertThat(actual, hasProperty("title", equalTo(diary.getTitle())));

    }

    /**
     * Test of updateDiary method, of class DiaryRepository.
     */
    @Test
    public void testUpdateDiary() {
        System.out.println("updateDiary");
        Diary diary = null;
        DiaryRepository instance = null;
        Optional<Diary> expResult = null;
        Optional<Diary> result = instance.updateDiary(diary);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDiary method, of class DiaryRepository.
     */
    @Test
    public void testDeleteDiary() {
        System.out.println("deleteDiary");
        UUID id = null;
        UUID authorId = null;
        DiaryRepository instance = null;
        boolean expResult = false;
        boolean result = instance.deleteDiary(id, authorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDiaryByCitizenId method, of class DiaryRepository.
     */
    @Test
    public void testDeleteDiaryByCitizenId() {
        System.out.println("deleteDiaryByCitizenId");
        UUID id = null;
        UUID authorId = null;
        DiaryRepository instance = null;
        boolean expResult = false;
        boolean result = instance.deleteDiaryByCitizenId(id, authorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
