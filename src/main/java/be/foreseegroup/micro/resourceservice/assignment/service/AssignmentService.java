package be.foreseegroup.micro.resourceservice.assignment.service;

import be.foreseegroup.micro.resourceservice.assignment.model.Assignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kaj on 24/09/15.
 */

@RestController
public class AssignmentService {
    private static final Logger LOG = LoggerFactory.getLogger(AssignmentService.class);

    @Autowired
    AssignmentRepository repo;

    @RequestMapping(method = RequestMethod.GET, value = "/assignments")
    public ResponseEntity<Iterable<Assignment>> getAll() {
        Iterable<Assignment> assignments = repo.findAll();
        LOG.info("/assignments getAll method called, response size: {}", repo.count());
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/assignments/{id}")
    public ResponseEntity<Assignment> getById(@PathVariable String id) {
        LOG.info("/assignments getById method called");
        Assignment assignment = repo.findOne(id);
        if (assignment == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/assignmentsbycid/{consultantId}")
    public ResponseEntity<Iterable<Assignment>> getByConsultantId(@PathVariable String consultantId) {
        LOG.info("/contracts getByConsultantId method called");
        Iterable<Assignment> assignments = repo.findByConsultantId(consultantId);
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/assignmentsbycuid/{customerId}")
    public ResponseEntity<Iterable<Assignment>> getByCustomerId(@PathVariable String customerId) {
        LOG.info("/contracts getByCustomerId method called");
        Iterable<Assignment> assignments = repo.findByCustomerId(customerId);
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/assignments")
    public ResponseEntity<Assignment> create(@RequestBody Assignment assignment) {
        LOG.info("/assignments create method called");
        Assignment createdAssignment = repo.save(assignment);
        return new ResponseEntity<>(createdAssignment, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/assignments/{id}")
    public ResponseEntity<Assignment> update(@PathVariable String id, @RequestBody Assignment assignment) {
        LOG.info("/assignments update method called");
        Assignment update = repo.findOne(id);
        if (update == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        update.setConsultantId(assignment.getConsultantId());
        update.setCustomerId(assignment.getCustomerId());
        update.setStartDate(assignment.getStartDate());
        update.setEndDate(assignment.getEndDate());
        Assignment updatedAssignment = repo.save(update);
        return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/assignments/{id}")
    public ResponseEntity<Assignment> delete(@PathVariable String id) {
        LOG.info("/assignments delete method called");
        Assignment assignment = repo.findOne(id);
        if (assignment == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        repo.delete(assignment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
