package pl.motokomando.healthcare.view.appointment;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pl.motokomando.healthcare.controller.appointment.AppointmentController;
import pl.motokomando.healthcare.model.appointment.AppointmentModel;
import pl.motokomando.healthcare.view.appointment.utils.MedicineRecord;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
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
    private TableView<MedicineRecord> medicinesTable;
    private TableView<MedicineRecord> prescriptionMedicinesTable;
    private TextField medicineNameTextField;
    private Button addMedicineToPrescriptionButton;
    private Button searchMedicineToPrescriptionButton;
    private ComboBox<String> chooseDoctorComboBox;
    private Label chooseDoctorLabel;
    private Button saveDoctorDetailsButton;
    private TextField billAmountTextField;
    private Label billAmountLabel;
    private Button saveBillDetailsButton;
    private Button diagnosisButton;
    private TextArea diagnosisTextArea;

    public AppointmentView() {
        initModel();
        setController();
        createPane();
        addContent();
    }

    public Parent asParent() {
        return appointmentPane;
    }

    private Stage currentStage() {
        return (Stage) appointmentPane.getScene().getWindow();
    }

    private void initModel() {
        model = new AppointmentModel();
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
        saveBillDetailsButton.setLayoutX(550.0);
        saveBillDetailsButton.setLayoutY(360.0);
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
        createMedicinesTable();
        prescriptionMedicinesTab.setContent(prescriptionMedicinesPane);
        prescriptionPane.getTabs().add(prescriptionMedicinesTab);
    }

    private void createMedicinesTable() {
        medicinesTable = new TableView<>();
        medicinesTable.setLayoutX(50.0);
        medicinesTable.setLayoutY(50.0);
        medicinesTable.setPrefHeight(500.0);
        medicinesTable.setPrefWidth(1100.0);
        setMedicinesTableContent(medicinesTable);
        prescriptionMedicinesPane.getChildren().add(medicinesTable);
    }

    private void createAddPrescriptionMedicineTab() {
        addPrescriptionMedicineTab = new Tab();
        addPrescriptionMedicineTab.setClosable(false);
        addPrescriptionMedicineTab.setText("Dodaj lek");
        addPrescriptionMedicinePane = new AnchorPane();
        addPrescriptionMedicinePane.setPrefHeight(200.0);
        addPrescriptionMedicinePane.setPrefWidth(200.0);
        createPrescriptionMedicinesTable();
        createMedicineNameTextField();
        createSearchMedicineToPrescriptionButton();
        createAddMedicineToPrescriptionButton();
        addPrescriptionMedicineTab.setContent(addPrescriptionMedicinePane);
        prescriptionPane.getTabs().add(addPrescriptionMedicineTab);
    }

    private void createSearchMedicineToPrescriptionButton(){
        searchMedicineToPrescriptionButton = new Button();
        searchMedicineToPrescriptionButton.setLayoutX(1080.0);
        searchMedicineToPrescriptionButton.setLayoutY(40.0);
        searchMedicineToPrescriptionButton.setMnemonicParsing(false);
        searchMedicineToPrescriptionButton.setText("Szukaj");
        searchMedicineToPrescriptionButton.setFont(new Font(16.0));
        addPrescriptionMedicinePane.getChildren().add(searchMedicineToPrescriptionButton);
    }

    private void createAddMedicineToPrescriptionButton() {
        addMedicineToPrescriptionButton = new Button();
        addMedicineToPrescriptionButton.setLayoutX(528.0);
        addMedicineToPrescriptionButton.setLayoutY(570.0);
        addMedicineToPrescriptionButton.setMnemonicParsing(false);
        addMedicineToPrescriptionButton.setText("Dodaj do recepty");
        addMedicineToPrescriptionButton.setFont(new Font(16.0));
        addPrescriptionMedicinePane.getChildren().add(addMedicineToPrescriptionButton);
    }

    private void createMedicineNameTextField() {
        medicineNameTextField = new TextField();
        medicineNameTextField.setLayoutX(50.0);
        medicineNameTextField.setLayoutY(40.0);
        medicineNameTextField.setPrefHeight(40.0);
        medicineNameTextField.setPrefWidth(1020.0);
        medicineNameTextField.setPromptText("Wprowadź nazwę leku");
        addPrescriptionMedicinePane.getChildren().add(medicineNameTextField);
    }

    private void createPrescriptionMedicinesTable() {
        prescriptionMedicinesTable = new TableView<>();
        prescriptionMedicinesTable.setLayoutX(50.0);
        prescriptionMedicinesTable.setLayoutY(99.0);
        prescriptionMedicinesTable.setPrefHeight(450.0);
        prescriptionMedicinesTable.setPrefWidth(1100.0);
        setMedicinesTableContent(prescriptionMedicinesTable);
        addPrescriptionMedicinePane.getChildren().add(prescriptionMedicinesTable);
    }

    private TableColumn<MedicineRecord, String> createMedicinesTableColumn() {
        TableColumn<MedicineRecord, String> column = new TableColumn<>();
        column.setPrefWidth(122.0);
        return column;
    }

    private List<TableColumn<MedicineRecord, String>> createMedicinesTableColumns() {
        List<TableColumn<MedicineRecord, String>> columnList = IntStream
                .range(0, 9)
                .mapToObj(i -> createMedicinesTableColumn())
                .collect(Collectors.toList());
        columnList.get(0).setText("Nazwa");
        columnList.get(1).setText("Numer NDC");
        columnList.get(2).setText("Nazwa firmy");
        columnList.get(3).setText("Typ");
        columnList.get(4).setText("Ogólny typ");
        columnList.get(5).setText("Skład");
        columnList.get(6).setText("Sposób podania");
        columnList.get(7).setText("Dawkowanie");
        columnList.get(8).setText("Rodzaj opakowania");
        return columnList;
    }

    private void setMedicinesTableContent(TableView<MedicineRecord> medicinesTable) {
        List<TableColumn<MedicineRecord, String>> columnList = createMedicinesTableColumns();
        medicinesTable.getColumns().addAll(columnList);
    }

}
