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

    private Tab tab;
    private TabPane tabPane;
    private Tab tab0;
    private AnchorPane anchorPane;
    private TableView<DoctorRecord> tableView;
    private TableColumn<DoctorRecord, String> tableColumn;
    private TableColumn<DoctorRecord, String> tableColumn0;
    private TableColumn<DoctorRecord, String> tableColumn1;
    private TableColumn<DoctorRecord, String> tableColumn2;
    private Tab tab1;
    private AnchorPane anchorPane0;
    private TextField textField;
    private Button button;
    private TextField textField0;
    private TextField textField1;
    private ChoiceBox<String> choiceBox;
    private Label label;
    private Tab tab2;
    private TabPane tabPane0;
    private Tab tab3;
    private AnchorPane anchorPane1;
    private TableView<PatientRecord> tableView0;
    private TableColumn<PatientRecord, String> tableColumn3;
    private TableColumn<PatientRecord, String> tableColumn4;
    private TableColumn<PatientRecord, String> tableColumn5;
    private TableColumn<PatientRecord, String> tableColumn6;
    private TableColumn<PatientRecord, String> tableColumn7;
    private Tab tab4;
    private AnchorPane anchorPane2;
    private TextField textField2;
    private TextField textField3;
    private DatePicker datePicker;
    private ChoiceBox<String> choiceBox0;
    private ChoiceBox<String> choiceBox1;
    private TextField textField4;
    private TextField textField5;
    private TextField textField6;
    private TextField textField7;
    private ChoiceBox<String> choiceBox2;
    private TextField textField8;
    private TextField textField9;
    private Button button0;
    private Label label0;
    private Label label1;
    private Label label2;

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
        tab = new Tab();
        tabPane = new TabPane();
        tab0 = new Tab();
        anchorPane = new AnchorPane();
        tableView = new TableView<>();
        tableColumn = new TableColumn<>();
        tableColumn0 = new TableColumn<>();
        tableColumn1 = new TableColumn<>();
        tableColumn2 = new TableColumn<>();
        tab1 = new Tab();
        anchorPane0 = new AnchorPane();
        textField = new TextField();
        button = new Button();
        textField0 = new TextField();
        textField1 = new TextField();
        choiceBox = new ChoiceBox<>();
        label = new Label();
        tab2 = new Tab();
        tabPane0 = new TabPane();
        tab3 = new Tab();
        anchorPane1 = new AnchorPane();
        tableView0 = new TableView<>();
        tableColumn3 = new TableColumn<>();
        tableColumn4 = new TableColumn<>();
        tableColumn5 = new TableColumn<>();
        tableColumn6 = new TableColumn<>();
        tableColumn7 = new TableColumn<>();
        tab4 = new Tab();
        anchorPane2 = new AnchorPane();
        textField2 = new TextField();
        textField3 = new TextField();
        datePicker = new DatePicker();
        choiceBox0 = new ChoiceBox<>();
        choiceBox1 = new ChoiceBox<>();
        textField4 = new TextField();
        textField5 = new TextField();
        textField6 = new TextField();
        textField7 = new TextField();
        choiceBox2 = new ChoiceBox<>();
        textField8 = new TextField();
        textField9 = new TextField();
        button0 = new Button();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();

        tab.setText("Lekarze");

        tabPane.setTabClosingPolicy(UNAVAILABLE);

        tab0.setText("Znajd� doktora");

        anchorPane.setMinHeight(0.0);
        anchorPane.setMinWidth(0.0);
        anchorPane.setPrefHeight(180.0);
        anchorPane.setPrefWidth(200.0);

        tableView.setLayoutX(50.0);
        tableView.setLayoutY(50.0);
        tableView.setPrefHeight(600.0);
        tableView.setPrefWidth(1500.0);

        tableColumn.setPrefWidth(375.0);
        tableColumn.setText("Imie");

        tableColumn0.setPrefWidth(375.0);
        tableColumn0.setText("Nazwisko");

        tableColumn1.setPrefWidth(375.0);
        tableColumn1.setText("Numer Telefonu");

        tableColumn2.setMinWidth(0.0);
        tableColumn2.setPrefWidth(375.0);
        tableColumn2.setText("Specjalizacja");

        tab0.setContent(anchorPane);

        tab1.setText("Dodaj Doktora");

        anchorPane0.setMinHeight(0.0);
        anchorPane0.setMinWidth(0.0);
        anchorPane0.setPrefHeight(800.0);
        anchorPane0.setPrefWidth(1600.0);

        textField.setLayoutX(650.0);
        textField.setLayoutY(50.0);
        textField.setPrefHeight(40.0);
        textField.setPrefWidth(300.0);
        textField.setPromptText("Imie");
        textField.setFont(new Font(16.0));

        button.setLayoutX(738.0);
        button.setLayoutY(472.0);
        button.setMnemonicParsing(false);
        button.setText("Dodaj doktora");
        button.setFont(new Font(16.0));

        textField0.setLayoutX(650.0);
        textField0.setLayoutY(150.0);
        textField0.setPrefHeight(40.0);
        textField0.setPrefWidth(300.0);
        textField0.setPromptText("Nazwisko");
        textField0.setFont(new Font(16.0));

        textField1.setLayoutX(650.0);
        textField1.setLayoutY(250.0);
        textField1.setPrefHeight(40.0);
        textField1.setPrefWidth(300.0);
        textField1.setPromptText("Numer telefonu");
        textField1.setFont(new Font(16.0));

        choiceBox.setLayoutX(650.0);
        choiceBox.setLayoutY(371.0);
        choiceBox.setPrefHeight(40.0);
        choiceBox.setPrefWidth(300.0);

        label.setLayoutX(650.0);
        label.setLayoutY(340.0);
        label.setText("Specjalizacja:");
        label.setFont(new Font(16.0));
        tab1.setContent(anchorPane0);
        tab.setContent(tabPane);

        tab2.setText("Pacjenci");

        tab3.setClosable(false);
        tab3.setText("Wyszukaj pacjenta");

        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(180.0);
        anchorPane1.setPrefWidth(200.0);

        tableView0.setLayoutX(50.0);
        tableView0.setLayoutY(50.0);
        tableView0.setPrefHeight(600.0);
        tableView0.setPrefWidth(1500.0);

        tableColumn3.setPrefWidth(300.0);
        tableColumn3.setText("Imie");

        tableColumn4.setPrefWidth(300.0);
        tableColumn4.setText("Nazwisko");

        tableColumn5.setPrefWidth(300.0);
        tableColumn5.setText("Numer Telefonu");

        tableColumn6.setMinWidth(0.0);
        tableColumn6.setPrefWidth(300.0);
        tableColumn6.setText("Grupa krwi");

        tableColumn7.setMinWidth(0.0);
        tableColumn7.setPrefWidth(300.0);
        tableColumn7.setText("Numer dokumentu");
        tab3.setContent(anchorPane1);

        tab4.setClosable(false);
        tab4.setText("Dodaj pacjenta");

        anchorPane2.setMinHeight(0.0);
        anchorPane2.setMinWidth(0.0);
        anchorPane2.setPrefHeight(180.0);
        anchorPane2.setPrefWidth(200.0);

        textField2.setLayoutX(350.0);
        textField2.setLayoutY(50.0);
        textField2.setPrefHeight(40.0);
        textField2.setPrefWidth(300.0);
        textField2.setPromptText("Imie");
        textField2.setFont(new Font(16.0));

        textField3.setLayoutX(350.0);
        textField3.setLayoutY(130.0);
        textField3.setPrefHeight(40.0);
        textField3.setPrefWidth(300.0);
        textField3.setPromptText("Nazwisko");
        textField3.setFont(new Font(16.0));

        datePicker.setLayoutX(350.0);
        datePicker.setLayoutY(210.0);
        datePicker.setPrefHeight(40.0);
        datePicker.setPrefWidth(300.0);
        datePicker.setPromptText("Data urodzenia");

        choiceBox0.setLayoutX(350.0);
        choiceBox0.setLayoutY(290.0);
        choiceBox0.setPrefHeight(40.0);
        choiceBox0.setPrefWidth(300.0);

        choiceBox1.setLayoutX(350.0);
        choiceBox1.setLayoutY(370.0);
        choiceBox1.setPrefHeight(40.0);
        choiceBox1.setPrefWidth(300.0);

        textField4.setLayoutX(950.0);
        textField4.setLayoutY(50.0);
        textField4.setPrefHeight(40.0);
        textField4.setPrefWidth(300.0);
        textField4.setPromptText("Ulica");
        textField4.setFont(new Font(16.0));

        textField5.setLayoutX(950.0);
        textField5.setLayoutY(130.0);
        textField5.setPrefHeight(40.0);
        textField5.setPrefWidth(300.0);
        textField5.setPromptText("Numer domu");
        textField5.setFont(new Font(16.0));

        textField6.setLayoutX(950.0);
        textField6.setLayoutY(210.0);
        textField6.setPrefHeight(40.0);
        textField6.setPrefWidth(300.0);
        textField6.setPromptText("Kod pocztowy");
        textField6.setFont(new Font(16.0));

        textField7.setLayoutX(950.0);
        textField7.setLayoutY(290.0);
        textField7.setPrefHeight(40.0);
        textField7.setPrefWidth(300.0);
        textField7.setPromptText("Miejscowo��");
        textField7.setFont(new Font(16.0));

        choiceBox2.setLayoutX(950.0);
        choiceBox2.setLayoutY(370.0);
        choiceBox2.setPrefHeight(40.0);
        choiceBox2.setPrefWidth(300.0);

        textField8.setLayoutX(950.0);
        textField8.setLayoutY(450.0);
        textField8.setPrefHeight(40.0);
        textField8.setPrefWidth(300.0);
        textField8.setPromptText("Numer dokumentu");
        textField8.setFont(new Font(16.0));

        textField9.setLayoutX(350.0);
        textField9.setLayoutY(450.0);
        textField9.setPrefHeight(40.0);
        textField9.setPrefWidth(300.0);
        textField9.setPromptText("Numer Telefonu");
        textField9.setFont(new Font(16.0));

        button0.setLayoutX(700.0);
        button0.setLayoutY(630.0);
        button0.setMnemonicParsing(false);
        button0.setPrefHeight(45.0);
        button0.setPrefWidth(200.0);
        button0.setText("Dodaj pacjenta");
        button0.setFont(new Font(16.0));

        label0.setLayoutX(350.0);
        label0.setLayoutY(260.0);
        label0.setText("P�e�:");
        label0.setFont(new Font(16.0));

        label1.setLayoutX(350.0);
        label1.setLayoutY(340.0);
        label1.setText("Grupa krwi:");
        label1.setFont(new Font(16.0));

        label2.setLayoutX(950.0);
        label2.setLayoutY(340.0);
        label2.setText("Dokument:");
        label2.setFont(new Font(16.0));
        tab4.setContent(anchorPane2);
        tab2.setContent(tabPane0);

        tableView.getColumns().add(tableColumn);
        tableView.getColumns().add(tableColumn0);
        tableView.getColumns().add(tableColumn1);
        tableView.getColumns().add(tableColumn2);
        anchorPane.getChildren().add(tableView);
        tabPane.getTabs().add(tab0);
        anchorPane0.getChildren().add(textField);
        anchorPane0.getChildren().add(button);
        anchorPane0.getChildren().add(textField0);
        anchorPane0.getChildren().add(textField1);
        anchorPane0.getChildren().add(choiceBox);
        anchorPane0.getChildren().add(label);
        tabPane.getTabs().add(tab1);
        mainPane.getTabs().add(tab);
        tableView0.getColumns().add(tableColumn3);
        tableView0.getColumns().add(tableColumn4);
        tableView0.getColumns().add(tableColumn5);
        tableView0.getColumns().add(tableColumn6);
        tableView0.getColumns().add(tableColumn7);
        anchorPane1.getChildren().add(tableView0);
        tabPane0.getTabs().add(tab3);
        anchorPane2.getChildren().add(textField2);
        anchorPane2.getChildren().add(textField3);
        anchorPane2.getChildren().add(datePicker);
        anchorPane2.getChildren().add(choiceBox0);
        anchorPane2.getChildren().add(choiceBox1);
        anchorPane2.getChildren().add(textField4);
        anchorPane2.getChildren().add(textField5);
        anchorPane2.getChildren().add(textField6);
        anchorPane2.getChildren().add(textField7);
        anchorPane2.getChildren().add(choiceBox2);
        anchorPane2.getChildren().add(textField8);
        anchorPane2.getChildren().add(textField9);
        anchorPane2.getChildren().add(button0);
        anchorPane2.getChildren().add(label0);
        anchorPane2.getChildren().add(label1);
        anchorPane2.getChildren().add(label2);
        tabPane0.getTabs().add(tab4);
        mainPane.getTabs().add(tab2);
    }

}
