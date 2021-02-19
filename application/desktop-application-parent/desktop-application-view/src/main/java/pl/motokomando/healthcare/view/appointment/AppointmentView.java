package pl.motokomando.healthcare.view.appointment;

import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pl.motokomando.healthcare.controller.appointment.AppointmentController;
import pl.motokomando.healthcare.model.appointment.AppointmentModel;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;
import pl.motokomando.healthcare.model.appointment.utils.ProductType;
import pl.motokomando.healthcare.view.appointment.utils.ActiveIngredientsColumnCallback;
import pl.motokomando.healthcare.view.appointment.utils.AdministrationRouteColumnCallback;
import pl.motokomando.healthcare.view.appointment.utils.PackagingVariantsColumnCallback;
import utils.FXTasks;

import static javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS;
import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AppointmentView {

    private AppointmentController controller;

    private AppointmentModel model;

    private TabPane appointmentPane;

    private TabPane prescriptionPane;
    private Tab prescriptionTab;
    private Tab doctorTab;
    private Tab prescriptionMedicinesTab;
    private Tab addPrescriptionMedicineTab;
    private Tab billTab;
    private Tab prescriptionNotesTab;
    private AnchorPane billPane;
    private AnchorPane prescriptionNotesPane;
    private AnchorPane doctorPane;
    private AnchorPane prescriptionMedicinesPane;
    private AnchorPane addPrescriptionMedicinePane;
    private TableView<MedicinesTableRecord> medicinesTable;
    private TableView<MedicinesTableRecord> prescriptionMedicinesTable;
    private TextField medicineSearchQueryTextField;
    private Button addMedicineToPrescriptionButton;
    private ComboBox<String> chooseDoctorComboBox;
    private Label chooseDoctorLabel;
    private Button saveDoctorDetailsButton;
    private TextField billAmountTextField;
    private Label billAmountLabel;
    private Button saveBillDetailsButton;
    private Button diagnosisButton;
    private TextArea diagnosisTextArea;

    public AppointmentView(Integer appointmentId) {
        initModel(appointmentId);
        setController();
        createPane();
        addContent();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return appointmentPane;
    }

    private Stage currentStage() {
        return (Stage) appointmentPane.getScene().getWindow();
    }

    private void initModel(Integer appointmentId) {
        model = new AppointmentModel(appointmentId);
    }

    private void setController() {
        controller = new AppointmentController(model);
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
        createPrescriptionTab();
        createDoctorTab();
        createPrescriptionNotesTab();
        createBillTab();
    }

    private void createBillTab() {
        billTab = new Tab();
        billTab.setText("Rachunek");
        billPane = new AnchorPane();
        billPane.setPrefHeight(200.0);
        billPane.setPrefWidth(200.0);
        createBillAmountTextField();
        createBillAmountLabel();
        createSaveBillDetailsButton();
        billTab.setContent(billPane);
        appointmentPane.getTabs().add(billTab);
    }

    private void createSaveBillDetailsButton() {
        saveBillDetailsButton = new Button();
        saveBillDetailsButton.setLayoutX(525.0);
        saveBillDetailsButton.setLayoutY(361.0);
        saveBillDetailsButton.setMnemonicParsing(false);
        saveBillDetailsButton.setText("Zatwierdź");
        saveBillDetailsButton.setFont(new Font(16.0));
        billPane.getChildren().add(saveBillDetailsButton);
    }

    private void createBillAmountLabel() {
        billAmountLabel = new Label();
        billAmountLabel.setLayoutX(500.0);
        billAmountLabel.setLayoutY(168.0);
        billAmountLabel.setText("Kwota (PLN):");
        billAmountLabel.setFont(new Font(16.0));
        billPane.getChildren().add(billAmountLabel);
    }

    private void createBillAmountTextField() {
        billAmountTextField = new TextField();
        billAmountTextField.setLayoutX(500.0);
        billAmountTextField.setLayoutY(193.0);
        billAmountTextField.setPrefHeight(50.0);
        billAmountTextField.setFont(new Font(16.0));
        billPane.getChildren().add(billAmountTextField);
    }

    private void createPrescriptionNotesTab() {
        prescriptionNotesTab = new Tab();
        prescriptionNotesTab.setText("Notatki");
        prescriptionNotesPane = new AnchorPane();
        prescriptionNotesPane.setPrefHeight(200.0);
        prescriptionNotesPane.setPrefWidth(200.0);
        createDiagnosisTextArea();
        createDiagnosisButton();
        prescriptionNotesTab.setContent(prescriptionNotesPane);
        appointmentPane.getTabs().add(prescriptionNotesTab);
    }

    private void createDiagnosisButton() {
        diagnosisButton = new Button();
        diagnosisButton.setLayoutX(575.0);
        diagnosisButton.setLayoutY(600.0);
        diagnosisButton.setMnemonicParsing(false);
        diagnosisButton.setText("Zapisz");
        diagnosisButton.setFont(new Font(16.0));
        prescriptionNotesPane.getChildren().add(diagnosisButton);
    }

    private void createDiagnosisTextArea() {
        diagnosisTextArea = new TextArea();
        diagnosisTextArea.setLayoutX(50.0);
        diagnosisTextArea.setLayoutY(50.0);
        diagnosisTextArea.setPrefHeight(500.0);
        diagnosisTextArea.setPrefWidth(1100.0);
        diagnosisTextArea.setPromptText("Dolegliwości, badania, zalecenia");
        prescriptionNotesPane.getChildren().add(diagnosisTextArea);
    }

    private void createDoctorTab() {
        doctorTab = new Tab();
        doctorTab.setText("Lekarz");
        doctorPane = new AnchorPane();
        doctorPane.setPrefHeight(180.0);
        doctorPane.setPrefWidth(200.0);
        createChooseDoctorComboBox();
        createChooseDoctorLabel();
        createSaveDoctorDetailsButton();
        doctorTab.setContent(doctorPane);
        appointmentPane.getTabs().add(doctorTab);
    }

    private void createSaveDoctorDetailsButton() {
        saveDoctorDetailsButton = new Button();
        saveDoctorDetailsButton.setLayoutX(555.0);
        saveDoctorDetailsButton.setLayoutY(361.0);
        saveDoctorDetailsButton.setMnemonicParsing(false);
        saveDoctorDetailsButton.setText("Zatwierdź");
        saveDoctorDetailsButton.setFont(new Font(16.0));
        doctorPane.getChildren().add(saveDoctorDetailsButton);
    }

    private void createChooseDoctorLabel() {
        chooseDoctorLabel = new Label();
        chooseDoctorLabel.setLayoutX(450.0);
        chooseDoctorLabel.setLayoutY(168.0);
        chooseDoctorLabel.setText("Wybierz lekarza:");
        chooseDoctorLabel.setFont(new Font(16.0));
        doctorPane.getChildren().add(chooseDoctorLabel);
    }

    private void createChooseDoctorComboBox() {
        chooseDoctorComboBox = new ComboBox<>();
        chooseDoctorComboBox.setLayoutX(450.0);
        chooseDoctorComboBox.setLayoutY(193.0);
        chooseDoctorComboBox.setPrefHeight(40.0);
        chooseDoctorComboBox.setPrefWidth(300.0);
        doctorPane.getChildren().add(chooseDoctorComboBox);
    }

    private void createPrescriptionTab() {
        prescriptionTab = new Tab();
        prescriptionTab.setText("Recepta");
        prescriptionPane = new TabPane();
        createPrescriptionMedicinesTab();
        createAddPrescriptionMedicineTab();
        prescriptionTab.setContent(prescriptionPane);
        appointmentPane.getTabs().add(prescriptionTab);
    }

    private void createPrescriptionMedicinesTab() {
        prescriptionMedicinesTab = new Tab();
        prescriptionMedicinesTab.setClosable(false);
        prescriptionMedicinesTab.setText("Leki na recepcie");
        prescriptionMedicinesPane = new AnchorPane();
        prescriptionMedicinesPane.setPrefHeight(200.0);
        prescriptionMedicinesPane.setPrefWidth(200.0);
        createPrescriptionMedicinesTable();
        prescriptionMedicinesTab.setContent(prescriptionMedicinesPane);
        prescriptionPane.getTabs().add(prescriptionMedicinesTab);
    }

    private void createMedicinesTable() {
        medicinesTable = new TableView<>();
        medicinesTable.setLayoutX(50.0);
        medicinesTable.setLayoutY(99.0);
        medicinesTable.setPrefHeight(450.0);
        medicinesTable.setPrefWidth(1100.0);
        medicinesTable.setPlaceholder(new Label("Wprowadź zapytanie w polu wyszukiwania, aby znaleźć leki"));
        setMedicinesTableColumns(medicinesTable);
        medicinesTable.setItems(model.medicinesTableContent());
        addPrescriptionMedicinePane.getChildren().add(medicinesTable);
    }

    private void createAddPrescriptionMedicineTab() {
        addPrescriptionMedicineTab = new Tab();
        addPrescriptionMedicineTab.setClosable(false);
        addPrescriptionMedicineTab.setText("Dodaj lek");
        addPrescriptionMedicinePane = new AnchorPane();
        addPrescriptionMedicinePane.setPrefHeight(200.0);
        addPrescriptionMedicinePane.setPrefWidth(200.0);
        createMedicinesTable();
        createMedicineNameTextField();
        createAddMedicineToPrescriptionButton();
        addPrescriptionMedicineTab.setContent(addPrescriptionMedicinePane);
        prescriptionPane.getTabs().add(addPrescriptionMedicineTab);
    }

    private void createAddMedicineToPrescriptionButton() {
        addMedicineToPrescriptionButton = new Button();
        addMedicineToPrescriptionButton.setLayoutX(528.0);
        addMedicineToPrescriptionButton.setLayoutY(580.0);
        addMedicineToPrescriptionButton.setMnemonicParsing(false);
        addMedicineToPrescriptionButton.setText("Dodaj do recepty");
        addMedicineToPrescriptionButton.setFont(new Font(16.0));
        addPrescriptionMedicinePane.getChildren().add(addMedicineToPrescriptionButton);
    }

    private void createMedicineNameTextField() {
        medicineSearchQueryTextField = new TextField();
        medicineSearchQueryTextField.setLayoutX(50.0);
        medicineSearchQueryTextField.setLayoutY(40.0);
        medicineSearchQueryTextField.setPrefHeight(40.0);
        medicineSearchQueryTextField.setPrefWidth(1500.0);
        medicineSearchQueryTextField.setPromptText("Wprowadź nazwę leku");
        addPrescriptionMedicinePane.getChildren().add(medicineSearchQueryTextField);
    }

    private void createPrescriptionMedicinesTable() {
        prescriptionMedicinesTable = new TableView<>();
        prescriptionMedicinesTable.setLayoutX(50.0);
        prescriptionMedicinesTable.setLayoutY(50.0);
        prescriptionMedicinesTable.setPrefHeight(500);
        prescriptionMedicinesTable.setPrefWidth(1100.0);
        prescriptionMedicinesTable.setPlaceholder(new Label("Brak leków powiązanych z receptą"));
        setMedicinesTableColumns(prescriptionMedicinesTable);
        prescriptionMedicinesPane.getChildren().add(prescriptionMedicinesTable);
    }

    private <T> TableColumn<MedicinesTableRecord, T> createMedicinesTableColumn() {
        TableColumn<MedicinesTableRecord, T> column = new TableColumn<>();
        column.setPrefWidth(122.0);
        return column;
    }

    private void setMedicinesTableColumns(TableView<MedicinesTableRecord> medicinesTable) {
        TableColumn<MedicinesTableRecord, String> column1 = createMedicinesTableFirstColumn();
        TableColumn<MedicinesTableRecord, String> column2 = createMedicinesTableSecondColumn();
        TableColumn<MedicinesTableRecord, String> column3 = createMedicinesTableThirdColumn();
        TableColumn<MedicinesTableRecord, ProductType> column4 = createMedicinesTableFourthColumn();
        TableColumn<MedicinesTableRecord, String> column5 = createMedicinesTableFifthColumn();
        TableColumn<MedicinesTableRecord, Void> column6 = createMedicinesTableSixthColumn();
        TableColumn<MedicinesTableRecord, Void> column7 = createMedicinesTableSeventhColumn();
        TableColumn<MedicinesTableRecord, String> column8 = createMedicinesTableEigthColumn();
        TableColumn<MedicinesTableRecord, Void> column9 = createMedicinesTableNinthColumn();
        medicinesTable.getColumns().add(column1);
        medicinesTable.getColumns().add(column2);
        medicinesTable.getColumns().add(column3);
        medicinesTable.getColumns().add(column4);
        medicinesTable.getColumns().add(column5);
        medicinesTable.getColumns().add(column6);
        medicinesTable.getColumns().add(column7);
        medicinesTable.getColumns().add(column8);
        medicinesTable.getColumns().add(column9);
    }

    private TableColumn<MedicinesTableRecord, Void> createMedicinesTableNinthColumn() {
        TableColumn<MedicinesTableRecord, Void> column9 = createMedicinesTableColumn();
        column9.setText("Rodzaj opakowania");
        column9.setStyle("-fx-alignment: center-left;");
        column9.setCellFactory(new PackagingVariantsColumnCallback(appointmentPane));
        return column9;
    }

    private TableColumn<MedicinesTableRecord, String> createMedicinesTableEigthColumn() {
        TableColumn<MedicinesTableRecord, String> column8 = createMedicinesTableColumn();
        column8.setText("Dawkowanie");
        column8.setStyle("-fx-alignment: center;");
        column8.setCellValueFactory(c -> c.getValue().dosageForm());
        return column8;
    }

    private TableColumn<MedicinesTableRecord, Void> createMedicinesTableSeventhColumn() {
        TableColumn<MedicinesTableRecord, Void> column7 = createMedicinesTableColumn();
        column7.setText("Sposób podania");
        column7.setCellFactory(new AdministrationRouteColumnCallback(appointmentPane));
        return column7;
    }

    private TableColumn<MedicinesTableRecord, Void> createMedicinesTableSixthColumn() {
        TableColumn<MedicinesTableRecord, Void> column6 = createMedicinesTableColumn();
        column6.setText("Skład");
        column6.setCellFactory(new ActiveIngredientsColumnCallback(appointmentPane));
        return column6;
    }

    private TableColumn<MedicinesTableRecord, String> createMedicinesTableFifthColumn() {
        TableColumn<MedicinesTableRecord, String> column5 = createMedicinesTableColumn();
        column5.setText("Ogólny typ");
        column5.setStyle("-fx-alignment: center-left;");
        column5.setCellValueFactory(c -> c.getValue().genericName());
        return column5;
    }

    private TableColumn<MedicinesTableRecord, ProductType> createMedicinesTableFourthColumn() {
        TableColumn<MedicinesTableRecord, ProductType> column4 = createMedicinesTableColumn();
        column4.setText("Typ");
        column4.setStyle("-fx-alignment: center;");
        column4.setCellValueFactory(c -> c.getValue().productType());
        return column4;
    }

    private TableColumn<MedicinesTableRecord, String> createMedicinesTableThirdColumn() {
        TableColumn<MedicinesTableRecord, String> column3 = createMedicinesTableColumn();
        column3.setText("Nazwa firmy");
        column3.setStyle("-fx-alignment: center-left;");
        column3.setCellValueFactory(c -> c.getValue().manufacturer());
        return column3;
    }

    private TableColumn<MedicinesTableRecord, String> createMedicinesTableSecondColumn() {
        TableColumn<MedicinesTableRecord, String> column2 = createMedicinesTableColumn();
        column2.setText("Numer NDC");
        column2.setStyle("-fx-alignment: center;");
        column2.setCellValueFactory(c -> c.getValue().productNDC());
        return column2;
    }

    private TableColumn<MedicinesTableRecord, String> createMedicinesTableFirstColumn() {
        TableColumn<MedicinesTableRecord, String> column1 = createMedicinesTableColumn();
        column1.setText("Nazwa");
        column1.setStyle("-fx-alignment: center-left;");
        column1.setCellValueFactory(c -> c.getValue().productName());
        return column1;
    }

    private void observeModelAndUpdate() {

    }

    private void delegateEventHandlers() {
        medicineSearchQueryTextField.setOnKeyPressed(this::medicineSearchQueryTextFieldEnterPressed);
    }

    private void medicineSearchQueryTextFieldEnterPressed(KeyEvent event) {
        if (event.getCode().equals(ENTER)) {
            searchMedicine();
        }
    }

    private void searchMedicine() {
        String query = medicineSearchQueryTextField.getText();
        Task<Void> task = FXTasks.createTask(() -> controller.handleMedicinesSearchAction(query));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnRunning(e -> {
            ProgressIndicator progressIndicator = new ProgressIndicator(INDETERMINATE_PROGRESS);
            progressIndicator.setMaxSize(40, 40);
            medicinesTable.setPlaceholder(progressIndicator);
        });
        task.setOnSucceeded(e -> medicinesTable.setPlaceholder(new Label("Brak wyników pasujących do wyszukiwania")));
        task.setOnFailed(e -> medicinesTable.setPlaceholder(new Label("Nie udało się załadować wyników wyszukiwania")));
    }
}
