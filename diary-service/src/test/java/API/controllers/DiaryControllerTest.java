/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.Diary;
import API.entities.DiaryDTO;
import API.services.DiaryService;
import API.services.IDiaryService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
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
public class DiaryControllerTest {

    public DiaryControllerTest() {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDiaryService diaryService;

    @MockBean
    private JwtUtils jwtUtils;
    
    @MockBean
    private RestTemplate restTemplate;

    private Date date = new Date();

    private final List<DiaryDTO> diaryDTO = java.util.Arrays.asList(
            new DiaryDTO(UUID.fromString("06d0166d-46b6-4bb5-8572-9299fc87c3dc"), "abba", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dd"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), "titel", this.date, null),
            new DiaryDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3df"), "lmao", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3da"), "titel1", this.date, null)
    );

    /**
     * Test of getDiaries method, of class DiaryController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDiaries() throws Exception {

        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3da"));
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizensIds);
        when(diaryService.getDiaries(listOfCitizensIds)).thenReturn(diaryDTO);
        this.mockMvc.perform(get("/").header("authorization", "Bearer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("06d0166d-46b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[0].content", is("abba")))
                .andExpect(jsonPath("$[0].authorID", is("06d0166d-56b6-4bb5-8572-9299fc87c3dd")))
                .andExpect(jsonPath("$[0].citizenID", is("06d0166d-56b6-4bb5-8572-9299fc87c3de")))
                .andExpect(jsonPath("$[0].title", is("titel")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[1].id", is("06d0166d-56b6-4bb5-8572-9299fc87c3df")))
                .andExpect(jsonPath("$[1].content", is("lmao")))
                .andExpect(jsonPath("$[1].authorID", is("06d0166d-56b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[1].citizenID", is("06d0166d-56b6-4bb5-8572-9299fc87c3da")))
                .andExpect(jsonPath("$[1].title", is("titel1")));

        verify(diaryService, times(1)).getDiaries(listOfCitizensIds);
        verifyNoMoreInteractions(diaryService);
    }

    /**
     * Test of getDiary method, of class DiaryController.
     */
    @Test
    public void testGetDiary() throws Exception {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3da"));
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizensIds);
        when(diaryService.getDiaries(listOfCitizensIds)).thenReturn(diaryDTO);
        this.mockMvc.perform(get("/").header("authorization", "Bearer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("06d0166d-46b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[0].content", is("abba")))
                .andExpect(jsonPath("$[0].authorID", is("06d0166d-56b6-4bb5-8572-9299fc87c3dd")))
                .andExpect(jsonPath("$[0].citizenID", is("06d0166d-56b6-4bb5-8572-9299fc87c3de")))
                .andExpect(jsonPath("$[0].title", is("titel")));
        verify(diaryService, times(1)).getDiaries(listOfCitizensIds);
        verifyNoMoreInteractions(diaryService);

    }

    /**
     * Test of getByCitizenID method, of class DiaryController.
     */
    @Test
    public void testGetByCitizenID() throws Exception {
        List<UUID> listOfCitizenIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"));
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizenIds);
        when(diaryService.getDiaries(listOfCitizenIds)).thenReturn(diaryDTO);
        this.mockMvc.perform(get("/").header("authorization", "Bearer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].title", is("titel")))
                .andExpect(jsonPath("$[0].content", is("abba")));
        verify(diaryService, times(1)).getDiaries(listOfCitizenIds);
        verifyNoMoreInteractions(diaryService);
    }

    /**
     * Test of createDiary method, of class DiaryController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCreateDiary() throws Exception {

        UUID citizensId = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de");
        UUID authorIDDiary = UUID.fromString("6a7a8cb3-6502-4acc-b302-72b9e30bdf8e");

        DiaryDTO diaryDTO = new DiaryDTO(UUID.fromString("e7c41f9e-cace-4f4d-845f-f3c4ed3fdaf9"), "Halló", authorIDDiary, citizensId, "titill", new Date(), null);

        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorIDDiary);
        when(diaryService.createDiary(any(DiaryDTO.class))).thenReturn(Optional.of(diaryDTO));
        
        this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(diaryDTO))
                .header("authorization", "Bearer")
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is("e7c41f9e-cace-4f4d-845f-f3c4ed3fdaf9")));
        
        ArgumentCaptor<DiaryDTO> dtoCaptor = ArgumentCaptor.forClass(DiaryDTO.class);
        verify(diaryService, times(1)).createDiary(dtoCaptor.capture());
        verifyNoMoreInteractions(diaryService);
        
        DiaryDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getId(), is(UUID.fromString("e7c41f9e-cace-4f4d-845f-f3c4ed3fdaf9")));
        
    }

    /**
     * Test of updateDiary method, of class DiaryController.
     */
    @Test
    public void testUpdateDiary() {
//        System.out.println("updateDiary");
//        HttpHeaders httpHeaders = null;
//        DiaryDTO diaryDTO = null;
//        DiaryController instance = new DiaryController();
//        ResponseEntity expResult = null;
//        ResponseEntity result = instance.updateDiary(httpHeaders, diaryDTO);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDiaryByCitizenId method, of class DiaryController.
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteDiaryByCitizenId() throws Exception {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");

        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorId);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(String.class))).thenReturn(new ResponseEntity(HttpStatus.OK));
        when(diaryService.deleteDiary(id, authorId)).thenReturn(true);

        this.mockMvc.perform(delete("/{id}", id.toString())
                .header("authorization", "Bearer ")
        )
                .andExpect(status().isOk());

        verify(diaryService, times(1)).deleteDiary(id, authorId);
        verifyNoMoreInteractions(diaryService);
    }

}
