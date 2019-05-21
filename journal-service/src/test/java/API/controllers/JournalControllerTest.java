/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.JournalDTO;
import API.services.IJournalService;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.client.RestTemplate;
import security.JwtUtils;

/**
 *
 * @author sigur
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class JournalControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private IJournalService journalService;
    
    @MockBean
    private JwtUtils jwtUtils;
    
    @MockBean
    private RestTemplate restTemplate;
    
    private final List<JournalDTO> journals = Arrays.asList(
            new JournalDTO(UUID.fromString("9b0cb957-79af-40e4-bc17-199e1e42fa84"), UUID.fromString("d200ec6d-4786-488c-8e59-1ef02f01f0bf"), UUID.fromString("bec2a674-95c3-47fc-9802-db8eba9e6911"), "Journal content#1", null, null),
            new JournalDTO(UUID.fromString("ce63e565-8944-4b43-8da7-9629c9307077"), UUID.fromString("86920326-34bd-4731-b58c-3394f1961fdd"), UUID.fromString("1010bedb-88ff-4525-8ea8-cbd6a994c559"), "Journal content#2", null, null),
            new JournalDTO(UUID.fromString("63a32dd7-7246-4f79-9449-057ef3091d26"), UUID.fromString("41b935f6-ea7a-41d4-a544-cb1f4395140c"), UUID.fromString("91b2e03a-af46-4972-adba-64ac672025aa"), "Journal content#3", null, null)
    );
    
    
    public JournalControllerTest() {
    }

    /**
     * Test of getJournals method, of class JournalController.
     */
    @Test
    public void testGetJournals() throws Exception {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("9b0cb957-79af-40e4-bc17-199e1e42fa84"));
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizensIds);
        when(journalService.getJournals(listOfCitizensIds)).thenReturn(journals);
        this.mockMvc.perform(get("/").header("authorization", "Bearer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("9b0cb957-79af-40e4-bc17-199e1e42fa84")))
                .andExpect(jsonPath("$[0].citizensID", is("d200ec6d-4786-488c-8e59-1ef02f01f0bf")))
                .andExpect(jsonPath("$[0].authorID", is("bec2a674-95c3-47fc-9802-db8eba9e6911")))
                .andExpect(jsonPath("$[0].content", is("Journal content#1")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[1].id", is("ce63e565-8944-4b43-8da7-9629c9307077")))
                .andExpect(jsonPath("$[1].citizensID", is("86920326-34bd-4731-b58c-3394f1961fdd")))
                .andExpect(jsonPath("$[1].authorID", is("1010bedb-88ff-4525-8ea8-cbd6a994c559")))
                .andExpect(jsonPath("$[1].content", is("Journal content#2")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[2].id", is("63a32dd7-7246-4f79-9449-057ef3091d26")))
                .andExpect(jsonPath("$[2].citizensID", is("41b935f6-ea7a-41d4-a544-cb1f4395140c")))
                .andExpect(jsonPath("$[2].authorID", is("91b2e03a-af46-4972-adba-64ac672025aa")))
                .andExpect(jsonPath("$[2].content", is("Journal content#3")));
        
        verify(journalService, times(1)).getJournals(listOfCitizensIds);
        verifyNoMoreInteractions(journalService);
                
    }

    /**
     * Test of findJournal method, of class JournalController.
     */
    @Test
    public void testFindJournal() {
        System.out.println("findJournal");
        String stringID = "";
        JournalController instance = new JournalController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.findJournal(stringID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findJournalByCitizen method, of class JournalController.
     */
    @Test
    public void testFindJournalByCitizen() {
        System.out.println("findJournalByCitizen");
        String stringID = "";
        JournalController instance = new JournalController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.findJournalByCitizen(stringID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createJournal method, of class JournalController.
     */
    @Test
    public void testCreateJournal() {
        System.out.println("createJournal");
        HttpHeaders httpHeaders = null;
        JournalDTO journalDTO = null;
        JournalController instance = new JournalController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.createJournal(httpHeaders, journalDTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of modifyJournal method, of class JournalController.
     */
    @Test
    public void testModifyJournal() {
        System.out.println("modifyJournal");
        HttpHeaders httpHeaders = null;
        JournalDTO journalDTO = null;
        JournalController instance = new JournalController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.modifyJournal(httpHeaders, journalDTO);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteJournalByCitizenId method, of class JournalController.
     */
    @Test
    public void testDeleteJournalByCitizenId() {
        System.out.println("deleteJournalByCitizenId");
        HttpHeaders httpHeaders = null;
        String stringID = "";
        JournalController instance = new JournalController();
        ResponseEntity expResult = null;
        ResponseEntity result = instance.deleteJournalByCitizenId(httpHeaders, stringID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
