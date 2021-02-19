package pl.motokomando.healthcare.view.base;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.tools.ValueExtractor;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.base.utils.AcademicTitle;
import pl.motokomando.healthcare.model.base.utils.BaseTableRecord;
import pl.motokomando.healthcare.model.base.utils.BloodType;
import pl.motokomando.healthcare.model.base.utils.DoctorDetails;
import pl.motokomando.healthcare.model.base.utils.MedicalSpecialty;
import pl.motokomando.healthcare.model.base.utils.PatientDetails;
import pl.motokomando.healthcare.model.base.utils.Sex;
import pl.motokomando.healthcare.model.utils.ServiceStore;
import pl.motokomando.healthcare.model.utils.SessionStore;
import pl.motokomando.healthcare.model.utils.UserInfo;
import pl.motokomando.healthcare.view.authentication.AuthenticationView;
import pl.motokomando.healthcare.view.patient.PatientView;
import utils.DefaultDatePickerConverter;
import utils.FXAlert;
import utils.FXTasks;
import utils.FXValidation;
import utils.TextFieldLimiter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
import static javafx.stage.Modality.WINDOW_MODAL;
import static utils.DateConstraints.PAST;

public class BaseView {

    private static final int TABLE_REFRESH_RATE = 10;

    private final ServiceStore serviceStore = ServiceStore.getInstance();
    private final SessionStore sessionStore = SessionStore.getInstance();

    private final ValidationSupport addPatientValidationSupport = new ValidationSupport();
    private final ValidationSupport addDoctorValidationSupport = new ValidationSupport();

    private BaseController controller;

    private BaseModel model;

    private TabPane basePane;

    private Tab doctorsTab;
    private TabPane doctorsPane;
    private Tab findDoctorTab;
    private AnchorPane findDoctorPane;
    private TableView<BaseTableRecord> doctorsTable;
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
    private TableView<BaseTableRecord> patientsTable;
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
    private Label doctorFirstNameLabel;
    private Label doctorLastNameLabel;
    private Label doctorPhoneNumberLabel;
    private Label patientFirstNameLabel;
    private Label patientLastNameLabel;
    private Label patientBirthDateLabel;
    private Label patientPhoneNumberLabel;
    private Label patientStreetNameLabel;
    private Label patientHouseNumberLabel;
    private Label patientZipCodeLabel;
    private Label patientCityLabel;
    private Label patientPeselLabel;

    private Pagination patientsTablePagination;
    private Pagination doctorsTablePagination;

    public BaseView() {
        initModel();
        setController();
        createPane();
        addContent();
        setupValidation();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return basePane;
    }

    private Stage currentStage() {
        return (Stage) basePane.getScene().getWindow();
    }

    private void initModel() {
        model = new BaseModel();
    }

    private void setController() {
        controller = new BaseController(model);
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
        UserInfo userInfo = Objects.requireNonNull(sessionStore.getUserInfo());
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
        userInfoPane.getChildren().add(userFirstNameLabel);
    }

    private void createUserLastNameLabel() {
        userLastNameLabel = new Label();
        userLastNameLabel.setText("Nazwisko");
        userLastNameLabel.setLayoutX(650);
        userLastNameLabel.setLayoutY(170);
        userInfoPane.getChildren().add(userLastNameLabel);
    }

    private void createUserUsernameLabel() {
        userUsernameLabel = new Label();
        userUsernameLabel.setText("Nazwa użytkownika");
        userUsernameLabel.setLayoutX(650);
        userUsernameLabel.setLayoutY(270);
        userInfoPane.getChildren().add(userUsernameLabel);
    }

    private void createUserEmailLabel() {
        userEmailLabel = new Label();
        userEmailLabel.setText("Email");
        userEmailLabel.setLayoutX(650);
        userEmailLabel.setLayoutY(370);
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
        userEmailTextField.setStyle("-fx-opacity: 1.0;");
        userInfoPane.getChildren().add(userEmailTextField);
    }

    private void createLogoutButton() {
        logoutButton = new Button();
        logoutButton.setText("Wyloguj się");
        logoutButton.setLayoutX(760);
        logoutButton.setLayoutY(550);
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
        createDoctorFirstNameLabel();
        createDoctorLastNameTextField();
        createDoctorLastNameLabel();
        createDoctorPhoneNumberTextField();
        createDoctorPhoneNumberLabel();
        createChooseDoctorSpecialtyComboBox();
        createChooseDoctorAcademicTitleComboBox();
        createDoctorSpecialtyLabel();
        createDoctorAcademicTitleLabel();
        createAddDoctorButton();
        addDoctorTab.setContent(addDoctorPane);
        doctorsPane.getTabs().add(addDoctorTab);
    }

    private void createDoctorFirstNameLabel(){
        doctorFirstNameLabel = new Label();
        doctorFirstNameLabel.setLayoutX(650.0);
        doctorFirstNameLabel.setLayoutY(40.0);
        doctorFirstNameLabel.setText("Imie");
        addDoctorPane.getChildren().add(doctorFirstNameLabel);
    }

    private void createDoctorLastNameLabel(){
        doctorLastNameLabel = new Label();
        doctorLastNameLabel.setLayoutX(650.0);
        doctorLastNameLabel.setLayoutY(140.0);
        doctorLastNameLabel.setText("Nazwisko");
        addDoctorPane.getChildren().add(doctorLastNameLabel);
    }

    private void createDoctorPhoneNumberLabel(){
        doctorPhoneNumberLabel = new Label();
        doctorPhoneNumberLabel.setLayoutX(650.0);
        doctorPhoneNumberLabel.setLayoutY(240.0);
        doctorPhoneNumberLabel.setText("Numer telefonu");
        addDoctorPane.getChildren().add(doctorPhoneNumberLabel);
    }

    private void createDoctorAcademicTitleLabel() {
        doctorAcademicTitleLabel = new Label();
        doctorAcademicTitleLabel.setLayoutX(650.0);
        doctorAcademicTitleLabel.setLayoutY(440.0);
        doctorAcademicTitleLabel.setText("Tytuł naukowy:");
        addDoctorPane.getChildren().add(doctorAcademicTitleLabel);
    }

    private void createDoctorSpecialtyLabel() {
        doctorSpecialtyLabel = new Label();
        doctorSpecialtyLabel.setLayoutX(650.0);
        doctorSpecialtyLabel.setLayoutY(340.0);
        doctorSpecialtyLabel.setText("Specjalizacja:");
        addDoctorPane.getChildren().add(doctorSpecialtyLabel);
    }

    private void createChooseDoctorAcademicTitleComboBox() {
        chooseDoctorAcademicTitleComboBox = new ComboBox<>();
        chooseDoctorAcademicTitleComboBox.setLayoutX(650.0);
        chooseDoctorAcademicTitleComboBox.setLayoutY(470.0);
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
        doctorPhoneNumberTextField.setLayoutY(270.0);
        doctorPhoneNumberTextField.setPrefHeight(40.0);
        doctorPhoneNumberTextField.setPrefWidth(300.0);
        doctorPhoneNumberTextField.setPromptText("Numer telefonu");
        addDoctorPane.getChildren().add(doctorPhoneNumberTextField);
    }

    private void createDoctorLastNameTextField() {
        doctorLastNameTextField = new TextField();
        doctorLastNameTextField.setLayoutX(650.0);
        doctorLastNameTextField.setLayoutY(170.0);
        doctorLastNameTextField.setPrefHeight(40.0);
        doctorLastNameTextField.setPrefWidth(300.0);
        doctorLastNameTextField.setPromptText("Nazwisko");
        addDoctorPane.getChildren().add(doctorLastNameTextField);
    }

    private void createAddDoctorButton() {
        addDoctorButton = new Button();
        addDoctorButton.setLayoutX(738.0);
        addDoctorButton.setLayoutY(590.0);
        addDoctorButton.setMnemonicParsing(false);
        addDoctorButton.setText("Dodaj lekarza");
        addDoctorButton.setDisable(true);
        addDoctorPane.getChildren().add(addDoctorButton);
    }

    private void createDoctorFirstNameTextField() {
        doctorFirstNameTextField = new TextField();
        doctorFirstNameTextField.setLayoutX(650.0);
        doctorFirstNameTextField.setLayoutY(70.0);
        doctorFirstNameTextField.setPrefHeight(40.0);
        doctorFirstNameTextField.setPrefWidth(300.0);
        doctorFirstNameTextField.setPromptText("Imie");
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
        createPatientFirstNameLabel();
        createPatientLastNameTextField();
        createPatientLastNameLabel();
        createPatientBirthDateDatePicker();
        createPatientBirthDateLabel();
        createChoosePatientSexComboBox();
        createChoosePatientBloodTypeComboBox();
        createPatientStreetNameTextField();
        createPatientStreetNameLabel();
        createPatientHouseNumberTextField();
        createPatientHouseNumberLabel();
        createPatientZipCodeTextField();
        createPatientZipCodeLabel();
        createPatientCityTextField();
        createPatientCityLabel();
        createPatientPeselTextField();
        createPatientPeselLabel();
        createPatientPhoneNumberTextField();
        createPatientPhoneNumberLabel();
        createAddPatientButton();
        createPatientSexLabel();
        createPatientBloodTypeLabel();
        addPatientTab.setContent(addPatientPane);
        patientsPane.getTabs().add(addPatientTab);
    }

    private void createPatientFirstNameLabel(){
        patientFirstNameLabel = new Label();
        patientFirstNameLabel.setLayoutX(350.0);
        patientFirstNameLabel.setLayoutY(20.0);
        patientFirstNameLabel.setText("Imie");
        addPatientPane.getChildren().add(patientFirstNameLabel);
    }

    private void createPatientLastNameLabel(){
        patientLastNameLabel = new Label();
        patientLastNameLabel.setLayoutX(350.0);
        patientLastNameLabel.setLayoutY(100.0);
        patientLastNameLabel.setText("Nazwisko");
        addPatientPane.getChildren().add(patientLastNameLabel);
    }

    private void createPatientBirthDateLabel(){
        patientBirthDateLabel = new Label();
        patientBirthDateLabel.setLayoutX(350.0);
        patientBirthDateLabel.setLayoutY(180.0);
        patientBirthDateLabel.setText("Data urodzenia");
        addPatientPane.getChildren().add(patientBirthDateLabel);
    }

    private void createPatientPhoneNumberLabel(){
        patientPhoneNumberLabel = new Label();
        patientPhoneNumberLabel.setLayoutX(350.0);
        patientPhoneNumberLabel.setLayoutY(420.0);
        patientPhoneNumberLabel.setText("Numer telefonu");
        addPatientPane.getChildren().add(patientPhoneNumberLabel);
    }

    private void createPatientStreetNameLabel(){
        patientStreetNameLabel = new Label();
        patientStreetNameLabel.setLayoutX(950.0);
        patientStreetNameLabel.setLayoutY(20.0);
        patientStreetNameLabel.setText("Ulica");
        addPatientPane.getChildren().add(patientStreetNameLabel);
    }

    private void createPatientHouseNumberLabel(){
        patientHouseNumberLabel = new Label();
        patientHouseNumberLabel.setLayoutX(950.0);
        patientHouseNumberLabel.setLayoutY(100.0);
        patientHouseNumberLabel.setText("Numer domu");
        addPatientPane.getChildren().add(patientHouseNumberLabel);
    }

    private void createPatientZipCodeLabel(){
        patientZipCodeLabel = new Label();
        patientZipCodeLabel.setLayoutX(950.0);
        patientZipCodeLabel.setLayoutY(180.0);
        patientZipCodeLabel.setText("Kod pocztowy");
        addPatientPane.getChildren().add(patientZipCodeLabel);
    }

    private void createPatientCityLabel(){
        patientCityLabel = new Label();
        patientCityLabel.setLayoutX(950.0);
        patientCityLabel.setLayoutY(260.0);
        patientCityLabel.setText("Miejscowość");
        addPatientPane.getChildren().add(patientCityLabel);
    }

    private void createPatientPeselLabel(){
        patientPeselLabel = new Label();
        patientPeselLabel.setLayoutX(950.0);
        patientPeselLabel.setLayoutY(340.0);
        patientPeselLabel.setText("Miejscowość");
        addPatientPane.getChildren().add(patientPeselLabel);
    }

    private void createPatientBloodTypeLabel() {
        patientBloodTypeLabel = new Label();
        patientBloodTypeLabel.setLayoutX(350.0);
        patientBloodTypeLabel.setLayoutY(340.0);
        patientBloodTypeLabel.setText("Grupa krwi:");
        addPatientPane.getChildren().add(patientBloodTypeLabel);
    }

    private void createPatientSexLabel() {
        patientSexLabel = new Label();
        patientSexLabel.setLayoutX(350.0);
        patientSexLabel.setLayoutY(260.0);
        patientSexLabel.setText("Płeć:");
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
        addPatientButton.setDisable(true);
        addPatientPane.getChildren().add(addPatientButton);
    }

    private void createPatientPhoneNumberTextField() {
        patientPhoneNumberTextField = new TextField();
        patientPhoneNumberTextField.setLayoutX(350.0);
        patientPhoneNumberTextField.setLayoutY(450.0);
        patientPhoneNumberTextField.setPrefHeight(40.0);
        patientPhoneNumberTextField.setPrefWidth(300.0);
        patientPhoneNumberTextField.setPromptText("Numer Telefonu");
        addPatientPane.getChildren().add(patientPhoneNumberTextField);
    }

    private void createPatientPeselTextField() {
        patientPeselTextField = new TextField();
        patientPeselTextField.setLayoutX(950.0);
        patientPeselTextField.setLayoutY(370.0);
        patientPeselTextField.setPrefHeight(40.0);
        patientPeselTextField.setPrefWidth(300.0);
        patientPeselTextField.setPromptText("PESEL");
        addPatientPane.getChildren().add(patientPeselTextField);
    }

    private void createPatientCityTextField() {
        patientCityTextField = new TextField();
        patientCityTextField.setLayoutX(950.0);
        patientCityTextField.setLayoutY(290.0);
        patientCityTextField.setPrefHeight(40.0);
        patientCityTextField.setPrefWidth(300.0);
        patientCityTextField.setPromptText("Miejscowość");
        addPatientPane.getChildren().add(patientCityTextField);
    }

    private void createPatientZipCodeTextField() {
        patientZipCodeTextField = new TextField();
        patientZipCodeTextField.setLayoutX(950.0);
        patientZipCodeTextField.setLayoutY(210.0);
        patientZipCodeTextField.setPrefHeight(40.0);
        patientZipCodeTextField.setPrefWidth(300.0);
        patientZipCodeTextField.setPromptText("Kod pocztowy");
        addPatientPane.getChildren().add(patientZipCodeTextField);
    }

    private void createPatientHouseNumberTextField() {
        patientHouseNumberTextField = new TextField();
        patientHouseNumberTextField.setLayoutX(950.0);
        patientHouseNumberTextField.setLayoutY(130.0);
        patientHouseNumberTextField.setPrefHeight(40.0);
        patientHouseNumberTextField.setPrefWidth(300.0);
        patientHouseNumberTextField.setPromptText("Numer domu");
        addPatientPane.getChildren().add(patientHouseNumberTextField);
    }

    private void createPatientStreetNameTextField() {
        patientStreetNameTextField = new TextField();
        patientStreetNameTextField.setLayoutX(950.0);
        patientStreetNameTextField.setLayoutY(50.0);
        patientStreetNameTextField.setPrefHeight(40.0);
        patientStreetNameTextField.setPrefWidth(300.0);
        patientStreetNameTextField.setPromptText("Ulica");
        addPatientPane.getChildren().add(patientStreetNameTextField);
    }

    private void createChoosePatientBloodTypeComboBox() {
        choosePatientBloodTypeComboBox = new ComboBox<>();
        choosePatientBloodTypeComboBox.setLayoutX(350.0);
        choosePatientBloodTypeComboBox.setLayoutY(370.0);
        choosePatientBloodTypeComboBox.setPrefHeight(40.0);
        choosePatientBloodTypeComboBox.setPrefWidth(300.0);
        choosePatientBloodTypeComboBox.setPromptText("Wybierz grupę krwi");
        choosePatientBloodTypeComboBox.getItems().setAll(Arrays
                .stream(BloodType.values())
                .map(BloodType::getName)
                .collect(Collectors.toList()));
        addPatientPane.getChildren().add(choosePatientBloodTypeComboBox);
    }

    private void createChoosePatientSexComboBox() {
        choosePatientSexComboBox = new ComboBox<>();
        choosePatientSexComboBox.setLayoutX(350.0);
        choosePatientSexComboBox.setLayoutY(290.0);
        choosePatientSexComboBox.setPrefHeight(40.0);
        choosePatientSexComboBox.setPrefWidth(300.0);
        choosePatientSexComboBox.setPromptText("Wybierz płeć");
        choosePatientSexComboBox.getItems().setAll(Arrays
                .stream(Sex.values())
                .map(Sex::getName)
                .collect(Collectors.toList()));
        addPatientPane.getChildren().add(choosePatientSexComboBox);
    }

    private void createPatientBirthDateDatePicker() {
        patientBirthDateDatePicker = new DatePicker();
        patientBirthDateDatePicker.setLayoutX(350.0);
        patientBirthDateDatePicker.setLayoutY(210.0);
        patientBirthDateDatePicker.setPrefHeight(40.0);
        patientBirthDateDatePicker.setPrefWidth(300.0);
        patientBirthDateDatePicker.setPromptText("Data urodzenia");
        patientBirthDateDatePicker.setConverter(new DefaultDatePickerConverter());
        addPatientPane.getChildren().add(patientBirthDateDatePicker);
    }

    private void createPatientLastNameTextField() {
        patientLastNameTextField = new TextField();
        patientLastNameTextField.setLayoutX(350.0);
        patientLastNameTextField.setLayoutY(130.0);
        patientLastNameTextField.setPrefHeight(40.0);
        patientLastNameTextField.setPrefWidth(300.0);
        patientLastNameTextField.setPromptText("Nazwisko");
        addPatientPane.getChildren().add(patientLastNameTextField);
    }

    private void createPatientFirstNameTextField() {
        patientFirstNameTextField = new TextField();
        patientFirstNameTextField.setLayoutX(350.0);
        patientFirstNameTextField.setLayoutY(50.0);
        patientFirstNameTextField.setPrefHeight(40.0);
        patientFirstNameTextField.setPrefWidth(300.0);
        patientFirstNameTextField.setPromptText("Imie");
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
        setPatientsTableColumns(patientsTable);
        setupPatientsTablePagination();
        patientsTable.setItems(model.patientsTablePageContent());
        findPatientPane.getChildren().add(patientsTable);
    }

    private void setPatientsTableColumns(TableView<BaseTableRecord> patientsTable) {
        List<TableColumn<BaseTableRecord, String>> columnList = createBaseTableColumns();
        patientsTable.getColumns().addAll(columnList);
    }

    private void setDoctorsTableColumns(TableView<BaseTableRecord> doctorsTable) {
        List<TableColumn<BaseTableRecord, String>> columnList = createBaseTableColumns();
        doctorsTable.getColumns().addAll(columnList);
    }

    private TableColumn<BaseTableRecord, String> createBaseTableColumn() {
        TableColumn<BaseTableRecord, String> column = new TableColumn<>();
        column.setPrefWidth(750.0);
        return column;
    }

    private List<TableColumn<BaseTableRecord, String>> createBaseTableColumns() {
        List<TableColumn<BaseTableRecord, String>> columnList = IntStream
                .range(0, 2)
                .mapToObj(i -> createBaseTableColumn())
                .collect(Collectors.toList());
        columnList.get(0).setText("Imię");
        columnList.get(0).setCellValueFactory(c -> c.getValue().firstName());
        columnList.get(1).setText("Nazwisko");
        columnList.get(1).setCellValueFactory(c -> c.getValue().lastName());
        return columnList;
    }

    private void createDoctorsTable() {
        doctorsTable = new TableView<>();
        doctorsTable.setLayoutX(50.0);
        doctorsTable.setLayoutY(50.0);
        doctorsTable.setPrefHeight(600.0);
        doctorsTable.setPrefWidth(1500.0);
        setDoctorsTableColumns(doctorsTable);
        setupDoctorsTablePagination();
        doctorsTable.setItems(model.doctorsTablePageContent());
        findDoctorPane.getChildren().add(doctorsTable);
    }

    private void setupPatientsTablePagination() {
        patientsTablePagination = new Pagination(1, 0);
        patientsTablePagination.setLayoutX(50);
        patientsTablePagination.setLayoutY(50);
        patientsTablePagination.setPageFactory(this::createPatientsTablePage);
        findPatientPane.getChildren().add(patientsTablePagination);
    }

    private void setupDoctorsTablePagination() {
        doctorsTablePagination = new Pagination(1, 0);
        doctorsTablePagination.setLayoutX(50);
        doctorsTablePagination.setLayoutY(50);
        doctorsTablePagination.setPageFactory(this::createDoctorsTablePage);
        findDoctorPane.getChildren().add(doctorsTablePagination);
    }

    private Node createPatientsTablePage(int pageIndex) {
        controller.updatePatientsTableCurrentPage(pageIndex + 1);
        updatePatientsTablePageData();
        return new BorderPane(patientsTable);
    }

    private Node createDoctorsTablePage(int pageIndex) {
        controller.updateDoctorsTableCurrentPage(pageIndex + 1);
        updateDoctorsTablePageData();
        return new BorderPane(doctorsTable);
    }

    private void setupValidation() {
        setDoctorFirstNameTextFieldValidator();
        setDoctorLastNameTextFieldValidator();
        setDoctorPhoneNumberTextFieldValidator();
        setDoctorAcademicTitleComboBoxValidator();
        setDoctorSpecialtyComboBoxValidator();
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
    }

    private void setPatientBirthDateDatePickerValidator() {
        final String fieldName = "data urodzenia";
        Validator<LocalDate> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<LocalDate> dateValidator = FXValidation.createDateValidator(fieldName, PAST);
        addPatientValidationSupport.registerValidator(
                patientBirthDateDatePicker,
                true,
                Validator.combine(emptyValidator, dateValidator));
    }

    private void setPatientBloodTypeComboBoxValidator() {
        final String fieldName = "grupa krwi";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        addPatientValidationSupport.registerValidator(choosePatientBloodTypeComboBox, true, emptyValidator);
    }

    private void setPatientSexComboBoxValidator() {
        final String fieldName = "płeć";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        addPatientValidationSupport.registerValidator(choosePatientSexComboBox, true, emptyValidator);
    }

    private void setPatientPhoneNumberTextFieldValidator() {
        final String fieldName = "nr telefonu";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9]+$"));
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 7);
        addPatientValidationSupport.registerValidator(
                patientPhoneNumberTextField,
                true,
                Validator.combine(emptyValidator, regexValidator, rangeValidator));
    }

    private void setPatientPeselTextFieldValidator() {
        final String fieldName = "PESEL";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^(0|[1-9][0-9]*)$"));
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 11);
        addPatientValidationSupport.registerValidator(
                patientPeselTextField,
                true,
                Validator.combine(emptyValidator, regexValidator, rangeValidator));
    }

    private void setPatientCityTextFieldValidator() {
        final String fieldName = "miejscowość";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        addPatientValidationSupport.registerValidator(
                patientCityTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setPatientZipCodeTextFieldValidator() {
        final String fieldName = "kod pocztowy";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9 \\-]*$"));
        addPatientValidationSupport.registerValidator(
                patientZipCodeTextField,
                true,
                Validator.combine(emptyValidator, regexValidator));
    }

    private void setPatientHouseNumberTextFieldValidator() {
        final String fieldName = "nr domu";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9a-zA-Z ./]*$"));
        addPatientValidationSupport.registerValidator(
                patientHouseNumberTextField,
                true,
                Validator.combine(emptyValidator, regexValidator));
    }

    private void setPatientStreetNameTextFieldValidator() {
        final String fieldName = "ulica";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        addPatientValidationSupport.registerValidator(
                patientStreetNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setPatientLastNameTextFieldValidator() {
        final String fieldName = "nazwisko";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        addPatientValidationSupport.registerValidator(
                patientLastNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setPatientFirstNameTextFieldValidator() {
        final String fieldName = "imię";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        addPatientValidationSupport.registerValidator(
                patientFirstNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setDoctorFirstNameTextFieldValidator() {
        final String fieldName = "imię";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        addDoctorValidationSupport.registerValidator(
                doctorFirstNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setDoctorLastNameTextFieldValidator() {
        final String fieldName = "nazwisko";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 2);
        addDoctorValidationSupport.registerValidator(
                doctorLastNameTextField,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setDoctorPhoneNumberTextFieldValidator() {
        final String fieldName = "nr telefonu";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^[0-9]+$"));
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 7);
        addDoctorValidationSupport.registerValidator(
                doctorPhoneNumberTextField,
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
                c -> model.doctorSpecialtyComboBoxCheckedItemsNumber());
        Validator<Integer> checkComboBoxBoxValidator = FXValidation.createCheckComboBoxValidator(fieldName);
        addDoctorValidationSupport.registerValidator(chooseDoctorSpecialtyComboBox, true, checkComboBoxBoxValidator);
    }

    private void observeModelAndUpdate() {
        model.patientsTableTotalPages().addListener((obs, oldValue, newValue) ->
                Platform.runLater(() -> patientsTablePagination.setPageCount((int) newValue)));
        model.doctorsTableTotalPages().addListener((obs, oldValue, newValue) ->
                Platform.runLater(() -> doctorsTablePagination.setPageCount((int) newValue)));
    }

    private void delegateEventHandlers() {
        schedulePatientsTableUpdate();
        scheduleDoctorsTableUpdate();
        setTextFieldsLimit();
        addPatientValidationSupport.invalidProperty().addListener((obs, wasInvalid, isNowInvalid) ->
                Platform.runLater(() -> addPatientButton.setDisable(isNowInvalid)));
        addDoctorValidationSupport.invalidProperty().addListener((obs, wasInvalid, isNowInvalid) ->
                Platform.runLater(() -> addDoctorButton.setDisable(isNowInvalid)));
        chooseDoctorSpecialtyComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) c ->
                controller.handleDoctorSpecialtyComboBoxCheckedItemsChanged(c.getList()));
        logoutButton.setOnMouseClicked(e -> logoutUser());
        addDoctorButton.setOnMouseClicked(e -> addDoctor());
        addPatientButton.setOnMouseClicked(e -> addPatient());
        patientsTable.setRowFactory(tv -> setPatientsTableRowFactory());
    }

    private void setTextFieldsLimit() {
        doctorFirstNameTextField.textProperty().addListener(new TextFieldLimiter(30));
        doctorLastNameTextField.textProperty().addListener(new TextFieldLimiter(30));
        doctorPhoneNumberTextField.textProperty().addListener(new TextFieldLimiter(15));
        patientFirstNameTextField.textProperty().addListener(new TextFieldLimiter(30));
        patientLastNameTextField.textProperty().addListener(new TextFieldLimiter(15));
        patientPhoneNumberTextField.textProperty().addListener(new TextFieldLimiter(15));
        patientPeselTextField.textProperty().addListener(new TextFieldLimiter(11));
        patientCityTextField.textProperty().addListener(new TextFieldLimiter(30));
        patientZipCodeTextField.textProperty().addListener(new TextFieldLimiter(10));
        patientHouseNumberTextField.textProperty().addListener(new TextFieldLimiter(10));
        patientStreetNameTextField.textProperty().addListener(new TextFieldLimiter(30));
    }

    private void schedulePatientsTableUpdate() {
        ScheduledService<Void> service = FXTasks.createService(() -> controller.updatePatientsTablePageData());
        serviceStore.addBaseService(service);
        service.setPeriod(Duration.seconds(TABLE_REFRESH_RATE));
        service.setOnFailed(e -> processUpdateBaseTableFailureResult(service.getException().getMessage()));
        service.start();
    }

    private void scheduleDoctorsTableUpdate() {
        ScheduledService<Void> service = FXTasks.createService(() -> controller.updateDoctorsTablePageData());
        serviceStore.addBaseService(service);
        service.setPeriod(Duration.seconds(TABLE_REFRESH_RATE));
        service.setOnFailed(e -> processUpdateBaseTableFailureResult(service.getException().getMessage()));
        service.start();
    }

    private void processUpdateBaseTableFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(WARNING)
                .alertTitle("Nie udało się pobrać niektórych danych")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    private TableRow<BaseTableRecord> setPatientsTableRowFactory() {
        TableRow<BaseTableRecord> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !row.isEmpty()) {
                BaseTableRecord record = patientsTable.getItems().get(row.getIndex());
                openPatientScene(record.getId());
            }
        });
        return row;
    }

    private void addDoctor() {
        Platform.runLater(() -> addDoctorButton.setDisable(true));
        DoctorDetails doctorDetails = createDoctorDetails();
        Task<Void> task = FXTasks.createTask(() -> controller.handleAddDoctorButtonClicked(doctorDetails));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> processAddDoctorSuccessResult());
        task.setOnFailed(e -> processAddDoctorFailureResult(task.getException().getMessage()));
    }

    private void addPatient() {
        Platform.runLater(() -> addPatientButton.setDisable(true));
        PatientDetails patientDetails = createAddPatientDetails();
        Task<Void> task = FXTasks.createTask(() -> controller.handleAddPatientButtonClicked(patientDetails));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> processAddPatientSuccessResult());
        task.setOnFailed(e -> processAddPatientFailureResult(task.getException().getMessage()));
    }

    private void updatePatientsTablePageData() {
        Task<Void> task = FXTasks.createTask(() -> controller.updatePatientsTablePageData());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnFailed(e -> processUpdateBaseTableFailureResult(task.getException().getMessage()));
    }

    private void updateDoctorsTablePageData() {
        Task<Void> task = FXTasks.createTask(() -> controller.updateDoctorsTablePageData());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnFailed(e -> processUpdateBaseTableFailureResult(task.getException().getMessage()));
    }

    private void processAddPatientSuccessResult() {
        Alert alert = FXAlert.builder()
                .alertType(INFORMATION)
                .alertTitle("Operacja ukończona pomyślnie")
                .contentText("Pomyślnie dodano nowego pacjenta")
                .owner(currentStage())
                .build();
        Platform.runLater(() -> {
            addPatientValidationSupport.getRegisteredControls().forEach(FXTasks::clearControlState);
            addPatientButton.setDisable(false);
            alert.showAndWait();
        });
        updatePatientsTablePageData();
    }

    private void processAddDoctorSuccessResult() {
        Alert alert = FXAlert.builder()
                .alertType(INFORMATION)
                .alertTitle("Operacja ukończona pomyślnie")
                .contentText("Pomyślnie dodano nowego lekarza")
                .owner(currentStage())
                .build();
        Platform.runLater(() -> {
            addDoctorValidationSupport.getRegisteredControls().forEach(FXTasks::clearControlState);
            addDoctorButton.setDisable(false);
            alert.showAndWait();
        });
        updateDoctorsTablePageData();
    }

    private void processAddPatientFailureResult(String errorMessage) {
        Alert alert = createAddPersonFailureAlert(errorMessage);
        Platform.runLater(() -> {
            addPatientButton.setDisable(false);
            alert.showAndWait();
        });
    }

    private void processAddDoctorFailureResult(String errorMessage) {
        Alert alert = createAddPersonFailureAlert(errorMessage);
        Platform.runLater(() -> {
            addDoctorButton.setDisable(false);
            alert.showAndWait();
        });
    }

    private Alert createAddPersonFailureAlert(String errorMessage) {
        return FXAlert.builder()
                    .alertType(ERROR)
                    .alertTitle("Operacja ukończona niepomyślnie")
                    .contentText(errorMessage)
                    .owner(currentStage())
                    .build();
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

    private PatientDetails createAddPatientDetails() {
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

    private void logoutUser() {
        Alert alert = FXAlert.builder()
                .alertType(CONFIRMATION)
                .contentText("Czy na pewno chcesz się wylogować?")
                .alertTitle("Wyloguj się")
                .owner(currentStage())
                .build();
        Platform.runLater(() -> alert.showAndWait()
                .filter(OK::equals)
                .ifPresent(e -> {
                    controller.invalidateSession();
                    openAuthenticationScene();
                }));
    }

    private void openAuthenticationScene() {
        Scene scene = new Scene(new AuthenticationView().asParent(), 700, 500);
        Platform.runLater(() -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Stage stage = currentStage();
            stage.close();
            stage.setScene(scene);
            stage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
            stage.setTitle("Healthcare Management - Panel Logowania");
            stage.show();
        });
    }

    private void openPatientScene(Integer patientId) {
        Scene scene = new Scene(new PatientView(patientId).asParent(), 900, 600);
        Platform.runLater(() -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Stage subStage = new Stage();
            subStage.setScene(scene);
            subStage.setX((screenBounds.getWidth() - scene.getWidth()) / 2);
            subStage.setY((screenBounds.getHeight() - scene.getHeight()) / 2);
            subStage.setTitle("Healthcare Management");
            subStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/images/favicon.png")));
            subStage.initOwner(currentStage());
            subStage.initModality(WINDOW_MODAL);
            subStage.setOnHidden(e -> serviceStore.cancelAllPatientServices());
            subStage.show();
        });
    }

}