package pl.motokomando.healthcare.model.base.utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
public final class BaseTableRecord {

    @Accessors(fluent = true)
    private final SimpleIntegerProperty id;
    @Accessors(fluent = true)
    private final SimpleStringProperty firstName;
    @Accessors(fluent = true)
    private final SimpleStringProperty lastName;

    public Integer getId() {
        return id.get();
    }

}
