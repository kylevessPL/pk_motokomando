package pl.motokomando.healthcare.view.base;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.model.base.utils.DoctorRecord;
import pl.motokomando.healthcare.model.base.utils.PatientRecord;
import pl.motokomando.healthcare.view.base.utils.doctor.AcademicTitle;
import pl.motokomando.healthcare.view.base.utils.doctor.MedicalSpecialty;
import pl.motokomando.healthcare.view.base.utils.patient.BloodType;
import pl.motokomando.healthcare.view.base.utils.patient.Sex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class BaseView {

    private final BaseModel model;
    private final BaseController controller;

    private TabPane mainPane;

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
    private ComboBox<String> chooseDoctorSpecialtyComboBox;
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

    private int recordsPerPage = 4; //TODO do poprawy

    public List<DoctorRecord> listDoctors;
    public List<PatientRecord> listPatients;

    public BaseView(BaseModel model, BaseController controller) {
        this.model = model;
        this.controller = controller;
        createPane();
        addContent();

        //TODO do poprawy also

        //dodaje doktorów
        listDoctors = new ArrayList<>(10);
        listPatients = new ArrayList<>(10);
        for (int i = 0; i < 10; i++){
            listDoctors.add(new DoctorRecord(new SimpleStringProperty("Dr"), "Andrzej", "Kowalski", "69691000" + i, "Urolog"));
        }

        addPaginationToDoctorsTable();
        //need to add some patients first !!!
        //addPaginationToPatientsTable();
        doctorsTable.setRowFactory(tv -> {
            TableRow<DoctorRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    DoctorRecord rowData = row.getItem();
                    System.out.println("działa");
                }
            });
            return row ;
        });

        patientsTable.setRowFactory(tv -> {
            TableRow<PatientRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    PatientRecord rowData = row.getItem();
                    System.out.println("działa");
                }
            });
            return row ;
        });
    }

    public Parent asParent() {
        return mainPane;
    }

    private void createPane() {
        mainPane = new TabPane();
        mainPane.setMaxHeight(USE_PREF_SIZE);
        mainPane.setMaxWidth(USE_PREF_SIZE);
        mainPane.setMinHeight(USE_PREF_SIZE);
        mainPane.setMinWidth(USE_PREF_SIZE);
        mainPane.setPrefHeight(800.0);
        mainPane.setPrefWidth(1600.0);
        mainPane.setTabClosingPolicy(UNAVAILABLE);
    }

    private void addContent() {
        createPatientsTab();
        createDoctorsTab();
    }

    private void createDoctorsTab() {
        doctorsTab = new Tab();
        doctorsTab.setText("Lekarze");
        doctorsPane = new TabPane();
        doctorsPane.setTabClosingPolicy(UNAVAILABLE);
        createFindDoctorTab();
        createAddDoctorTab();
        doctorsTab.setContent(doctorsPane);
        mainPane.getTabs().add(doctorsTab);
    }

    private void createAddDoctorTab() {
        addDoctorTab = new Tab();
        addDoctorTab.setText("Dodaj lekarza");
        addDoctorPane = new AnchorPane();
        addDoctorPane.setPrefHeight(800.0);
        addDoctorPane.setPrefWidth(1600.0);
        createDoctorFirstNameTextField();
        createAddDoctorButton();
        createDoctorLastNameTextField();
        createDoctorPhoneNumberTextField();
        createChooseDoctorSpecialtyComboBox();
        createChooseDoctorAcademicTitleComboBox();
        createDoctorSpecialtylabel();
        createDoctorAcademicTitleLabel();
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

    private void createDoctorSpecialtylabel() {
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
        chooseDoctorAcademicTitleComboBox.getItems().setAll(Arrays
                .stream(AcademicTitle.values())
                .map(AcademicTitle::getName)
                .collect(Collectors.toList()));
        addDoctorPane.getChildren().add(chooseDoctorAcademicTitleComboBox);
    }

    private void createChooseDoctorSpecialtyComboBox() {
        chooseDoctorSpecialtyComboBox = new ComboBox<>();
        chooseDoctorSpecialtyComboBox.setLayoutX(650.0);
        chooseDoctorSpecialtyComboBox.setLayoutY(370.0);
        chooseDoctorSpecialtyComboBox.setPrefHeight(40.0);
        chooseDoctorSpecialtyComboBox.setPrefWidth(300.0);
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
        mainPane.getTabs().add(patientsTab);
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

    public Node createDoctorPage(int pageIndex){
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listDoctors.size());
        doctorsTable.setItems(FXCollections.observableArrayList(listDoctors.subList(fromIndex, toIndex)));
        return new BorderPane(doctorsTable);
    }

    public Node createPatientPage(int pageIndex){
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listDoctors.size());
        patientsTable.setItems(FXCollections.observableArrayList(listPatients.subList(fromIndex, toIndex)));
        return new BorderPane(doctorsTable);
    }


}