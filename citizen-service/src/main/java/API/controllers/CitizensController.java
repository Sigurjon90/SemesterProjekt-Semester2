/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.controllers;

import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.GetCitizensDTO;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import API.services.ICitizensService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.JwtUtils;

/**
 * @author sigur
 * @author thinkbuntu
 */
@RestController
@RequestMapping("/")
public class CitizensController {

    @Autowired
    ICitizensService citizensService;

    @Autowired
    private JwtUtils jwtUtils;

    // Get assigned citizens
    @RequestMapping(path = "/mycitizens", method = RequestMethod.GET)
    public ResponseEntity getMyCitizens(@RequestHeader HttpHeaders httpHeaders) {
        String token = httpHeaders.getFirst("authorization");
        List<UUID> listOfCitizensIds = jwtUtils.getMyCitizens(token);
        UUID careCenterId = jwtUtils.getCareCenterId(token);

        GetCitizensDTO getCitizensDTO = citizensService.getMyCitizens(listOfCitizensIds, careCenterId);
        return new ResponseEntity(getCitizensDTO, HttpStatus.OK);
    }

    // Create Citizen from CreateDTO
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createCitizen(@RequestHeader HttpHeaders httpHeaders, @RequestBody CreateDTO createDTO) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        CitizenDTO citizenDTO = citizensService.createCitizen(createDTO, authorId);

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
    public ResponseEntity updateCitizen(@RequestHeader HttpHeaders httpHeaders, @RequestBody CitizenDTO citizenDTO) {
        return batchUpdateCitizen(httpHeaders, Arrays.asList(citizenDTO));
    }

    // Batch
    @RequestMapping(path = "/batch", method = RequestMethod.PUT)
    public ResponseEntity batchUpdateCitizen(@RequestHeader HttpHeaders httpHeaders, @RequestBody List<CitizenDTO> citizenDTOList) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        List<CitizenDTO> citizenDTOListReturned = citizensService.batchUpdate(citizenDTOList, authorId);

        if (citizenDTOListReturned != null) {
            return new ResponseEntity(citizenDTOListReturned, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    // Delete 
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCitizen(@RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String stringID) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        UUID id = UUID.fromString(stringID);
        boolean bool = citizensService.deleteCitizen(id, authorId);

        if (bool) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
