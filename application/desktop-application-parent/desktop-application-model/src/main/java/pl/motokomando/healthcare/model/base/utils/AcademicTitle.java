package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum AcademicTitle {

    MD("lek. med."),
    DDS("lek. dent."),
    MD_PHD("dr n. med."),
    MD_PHD_DSC("dr hab n. med."),
    PROF_MD_PHD_DSC("prof. dr hab n. med.");

    private final String name;

    public static AcademicTitle findByName(String name) {
        return Arrays.stream(AcademicTitle.values())
                .filter(e -> e.name.equals(name))
                .findFirst()
                .orElse(null);
    }

}
