package pl.motokomando.healthcare.domain.model.medicines.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProductType {

    @JsonProperty("HUMAN PRESCRIPTION DRUG")
    PRESCRIPTION,
    @JsonProperty("HUMAN OTC DRUG")
    OTC

}
