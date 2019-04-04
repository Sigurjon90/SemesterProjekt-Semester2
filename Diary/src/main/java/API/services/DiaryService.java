package API.services;

import API.entities.Diary;
import API.entities.DiaryDTO;
import API.repositories.DiaryRepository;
import API.repositories.IDiaryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DiaryService implements IDiaryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IDiaryRepository diaryRepository;

    @Override
    public void saveDiary(DiaryDTO diary) {

    }

    @Override
    public Optional<DiaryDTO> findById(UUID Id) {
        return Optional.empty();
    }

    @Override
    public List<DiaryDTO> getDiaries() {
        List<Diary> diaries = diaryRepository.getDiaries();
   /*     Type diaryDTOS = new TypeToken<List<DiaryDTO>>(){}.getType();
        return modelMapper.map(diaries, diaryDTOS); */
        return diaries.stream().map(source -> modelMapper.map(source, DiaryDTO.class)).collect(Collectors.toList());
    }

}
