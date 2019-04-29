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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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

    @Autowired
    private RestTemplate restTemplate;


    // Get assigned citizens
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = GetCitizensDTO.class)
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getMyCitizens(@RequestHeader HttpHeaders httpHeaders) {
        String token = httpHeaders.getFirst("authorization");
        List<UUID> listOfCitizensIds = jwtUtils.getMyCitizens(token);
        UUID careCenterId = jwtUtils.getCareCenterId(token);

        GetCitizensDTO getCitizensDTO = citizensService.getMyCitizens(listOfCitizensIds, careCenterId);
        return new ResponseEntity(getCitizensDTO, HttpStatus.OK);
    }

    // Create Citizen from CreateDTO
    @ApiResponses(value = {
        @ApiResponse(code = 200, message= "Non", response = void.class),
        @ApiResponse(code = 201, message= "Successful", response = CitizenDTO.class)
    })
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

    // Find Citizen by ID
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = CitizenDTO.class)
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findCitizen(@PathVariable("id") String stringID) {
        UUID id = UUID.fromString(stringID);

        CitizenDTO citizens = citizensService.findCitizen(id);

        if (citizens != null) {
            return new ResponseEntity(citizens, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = CitizenDTO.class, responseContainer = "List")
    })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateCitizen(@RequestHeader HttpHeaders httpHeaders, @RequestBody CitizenDTO citizenDTO) {
        return batchUpdateCitizen(httpHeaders, Arrays.asList(citizenDTO));
    }

    // Batch
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = CitizenDTO.class, responseContainer = "List")
    })
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

    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = void.class)
    })
    @HystrixCommand(fallbackMethod = "fallback", ignoreExceptions = {NotFoundException.class})
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCitizen(@RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String stringID) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        UUID id = UUID.fromString(stringID);
        boolean bool = citizensService.deleteCitizen(id, authorId);

        List<Integer> listOfResponseCodes = Arrays.asList(
            deleteFromApi("http://diary-service/" + id, httpHeaders),
            deleteFromApi("http://journal-service/" + id, httpHeaders)
        );

        if (bool && listOfResponseCodes.stream().allMatch(resp -> resp == 200)) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private Integer deleteFromApi(String url, HttpHeaders httpHeaders) {
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(httpHeaders);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        }
        catch (Exception ex) {
            return 500;
        }

        return response.getStatusCodeValue();
    }

    public ResponseEntity fallback(HttpHeaders httpHeaders, String stringId, Throwable hystrixCommand) {
        return new ResponseEntity("", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
