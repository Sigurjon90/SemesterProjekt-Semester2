package API.services;

import API.entities.Diary;
import API.entities.DiaryDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDiaryService {
    Optional<DiaryDTO> createDiary(DiaryDTO diaryDTO);

    Optional<DiaryDTO> findById(UUID Id);

    Optional<DiaryDTO> findByCitizenId(UUID id);

    List<DiaryDTO> getDiaries(List<UUID> listOfCitizensIds);

    Optional<DiaryDTO> updateDiary(DiaryDTO diaryDTO);

    boolean deleteDiary(UUID id, UUID authorId);
}