package pl.motokomando.healthcare.domain.model.medicines.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.medicines.Medicine;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFDAResponse implements Serializable {

    @JsonProperty("results")
    private List<Medicine> medicines;

}
