package pl.motokomando.healthcare.domain.model.medicines.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveIngredient {

    @JsonProperty
    private String name;
    @JsonProperty
    private String strength;

}
