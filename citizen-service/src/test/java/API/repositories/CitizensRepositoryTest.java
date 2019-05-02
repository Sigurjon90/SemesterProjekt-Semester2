/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Citizen;
import java.io.IOException;
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
 * @author madsfalken
 */
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CitizensRepositoryTest {

    @InjectMocks
    private static CitizensRepository citizensRepository;

    final static EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6);
    
    private static UUID identifier;

    private final List<Citizen> citizens = Arrays.asList(
        new Citizen(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, "234312-2341", 56942412, Arrays.asList("Pyroman", "Alkoholiker"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8")),
        new Citizen(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), "Kris", "Middelfartsvej 82", "Bolbro", 1544, "234312-5643", 56942415, Arrays.asList("KaffeDrikker"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))
    );
    
    @BeforeClass
    public static void setUpBeforeClass() throws IOException, SQLException {
        final String url = postgres.start("localhost", 5431, "dbName", "username", "password");
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.createStatement().execute("create table citizens(id uuid primary key, name text, address text not null, city text not null, zip integer not null, cpr text  not null, phone integer not null, archived boolean, care_center_id uuid, date_created timestamp default timezone('utc'::text, now()), author_id uuid);");
            conn.createStatement().execute("create table diagnose(citizens_id uuid, diagnose text);");
            conn.createStatement().execute("INSERT INTO citizens(id, name, address, city, zip, cpr, phone, archived, author_id, care_center_id) VALUES ('06d0166d-56b6-4bb5-8572-9299fc87c3dc', 'Jørgen', 'Middelfartsvej 72', 'Bolbro', 1234, '234312-2341', 56942412, false , '75d988af-13d8-4513-ad27-3aa7741cc823', '66dd7224-60bf-4c3a-a4c3-82bcf18453b8')");
            conn.createStatement().execute("INSERT INTO citizens(id, name, address, city, zip, cpr, phone, archived, author_id, care_center_id) VALUES ('b6a4bdfe-3222-4956-b4a3-93ab21ba8454', 'Kris', 'Middelfartsvej 82', 'Bolbro', 1544, '234312-5643', 56942415, false , '75d988af-13d8-4513-ad27-3aa7741cc823', '66dd7224-60bf-4c3a-a4c3-82bcf18453b8')");
            conn.createStatement().execute("INSERT INTO diagnose(citizens_id, diagnose) VALUES ('06d0166d-56b6-4bb5-8572-9299fc87c3dc', 'Pyroman')");
            conn.createStatement().execute("INSERT INTO diagnose(citizens_id, diagnose) VALUES ('06d0166d-56b6-4bb5-8572-9299fc87c3dc', 'Alkoholiker')");
            conn.createStatement().execute("INSERT INTO diagnose(citizens_id, diagnose) VALUES ('b6a4bdfe-3222-4956-b4a3-93ab21ba8454', 'KaffeDrikker')");
        }
        citizensRepository = new CitizensRepository(url, "username", "password");
    }
    
    @AfterClass
    public static void afterClass() {
        postgres.stop();
    }
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**e
     * Test of getMyCitizens method, of class CitizensRepository.
     */
    @Test
    public void stage1_testGetMyCitizens() {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"));
        List<Citizen> actuals = citizensRepository.getMyCitizens(listOfCitizensIds);
        
        Citizen citizen = citizens.get(0);
        Citizen actual = actuals.get(0);
        assertThat(actual, hasProperty("id", equalTo(citizen.getId())));
        assertThat(actual, hasProperty("name", equalTo(citizen.getName())));
        assertThat(actual, hasProperty("address", equalTo(citizen.getAddress())));
        assertThat(actual, hasProperty("city", equalTo(citizen.getCity())));
        assertThat(actual, hasProperty("zip", equalTo(citizen.getZip())));
        assertThat(actual, hasProperty("phoneNumber", equalTo(citizen.getPhoneNumber())));
        assertThat(actual, hasProperty("diagnoses", equalTo(citizen.getDiagnoses())));
        assertThat(actual, hasProperty("authorId", equalTo(citizen.getAuthorId())));
        assertThat(actual, hasProperty("careCenterId", equalTo(citizen.getCareCenterId())));
    }

    /**
     * Test of getCareCenterCitizens method, of class CitizensRepository.
     */
    @Test
    public void stage2_testGetCareCenterCitizens() {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"));
        UUID careCenterId = UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8");
        List<Citizen> actuals = citizensRepository.getCareCenterCitizens(careCenterId, listOfCitizensIds);
        
        Citizen citizen = citizens.get(1);
        Citizen actual = actuals.get(0);
        assertThat(actual, hasProperty("id", equalTo(citizen.getId())));
        assertThat(actual, hasProperty("name", equalTo(citizen.getName())));
        assertThat(actual, hasProperty("address", equalTo(citizen.getAddress())));
        assertThat(actual, hasProperty("city", equalTo(citizen.getCity())));
        assertThat(actual, hasProperty("zip", equalTo(citizen.getZip())));
        assertThat(actual, hasProperty("phoneNumber", equalTo(citizen.getPhoneNumber())));
        assertThat(actual, hasProperty("diagnoses", equalTo(citizen.getDiagnoses())));
        assertThat(actual, hasProperty("authorId", equalTo(citizen.getAuthorId())));
        assertThat(actual, hasProperty("careCenterId", equalTo(citizen.getCareCenterId())));
    }

    /**
     * Test of getCitizens method, of class CitizensRepository.
     */
    @Test
    public void stage3_testGetCitizens() {
        List<Citizen> actuals = citizensRepository.getCitizens();
        
        for (int i = 0; i < actuals.size(); i++) {
            Citizen citizen = citizens.get(i);
            Citizen actual = actuals.get(i);
            assertThat(actual, hasProperty("id", equalTo(citizen.getId())));
            assertThat(actual, hasProperty("name", equalTo(citizen.getName())));
            assertThat(actual, hasProperty("address", equalTo(citizen.getAddress())));
            assertThat(actual, hasProperty("city", equalTo(citizen.getCity())));
            assertThat(actual, hasProperty("zip", equalTo(citizen.getZip())));
            assertThat(actual, hasProperty("phoneNumber", equalTo(citizen.getPhoneNumber())));
            assertThat(actual, hasProperty("diagnoses", equalTo(citizen.getDiagnoses())));
            assertThat(actual, hasProperty("authorId", equalTo(citizen.getAuthorId())));
            assertThat(actual, hasProperty("careCenterId", equalTo(citizen.getCareCenterId())));
        }
    }

    /**
     * Test of findCitizen method, of class CitizensRepository.
     */
    @Test
    public void stage4_testFindCitizen() {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        Citizen actual = citizensRepository.findCitizen(id);
        
        assertThat(actual, hasProperty("id", equalTo(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"))));
        assertThat(actual, hasProperty("name", equalTo("Jørgen")));
        assertThat(actual, hasProperty("address", equalTo("Middelfartsvej 72")));
        assertThat(actual, hasProperty("city", equalTo("Bolbro")));
        assertThat(actual, hasProperty("zip", equalTo(1234)));
        assertThat(actual, hasProperty("phoneNumber", equalTo(56942412)));
        assertThat(actual, hasProperty("diagnoses", equalTo(Arrays.asList("Pyroman", "Alkoholiker"))));
        assertThat(actual, hasProperty("archived", equalTo(false)));
        assertThat(actual, hasProperty("authorId", equalTo(UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"))));
        assertThat(actual, hasProperty("careCenterId", equalTo(UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))));
    }

    /**
     * Test of createCitizen method, of class CitizensRepository.
     */
    @Test
    public void stage5_testCreateCitizen() {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        UUID careCenterId = UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8");
        
        Citizen create = new Citizen(
                null,
                "John",
                "Vejviservej 51",
                "Vollsmose",
                5000,
                "245467-5690",
                24564367,
                Arrays.asList("Corder", "Adderall addict"),
                false,
                null,
                null,
                null
        );
        
        Citizen actual = citizensRepository.createCitizen(create, authorId, careCenterId);
        
        identifier = actual.getId(); // wacky hack - for next stages
        
        assertNotNull(actual.getId());
        assertThat(actual, hasProperty("name", equalTo("John")));
        assertThat(actual, hasProperty("address", equalTo("Vejviservej 51")));
        assertThat(actual, hasProperty("city", equalTo("Vollsmose")));
        assertThat(actual, hasProperty("zip", equalTo(5000)));
        assertThat(actual, hasProperty("phoneNumber", equalTo(24564367)));
        assertThat(actual, hasProperty("diagnoses", equalTo(Arrays.asList("Corder", "Adderall addict"))));
        assertThat(actual, hasProperty("archived", equalTo(false)));
        assertThat(actual, hasProperty("authorId", equalTo(UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"))));
        assertThat(actual, hasProperty("careCenterId", equalTo(UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))));
    }

    /**
     * Test of batchUpdate method, of class CitizensRepository.
     */
    @Test
    public void stage6_testBatchUpdate() {
        UUID authorId = UUID.fromString("bce54bdd-fb06-48c8-b1d0-a3ed9f3e0121");
        
        Citizen update = new Citizen(
                identifier,
                "John Doe",
                "Vejviservej 51",
                "Vollsmose",
                5000,
                "245467-5690",
                24564367,
                Arrays.asList("Coder", "Adderall addict", "Aggressive"),
                false,
                null,
                UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"),
                UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8")
        );
        
        List<Citizen> actuals = citizensRepository.batchUpdate(Arrays.asList(update), authorId);
        
        Citizen actual = actuals.get(0);
        assertThat(actual, hasProperty("id", equalTo(identifier)));
        assertThat(actual, hasProperty("name", equalTo("John Doe")));
        assertThat(actual, hasProperty("address", equalTo("Vejviservej 51")));
        assertThat(actual, hasProperty("city", equalTo("Vollsmose")));
        assertThat(actual, hasProperty("zip", equalTo(5000)));
        assertThat(actual, hasProperty("phoneNumber", equalTo(24564367)));
        assertThat(actual, hasProperty("diagnoses", equalTo(Arrays.asList("Coder", "Adderall addict", "Aggressive"))));
        assertThat(actual, hasProperty("archived", equalTo(false)));
        assertThat(actual, hasProperty("authorId", equalTo(UUID.fromString("bce54bdd-fb06-48c8-b1d0-a3ed9f3e0121"))));
        assertThat(actual, hasProperty("careCenterId", equalTo(UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))));
    }

    /**
     * Test of deleteCitizen method, of class CitizensRepository.
     */
    @Test
    public void stage7_testDeleteCitizen() {
        UUID id = identifier;
        UUID authorId = UUID.fromString("bce54bdd-fb06-48c8-b1d0-a3ed9f3e0121");
        
        boolean returned = citizensRepository.deleteCitizen(id, authorId);
        
        assertTrue(returned);
        
        Citizen actual = citizensRepository.findCitizen(id);
        assertNull(actual);
    }
}
