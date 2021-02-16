package pl.motokomando.healthcare.model.utils;

import javafx.concurrent.Service;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ServiceStore {

    private static final ServiceStore INSTANCE = new ServiceStore();

    private final Set<Service<?>> baseServiceSet = new HashSet<>();
    private final Set<Service<?>> patientServiceSet = new HashSet<>();
    private final Set<Service<?>> appointmentServiceSet = new HashSet<>();

    public static ServiceStore getInstance() {
        return INSTANCE;
    }

    public void addBaseService(Service<?> service) {
        baseServiceSet.add(service);
    }

    public void addPatientService(Service<?> service) {
        patientServiceSet.add(service);
    }

    public void addAppointmentService(Service<?> service) {
        appointmentServiceSet.add(service);
    }

    public void cancelAllBaseServices() {
        baseServiceSet.forEach(Service::cancel);
        baseServiceSet.clear();
    }

    public void cancelAllPatientServices() {
        patientServiceSet.forEach(Service::cancel);
        patientServiceSet.clear();
    }

    public void cancelAllAppointmentServices() {
        appointmentServiceSet.forEach(Service::cancel);
        appointmentServiceSet.clear();
    }

}
