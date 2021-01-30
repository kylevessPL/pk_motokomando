package pl.motokomando.healthcare.view.base;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.base.utils.DoctorRecord;
import pl.motokomando.healthcare.model.base.utils.PatientRecord;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class BaseView {

    private final BaseController controller;

    private TabPane mainPane;

    private Tab tab_doctors;
    private TabPane tabPane_doctors;
    private Tab tab_find_doctor;
    private AnchorPane anchorPane_find_doctor;
    private TableView<DoctorRecord> tableView_doctors;
    private TableColumn<DoctorRecord, String> tableColumn_doctor_name;
    private TableColumn<DoctorRecord, String> tableColumn_doctor_surname;
    private TableColumn<DoctorRecord, String> tableColumn_doctor_phone_number;
    private TableColumn<DoctorRecord, String> tableColumn_doctor_specialisation;
    private Tab tab_add_new_doctor;
    private AnchorPane anchorPane_add_new_doctor;
    private TextField textField_new_doctor_name;
    private Button button_add_new_doctor;
    private TextField textField_new_doctor_surname;
    private TextField textField_new_doctor_phone_number;
    private ChoiceBox<String> choiceBox_doctor_specialisations;
    private Label label_new_doctor_specialisation;
    private Tab tab_patients;
    private TabPane tabPane_patients;
    private Tab tab_find_patient;
    private AnchorPane anchorPane_find_patient;
    private TableView<PatientRecord> tableView_patients;
    private TableColumn<PatientRecord, String> tableColumn_patient_name;
    private TableColumn<PatientRecord, String> tableColumn_patient_surname;
    private TableColumn<PatientRecord, String> tableColumn_patient_phone_number;
    private TableColumn<PatientRecord, String> tableColumn_patient_blood_group;
    private TableColumn<PatientRecord, String> tableColumn_patient_id_number;
    private Tab tab_add_new_patient;
    private AnchorPane anchorPane_add_new_patient;
    private TextField textField_new_patient_name;
    private TextField textField_new_patient_surname;
    private DatePicker datePicker_new_patient_birthday;
    private ChoiceBox<String> choiceBox_new_patient_sex;
    private ChoiceBox<String> choiceBox_new_patient_blood_group;
    private TextField textField_new_patient_street;
    private TextField textField_new_patient_house_number;
    private TextField textField_new_patient_zip_code;
    private TextField textField_new_patient_city;
    private ChoiceBox<String> choiceBox_new_patient_id_type;
    private TextField textField_new_patient_id_number;
    private TextField textField_new_patient_phone_number;
    private Button button_add_new_patient;
    private Label label_new_patient_sex;
    private Label label_new_patient_blood_group;
    private Label label_new_patient_id_type;

    public BaseView(BaseController controller) {
        this.controller = controller;
        createPane();
        addContent();
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
        tab_doctors = new Tab();
        tabPane_doctors = new TabPane();
        tab_find_doctor = new Tab();
        anchorPane_find_doctor = new AnchorPane();
        tableView_doctors = new TableView<>();
        tableColumn_doctor_name = new TableColumn<>();
        tableColumn_doctor_surname = new TableColumn<>();
        tableColumn_doctor_phone_number = new TableColumn<>();
        tableColumn_doctor_specialisation = new TableColumn<>();
        tab_add_new_doctor = new Tab();
        anchorPane_add_new_doctor = new AnchorPane();
        textField_new_doctor_name = new TextField();
        button_add_new_doctor = new Button();
        textField_new_doctor_surname = new TextField();
        textField_new_doctor_phone_number = new TextField();
        choiceBox_doctor_specialisations = new ChoiceBox<>();
        label_new_doctor_specialisation = new Label();
        tab_patients = new Tab();
        tabPane_patients = new TabPane();
        tab_find_patient = new Tab();
        anchorPane_find_patient = new AnchorPane();
        tableView_patients = new TableView<>();
        tableColumn_patient_name = new TableColumn<>();
        tableColumn_patient_surname = new TableColumn<>();
        tableColumn_patient_phone_number = new TableColumn<>();
        tableColumn_patient_blood_group = new TableColumn<>();
        tableColumn_patient_id_number = new TableColumn<>();
        tab_add_new_patient = new Tab();
        anchorPane_add_new_patient = new AnchorPane();
        textField_new_patient_name = new TextField();
        textField_new_patient_surname = new TextField();
        datePicker_new_patient_birthday = new DatePicker();
        choiceBox_new_patient_sex = new ChoiceBox<>();
        choiceBox_new_patient_blood_group = new ChoiceBox<>();
        textField_new_patient_street = new TextField();
        textField_new_patient_house_number = new TextField();
        textField_new_patient_zip_code = new TextField();
        textField_new_patient_city = new TextField();
        choiceBox_new_patient_id_type = new ChoiceBox<>();
        textField_new_patient_id_number = new TextField();
        textField_new_patient_phone_number = new TextField();
        button_add_new_patient = new Button();
        label_new_patient_sex = new Label();
        label_new_patient_blood_group = new Label();
        label_new_patient_id_type = new Label();

        tab_doctors.setText("Lekarze");

        tabPane_doctors.setTabClosingPolicy(UNAVAILABLE);

        tab_find_doctor.setText("Znajdź doktora");

        anchorPane_find_doctor.setMinHeight(0.0);
        anchorPane_find_doctor.setMinWidth(0.0);
        anchorPane_find_doctor.setPrefHeight(180.0);
        anchorPane_find_doctor.setPrefWidth(200.0);

        tableView_doctors.setLayoutX(50.0);
        tableView_doctors.setLayoutY(50.0);
        tableView_doctors.setPrefHeight(600.0);
        tableView_doctors.setPrefWidth(1500.0);

        tableColumn_doctor_name.setPrefWidth(375.0);
        tableColumn_doctor_name.setText("Imie");

        tableColumn_doctor_surname.setPrefWidth(375.0);
        tableColumn_doctor_surname.setText("Nazwisko");

        tableColumn_doctor_phone_number.setPrefWidth(375.0);
        tableColumn_doctor_phone_number.setText("Numer Telefonu");

        tableColumn_doctor_specialisation.setMinWidth(0.0);
        tableColumn_doctor_specialisation.setPrefWidth(375.0);
        tableColumn_doctor_specialisation.setText("Specjalizacja");

        tab_find_doctor.setContent(anchorPane_find_doctor);

        tab_add_new_doctor.setText("Dodaj Doktora");

        anchorPane_add_new_doctor.setMinHeight(0.0);
        anchorPane_add_new_doctor.setMinWidth(0.0);
        anchorPane_add_new_doctor.setPrefHeight(800.0);
        anchorPane_add_new_doctor.setPrefWidth(1600.0);

        textField_new_doctor_name.setLayoutX(650.0);
        textField_new_doctor_name.setLayoutY(50.0);
        textField_new_doctor_name.setPrefHeight(40.0);
        textField_new_doctor_name.setPrefWidth(300.0);
        textField_new_doctor_name.setPromptText("Imie");
        textField_new_doctor_name.setFont(new Font(16.0));

        button_add_new_doctor.setLayoutX(738.0);
        button_add_new_doctor.setLayoutY(472.0);
        button_add_new_doctor.setMnemonicParsing(false);
        button_add_new_doctor.setText("Dodaj doktora");
        button_add_new_doctor.setFont(new Font(16.0));

        textField_new_doctor_surname.setLayoutX(650.0);
        textField_new_doctor_surname.setLayoutY(150.0);
        textField_new_doctor_surname.setPrefHeight(40.0);
        textField_new_doctor_surname.setPrefWidth(300.0);
        textField_new_doctor_surname.setPromptText("Nazwisko");
        textField_new_doctor_surname.setFont(new Font(16.0));

        textField_new_doctor_phone_number.setLayoutX(650.0);
        textField_new_doctor_phone_number.setLayoutY(250.0);
        textField_new_doctor_phone_number.setPrefHeight(40.0);
        textField_new_doctor_phone_number.setPrefWidth(300.0);
        textField_new_doctor_phone_number.setPromptText("Numer telefonu");
        textField_new_doctor_phone_number.setFont(new Font(16.0));

        choiceBox_doctor_specialisations.setLayoutX(650.0);
        choiceBox_doctor_specialisations.setLayoutY(371.0);
        choiceBox_doctor_specialisations.setPrefHeight(40.0);
        choiceBox_doctor_specialisations.setPrefWidth(300.0);

        label_new_doctor_specialisation.setLayoutX(650.0);
        label_new_doctor_specialisation.setLayoutY(340.0);
        label_new_doctor_specialisation.setText("Specjalizacja:");
        label_new_doctor_specialisation.setFont(new Font(16.0));
        tab_add_new_doctor.setContent(anchorPane_add_new_doctor);
        tab_doctors.setContent(tabPane_doctors);

        tab_patients.setText("Pacjenci");

        tab_find_patient.setClosable(false);
        tab_find_patient.setText("Wyszukaj pacjenta");

        anchorPane_find_patient.setMinHeight(0.0);
        anchorPane_find_patient.setMinWidth(0.0);
        anchorPane_find_patient.setPrefHeight(180.0);
        anchorPane_find_patient.setPrefWidth(200.0);

        tableView_patients.setLayoutX(50.0);
        tableView_patients.setLayoutY(50.0);
        tableView_patients.setPrefHeight(600.0);
        tableView_patients.setPrefWidth(1500.0);

        tableColumn_patient_name.setPrefWidth(300.0);
        tableColumn_patient_name.setText("Imie");

        tableColumn_patient_surname.setPrefWidth(300.0);
        tableColumn_patient_surname.setText("Nazwisko");

        tableColumn_patient_phone_number.setPrefWidth(300.0);
        tableColumn_patient_phone_number.setText("Numer Telefonu");

        tableColumn_patient_blood_group.setMinWidth(0.0);
        tableColumn_patient_blood_group.setPrefWidth(300.0);
        tableColumn_patient_blood_group.setText("Grupa krwi");

        tableColumn_patient_id_number.setMinWidth(0.0);
        tableColumn_patient_id_number.setPrefWidth(300.0);
        tableColumn_patient_id_number.setText("Numer dokumentu");
        tab_find_patient.setContent(anchorPane_find_patient);

        tab_add_new_patient.setClosable(false);
        tab_add_new_patient.setText("Dodaj pacjenta");

        anchorPane_add_new_patient.setMinHeight(0.0);
        anchorPane_add_new_patient.setMinWidth(0.0);
        anchorPane_add_new_patient.setPrefHeight(180.0);
        anchorPane_add_new_patient.setPrefWidth(200.0);

        textField_new_patient_name.setLayoutX(350.0);
        textField_new_patient_name.setLayoutY(50.0);
        textField_new_patient_name.setPrefHeight(40.0);
        textField_new_patient_name.setPrefWidth(300.0);
        textField_new_patient_name.setPromptText("Imie");
        textField_new_patient_name.setFont(new Font(16.0));

        textField_new_patient_surname.setLayoutX(350.0);
        textField_new_patient_surname.setLayoutY(130.0);
        textField_new_patient_surname.setPrefHeight(40.0);
        textField_new_patient_surname.setPrefWidth(300.0);
        textField_new_patient_surname.setPromptText("Nazwisko");
        textField_new_patient_surname.setFont(new Font(16.0));

        datePicker_new_patient_birthday.setLayoutX(350.0);
        datePicker_new_patient_birthday.setLayoutY(210.0);
        datePicker_new_patient_birthday.setPrefHeight(40.0);
        datePicker_new_patient_birthday.setPrefWidth(300.0);
        datePicker_new_patient_birthday.setPromptText("Data urodzenia");

        choiceBox_new_patient_sex.setLayoutX(350.0);
        choiceBox_new_patient_sex.setLayoutY(290.0);
        choiceBox_new_patient_sex.setPrefHeight(40.0);
        choiceBox_new_patient_sex.setPrefWidth(300.0);

        choiceBox_new_patient_blood_group.setLayoutX(350.0);
        choiceBox_new_patient_blood_group.setLayoutY(370.0);
        choiceBox_new_patient_blood_group.setPrefHeight(40.0);
        choiceBox_new_patient_blood_group.setPrefWidth(300.0);

        textField_new_patient_street.setLayoutX(950.0);
        textField_new_patient_street.setLayoutY(50.0);
        textField_new_patient_street.setPrefHeight(40.0);
        textField_new_patient_street.setPrefWidth(300.0);
        textField_new_patient_street.setPromptText("Ulica");
        textField_new_patient_street.setFont(new Font(16.0));

        textField_new_patient_house_number.setLayoutX(950.0);
        textField_new_patient_house_number.setLayoutY(130.0);
        textField_new_patient_house_number.setPrefHeight(40.0);
        textField_new_patient_house_number.setPrefWidth(300.0);
        textField_new_patient_house_number.setPromptText("Numer domu");
        textField_new_patient_house_number.setFont(new Font(16.0));

        textField_new_patient_zip_code.setLayoutX(950.0);
        textField_new_patient_zip_code.setLayoutY(210.0);
        textField_new_patient_zip_code.setPrefHeight(40.0);
        textField_new_patient_zip_code.setPrefWidth(300.0);
        textField_new_patient_zip_code.setPromptText("Kod pocztowy");
        textField_new_patient_zip_code.setFont(new Font(16.0));

        textField_new_patient_city.setLayoutX(950.0);
        textField_new_patient_city.setLayoutY(290.0);
        textField_new_patient_city.setPrefHeight(40.0);
        textField_new_patient_city.setPrefWidth(300.0);
        textField_new_patient_city.setPromptText("Miejscowo��");
        textField_new_patient_city.setFont(new Font(16.0));

        choiceBox_new_patient_id_type.setLayoutX(950.0);
        choiceBox_new_patient_id_type.setLayoutY(370.0);
        choiceBox_new_patient_id_type.setPrefHeight(40.0);
        choiceBox_new_patient_id_type.setPrefWidth(300.0);

        textField_new_patient_id_number.setLayoutX(950.0);
        textField_new_patient_id_number.setLayoutY(450.0);
        textField_new_patient_id_number.setPrefHeight(40.0);
        textField_new_patient_id_number.setPrefWidth(300.0);
        textField_new_patient_id_number.setPromptText("Numer dokumentu");
        textField_new_patient_id_number.setFont(new Font(16.0));

        textField_new_patient_phone_number.setLayoutX(350.0);
        textField_new_patient_phone_number.setLayoutY(450.0);
        textField_new_patient_phone_number.setPrefHeight(40.0);
        textField_new_patient_phone_number.setPrefWidth(300.0);
        textField_new_patient_phone_number.setPromptText("Numer Telefonu");
        textField_new_patient_phone_number.setFont(new Font(16.0));

        button_add_new_patient.setLayoutX(700.0);
        button_add_new_patient.setLayoutY(630.0);
        button_add_new_patient.setMnemonicParsing(false);
        button_add_new_patient.setPrefHeight(45.0);
        button_add_new_patient.setPrefWidth(200.0);
        button_add_new_patient.setText("Dodaj pacjenta");
        button_add_new_patient.setFont(new Font(16.0));

        label_new_patient_sex.setLayoutX(350.0);
        label_new_patient_sex.setLayoutY(260.0);
        label_new_patient_sex.setText("P�e�:");
        label_new_patient_sex.setFont(new Font(16.0));

        label_new_patient_blood_group.setLayoutX(350.0);
        label_new_patient_blood_group.setLayoutY(340.0);
        label_new_patient_blood_group.setText("Grupa krwi:");
        label_new_patient_blood_group.setFont(new Font(16.0));

        label_new_patient_id_type.setLayoutX(950.0);
        label_new_patient_id_type.setLayoutY(340.0);
        label_new_patient_id_type.setText("Dokument:");
        label_new_patient_id_type.setFont(new Font(16.0));
        tab_add_new_patient.setContent(anchorPane_add_new_patient);
        tab_patients.setContent(tabPane_patients);

        tableView_doctors.getColumns().add(tableColumn_doctor_name);
        tableView_doctors.getColumns().add(tableColumn_doctor_surname);
        tableView_doctors.getColumns().add(tableColumn_doctor_phone_number);
        tableView_doctors.getColumns().add(tableColumn_doctor_specialisation);
        anchorPane_find_doctor.getChildren().add(tableView_doctors);
        tabPane_doctors.getTabs().add(tab_find_doctor);
        anchorPane_add_new_doctor.getChildren().add(textField_new_doctor_name);
        anchorPane_add_new_doctor.getChildren().add(button_add_new_doctor);
        anchorPane_add_new_doctor.getChildren().add(textField_new_doctor_surname);
        anchorPane_add_new_doctor.getChildren().add(textField_new_doctor_phone_number);
        anchorPane_add_new_doctor.getChildren().add(choiceBox_doctor_specialisations);
        anchorPane_add_new_doctor.getChildren().add(label_new_doctor_specialisation);
        tabPane_doctors.getTabs().add(tab_add_new_doctor);
        mainPane.getTabs().add(tab_doctors);
        tableView_patients.getColumns().add(tableColumn_patient_name);
        tableView_patients.getColumns().add(tableColumn_patient_surname);
        tableView_patients.getColumns().add(tableColumn_patient_phone_number);
        tableView_patients.getColumns().add(tableColumn_patient_blood_group);
        tableView_patients.getColumns().add(tableColumn_patient_id_number);
        anchorPane_find_patient.getChildren().add(tableView_patients);
        tabPane_patients.getTabs().add(tab_find_patient);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_name);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_surname);
        anchorPane_add_new_patient.getChildren().add(datePicker_new_patient_birthday);
        anchorPane_add_new_patient.getChildren().add(choiceBox_new_patient_sex);
        anchorPane_add_new_patient.getChildren().add(choiceBox_new_patient_blood_group);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_street);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_house_number);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_zip_code);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_city);
        anchorPane_add_new_patient.getChildren().add(choiceBox_new_patient_id_type);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_id_number);
        anchorPane_add_new_patient.getChildren().add(textField_new_patient_phone_number);
        anchorPane_add_new_patient.getChildren().add(button_add_new_patient);
        anchorPane_add_new_patient.getChildren().add(label_new_patient_sex);
        anchorPane_add_new_patient.getChildren().add(label_new_patient_blood_group);
        anchorPane_add_new_patient.getChildren().add(label_new_patient_id_type);
        tabPane_patients.getTabs().add(tab_add_new_patient);
        mainPane.getTabs().add(tab_patients);
    }

}
