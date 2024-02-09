package fr.univlr.info.AppointmentAPIV1.controller;

import fr.univlr.info.AppointmentAPIV1.model.Appointment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class AppointmentDateValidator
        implements ConstraintValidator<AppointmentDateConstraint, Appointment> {
    @Override
    public void initialize(AppointmentDateConstraint dateCst) {
    }

    @Override
    public boolean isValid(Appointment app, ConstraintValidatorContext ctxt) {
        if(app.getStartDate() != null) {
            if (app.getStartDate().compareTo(app.getEndDate() ) < 0 ) {
                return true; // modify the code to check app object
            }
            return false;
        }
        return false;
    }

}
