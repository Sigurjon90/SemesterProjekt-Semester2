/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.Citizen;
import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.DeleteDTO;
import API.repositories.CitizensRepository;
import API.services.CitizensService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sigur
 */
@RestController
@RequestMapping("/citizens")
public class CitizensController {

    @Autowired
    CitizensRepository citizensRepository;

    @Autowired
    CitizensService citizensService;
    
    // Test Array Method
    
    @RequestMapping(path = "/limited", method = RequestMethod.GET)
    public ResponseEntity getLimited() {
        List<Citizen> idList = citizensRepository.getLimited();
        return new ResponseEntity(idList, HttpStatus.OK);
    }
    
    // Create Citizen from CreateDTO
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createCitizen(@RequestBody CreateDTO createDTO) {
        
        List<UUID> listOfId = new ArrayList();
        listOfId.add(UUID.fromString("dd24aa7b-9833-4a81-9302-f03ceba60dff"));
        listOfId.add(UUID.fromString("758badd7-5ca5-473e-9669-23c0eabab899"));
        listOfId.add(UUID.fromString("3f936a2f-9821-48d5-a452-fcc52a82058b"));
        
        listOfId.toArray();
        
        

        CitizenDTO citizenDTO = citizensService.createCitizen(createDTO);

        if (citizenDTO != null) {
            return new ResponseEntity(citizenDTO, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // Get all Citizens
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCitizens() {
        List<CitizenDTO> citizens = citizensService.getCitizens();

        if (citizens != null) {
            return new ResponseEntity(citizens, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // Find Citizen by ID
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findCitizen(@PathVariable("id") String stringID) {
        UUID id = UUID.fromString(stringID);

        CitizenDTO citizens = citizensService.findCitizen(id);

        if (citizens != null) {
            return new ResponseEntity(citizens, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateCitizen(@RequestBody CitizenDTO citizenDTO) {
        return batchUpdateCitizen(Arrays.asList(citizenDTO));
    }
    
    // Batch
    @RequestMapping(path = "/batch", method = RequestMethod.PUT)
    public ResponseEntity batchUpdateCitizen(@RequestBody List<CitizenDTO> citizenDTOList) {

        List<CitizenDTO> citizenDTOListReturned = citizensService.batchUpdate(citizenDTOList);
        
        if(citizenDTOListReturned != null) {
            return new ResponseEntity(citizenDTOListReturned, HttpStatus.OK);
        }
        
        return new ResponseEntity(HttpStatus.NOT_FOUND);
        
    }
    
    // Delete 
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteCitizen(@RequestBody DeleteDTO deleteDTO) {

        boolean bool = citizensService.deleteCitizen(deleteDTO);

        if (bool) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
