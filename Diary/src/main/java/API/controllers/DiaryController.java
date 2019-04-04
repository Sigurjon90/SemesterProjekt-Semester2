/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.Diary;
import java.util.Arrays;
import java.util.List;

import API.repositories.DiaryRepository;
import API.repositories.IDiaryRepository;
import API.services.IDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    
}
