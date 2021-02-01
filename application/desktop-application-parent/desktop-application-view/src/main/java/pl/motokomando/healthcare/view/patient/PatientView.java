package pl.motokomando.healthcare.view.patient;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import pl.motokomando.healthcare.controller.patient.PatientController;
import pl.motokomando.healthcare.model.patient.utils.AppointmentRecord;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class PatientView {

    private final PatientController controller;

    private TabPane patientPane;

    private Tab tab;
    private AnchorPane anchorPane;
    private TextField textField;
    private TextField textField0;
    private TextField textField1;
    private TextField textField2;
    private TextField textField3;
    private TextField textField4;
    private TextField textField5;
    private ChoiceBox<String> choiceBox;
    private Label label;
    private ChoiceBox<String> choiceBox0;
    private ChoiceBox<String> choiceBox1;
    private Label label0;
    private Label label1;
    private DatePicker datePicker;
    private TextField textField6;
    private Button button;
    private Tab tab0;
    private AnchorPane anchorPane0;
    private TableView<AppointmentRecord> tableView;
    private TableColumn<AppointmentRecord, String> tableColumn;
    private TableColumn<AppointmentRecord, String> tableColumn0;
    private TableColumn<AppointmentRecord, String> tableColumnDoctor;
    private Tab tab1;
    private AnchorPane anchorPane1;
    private DatePicker datePicker0;
    private ComboBox<String> comboBox2;
    private ComboBox<String> comboBox3;
    private Button button0;
    private Button buttonUnlockChanging;
    private Label label2;
    private Label label3;

    public PatientView(PatientController controller) {
        this.controller = controller;
        createPane();
        addContent();
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
        tab = new Tab();
        anchorPane = new AnchorPane();
        textField = new TextField();
        textField0 = new TextField();
        textField1 = new TextField();
        textField2 = new TextField();
        textField3 = new TextField();
        textField4 = new TextField();
        textField5 = new TextField();
        choiceBox = new ChoiceBox<>();
        label = new Label();
        choiceBox0 = new ChoiceBox<>();
        choiceBox1 = new ChoiceBox<>();
        label0 = new Label();
        label1 = new Label();
        datePicker = new DatePicker();
        textField6 = new TextField();
        button = new Button();
        tab0 = new Tab();
        anchorPane0 = new AnchorPane();
        tableView = new TableView<>();
        tableColumn = new TableColumn<>();
        tableColumn0 = new TableColumn<>();
        tableColumnDoctor = new TableColumn<>();
        tab1 = new Tab();
        anchorPane1 = new AnchorPane();
        datePicker0 = new DatePicker();
        comboBox2 = new ComboBox<>();
        comboBox3 = new ComboBox<>();
        button0 = new Button();
        buttonUnlockChanging = new Button();
        label2 = new Label();
        label3 = new Label();

        tab.setText("Pacjent");

        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.setPrefWidth(200.0);

        textField.setLayoutX(50.0);
        textField.setLayoutY(60.0);
        textField.setPrefHeight(30.0);
        textField.setPrefWidth(200.0);
        textField.setPromptText("Imie");
        textField.setFont(new Font(14.0));
        textField.setDisable(true);

        textField0.setLayoutX(650.0);
        textField0.setLayoutY(60.0);
        textField0.setPrefHeight(30.0);
        textField0.setPrefWidth(200.0);
        textField0.setPromptText("Numer telefonu");
        textField0.setFont(new Font(14.0));
        textField0.setDisable(true);

        textField1.setLayoutX(650.0);
        textField1.setLayoutY(220.0);
        textField1.setPrefHeight(30.0);
        textField1.setPrefWidth(200.0);
        textField1.setPromptText("Numer dokumentu");
        textField1.setFont(new Font(14.0));
        textField1.setDisable(true);

        textField2.setLayoutX(50.0);
        textField2.setLayoutY(140.0);
        textField2.setPrefHeight(30.0);
        textField2.setPrefWidth(200.0);
        textField2.setPromptText("Nazwisko");
        textField2.setFont(new Font(14.0));
        textField2.setDisable(true);

        textField3.setLayoutX(350.0);
        textField3.setLayoutY(300.0);
        textField3.setPrefHeight(30.0);
        textField3.setPrefWidth(200.0);
        textField3.setPromptText("Kod pocztowy");
        textField3.setFont(new Font(14.0));
        textField3.setDisable(true);

        textField4.setLayoutX(350.0);
        textField4.setLayoutY(60.0);
        textField4.setPrefHeight(30.0);
        textField4.setPrefWidth(200.0);
        textField4.setPromptText("Ulica");
        textField4.setFont(new Font(14.0));
        textField4.setDisable(true);

        textField5.setLayoutX(350.0);
        textField5.setLayoutY(220.0);
        textField5.setPrefHeight(30.0);
        textField5.setPrefWidth(200.0);
        textField5.setPromptText("Miejscowość");
        textField5.setFont(new Font(14.0));
        textField5.setDisable(true);

        choiceBox.setLayoutX(50.0);
        choiceBox.setLayoutY(220.0);
        choiceBox.setPrefHeight(30.0);
        choiceBox.setPrefWidth(200.0);
        choiceBox.setDisable(true);

        label.setLayoutX(50.0);
        label.setLayoutY(200.0);
        label.setText("Płeć");
        label.setFont(new Font(14.0));

        choiceBox0.setLayoutX(650.0);
        choiceBox0.setLayoutY(140.0);
        choiceBox0.setPrefHeight(30.0);
        choiceBox0.setPrefWidth(200.0);
        choiceBox0.setDisable(true);

        choiceBox1.setLayoutX(650.0);
        choiceBox1.setLayoutY(300.0);
        choiceBox1.setPrefHeight(30.0);
        choiceBox1.setPrefWidth(200.0);
        choiceBox1.setDisable(true);

        label0.setLayoutX(650.0);
        label0.setLayoutY(280.0);
        label0.setText("Grupa krwi");
        label0.setFont(new Font(14.0));

        label1.setLayoutX(650.0);
        label1.setLayoutY(120.0);
        label1.setText("Dokument");
        label1.setFont(new Font(14.0));

        datePicker.setLayoutX(50.0);
        datePicker.setLayoutY(300.0);
        datePicker.setPrefHeight(30.0);
        datePicker.setPrefWidth(200.0);
        datePicker.setPromptText("Data urodzenia");
        datePicker.setDisable(true);

        textField6.setLayoutX(350.0);
        textField6.setLayoutY(140.0);
        textField6.setPrefHeight(30.0);
        textField6.setPrefWidth(200.0);
        textField6.setPromptText("Numer domu");
        textField6.setFont(new Font(14.0));
        textField6.setDisable(true);

        tab0.setText("Wizyty");

        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(180.0);
        anchorPane0.setPrefWidth(200.0);

        tableView.setLayoutX(43.0);
        tableView.setLayoutY(36.0);
        tableView.setPrefHeight(500.0);
        tableView.setPrefWidth(800.0);

        tableColumn.setPrefWidth(266.0);
        tableColumn.setText("Data");

        tableColumn0.setPrefWidth(266.0);
        tableColumn0.setText("Status");

        tableColumnDoctor.setPrefWidth(268.0);
        tableColumnDoctor.setText("Lekarz");

        tab0.setContent(anchorPane0);

        tab1.setText("Zarezerwuj wizytę");

        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(180.0);
        anchorPane1.setPrefWidth(200.0);

        datePicker0.setLayoutX(300.0);
        datePicker0.setLayoutY(170.0);
        datePicker0.setPrefHeight(40.0);
        datePicker0.setPrefWidth(300.0);
        datePicker0.setPromptText("Wybierz datę");

        comboBox2.setLayoutX(300.0);
        comboBox2.setLayoutY(80.0);
        comboBox2.setPrefHeight(40.0);
        comboBox2.setPrefWidth(300.0);

        comboBox3.setLayoutX(300.0);
        comboBox3.setLayoutY(280.0);
        comboBox3.setPrefHeight(40.0);
        comboBox3.setPrefWidth(300.0);
        setWorkingHours(comboBox3);

        button0.setLayoutX(401.0);
        button0.setLayoutY(400.0);
        button0.setMnemonicParsing(false);
        button0.setText("Zarezerwuj");
        button0.setFont(new Font(16.0));

        button.setLayoutX(360.0);
        button.setLayoutY(436.0);
        button.setMnemonicParsing(false);
        button.setText("Zaktualizuj");
        button.setFont(new Font(14.0));
        tab.setContent(anchorPane);

        buttonUnlockChanging.setLayoutX(480.0);
        buttonUnlockChanging.setLayoutY(436.0);
        buttonUnlockChanging.setMnemonicParsing(false);
        buttonUnlockChanging.setText("Edytuj");
        buttonUnlockChanging.setFont(new Font(14.0));

        label2.setLayoutX(300.0);
        label2.setLayoutY(50.0);
        label2.setText("Lekarz");
        label2.setFont(new Font(16.0));

        label3.setLayoutX(300.0);
        label3.setLayoutY(250.0);
        label3.setText("Godzina");
        label3.setFont(new Font(16.0));
        tab1.setContent(anchorPane1);

        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(textField0);
        anchorPane.getChildren().add(textField1);
        anchorPane.getChildren().add(textField2);
        anchorPane.getChildren().add(textField3);
        anchorPane.getChildren().add(textField4);
        anchorPane.getChildren().add(textField5);
        anchorPane.getChildren().add(choiceBox);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(choiceBox0);
        anchorPane.getChildren().add(choiceBox1);
        anchorPane.getChildren().add(label0);
        anchorPane.getChildren().add(label1);
        anchorPane.getChildren().add(datePicker);
        anchorPane.getChildren().add(textField6);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(buttonUnlockChanging);
        patientPane.getTabs().add(tab);
        tableView.getColumns().add(tableColumn);
        tableView.getColumns().add(tableColumn0);
        tableView.getColumns().add(tableColumnDoctor);
        anchorPane0.getChildren().add(tableView);
        patientPane.getTabs().add(tab0);
        anchorPane1.getChildren().add(datePicker0);
        anchorPane1.getChildren().add(comboBox2);
        anchorPane1.getChildren().add(comboBox3);
        anchorPane1.getChildren().add(button0);
        anchorPane1.getChildren().add(label2);
        anchorPane1.getChildren().add(label3);
        patientPane.getTabs().add(tab1);
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
