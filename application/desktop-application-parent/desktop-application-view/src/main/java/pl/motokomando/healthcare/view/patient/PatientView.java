package pl.motokomando.healthcare.view.patient;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import pl.motokomando.healthcare.model.patient.utils.AppointmentRecord;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class PatientView {

    private final PatientController controller;

    private TabPane patientPane;

    private Tab tabPatient;
    private AnchorPane anchorPanePatient;
    private TextField textFieldPatientName;
    private TextField textFieldPatientPhoneNumber;
    private TextField textFieldPatientIdNumber;
    private TextField textFieldPatientSurname;
    private TextField textFieldPatientZipCode;
    private TextField textFieldPatientStreet;
    private TextField textFieldPatientCity;
    private ChoiceBox<String> choiceBoxPatientSex;
    private Label labelPatientSex;
    private ChoiceBox<String> choiceBoxPatientDocumentType;
    private ChoiceBox<String> choiceBoxPatientBloodGroup;
    private Label labelPatientBloodGroup;
    private Label labelPatientDocumentType;
    private DatePicker datePickerPatientBirthDay;
    private TextField textFieldPatientHouseNumber;
    private Button buttonPatientUpdate;
    private Tab tabAppointments;
    private AnchorPane anchorPaneAppointment;
    private TableView<AppointmentRecord> tableViewAppointments;
    private TableColumn<AppointmentRecord, String> tableColumnAppointmentsDate;
    private TableColumn<AppointmentRecord, String> tableColumnAppointmentsStatus;
    private TableColumn<AppointmentRecord, String> tableColumnAppointmentsDoctor;
    private Tab tabBookAppointment;
    private AnchorPane anchorPaneBookAppointment;
    private DatePicker datePickerBookAppointmentDate;
    private ComboBox<String> comboBoxBookAppointmentDoctor;
    private ComboBox<String> comboBoxBookAppointmentHour;
    private Button buttonBookAppointmentBook;
    private Button buttonUnlockChanging;
    private Label labelBookAppointmentDoctor;
    private Label labelBookAppointmentHour;

    public List<AppointmentRecord> listAppointments;
    int recordsPerPage = 4;

    public PatientView(PatientController controller) {
        this.controller = controller;
        createPane();
        addContent();

        //neeto to fill up with data
        //addPaginationToDoctorsTable();
    }

    private void addPaginationToDoctorsTable() {
        Pagination pagination = new Pagination((listAppointments.size() / recordsPerPage + 1), 0);
        pagination.setPageFactory(this::createAppointmentPage);
        anchorPaneAppointment.getChildren().add(pagination);
        pagination.setLayoutX(50);
        pagination.setLayoutY(50);
    }

    public Node createAppointmentPage(int pageIndex){
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listAppointments.size());
        tableViewAppointments.setItems(FXCollections.observableArrayList(listAppointments.subList(fromIndex, toIndex)));

        return new BorderPane(tableViewAppointments);
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
        tabPatient = new Tab();
        anchorPanePatient = new AnchorPane();
        textFieldPatientName = new TextField();
        textFieldPatientPhoneNumber = new TextField();
        textFieldPatientIdNumber = new TextField();
        textFieldPatientSurname = new TextField();
        textFieldPatientZipCode = new TextField();
        textFieldPatientStreet = new TextField();
        textFieldPatientCity = new TextField();
        choiceBoxPatientSex = new ChoiceBox<>();
        labelPatientSex = new Label();
        choiceBoxPatientDocumentType = new ChoiceBox<>();
        choiceBoxPatientBloodGroup = new ChoiceBox<>();
        labelPatientBloodGroup = new Label();
        labelPatientDocumentType = new Label();
        datePickerPatientBirthDay = new DatePicker();
        textFieldPatientHouseNumber = new TextField();
        buttonPatientUpdate = new Button();
        tabAppointments = new Tab();
        anchorPaneAppointment = new AnchorPane();
        tableViewAppointments = new TableView<>();
        tableColumnAppointmentsDate = new TableColumn<>();
        tableColumnAppointmentsStatus = new TableColumn<>();
        tableColumnAppointmentsDoctor = new TableColumn<>();
        tabBookAppointment = new Tab();
        anchorPaneBookAppointment = new AnchorPane();
        datePickerBookAppointmentDate = new DatePicker();
        comboBoxBookAppointmentDoctor = new ComboBox<>();
        comboBoxBookAppointmentHour = new ComboBox<>();
        buttonBookAppointmentBook = new Button();
        buttonUnlockChanging = new Button();
        labelBookAppointmentDoctor = new Label();
        labelBookAppointmentHour = new Label();

        tabPatient.setText("Pacjent");

        anchorPanePatient.setMinHeight(0.0);
        anchorPanePatient.setMinWidth(0.0);
        anchorPanePatient.setPrefHeight(180.0);
        anchorPanePatient.setPrefWidth(200.0);

        textFieldPatientName.setLayoutX(50.0);
        textFieldPatientName.setLayoutY(60.0);
        textFieldPatientName.setPrefHeight(30.0);
        textFieldPatientName.setPrefWidth(200.0);
        textFieldPatientName.setPromptText("Imie");
        textFieldPatientName.setFont(new Font(14.0));
        textFieldPatientName.setDisable(true);

        textFieldPatientPhoneNumber.setLayoutX(650.0);
        textFieldPatientPhoneNumber.setLayoutY(60.0);
        textFieldPatientPhoneNumber.setPrefHeight(30.0);
        textFieldPatientPhoneNumber.setPrefWidth(200.0);
        textFieldPatientPhoneNumber.setPromptText("Numer telefonu");
        textFieldPatientPhoneNumber.setFont(new Font(14.0));
        textFieldPatientPhoneNumber.setDisable(true);

        textFieldPatientIdNumber.setLayoutX(650.0);
        textFieldPatientIdNumber.setLayoutY(220.0);
        textFieldPatientIdNumber.setPrefHeight(30.0);
        textFieldPatientIdNumber.setPrefWidth(200.0);
        textFieldPatientIdNumber.setPromptText("Numer dokumentu");
        textFieldPatientIdNumber.setFont(new Font(14.0));
        textFieldPatientIdNumber.setDisable(true);

        textFieldPatientSurname.setLayoutX(50.0);
        textFieldPatientSurname.setLayoutY(140.0);
        textFieldPatientSurname.setPrefHeight(30.0);
        textFieldPatientSurname.setPrefWidth(200.0);
        textFieldPatientSurname.setPromptText("Nazwisko");
        textFieldPatientSurname.setFont(new Font(14.0));
        textFieldPatientSurname.setDisable(true);

        textFieldPatientZipCode.setLayoutX(350.0);
        textFieldPatientZipCode.setLayoutY(300.0);
        textFieldPatientZipCode.setPrefHeight(30.0);
        textFieldPatientZipCode.setPrefWidth(200.0);
        textFieldPatientZipCode.setPromptText("Kod pocztowy");
        textFieldPatientZipCode.setFont(new Font(14.0));
        textFieldPatientZipCode.setDisable(true);

        textFieldPatientStreet.setLayoutX(350.0);
        textFieldPatientStreet.setLayoutY(60.0);
        textFieldPatientStreet.setPrefHeight(30.0);
        textFieldPatientStreet.setPrefWidth(200.0);
        textFieldPatientStreet.setPromptText("Ulica");
        textFieldPatientStreet.setFont(new Font(14.0));
        textFieldPatientStreet.setDisable(true);

        textFieldPatientCity.setLayoutX(350.0);
        textFieldPatientCity.setLayoutY(220.0);
        textFieldPatientCity.setPrefHeight(30.0);
        textFieldPatientCity.setPrefWidth(200.0);
        textFieldPatientCity.setPromptText("Miejscowość");
        textFieldPatientCity.setFont(new Font(14.0));
        textFieldPatientCity.setDisable(true);

        choiceBoxPatientSex.setLayoutX(50.0);
        choiceBoxPatientSex.setLayoutY(220.0);
        choiceBoxPatientSex.setPrefHeight(30.0);
        choiceBoxPatientSex.setPrefWidth(200.0);
        choiceBoxPatientSex.setDisable(true);

        labelPatientSex.setLayoutX(50.0);
        labelPatientSex.setLayoutY(200.0);
        labelPatientSex.setText("Płeć");
        labelPatientSex.setFont(new Font(14.0));

        choiceBoxPatientDocumentType.setLayoutX(650.0);
        choiceBoxPatientDocumentType.setLayoutY(140.0);
        choiceBoxPatientDocumentType.setPrefHeight(30.0);
        choiceBoxPatientDocumentType.setPrefWidth(200.0);
        choiceBoxPatientDocumentType.setDisable(true);

        choiceBoxPatientBloodGroup.setLayoutX(650.0);
        choiceBoxPatientBloodGroup.setLayoutY(300.0);
        choiceBoxPatientBloodGroup.setPrefHeight(30.0);
        choiceBoxPatientBloodGroup.setPrefWidth(200.0);
        choiceBoxPatientBloodGroup.setDisable(true);

        labelPatientBloodGroup.setLayoutX(650.0);
        labelPatientBloodGroup.setLayoutY(280.0);
        labelPatientBloodGroup.setText("Grupa krwi");
        labelPatientBloodGroup.setFont(new Font(14.0));

        labelPatientDocumentType.setLayoutX(650.0);
        labelPatientDocumentType.setLayoutY(120.0);
        labelPatientDocumentType.setText("Dokument");
        labelPatientDocumentType.setFont(new Font(14.0));

        datePickerPatientBirthDay.setLayoutX(50.0);
        datePickerPatientBirthDay.setLayoutY(300.0);
        datePickerPatientBirthDay.setPrefHeight(30.0);
        datePickerPatientBirthDay.setPrefWidth(200.0);
        datePickerPatientBirthDay.setPromptText("Data urodzenia");
        datePickerPatientBirthDay.setDisable(true);

        textFieldPatientHouseNumber.setLayoutX(350.0);
        textFieldPatientHouseNumber.setLayoutY(140.0);
        textFieldPatientHouseNumber.setPrefHeight(30.0);
        textFieldPatientHouseNumber.setPrefWidth(200.0);
        textFieldPatientHouseNumber.setPromptText("Numer domu");
        textFieldPatientHouseNumber.setFont(new Font(14.0));
        textFieldPatientHouseNumber.setDisable(true);

        tabAppointments.setText("Wizyty");

        anchorPaneAppointment.setMinHeight(0.0);
        anchorPaneAppointment.setMinWidth(0.0);
        anchorPaneAppointment.setPrefHeight(180.0);
        anchorPaneAppointment.setPrefWidth(200.0);

        tableViewAppointments.setLayoutX(43.0);
        tableViewAppointments.setLayoutY(36.0);
        tableViewAppointments.setPrefHeight(500.0);
        tableViewAppointments.setPrefWidth(800.0);

        tableColumnAppointmentsDate.setPrefWidth(266.0);
        tableColumnAppointmentsDate.setText("Data");

        tableColumnAppointmentsStatus.setPrefWidth(266.0);
        tableColumnAppointmentsStatus.setText("Status");

        tableColumnAppointmentsDoctor.setPrefWidth(268.0);
        tableColumnAppointmentsDoctor.setText("Lekarz");

        tabAppointments.setContent(anchorPaneAppointment);

        tabBookAppointment.setText("Zarezerwuj wizytę");

        anchorPaneBookAppointment.setMinHeight(0.0);
        anchorPaneBookAppointment.setMinWidth(0.0);
        anchorPaneBookAppointment.setPrefHeight(180.0);
        anchorPaneBookAppointment.setPrefWidth(200.0);

        datePickerBookAppointmentDate.setLayoutX(300.0);
        datePickerBookAppointmentDate.setLayoutY(170.0);
        datePickerBookAppointmentDate.setPrefHeight(40.0);
        datePickerBookAppointmentDate.setPrefWidth(300.0);
        datePickerBookAppointmentDate.setPromptText("Wybierz datę");

        comboBoxBookAppointmentDoctor.setLayoutX(300.0);
        comboBoxBookAppointmentDoctor.setLayoutY(80.0);
        comboBoxBookAppointmentDoctor.setPrefHeight(40.0);
        comboBoxBookAppointmentDoctor.setPrefWidth(300.0);

        comboBoxBookAppointmentHour.setLayoutX(300.0);
        comboBoxBookAppointmentHour.setLayoutY(280.0);
        comboBoxBookAppointmentHour.setPrefHeight(40.0);
        comboBoxBookAppointmentHour.setPrefWidth(300.0);
        setWorkingHours(comboBoxBookAppointmentHour);

        buttonBookAppointmentBook.setLayoutX(401.0);
        buttonBookAppointmentBook.setLayoutY(400.0);
        buttonBookAppointmentBook.setMnemonicParsing(false);
        buttonBookAppointmentBook.setText("Zarezerwuj");
        buttonBookAppointmentBook.setFont(new Font(16.0));

        buttonPatientUpdate.setLayoutX(360.0);
        buttonPatientUpdate.setLayoutY(436.0);
        buttonPatientUpdate.setMnemonicParsing(false);
        buttonPatientUpdate.setText("Zaktualizuj");
        buttonPatientUpdate.setFont(new Font(14.0));
        tabPatient.setContent(anchorPanePatient);

        buttonUnlockChanging.setLayoutX(480.0);
        buttonUnlockChanging.setLayoutY(436.0);
        buttonUnlockChanging.setMnemonicParsing(false);
        buttonUnlockChanging.setText("Edytuj");
        buttonUnlockChanging.setFont(new Font(14.0));

        labelBookAppointmentDoctor.setLayoutX(300.0);
        labelBookAppointmentDoctor.setLayoutY(50.0);
        labelBookAppointmentDoctor.setText("Lekarz");
        labelBookAppointmentDoctor.setFont(new Font(16.0));

        labelBookAppointmentHour.setLayoutX(300.0);
        labelBookAppointmentHour.setLayoutY(250.0);
        labelBookAppointmentHour.setText("Godzina");
        labelBookAppointmentHour.setFont(new Font(16.0));
        tabBookAppointment.setContent(anchorPaneBookAppointment);

        anchorPanePatient.getChildren().add(textFieldPatientName);
        anchorPanePatient.getChildren().add(textFieldPatientPhoneNumber);
        anchorPanePatient.getChildren().add(textFieldPatientIdNumber);
        anchorPanePatient.getChildren().add(textFieldPatientSurname);
        anchorPanePatient.getChildren().add(textFieldPatientZipCode);
        anchorPanePatient.getChildren().add(textFieldPatientStreet);
        anchorPanePatient.getChildren().add(textFieldPatientCity);
        anchorPanePatient.getChildren().add(choiceBoxPatientSex);
        anchorPanePatient.getChildren().add(labelPatientSex);
        anchorPanePatient.getChildren().add(choiceBoxPatientDocumentType);
        anchorPanePatient.getChildren().add(choiceBoxPatientBloodGroup);
        anchorPanePatient.getChildren().add(labelPatientBloodGroup);
        anchorPanePatient.getChildren().add(labelPatientDocumentType);
        anchorPanePatient.getChildren().add(datePickerPatientBirthDay);
        anchorPanePatient.getChildren().add(textFieldPatientHouseNumber);
        anchorPanePatient.getChildren().add(buttonPatientUpdate);
        anchorPanePatient.getChildren().add(buttonUnlockChanging);
        patientPane.getTabs().add(tabPatient);
        tableViewAppointments.getColumns().add(tableColumnAppointmentsDate);
        tableViewAppointments.getColumns().add(tableColumnAppointmentsStatus);
        tableViewAppointments.getColumns().add(tableColumnAppointmentsDoctor);
        anchorPaneAppointment.getChildren().add(tableViewAppointments);
        patientPane.getTabs().add(tabAppointments);
        anchorPaneBookAppointment.getChildren().add(datePickerBookAppointmentDate);
        anchorPaneBookAppointment.getChildren().add(comboBoxBookAppointmentDoctor);
        anchorPaneBookAppointment.getChildren().add(comboBoxBookAppointmentHour);
        anchorPaneBookAppointment.getChildren().add(buttonBookAppointmentBook);
        anchorPaneBookAppointment.getChildren().add(labelBookAppointmentDoctor);
        anchorPaneBookAppointment.getChildren().add(labelBookAppointmentHour);
        patientPane.getTabs().add(tabBookAppointment);
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
