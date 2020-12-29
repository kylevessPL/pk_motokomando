package pl.motokomando.healthcare.domain.model.medicines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.medicines.utils.ActiveIngredient;
import pl.motokomando.healthcare.domain.model.medicines.utils.PackagingDeserializer;
import pl.motokomando.healthcare.domain.model.medicines.utils.ProductType;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Medicine implements Serializable {

    @JsonProperty("product_ndc")
    private String productNDC;
    @JsonProperty("labeler_name")
    private String manufacturer;
    @JsonProperty("brand_name")
    private String productName;
    @JsonProperty("generic_name")
    private String genericName;
    @JsonProperty("product_type")
    private ProductType productType;
    @JsonProperty("active_ingredients")
    private List<ActiveIngredient> activeIngredients;
    @JsonProperty("route")
    private String[] administrationRoute;
    @JsonProperty("dosage_form")
    private String dosageForm;
    @JsonProperty("packaging")
    @JsonDeserialize(using = PackagingDeserializer.class)
    private String[] packagingVariants;

}
