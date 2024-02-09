package fr.univlr.info.AppointmentAPIV1.store;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByStartDateGreaterThan(@Param("date") Date date);
}
