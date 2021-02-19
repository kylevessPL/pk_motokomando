package pl.motokomando.healthcare.model.appointment.utils;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public final class MedicinesTableRecord {

    private final SimpleStringProperty productNDC;
    private final SimpleStringProperty manufacturer;
    private final SimpleStringProperty productName;
    private final SimpleStringProperty genericName;
    private final SimpleObjectProperty<ProductType> productType;
    private final SimpleListProperty<ActiveIngredient> activeIngredients;
    private final SimpleObjectProperty<String[]> administrationRoute;
    private final SimpleStringProperty dosageForm;
    private final SimpleObjectProperty<String[]> packagingVariants;

}
