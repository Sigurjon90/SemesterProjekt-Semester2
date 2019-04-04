package API.services;

import API.entities.Diary;
import API.entities.DiaryDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDiaryService {
    void saveDiary(DiaryDTO diary);

    Optional<DiaryDTO> findById(UUID Id);

    List<DiaryDTO> getDiaries();
}
