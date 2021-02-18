package pl.motokomando.healthcare.model.base.utils;

import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
public final class PersonTableRecord {

    private final Integer id;
    @Accessors(fluent = true)
    private final SimpleStringProperty firstName;
    @Accessors(fluent = true)
    private final SimpleStringProperty lastName;

}
