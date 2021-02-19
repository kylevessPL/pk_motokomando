package pl.motokomando.healthcare.view.patient;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.SneakyThrows;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import pl.motokomando.healthcare.controller.patient.PatientController;
import pl.motokomando.healthcare.model.base.utils.BloodType;
import pl.motokomando.healthcare.model.base.utils.PatientDetails;
import pl.motokomando.healthcare.model.base.utils.Sex;
import pl.motokomando.healthcare.model.patient.PatientModel;
import pl.motokomando.healthcare.model.patient.utils.PatientAppointmentsTableRecord;
import pl.motokomando.healthcare.model.utils.ServiceStore;
import utils.DefaultDatePickerConverter;
import utils.FXAlert;
import utils.FXTasks;
import utils.FXValidation;
import utils.TextFieldLimiter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;
import static javafx.scene.control.ButtonType.OK;
import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import static utils.DateConstraints.PAST;

public class PatientView {

    private static final int TABLE_REFRESH_RATE = 10;

    private final ServiceStore serviceStore = ServiceStore.getInstance();

    private final ValidationSupport updatePatientDetailsValidationSupport = new ValidationSupport();

    private PatientController controller;

    private PatientModel model;

    private TabPane patientPane;

    private Tab patientDetailsTab;
    private AnchorPane patientDetailsPane;
    private TextField patientFirstNameTextField;
    private TextField patientLastNameTextField;
    private TextField patientPhoneNumberTextField;
    private TextField patientZipCodeTextField;
    private TextField patientStreetNameTextField;
    private TextField patientCityTextField;
    private ComboBox<String> choosePatientSexComboBox;
    private Label patientSexLabel;
    private ComboBox<String> choosePatientBloodTypeComboBox;
    private Label patientBloodTypeLabel;
    private Label patientRegistrationDateLabel;
    private DatePicker patientBirthDateDatePicker;
    private TextField patientHouseNumberTextField;
    private TextField patientPeselTextField;
    private TextField patientRegistrationTextField;
    private Button unlockUpdatePatientDetailsButton;
    private Tab patientAppointmentsTab;
    private AnchorPane patientAppointmentsPane;
    private TableView<PatientAppointmentsTableRecord> patientAppointmentsTable;
    private Tab scheduleAppointmentTab;
    private AnchorPane scheduleAppointmentPane;
    private DatePicker appointmentDateDatePicker;
    private ComboBox<String> chooseDoctorComboBox;
    private ComboBox<String> chooseAppointmentHourComboBox;
    private Button scheduleAppointmentButton;
    private Button updatePatientDetailsButton;
    private Label scheduleAppointmentDoctorLabel;
    private Label scheduleAppointmentHourLabel;
    private Label scheduleAppointmentChooseDoctorLabel;
    private Label patientFirstNameLabel;
    private Label patientLastNameLabel;
    private Label patientPhoneNumberLabel;
    private Label patientZipCodeLabel;
    private Label patientStreetNameLabel;
    private Label patientCityLabel;
    private Label patientBirthDateLabel;
    private Label patientHouseNumberLabel;
    private Label patientPeselLabel;
    private Button patientAppointmentCanelButton;
    private ImageView imageViewLogo;

    private Pagination patientAppointmentsTablePagination;

    public PatientView(Integer patientId) {
        initModel(patientId);
        setController();
        createPane();
        addContent();
        setupValidation();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return patientPane;
    }

    private Stage currentStage() {
        return (Stage) patientPane.getScene().getWindow();
    }

    private void initModel(Integer patientId) {
        model = new PatientModel(patientId);
    }

    private void setController() {
        controller = new PatientController(model);
    }

    private void createPane() {
        patientPane = new TabPane();
        patientPane.setMaxHeight(USE_PREF_SIZE);
        patientPane.setMaxWidth(USE_PREF_SIZE);
        patientPane.setMinHeight(USE_PREF_SIZE);
        patientPane.setMinWidth(USE_PREF_SIZE);
        patientPane.setPrefHeight(600.0);
        patientPane.setPrefWidth(900.0);
        patientPane.setTabClosingPolicy(UNAVAILABLE);
    }

    private void addContent() {
        createPatientDetailsTab();
        createScheduleAppointmentTab();
        createPatientAppointmentsTab();
    }

    private void createPatientAppointmentsTab() {
        patientAppointmentsTab = new Tab();
        patientAppointmentsTab.setText("Wizyty");
        patientAppointmentsPane = new AnchorPane();
        patientAppointmentsPane.setPrefHeight(180.0);
        patientAppointmentsPane.setPrefWidth(200.0);
        createPatientAppointmentsTable();
        createAppointmentCancelButton();
        patientAppointmentsTab.setContent(patientAppointmentsPane);
        patientPane.getTabs().add(patientAppointmentsTab);
    }

    private void createAppointmentCancelButton(){
        patientAppointmentCanelButton = new Button();
        patientAppointmentCanelButton.setLayoutX(785.0);
        patientAppointmentCanelButton.setLayoutY(15.0);
        patientAppointmentCanelButton.setMnemonicParsing(false);
        patientAppointmentCanelButton.setText("Anuluj");
        patientAppointmentCanelButton.setDisable(true);
        patientAppointmentsPane.getChildren().add(patientAppointmentCanelButton);
    }

    private void createScheduleAppointmentTab() {
        scheduleAppointmentTab = new Tab();
        scheduleAppointmentTab.setText("Zarezerwuj wizytę");
        scheduleAppointmentPane = new AnchorPane();
        scheduleAppointmentPane.setPrefHeight(180.0);
        scheduleAppointmentPane.setPrefWidth(200.0);
        createAppointmentDateDatePicker();
        createChooseDoctorComboBox();
        createChooseDoctorLabel();
        createChooseAppointmentHourComboBox();
        createScheduleAppointmentButton();
        createScheduleAppointmentDoctorLabel();
        createScheduleAppointmentHourLabel();
        scheduleAppointmentTab.setContent(scheduleAppointmentPane);
        patientPane.getTabs().add(scheduleAppointmentTab);
    }

    private void createChooseDoctorLabel(){
        scheduleAppointmentChooseDoctorLabel = new Label();
        scheduleAppointmentChooseDoctorLabel.setLayoutX(300);
        scheduleAppointmentChooseDoctorLabel.setLayoutY(150);
        scheduleAppointmentChooseDoctorLabel.setText("Data wizyty");
        scheduleAppointmentPane.getChildren().add(scheduleAppointmentChooseDoctorLabel);
    }

    private void createScheduleAppointmentHourLabel() {
        scheduleAppointmentHourLabel = new Label();
        scheduleAppointmentHourLabel.setLayoutX(300.0);
        scheduleAppointmentHourLabel.setLayoutY(250.0);
        scheduleAppointmentHourLabel.setText("Godzina");
        scheduleAppointmentPane.getChildren().add(scheduleAppointmentHourLabel);
    }

    private void createScheduleAppointmentDoctorLabel() {
        scheduleAppointmentDoctorLabel = new Label();
        scheduleAppointmentDoctorLabel.setLayoutX(300.0);
        scheduleAppointmentDoctorLabel.setLayoutY(50.0);
        scheduleAppointmentDoctorLabel.setText("Lekarz");
        scheduleAppointmentPane.getChildren().add(scheduleAppointmentDoctorLabel);
    }

    private void createScheduleAppointmentButton() {
        scheduleAppointmentButton = new Button();
        scheduleAppointmentButton.setLayoutX(401.0);
        scheduleAppointmentButton.setLayoutY(400.0);
        scheduleAppointmentButton.setMnemonicParsing(false);
        scheduleAppointmentButton.setText("Zarezerwuj");
        scheduleAppointmentPane.getChildren().add(scheduleAppointmentButton);
    }

    private void createChooseAppointmentHourComboBox() {
        chooseAppointmentHourComboBox = new ComboBox<>();
        chooseAppointmentHourComboBox.setLayoutX(300.0);
        chooseAppointmentHourComboBox.setLayoutY(280.0);
        chooseAppointmentHourComboBox.setPrefHeight(40.0);
        chooseAppointmentHourComboBox.setPrefWidth(300.0);
        setWorkingHours(chooseAppointmentHourComboBox);
        scheduleAppointmentPane.getChildren().add(chooseAppointmentHourComboBox);
    }

    private void createChooseDoctorComboBox() {
        chooseDoctorComboBox = new ComboBox<>();
        chooseDoctorComboBox.setLayoutX(300.0);
        chooseDoctorComboBox.setLayoutY(80.0);
        chooseDoctorComboBox.setPrefHeight(40.0);
        chooseDoctorComboBox.setPrefWidth(300.0);
        scheduleAppointmentPane.getChildren().add(chooseDoctorComboBox);
    }

    private void createAppointmentDateDatePicker() {
        appointmentDateDatePicker = new DatePicker();
        appointmentDateDatePicker.setLayoutX(300.0);
        appointmentDateDatePicker.setLayoutY(180.0);
        appointmentDateDatePicker.setPrefHeight(40.0);
        appointmentDateDatePicker.setPrefWidth(300.0);
        appointmentDateDatePicker.setPromptText("Wybierz datę");
        patientBirthDateDatePicker.setConverter(new DefaultDatePickerConverter());
        scheduleAppointmentPane.getChildren().add(appointmentDateDatePicker);
    }

    private void createPatientDetailsTab() {
        patientDetailsTab = new Tab();
        patientDetailsTab.setText("Pacjent");
        patientDetailsPane = new AnchorPane();
        patientDetailsPane.setPrefHeight(180.0);
        patientDetailsPane.setPrefWidth(200.0);
        createLogoImage(patientDetailsPane);
        createPatientFirstNameTextField();
        createPatientFirstNameLabel();
        createPatientPhoneNumberTextField();
        createPatientPhoneNumberLabel();
        createPatientPeselTextField();
        createPatientPeselLabel();
        createPatientLastNameTextField();
        createPatientLastNameLabel();
        createPatientZipCodeTextField();
        createPatientZipCodeLabel();
        createPatientStreetNameTextField();
        createPatientStreetNameLabel();
        createPatientCityTextField();
        createPatientCityLabel();
        createChoosePatientSexComboBox();
        createPatientSexLabel();
        createChoosePatientBloodTypeComboBox();
        createPatientBloodTypeLabel();
        createPatientBirthDateDatePicker();
        createPatientBirthDateLabel();
        createPatientHouseNumberTextField();
        createPatientHouseNumberLabel();
        createPatientRegistrationInfoTextField();
        createPatientRegistrationDateLabel();
        createUpdatePatientDetailsButton();
        createUnlockUpdatePatientDetailsButton();
        getPatientDetails();
        patientDetailsTab.setContent(patientDetailsPane);
        patientPane.getTabs().add(patientDetailsTab);
    }

    private void createLogoImage(AnchorPane backgroundPane) {
        Image logoImage = new Image(this.getClass().getResourceAsStream("/images/logo.png"));
        imageViewLogo = new ImageView(logoImage);
        imageViewLogo.setLayoutX(50);
        imageViewLogo.setLayoutY(330);
        imageViewLogo.setFitHeight(200);
        imageViewLogo.setFitWidth(800);
        imageViewLogo.setOpacity(0.3);
        backgroundPane.getChildren().add(imageViewLogo);
    }

    private void createPatientFirstNameLabel(){
        patientFirstNameLabel = new Label();
        patientFirstNameLabel.setLayoutX(50.0);
        patientFirstNameLabel.setLayoutY(30.0);
        patientFirstNameLabel.setText("Imię");
        patientDetailsPane.getChildren().add(patientFirstNameLabel);
    }

    private void createPatientLastNameLabel(){
        patientLastNameLabel = new Label();
        patientLastNameLabel.setLayoutX(50.0);
        patientLastNameLabel.setLayoutY(110.0);
        patientLastNameLabel.setText("Nazwisko");
        patientDetailsPane.getChildren().add(patientLastNameLabel);
    }

    private void createPatientBirthDateLabel(){
        patientBirthDateLabel = new Label();
        patientBirthDateLabel.setLayoutX(50.0);
        patientBirthDateLabel.setLayoutY(270.0);
        patientBirthDateLabel.setText("Data urodzenia");
        patientDetailsPane.getChildren().add(patientBirthDateLabel);
    }

    private void createPatientZipCodeLabel(){
        patientZipCodeLabel = new Label();
        patientZipCodeLabel.setLayoutX(350.0);
        patientZipCodeLabel.setLayoutY(270.0);
        patientZipCodeLabel.setText("Kod pocztowy");
        patientDetailsPane.getChildren().add(patientZipCodeLabel);
    }

    private void createPatientStreetNameLabel(){
        patientStreetNameLabel = new Label();
        patientStreetNameLabel.setLayoutX(350.0);
        patientStreetNameLabel.setLayoutY(30.0);
        patientStreetNameLabel.setText("Ulica");
        patientDetailsPane.getChildren().add(patientStreetNameLabel);
    }

    private void createPatientHouseNumberLabel(){
        patientHouseNumberLabel = new Label();
        patientHouseNumberLabel.setLayoutX(350.0);
        patientHouseNumberLabel.setLayoutY(110.0);
        patientHouseNumberLabel.setText("Numer domu");
        patientDetailsPane.getChildren().add(patientHouseNumberLabel);
    }

    private void createPatientCityLabel(){
        patientCityLabel = new Label();
        patientCityLabel.setLayoutX(350.0);
        patientCityLabel.setLayoutY(190.0);
        patientCityLabel.setText("Miejscowość");
        patientDetailsPane.getChildren().add(patientCityLabel);
    }

    private void createPatientPeselLabel(){
        patientPeselLabel = new Label();
        patientPeselLabel.setLayoutX(650.0);
        patientPeselLabel.setLayoutY(110.0);
        patientPeselLabel.setText("PESEL");
        patientDetailsPane.getChildren().add(patientPeselLabel);
    }

    private void createPatientPhoneNumberLabel(){
        patientPhoneNumberLabel = new Label();
        patientPhoneNumberLabel.setLayoutX(650.0);
        patientPhoneNumberLabel.setLayoutY(30.0);
        patientPhoneNumberLabel.setText("Numer telefonu");
        patientDetailsPane.getChildren().add(patientPhoneNumberLabel);
    }

    private void createUnlockUpdatePatientDetailsButton() {
        unlockUpdatePatientDetailsButton = new Button();
        unlockUpdatePatientDetailsButton.setLayoutX(480.0);
        unlockUpdatePatientDetailsButton.setLayoutY(480.0);
        unlockUpdatePatientDetailsButton.setMnemonicParsing(false);
        unlockUpdatePatientDetailsButton.setText("Edytuj");
        patientDetailsPane.getChildren().add(unlockUpdatePatientDetailsButton);
    }

    private void createUpdatePatientDetailsButton() {
        updatePatientDetailsButton = new Button();
        updatePatientDetailsButton.setLayoutX(360.0);
        updatePatientDetailsButton.setLayoutY(480.0);
        updatePatientDetailsButton.setMnemonicParsing(false);
        updatePatientDetailsButton.setText("Zaktualizuj");
        updatePatientDetailsButton.setDisable(true);
        patientDetailsPane.getChildren().add(updatePatientDetailsButton);
    }

    private void createPatientHouseNumberTextField() {
        patientHouseNumberTextField = new TextField();
        patientHouseNumberTextField.setLayoutX(350.0);
        patientHouseNumberTextField.setLayoutY(140.0);
        patientHouseNumberTextField.setPrefHeight(30.0);
        patientHouseNumberTextField.setPrefWidth(200.0);
        patientHouseNumberTextField.setPromptText("Podaj numer domu");
        patientDetailsPane.getChildren().add(patientHouseNumberTextField);
    }

    private void createPatientBirthDateDatePicker() {
        patientBirthDateDatePicker = new DatePicker();
        patientBirthDateDatePicker.setLayoutX(50.0);
        patientBirthDateDatePicker.setLayoutY(300.0);
        patientBirthDateDatePicker.setPrefHeight(30.0);
        patientBirthDateDatePicker.setPrefWidth(200.0);
        patientBirthDateDatePicker.setPromptText("Data urodzenia");
        patientDetailsPane.getChildren().add(patientBirthDateDatePicker);
    }

    private void createPatientBloodTypeLabel() {
        patientBloodTypeLabel = new Label();
        patientBloodTypeLabel.setLayoutX(650.0);
        patientBloodTypeLabel.setLayoutY(190.0);
        patientBloodTypeLabel.setText("Grupa krwi");
        patientDetailsPane.getChildren().add(patientBloodTypeLabel);
    }

    private void createPatientRegistrationDateLabel() {
        patientRegistrationDateLabel = new Label();
        patientRegistrationDateLabel.setLayoutX(650.0);
        patientRegistrationDateLabel.setLayoutY(270.0);
        patientRegistrationDateLabel.setText("Data rejestracji");
        patientDetailsPane.getChildren().add(patientRegistrationDateLabel);
    }

    private void createChoosePatientBloodTypeComboBox() {
        choosePatientBloodTypeComboBox = new ComboBox<>();
        choosePatientBloodTypeComboBox.setLayoutX(650.0);
        choosePatientBloodTypeComboBox.setLayoutY(220.0);
        choosePatientBloodTypeComboBox.setPrefHeight(30.0);
        choosePatientBloodTypeComboBox.setPrefWidth(200.0);
        choosePatientBloodTypeComboBox.setPromptText("Wybierz grupę krwi");
        choosePatientBloodTypeComboBox.getItems().setAll(Arrays
                .stream(BloodType.values())
                .map(BloodType::getName)
                .collect(Collectors.toList()));
        patientDetailsPane.getChildren().add(choosePatientBloodTypeComboBox);
    }

    private void createPatientSexLabel() {
        patientSexLabel = new Label();
        patientSexLabel.setLayoutX(50.0);
        patientSexLabel.setLayoutY(190.0);
        patientSexLabel.setText("Płeć");
        patientDetailsPane.getChildren().add(patientSexLabel);
    }

    private void createChoosePatientSexComboBox() {
        choosePatientSexComboBox = new ComboBox<>();
        choosePatientSexComboBox.setLayoutX(50.0);
        choosePatientSexComboBox.setLayoutY(220.0);
        choosePatientSexComboBox.setPrefHeight(30.0);
        choosePatientSexComboBox.setPrefWidth(200.0);
        choosePatientSexComboBox.setPromptText("Wybierz płeć");
        choosePatientSexComboBox.getItems().setAll(Arrays
                .stream(Sex.values())
                .map(Sex::getName)
                .collect(Collectors.toList()));
        patientDetailsPane.getChildren().add(choosePatientSexComboBox);
    }

    private void createPatientCityTextField() {
        patientCityTextField = new TextField();
        patientCityTextField.setLayoutX(350.0);
        patientCityTextField.setLayoutY(220.0);
        patientCityTextField.setPrefHeight(30.0);
        patientCityTextField.setPrefWidth(200.0);
        patientCityTextField.setPromptText("Podaj miejscowość");
        patientDetailsPane.getChildren().add(patientCityTextField);
    }

    private void createPatientStreetNameTextField() {
        patientStreetNameTextField = new TextField();
        patientStreetNameTextField.setLayoutX(350.0);
        patientStreetNameTextField.setLayoutY(60.0);
        patientStreetNameTextField.setPrefHeight(30.0);
        patientStreetNameTextField.setPrefWidth(200.0);
        patientStreetNameTextField.setPromptText("Podaj ulicę");
        patientDetailsPane.getChildren().add(patientStreetNameTextField);
    }

    private void createPatientZipCodeTextField() {
        patientZipCodeTextField = new TextField();
        patientZipCodeTextField.setLayoutX(350.0);
        patientZipCodeTextField.setLayoutY(300.0);
        patientZipCodeTextField.setPrefHeight(30.0);
        patientZipCodeTextField.setPrefWidth(200.0);
        patientZipCodeTextField.setPromptText("Podaj kod pocztowy");
        patientDetailsPane.getChildren().add(patientZipCodeTextField);
    }

    private void createPatientLastNameTextField() {
        patientLastNameTextField = new TextField();
        patientLastNameTextField.setLayoutX(50.0);
        patientLastNameTextField.setLayoutY(140.0);
        patientLastNameTextField.setPrefHeight(30.0);
        patientLastNameTextField.setPrefWidth(200.0);
        patientLastNameTextField.setPromptText("Podaj nazwisko");
        patientDetailsPane.getChildren().add(patientLastNameTextField);
    }

    private void createPatientPeselTextField() {
        patientPeselTextField = new TextField();
        patientPeselTextField.setLayoutX(650.0);
        patientPeselTextField.setLayoutY(140.0);
        patientPeselTextField.setPrefHeight(30.0);
        patientPeselTextField.setPrefWidth(200.0);
        patientPeselTextField.setPromptText("Podaj PESEL");
        patientDetailsPane.getChildren().add(patientPeselTextField);
    }

    private void createPatientRegistrationInfoTextField() {
        patientRegistrationTextField = new TextField();
        patientRegistrationTextField.setDisable(true);
        patientRegistrationTextField.setEditable(false);
        patientRegistrationTextField.setLayoutX(650.0);
        patientRegistrationTextField.setLayoutY(300.0);
        patientRegistrationTextField.setPrefHeight(30.0);
        patientRegistrationTextField.setPrefWidth(200.0);
        patientRegistrationTextField.setStyle("-fx-opacity: 1.0;");
        patientDetailsPane.getChildren().add(patientRegistrationTextField);
    }

    private void createPatientPhoneNumberTextField() {
        patientPhoneNumberTextField = new TextField();
        patientPhoneNumberTextField.setLayoutX(650.0);
        patientPhoneNumberTextField.setLayoutY(60.0);
        patientPhoneNumberTextField.setPrefHeight(30.0);
        patientPhoneNumberTextField.setPrefWidth(200.0);
        patientPhoneNumberTextField.setPromptText("Podaj numer");
        patientDetailsPane.getChildren().add(patientPhoneNumberTextField);
    }

    private void createPatientFirstNameTextField() {
        patientFirstNameTextField = new TextField();
        patientFirstNameTextField.setLayoutX(50.0);
        patientFirstNameTextField.setLayoutY(60.0);
        patientFirstNameTextField.setPrefHeight(30.0);
        patientFirstNameTextField.setPrefWidth(200.0);
        patientFirstNameTextField.setPromptText("Podaj imię");
        patientDetailsPane.getChildren().add(patientFirstNameTextField);
    }

    private void createPatientAppointmentsTable() {
        patientAppointmentsTable = new TableView<>();
        patientAppointmentsTable.setLayoutX(43.0);
        patientAppointmentsTable.setLayoutY(36.0);
        patientAppointmentsTable.setPrefHeight(450.0);
        patientAppointmentsTable.setPrefWidth(800.0);
        setPatientAppointmentsTableColumns(patientAppointmentsTable);
        setupPatientAppointmentsTablePagination();
        patientAppointmentsTable.setItems(model.patientAppointmentsTablePageContent());
        patientAppointmentsPane.getChildren().add(patientAppointmentsTable);
    }

    private void setupPatientAppointmentsTablePagination() {
        patientAppointmentsTablePagination = new Pagination(1, 0);
        patientAppointmentsTablePagination.setLayoutX(50);
        patientAppointmentsTablePagination.setLayoutY(50);
        patientAppointmentsTablePagination.setPageFactory(this::createPatientAppointmentsTablePage);
        patientAppointmentsPane.getChildren().add(patientAppointmentsTablePagination);
    }

    private Node createPatientAppointmentsTablePage(Integer pageIndex) {
        controller.updatePatientAppointmentsTableCurrentPage(pageIndex + 1);
        updatePatientAppointmentsTablePageData();
        return new BorderPane(patientAppointmentsTable);
    }

    private void updatePatientAppointmentsTablePageData() {
        Task<Void> task = FXTasks.createTask(() -> controller.updatePatientAppointmentsTablePageData());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnFailed(e -> processUpdatePatientAppointmentsTableFailureResult(task.getException().getMessage()));
    }

    private void processUpdatePatientAppointmentsTableFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(WARNING)
                .alertTitle("Nie udało się pobrać niektórych danych")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    private TableColumn<PatientAppointmentsTableRecord, String> createPatientAppointmentsTableColumn() {
        TableColumn<PatientAppointmentsTableRecord, String> column = new TableColumn<>();
        column.setPrefWidth(400.0);
        return column;
    }

    private List<TableColumn<PatientAppointmentsTableRecord, String>> createPatientAppointmentsTableColumns() {
        List<TableColumn<PatientAppointmentsTableRecord, String>> columnList = IntStream
                .range(0, 2)
                .mapToObj(i -> createPatientAppointmentsTableColumn())
                .collect(Collectors.toList());
        columnList.get(0).setText("Data wizyty");
        columnList.get(0).setCellValueFactory(c -> c.getValue().appointmentDate());
        columnList.get(1).setText("Status");
        columnList.get(1).setCellValueFactory(c -> c.getValue().appointmentStatus());
        return columnList;
    }

    private void setupValidation() {
        setPatientFirstNameTextFieldValidator();
        setPatientLastNameTextFieldValidator();
        setPatientStreetNameTextFieldValidator();
        setPatientHouseNumberTextFieldValidator();
        setPatientZipCodeTextFieldValidator();
        setPatientCityTextFieldValidator();
        setPatientPeselTextFieldValidator();
        setPatientPhoneNumberTextFieldValidator();
        setPatientSexComboBoxValidator();
        setPatientBloodTypeComboBoxValidator();
        setPatientBirthDateDatePickerValidator();
        updatePatientDetailsValidationSupport.getRegisteredControls().forEach(c -> c.setDisable(true));
    }

    private void setPatientBirthDateDatePickerValidator() {
        final String fieldName = "data urodzenia";
        Validator<LocalDate> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<LocalDate> dateValidator = FXValidation.createDateValidator(fieldName, PAST);
        updatePatientDetailsValidationSupport.registerValidator(
                patientBirthDateDatePicker,
                true,
                Validator.combine(emptyValidator, dateValidator));
    }

    private void setPatientBloodTypeComboBoxValidator() {
        final String fieldName = "grupa krwi";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        updatePatientDetailsValidationSupport.registerValidator(choosePatientBloodTypeComboBox, true, emptyValidator);
    }

    private void setPatientSexComboBoxValidator() {
        final String fieldName = "płeć";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        updatePatientDetailsValidationSupport.registerValidator(choosePatientSexComboBox, true, emptyValidator);
    }

    private void setPatientPhoneNumberTextFieldValidator() {
        final String fieldName = "nr telefonu";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9]+$"));
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 7);
        updatePatientDetailsValidationSupport.registerValidator(
                patientPhoneNumberTextField,
                true,
                Validator.combine(emptyValidator, regexValidator, rangeValidator));
    }

    private void setPatientPeselTextFieldValidator() {
        final String fieldName = "PESEL";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^(0|[1-9][0-9]*)$"));
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 11);
        updatePatientDetailsValidationSupport.registerValidator(
                patientPeselTextField,
                true,
                Validator.combine(emptyValidator, regexValidator, rangeValidator));
    }

    private void setPatientCityTextFieldValidator() {
        final String fieldName = "miejscowość";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        updatePatientDetailsValidationSupport.registerValidator(
                patientCityTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setPatientZipCodeTextFieldValidator() {
        final String fieldName = "kod pocztowy";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9 \\-]*$"));
        updatePatientDetailsValidationSupport.registerValidator(
                patientZipCodeTextField,
                true,
                Validator.combine(emptyValidator, regexValidator));
    }

    private void setPatientHouseNumberTextFieldValidator() {
        final String fieldName = "nr domu";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9a-zA-Z ./]*$"));
        updatePatientDetailsValidationSupport.registerValidator(
                patientHouseNumberTextField,
                true,
                Validator.combine(emptyValidator, regexValidator));
    }

    private void setPatientStreetNameTextFieldValidator() {
        final String fieldName = "ulica";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        updatePatientDetailsValidationSupport.registerValidator(
                patientStreetNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setPatientLastNameTextFieldValidator() {
        final String fieldName = "nazwisko";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        updatePatientDetailsValidationSupport.registerValidator(
                patientLastNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setPatientFirstNameTextFieldValidator() {
        final String fieldName = "imię";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        updatePatientDetailsValidationSupport.registerValidator(
                patientFirstNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void observeModelAndUpdate() {
        model.patientDetails().addListener((obs, oldValue, newValue) -> Platform.runLater(this::setPatientDetailsFields));
        model.patientAppointmentsTableTotalPages().addListener((obs, oldValue, newValue) ->
                Platform.runLater(() -> patientAppointmentsTablePagination.setPageCount((int) newValue)));
    }

    private void delegateEventHandlers() {
        schedulePatientAppointmentsTableUpdate();
        setTextFieldsLimit();
        updatePatientDetailsValidationSupport.validationResultProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(this::switchUpdatePatientDetailsButtonState));
        unlockUpdatePatientDetailsButton.setOnMouseClicked(e ->
                Platform.runLater(this::switchUnlockUpdatePatientDetailsButtonsState));
        updatePatientDetailsButton.setOnMouseClicked(e -> updatePatientDetails());
    }

    private void setTextFieldsLimit() {
        patientFirstNameTextField.textProperty().addListener(new TextFieldLimiter(30));
        patientLastNameTextField.textProperty().addListener(new TextFieldLimiter(15));
        patientPhoneNumberTextField.textProperty().addListener(new TextFieldLimiter(15));
        patientPeselTextField.textProperty().addListener(new TextFieldLimiter(11));
        patientCityTextField.textProperty().addListener(new TextFieldLimiter(30));
        patientZipCodeTextField.textProperty().addListener(new TextFieldLimiter(10));
        patientHouseNumberTextField.textProperty().addListener(new TextFieldLimiter(10));
        patientStreetNameTextField.textProperty().addListener(new TextFieldLimiter(30));
    }

    private void switchUnlockUpdatePatientDetailsButtonsState() {
        setPatientDetailsFields();
        updatePatientDetailsValidationSupport.getRegisteredControls().forEach(c -> c.setDisable(!c.isDisabled()));
        unlockUpdatePatientDetailsButton.setText(unlockUpdatePatientDetailsButton.getText().equals("Edytuj") ?
                "Zablokuj" : "Edytuj");
        switchUpdatePatientDetailsButtonState();
    }

    private void switchUpdatePatientDetailsButtonState() {
        updatePatientDetailsButton.setDisable(unlockUpdatePatientDetailsButton.getText().equals("Edytuj") ||
                updatePatientDetailsValidationSupport.isInvalid() || !isPatientDetailsFieldsChanged());
    }

    private boolean isPatientDetailsFieldsChanged() {
        PatientDetails patientDetails = createPatientDetails();
        return getPatientDetailsDiff(patientDetails).isPresent();
    }

    private void schedulePatientAppointmentsTableUpdate() {
        ScheduledService<Void> service = FXTasks.createService(() -> controller.updatePatientAppointmentsTablePageData());
        serviceStore.addPatientService(service);
        service.setPeriod(Duration.seconds(TABLE_REFRESH_RATE));
        service.setOnFailed(e -> processUpdatePatientAppointmentsTableFailureResult(service.getException().getMessage()));
        service.start();
    }

    private void setPatientAppointmentsTableColumns(TableView<PatientAppointmentsTableRecord> patientAppointmentsTable) {
        List<TableColumn<PatientAppointmentsTableRecord, String>> columnList = createPatientAppointmentsTableColumns();
        patientAppointmentsTable.getColumns().addAll(columnList);
    }

    void setWorkingHours(ComboBox<String> comboBoxHour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime workingHours = LocalTime.of(8, 0, 0);
        while (!workingHours.isAfter(LocalTime.of(18, 0, 0))) {
            comboBoxHour.getItems().add(workingHours.format(formatter));
            workingHours = workingHours.plusMinutes(30);
        }
    }

    private void getPatientDetails() {
        Task<Void> task = FXTasks.createTask(() -> controller.getPatientDetails());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnFailed(e -> getPatientDetailsFailureResult(task.getException().getMessage()));
    }

    private void getPatientDetailsFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(ERROR)
                .alertTitle("Nie udało się pobrać danych pacjenta")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(() -> alert.showAndWait()
                .filter(OK::equals)
                .ifPresent(e -> currentStage().close()));
    }

    private void updatePatientDetails() {
        Alert alert = FXAlert.builder()
                .alertType(CONFIRMATION)
                .contentText("Czy na pewno zaktualizować dane tego pacjenta?")
                .alertTitle("Aktualizacja danych pacjenta")
                .owner(currentStage())
                .build();
        Platform.runLater(() -> alert.showAndWait()
                .filter(OK::equals)
                .ifPresent(e -> processPatientDetailsUpdate()));
    }

    private void processPatientDetailsUpdate() {
        Platform.runLater(() -> updatePatientDetailsButton.setDisable(true));
        PatientDetails patientDetails = createPatientDetails();
        Task<Void> task = FXTasks.createTask(() -> controller.handleUpdatePatientDetailsButtonClicked(patientDetails));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> processUpdatePatientDetailsSuccessResult());
        task.setOnFailed(e -> processUpdatePatientDetailsFailureResult(task.getException().getMessage()));
    }

    private void processUpdatePatientDetailsSuccessResult() {
        Alert alert = FXAlert.builder()
                .alertType(INFORMATION)
                .alertTitle("Operacja ukończona pomyślnie")
                .contentText("Pomyślnie zaktualizowano dane pacjenta")
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    private void processUpdatePatientDetailsFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(ERROR)
                .alertTitle("Nie udało się zaktualizować danych pacjenta")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    @SneakyThrows
    private Optional<Map<String, String>> getPatientDetailsDiff(PatientDetails patientDetails) {
        Map<String, String> objectsDiff = FXTasks.getObjectsDiff(model.getPatientDetails(), patientDetails);
        return Optional.of(objectsDiff).filter(e -> !e.isEmpty());
    }

    private PatientDetails createPatientDetails() {
        String firstName = patientFirstNameTextField.getText();
        String lastName = patientLastNameTextField.getText();
        LocalDate birthDate = patientBirthDateDatePicker.getValue();
        Sex sex = Sex.findByName(choosePatientSexComboBox.getSelectionModel().getSelectedItem());
        BloodType bloodType = BloodType.findByName(choosePatientBloodTypeComboBox.getSelectionModel().getSelectedItem());
        String streetName = patientStreetNameTextField.getText();
        String houseNumber = patientHouseNumberTextField.getText();
        String zipCode = patientZipCodeTextField.getText();
        String city = patientCityTextField.getText();
        BigDecimal pesel = new BigDecimal(patientPeselTextField.getText());
        String phoneNumber = patientPhoneNumberTextField.getText();
        return new PatientDetails(firstName, lastName, birthDate, sex, bloodType,
                streetName, houseNumber, zipCode, city, pesel, phoneNumber);
    }

    private void setPatientDetailsFields() {
        PatientDetails patientDetails = model.getPatientDetails();
        patientFirstNameTextField.setText(patientDetails.getFirstName());
        patientLastNameTextField.setText(patientDetails.getLastName());
        patientBirthDateDatePicker.setValue(patientDetails.getBirthDate());
        choosePatientSexComboBox.getSelectionModel().select(patientDetails.getSex().getName());
        choosePatientBloodTypeComboBox.getSelectionModel().select(patientDetails.getBloodType().getName());
        patientStreetNameTextField.setText(patientDetails.getStreetName());
        patientHouseNumberTextField.setText(patientDetails.getHouseNumber());
        patientZipCodeTextField.setText(patientDetails.getZipCode());
        patientCityTextField.setText(patientDetails.getCity());
        patientPeselTextField.setText(patientDetails.getPesel().toString());
        patientPhoneNumberTextField.setText(patientDetails.getPhoneNumber());
        patientRegistrationTextField.setText(model.getPatientRegistrationDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }

}
