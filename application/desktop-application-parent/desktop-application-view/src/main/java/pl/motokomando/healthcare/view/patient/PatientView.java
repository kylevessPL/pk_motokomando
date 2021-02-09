package pl.motokomando.healthcare.view.patient;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import pl.motokomando.healthcare.controller.patient.PatientController;
import pl.motokomando.healthcare.model.patient.PatientModel;
import pl.motokomando.healthcare.model.patient.utils.AppointmentRecord;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class PatientView {

    private final PatientModel model;
    private final PatientController controller;

    private TabPane patientPane;

    private Tab patientDetailsTab;
    private AnchorPane patientDetailsPane;
    private TextField patientFirstNameTextField;
    private TextField patientLastNameTextField;
    private TextField patientPhoneNumberTextField;
    private TextField patientZipCodeTextField;
    private TextField patientStreetNameTextField;
    private TextField patientCityTextField;
    private ComboBox<String> patientSexComboBox;
    private Label patientSexLabel;
    private ComboBox<String> patientBloodTypeComboBox;
    private Label patientBloodTypeLabel;
    private DatePicker patientBirthDateDatePicker;
    private TextField patientHouseNumberTextField;
    private TextField patientPeselTextField;
    private Button updatePatientDetailsButton;
    private Tab patientAppointmentsTab;
    private AnchorPane patientAppointmentsPane;
    private TableView<AppointmentRecord> patientAppointmentsTable;
    private Tab scheduleAppointmentTab;
    private AnchorPane scheduleAppointmentPane;
    private DatePicker appointmentDateDatePicker;
    private ComboBox<String> chooseDoctorComboBox;
    private ComboBox<String> chooseAppointmentHourComboBox;
    private Button scheduleAppointmentButton;
    private Button unlockAppointmentDetailsChangeButton;
    private Label scheduleAppointmentDoctorLabel;
    private Label scheduleAppointmentHourLabel;

    public List<AppointmentRecord> listAppointments;
    int recordsPerPage = 4;

    public PatientView(PatientModel model, PatientController controller) {
        this.model = model;
        this.controller = controller;
        createPane();
        addContent();

        //TODO fill up table with data
        //addPaginationToDoctorsTable();
    }

    public Parent asParent() {
        return patientPane;
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
        patientAppointmentsTab.setContent(patientAppointmentsPane);
        patientPane.getTabs().add(patientAppointmentsTab);
    }

    private void createScheduleAppointmentTab() {
        scheduleAppointmentTab = new Tab();
        scheduleAppointmentTab.setText("Zarezerwuj wizytę");
        scheduleAppointmentPane = new AnchorPane();
        scheduleAppointmentPane.setPrefHeight(180.0);
        scheduleAppointmentPane.setPrefWidth(200.0);
        createAppointmentDateDatePicker();
        createChooseDoctorComboBox();
        createChooseAppointmentHourComboBox();
        createScheduleAppointmentButton();
        createScheduleAppointmentDoctorLabel();
        createScheduleAppointmentHourLabel();
        scheduleAppointmentTab.setContent(scheduleAppointmentPane);
        patientPane.getTabs().add(scheduleAppointmentTab);
    }

    private void createScheduleAppointmentHourLabel() {
        scheduleAppointmentHourLabel = new Label();
        scheduleAppointmentHourLabel.setLayoutX(300.0);
        scheduleAppointmentHourLabel.setLayoutY(250.0);
        scheduleAppointmentHourLabel.setText("Godzina");
        scheduleAppointmentHourLabel.setFont(new Font(16.0));
        scheduleAppointmentPane.getChildren().add(scheduleAppointmentHourLabel);
    }

    private void createScheduleAppointmentDoctorLabel() {
        scheduleAppointmentDoctorLabel = new Label();
        scheduleAppointmentDoctorLabel.setLayoutX(300.0);
        scheduleAppointmentDoctorLabel.setLayoutY(50.0);
        scheduleAppointmentDoctorLabel.setText("Lekarz");
        scheduleAppointmentDoctorLabel.setFont(new Font(16.0));
        scheduleAppointmentPane.getChildren().add(scheduleAppointmentDoctorLabel);
    }

    private void createScheduleAppointmentButton() {
        scheduleAppointmentButton = new Button();
        scheduleAppointmentButton.setLayoutX(401.0);
        scheduleAppointmentButton.setLayoutY(400.0);
        scheduleAppointmentButton.setMnemonicParsing(false);
        scheduleAppointmentButton.setText("Zarezerwuj");
        scheduleAppointmentButton.setFont(new Font(16.0));
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
        appointmentDateDatePicker.setLayoutY(170.0);
        appointmentDateDatePicker.setPrefHeight(40.0);
        appointmentDateDatePicker.setPrefWidth(300.0);
        appointmentDateDatePicker.setPromptText("Wybierz datę");
        scheduleAppointmentPane.getChildren().add(appointmentDateDatePicker);
    }

    private void createPatientDetailsTab() {
        patientDetailsTab = new Tab();
        patientDetailsTab.setText("Pacjent");
        patientDetailsPane = new AnchorPane();
        patientDetailsPane.setPrefHeight(180.0);
        patientDetailsPane.setPrefWidth(200.0);
        createPatientFirstNameTextField();
        createPatientPhoneNumberTextField();
        createPatientPeselTextField();
        createPatientLastNameTextField();
        createPatientZipCodeTextField();
        createPatientStreetNameTextField();
        createPatientCityTextField();
        createPatientSexComboBox();
        createPatientSexLabel();
        createPatientBloodTypeComboBox();
        createPatientBloodTypeLabel();
        createPatientBirthDateDatePicker();
        createPatientHouseNumberTextField();
        createUpdatePatientDetailsButton();
        createUnlockAppointmentDetailsChangeButton();
        patientDetailsTab.setContent(patientDetailsPane);
        patientPane.getTabs().add(patientDetailsTab);
    }

    private void createUnlockAppointmentDetailsChangeButton() {
        unlockAppointmentDetailsChangeButton = new Button();
        unlockAppointmentDetailsChangeButton.setLayoutX(480.0);
        unlockAppointmentDetailsChangeButton.setLayoutY(436.0);
        unlockAppointmentDetailsChangeButton.setMnemonicParsing(false);
        unlockAppointmentDetailsChangeButton.setText("Edytuj");
        unlockAppointmentDetailsChangeButton.setFont(new Font(14.0));
        patientDetailsPane.getChildren().add(unlockAppointmentDetailsChangeButton);
    }

    private void createUpdatePatientDetailsButton() {
        updatePatientDetailsButton = new Button();
        updatePatientDetailsButton.setLayoutX(360.0);
        updatePatientDetailsButton.setLayoutY(436.0);
        updatePatientDetailsButton.setMnemonicParsing(false);
        updatePatientDetailsButton.setText("Zaktualizuj");
        updatePatientDetailsButton.setFont(new Font(14.0));
        patientDetailsPane.getChildren().add(updatePatientDetailsButton);
    }

    private void createPatientHouseNumberTextField() {
        patientHouseNumberTextField = new TextField();
        patientHouseNumberTextField.setLayoutX(350.0);
        patientHouseNumberTextField.setLayoutY(140.0);
        patientHouseNumberTextField.setPrefHeight(30.0);
        patientHouseNumberTextField.setPrefWidth(200.0);
        patientHouseNumberTextField.setPromptText("Numer domu");
        patientHouseNumberTextField.setFont(new Font(14.0));
        patientHouseNumberTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientHouseNumberTextField);
    }

    private void createPatientBirthDateDatePicker() {
        patientBirthDateDatePicker = new DatePicker();
        patientBirthDateDatePicker.setLayoutX(50.0);
        patientBirthDateDatePicker.setLayoutY(300.0);
        patientBirthDateDatePicker.setPrefHeight(30.0);
        patientBirthDateDatePicker.setPrefWidth(200.0);
        patientBirthDateDatePicker.setPromptText("Data urodzenia");
        patientBirthDateDatePicker.setDisable(true);
        patientDetailsPane.getChildren().add(patientBirthDateDatePicker);
    }

    private void createPatientBloodTypeLabel() {
        patientBloodTypeLabel = new Label();
        patientBloodTypeLabel.setLayoutX(650.0);
        patientBloodTypeLabel.setLayoutY(280.0);
        patientBloodTypeLabel.setText("Grupa krwi");
        patientBloodTypeLabel.setFont(new Font(14.0));
        patientDetailsPane.getChildren().add(patientBloodTypeLabel);
    }

    private void createPatientBloodTypeComboBox() {
        patientBloodTypeComboBox = new ComboBox<>();
        patientBloodTypeComboBox.setLayoutX(650.0);
        patientBloodTypeComboBox.setLayoutY(300.0);
        patientBloodTypeComboBox.setPrefHeight(30.0);
        patientBloodTypeComboBox.setPrefWidth(200.0);
        patientBloodTypeComboBox.setDisable(true);
        patientDetailsPane.getChildren().add(patientBloodTypeComboBox);
    }

    private void createPatientSexLabel() {
        patientSexLabel = new Label();
        patientSexLabel.setLayoutX(50.0);
        patientSexLabel.setLayoutY(200.0);
        patientSexLabel.setText("Płeć");
        patientSexLabel.setFont(new Font(14.0));
        patientDetailsPane.getChildren().add(patientSexLabel);
    }

    private void createPatientSexComboBox() {
        patientSexComboBox = new ComboBox<>();
        patientSexComboBox.setLayoutX(50.0);
        patientSexComboBox.setLayoutY(220.0);
        patientSexComboBox.setPrefHeight(30.0);
        patientSexComboBox.setPrefWidth(200.0);
        patientSexComboBox.setDisable(true);
        patientDetailsPane.getChildren().add(patientSexComboBox);
    }

    private void createPatientCityTextField() {
        patientCityTextField = new TextField();
        patientCityTextField.setLayoutX(350.0);
        patientCityTextField.setLayoutY(220.0);
        patientCityTextField.setPrefHeight(30.0);
        patientCityTextField.setPrefWidth(200.0);
        patientCityTextField.setPromptText("Miejscowość");
        patientCityTextField.setFont(new Font(14.0));
        patientCityTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientCityTextField);
    }

    private void createPatientStreetNameTextField() {
        patientStreetNameTextField = new TextField();
        patientStreetNameTextField.setLayoutX(350.0);
        patientStreetNameTextField.setLayoutY(60.0);
        patientStreetNameTextField.setPrefHeight(30.0);
        patientStreetNameTextField.setPrefWidth(200.0);
        patientStreetNameTextField.setPromptText("Ulica");
        patientStreetNameTextField.setFont(new Font(14.0));
        patientStreetNameTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientStreetNameTextField);
    }

    private void createPatientZipCodeTextField() {
        patientZipCodeTextField = new TextField();
        patientZipCodeTextField.setLayoutX(350.0);
        patientZipCodeTextField.setLayoutY(300.0);
        patientZipCodeTextField.setPrefHeight(30.0);
        patientZipCodeTextField.setPrefWidth(200.0);
        patientZipCodeTextField.setPromptText("Kod pocztowy");
        patientZipCodeTextField.setFont(new Font(14.0));
        patientZipCodeTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientZipCodeTextField);
    }

    private void createPatientLastNameTextField() {
        patientLastNameTextField = new TextField();
        patientLastNameTextField.setLayoutX(50.0);
        patientLastNameTextField.setLayoutY(140.0);
        patientLastNameTextField.setPrefHeight(30.0);
        patientLastNameTextField.setPrefWidth(200.0);
        patientLastNameTextField.setPromptText("Nazwisko");
        patientLastNameTextField.setFont(new Font(14.0));
        patientLastNameTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientLastNameTextField);
    }

    private void createPatientPeselTextField() {
        patientPeselTextField = new TextField();
        patientPeselTextField.setLayoutX(650.0);
        patientPeselTextField.setLayoutY(220.0);
        patientPeselTextField.setPrefHeight(30.0);
        patientPeselTextField.setPrefWidth(200.0);
        patientPeselTextField.setPromptText("PESEL");
        patientPeselTextField.setFont(new Font(14.0));
        patientPeselTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientPeselTextField);
    }

    private void createPatientPhoneNumberTextField() {
        patientPhoneNumberTextField = new TextField();
        patientPhoneNumberTextField.setLayoutX(650.0);
        patientPhoneNumberTextField.setLayoutY(60.0);
        patientPhoneNumberTextField.setPrefHeight(30.0);
        patientPhoneNumberTextField.setPrefWidth(200.0);
        patientPhoneNumberTextField.setPromptText("Numer telefonu");
        patientPhoneNumberTextField.setFont(new Font(14.0));
        patientPhoneNumberTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientPhoneNumberTextField);
    }

    private void createPatientFirstNameTextField() {
        patientFirstNameTextField = new TextField();
        patientFirstNameTextField.setLayoutX(50.0);
        patientFirstNameTextField.setLayoutY(60.0);
        patientFirstNameTextField.setPrefHeight(30.0);
        patientFirstNameTextField.setPrefWidth(200.0);
        patientFirstNameTextField.setPromptText("Imie");
        patientFirstNameTextField.setFont(new Font(14.0));
        patientFirstNameTextField.setDisable(true);
        patientDetailsPane.getChildren().add(patientFirstNameTextField);
    }

    private void createPatientAppointmentsTable() {
        patientAppointmentsTable = new TableView<>();
        patientAppointmentsTable.setLayoutX(43.0);
        patientAppointmentsTable.setLayoutY(36.0);
        patientAppointmentsTable.setPrefHeight(500.0);
        setPatientAppointmentsTableContent(patientAppointmentsTable);
        patientAppointmentsPane.getChildren().add(patientAppointmentsTable);
    }

    private TableColumn<AppointmentRecord, String> createPatientAppointmentsTableColumn() {
        TableColumn<AppointmentRecord, String> column = new TableColumn<>();
        column.setPrefWidth(266.0);
        return column;
    }

    private List<TableColumn<AppointmentRecord, String>> createPatientAppointmentsTableColumns() {
        List<TableColumn<AppointmentRecord, String>> columnList = IntStream
                .range(0, 3)
                .mapToObj(i -> createPatientAppointmentsTableColumn())
                .collect(Collectors.toList());
        columnList.get(0).setText("Data");
        columnList.get(1).setText("Status");
        columnList.get(2).setText("Lekarz");
        return columnList;
    }

    private void setPatientAppointmentsTableContent(TableView<AppointmentRecord> patientAppointmentsTable) {
        List<TableColumn<AppointmentRecord, String>> columnList = createPatientAppointmentsTableColumns();
        patientAppointmentsTable.getColumns().addAll(columnList);
    }

    private void addPaginationToDoctorsTable() {
        Pagination pagination = new Pagination((listAppointments.size() / recordsPerPage + 1), 0);
        pagination.setPageFactory(this::createAppointmentPage);
        patientAppointmentsPane.getChildren().add(pagination);
        pagination.setLayoutX(50);
        pagination.setLayoutY(50);
    }

    public Node createAppointmentPage(int pageIndex) {
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listAppointments.size());
        patientAppointmentsTable.setItems(FXCollections.observableArrayList(listAppointments.subList(fromIndex, toIndex)));
        return new BorderPane(patientAppointmentsTable);
    }

    void setWorkingHours(ComboBox<String> comboBoxHour) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime workingHours = LocalTime.of(8, 0, 0);
        while (!workingHours.isAfter(LocalTime.of(18, 0, 0))) {
            comboBoxHour.getItems().add(workingHours.format(formatter));
            workingHours = workingHours.plusMinutes(30);
        }
    }

}
