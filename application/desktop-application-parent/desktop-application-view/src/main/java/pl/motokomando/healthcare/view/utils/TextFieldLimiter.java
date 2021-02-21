package pl.motokomando.healthcare.view.utils;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TextFieldLimiter implements ChangeListener<String> {

    private final Integer limit;

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        StringProperty textProperty = (StringProperty) observable;
        TextField textField = (TextField) textProperty.getBean();
        if (textField.getText().length() > limit) {
            String s = textField.getText().substring(0, limit);
            textField.setText(s);
        }
    }

}
