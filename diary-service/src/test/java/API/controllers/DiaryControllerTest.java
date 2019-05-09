/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.DiaryDTO;
import API.services.IDiaryService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import security.JwtUtils;

/**
 *
 * @author Jonas
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class DiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDiaryService diaryService;

    @MockBean
    private JwtUtils jwtUtils;

    private final List<DiaryDTO> diaryDTO = java.util.Arrays.asList(
            new DiaryDTO(UUID.fromString("06d0166d-46b6-4bb5-8572-9299fc87c3dc"), "abba", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dd"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), "titel", new Date(System.currentTimeMillis()), null),
            new DiaryDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3df"), "lmao", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3da"), "titel1", new Date(System.currentTimeMillis()), null)
    );

    public DiaryControllerTest() {
    }

    /**
     * Test of getDiaries method, of class DiaryController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDiaries() throws Exception {
        List<UUID> listOfCitizenIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3da"));
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizenIds);
        when(diaryService.getDiaries(listOfCitizenIds)).thenReturn(diaryDTO);
        this.mockMvc.perform(get("/")
                .header("authorization", "Bearer")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("06d0166d-46b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[0].content", is("abba")))
                .andExpect(jsonPath("$[0].authorID", is("06d0166d-56b6-4bb5-8572-9299fc87c3dd")))
                .andExpect(jsonPath("$[0].citizenID", is("06d0166d-56b6-4bb5-8572-9299fc87c3de")))
                .andExpect(jsonPath("$[0].title", is("titel")))
                .andExpect(jsonPath("$[1].id", is("06d0166d-56b6-4bb5-8572-9299fc87c3df")))
                .andExpect(jsonPath("$[1].content", is("lmao")))
                .andExpect(jsonPath("$[1].authorID", is("06d0166d-56b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[1].citizenID", is("06d0166d-56b6-4bb5-8572-9299fc87c3da")))
                .andExpect(jsonPath("$[1].title", is("titel1")));
        verify(diaryService, times(1)).getDiaries(listOfCitizenIds);
        verifyNoMoreInteractions(diaryService);
    }

    /**
     * Test of getDiary method, of class DiaryController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetDiary() throws Exception {
        List<UUID> listOfCitizenIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"));
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizenIds);
        when(diaryService.getDiaries(listOfCitizenIds)).thenReturn(diaryDTO);
        this.mockMvc.perform(get("/")
                .header("authorization", "Bearer")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("06d0166d-46b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[0].content", is("abba")))
                .andExpect(jsonPath("$[0].authorID", is("06d0166d-56b6-4bb5-8572-9299fc87c3dd")))
                .andExpect(jsonPath("$[0].citizenID", is("06d0166d-56b6-4bb5-8572-9299fc87c3de")))
                .andExpect(jsonPath("$[0].title", is("titel")));
        verify(diaryService, times(1)).getDiaries(listOfCitizenIds);
        verifyNoMoreInteractions(diaryService);
    }

    /**
     * Test of getByCitizenID method, of class DiaryController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetByCitizenID() throws Exception {
        List<UUID> listOfCitizenIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"));
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizenIds);
        when(diaryService.getDiaries(listOfCitizenIds)).thenReturn(diaryDTO);
        this.mockMvc.perform(get("/")
                .header("authorization", "Bearer")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].content", is("abba")))
                .andExpect(jsonPath("$[0].title", is("titel")));
    }

    /**
     * Test of createDiary method, of class DiaryController.
     */
    /*  @Test
    public void testCreateDiary() throws Exception {
        UUID authorId = UUID.fromString("06d0266d-56b6-4bb5-8572-9299fc87c3de");
        UUID citizenId = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de");
        DiaryDTO diaryDTO = new DiaryDTO(UUID.randomUUID(), "hallo", authorId, citizenId, "title2", null, null);
        this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(diaryDTO))
                .header("authorization", "Bearer")
        )
    } */
    /**
     * Test of updateDiary method, of class DiaryController.
     *
     * @throws java.io.UnsupportedEncodingException
     */
    @Test
    public void testUpdateDiary() throws Exception {

        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");

        DiaryDTO diaryDTOO = new DiaryDTO(UUID.fromString("06d0166d-46b6-4bb5-8572-9299fc87c3dc"), "abba", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dd"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), "titel", new Date(System.currentTimeMillis()), null);
        //  DiaryDTO DiaTwo = new DiaryDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3df"), "lmao", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3da"), "titel1", new Date(System.currentTimeMillis()), null);

        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorId);
        when(diaryService.updateDiary(any(DiaryDTO.class))).thenReturn(Optional.of(diaryDTOO));
        this.mockMvc.perform(put("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(diaryDTOO))
                .header("authorization", "Bearer ")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is("06d0166d-46b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$.content", is("abba")));

        verify(diaryService, times(1)).updateDiary(any(DiaryDTO.class));
        verifyNoMoreInteractions(diaryService);
    }

    /**
     * Test of deleteDiaryByCitizenId method, of class DiaryController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteDiaryByCitizenId() throws Exception {

        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");

        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorId);
        when(diaryService.deleteDiary(id, authorId)).thenReturn(true);

        this.mockMvc.perform(delete("/{id}", id.toString())
                .header("authorization", "Bearer ")
        )
                .andExpect(status().isOk());

        verify(diaryService, times(1)).deleteDiary(id, authorId);
        verifyNoMoreInteractions(diaryService);

    }

}
