/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.Citizen;
import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.GetCitizensDTO;
import API.repositories.ICitizensRepository;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.is;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

/**
 *
 * @author madsfalken
 */
public class CitizensServiceTest {
    
    @InjectMocks
    private CitizensService service;
    
    @Mock
    private ICitizensRepository repositoryMock;
    
    @Mock
    private ModelMapper modelMapper;
    
    private List<Citizen> myCitizens = Arrays.asList(
        new Citizen(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, "234312-2341", 56942412, Arrays.asList("Mongol"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))
    );
    
    private List<Citizen> careCenterCitizens = Arrays.asList(
        new Citizen(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), "Kris", "Middelfartsvej 82", "Bolbro", 1544, "234312-5643", 56942415, Arrays.asList("Mongol", "Deaf"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))
    );
    
    private CreateDTO createCitizen = new CreateDTO("Jørgen", "Middelfartsvej 72", "Bolbro", 1234, "234312-2341", 56942412, Arrays.asList("Mongol"));
    
    private List<Citizen> citizens = Arrays.asList(
        new Citizen(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, "234312-2341", 56942412, Arrays.asList("Mongol"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8")),
        new Citizen(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), "Kris", "Middelfartsvej 82", "Bolbro", 1544, "234312-5643", 56942415, Arrays.asList("Mongol", "Deaf"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))
    );
    
    private List<CitizenDTO> DTOS = Arrays.asList(
        new CitizenDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), "Jørgen", "Middelfartsvej 72", "Bolbro", 1234, 56942412, Arrays.asList("Mongol"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8")),
        new CitizenDTO(UUID.fromString("b6a4bdfe-3222-4956-b4a3-93ab21ba8454"), "Kris", "Middelfartsvej 82", "Bolbro", 1544, 56942415, Arrays.asList("Mongol", "Deaf"), false, new Date(System.currentTimeMillis()).toString(), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))
    );
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of createCitizen method, of class CitizensService.
     */
    @Test
    public void testCreateCitizen() {
        when(repositoryMock.createCitizen(myCitizens.get(0), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"))).thenReturn(myCitizens.get(0));
        when(modelMapper.map(myCitizens.get(0), CitizenDTO.class)).thenReturn(DTOS.get(0));
        when(modelMapper.map(createCitizen, Citizen.class)).thenReturn(myCitizens.get(0));
        
        CitizenDTO actual = service.createCitizen(createCitizen, UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"));
        
        verify(repositoryMock, times(1)).createCitizen(myCitizens.get(0), UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823"), UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8"));
        verifyNoMoreInteractions(repositoryMock);
        
        assertThat(actual, is(DTOS.get(0)));
    }

    /**
     * Test of getCitizens method, of class CitizensService.
     */
    @Test
    public void testGetCitizens() {
        when(repositoryMock.getCitizens()).thenReturn(myCitizens);
        when(modelMapper.map(myCitizens.get(0), CitizenDTO.class)).thenReturn(DTOS.get(0));
        
        List<CitizenDTO> actual = service.getCitizens();
        
        verify(repositoryMock, times(1)).getCitizens();
        verifyNoMoreInteractions(repositoryMock);
        
        modelMapper.validate();
        
        assertThat(actual, is(Arrays.asList(DTOS.get(0))));
    }

    /**
     * Test of getMyCitizens method, of class CitizensService.
     */
    @Test
    public void testGetMyCitizens() {
        List<UUID> citizensUUID = Arrays.asList(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"));
        UUID careCenterID = UUID.fromString("66dd7224-60bf-4c3a-a4c3-82bcf18453b8");
        when(repositoryMock.getMyCitizens(citizensUUID)).thenReturn(myCitizens);
        when(modelMapper.map(myCitizens.get(0), CitizenDTO.class)).thenReturn(DTOS.get(0));
        when(repositoryMock.getCareCenterCitizens(careCenterID, citizensUUID)).thenReturn(careCenterCitizens);
        when(modelMapper.map(careCenterCitizens.get(0), CitizenDTO.class)).thenReturn(DTOS.get(1));
        
        GetCitizensDTO citizensDTO = service.getMyCitizens(citizensUUID, careCenterID);
        
        verify(repositoryMock, times(1)).getMyCitizens(citizensUUID);
        verify(repositoryMock, times(1)).getCareCenterCitizens(careCenterID, citizensUUID);
        
        assertNotSame(citizensDTO.getPrimaryCitizens(), citizensDTO.getOtherCitizens());
    }

    /**
     * Test of findCitizen method, of class CitizensService.
     */
    @Test
    public void testFindCitizen() {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        when(repositoryMock.findCitizen(id)).thenReturn(myCitizens.get(0));
        when(modelMapper.map(myCitizens.get(0), CitizenDTO.class)).thenReturn(DTOS.get(0));
        
        CitizenDTO citizenDTO = service.findCitizen(id);
        
        verify(repositoryMock, times(1)).findCitizen(id);
        verifyNoMoreInteractions(repositoryMock);

        assertThat(citizenDTO, is(DTOS.get(0)));
    }
    
    @Test
    public void testFindCitizen_ShouldReturnNull() {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dd");
        when(repositoryMock.findCitizen(id)).thenReturn(null);
        
        CitizenDTO citizenDTO = service.findCitizen(id);
        
        verify(repositoryMock, times(1)).findCitizen(id);
        verifyNoMoreInteractions(repositoryMock);

        assertNull(citizenDTO);
    }

    /**
     * Test of batchUpdate method, of class CitizensService.
     */
    @Test
    public void testBatchUpdate() {
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        when(repositoryMock.batchUpdate(citizens, authorId)).thenReturn(citizens);
        when(modelMapper.map(DTOS.get(0), Citizen.class)).thenReturn(citizens.get(0));
        when(modelMapper.map(DTOS.get(1), Citizen.class)).thenReturn(citizens.get(1));
        when(modelMapper.map(citizens.get(0), CitizenDTO.class)).thenReturn(DTOS.get(0));
        when(modelMapper.map(citizens.get(1), CitizenDTO.class)).thenReturn(DTOS.get(1));
        
        List<CitizenDTO> actual = service.batchUpdate(DTOS, authorId);
        
        verify(repositoryMock, times(1)).batchUpdate(citizens, authorId);
        verifyNoMoreInteractions(repositoryMock);

        assertThat(actual, is(DTOS));
    }

    /**
     * Test of deleteCitizen method, of class CitizensService.
     */
    @Test
    public void testDeleteCitizen() {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        when(repositoryMock.deleteCitizen(id, authorId)).thenReturn(true);
        
        boolean actual = service.deleteCitizen(id, authorId);
        
        verify(repositoryMock, times(1)).deleteCitizen(id, authorId);
        verifyNoMoreInteractions(repositoryMock);
        
        assertTrue(actual);
    }
    
}
