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
        void saveDiary(Diary diary);
        
        Optional<Diary> findById(UUID Id);
        
        List<Diary> getDiaries();
}
