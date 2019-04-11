/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.Diary;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import API.entities.DiaryDTO;
import API.repositories.DiaryRepository;
import API.repositories.IDiaryRepository;
import API.services.DiaryService;
import API.services.IDiaryService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Jonas
 */
@RestController
@RequestMapping("/diaries")

public class DiaryController {
    @Autowired
    private IDiaryService diaryService;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getDiaries(){
     return new ResponseEntity(diaryService.getDiaries(), HttpStatus.OK);
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getDiary(@PathVariable("id") String id){
        Optional diary = diaryService.findById(UUID.fromString(id));
        if(diary.isPresent()) {
            return new ResponseEntity(diary.get(), HttpStatus.OK);
        }
        return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
    }
    @RequestMapping(path = "/{citizenID}", method = RequestMethod.GET)
    public ResponseEntity getByCitizenID(@PathVariable("citizenID") String citizenID){
        Optional diary = diaryService.findByCitizenId(UUID.fromString(citizenID));
        if (diary.isPresent()){
            return new ResponseEntity(diary.get(), HttpStatus.OK);
        }
        return new ResponseEntity("not found", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createDiary(@RequestBody DiaryDTO diaryDTO){
        Optional diary = diaryService.createDiary(diaryDTO);
        if(diary.isPresent()){
            return new ResponseEntity(diary.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateDiary(@RequestBody DiaryDTO diaryDTO){
        Optional diary = diaryService.updateDiary(diaryDTO);
        if(diary.isPresent()){
            return new ResponseEntity(diary.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteDiary(@RequestBody DiaryDTO diaryDTO){
        boolean isDeleted = diaryService.deleteDiary(diaryDTO);
        if(isDeleted){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}

