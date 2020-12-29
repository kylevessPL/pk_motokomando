package pl.motokomando.healthcare.domain.model.medicines.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Packaging implements Serializable {

    @JsonProperty("description")
    private String description;

}
