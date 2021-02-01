package pl.motokomando.healthcare.view.appointment;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import pl.motokomando.healthcare.controller.appointment.AppointmentController;
import pl.motokomando.healthcare.model.appointment.utils.MedicineRecord;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AppointmentView {

    private final AppointmentController controller;

    private TabPane appointmentPane;

    private Tab tab;
    private TabPane tabPane;
    private Tab tab0;
    private AnchorPane anchorPane;
    private TableView<MedicineRecord> tableView;
    private TableColumn<MedicineRecord, String> tableColumn;
    private TableColumn<MedicineRecord, String> tableColumn0;
    private TableColumn<MedicineRecord, String> tableColumn1;
    private TableColumn<MedicineRecord, String> tableColumn2;
    private TableColumn<MedicineRecord, String> tableColumn3;
    private TableColumn<MedicineRecord, String> tableColumn4;
    private TableColumn<MedicineRecord, String> tableColumn5;
    private TableColumn<MedicineRecord, String> tableColumn6;
    private TableColumn<MedicineRecord, String> tableColumn7;
    private Tab tab1;
    private AnchorPane anchorPane0;
    private TableView<MedicineRecord> tableView0;
    private TableColumn<MedicineRecord, String> tableColumn8;
    private TableColumn<MedicineRecord, String> tableColumn9;
    private TableColumn<MedicineRecord, String> tableColumn10;
    private TableColumn<MedicineRecord, String> tableColumn11;
    private TableColumn<MedicineRecord, String> tableColumn12;
    private TableColumn<MedicineRecord, String> tableColumn13;
    private TableColumn<MedicineRecord, String> tableColumn14;
    private TableColumn<MedicineRecord, String> tableColumn15;
    private TableColumn<MedicineRecord, String> tableColumn16;
    private TextField textField;
    private Button button;
    private Tab tab2;
    private AnchorPane anchorPane1;
    private ChoiceBox<String> choiceBox;
    private Label label;
    private Button button0;
    private Tab tab3;
    private Tab tabDoctorNotes;
    private AnchorPane anchorPane2;
    private AnchorPane anchorPaneDoctorNotes;
    private TextField textField0;
    private Label label0;
    private Button button1;
    private Button buttonSaveNotes;
    private TextArea textAreaDoctorNotes;

    public AppointmentView(AppointmentController controller) {
        this.controller = controller;
        createPane();
        addContent();
    }

    public Parent asParent() {
        return appointmentPane;
    }

    private void createPane() {
        appointmentPane = new TabPane();
        appointmentPane.setMaxHeight(USE_PREF_SIZE);
        appointmentPane.setMaxWidth(USE_PREF_SIZE);
        appointmentPane.setMinHeight(USE_PREF_SIZE);
        appointmentPane.setMinWidth(USE_PREF_SIZE);
        appointmentPane.setPrefHeight(700.0);
        appointmentPane.setPrefWidth(1200.0);
        appointmentPane.setTabClosingPolicy(UNAVAILABLE);
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
        tableColumn3 = new TableColumn<>();
        tableColumn4 = new TableColumn<>();
        tableColumn5 = new TableColumn<>();
        tableColumn6 = new TableColumn<>();
        tableColumn7 = new TableColumn<>();
        tab1 = new Tab();
        anchorPane0 = new AnchorPane();
        tableView0 = new TableView<>();
        tableColumn8 = new TableColumn<>();
        tableColumn9 = new TableColumn<>();
        tableColumn10 = new TableColumn<>();
        tableColumn11 = new TableColumn<>();
        tableColumn12 = new TableColumn<>();
        tableColumn13 = new TableColumn<>();
        tableColumn14 = new TableColumn<>();
        tableColumn15 = new TableColumn<>();
        tableColumn16 = new TableColumn<>();
        textField = new TextField();
        textAreaDoctorNotes = new TextArea();
        button = new Button();
        tab2 = new Tab();
        anchorPane1 = new AnchorPane();
        choiceBox = new ChoiceBox<>();
        label = new Label();
        button0 = new Button();
        tab3 = new Tab();
        tabDoctorNotes = new Tab();
        anchorPane2 = new AnchorPane();
        anchorPaneDoctorNotes = new AnchorPane();
        textField0 = new TextField();
        label0 = new Label();
        button1 = new Button();
        buttonSaveNotes = new Button();

        tab.setText("Recepta");

        tab0.setClosable(false);
        tab0.setText("Leki na recepcie");

        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);

        tableView.setLayoutX(50.0);
        tableView.setLayoutY(50.0);
        tableView.setPrefHeight(500.0);
        tableView.setPrefWidth(1100.0);

        tableColumn.setPrefWidth(122.0);
        tableColumn.setText("Nazwa");

        tableColumn0.setPrefWidth(122.0);
        tableColumn0.setText("Numer NDC");

        tableColumn1.setPrefWidth(122.0);
        tableColumn1.setText("Nazwa firmy");

        tableColumn2.setMinWidth(0.0);
        tableColumn2.setPrefWidth(122.0);
        tableColumn2.setText("Typ");

        tableColumn3.setMinWidth(0.0);
        tableColumn3.setPrefWidth(122.0);
        tableColumn3.setText("Ogólny typ");

        tableColumn4.setMinWidth(0.0);
        tableColumn4.setPrefWidth(122.0);
        tableColumn4.setText("Skład");

        tableColumn5.setMinWidth(0.0);
        tableColumn5.setPrefWidth(122.0);
        tableColumn5.setText("Sposób podania");

        tableColumn6.setMinWidth(0.0);
        tableColumn6.setPrefWidth(122.0);
        tableColumn6.setText("Dawkowanie");

        tableColumn7.setMinWidth(0.0);
        tableColumn7.setPrefWidth(122.0);
        tableColumn7.setText("Rodzaj opakowania");
        tab0.setContent(anchorPane);

        tab1.setClosable(false);
        tab1.setText("Dodaj lek");

        anchorPane0.setPrefHeight(200.0);
        anchorPane0.setPrefWidth(200.0);

        tableView0.setLayoutX(50.0);
        tableView0.setLayoutY(99.0);
        tableView0.setPrefHeight(450.0);
        tableView0.setPrefWidth(1100.0);

        tableColumn8.setPrefWidth(122.0);
        tableColumn8.setText("Nazwa");

        tableColumn9.setPrefWidth(122.0);
        tableColumn9.setText("Numer NDC");

        tableColumn10.setPrefWidth(122.0);
        tableColumn10.setText("Nazwa firmy");

        tableColumn11.setMinWidth(0.0);
        tableColumn11.setPrefWidth(122.0);
        tableColumn11.setText("Typ");

        tableColumn12.setMinWidth(0.0);
        tableColumn12.setPrefWidth(122.0);
        tableColumn12.setText("Ogólny typ");

        tableColumn13.setMinWidth(0.0);
        tableColumn13.setPrefWidth(122.0);
        tableColumn13.setText("Skład");

        tableColumn14.setMinWidth(0.0);
        tableColumn14.setPrefWidth(122.0);
        tableColumn14.setText("Sposób podania");

        tableColumn15.setMinWidth(0.0);
        tableColumn15.setPrefWidth(122.0);
        tableColumn15.setText("Dawkowanie");

        tableColumn16.setMinWidth(0.0);
        tableColumn16.setPrefWidth(122.0);
        tableColumn16.setText("Rodzaj opakowania");

        textField.setLayoutX(50.0);
        textField.setLayoutY(40.0);
        textField.setPrefHeight(40.0);
        textField.setPrefWidth(1500.0);
        textField.setPromptText("Wprowadź nazwe leku");

        textAreaDoctorNotes.setLayoutX(50.0);
        textAreaDoctorNotes.setLayoutY(50.0);
        textAreaDoctorNotes.setPrefHeight(500.0);
        textAreaDoctorNotes.setPrefWidth(1100.0);
        textAreaDoctorNotes.setPromptText("Dolegliwości, badania, zalecenia");

        button.setLayoutX(528.0);
        button.setLayoutY(580.0);
        button.setMnemonicParsing(false);
        button.setText("Dodaj do recepty");
        button.setFont(new Font(16.0));
        tab1.setContent(anchorPane0);
        tab.setContent(tabPane);

        tab2.setText("Doktor");

        anchorPane1.setMinHeight(0.0);
        anchorPane1.setMinWidth(0.0);
        anchorPane1.setPrefHeight(180.0);
        anchorPane1.setPrefWidth(200.0);

        choiceBox.setLayoutX(450.0);
        choiceBox.setLayoutY(193.0);
        choiceBox.setPrefHeight(40.0);
        choiceBox.setPrefWidth(300.0);

        label.setLayoutX(450.0);
        label.setLayoutY(168.0);
        label.setText("Wybierz lekarza:");
        label.setFont(new Font(16.0));

        button0.setLayoutX(555.0);
        button0.setLayoutY(361.0);
        button0.setMnemonicParsing(false);
        button0.setText("Zatwierdź");
        button0.setFont(new Font(16.0));
        tab2.setContent(anchorPane1);

        tab3.setText("Rachunek");
        tabDoctorNotes.setText("Notatki");

        anchorPane2.setPrefHeight(200.0);
        anchorPane2.setPrefWidth(200.0);

        anchorPaneDoctorNotes.setPrefHeight(200.0);
        anchorPaneDoctorNotes.setPrefWidth(200.0);

        textField0.setLayoutX(500.0);
        textField0.setLayoutY(193.0);
        textField0.setPrefHeight(50.0);
        textField0.setFont(new Font(16.0));

        label0.setLayoutX(500.0);
        label0.setLayoutY(168.0);
        label0.setText("Kwota (PLN):");
        label0.setFont(new Font(16.0));

        button1.setLayoutX(525.0);
        button1.setLayoutY(361.0);
        button1.setMnemonicParsing(false);
        button1.setText("Zatwierdź i drukuj");
        button1.setFont(new Font(16.0));

        buttonSaveNotes.setLayoutX(575.0);
        buttonSaveNotes.setLayoutY(600.0);
        buttonSaveNotes.setMnemonicParsing(false);
        buttonSaveNotes.setText("Zapisz");
        buttonSaveNotes.setFont(new Font(16.0));

        tab3.setContent(anchorPane2);
        tabDoctorNotes.setContent(anchorPaneDoctorNotes);

        tableView.getColumns().add(tableColumn);
        tableView.getColumns().add(tableColumn0);
        tableView.getColumns().add(tableColumn1);
        tableView.getColumns().add(tableColumn2);
        tableView.getColumns().add(tableColumn3);
        tableView.getColumns().add(tableColumn4);
        tableView.getColumns().add(tableColumn5);
        tableView.getColumns().add(tableColumn6);
        tableView.getColumns().add(tableColumn7);
        anchorPane.getChildren().add(tableView);
        tabPane.getTabs().add(tab0);
        tableView0.getColumns().add(tableColumn8);
        tableView0.getColumns().add(tableColumn9);
        tableView0.getColumns().add(tableColumn10);
        tableView0.getColumns().add(tableColumn11);
        tableView0.getColumns().add(tableColumn12);
        tableView0.getColumns().add(tableColumn13);
        tableView0.getColumns().add(tableColumn14);
        tableView0.getColumns().add(tableColumn15);
        tableView0.getColumns().add(tableColumn16);
        anchorPane0.getChildren().add(tableView0);
        anchorPane0.getChildren().add(textField);
        anchorPane0.getChildren().add(button);
        tabPane.getTabs().add(tab1);
        appointmentPane.getTabs().add(tab);
        anchorPane1.getChildren().add(choiceBox);
        anchorPane1.getChildren().add(label);
        anchorPane1.getChildren().add(button0);
        appointmentPane.getTabs().add(tab2);
        anchorPane2.getChildren().add(textField0);
        anchorPane2.getChildren().add(label0);
        anchorPane2.getChildren().add(button1);
        anchorPaneDoctorNotes.getChildren().add(textAreaDoctorNotes);
        anchorPaneDoctorNotes.getChildren().add(buttonSaveNotes);
        appointmentPane.getTabs().add(tabDoctorNotes);
        appointmentPane.getTabs().add(tab3);
    }

}
