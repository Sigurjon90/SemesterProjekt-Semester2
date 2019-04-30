/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.GetCitizensDTO;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author madsfalken
 */
public class CitizensServiceTest {
    
    public CitizensServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createCitizen method, of class CitizensService.
     */
    @Test
    public void testCreateCitizen() {
        System.out.println("createCitizen");
        CreateDTO createDTO = null;
        UUID authorId = null;
        CitizensService instance = new CitizensService();
        CitizenDTO expResult = null;
        CitizenDTO result = instance.createCitizen(createDTO, authorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCitizens method, of class CitizensService.
     */
    @Test
    public void testGetCitizens() {
        System.out.println("getCitizens");
        CitizensService instance = new CitizensService();
        List<CitizenDTO> expResult = null;
        List<CitizenDTO> result = instance.getCitizens();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMyCitizens method, of class CitizensService.
     */
    @Test
    public void testGetMyCitizens() {
        System.out.println("getMyCitizens");
        List<UUID> listOfCitizensIds = null;
        UUID careCenterId = null;
        CitizensService instance = new CitizensService();
        GetCitizensDTO expResult = null;
        GetCitizensDTO result = instance.getMyCitizens(listOfCitizensIds, careCenterId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCitizen method, of class CitizensService.
     */
    @Test
    public void testFindCitizen() {
        System.out.println("findCitizen");
        UUID id = null;
        CitizensService instance = new CitizensService();
        CitizenDTO expResult = null;
        CitizenDTO result = instance.findCitizen(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of batchUpdate method, of class CitizensService.
     */
    @Test
    public void testBatchUpdate() {
        System.out.println("batchUpdate");
        List<CitizenDTO> citizenDTOList = null;
        UUID authorId = null;
        CitizensService instance = new CitizensService();
        List<CitizenDTO> expResult = null;
        List<CitizenDTO> result = instance.batchUpdate(citizenDTOList, authorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCitizen method, of class CitizensService.
     */
    @Test
    public void testDeleteCitizen() {
        System.out.println("deleteCitizen");
        UUID id = null;
        UUID authorId = null;
        CitizensService instance = new CitizensService();
        boolean expResult = false;
        boolean result = instance.deleteCitizen(id, authorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
