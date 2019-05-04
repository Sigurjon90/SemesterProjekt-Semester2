/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.GetCitizensDTO;
import API.services.ICitizensService;
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
 * @author madsfalken
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class CitizensControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ICitizensService citizensService;
    
    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private RestTemplate restTemplate;
    
    private final List<CitizenDTO> citizens = java.util.Arrays.asList(
        new CitizenDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, 56942412, java.util.Arrays.asList("Mongol"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8")),
        new CitizenDTO(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), "Kris", "Middelfartsvej 82", "Bolbro", 1544, 56942415, java.util.Arrays.asList("Mongol", "Deaf"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))
    );

    /**
     * Test of getMyCitizens method, of class CitizensController.
     */
    @Test
    public void testGetMyCitizens() throws Exception {
        List<UUID> listOfCitizensIds = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"));
        UUID careCenterId = UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8");
        when(jwtUtils.getMyCitizens(any(String.class))).thenReturn(listOfCitizensIds);
        when(jwtUtils.getCareCenterId(any(String.class))).thenReturn(careCenterId);
        GetCitizensDTO getCitizensDTO = new GetCitizensDTO(Arrays.asList(citizens.get(0)), Arrays.asList(citizens.get(1)));
        when(citizensService.getMyCitizens(listOfCitizensIds, careCenterId)).thenReturn(getCitizensDTO);
        this.mockMvc.perform(get("/")
                .header("authorization", "Bearer ")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.primaryCitizens[0].id", is("06d0166d-56b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$.primaryCitizens[0].name", is("Jørgen")))
                .andExpect(jsonPath("$.otherCitizens[0].id", is("b6a4bdfe-3222-4956-b4a3-93ab21ba8454")))
                .andExpect(jsonPath("$.otherCitizens[0].name", is("Kris")));

        verify(citizensService, times(1)).getMyCitizens(listOfCitizensIds, careCenterId);
        verifyNoMoreInteractions(citizensService);
    }

    /**
     * Test of createCitizen method, of class CitizensController.
     */
    @Test
    public void testCreateCitizen_ShouldAddCitizenAndReturnAddedEntry() throws Exception {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        UUID careCenterId = UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8");
        CreateDTO citizenCreateDto = new CreateDTO("John Doe", "Albanivej 81", "Odense C", 5000, "245489-5612", 45654324, Arrays.asList("Propaganda"));
        CitizenDTO citizenDTO = new CitizenDTO(UUID.fromString("36d9cc14-558f-4a5c-afb6-60662fc2abb7"), "John Doe", "Albanivej 81", "Odense C", 5000, 45654324, Arrays.asList("Propaganda"), false, new Date().toString(), authorId, careCenterId);
        
        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorId);
        when(jwtUtils.getCareCenterId(any(String.class))).thenReturn(careCenterId);
        when(citizensService.createCitizen(any(CreateDTO.class), eq(authorId), eq(careCenterId))).thenReturn(citizenDTO);
        
        this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(citizenCreateDto))
                .header("authorization", "Bearer ")
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is("36d9cc14-558f-4a5c-afb6-60662fc2abb7")));
                
        ArgumentCaptor<CreateDTO> dtoCaptor = ArgumentCaptor.forClass(CreateDTO.class);
        verify(citizensService, times(1)).createCitizen(dtoCaptor.capture(), eq(authorId), eq(careCenterId));
        verifyNoMoreInteractions(citizensService);
        
        CreateDTO dtoArgument = dtoCaptor.getValue();
        assertThat(dtoArgument.getName(), is("John Doe"));
    }

    /**
     * Test of findCitizen method, of class CitizensController.
     */
    @Test
    public void testFindCitizen_ShouldReturnFoundCitizen() throws Exception {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        CitizenDTO citizenDTO = new CitizenDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, 56942412, java.util.Arrays.asList("Mongol"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"));
        
        when(citizensService.findCitizen(id)).thenReturn(citizenDTO);
        
        this.mockMvc.perform(get("/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.name", is("Jørgen")));
        
        verify(citizensService, times(1)).findCitizen(id);
        verifyNoMoreInteractions(citizensService);
    }
    
    @Test
    public void testFindCitizen_NotFound_ShouldReturnHttpStatusCode404() throws Exception {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de");
        when(citizensService.findCitizen(id)).thenReturn(null);
        
        this.mockMvc.perform(get("/{id}", id))
                .andExpect(status().isNotFound());
        
        verify(citizensService, times(1)).findCitizen(id);
        verifyNoMoreInteractions(citizensService);
    }

    /**
     * Test of updateCitizen method, of class CitizensController.
     */
    @Test
    public void testUpdateCitizen_ShouldUpdateAndReturnEntry() throws Exception {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        CitizenDTO citizenDTO = new CitizenDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, 56942412, java.util.Arrays.asList("Mongol"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"));
        
        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorId);
        when(citizensService.batchUpdate(any(List.class), eq(authorId))).thenReturn(Arrays.asList(citizenDTO));
        
        this.mockMvc.perform(put("/")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(TestUtils.convertObjectToJsonBytes(citizenDTO))
                    .header("authorization", "Bearer ")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("06d0166d-56b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[0].name", is("Jørgen")));
        
        verify(citizensService, times(1)).batchUpdate(any(List.class), eq(authorId));
        verifyNoMoreInteractions(citizensService);
    }

    /**
     * Test of batchUpdateCitizen method, of class CitizensController.
     */
    @Test
    public void testBatchUpdateCitizen_ShouldUpdateAndReturnEntries() throws Exception {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        CitizenDTO citizenDTOOne = new CitizenDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, 56942412, java.util.Arrays.asList("Mongol"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"));
        CitizenDTO citizenDTOTWO = new CitizenDTO(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), "Kris", "Middelfartsvej 82", "Bolbro", 1544, 56942415, java.util.Arrays.asList("Mongol", "Deaf"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"));
        
        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorId);
        when(citizensService.batchUpdate(any(List.class), eq(authorId))).thenReturn(Arrays.asList(citizenDTOOne, citizenDTOTWO));
        
        this.mockMvc.perform(put("/batch")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(TestUtils.convertObjectToJsonBytes(Arrays.asList(citizenDTOOne, citizenDTOTWO)))
                .header("authorization", "Bearer ")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is("06d0166d-56b6-4bb5-8572-9299fc87c3dc")))
                .andExpect(jsonPath("$[0].name", is("Jørgen")))
                .andExpect(jsonPath("$[1].id", is("b6a4bdfe-3222-4956-b4a3-93ab21ba8454")))
                .andExpect(jsonPath("$[1].name", is("Kris")));
        
        verify(citizensService, times(1)).batchUpdate(any(List.class), eq(authorId));
        verifyNoMoreInteractions(citizensService);
    }

    /**
     * Test of deleteCitizen method, of class CitizensController.
     */
    @Test
    public void testDeleteCitizen() throws Exception {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        
        when(jwtUtils.getUserId(any(String.class))).thenReturn(authorId);
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.DELETE), any(HttpEntity.class), eq(String.class))).thenReturn(new ResponseEntity(HttpStatus.OK));
        when(citizensService.deleteCitizen(id, authorId)).thenReturn(true);
        
        this.mockMvc.perform(delete("/{id}", id.toString())
                .header("authorization", "Bearer ")
        )
            .andExpect(status().isOk());
        
        verify(citizensService, times(1)).deleteCitizen(id, authorId);
        verifyNoMoreInteractions(citizensService);
    }
    
}
