package API.controllers;

/**
 *
 * @author thinkbuntu
 */
// Handles requests from URL
import API.entities.Journal;
import API.entities.JournalDTO;
import API.repositories.JournalRepository;
import API.services.IJournalService;
import API.services.JournalService;
import java.util.Arrays;
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

@RestController
@RequestMapping("/journals") // Prefixed URL for request -> "Alle requests skal ske genne dette URL"
public class JournalController {

    @Autowired
    IJournalService journalService;

    // Find all journals
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getJournals() {
        List<JournalDTO> journals = journalService.getJournals();
        
            return new ResponseEntity(journals, HttpStatus.OK);
    }

    // Find JournalDTO on ID
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity findJournal(@PathVariable("id") String stringID) {
        UUID id = UUID.fromString(stringID);
        
        JournalDTO journal = journalService.findJournal(id);
        
        if(journal != null) {
        return new ResponseEntity(journal, HttpStatus.OK);
        }
        
        return new ResponseEntity(HttpStatus.NOT_FOUND);
        
    }
    
    // Find Journal på Citizen ID
    @RequestMapping(path = "/citizen/{id}", method = RequestMethod.GET)
    public ResponseEntity findJournalByCitizen(@PathVariable("id") String stringID) {
        UUID id = UUID.fromString(stringID);
        
        JournalDTO journal = journalService.findJournaByCitizen(id);
        
        if(journal != null) {
        return new ResponseEntity(journal, HttpStatus.OK);
        }
        
        return new ResponseEntity(HttpStatus.NOT_FOUND);
        
    }

    // Create Journal
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createJournal(@RequestBody JournalDTO journalDTO) {

        JournalDTO journal = journalService.createJournal(journalDTO);

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.CREATED);
        }

           return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    // Modify Journal
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity modifyJournal(@RequestBody JournalDTO journalDTO) {

        JournalDTO journal = journalService.modifyJournal(journalDTO);

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    // Delete Journal on ID
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteJournal(@PathVariable("id") String stringID) {

        UUID id = UUID.fromString(stringID);
        JournalDTO journal = journalService.deleteJournal(id, UUID.randomUUID());

        if (journal != null) {
            return new ResponseEntity(journal, HttpStatus.OK);
        }
        
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}