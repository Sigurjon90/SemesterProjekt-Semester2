/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.DiaryDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonas
 */
public class DiaryServiceTest {
    
    public DiaryServiceTest() {
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
     * Test of createDiary method, of class DiaryService.
     */
    @Test
    public void testCreateDiary() {
        System.out.println("createDiary");
        DiaryDTO diaryDTO = null;
        DiaryService instance = new DiaryService();
        Optional<DiaryDTO> expResult = null;
        Optional<DiaryDTO> result = instance.createDiary(diaryDTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findById method, of class DiaryService.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        UUID Id = null;
        DiaryService instance = new DiaryService();
        Optional<DiaryDTO> expResult = null;
        Optional<DiaryDTO> result = instance.findById(Id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findByCitizenId method, of class DiaryService.
     */
    @Test
    public void testFindByCitizenId() {
        System.out.println("findByCitizenId");
        UUID id = null;
        DiaryService instance = new DiaryService();
        Optional<DiaryDTO> expResult = null;
        Optional<DiaryDTO> result = instance.findByCitizenId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDiaries method, of class DiaryService.
     */
    @Test
    public void testGetDiaries() {
        System.out.println("getDiaries");
        List<UUID> listOfCitizensIds = null;
        DiaryService instance = new DiaryService();
        List<DiaryDTO> expResult = null;
        List<DiaryDTO> result = instance.getDiaries(listOfCitizensIds);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateDiary method, of class DiaryService.
     */
    @Test
    public void testUpdateDiary() {
        System.out.println("updateDiary");
        DiaryDTO diaryDTO = null;
        DiaryService instance = new DiaryService();
        Optional<DiaryDTO> expResult = null;
        Optional<DiaryDTO> result = instance.updateDiary(diaryDTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDiary method, of class DiaryService.
     */
    @Test
    public void testDeleteDiary() {
        System.out.println("deleteDiary");
        UUID id = null;
        UUID authorId = null;
        DiaryService instance = new DiaryService();
        boolean expResult = false;
        boolean result = instance.deleteDiary(id, authorId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
