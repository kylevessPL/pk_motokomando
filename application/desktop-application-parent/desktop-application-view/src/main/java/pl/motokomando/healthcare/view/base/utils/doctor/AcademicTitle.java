package pl.motokomando.healthcare.view.base.utils.doctor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AcademicTitle {

    MD("lek. med."),
    DDS("lek. dent."),
    MD_PHD("dr n. med."),
    MD_PHD_DSC("dr hab n. med."),
    PROF_MD_PHD_DSC("prof. dr hab n. med.");

    private final String name;

}
