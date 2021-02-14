package pl.motokomando.healthcare.controller.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import pl.motokomando.healthcare.model.base.utils.DoctorPagedResponse;
import pl.motokomando.healthcare.model.base.utils.DoctorRecord;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    public static List<DoctorRecord> mapDoctorPagedResponseToDoctorRecord(List<DoctorPagedResponse> doctorList) {
        return doctorList
                .stream()
                .map(e -> new DoctorRecord(
                        new SimpleIntegerProperty(e.getId()),
                        new SimpleStringProperty(e.getFirstName()),
                        new SimpleStringProperty(e.getLastName())))
                .collect(Collectors.toList());
    }

}
