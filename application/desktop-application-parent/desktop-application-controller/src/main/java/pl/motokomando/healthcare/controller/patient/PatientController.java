package pl.motokomando.healthcare.controller.patient;

import pl.motokomando.healthcare.model.patient.PatientModel;

public class PatientController {

    private final PatientModel patientModel;

    public PatientController(PatientModel patientModel) {
        this.patientModel = patientModel;
    }

}
