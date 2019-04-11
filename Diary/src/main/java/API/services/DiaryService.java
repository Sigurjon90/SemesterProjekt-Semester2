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
    public Optional<DiaryDTO> createDiary(DiaryDTO diaryDTO) {
    Diary diary = modelMapper.map(diaryDTO, Diary.class);
    Optional<Diary> diaryCreated = diaryRepository.createDiary(diary);
    if(diaryCreated.isPresent()){
        DiaryDTO diaryDto = modelMapper.map(diaryCreated.get(), diaryDTO.getClass());
        return Optional.of(diaryDto);
    }
    return Optional.empty();
    }

    @Override
    public Optional<DiaryDTO> findById(UUID Id){
        Optional diary = diaryRepository.findById(Id);
        if(diary.isPresent()){
            DiaryDTO diaryDTO = modelMapper.map(diary.get(), DiaryDTO.class);
            return Optional.of(diaryDTO);
        }
        return Optional.empty();
        // public Optional<DiaryDTO> findById(UUID Id) { return Optional.empty();}
    }

    @Override
    public Optional<DiaryDTO> findByCitizenId(UUID id){
        Optional diary = diaryRepository.findByCitizenID(id);
        if(diary.isPresent()){
            DiaryDTO diaryDTO = modelMapper.map(diary.get(), DiaryDTO.class);
            return Optional.ofNullable(diaryDTO);
        }
        return Optional.empty();
    }

    @Override
    public List<DiaryDTO> getDiaries() {
        List<Diary> diaries = diaryRepository.getDiaries();
   /*     Type diaryDTOS = new TypeToken<List<DiaryDTO>>(){}.getType();
        return modelMapper.map(diaries, diaryDTOS); */
        return diaries.stream().map(source -> modelMapper.map(source, DiaryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<DiaryDTO> updateDiary(DiaryDTO diaryDTO) {
        Diary diary = modelMapper.map(diaryDTO, Diary.class);
        Optional<Diary> diaryUpdated = diaryRepository.updateDiary(diary);
        if(diaryUpdated.isPresent()){
            DiaryDTO diaryDto = modelMapper.map(diaryUpdated.get(), diaryDTO.getClass());
            return Optional.of(diaryDto);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteDiary(DiaryDTO diaryDTO) {
        Diary diary = modelMapper.map(diaryDTO, Diary.class);
        return diaryRepository.deleteDiary(diary);
    }

}
