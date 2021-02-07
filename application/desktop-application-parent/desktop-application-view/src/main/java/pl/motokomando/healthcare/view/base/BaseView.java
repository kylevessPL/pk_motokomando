package pl.motokomando.healthcare.view.base;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.base.utils.DoctorRecord;
import pl.motokomando.healthcare.model.base.utils.PatientRecord;
import pl.motokomando.healthcare.view.base.utils.doctor.MedicalSpecialty;
import pl.motokomando.healthcare.view.base.utils.patient.BloodType;
import pl.motokomando.healthcare.view.base.utils.patient.DocumentType;
import pl.motokomando.healthcare.view.base.utils.patient.Sex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class BaseView {

    private final BaseController controller;

    private TabPane mainPane;

    private Tab tabDoctors;
    private TabPane tabPaneDoctors;
    private Tab tabFindDoctor;
    private AnchorPane anchorPaneFindDoctor;
    private TableView<DoctorRecord> tableViewDoctors;
    private TableColumn<DoctorRecord, String> tableColumnAcademicTittle;
    private TableColumn<DoctorRecord, String> tableColumnDoctorName;
    private TableColumn<DoctorRecord, String> tableColumnDoctorSurname;
    private TableColumn<DoctorRecord, String> tableColumnDoctorPhoneNumber;
    private TableColumn<DoctorRecord, String> tableColumnDoctorSpecialisation;
    private Tab tabAddNewDoctor;
    private AnchorPane anchorPaneAddNewDoctor;
    private TextField textFieldNewDoctorName;
    private Button buttonAddNewDoctor;
    private TextField textFieldNewDoctorSurname;
    private TextField textFieldNewDoctorPhoneNumber;
    private ComboBox<String> comboBoxDoctorSpecialisations;
    private ComboBox<String> comboBoxDoctorAcademicTittle;
    private Label labelNewDoctorSpecialisation;
    private Label labelNewDoctorAcademicTittle;
    private Tab tabPatients;
    private TabPane tabPanePatients;
    private Tab tabFindPatient;
    private AnchorPane anchorPaneFindPatient;
    private TableView<PatientRecord> tableViewPatients;
    private TableColumn<PatientRecord, String> tableColumnPatientName;
    private TableColumn<PatientRecord, String> tableColumnPatientSurname;
    private TableColumn<PatientRecord, String> tableColumnPatientPhoneNumber;
    private TableColumn<PatientRecord, String> tableColumnPatientBloodGroup;
    private TableColumn<PatientRecord, String> tableColumnPatientIdNumber;
    private Tab tabAddNewPatient;
    private AnchorPane anchorPaneAddNewPatient;
    private TextField textFieldNewPatientName;
    private TextField textFieldNewPatientSurname;
    private DatePicker datePickerNewPatientBirthday;
    private ComboBox<String> comboBoxNewPatientSex;
    private ComboBox<String> comboBoxNewPatientBloodGroup;
    private TextField textFieldNewPatientStreet;
    private TextField textFieldNewPatientHouseNumber;
    private TextField textFieldNewPatientZipCode;
    private TextField textFieldNewPatientCity;
    private ComboBox<String> comboBoxNewPatientIdType;
    private TextField textFieldNewPatientIdNumber;
    private TextField textFieldNewPatientPhoneNumber;
    private Button buttonAddNewPatient;
    private Label labelNewPatientSex;
    private Label labelNewPatientBloodGroup;
    private Label labelNewPatientIdType;
    private int recordsPerPage = 4;

    public List<DoctorRecord> listDoctors;
    public List<PatientRecord> listPatients;

    public BaseView(BaseController controller) {
        this.controller = controller;
        createPane();
        addContent();

        //dodaje doktorów
        listDoctors = new ArrayList<>(10);
        listPatients = new ArrayList<>(10);
        for (int i = 0; i < 10; i++){
            listDoctors.add(new DoctorRecord(new SimpleStringProperty("Dr"), "Andrzej", "Kowalski", "69691000" + i, "Urolog"));
        }

        addPaginationToDoctorsTable();
        //need to add some patients first !!!
        //addPaginationToPatientsTable();
        tableViewDoctors.setRowFactory(tv -> {
            TableRow<DoctorRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    DoctorRecord rowData = row.getItem();
                    System.out.println("działa");
                }
            });
            return row ;
        });
        tableViewPatients.setRowFactory(tv -> {
            TableRow<PatientRecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    PatientRecord rowData = row.getItem();
                    System.out.println("działa");
                }
            });
            return row ;
        });
    }

    private void addPaginationToDoctorsTable() {
        Pagination pagination = new Pagination((listDoctors.size() / recordsPerPage + 1), 0);
        pagination.setPageFactory(this::createDoctorPage);
        anchorPaneFindDoctor.getChildren().add(pagination);
        pagination.setLayoutX(50);
        pagination.setLayoutY(50);
    }

    private void addPaginationToPatientsTable() {
        Pagination pagination = new Pagination((listPatients.size() / recordsPerPage + 1), 0);
        pagination.setPageFactory(this::createPatientPage);
        anchorPaneFindPatient.getChildren().add(pagination);
        pagination.setLayoutX(50);
        pagination.setLayoutY(50);
    }

    public Parent asParent() {
        return mainPane;
    }

    public Node createDoctorPage(int pageIndex){
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listDoctors.size());
        tableViewDoctors.setItems(FXCollections.observableArrayList(listDoctors.subList(fromIndex, toIndex)));

        return new BorderPane(tableViewDoctors);
    }

    public Node createPatientPage(int pageIndex){
        int fromIndex = pageIndex * recordsPerPage;
        int toIndex = Math.min(fromIndex + recordsPerPage, listDoctors.size());
        tableViewPatients.setItems(FXCollections.observableArrayList(listPatients.subList(fromIndex, toIndex)));

        return new BorderPane(tableViewDoctors);
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
        tabDoctors = new Tab();
        tabPaneDoctors = new TabPane();
        tabFindDoctor = new Tab();
        anchorPaneFindDoctor = new AnchorPane();
        tableViewDoctors = new TableView<>();
        tableColumnAcademicTittle = new TableColumn<>();
        tableColumnDoctorName = new TableColumn<>();
        tableColumnDoctorSurname = new TableColumn<>();
        tableColumnDoctorPhoneNumber = new TableColumn<>();
        tableColumnDoctorSpecialisation = new TableColumn<>();
        tabAddNewDoctor = new Tab();
        anchorPaneAddNewDoctor = new AnchorPane();
        textFieldNewDoctorName = new TextField();
        buttonAddNewDoctor = new Button();
        textFieldNewDoctorSurname = new TextField();
        textFieldNewDoctorPhoneNumber = new TextField();
        comboBoxDoctorSpecialisations = new ComboBox<>();
        comboBoxDoctorAcademicTittle = new ComboBox<>();
        labelNewDoctorSpecialisation = new Label();
        labelNewDoctorAcademicTittle = new Label();
        tabPatients = new Tab();
        tabPanePatients = new TabPane();
        tabFindPatient = new Tab();
        anchorPaneFindPatient = new AnchorPane();
        tableViewPatients = new TableView<>();
        tableColumnPatientName = new TableColumn<>();
        tableColumnPatientSurname = new TableColumn<>();
        tableColumnPatientPhoneNumber = new TableColumn<>();
        tableColumnPatientBloodGroup = new TableColumn<>();
        tableColumnPatientIdNumber = new TableColumn<>();
        tabAddNewPatient = new Tab();
        anchorPaneAddNewPatient = new AnchorPane();
        textFieldNewPatientName = new TextField();
        textFieldNewPatientSurname = new TextField();
        datePickerNewPatientBirthday = new DatePicker();
        comboBoxNewPatientSex = new ComboBox<>();
        comboBoxNewPatientBloodGroup = new ComboBox<>();
        textFieldNewPatientStreet = new TextField();
        textFieldNewPatientHouseNumber = new TextField();
        textFieldNewPatientZipCode = new TextField();
        textFieldNewPatientCity = new TextField();
        comboBoxNewPatientIdType = new ComboBox<>();
        textFieldNewPatientIdNumber = new TextField();
        textFieldNewPatientPhoneNumber = new TextField();
        buttonAddNewPatient = new Button();
        labelNewPatientSex = new Label();
        labelNewPatientBloodGroup = new Label();
        labelNewPatientIdType = new Label();

        tabDoctors.setText("Lekarze");

        tabPaneDoctors.setTabClosingPolicy(UNAVAILABLE);

        tabFindDoctor.setText("Znajdź lekarza");

        anchorPaneFindDoctor.setMinHeight(0.0);
        anchorPaneFindDoctor.setMinWidth(0.0);
        anchorPaneFindDoctor.setPrefHeight(180.0);
        anchorPaneFindDoctor.setPrefWidth(200.0);

        tableViewDoctors.setLayoutX(50.0);
        tableViewDoctors.setLayoutY(50.0);
        tableViewDoctors.setPrefHeight(600.0);
        tableViewDoctors.setPrefWidth(1500.0);

        tableColumnAcademicTittle.setPrefWidth(300.0);
        tableColumnAcademicTittle.setText("Tytuł naukowy");
        tableColumnAcademicTittle.setCellValueFactory(p -> p.getValue().getTableColumnAcademicTittle());

        tableColumnDoctorName.setPrefWidth(300.0);
        tableColumnDoctorName.setText("Imie");

        tableColumnDoctorSurname.setPrefWidth(300.0);
        tableColumnDoctorSurname.setText("Nazwisko");

        tableColumnDoctorPhoneNumber.setPrefWidth(300.0);
        tableColumnDoctorPhoneNumber.setText("Numer Telefonu");

        tableColumnDoctorSpecialisation.setMinWidth(0.0);
        tableColumnDoctorSpecialisation.setPrefWidth(300.0);
        tableColumnDoctorSpecialisation.setText("Specjalizacja");

        tabFindDoctor.setContent(anchorPaneFindDoctor);

        tabAddNewDoctor.setText("Dodaj lekarza");

        anchorPaneAddNewDoctor.setMinHeight(0.0);
        anchorPaneAddNewDoctor.setMinWidth(0.0);
        anchorPaneAddNewDoctor.setPrefHeight(800.0);
        anchorPaneAddNewDoctor.setPrefWidth(1600.0);

        textFieldNewDoctorName.setLayoutX(650.0);
        textFieldNewDoctorName.setLayoutY(50.0);
        textFieldNewDoctorName.setPrefHeight(40.0);
        textFieldNewDoctorName.setPrefWidth(300.0);
        textFieldNewDoctorName.setPromptText("Imie");
        textFieldNewDoctorName.setFont(new Font(16.0));

        buttonAddNewDoctor.setLayoutX(738.0);
        buttonAddNewDoctor.setLayoutY(590.0);
        buttonAddNewDoctor.setMnemonicParsing(false);
        buttonAddNewDoctor.setText("Dodaj lekarza");
        buttonAddNewDoctor.setFont(new Font(16.0));

        textFieldNewDoctorSurname.setLayoutX(650.0);
        textFieldNewDoctorSurname.setLayoutY(150.0);
        textFieldNewDoctorSurname.setPrefHeight(40.0);
        textFieldNewDoctorSurname.setPrefWidth(300.0);
        textFieldNewDoctorSurname.setPromptText("Nazwisko");
        textFieldNewDoctorSurname.setFont(new Font(16.0));

        textFieldNewDoctorPhoneNumber.setLayoutX(650.0);
        textFieldNewDoctorPhoneNumber.setLayoutY(250.0);
        textFieldNewDoctorPhoneNumber.setPrefHeight(40.0);
        textFieldNewDoctorPhoneNumber.setPrefWidth(300.0);
        textFieldNewDoctorPhoneNumber.setPromptText("Numer telefonu");
        textFieldNewDoctorPhoneNumber.setFont(new Font(16.0));

        comboBoxDoctorSpecialisations.setLayoutX(650.0);
        comboBoxDoctorSpecialisations.setLayoutY(370.0);
        comboBoxDoctorSpecialisations.setPrefHeight(40.0);
        comboBoxDoctorSpecialisations.setPrefWidth(300.0);
        comboBoxDoctorSpecialisations.getItems().setAll(Arrays.stream(MedicalSpecialty.values()).map(MedicalSpecialty::getName).collect(Collectors.toList()));

        comboBoxDoctorAcademicTittle.setLayoutX(650.0);
        comboBoxDoctorAcademicTittle.setLayoutY(490.0);
        comboBoxDoctorAcademicTittle.setPrefHeight(40.0);
        comboBoxDoctorAcademicTittle.setPrefWidth(300.0);
        //comboBoxDoctorAcademicTittle.getItems().setAll(Arrays.stream(AcademicTittle.values()).map(AcademicTittle::getName).collect(Collectors.toList()));

        labelNewDoctorSpecialisation.setLayoutX(650.0);
        labelNewDoctorSpecialisation.setLayoutY(340.0);
        labelNewDoctorSpecialisation.setText("Specjalizacja:");
        labelNewDoctorSpecialisation.setFont(new Font(16.0));

        labelNewDoctorAcademicTittle.setLayoutX(650.0);
        labelNewDoctorAcademicTittle.setLayoutY(460.0);
        labelNewDoctorAcademicTittle.setText("Tytuł naukowy:");
        labelNewDoctorAcademicTittle.setFont(new Font(16.0));

        tabAddNewDoctor.setContent(anchorPaneAddNewDoctor);
        tabDoctors.setContent(tabPaneDoctors);

        tabPatients.setText("Pacjenci");

        tabFindPatient.setClosable(false);
        tabFindPatient.setText("Wyszukaj pacjenta");

        anchorPaneFindPatient.setMinHeight(0.0);
        anchorPaneFindPatient.setMinWidth(0.0);
        anchorPaneFindPatient.setPrefHeight(180.0);
        anchorPaneFindPatient.setPrefWidth(200.0);

        tableViewPatients.setLayoutX(50.0);
        tableViewPatients.setLayoutY(50.0);
        tableViewPatients.setPrefHeight(600.0);
        tableViewPatients.setPrefWidth(1500.0);

        tableColumnPatientName.setPrefWidth(300.0);
        tableColumnPatientName.setText("Imie");

        tableColumnPatientSurname.setPrefWidth(300.0);
        tableColumnPatientSurname.setText("Nazwisko");

        tableColumnPatientPhoneNumber.setPrefWidth(300.0);
        tableColumnPatientPhoneNumber.setText("Numer Telefonu");

        tableColumnPatientBloodGroup.setMinWidth(0.0);
        tableColumnPatientBloodGroup.setPrefWidth(300.0);
        tableColumnPatientBloodGroup.setText("Grupa krwi");

        tableColumnPatientIdNumber.setMinWidth(0.0);
        tableColumnPatientIdNumber.setPrefWidth(300.0);
        tableColumnPatientIdNumber.setText("Numer dokumentu");
        tabFindPatient.setContent(anchorPaneFindPatient);

        tabAddNewPatient.setClosable(false);
        tabAddNewPatient.setText("Dodaj pacjenta");

        anchorPaneAddNewPatient.setMinHeight(0.0);
        anchorPaneAddNewPatient.setMinWidth(0.0);
        anchorPaneAddNewPatient.setPrefHeight(180.0);
        anchorPaneAddNewPatient.setPrefWidth(200.0);

        textFieldNewPatientName.setLayoutX(350.0);
        textFieldNewPatientName.setLayoutY(50.0);
        textFieldNewPatientName.setPrefHeight(40.0);
        textFieldNewPatientName.setPrefWidth(300.0);
        textFieldNewPatientName.setPromptText("Imie");
        textFieldNewPatientName.setFont(new Font(16.0));

        textFieldNewPatientSurname.setLayoutX(350.0);
        textFieldNewPatientSurname.setLayoutY(130.0);
        textFieldNewPatientSurname.setPrefHeight(40.0);
        textFieldNewPatientSurname.setPrefWidth(300.0);
        textFieldNewPatientSurname.setPromptText("Nazwisko");
        textFieldNewPatientSurname.setFont(new Font(16.0));

        datePickerNewPatientBirthday.setLayoutX(350.0);
        datePickerNewPatientBirthday.setLayoutY(210.0);
        datePickerNewPatientBirthday.setPrefHeight(40.0);
        datePickerNewPatientBirthday.setPrefWidth(300.0);
        datePickerNewPatientBirthday.setPromptText("Data urodzenia");

        comboBoxNewPatientSex.setLayoutX(350.0);
        comboBoxNewPatientSex.setLayoutY(290.0);
        comboBoxNewPatientSex.setPrefHeight(40.0);
        comboBoxNewPatientSex.setPrefWidth(300.0);
        comboBoxNewPatientSex.getItems().setAll(Arrays.stream(Sex.values()).map(Sex::getName).collect(Collectors.toList()));

        comboBoxNewPatientBloodGroup.setLayoutX(350.0);
        comboBoxNewPatientBloodGroup.setLayoutY(370.0);
        comboBoxNewPatientBloodGroup.setPrefHeight(40.0);
        comboBoxNewPatientBloodGroup.setPrefWidth(300.0);
        comboBoxNewPatientBloodGroup.getItems().setAll(Arrays.stream(BloodType.values()).map(BloodType::getName).collect(Collectors.toList()));

        textFieldNewPatientStreet.setLayoutX(950.0);
        textFieldNewPatientStreet.setLayoutY(50.0);
        textFieldNewPatientStreet.setPrefHeight(40.0);
        textFieldNewPatientStreet.setPrefWidth(300.0);
        textFieldNewPatientStreet.setPromptText("Ulica");
        textFieldNewPatientStreet.setFont(new Font(16.0));

        textFieldNewPatientHouseNumber.setLayoutX(950.0);
        textFieldNewPatientHouseNumber.setLayoutY(130.0);
        textFieldNewPatientHouseNumber.setPrefHeight(40.0);
        textFieldNewPatientHouseNumber.setPrefWidth(300.0);
        textFieldNewPatientHouseNumber.setPromptText("Numer domu");
        textFieldNewPatientHouseNumber.setFont(new Font(16.0));

        textFieldNewPatientZipCode.setLayoutX(950.0);
        textFieldNewPatientZipCode.setLayoutY(210.0);
        textFieldNewPatientZipCode.setPrefHeight(40.0);
        textFieldNewPatientZipCode.setPrefWidth(300.0);
        textFieldNewPatientZipCode.setPromptText("Kod pocztowy");
        textFieldNewPatientZipCode.setFont(new Font(16.0));

        textFieldNewPatientCity.setLayoutX(950.0);
        textFieldNewPatientCity.setLayoutY(290.0);
        textFieldNewPatientCity.setPrefHeight(40.0);
        textFieldNewPatientCity.setPrefWidth(300.0);
        textFieldNewPatientCity.setPromptText("Miejscowość");
        textFieldNewPatientCity.setFont(new Font(16.0));

        comboBoxNewPatientIdType.setLayoutX(950.0);
        comboBoxNewPatientIdType.setLayoutY(370.0);
        comboBoxNewPatientIdType.setPrefHeight(40.0);
        comboBoxNewPatientIdType.setPrefWidth(300.0);
        comboBoxNewPatientIdType.getItems().setAll(Arrays.stream(DocumentType.values()).map(DocumentType::getName).collect(Collectors.toList()));

        textFieldNewPatientIdNumber.setLayoutX(950.0);
        textFieldNewPatientIdNumber.setLayoutY(450.0);
        textFieldNewPatientIdNumber.setPrefHeight(40.0);
        textFieldNewPatientIdNumber.setPrefWidth(300.0);
        textFieldNewPatientIdNumber.setPromptText("Numer dokumentu");
        textFieldNewPatientIdNumber.setFont(new Font(16.0));

        textFieldNewPatientPhoneNumber.setLayoutX(350.0);
        textFieldNewPatientPhoneNumber.setLayoutY(450.0);
        textFieldNewPatientPhoneNumber.setPrefHeight(40.0);
        textFieldNewPatientPhoneNumber.setPrefWidth(300.0);
        textFieldNewPatientPhoneNumber.setPromptText("Numer Telefonu");
        textFieldNewPatientPhoneNumber.setFont(new Font(16.0));

        buttonAddNewPatient.setLayoutX(700.0);
        buttonAddNewPatient.setLayoutY(630.0);
        buttonAddNewPatient.setMnemonicParsing(false);
        buttonAddNewPatient.setPrefHeight(45.0);
        buttonAddNewPatient.setPrefWidth(200.0);
        buttonAddNewPatient.setText("Dodaj pacjenta");
        buttonAddNewPatient.setFont(new Font(16.0));

        labelNewPatientSex.setLayoutX(350.0);
        labelNewPatientSex.setLayoutY(260.0);
        labelNewPatientSex.setText("Płeć:");
        labelNewPatientSex.setFont(new Font(16.0));

        labelNewPatientBloodGroup.setLayoutX(350.0);
        labelNewPatientBloodGroup.setLayoutY(340.0);
        labelNewPatientBloodGroup.setText("Grupa krwi:");
        labelNewPatientBloodGroup.setFont(new Font(16.0));

        labelNewPatientIdType.setLayoutX(950.0);
        labelNewPatientIdType.setLayoutY(340.0);
        labelNewPatientIdType.setText("Dokument:");
        labelNewPatientIdType.setFont(new Font(16.0));
        tabAddNewPatient.setContent(anchorPaneAddNewPatient);
        tabPatients.setContent(tabPanePatients);

        tableViewDoctors.getColumns().add(tableColumnAcademicTittle);
        tableViewDoctors.getColumns().add(tableColumnDoctorName);
        tableViewDoctors.getColumns().add(tableColumnDoctorSurname);
        tableViewDoctors.getColumns().add(tableColumnDoctorPhoneNumber);
        tableViewDoctors.getColumns().add(tableColumnDoctorSpecialisation);
        anchorPaneFindDoctor.getChildren().add(tableViewDoctors);
        tabPaneDoctors.getTabs().add(tabFindDoctor);
        anchorPaneAddNewDoctor.getChildren().add(textFieldNewDoctorName);
        anchorPaneAddNewDoctor.getChildren().add(buttonAddNewDoctor);
        anchorPaneAddNewDoctor.getChildren().add(textFieldNewDoctorSurname);
        anchorPaneAddNewDoctor.getChildren().add(textFieldNewDoctorPhoneNumber);
        anchorPaneAddNewDoctor.getChildren().add(comboBoxDoctorSpecialisations);
        anchorPaneAddNewDoctor.getChildren().add(comboBoxDoctorAcademicTittle);
        anchorPaneAddNewDoctor.getChildren().add(labelNewDoctorSpecialisation);
        anchorPaneAddNewDoctor.getChildren().add(labelNewDoctorAcademicTittle);
        tabPaneDoctors.getTabs().add(tabAddNewDoctor);
        mainPane.getTabs().add(tabDoctors);
        tableViewPatients.getColumns().add(tableColumnPatientName);
        tableViewPatients.getColumns().add(tableColumnPatientSurname);
        tableViewPatients.getColumns().add(tableColumnPatientPhoneNumber);
        tableViewPatients.getColumns().add(tableColumnPatientBloodGroup);
        tableViewPatients.getColumns().add(tableColumnPatientIdNumber);
        anchorPaneFindPatient.getChildren().add(tableViewPatients);
        tabPanePatients.getTabs().add(tabFindPatient);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientName);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientSurname);
        anchorPaneAddNewPatient.getChildren().add(datePickerNewPatientBirthday);
        anchorPaneAddNewPatient.getChildren().add(comboBoxNewPatientSex);
        anchorPaneAddNewPatient.getChildren().add(comboBoxNewPatientBloodGroup);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientStreet);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientHouseNumber);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientZipCode);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientCity);
        anchorPaneAddNewPatient.getChildren().add(comboBoxNewPatientIdType);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientIdNumber);
        anchorPaneAddNewPatient.getChildren().add(textFieldNewPatientPhoneNumber);
        anchorPaneAddNewPatient.getChildren().add(buttonAddNewPatient);
        anchorPaneAddNewPatient.getChildren().add(labelNewPatientSex);
        anchorPaneAddNewPatient.getChildren().add(labelNewPatientBloodGroup);
        anchorPaneAddNewPatient.getChildren().add(labelNewPatientIdType);
        tabPanePatients.getTabs().add(tabAddNewPatient);
        mainPane.getTabs().add(tabPatients);
    }
}