package pl.motokomando.healthcare.view.base;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.appointment.AppointmentModel;
import pl.motokomando.healthcare.model.authentication.AuthenticationModel;
import pl.motokomando.healthcare.model.authentication.utils.UserInfo;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.patient.PatientModel;
import pl.motokomando.healthcare.view.authentication.AuthenticationView;
import pl.motokomando.healthcare.view.base.utils.AcademicTitle;
import pl.motokomando.healthcare.view.base.utils.BloodType;
import pl.motokomando.healthcare.view.base.utils.DoctorRecord;
import pl.motokomando.healthcare.view.base.utils.MedicalSpecialty;
import pl.motokomando.healthcare.view.base.utils.PatientRecord;
import pl.motokomando.healthcare.view.base.utils.Sex;
import utils.FXAlert;
import utils.FXValidation;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.ButtonType.OK;
import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class BaseView {

    private final ValidationSupport addDoctorValidationSupport = new ValidationSupport();

    private final AuthenticationModel authenticationModel;

    private BaseController controller;

    private BaseModel baseModel;
    private AppointmentModel appointmentModel;
    private PatientModel patientModel;

    private TabPane basePane;

    private Tab doctorsTab;
    private TabPane doctorsPane;
    private Tab findDoctorTab;
    private AnchorPane findDoctorPane;
    private TableView<DoctorRecord> doctorsTable;
    private Tab addDoctorTab;
    private AnchorPane addDoctorPane;
    private TextField doctorFirstNameTextField;
    private Button addDoctorButton;
    private TextField doctorLastNameTextField;
    private TextField doctorPhoneNumberTextField;
    private CheckComboBox<String> chooseDoctorSpecialtyComboBox;
    private ComboBox<String> chooseDoctorAcademicTitleComboBox;
    private Label doctorSpecialtyLabel;
    private Label doctorAcademicTitleLabel;
    private Tab patientsTab;
    private TabPane patientsPane;
    private Tab findPatientTab;
    private AnchorPane findPatientPane;
    private TableView<PatientRecord> patientsTable;
    private Tab addPatientTab;
    private AnchorPane addPatientPane;
    private TextField patientFirstNameTextField;
    private TextField patientLastNameTextField;
    private DatePicker patientBirthDateDatePicker;
    private ComboBox<String> choosePatientSexComboBox;
    private ComboBox<String> choosePatientBloodTypeComboBox;
    private TextField patientStreetNameTextField;
    private TextField patientHouseNumberTextField;
    private TextField patientZipCodeTextField;
    private TextField patientCityTextField;
    private TextField patientPeselTextField;
    private TextField patientPhoneNumberTextField;
    private Button addPatientButton;
    private Label patientSexLabel;
    private Label patientBloodTypeLabel;
    private Tab userInfoTab;
    private AnchorPane userInfoPane;
    private TextField userFirstNameTextField;
    private TextField userLastNameTextField;
    private TextField userUsernameTextField;
    private TextField userEmailTextField;
    private Button logoutButton;
    private Label userFirstNameLabel;
    private Label userLastNameLabel;
    private Label userEmailLabel;
    private Label userUsernameLabel;

    private int recordsPerPage = 4; //TODO do poprawy

    public List<DoctorRecord> listDoctors;
    public List<PatientRecord> listPatients;

    public BaseView(AuthenticationModel authenticationModel) {
        this.authenticationModel = authenticationModel;
        initModel();
        setController();
        createPane();
        addContent();
        setupValidation();
        delegateEventHandlers();
        observeModelAndUpdate();

        //TODO do poprawy also

        //dodaje doktorów
        listDoctors = new ArrayList<>(10);
        listPatients = new ArrayList<>(10);
        IntStream.range(0, 10).forEach(i -> listDoctors.add(new DoctorRecord(new SimpleStringProperty("Dr"), "Andrzej", "Kowalski", "69691000" + i, "Urolog")));

        addPaginationToDoctorsTable();
        //need to add some patients first !!!
        //addPaginationToPatientsTable();
        doctorsTable.setRowFactory(tv -> {
            TableRow<DoctorRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    DoctorRecord rowData = row.getItem();
                    System.out.println("działa");
                }
            });
            return row;
        });

        patientsTable.setRowFactory(tv -> {
            TableRow<PatientRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    PatientRecord rowData = row.getItem();
                    System.out.println("działa");
                }
            });
            return row;
        });
    }

    public Parent asParent() {
        return basePane;
    }

    private Stage currentStage() {
        return (Stage) basePane.getScene().getWindow();
    }

    private void initModel() {
        baseModel = new BaseModel();
        appointmentModel = new AppointmentModel();
        patientModel = new PatientModel();
    }

    private void setController() {
        controller = new BaseController(baseModel);
    }

    private void createPane() {
        basePane = new TabPane();
        basePane.setMaxHeight(USE_PREF_SIZE);
        basePane.setMaxWidth(USE_PREF_SIZE);
        basePane.setMinHeight(USE_PREF_SIZE);
        basePane.setMinWidth(USE_PREF_SIZE);
        basePane.setPrefHeight(800.0);
        basePane.setPrefWidth(1600.0);
        basePane.setTabClosingPolicy(UNAVAILABLE);
    }

    private void addContent() {
        createPatientsTab();
        createDoctorsTab();
        createUserInfoTab();
    }

    private void createUserInfoTab() {
        userInfoTab = new Tab();
        userInfoTab.setText("Konto");
        userInfoPane = new AnchorPane();
        createUserFirstNameLabel();
        createUserFirstNameTextField();
        createUserLastNameLabel();
        createUserLastNameTextField();
        createUserUsernameLabel();
        createUserUsernameTextField();
        createUserEmailLabel();
        createUserEmailTextField();
        createLogoutButton();
        setUserDetails();
        userInfoTab.setContent(userInfoPane);
        basePane.getTabs().add(userInfoTab);
    }

    private void setUserDetails() {
        UserInfo userInfo = authenticationModel.getUserInfo();
        userFirstNameTextField.setText(userInfo.getFirstName());
        userLastNameTextField.setText(userInfo.getLastName());
        userUsernameTextField.setText(userInfo.getUsername());
        userEmailTextField.setText(userInfo.getEmail());
    }

    private void createUserFirstNameLabel() {
        userFirstNameLabel = new Label();
        userFirstNameLabel.setText("Imię");
        userFirstNameLabel.setLayoutX(650);
        userFirstNameLabel.setLayoutY(70);
        userFirstNameLabel.setFont(new Font(16.0));
        userInfoPane.getChildren().add(userFirstNameLabel);
    }

    private void createUserLastNameLabel() {
        userLastNameLabel = new Label();
        userLastNameLabel.setText("Nazwisko");
        userLastNameLabel.setLayoutX(650);
        userLastNameLabel.setLayoutY(170);
        userLastNameLabel.setFont(new Font(16.0));
        userInfoPane.getChildren().add(userLastNameLabel);
    }

    private void createUserUsernameLabel() {
        userUsernameLabel = new Label();
        userUsernameLabel.setText("Nazwa użytkownika");
        userUsernameLabel.setLayoutX(650);
        userUsernameLabel.setLayoutY(270);
        userUsernameLabel.setFont(new Font(16.0));
        userInfoPane.getChildren().add(userUsernameLabel);
    }

    private void createUserEmailLabel() {
        userEmailLabel = new Label();
        userEmailLabel.setText("Email");
        userEmailLabel.setLayoutX(650);
        userEmailLabel.setLayoutY(370);
        userEmailLabel.setFont(new Font(16.0));
        userInfoPane.getChildren().add(userEmailLabel);
    }

    private void createUserFirstNameTextField() {
        userFirstNameTextField = new TextField();
        userFirstNameTextField.setDisable(true);
        userFirstNameTextField.setEditable(false);
        userFirstNameTextField.setLayoutX(650);
        userFirstNameTextField.setLayoutY(100);
        userFirstNameTextField.setPrefWidth(300);
        userFirstNameTextField.setPrefHeight(40);
        userFirstNameTextField.setFont(new Font(16.0));
        userFirstNameTextField.setStyle("-fx-opacity: 1.0;");
        userInfoPane.getChildren().add(userFirstNameTextField);
    }

    private void createUserLastNameTextField() {
        userLastNameTextField = new TextField();
        userLastNameTextField.setDisable(true);
        userLastNameTextField.setEditable(false);
        userLastNameTextField.setLayoutX(650);
        userLastNameTextField.setLayoutY(200);
        userLastNameTextField.setPrefWidth(300);
        userLastNameTextField.setPrefHeight(40);
        userLastNameTextField.setFont(new Font(16.0));
        userLastNameTextField.setStyle("-fx-opacity: 1.0;");
        userInfoPane.getChildren().add(userLastNameTextField);
    }

    private void createUserUsernameTextField() {
        userUsernameTextField = new TextField();
        userUsernameTextField.setDisable(true);
        userUsernameTextField.setEditable(false);
        userUsernameTextField.setLayoutX(650);
        userUsernameTextField.setLayoutY(300);
        userUsernameTextField.setPrefWidth(300);
        userUsernameTextField.setPrefHeight(40);
        userUsernameTextField.setFont(new Font(16.0));
        userUsernameTextField.setStyle("-fx-opacity: 1.0;");
        userInfoPane.getChildren().add(userUsernameTextField);
    }

    private void createUserEmailTextField() {
        userEmailTextField = new TextField();
        userEmailTextField.setDisable(true);
        userEmailTextField.setEditable(false);
        userEmailTextField.setLayoutX(650);
        userEmailTextField.setLayoutY(400);
        userEmailTextField.setPrefWidth(300);
        userEmailTextField.setPrefHeight(40);
        userEmailTextField.setFont(new Font(16.0));
        userEmailTextField.setStyle("-fx-opacity: 1.0;");
        userInfoPane.getChildren().add(userEmailTextField);
    }

    private void createLogoutButton() {
        logoutButton = new Button();
        logoutButton.setText("Wyloguj się");
        logoutButton.setLayoutX(760);
        logoutButton.setLayoutY(550);
        logoutButton.setFont(new Font(16.0));
        userInfoPane.getChildren().add(logoutButton);
    }

    private void createDoctorsTab() {
        doctorsTab = new Tab();
        doctorsTab.setText("Lekarze");
        doctorsPane = new TabPane();
        doctorsPane.setTabClosingPolicy(UNAVAILABLE);
        createFindDoctorTab();
        createAddDoctorTab();
        doctorsTab.setContent(doctorsPane);
        basePane.getTabs().add(doctorsTab);
    }

    private void createAddDoctorTab() {
        addDoctorTab = new Tab();
        addDoctorTab.setText("Dodaj lekarza");
        addDoctorPane = new AnchorPane();
        addDoctorPane.setPrefHeight(800.0);
        addDoctorPane.setPrefWidth(1600.0);
        createDoctorFirstNameTextField();
        createDoctorLastNameTextField();
        createDoctorPhoneNumberTextField();
        createChooseDoctorSpecialtyComboBox();
        createChooseDoctorAcademicTitleComboBox();
        createDoctorSpecialtyLabel();
        createDoctorAcademicTitleLabel();
        createAddDoctorButton();
        addDoctorTab.setContent(addDoctorPane);
        doctorsPane.getTabs().add(addDoctorTab);
    }

    private void createDoctorAcademicTitleLabel() {
        doctorAcademicTitleLabel = new Label();
        doctorAcademicTitleLabel.setLayoutX(650.0);
        doctorAcademicTitleLabel.setLayoutY(460.0);
        doctorAcademicTitleLabel.setText("Tytuł naukowy:");
        doctorAcademicTitleLabel.setFont(new Font(16.0));
        addDoctorPane.getChildren().add(doctorAcademicTitleLabel);
    }

    private void createDoctorSpecialtyLabel() {
        doctorSpecialtyLabel = new Label();
        doctorSpecialtyLabel.setLayoutX(650.0);
        doctorSpecialtyLabel.setLayoutY(340.0);
        doctorSpecialtyLabel.setText("Specjalizacja:");
        doctorSpecialtyLabel.setFont(new Font(16.0));
        addDoctorPane.getChildren().add(doctorSpecialtyLabel);
    }

    private void createChooseDoctorAcademicTitleComboBox() {
        chooseDoctorAcademicTitleComboBox = new ComboBox<>();
        chooseDoctorAcademicTitleComboBox.setLayoutX(650.0);
        chooseDoctorAcademicTitleComboBox.setLayoutY(490.0);
        chooseDoctorAcademicTitleComboBox.setPrefHeight(40.0);
        chooseDoctorAcademicTitleComboBox.setPrefWidth(300.0);
        chooseDoctorAcademicTitleComboBox.setPromptText("Wybierz tytuł naukowy");
        chooseDoctorAcademicTitleComboBox.getItems().setAll(Arrays
                .stream(AcademicTitle.values())
                .map(AcademicTitle::getName)
                .collect(Collectors.toList()));
        addDoctorPane.getChildren().add(chooseDoctorAcademicTitleComboBox);
    }

    private void createChooseDoctorSpecialtyComboBox() {
        chooseDoctorSpecialtyComboBox = new CheckComboBox<>();
        chooseDoctorSpecialtyComboBox.setLayoutX(650.0);
        chooseDoctorSpecialtyComboBox.setLayoutY(370.0);
        chooseDoctorSpecialtyComboBox.setPrefHeight(40.0);
        chooseDoctorSpecialtyComboBox.setPrefWidth(300.0);
        chooseDoctorSpecialtyComboBox.setTitle("Wybrano");
        chooseDoctorSpecialtyComboBox.setShowCheckedCount(true);
        chooseDoctorSpecialtyComboBox.getItems().setAll(Arrays
                .stream(MedicalSpecialty.values())
                .map(MedicalSpecialty::getName)
                .collect(Collectors.toList()));
        addDoctorPane.getChildren().add(chooseDoctorSpecialtyComboBox);
    }

    private void createDoctorPhoneNumberTextField() {
        doctorPhoneNumberTextField = new TextField();
        doctorPhoneNumberTextField.setLayoutX(650.0);
        doctorPhoneNumberTextField.setLayoutY(250.0);
        doctorPhoneNumberTextField.setPrefHeight(40.0);
        doctorPhoneNumberTextField.setPrefWidth(300.0);
        doctorPhoneNumberTextField.setPromptText("Numer telefonu");
        doctorPhoneNumberTextField.setFont(new Font(16.0));
        addDoctorPane.getChildren().add(doctorPhoneNumberTextField);
    }

    private void createDoctorLastNameTextField() {
        doctorLastNameTextField = new TextField();
        doctorLastNameTextField.setLayoutX(650.0);
        doctorLastNameTextField.setLayoutY(150.0);
        doctorLastNameTextField.setPrefHeight(40.0);
        doctorLastNameTextField.setPrefWidth(300.0);
        doctorLastNameTextField.setPromptText("Nazwisko");
        doctorLastNameTextField.setFont(new Font(16.0));
        addDoctorPane.getChildren().add(doctorLastNameTextField);
    }

    private void createAddDoctorButton() {
        addDoctorButton = new Button();
        addDoctorButton.setLayoutX(738.0);
        addDoctorButton.setLayoutY(590.0);
        addDoctorButton.setMnemonicParsing(false);
        addDoctorButton.setText("Dodaj lekarza");
        addDoctorButton.setFont(new Font(16.0));
        addDoctorPane.getChildren().add(addDoctorButton);
    }

    private void createDoctorFirstNameTextField() {
        doctorFirstNameTextField = new TextField();
        doctorFirstNameTextField.setLayoutX(650.0);
        doctorFirstNameTextField.setLayoutY(50.0);
        doctorFirstNameTextField.setPrefHeight(40.0);
        doctorFirstNameTextField.setPrefWidth(300.0);
        doctorFirstNameTextField.setPromptText("Imie");
        doctorFirstNameTextField.setFont(new Font(16.0));
        addDoctorPane.getChildren().add(doctorFirstNameTextField);
    }

    private void createFindDoctorTab() {
        findDoctorTab = new Tab();
        findDoctorTab.setText("Znajdź lekarza");
        findDoctorPane = new AnchorPane();
        findDoctorPane.setMinHeight(0.0);
        findDoctorPane.setMinWidth(0.0);
        findDoctorPane.setPrefHeight(180.0);
        findDoctorPane.setPrefWidth(200.0);
        createDoctorsTable();
        findDoctorTab.setContent(findDoctorPane);
        doctorsPane.getTabs().add(findDoctorTab);
    }

    private void createPatientsTab() {
        patientsTab = new Tab();
        patientsTab.setText("Pacjenci");
        patientsPane = new TabPane();
        createFindPatientTab();
        createAddPatientTab();
        patientsTab.setContent(patientsPane);
        basePane.getTabs().add(patientsTab);
    }

    private void createAddPatientTab() {
        addPatientTab = new Tab();
        addPatientTab.setClosable(false);
        addPatientTab.setText("Dodaj pacjenta");
        addPatientPane = new AnchorPane();
        addPatientPane.setPrefHeight(180.0);
        addPatientPane.setPrefWidth(200.0);
        createPatientFirstNameTextField();
        createPatientLastNameTextField();
        createPatientBirthDateDatePicker();
        createChoosePatientSexComboBox();
        createChoosePatientBloodTypeComboBox();
        createPatientStreetNameTextField();
        createPatientHouseNumberTextField();
        createPatientZipCodeTextField();
        createPatientCityTextField();
        createPatientPeselTextField();
        createPatientPhoneNumberTextField();
        createAddPatientButton();
        createPatientSexLabel();
        createPatientBloodTypeLabel();
        addPatientTab.setContent(addPatientPane);
        patientsPane.getTabs().add(addPatientTab);
    }

    private void createPatientBloodTypeLabel() {
        patientBloodTypeLabel = new Label();
        patientBloodTypeLabel.setLayoutX(350.0);
        patientBloodTypeLabel.setLayoutY(340.0);
        patientBloodTypeLabel.setText("Grupa krwi:");
        patientBloodTypeLabel.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientBloodTypeLabel);
    }

    private void createPatientSexLabel() {
        patientSexLabel = new Label();
        patientSexLabel.setLayoutX(350.0);
        patientSexLabel.setLayoutY(260.0);
        patientSexLabel.setText("Płeć:");
        patientSexLabel.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientSexLabel);
    }

    private void createAddPatientButton() {
        addPatientButton = new Button();
        addPatientButton.setLayoutX(700.0);
        addPatientButton.setLayoutY(630.0);
        addPatientButton.setMnemonicParsing(false);
        addPatientButton.setPrefHeight(45.0);
        addPatientButton.setPrefWidth(200.0);
        addPatientButton.setText("Dodaj pacjenta");
        addPatientButton.setFont(new Font(16.0));
        addPatientPane.getChildren().add(addPatientButton);
    }

    private void createPatientPhoneNumberTextField() {
        patientPhoneNumberTextField = new TextField();
        patientPhoneNumberTextField.setLayoutX(350.0);
        patientPhoneNumberTextField.setLayoutY(450.0);
        patientPhoneNumberTextField.setPrefHeight(40.0);
        patientPhoneNumberTextField.setPrefWidth(300.0);
        patientPhoneNumberTextField.setPromptText("Numer Telefonu");
        patientPhoneNumberTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientPhoneNumberTextField);
    }

    private void createPatientPeselTextField() {
        patientPeselTextField = new TextField();
        patientPeselTextField.setLayoutX(950.0);
        patientPeselTextField.setLayoutY(450.0);
        patientPeselTextField.setPrefHeight(40.0);
        patientPeselTextField.setPrefWidth(300.0);
        patientPeselTextField.setPromptText("PESEL");
        patientPeselTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientPeselTextField);
    }

    private void createPatientCityTextField() {
        patientCityTextField = new TextField();
        patientCityTextField.setLayoutX(950.0);
        patientCityTextField.setLayoutY(290.0);
        patientCityTextField.setPrefHeight(40.0);
        patientCityTextField.setPrefWidth(300.0);
        patientCityTextField.setPromptText("Miejscowość");
        patientCityTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientCityTextField);
    }

    private void createPatientZipCodeTextField() {
        patientZipCodeTextField = new TextField();
        patientZipCodeTextField.setLayoutX(950.0);
        patientZipCodeTextField.setLayoutY(210.0);
        patientZipCodeTextField.setPrefHeight(40.0);
        patientZipCodeTextField.setPrefWidth(300.0);
        patientZipCodeTextField.setPromptText("Kod pocztowy");
        patientZipCodeTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientZipCodeTextField);
    }

    private void createPatientHouseNumberTextField() {
        patientHouseNumberTextField = new TextField();
        patientHouseNumberTextField.setLayoutX(950.0);
        patientHouseNumberTextField.setLayoutY(130.0);
        patientHouseNumberTextField.setPrefHeight(40.0);
        patientHouseNumberTextField.setPrefWidth(300.0);
        patientHouseNumberTextField.setPromptText("Numer domu");
        patientHouseNumberTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientHouseNumberTextField);
    }

    private void createPatientStreetNameTextField() {
        patientStreetNameTextField = new TextField();
        patientStreetNameTextField.setLayoutX(950.0);
        patientStreetNameTextField.setLayoutY(50.0);
        patientStreetNameTextField.setPrefHeight(40.0);
        patientStreetNameTextField.setPrefWidth(300.0);
        patientStreetNameTextField.setPromptText("Ulica");
        patientStreetNameTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientStreetNameTextField);
    }

    private void createChoosePatientBloodTypeComboBox() {
        choosePatientBloodTypeComboBox = new ComboBox<>();
        choosePatientBloodTypeComboBox.setLayoutX(350.0);
        choosePatientBloodTypeComboBox.setLayoutY(370.0);
        choosePatientBloodTypeComboBox.setPrefHeight(40.0);
        choosePatientBloodTypeComboBox.setPrefWidth(300.0);
        choosePatientBloodTypeComboBox.getItems().setAll(Arrays.stream(BloodType.values()).map(BloodType::getName).collect(Collectors.toList()));
        addPatientPane.getChildren().add(choosePatientBloodTypeComboBox);
    }

    private void createChoosePatientSexComboBox() {
        choosePatientSexComboBox = new ComboBox<>();
        choosePatientSexComboBox.setLayoutX(350.0);
        choosePatientSexComboBox.setLayoutY(290.0);
        choosePatientSexComboBox.setPrefHeight(40.0);
        choosePatientSexComboBox.setPrefWidth(300.0);
        choosePatientSexComboBox.getItems().setAll(Arrays.stream(Sex.values()).map(Sex::getName).collect(Collectors.toList()));
        addPatientPane.getChildren().add(choosePatientSexComboBox);
    }

    private void createPatientBirthDateDatePicker() {
        patientBirthDateDatePicker = new DatePicker();
        patientBirthDateDatePicker.setLayoutX(350.0);
        patientBirthDateDatePicker.setLayoutY(210.0);
        patientBirthDateDatePicker.setPrefHeight(40.0);
        patientBirthDateDatePicker.setPrefWidth(300.0);
        patientBirthDateDatePicker.setPromptText("Data urodzenia");
        addPatientPane.getChildren().add(patientBirthDateDatePicker);
    }

    private void createPatientLastNameTextField() {
        patientLastNameTextField = new TextField();
        patientLastNameTextField.setLayoutX(350.0);
        patientLastNameTextField.setLayoutY(130.0);
        patientLastNameTextField.setPrefHeight(40.0);
        patientLastNameTextField.setPrefWidth(300.0);
        patientLastNameTextField.setPromptText("Nazwisko");
        patientLastNameTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientLastNameTextField);
    }

    private void createPatientFirstNameTextField() {
        patientFirstNameTextField = new TextField();
        patientFirstNameTextField.setLayoutX(350.0);
        patientFirstNameTextField.setLayoutY(50.0);
        patientFirstNameTextField.setPrefHeight(40.0);
        patientFirstNameTextField.setPrefWidth(300.0);
        patientFirstNameTextField.setPromptText("Imie");
        patientFirstNameTextField.setFont(new Font(16.0));
        addPatientPane.getChildren().add(patientFirstNameTextField);
    }

    private void createFindPatientTab() {
        findPatientTab = new Tab();
        findPatientTab.setClosable(false);
        findPatientTab.setText("Wyszukaj pacjenta");
        findPatientPane = new AnchorPane();
        findPatientPane.setPrefHeight(180.0);
        findPatientPane.setPrefWidth(200.0);
        createPatientsTable();
        findPatientTab.setContent(findPatientPane);
        patientsPane.getTabs().add(findPatientTab);
    }

    private void createPatientsTable() {
        patientsTable = new TableView<>();
        patientsTable.setLayoutX(50.0);
        patientsTable.setLayoutY(50.0);
        patientsTable.setPrefHeight(600.0);
        patientsTable.setPrefWidth(1500.0);
        setPatientsTableContent(patientsTable);
        findPatientPane.getChildren().add(patientsTable);
    }

    private TableColumn<PatientRecord, String> createPatientsTableColumn() {
        TableColumn<PatientRecord, String> column = new TableColumn<>();
        column.setPrefWidth(375.0);
        return column;
    }

    private List<TableColumn<PatientRecord, String>> createPatientsTableColumns() {
        List<TableColumn<PatientRecord, String>> columnList = IntStream
                .range(0, 4)
                .mapToObj(i -> createPatientsTableColumn())
                .collect(Collectors.toList());
        columnList.get(0).setText("Imię");
        columnList.get(1).setText("Nazwisko");
        columnList.get(2).setText("PESEL");
        columnList.get(3).setText("Płeć");
        return columnList;
    }

    private void setPatientsTableContent(TableView<PatientRecord> patientsTable) {
        List<TableColumn<PatientRecord, String>> columnList = createPatientsTableColumns();
        patientsTable.getColumns().addAll(columnList);
    }

    private void createDoctorsTable() {
        doctorsTable = new TableView<>();
        doctorsTable.setLayoutX(50.0);
        doctorsTable.setLayoutY(50.0);
        doctorsTable.setPrefHeight(600.0);
        doctorsTable.setPrefWidth(1500.0);
        setDoctorsTableContent(doctorsTable);
        findDoctorPane.getChildren().add(doctorsTable);
    }

    private TableColumn<DoctorRecord, String> createDoctorsTableColumn() {
        TableColumn<DoctorRecord, String> column = new TableColumn<>();
        column.setPrefWidth(300.0);
        return column;
    }

    private List<TableColumn<DoctorRecord, String>> createDoctorsTableColumns() {
        List<TableColumn<DoctorRecord, String>> columnList = IntStream
                .range(0, 5)
                .mapToObj(i -> createDoctorsTableColumn())
                .collect(Collectors.toList());
        columnList.get(0).setText("Imię");
        columnList.get(1).setText("Nazwisko");
        columnList.get(2).setText("Tytuł naukowy");
        columnList.get(2).setCellValueFactory(c -> c.getValue().getTableColumnAcademicTitle());
        columnList.get(3).setText("Specjalizacja");
        columnList.get(4).setText("Numer telefonu");
        return columnList;
    }

    private void setDoctorsTableContent(TableView<DoctorRecord> doctorsTable) {
        List<TableColumn<DoctorRecord, String>> columnList = createDoctorsTableColumns();
        doctorsTable.getColumns().addAll(columnList);
    }

    private void addPaginationToDoctorsTable() {
        Pagination pagination = new Pagination((listDoctors.size() / recordsPerPage + 1), 0);
        pagination.setPageFactory(this::createDoctorPage);
        findDoctorPane.getChildren().add(pagination);
        pagination.setLayoutX(50);
        pagination.setLayoutY(50);
    }

    private void addPaginationToPatientsTable() {
        Pagination pagination = new Pagination((listPatients.size() / recordsPerPage + 1), 0);
        pagination.setPageFactory(this::createPatientPage);
        findPatientPane.getChildren().add(pagination);
        pagination.setLayoutX(50);
        pagination.setLayoutY(50);
    }

    private Node createDoctorPage(int pageIndex) {
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listDoctors.size());
        doctorsTable.setItems(FXCollections.observableArrayList(listDoctors.subList(fromIndex, toIndex)));
        return new BorderPane(doctorsTable);
    }

    private Node createPatientPage(int pageIndex) {
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listDoctors.size());
        patientsTable.setItems(FXCollections.observableArrayList(listPatients.subList(fromIndex, toIndex)));
        return new BorderPane(doctorsTable);
    }

    private void setupValidation() {
        setDoctorFirstNameTextFieldValidator();
        setDoctorLastNameTextFieldValidator();
        setDoctorPhoneNumberTextFieldValidator();
        setDoctorAcademicTitleComboBoxValidator();
        setDoctorSpecialtyComboBoxValidator();
    }

    private void setDoctorFirstNameTextFieldValidator() {
        final String fieldName = "imię";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createRangeValidator(fieldName, ValueRange.of(2, 30));
        addDoctorValidationSupport.registerValidator(doctorFirstNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setDoctorLastNameTextFieldValidator() {
        final String fieldName = "nazwisko";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createRangeValidator(fieldName, ValueRange.of(2, 30));
        addDoctorValidationSupport.registerValidator(doctorLastNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setDoctorPhoneNumberTextFieldValidator() {
        final String fieldName = "nr telefonu";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9]+$"));
        Validator<String> rangeValidator = FXValidation.createRangeValidator(fieldName, ValueRange.of(7, 15));
        addDoctorValidationSupport.registerValidator(doctorPhoneNumberTextField,
                true,
                Validator.combine(emptyValidator, regexValidator, rangeValidator));
    }

    private void setDoctorAcademicTitleComboBoxValidator() {
        final String fieldName = "tytuł naukowy";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        addDoctorValidationSupport.registerValidator(chooseDoctorAcademicTitleComboBox, true, emptyValidator);
    }

    private void setDoctorSpecialtyComboBoxValidator() {
        final String fieldName = "specjalizacja";
        ValueExtractor.addObservableValueExtractor(c -> c == chooseDoctorSpecialtyComboBox,
                c -> baseModel.doctorSpecialtyComboBoxCheckedItemsNumber());
        Validator<Integer> checkComboBoxBoxValidator = FXValidation.createCheckComboBoxValidator(fieldName);
        addDoctorValidationSupport.registerValidator(chooseDoctorSpecialtyComboBox, true, checkComboBoxBoxValidator);
    }

    private void observeModelAndUpdate() {
    }

    private void delegateEventHandlers() {
        chooseDoctorSpecialtyComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c ->
                controller.handleDoctorSpecialtyComboBoxCheckedItemsChanged(c.getList()));
        logoutButton.setOnMouseClicked(e -> logoutUser());
        addDoctorButton.setOnMouseClicked(e -> addDoctor());
    }

    private void addDoctor() {
        addDoctorValidationSupport.revalidate();
        addDoctorValidationSupport.redecorate();
        if (addDoctorValidationSupport.isInvalid()) {
            String alertContent = FXValidation.prettyPrintErrors(
                    addDoctorValidationSupport.getValidationResult().getErrors());
            Alert alert = FXAlert.builder()
                    .alertType(ERROR)
                    .contentText(alertContent)
                    .alertTitle("Niepoprawne dane")
                    .build();
            Platform.runLater(alert::showAndWait);
        } else {
            controller.handleAddDoctorButtonClicked();
        }
    }

    private void logoutUser() {
        Alert alert = FXAlert.builder()
                .alertType(CONFIRMATION)
                .contentText("Czy na pewno chcesz się wylogować?")
                .alertTitle("Wyloguj się")
                .build();
        Platform.runLater(() -> alert.showAndWait()
                .filter(OK::equals)
                .ifPresent(e -> openAuthenticationScene()));
    }

    private void openAuthenticationScene() {
        Scene scene = new Scene(new AuthenticationView().asParent(), 700, 500);
        Platform.runLater(() -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Stage stage = currentStage();
            stage.setScene(scene);
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
            stage.setTitle("Healthcare Management - Panel Logowania");
            stage.show();
        });
    }

}