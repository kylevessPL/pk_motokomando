package pl.motokomando.healthcare.model.appointment.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public final class MedicineResponse {

    private final String productNDC;
    private final String manufacturer;
    private final String productName;
    private final String genericName;
    private final ProductType productType;
    private final List<ActiveIngredient> activeIngredients;
    private final String[] administrationRoute;
    private final String dosageForm;
    private final String[] packagingVariants;

}
