/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.Diary;
import API.entities.DiaryDTO;
import API.repositories.IDiaryRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 *
 * @author sigur
 */
public class DiaryServiceTest {

    public DiaryServiceTest() {
    }

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DiaryService service;

    @Mock
    private IDiaryRepository repositoryMock;

    private final UUID citizensId = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de");
    private final UUID authorIDDiary = UUID.fromString("6a7a8cb3-6502-4acc-b302-72b9e30bdf8e");
    private final UUID diaryId = UUID.fromString("e2a96b4d-edff-497b-a094-268e3b86ed28");
    private final Date date = new Date();

    private final List<DiaryDTO> diaryDTO = java.util.Arrays.asList(
            new DiaryDTO(diaryId, "hallo", authorIDDiary, citizensId, "hallo", this.date, null),
            new DiaryDTO(UUID.fromString("06d0166d-46b6-4bb5-8572-9299fc87c3dc"), "abba", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dd"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3de"), "titel", this.date, null),
            new DiaryDTO(UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3df"), "lmao", UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc"), UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3da"), "titel1", this.date, null)
    );

    private final List<Diary> diary = Arrays.asList(
            new Diary(diaryId, "hallo", authorIDDiary, citizensId, this.date, null, "titill")
    );

    private final DiaryDTO createDTOTest = new DiaryDTO(diaryId, "Halló", authorIDDiary, citizensId, "titill", this.date, null);
    private final Diary createDiaryTest = new Diary(diaryId, "hallo", authorIDDiary, citizensId, this.date, null, "titil");

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Test of createDiary method, of class DiaryService.
     */
    @Test
    public void testCreateDiary() {
        when(repositoryMock.createDiary(createDiaryTest)).thenReturn(Optional.of(createDiaryTest));
        when(modelMapper.map(createDiaryTest, DiaryDTO.class)).thenReturn(createDTOTest);
        when(modelMapper.map(createDTOTest, Diary.class)).thenReturn(createDiaryTest);
        Optional<DiaryDTO> actual = service.createDiary(createDTOTest);

        verify(repositoryMock, times(1)).createDiary(createDiaryTest);
        verifyNoMoreInteractions(repositoryMock);

        assertThat(actual, is(Optional.of(createDTOTest)));
    }

    /**
     * Test of findById method, of class DiaryService.
     */
    @Test
    public void testFindById() {
        when(repositoryMock.findById(authorIDDiary)).thenReturn(Optional.of(diary.get(0)));
        when(modelMapper.map(diary.get(0), DiaryDTO.class)).thenReturn(diaryDTO.get(0));

        Optional<DiaryDTO> diaryDTOO = service.findById(authorIDDiary);
        DiaryDTO diaryDTOo = new DiaryDTO(diaryId, "hallo", authorIDDiary, citizensId, "TitilHallo", this.date, null);

        verify(repositoryMock, times(1)).findById(authorIDDiary);
        verifyNoMoreInteractions(repositoryMock);

        assertThat(diaryDTOo.getId(), is(diaryId));
        assertThat(diaryDTOo.getContent(), is("hallo"));
        assertThat(diaryDTOo.getTitle(), is("TitelHallo"));
        assertThat(diaryDTOo.getAuthorID(), is (authorIDDiary));
        assertThat(diaryDTOo.getCitizenID(), is (citizensId));
    }

    /**
     * Test of findByCitizenId method, of class DiaryService.
     */
    @Test
    public void testFindByCitizenId() {
        when(repositoryMock.findByCitizenID(citizensId)).thenReturn(Optional.of(diary.get(0)));
        when(modelMapper.map(citizensId, DiaryDTO.class)).thenReturn(diaryDTO.get(0));

        DiaryDTO diaryDTOo = new DiaryDTO(diaryId, "hallo", authorIDDiary, citizensId, "TitilHallo", this.date, null);

        verify(repositoryMock, times(1)).findByCitizenID(citizensId);
        verifyNoMoreInteractions(repositoryMock);

        modelMapper.validate();
        assertThat(diaryDTOo.getId(), is(diaryId));
        assertThat(diaryDTOo.getContent(), is("hallo"));
        assertThat(diaryDTOo.getAuthorID(), is(authorIDDiary));
        assertThat(diaryDTOo.getCitizenID(), is(citizensId));
        assertThat(diaryDTOo.getTitle(), is("TitilHallo"));
    }

    /**
     * Test of getDiaries method, of class DiaryService.
     */
    @Test
    public void testGetDiaries() {
        List<UUID> diaryUUID = Arrays.asList(UUID.fromString("e2a96b4d-edff-497b-a094-268e3b86ed28"));
        when(repositoryMock.getDiaries(diaryUUID)).thenReturn(diary);
        when(modelMapper.map(diary.get(0), DiaryDTO.class)).thenReturn(diaryDTO.get(0));

        List<DiaryDTO> actual = service.getDiaries(diaryUUID);

        verify(repositoryMock, times(1)).getDiaries(diaryUUID);
        verifyNoMoreInteractions(repositoryMock);

        modelMapper.validate();
        assertThat(actual, is(Arrays.asList(diaryDTO.get(0))));

    }

    /**
     * Test of updateDiary method, of class DiaryService.
     */
    @Test
    public void testUpdateDiary() {
        when(repositoryMock.updateDiary(createDiaryTest)).thenReturn(Optional.of(createDiaryTest));

        when(modelMapper.map(createDTOTest, Diary.class)).thenReturn(createDiaryTest);
        //   when(modelMapper.map(diaryDTO.get(1), Diary.class)).thenReturn(diaries.get(1));
        when(modelMapper.map(createDiaryTest, DiaryDTO.class)).thenReturn(createDTOTest);
        //  when(modelMapper.map(Optional.of(Arrays.asList(diaries.get(1))), DiaryDTO.class)).thenReturn(diaryDTO.get(1));

        Optional<DiaryDTO> actual = service.updateDiary(createDTOTest);

        verify(repositoryMock, times(1)).updateDiary(createDiaryTest);
        verifyNoMoreInteractions(repositoryMock);

        assertThat(actual, is(Optional.of(createDTOTest)));
    }

    /**
     * Test of deleteDiary method, of class DiaryService.
     */
    @Test
    public void testDeleteDiary() {
        UUID id = UUID.fromString("06d0166d-56b6-4bb5-8572-9299fc87c3dc");
        UUID authorId = UUID.fromString("75d988af-13d8-4513-ad27-3aa7741cc823");
        when(repositoryMock.deleteDiaryByCitizenId(id, authorId)).thenReturn(true);

        boolean actual = service.deleteDiary(id, authorId);

        verify(repositoryMock, times(1)).deleteDiaryByCitizenId(id, authorId);
        verifyNoMoreInteractions(repositoryMock);

        assertTrue(actual);
    }

}
