package fr.univlr.info.AppointmentAPIV1.controller;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;
import fr.univlr.info.AppointmentAPIV1.model.Doctor;
import fr.univlr.info.AppointmentAPIV1.store.AppointmentRepository;
import fr.univlr.info.AppointmentAPIV1.store.DoctorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/api")
public class DoctorController {
    private final DoctorRepository docRepository;

    public DoctorController(DoctorRepository docRepository) {
        this.docRepository = docRepository;
    }

    @GetMapping("/doctors")
    ResponseEntity<Collection<Doctor>> all() {
        List<Doctor> docs = docRepository.findAll();
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }

    @GetMapping("/doctors/{name}")
    public ResponseEntity<Doctor> getById(@PathVariable String name) {
        //Test d'existence
        try {
            Doctor doc = docRepository.findByName(name).get();
            return new ResponseEntity<>(doc, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/doctors/{name}/appointments")
    public ResponseEntity<List<Appointment>> getAllDocAppointments(@PathVariable String name) {
        Doctor doc = docRepository.findByName(name).get();
        List<Appointment> appts = doc.getAppointments();
        return new ResponseEntity<>(appts,HttpStatus.OK);
    }
    

    @DeleteMapping("/doctors/{name}")
    public ResponseEntity<Boolean> deleteDoctor(@PathVariable String name) {
        //Test d'existence
        try {
            Doctor doc = docRepository.findByName(name).get();
            //test d'intégrité
            if(doc.getAppointments().size() != 0)
                return new ResponseEntity<>(false,HttpStatus.CONFLICT);
            docRepository.delete(doc);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/doctors")
    public ResponseEntity<Boolean> deleteAllDoctor() {
        docRepository.deleteAll();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
