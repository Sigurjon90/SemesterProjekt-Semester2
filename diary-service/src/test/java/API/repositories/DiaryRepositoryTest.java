/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Diary;
import java.io.IOException;
import java.util.Optional;
import org.junit.After;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
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
public class DiaryRepositoryTest {
/*
    public DiaryRepositoryTest() {
    }
    @InjectMocks
    private static DiaryRepository diaryRepository;

    final static EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6);

    private static UUID identifier;

    private final UUID citizensId = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de");
    private final UUID authorIDDiary = UUID.fromString("06d0166d-46b6-4bb5-8572-9299fc87c3dc");
    private final UUID diaryId = UUID.fromString("e2a96b4d-edff-497b-a094-268e3b86ed28");
    private final Date date = new Date();

    private final List<Diary> diaries = Arrays.asList(
            new Diary(UUID.fromString("5577fa4b-56b9-4d85-9323-8edc58fb7e6b"), "abba", UUID.fromString("16fe369d-ad7f-49c6-b87f-63e10178d352"), UUID.fromString("9970ebb5-c403-4404-84ef-d6d80973f755"), new Date(System.currentTimeMillis()), null, "titel22"),
            new Diary(UUID.fromString("8dd18834-88de-4380-94e9-acf1c2506d3c"), "walla", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dd"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3ff"), new Date(System.currentTimeMillis()), null, "titel33"));

    @BeforeClass
    public static void setUpBeforeClass() throws IOException, SQLException {
        final String url = postgres.start("localhost", 5433, "dbName", "username", "password");
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.createStatement().execute("DROP table diaries");
            conn.createStatement().execute("create table diaries(id uuid not null primary key, content text,  date_created  timestamp default timezone('utc'::text, now()), author_id uuid, citizen_id uuid, date_modified timestamp, title text, archived boolean default false );");
            conn.createStatement().execute("INSERT INTO diaries(id, content, author_ID, citizen_ID) VALUES ('5577fa4b-56b9-4d85-9323-8edc58fb7e6b', 'abba', '16fe369d-ad7f-49c6-b87f-63e10178d352', '9970ebb5-c403-4404-84ef-d6d80973f755')");
            conn.createStatement().execute("INSERT INTO diaries(id, content, author_ID, citizen_ID) VALUES ('904847cd-664f-497b-a45e-3b411077fafa', 'ja', '53377ccf-2ddd-436a-ac99-dfc19cb01cd9', '47a1b138-b521-474c-ab3c-d0e4451d5bd2')");
        }
        diaryRepository = new DiaryRepository(url, "username", "password");
    }

    @AfterClass
    public static void afterClass() {
        postgres.stop();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
*/
    /**
     * Test of createDiary method, of class DiaryRepository.
     */
    /*
    @Test
    public void testCreateDiary() {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");

        Diary create = new Diary(null, "hallo", authorIDDiary, citizensId, this.date, null, "titil");

        Diary actual;
        actual = diaryRepository.createDiary(Optional.of(Arrays.asList(create)));

        identifier = actual.getId();

        assertNotNull(actual.getId());
        assertThat(actual, hasProperty("content", equalTo("hallo")));
        assertThat(actual, hasProperty("author_id", equalTo(authorIDDiary)));
        assertThat(actual, hasProperty("citizen_id", equalTo(citizensId)));
    }

    /**
     * Test of findById method, of class DiaryRepository.
     */
    /*
    @Test
    public void testFindById() {
        UUID id = UUID.fromString("5577fa4b-56b9-4d85-9323-8edc58fb7e6b");

        Optional<Diary> actual = diaryRepository.findById(id);

        assertThat(actual, hasProperty("id", equalTo(UUID.fromString("5577fa4b-56b9-4d85-9323-8edc58fb7e6b"))));
        assertThat(actual, hasProperty("content", equalTo("hallo")));
        assertThat(actual, hasProperty("author_id", equalTo(authorIDDiary)));
        assertThat(actual, hasProperty("citizen_id", equalTo(citizensId)));
        assertThat(actual, hasProperty("title", equalTo("titil")));
    }

    /**
     * Test of findByCitizenID method, of class DiaryRepository.
     */
    /*
    @Test
    public void testFindByCitizenID() {

        fail("The test case is a prototype.");
    }

    /**
     * Test of getDiaries method, of class DiaryRepository.
     */
    /*
    @Test
    public void testGetDiaries() {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("5577fa4b-56b9-4d85-9323-8edc58fb7e6b"));
        List<Diary> actuals = diaryRepository.getDiaries(listOfCitizensIds);

        
            Diary diary = diaries.get(0);
            Diary actual = actuals.get(0);
            assertThat(actual, hasProperty("id", equalTo(diary.getId())));
            assertThat(actual, hasProperty("content", equalTo(diary.getContent())));
            assertThat(actual, hasProperty("author_id", equalTo(diary.getAuthorID())));
            assertThat(actual, hasProperty("citizen_id", equalTo(diary.getCitizenID())));
            assertThat(actual, hasProperty("title", equalTo(diary.getTitle())));
            
        }

        /**
         * Test of updateDiary method, of class DiaryRepository.
         */
    /*
        @Test
        public void testUpdateDiary
        
        
            () {

        UUID authorId = UUID.fromString("bce54bdd-fb06-48c8-b1d0-a3ed9f3e0121");

            Diary update = new Diary(diaryId, "hallo", authorIDDiary, citizensId, this.date, null, "titil");

            List<Diary> actuals = diaryRepository.updateDiary(Optional.of(update));

            Diary actual = actuals.get(0);

            assertThat(actual, hasProperty("id", equalTo(diaryId)));
            assertThat(actual, hasProperty("content", equalTo("hallo")));
            assertThat(actual, hasProperty("author_id", equalTo(authorIDDiary)));
            assertThat(actual, hasProperty("citizen_id", equalTo(citizensId)));
            assertThat(actual, hasProperty("title", equalTo("titil")));
        }

        /**
         * Test of deleteDiary method, of class DiaryRepository.
         */
    /*
        @Test
        public void testDeleteDiary
        
        
            () {
        UUID id = identifier;
            UUID authorId = UUID.fromString("bce54bdd-fb06-48c8-b1d0-a3ed9f3e0121");

            boolean returned = diaryRepository.deleteDiary(id, authorId);

            assertTrue(returned);

            Optional<Diary> actual = diaryRepository.findById(id);
            assertNull(actual);
        }

        /**
         * Test of deleteDiaryByCitizenId method, of class DiaryRepository.
         */
    /*
        @Test
        public void testDeleteDiaryByCitizenId
        
        
            () {

        fail("The test case is a prototype.");
        }
*/
    }
