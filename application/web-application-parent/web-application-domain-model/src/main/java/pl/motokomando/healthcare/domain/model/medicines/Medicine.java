package pl.motokomando.healthcare.domain.model.medicines;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.domain.model.medicines.utils.ActiveIngredients;
import pl.motokomando.healthcare.domain.model.medicines.utils.Packaging;
import pl.motokomando.healthcare.domain.model.medicines.utils.ProductType;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Setter
@JsonIgnoreProperties(value = { "finished", "listing_expiration_date", "openfda", "marketing_category", "spl_id", "marketing_start_date", "product_id", "application_number", "brand_name_base" })
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
    private List<ActiveIngredients> activeIngredients;
    @JsonProperty("route")
    private String[] administrationRoute;
    @JsonProperty("dosage_form")
    private String dosageForm;
    @JsonProperty("packaging")
    private List<Packaging> packaging;

}
