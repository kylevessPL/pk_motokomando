package pl.motokomando.healthcare.view.doctor;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import pl.motokomando.healthcare.controller.doctor.DoctorController;
import pl.motokomando.healthcare.model.base.utils.AcademicTitle;
import pl.motokomando.healthcare.model.base.utils.DoctorDetails;
import pl.motokomando.healthcare.model.base.utils.MedicalSpecialty;
import pl.motokomando.healthcare.model.doctor.DoctorModel;
import pl.motokomando.healthcare.view.utils.FXAlert;
import pl.motokomando.healthcare.view.utils.FXTasks;
import pl.motokomando.healthcare.view.utils.FXValidation;
import pl.motokomando.healthcare.view.utils.TextFieldLimiter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.ButtonType.OK;

public class DoctorView {

    private final ValidationSupport updateDoctorDetailsValidationSupport = new ValidationSupport();

    private DoctorController controller;

    private DoctorModel model;

    private AnchorPane doctorDetailsPane;
    private TextField doctorFirstNameTextField;
    private TextField doctorLastNameTextField;
    private TextField doctorPhoneNumberTextField;
    private ComboBox<String> chooseDoctorAcademicTitleComboBox;
    private CheckComboBox<String> chooseDoctorSpecialtyComboBox;
    private Button unlockUpdateDoctorDetailsButton;
    private Button updateDoctorDetailsButton;

    public DoctorView(Integer doctorId) {
        initModel(doctorId);
        setController();
        addContent();
        setupValidation();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return doctorDetailsPane;
    }

    private Stage currentStage() {
        return (Stage) doctorDetailsPane.getScene().getWindow();
    }

    private void initModel(Integer doctorId) {
        model = new DoctorModel(doctorId);
    }

    private void setController() {
        controller = new DoctorController(model);
    }

    private void addContent() {
        createDoctorDetailsPane();
        getDoctorDetails();
    }

    private void createDoctorDetailsPane() {
        doctorDetailsPane = new AnchorPane();
        doctorDetailsPane.setPrefHeight(600.0);
        doctorDetailsPane.setPrefWidth(900.0);
        createBackgroundImage();
        createDoctorFirstNameTextField();
        createDoctorPhoneNumberTextField();
        createDoctorLastNameTextField();
        createChooseDoctorAcademicTitleComboBox();
        createDoctorAcademicTitleLabel();
        createChooseDoctorSpecialtyComboBox();
        createDoctorMedicalSpecialtyLabel();
        createDoctorFirstNameLabel();
        createDoctorLastNameLabel();
        createDoctorPhoneNumberLabel();
        createUpdateDoctorDetailsButton();
        createUnlockUpdateDoctorDetailsButton();
    }

    private void createBackgroundImage() {
        ImageView backgroundImage = new ImageView(new Image(this.getClass().getResourceAsStream("/images/logo.png")));
        backgroundImage.setLayoutX(50);
        backgroundImage.setLayoutY(200);
        backgroundImage.setFitHeight(200);
        backgroundImage.setFitWidth(800);
        backgroundImage.setOpacity(0.3);
        doctorDetailsPane.getChildren().add(backgroundImage);
    }

    private void createUnlockUpdateDoctorDetailsButton() {
        unlockUpdateDoctorDetailsButton = new Button();
        unlockUpdateDoctorDetailsButton.setLayoutX(480.0);
        unlockUpdateDoctorDetailsButton.setLayoutY(436.0);
        unlockUpdateDoctorDetailsButton.setMnemonicParsing(false);
        unlockUpdateDoctorDetailsButton.setText("Edytuj");
        doctorDetailsPane.getChildren().add(unlockUpdateDoctorDetailsButton);
    }

    private void createUpdateDoctorDetailsButton() {
        updateDoctorDetailsButton = new Button();
        updateDoctorDetailsButton.setDisable(true);
        updateDoctorDetailsButton.setLayoutX(360.0);
        updateDoctorDetailsButton.setLayoutY(436.0);
        updateDoctorDetailsButton.setMnemonicParsing(false);
        updateDoctorDetailsButton.setText("Zaktualizuj");
        updateDoctorDetailsButton.setDisable(true);
        doctorDetailsPane.getChildren().add(updateDoctorDetailsButton);
    }

    private void createDoctorMedicalSpecialtyLabel() {
        Label doctorMedicalSpecialtyLabel = new Label();
        doctorMedicalSpecialtyLabel.setLayoutX(550.0);
        doctorMedicalSpecialtyLabel.setLayoutY(155.0);
        doctorMedicalSpecialtyLabel.setText("Specjalizacja");
        doctorDetailsPane.getChildren().add(doctorMedicalSpecialtyLabel);
    }

    private void createChooseDoctorSpecialtyComboBox() {
        chooseDoctorSpecialtyComboBox = new CheckComboBox<>();
        chooseDoctorSpecialtyComboBox.setLayoutX(550.0);
        chooseDoctorSpecialtyComboBox.setLayoutY(185.0);
        chooseDoctorSpecialtyComboBox.setPrefHeight(30.0);
        chooseDoctorSpecialtyComboBox.setPrefWidth(200.0);
        chooseDoctorSpecialtyComboBox.setTitle("Wybrano");
        chooseDoctorSpecialtyComboBox.setShowCheckedCount(true);
        chooseDoctorSpecialtyComboBox.getItems().setAll(Arrays
                .stream(MedicalSpecialty.values())
                .map(MedicalSpecialty::getName)
                .collect(Collectors.toList()));
        doctorDetailsPane.getChildren().add(chooseDoctorSpecialtyComboBox);
    }

    private void createDoctorAcademicTitleLabel() {
        Label doctorAcademicTitleLabel = new Label();
        doctorAcademicTitleLabel.setLayoutX(150.0);
        doctorAcademicTitleLabel.setLayoutY(190.0);
        doctorAcademicTitleLabel.setText("Tytuł naukowy");
        doctorDetailsPane.getChildren().add(doctorAcademicTitleLabel);
    }

    private void createDoctorFirstNameLabel() {
        Label doctorFirstNameLabel = new Label();
        doctorFirstNameLabel.setLayoutX(150.0);
        doctorFirstNameLabel.setLayoutY(30.0);
        doctorFirstNameLabel.setText("Imię");
        doctorDetailsPane.getChildren().add(doctorFirstNameLabel);
    }

    private void createDoctorLastNameLabel() {
        Label doctorLastNameLabel = new Label();
        doctorLastNameLabel.setLayoutX(150.0);
        doctorLastNameLabel.setLayoutY(110.0);
        doctorLastNameLabel.setText("Nazwisko");
        doctorDetailsPane.getChildren().add(doctorLastNameLabel);
    }

    private void createDoctorPhoneNumberLabel() {
        Label doctorPhoneNumberLabel = new Label();
        doctorPhoneNumberLabel.setLayoutX(550.0);
        doctorPhoneNumberLabel.setLayoutY(65.0);
        doctorPhoneNumberLabel.setText("Numer telefonu");
        doctorDetailsPane.getChildren().add(doctorPhoneNumberLabel);
    }

    private void createChooseDoctorAcademicTitleComboBox() {
        chooseDoctorAcademicTitleComboBox = new ComboBox<>();
        chooseDoctorAcademicTitleComboBox.setLayoutX(150.0);
        chooseDoctorAcademicTitleComboBox.setLayoutY(220.0);
        chooseDoctorAcademicTitleComboBox.setPrefHeight(30.0);
        chooseDoctorAcademicTitleComboBox.setPrefWidth(200.0);
        chooseDoctorAcademicTitleComboBox.setPromptText("Wybierz tytuł");
        chooseDoctorAcademicTitleComboBox.getItems().setAll(Arrays
                .stream(AcademicTitle.values())
                .map(AcademicTitle::getName)
                .collect(Collectors.toList()));
        doctorDetailsPane.getChildren().add(chooseDoctorAcademicTitleComboBox);
    }

    private void createDoctorLastNameTextField() {
        doctorLastNameTextField = new TextField();
        doctorLastNameTextField.setLayoutX(150.0);
        doctorLastNameTextField.setLayoutY(140.0);
        doctorLastNameTextField.setPrefHeight(30.0);
        doctorLastNameTextField.setPrefWidth(200.0);
        doctorLastNameTextField.setPromptText("Podaj nazwisko");
        doctorDetailsPane.getChildren().add(doctorLastNameTextField);
    }

    private void createDoctorPhoneNumberTextField() {
        doctorPhoneNumberTextField = new TextField();
        doctorPhoneNumberTextField.setLayoutX(550.0);
        doctorPhoneNumberTextField.setLayoutY(95.0);
        doctorPhoneNumberTextField.setPrefHeight(30.0);
        doctorPhoneNumberTextField.setPrefWidth(200.0);
        doctorPhoneNumberTextField.setPromptText("Podaj numer");
        doctorDetailsPane.getChildren().add(doctorPhoneNumberTextField);
    }

    private void createDoctorFirstNameTextField() {
        doctorFirstNameTextField = new TextField();
        doctorFirstNameTextField.setLayoutX(150.0);
        doctorFirstNameTextField.setLayoutY(60.0);
        doctorFirstNameTextField.setPrefHeight(30.0);
        doctorFirstNameTextField.setPrefWidth(200.0);
        doctorFirstNameTextField.setPromptText("Podaj imię");
        doctorDetailsPane.getChildren().add(doctorFirstNameTextField);
    }

    private void setupValidation() {
        setDoctorFirstNameTextFieldValidator();
        setDoctorLastNameTextFieldValidator();
        setDoctorPhoneNumberTextFieldValidator();
        setDoctorAcademicTitleComboBoxValidator();
        setDoctorSpecialtyComboBoxValidator();
        updateDoctorDetailsValidationSupport.getRegisteredControls().forEach(c -> c.setDisable(true));
    }

    private void setDoctorFirstNameTextFieldValidator() {
        final String fieldName = "imię";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        updateDoctorDetailsValidationSupport.registerValidator(
                doctorFirstNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setDoctorLastNameTextFieldValidator() {
        final String fieldName = "nazwisko";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        updateDoctorDetailsValidationSupport.registerValidator(
                doctorLastNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setDoctorPhoneNumberTextFieldValidator() {
        final String fieldName = "nr telefonu";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9]+$"));
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 7);
        updateDoctorDetailsValidationSupport.registerValidator(
                doctorPhoneNumberTextField,
                true,
                Validator.combine(emptyValidator, regexValidator, rangeValidator));
    }

    private void setDoctorAcademicTitleComboBoxValidator() {
        final String fieldName = "tytuł naukowy";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        updateDoctorDetailsValidationSupport.registerValidator(chooseDoctorAcademicTitleComboBox, true, emptyValidator);
    }

    private void setDoctorSpecialtyComboBoxValidator() {
        final String fieldName = "specjalizacja";
        ValueExtractor.addObservableValueExtractor(c -> c == chooseDoctorSpecialtyComboBox,
                c -> model.doctorSpecialtyComboBoxCheckedItemsNumber());
        Validator<Integer> checkComboBoxBoxValidator = FXValidation.createCheckComboBoxValidator(fieldName);
        updateDoctorDetailsValidationSupport.registerValidator(chooseDoctorSpecialtyComboBox, true, checkComboBoxBoxValidator);
    }

    private void setTextFieldsLimit() {
        doctorFirstNameTextField.textProperty().addListener(new TextFieldLimiter(30));
        doctorLastNameTextField.textProperty().addListener(new TextFieldLimiter(30));
        doctorPhoneNumberTextField.textProperty().addListener(new TextFieldLimiter(15));
    }

    private void observeModelAndUpdate() {
        model.doctorDetails().addListener((obs, oldValue, newValue) -> Platform.runLater(this::setDoctorDetailsFields));
    }

    private void delegateEventHandlers() {
        setTextFieldsLimit();
        chooseDoctorSpecialtyComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c ->
                controller.handleDoctorSpecialtyComboBoxCheckedItemsChanged(c.getList()));
        updateDoctorDetailsValidationSupport.validationResultProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(this::switchUpdateDoctorDetailsButtonState));
        unlockUpdateDoctorDetailsButton.setOnMouseClicked(e ->
                Platform.runLater(this::switchUnlockUpdateDoctorDetailsButtonsState));
        updateDoctorDetailsButton.setOnMouseClicked(e -> updateDoctorDetails());
    }

    private void switchUnlockUpdateDoctorDetailsButtonsState() {
        setDoctorDetailsFields();
        updateDoctorDetailsValidationSupport.getRegisteredControls().forEach(c -> c.setDisable(!c.isDisabled()));
        unlockUpdateDoctorDetailsButton.setText(unlockUpdateDoctorDetailsButton.getText().equals("Edytuj") ?
                "Zablokuj" : "Edytuj");
        switchUpdateDoctorDetailsButtonState();
    }

    private void setDoctorDetailsFields() {
        DoctorDetails doctorDetails = model.getDoctorDetails();
        doctorFirstNameTextField.setText(doctorDetails.getFirstName());
        doctorLastNameTextField.setText(doctorDetails.getLastName());
        chooseDoctorAcademicTitleComboBox.getSelectionModel().select(doctorDetails.getAcademicTitle().getName());
        doctorDetails.getSpecialties().forEach(e -> chooseDoctorSpecialtyComboBox.getCheckModel().check(e.getName()));
        doctorPhoneNumberTextField.setText(doctorDetails.getPhoneNumber());
    }

    private void switchUpdateDoctorDetailsButtonState() {
        updateDoctorDetailsButton.setDisable(unlockUpdateDoctorDetailsButton.getText().equals("Edytuj") ||
                updateDoctorDetailsValidationSupport.isInvalid() || !isDoctorDetailsFieldsChanged());
    }

    private boolean isDoctorDetailsFieldsChanged() {
        DoctorDetails doctorDetails = createDoctorDetails();
        return getDoctorDetailsDiff(doctorDetails).isPresent();
    }

    private DoctorDetails createDoctorDetails() {
        String firstName = doctorFirstNameTextField.getText();
        String lastName = doctorLastNameTextField.getText();
        AcademicTitle academicTitle = AcademicTitle.findByName(
                chooseDoctorAcademicTitleComboBox.getSelectionModel().getSelectedItem());
        List<MedicalSpecialty> specialties = chooseDoctorSpecialtyComboBox.getCheckModel().getCheckedItems()
                .stream()
                .map(MedicalSpecialty::findByName)
                .collect(Collectors.toList());
        String phoneNumber = doctorPhoneNumberTextField.getText();
        return new DoctorDetails(firstName, lastName, academicTitle, specialties, phoneNumber);
    }

    @SneakyThrows
    private Optional<Map<String, String>> getDoctorDetailsDiff(DoctorDetails doctorDetails) {
        Map<String, String> objectsDiff = FXTasks.getObjectsDiff(model.getDoctorDetails(), doctorDetails);
        return Optional.of(objectsDiff).filter(e -> !e.isEmpty());
    }

    private void getDoctorDetails() {
        Task<Void> task = FXTasks.createTask(() -> controller.getDoctorDetails());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnFailed(e -> getDoctorDetailsFailureResult(task.getException().getMessage()));
    }

    private void getDoctorDetailsFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(ERROR)
                .alertTitle("Nie udało się pobrać danych lekarza")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(() -> alert.showAndWait()
                .filter(OK::equals)
                .ifPresent(e -> currentStage().close()));
    }

    private void updateDoctorDetails() {
        Alert alert = FXAlert.builder()
                .alertType(CONFIRMATION)
                .contentText("Czy na pewno zaktualizować dane tego lekarza?")
                .alertTitle("Aktualizacja danych lekarza")
                .owner(currentStage())
                .build();
        Platform.runLater(() -> alert.showAndWait()
                .filter(OK::equals)
                .ifPresent(e -> processDoctorDetailsUpdate()));
    }

    private void processDoctorDetailsUpdate() {
        Platform.runLater(() -> updateDoctorDetailsButton.setDisable(true));
        DoctorDetails doctorDetails = createDoctorDetails();
        Task<Void> task = FXTasks.createTask(() -> controller.handleUpdateDoctorDetailsButtonClicked(doctorDetails));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> processUpdateDoctorDetailsSuccessResult());
        task.setOnFailed(e -> {
            Platform.runLater(() -> updateDoctorDetailsButton.setDisable(false));
            processUpdateDoctorDetailsFailureResult(task.getException().getMessage());
        });
    }

    private void processUpdateDoctorDetailsSuccessResult() {
        Alert alert = FXAlert.builder()
                .alertType(INFORMATION)
                .alertTitle("Operacja ukończona pomyślnie")
                .contentText("Pomyślnie zaktualizowano dane lekarza")
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    private void processUpdateDoctorDetailsFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(ERROR)
                .alertTitle("Nie udało się zaktualizować danych lekarza")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

}