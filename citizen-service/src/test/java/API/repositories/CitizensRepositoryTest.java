/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Citizen;
import java.sql.Array;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author madsfalken
 */
@RunWith(MockitoJUnitRunner.class)
public class CitizensRepositoryTest {

    @InjectMocks
    private CitizensRepository citizensRepository;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    private final List<Citizen> citizens = Arrays.asList(
        new Citizen(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, "234312-2341", 56942412, null, false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8")),
        new Citizen(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), "Kris", "Middelfartsvej 82", "Bolbro", 1544, "234312-5643", 56942415, null, false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))
    );
    
    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connection.prepareStatement(any(String.class))).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        Array diagonsisOne = connection.createArrayOf("UUID", new String[]{ "Mongol" });
        Array diagonsisTwo = connection.createArrayOf("UUID", new String[]{ "Mongol", "Deaf" });
        when(rs.getArray("diagnoses")).thenReturn(null).thenReturn(null);
        when(rs.getObject("id")).thenReturn(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc")).thenReturn(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"));
        when(rs.getString("name")).thenReturn("Jørgen").thenReturn("Kris");
        when(rs.getString("address")).thenReturn("Middelfartsvej 72").thenReturn("Middelfartsvej 82");
        when(rs.getString("city")).thenReturn("Bolbro").thenReturn("Bolbro");
        when(rs.getInt("zip")).thenReturn(1234).thenReturn(1234);
        when(rs.getString("cpr")).thenReturn("245412-4543").thenReturn("245412-4512");
        when(rs.getInt("phone")).thenReturn(56942412).thenReturn(56942415);
        when(rs.getBoolean("archived")).thenReturn(false).thenReturn(false);
        when(rs.getString("date_created")).thenReturn(new Date(System.currentTimeMillis()).toString()).thenReturn(new Date(System.currentTimeMillis()).toString());
        when(rs.getObject("author_id")).thenReturn(UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823")).thenReturn(UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"));
        when(rs.getObject("care_center_id")).thenReturn(UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8")).thenReturn(UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"));
    }

    /**
     * Test of getMyCitizens method, of class CitizensRepository.
     */
    @Test
    public void testGetMyCitizens() throws SQLException {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"));
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Citizen> actual = this.citizensRepository.getMyCitizens(listOfCitizensIds);
        
        assertThat(actual, is(citizens));
    }

    /**
     * Test of getCareCenterCitizens method, of class CitizensRepository.
     */
    @Test
    public void testGetCareCenterCitizens() throws SQLException {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"));
        UUID careCenterId = UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8");
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Citizen> actual = this.citizensRepository.getCareCenterCitizens(careCenterId, listOfCitizensIds);
        
        assertThat(actual, is(citizens));
    }

    /**
     * Test of getCitizens method, of class CitizensRepository.
     */
    @Test
    public void testGetCitizens() throws SQLException {
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Citizen> actual = this.citizensRepository.getCitizens();
        
        assertThat(actual, is(citizens));
    }

    /**
     * Test of findCitizen method, of class CitizensRepository.
     */
    @Test
    public void testFindCitizen() throws SQLException {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        when(rs.next()).thenReturn(true).thenReturn(false);
        Citizen actual = this.citizensRepository.findCitizen(id);
        
        assertThat(actual, is(citizens.get(0)));
    }

    /**
     * Test of deleteCitizen method, of class CitizensRepository.
     */
    @Test
    public void testDeleteCitizen() throws SQLException {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        when(stmt.executeUpdate()).thenReturn(1);
        
        boolean actual = this.citizensRepository.deleteCitizen(id, authorId);
        
        assertTrue(actual);
    }

    /**
     * Test of batchUpdate method, of class CitizensRepository.
     */
    @Test
    public void testBatchUpdate() throws SQLException {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        
        List<Citizen> actual = this.citizensRepository.batchUpdate(citizens, authorId);
        
        assertThat(actual, is(citizens));
    }

    /**
     * Test of createCitizen method, of class CitizensRepository.
     */
    @Test
    public void testCreateCitizen() throws SQLException {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        UUID careCenterId = UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8");
        when(rs.next()).thenReturn(true).thenReturn(false);
        
        Citizen actual = this.citizensRepository.createCitizen(citizens.get(0), authorId, careCenterId);
        
        assertThat(actual, is(citizens.get(0)));
    }

}
