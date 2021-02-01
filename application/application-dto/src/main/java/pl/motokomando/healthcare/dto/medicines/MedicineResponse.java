package pl.motokomando.healthcare.dto.medicines;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.motokomando.healthcare.dto.medicines.utils.ActiveIngredient;
import pl.motokomando.healthcare.dto.medicines.utils.ProductType;

import java.util.List;

@Schema
@NoArgsConstructor
@Getter
@Setter
public class MedicineResponse {

    @Schema(description = "FDA National Drug Code", example = "76282-200")
    private String productNDC;
    @Schema(description = "Product manufacturer", example = "Exelan Pharmaceuticals, Inc.")
    private String manufacturer;
    @Schema(description = "Product name", example = "Fosinopril sodium")
    private String productName;
    @Schema(description = "Generic product name", example = "Alcohol")
    private String genericName;
    @Schema(description = "Prescription drug or OTC", example = "OTC")
    private ProductType productType;
    @Schema(description = "Active ingredients")
    private List<ActiveIngredient> activeIngredients;
    @Schema(description = "Administration route", example = "[\"ORAL\", \"TOPICAL\"]")
    private String[] administrationRoute;
    @Schema(description = "Dosage form", example = "TABLET")
    private String dosageForm;
    @Schema(description = "Packaging variants", example = "[\"148 mL in 1 bottle, dispensing (0536-1261-63)\", \"150 mL in 1 tube (49967-047-01)\"]")
    private String[] packagingVariants;

}
