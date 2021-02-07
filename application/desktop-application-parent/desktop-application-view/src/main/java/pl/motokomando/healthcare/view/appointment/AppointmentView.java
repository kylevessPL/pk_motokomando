package pl.motokomando.healthcare.view.appointment;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import pl.motokomando.healthcare.controller.appointment.AppointmentController;
import pl.motokomando.healthcare.model.appointment.utils.MedicineRecord;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AppointmentView {

    private final AppointmentController controller;

    private TabPane appointmentPane;

    private Tab tabPrescription;
    private TabPane tabPane;
    private Tab tabDrugsOnPrescription;
    private AnchorPane anchorPaneDrugsOnPrescription;
    private TableView<MedicineRecord> tableViewDrugsOnPrescription;
    private TableColumn<MedicineRecord, String> tableColumnDrugName;
    private TableColumn<MedicineRecord, String> tableColumnDrugNDCNumber;
    private TableColumn<MedicineRecord, String> tableColumnDrugCompanyName;
    private TableColumn<MedicineRecord, String> tableColumnDrugType;
    private TableColumn<MedicineRecord, String> tableColumnDrugOverallType;
    private TableColumn<MedicineRecord, String> tableColumnDrugComposition;
    private TableColumn<MedicineRecord, String> tableColumnDrugServeWay;
    private TableColumn<MedicineRecord, String> tableColumnDrugDosage;
    private TableColumn<MedicineRecord, String> tableColumnDrugBoxType;
    private Tab tabAddDrug;
    private AnchorPane anchorPaneAddDrug;
    private TableView<MedicineRecord> tableViewAddDrug;
    private TextField textFieldEnterDrugName;
    private Button buttonAddToPresciption;
    private Tab tabDoctor;
    private AnchorPane anchorPaneDoctor;
    private ChoiceBox<String> choiceBoxDoctor;
    private Label labeChooseDoctor;
    private Button buttonSaveDoctor;
    private Tab tabBill;
    private Tab tabDoctorNotes;
    private AnchorPane anchorPaneBill;
    private AnchorPane anchorPaneDoctorNotes;
    private TextField textFieldBill;
    private Label labelBill;
    private Button buttonSaveAndPrintBill;
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
        tabPrescription = new Tab();
        tabPane = new TabPane();
        tabDrugsOnPrescription = new Tab();
        anchorPaneDrugsOnPrescription = new AnchorPane();
        tableViewDrugsOnPrescription = new TableView<>();
        tableColumnDrugName = new TableColumn<>();
        tableColumnDrugNDCNumber = new TableColumn<>();
        tableColumnDrugCompanyName = new TableColumn<>();
        tableColumnDrugType = new TableColumn<>();
        tableColumnDrugOverallType = new TableColumn<>();
        tableColumnDrugComposition = new TableColumn<>();
        tableColumnDrugServeWay = new TableColumn<>();
        tableColumnDrugDosage = new TableColumn<>();
        tableColumnDrugBoxType = new TableColumn<>();
        tabAddDrug = new Tab();
        anchorPaneAddDrug = new AnchorPane();
        tableViewAddDrug = new TableView<>();
        textFieldEnterDrugName = new TextField();
        textAreaDoctorNotes = new TextArea();
        buttonAddToPresciption = new Button();
        tabDoctor = new Tab();
        anchorPaneDoctor = new AnchorPane();
        choiceBoxDoctor = new ChoiceBox<>();
        labeChooseDoctor = new Label();
        buttonSaveDoctor = new Button();
        tabBill = new Tab();
        tabDoctorNotes = new Tab();
        anchorPaneBill = new AnchorPane();
        anchorPaneDoctorNotes = new AnchorPane();
        textFieldBill = new TextField();
        labelBill = new Label();
        buttonSaveAndPrintBill = new Button();
        buttonSaveNotes = new Button();

        tabPrescription.setText("Recepta");

        tabDrugsOnPrescription.setClosable(false);
        tabDrugsOnPrescription.setText("Leki na recepcie");

        anchorPaneDrugsOnPrescription.setPrefHeight(200.0);
        anchorPaneDrugsOnPrescription.setPrefWidth(200.0);

        tableViewDrugsOnPrescription.setLayoutX(50.0);
        tableViewDrugsOnPrescription.setLayoutY(50.0);
        tableViewDrugsOnPrescription.setPrefHeight(500.0);
        tableViewDrugsOnPrescription.setPrefWidth(1100.0);

        tableColumnDrugName.setPrefWidth(122.0);
        tableColumnDrugName.setText("Nazwa");

        tableColumnDrugNDCNumber.setPrefWidth(122.0);
        tableColumnDrugNDCNumber.setText("Numer NDC");

        tableColumnDrugCompanyName.setPrefWidth(122.0);
        tableColumnDrugCompanyName.setText("Nazwa firmy");

        tableColumnDrugType.setMinWidth(0.0);
        tableColumnDrugType.setPrefWidth(122.0);
        tableColumnDrugType.setText("Typ");

        tableColumnDrugOverallType.setMinWidth(0.0);
        tableColumnDrugOverallType.setPrefWidth(122.0);
        tableColumnDrugOverallType.setText("Ogólny typ");

        tableColumnDrugComposition.setMinWidth(0.0);
        tableColumnDrugComposition.setPrefWidth(122.0);
        tableColumnDrugComposition.setText("Skład");

        tableColumnDrugServeWay.setMinWidth(0.0);
        tableColumnDrugServeWay.setPrefWidth(122.0);
        tableColumnDrugServeWay.setText("Sposób podania");

        tableColumnDrugDosage.setMinWidth(0.0);
        tableColumnDrugDosage.setPrefWidth(122.0);
        tableColumnDrugDosage.setText("Dawkowanie");

        tableColumnDrugBoxType.setMinWidth(0.0);
        tableColumnDrugBoxType.setPrefWidth(122.0);
        tableColumnDrugBoxType.setText("Rodzaj opakowania");

        tabDrugsOnPrescription.setContent(anchorPaneDrugsOnPrescription);

        tabAddDrug.setClosable(false);
        tabAddDrug.setText("Dodaj lek");

        anchorPaneAddDrug.setPrefHeight(200.0);
        anchorPaneAddDrug.setPrefWidth(200.0);

        tableViewAddDrug.setLayoutX(50.0);
        tableViewAddDrug.setLayoutY(99.0);
        tableViewAddDrug.setPrefHeight(450.0);
        tableViewAddDrug.setPrefWidth(1100.0);

        textFieldEnterDrugName.setLayoutX(50.0);
        textFieldEnterDrugName.setLayoutY(40.0);
        textFieldEnterDrugName.setPrefHeight(40.0);
        textFieldEnterDrugName.setPrefWidth(1500.0);
        textFieldEnterDrugName.setPromptText("Wprowadź nazwe leku");

        textAreaDoctorNotes.setLayoutX(50.0);
        textAreaDoctorNotes.setLayoutY(50.0);
        textAreaDoctorNotes.setPrefHeight(500.0);
        textAreaDoctorNotes.setPrefWidth(1100.0);
        textAreaDoctorNotes.setPromptText("Dolegliwości, badania, zalecenia");

        buttonAddToPresciption.setLayoutX(528.0);
        buttonAddToPresciption.setLayoutY(580.0);
        buttonAddToPresciption.setMnemonicParsing(false);
        buttonAddToPresciption.setText("Dodaj do recepty");
        buttonAddToPresciption.setFont(new Font(16.0));
        tabAddDrug.setContent(anchorPaneAddDrug);
        tabPrescription.setContent(tabPane);

        tabDoctor.setText("Lekarz");

        anchorPaneDoctor.setMinHeight(0.0);
        anchorPaneDoctor.setMinWidth(0.0);
        anchorPaneDoctor.setPrefHeight(180.0);
        anchorPaneDoctor.setPrefWidth(200.0);

        choiceBoxDoctor.setLayoutX(450.0);
        choiceBoxDoctor.setLayoutY(193.0);
        choiceBoxDoctor.setPrefHeight(40.0);
        choiceBoxDoctor.setPrefWidth(300.0);

        labeChooseDoctor.setLayoutX(450.0);
        labeChooseDoctor.setLayoutY(168.0);
        labeChooseDoctor.setText("Wybierz lekarza:");
        labeChooseDoctor.setFont(new Font(16.0));

        buttonSaveDoctor.setLayoutX(555.0);
        buttonSaveDoctor.setLayoutY(361.0);
        buttonSaveDoctor.setMnemonicParsing(false);
        buttonSaveDoctor.setText("Zatwierdź");
        buttonSaveDoctor.setFont(new Font(16.0));
        tabDoctor.setContent(anchorPaneDoctor);

        tabBill.setText("Rachunek");
        tabDoctorNotes.setText("Notatki");

        anchorPaneBill.setPrefHeight(200.0);
        anchorPaneBill.setPrefWidth(200.0);

        anchorPaneDoctorNotes.setPrefHeight(200.0);
        anchorPaneDoctorNotes.setPrefWidth(200.0);

        textFieldBill.setLayoutX(500.0);
        textFieldBill.setLayoutY(193.0);
        textFieldBill.setPrefHeight(50.0);
        textFieldBill.setFont(new Font(16.0));

        labelBill.setLayoutX(500.0);
        labelBill.setLayoutY(168.0);
        labelBill.setText("Kwota (PLN):");
        labelBill.setFont(new Font(16.0));

        buttonSaveAndPrintBill.setLayoutX(525.0);
        buttonSaveAndPrintBill.setLayoutY(361.0);
        buttonSaveAndPrintBill.setMnemonicParsing(false);
        buttonSaveAndPrintBill.setText("Zatwierdź i drukuj");
        buttonSaveAndPrintBill.setFont(new Font(16.0));

        buttonSaveNotes.setLayoutX(575.0);
        buttonSaveNotes.setLayoutY(600.0);
        buttonSaveNotes.setMnemonicParsing(false);
        buttonSaveNotes.setText("Zapisz");
        buttonSaveNotes.setFont(new Font(16.0));

        tabBill.setContent(anchorPaneBill);
        tabDoctorNotes.setContent(anchorPaneDoctorNotes);

        setTableViewDrugsOnPrescription(tableViewDrugsOnPrescription);
        anchorPaneDrugsOnPrescription.getChildren().add(tableViewDrugsOnPrescription);
        tabPane.getTabs().add(tabDrugsOnPrescription);
        setTableViewDrugsOnPrescription(tableViewAddDrug);
        anchorPaneAddDrug.getChildren().add(tableViewAddDrug);
        anchorPaneAddDrug.getChildren().add(textFieldEnterDrugName);
        anchorPaneAddDrug.getChildren().add(buttonAddToPresciption);
        tabPane.getTabs().add(tabAddDrug);
        appointmentPane.getTabs().add(tabPrescription);
        anchorPaneDoctor.getChildren().add(choiceBoxDoctor);
        anchorPaneDoctor.getChildren().add(labeChooseDoctor);
        anchorPaneDoctor.getChildren().add(buttonSaveDoctor);
        appointmentPane.getTabs().add(tabDoctor);
        anchorPaneBill.getChildren().add(textFieldBill);
        anchorPaneBill.getChildren().add(labelBill);
        anchorPaneBill.getChildren().add(buttonSaveAndPrintBill);
        anchorPaneDoctorNotes.getChildren().add(textAreaDoctorNotes);
        anchorPaneDoctorNotes.getChildren().add(buttonSaveNotes);
        appointmentPane.getTabs().add(tabDoctorNotes);
        appointmentPane.getTabs().add(tabBill);
    }

    private void setTableViewDrugsOnPrescription(TableView<MedicineRecord> tableViewDrugsOnPrescription) {
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugName);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugNDCNumber);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugCompanyName);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugType);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugOverallType);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugComposition);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugServeWay);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugDosage);
        tableViewDrugsOnPrescription.getColumns().add(tableColumnDrugBoxType);
    }

}
