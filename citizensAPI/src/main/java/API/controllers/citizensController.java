/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.CitizensDTO;
import API.repositories.CitizensRepository;
import API.services.CitizensService;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sigur
 */
@RestController
@RequestMapping("/citizens")
public class citizensController {

    @Autowired
    CitizensRepository citizensRepositories;

    @Autowired
    CitizensService citizensService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCitizens() {

        List<CitizensDTO> citizens = citizensService.getCitizens();

        if (citizens != null) {
            return new ResponseEntity(citizens, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findJournal(@PathVariable("id") String stringID) {
        UUID id = UUID.fromString(stringID);

        CitizensDTO citizens = citizensService.findCitizen(id);

        if (citizens != null) {
            return new ResponseEntity(citizensService.findCitizen(id), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    // Create Journal
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createJournal(@RequestBody CitizensDTO citizensDTO) {

        // Hvorfor det her step?
        CitizensDTO cDTO = citizensService.createCitizen(citizensDTO);

        if (cDTO != null) {
            return new ResponseEntity(cDTO, HttpStatus.CREATED);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

//    // Modify Journal
//    @RequestMapping(method = RequestMethod.PUT)
//    public ResponseEntity modifyJournal(@RequestBody CitizensDTO journalDTO) {
//
//        CitizensDTO journal = citizensService.
//
//        if (journal != null) {
//            return new ResponseEntity(journal, HttpStatus.OK);
//        }
//
//        return new ResponseEntity(HttpStatus.BAD_REQUEST);
//    }
    // Delete Journal on ID
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteJournal(@PathVariable("id") String stringID) {

        UUID id = UUID.fromString(stringID);
        CitizensDTO cDTO = citizensService.archiveCitizens(id, UUID.randomUUID());

        if (cDTO != null) {
            return new ResponseEntity(cDTO, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

}