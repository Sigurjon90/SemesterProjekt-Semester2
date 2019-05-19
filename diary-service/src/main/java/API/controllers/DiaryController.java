/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import API.entities.DiaryDTO;
import API.services.IDiaryService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.JwtUtils;

/**
 *
 * @author Jonas
 */
@RestController
@RequestMapping("/")

public class DiaryController {
    
    @Autowired
    private IDiaryService diaryService;

    @Autowired
    private JwtUtils jwtUtils;
    
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = DiaryDTO.class, responseContainer = "List")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getDiaries(@RequestHeader HttpHeaders httpHeaders){
        String token = httpHeaders.getFirst("authorization");
        List<UUID> listOfCitizensIds = jwtUtils.getMyCitizens(token);
        List<DiaryDTO> diaries = diaryService.getDiaries(listOfCitizensIds);
        return new ResponseEntity(diaries, HttpStatus.OK);
    }
    
    // NEW METHOD
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = DiaryDTO.class)
    })
    @RequestMapping(path = "/all/{id}", method = RequestMethod.GET)
    public ResponseEntity getDiariesByCitizenID(@PathVariable("id") String id){
        List<DiaryDTO> diaries = diaryService.getDiariesByCitizenID(UUID.fromString(id));
        
        if(diaries != null) {
            return new ResponseEntity(diaries, HttpStatus.OK);
        }
        
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = DiaryDTO.class)
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getDiary(@PathVariable("id") String id){
        Optional diary = diaryService.findById(UUID.fromString(id));
        if(diary.isPresent()) {
            return new ResponseEntity(diary.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = DiaryDTO.class)
    })
    @RequestMapping(path = "/citizen/{id}", method = RequestMethod.GET)
    public ResponseEntity getByCitizenID(@PathVariable("id") String id){
        Optional diary = diaryService.findByCitizenId(UUID.fromString(id));
        if (diary.isPresent()){
            return new ResponseEntity(diary.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "No 200", response = void.class),
        @ApiResponse(code = 201, message= "Successful", response = DiaryDTO.class)
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createDiary(@RequestHeader HttpHeaders httpHeaders, @RequestBody DiaryDTO diaryDTO){
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        diaryDTO.setAuthorID(authorId);
        Optional diary = diaryService.createDiary(diaryDTO);
        if(diary.isPresent()){
            return new ResponseEntity(diary.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = DiaryDTO.class)
    })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateDiary(@RequestHeader HttpHeaders httpHeaders, @RequestBody DiaryDTO diaryDTO){
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        diaryDTO.setAuthorID(authorId);
        Optional diary = diaryService.entryUpdateDiary(diaryDTO);
        if(diary.isPresent()){
            return new ResponseEntity(diary.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = void.class)
    })
    @RequestMapping(path="/{citizenId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDiaryByCitizenId(@RequestHeader HttpHeaders httpHeaders, @PathVariable("citizenId") String stringID){
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        UUID id = UUID.fromString(stringID);
        boolean isDeleted = diaryService.deleteDiary(id, authorId);
        if(isDeleted){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}

