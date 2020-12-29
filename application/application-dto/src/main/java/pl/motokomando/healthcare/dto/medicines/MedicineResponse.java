package pl.motokomando.healthcare.dto.medicines;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.medicines.utils.ActiveIngredients;
import pl.motokomando.healthcare.dto.medicines.utils.Packaging;
import pl.motokomando.healthcare.dto.medicines.utils.ProductType;

import java.util.List;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
public class MedicineResponse {

    @ApiModelProperty(value = "FDA National Drug Code", example = "76282-200")
    private String productNDC;
    @ApiModelProperty(value = "Product manufacturer", example = "Exelan Pharmaceuticals, Inc.")
    private String manufacturer;
    @ApiModelProperty(value = "Product name", example = "Fosinopril sodium")
    private String productName;
    @ApiModelProperty(value = "Generic product name", example = "Alcohol")
    private String genericName;
    @ApiModelProperty(value = "Prescription drug or OTC", example = "OTC")
    private ProductType productType;
    @ApiModelProperty(value = "Active ingredients")
    private List<ActiveIngredients> activeIngredients;
    @ApiModelProperty(value = "Administration route", example = "[\"ORAL\"]")
    private String[] administrationRoute;
    @ApiModelProperty(value = "Dosage form", example = "TABLET")
    private String dosageForm;
    @ApiModelProperty(value = "Packaging variants")
    private List<Packaging> packaging;

}
