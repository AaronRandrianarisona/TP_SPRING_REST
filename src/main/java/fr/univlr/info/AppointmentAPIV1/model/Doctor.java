package fr.univlr.info.AppointmentAPIV1.model;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Doctor {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private List<Appointment> appointments;

    public Doctor(Long id, String name, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.appointments = appointments;
    }

    public Doctor() {}

    public Doctor(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
package fr.univlr.info.AppointmentAPIV1.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Doctor {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(targetEntity=Appointment.class, mappedBy = "doctorObj")
    private List<Appointment> appointments;

    public Doctor(Long id, String name, List<Appointment> appointments) {
        this.id = id;
        this.name = name;
        this.appointments = appointments;
    }

    public Doctor() {}

    public Doctor(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
