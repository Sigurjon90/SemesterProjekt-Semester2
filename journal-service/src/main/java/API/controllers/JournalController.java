package API.controllers;

/**
 *
 * @author thinkbuntu
 */
// Handles requests from URL
import API.entities.JournalDTO;
import API.services.IJournalService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getJournals(@RequestHeader HttpHeaders httpHeaders) {
        String token = httpHeaders.getFirst("authorization");
        List<UUID> listOfCitizensIds = jwtUtils.getMyCitizens(token);
        List<JournalDTO> journals = journalService.getJournals(listOfCitizensIds);

        return new ResponseEntity(journals, HttpStatus.OK);
    }

    // Find JournalDTO on ID
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
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteJournal(@RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String stringID) {
        String token = httpHeaders.getFirst("authorization");
        UUID authorId = jwtUtils.getUserId(token);
        UUID id = UUID.fromString(stringID);
        JournalDTO journal = journalService.deleteJournal(id, authorId);

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
