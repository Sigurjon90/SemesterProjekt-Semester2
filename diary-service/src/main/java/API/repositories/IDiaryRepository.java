/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Diary;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author Jonas
 */
public interface IDiaryRepository {
        Optional<Diary> createDiary(Diary diary);
        
        Optional<Diary> findById(UUID Id);

        Optional<Diary> findByCitizenID(UUID id);
        
        List<Diary> getDiaries(List<UUID> listOfCitizensIds);

        Optional<Diary> updateDiary(Diary diary);
        
        Optional<Diary> entryUpdateDiary(Diary diary);
        
        List<Diary> getDiariesByCitizenID(UUID citizenID);

        boolean deleteDiary(UUID id, UUID authorId);

        boolean deleteDiaryByCitizenId(UUID id, UUID authorId);
}
