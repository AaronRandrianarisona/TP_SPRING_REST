package fr.univlr.info.AppointmentAPIV1.controller;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;
import fr.univlr.info.AppointmentAPIV1.store.AppointmentRepository;
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

@RestController
@RequestMapping(path = "/api")
public class AppointmentController {
    private final AppointmentRepository apptRepository;

    public AppointmentController(AppointmentRepository apptRepository) {
        this.apptRepository = apptRepository;
    }

    @GetMapping("/appointments")
    ResponseEntity<Collection<Appointment>> all() {
        List<Appointment> appts = apptRepository.findAll();
        return new ResponseEntity<>(appts, HttpStatus.OK);
    }

    @PostMapping("/appointments")
    ResponseEntity<Appointment> newAppointment(@Valid @RequestBody Appointment appt) {
        // if(this.apptRepository.findById(appt.getId()).isPresent()) {
        // return new ResponseEntity<>(HttpStatus.CONFLICT);
        // }
        Appointment appointment = apptRepository.save(appt);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointment.getId())
                .toUri();
        // URI location = URI.create("http://localhost:8000/api/appointments");
        headers.set("Accept", "application/json");
        headers.setLocation(location);
        return new ResponseEntity<>(appointment, headers, HttpStatus.CREATED);
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        try {
            Appointment appt = apptRepository.findById(id).get();
            return new ResponseEntity<>(appt, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @Valid @RequestBody Appointment appt) {
        Appointment oldAppt = apptRepository.findById(id).get();
        oldAppt.setDoctor(appt.getDoctor());
        oldAppt.setEndDate(appt.getEndDate());
        oldAppt.setPatient(appt.getPatient());
        oldAppt.setStartDate(appt.getStartDate());
        Appointment updatedAppt = apptRepository.save(oldAppt);
        return new ResponseEntity<>(updatedAppt, HttpStatus.OK);
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Boolean> deleteAppointment(@PathVariable Long id) {
        try {
            Appointment appt = apptRepository.findById(id).get();
            apptRepository.delete(appt);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/appointments")
    public ResponseEntity<Boolean> deleteAllAppointment() {
        apptRepository.deleteAll();
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
