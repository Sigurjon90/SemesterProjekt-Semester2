package API.controllers;

/**
 *
 * @author thinkbuntu
 */
// Handles requests from URL
import API.entities.JournalDTO;
import API.services.IJournalService;
import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import security.JwtUtils;

@RestController
@RequestMapping("/")
public class JournalController {

    @Autowired
    IJournalService journalService;

    @Autowired
    private JwtUtils jwtUtils;

    // Find all journals ASSIGNED TO YOU by Citizens ID
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = JournalDTO.class, responseContainer = "List")
    })
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getJournals(@RequestHeader HttpHeaders httpHeaders) {
        String token = httpHeaders.getFirst("authorization");
        List<UUID> listOfCitizensIds = jwtUtils.getMyCitizens(token);
        List<JournalDTO> journals = journalService.getJournals(listOfCitizensIds);

        return new ResponseEntity(journals, HttpStatus.OK);
    }

    // Find JournalDTO on ID
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = JournalDTO.class)
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findJournal(@PathVariable("id") String stringID) {
        UUID id = UUID.fromString(stringID);

        JournalDTO journal = journalService.findJournal(id);

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    // Find Journal på Citizen ID
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = JournalDTO.class)
    })
    @RequestMapping(path = "/citizen/{id}", method = RequestMethod.GET)
    public ResponseEntity findJournalByCitizen(@PathVariable("id") String stringID) {
        UUID id = UUID.fromString(stringID);

        JournalDTO journal = journalService.findJournaByCitizen(id);

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    // Create Journal
    @ApiResponses(value = {
        @ApiResponse(code = 200, message= "Non", response = void.class),
        @ApiResponse(code = 201, message= "Successful", response = JournalDTO.class)
    })
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createJournal(@RequestHeader HttpHeaders httpHeaders, @RequestBody JournalDTO journalDTO) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        journalDTO.setAuthorID(authorId);
        JournalDTO journal = journalService.createJournal(journalDTO);

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.CREATED);
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // Modify Journal
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = JournalDTO.class)
    })
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity modifyJournal(@RequestHeader HttpHeaders httpHeaders, @RequestBody JournalDTO journalDTO) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        journalDTO.setAuthorID(authorId);
        JournalDTO journal = journalService.modifyJournal(journalDTO);

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    // Delete Journal on ID
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message= "Successful", response = void.class)
    })
    @RequestMapping(path = "/{citizenId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteJournalByCitizenId(@RequestHeader HttpHeaders httpHeaders, @PathVariable("citizenId") String stringID) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        UUID id = UUID.fromString(stringID);
        boolean isDeleted = journalService.deleteJournal(id, authorId);
        if (isDeleted) {
            return new ResponseEntity(HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
