package pl.motokomando.healthcare.view.utils;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TextAreaLimiter implements ChangeListener<String> {

    private final Integer limit;

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        StringProperty textProperty = (StringProperty) observable;
        TextArea textArea = (TextArea) textProperty.getBean();
        if (textArea.getText().length() > limit) {
            String s = textArea.getText().substring(0, limit);
            textArea.setText(s);
        }
    }

}
