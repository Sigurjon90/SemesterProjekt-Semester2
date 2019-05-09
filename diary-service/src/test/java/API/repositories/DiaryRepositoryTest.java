/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Diary;
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
public class DiaryRepositoryTest {
    
    public DiaryRepositoryTest() {
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
     * Test of createDiary method, of class DiaryRepository.
     */
    @Test
    public void testCreateDiary() {
        System.out.println("createDiary");
        Diary diary = null;
        DiaryRepository instance = null;
        Optional<Diary> expResult = null;
        Optional<Diary> result = instance.createDiary(diary);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findById method, of class DiaryRepository.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        UUID id = null;
        DiaryRepository instance = null;
        Optional<Diary> expResult = null;
        Optional<Diary> result = instance.findById(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        System.out.println("getDiaries");
        List<UUID> listOfCitizensIds = null;
        DiaryRepository instance = null;
        List<Diary> expResult = null;
        List<Diary> result = instance.getDiaries(listOfCitizensIds);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
