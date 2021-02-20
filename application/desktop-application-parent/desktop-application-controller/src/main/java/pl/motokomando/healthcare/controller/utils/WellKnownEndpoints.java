package pl.motokomando.healthcare.controller.utils;

public final class WellKnownEndpoints {

    public static final String USER_INFO = "/api/v1/authorization/userinfo";
    public static final String PATIENTS = "/api/v1/patients";
    public static final String PATIENT = "/api/v1/patients/{id}";
    public static final String PATIENT_APPOINTMENTS = "/api/v1/patients/{id}/appointments";
    public static final String PATIENT_APPOINTMENT = "/api/v1/patients/{patientId}/appointments/{appointmentId}";
    public static final String DOCTOR = "/api/v1/doctors/{id}";
    public static final String DOCTORS = "/api/v1/doctors";
    public static final String DOCTOR_APPOINTMENTS = "/api/v1/doctors/{id}/appointments";
    public static final String MEDICINES = "/api/v1/medicines";
    public static final String BILLS = "/api/v1/bills";
    public static final String BILL = "/api/v1/bills/{id}";
    public static final String PRESCRIPTIONS = "/api/v1/prescriptions";
    public static final String PRESCRIPTION = "/api/v1/prescriptions/{id}";
    public static final String PRESCRIPTION_MEDICINES = "/api/v1/prescriptions/{id}/medicines";
    public static final String PRESCRIPTION_MEDICINE = "/api/v1/prescriptions/{prescriptionId}/medicines/{medicineId}";
    
}
