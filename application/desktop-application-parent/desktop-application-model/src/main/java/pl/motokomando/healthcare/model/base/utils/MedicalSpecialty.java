package pl.motokomando.healthcare.model.base.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum MedicalSpecialty {

    ANESTHESIA ("Znieczulenie"),
    CARDIOVASCULAR ("Krążeniowy"),
    COMMUNITYHEALTH ("Zdrowie społeczności"),
    DENTISTRY ("Dentystyka"),
    DERMATOLOGY ("Dermatologia"),
    DIETNUTRITION ("Dietetyk"),
    EMERGENCY ("Lekarz pierwszego kontaktu"),
    ENDOCRINE ("Hormonalny"),
    GASTROENTEROLOGIC ("Gastroenterologia"),
    GENETIC ("Genetyczny"),
    GERIATRIC ("Geriartyczny"),
    GYNECOLOGIC ("Ginekologia"),
    HEMATOLOGIC ("Hematologiczny"),
    INFECTIOUS ("Zakaźny"),
    LABORATORYSCIENCE ("Nauka laboratoryjna"),
    MIDWIFERY ("Położnictwo"),
    MUSCULOSKELETAL ("Mięśniowo-szkieletowy"),
    NEUROLOGIC ("Neurologiczny"),
    NURSING ("Pielęgniarstwo"),
    OBSTETRIC ("Położniczy"),
    ONCOLOGIC ("Onkologiczny"),
    OPTOMETRIC ("Optyczny"),
    OTOLARYNGOLOGIC ("Otolaryngologiczne"),
    PATHOLOGY ("Patologia"),
    PEDIATRIC ("Pediatryczny"),
    PHARMACYSPECIALTY ("Farmacyjny"),
    PHYSIOTHERAPY ("Fizjoterapia"),
    PLASTICSURGERY ("Chirurgia plastyczna"),
    PODIATRIC ("Podiatryczny"),
    PRIMARYCARE ("Opieka podstawowa"),
    PSYCHIATRIC ("Psychiatryczny"),
    PUBLICHEALTH ("Zdrowie publiczne"),
    PULMONARY ("Płucny"),
    RADIOGRAPHY ("Radiografia"),
    RENAL ("Nerkowy"),
    RESPIRATORYTHERAPY ("Terapia oddechowa"),
    RHEUMATOLOGIC ("Reumatologia"),
    SPEECHPATHOLOGY ("Patologia wymowy"),
    SURGICAL ("Chirurgiczny"),
    TOXICOLOGIC ("Toksykologiczny"),
    UROLOGIC ("Urologiczny");

    private final String name;

    public static MedicalSpecialty findByName(String name) {
        return Arrays.stream(MedicalSpecialty.values())
                .filter(e -> e.name.equals(name))
                .findFirst()
                .orElse(null);
    }

}