package pl.motokomando.healthcare.domain.model.medicines.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class ActiveIngredients {

    @JsonProperty("name")
    private String name;
    @JsonProperty("strength")
    private String strength;

}
