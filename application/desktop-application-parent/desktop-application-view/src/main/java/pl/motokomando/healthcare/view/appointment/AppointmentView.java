package pl.motokomando.healthcare.view.appointment;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import pl.motokomando.healthcare.controller.appointment.AppointmentController;
import pl.motokomando.healthcare.model.appointment.AppointmentModel;
import pl.motokomando.healthcare.model.appointment.utils.MedicinesTableRecord;
import pl.motokomando.healthcare.model.appointment.utils.PrescriptionMedicinesTableRecord;
import pl.motokomando.healthcare.model.appointment.utils.ProductType;
import pl.motokomando.healthcare.model.utils.ServiceStore;
import pl.motokomando.healthcare.view.appointment.utils.MedicinesTableActiveIngredientsCallback;
import pl.motokomando.healthcare.view.appointment.utils.MedicinesTableAdministrationRouteCallback;
import pl.motokomando.healthcare.view.appointment.utils.MedicinesTablePackagingVariantsCallback;
import pl.motokomando.healthcare.view.appointment.utils.PrescriptionMedicinesTableActiveIngredientsCallback;
import pl.motokomando.healthcare.view.appointment.utils.PrescriptionMedicinesTableAdministrationRouteCallback;
import pl.motokomando.healthcare.view.appointment.utils.PrescriptionMedicinesTablePackagingVariantsCallback;
import utils.FXAlert;
import utils.FXTasks;
import utils.FXValidation;
import utils.TextAreaLimiter;
import utils.TextFieldLimiter;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;
import static javafx.scene.control.ButtonType.OK;
import static javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS;
import static javafx.scene.control.SelectionMode.MULTIPLE;
import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class AppointmentView {

    private final ServiceStore serviceStore = ServiceStore.getInstance();

    private final ValidationSupport notesValidationSupport = new ValidationSupport();
    private final ValidationSupport billAmountValidationSupport = new ValidationSupport();

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
    private TableView<PrescriptionMedicinesTableRecord> prescriptionMedicinesTable;
    private TextField medicineSearchQueryTextField;
    private Button searchMedicineToPrescriptionButton;
    private ComboBox<String> chooseDoctorComboBox;
    private Label chooseDoctorLabel;
    private Button saveDoctorDetailsButton;
    private TextField billAmountTextField;
    private Label billAmountLabel;
    private Button saveBillAmountButton;
    private Button saveNotesButton;
    private TextArea notesTextArea;
    private ImageView imageViewLogo;
    private TextField medicineExpiryDateTextField;
    private TextField medicineDateOfIssueTextField;
    private Label medicineExpiryDateLabel;
    private Label medicineDateOfIssueLabel;

    public AppointmentView(Integer patientId, Integer appointmentId) {
        initModel(patientId, appointmentId);
        setController();
        createPane();
        addContent();
        setupValidation();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return appointmentPane;
    }

    private Stage currentStage() {
        return (Stage) appointmentPane.getScene().getWindow();
    }

    private void initModel(Integer patientId, Integer appointmentId) {
        model = new AppointmentModel(patientId, appointmentId);
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
        appointmentPane.setPrefWidth(1400.0);
        appointmentPane.setTabClosingPolicy(UNAVAILABLE);
    }

    private void addContent() {
        createPrescriptionTab();
        createDoctorTab();
        createPrescriptionNotesTab();
        createBillTab();
        getAppointmentDetails();
    }

    private void createBillTab() {
        billTab = new Tab();
        billTab.setText("Rachunek");
        billPane = new AnchorPane();
        billPane.setPrefHeight(200.0);
        billPane.setPrefWidth(200.0);
        createLogoImage(billPane);
        createBillAmountTextField();
        createBillAmountLabel();
        createSaveBillDetailsButton();
        billTab.setContent(billPane);
        appointmentPane.getTabs().add(billTab);
    }

    private void createLogoImage(AnchorPane backgroundPane) {
        Image logoImage = new Image(this.getClass().getResourceAsStream("/images/logo.png"));
        imageViewLogo = new ImageView(logoImage);
        imageViewLogo.setLayoutX(50);
        imageViewLogo.setLayoutY(160);
        imageViewLogo.setFitHeight(325);
        imageViewLogo.setFitWidth(1300);
        imageViewLogo.setOpacity(0.3);
        backgroundPane.getChildren().add(imageViewLogo);
    }

    private void createSaveBillDetailsButton() {
        saveBillAmountButton = new Button();
        saveBillAmountButton.setDisable(true);
        saveBillAmountButton.setLayoutX(655.0);
        saveBillAmountButton.setLayoutY(400.0);
        saveBillAmountButton.setMnemonicParsing(false);
        saveBillAmountButton.setText("Zatwierdź");
        saveBillAmountButton.setFont(new Font(16.0));
        billPane.getChildren().add(saveBillAmountButton);
    }

    private void createBillAmountLabel() {
        billAmountLabel = new Label();
        billAmountLabel.setLayoutX(600.0);
        billAmountLabel.setLayoutY(160.0);
        billAmountLabel.setText("Kwota (PLN):");
        billAmountLabel.setFont(new Font(16.0));
        billPane.getChildren().add(billAmountLabel);
    }

    private void createBillAmountTextField() {
        billAmountTextField = new TextField();
        billAmountTextField.setLayoutX(600.0);
        billAmountTextField.setLayoutY(190.0);
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
        saveNotesButton = new Button();
        saveNotesButton.setDisable(true);
        saveNotesButton.setLayoutX(575.0);
        saveNotesButton.setLayoutY(700.0);
        saveNotesButton.setMnemonicParsing(false);
        saveNotesButton.setText("Zapisz");
        saveNotesButton.setFont(new Font(16.0));
        prescriptionNotesPane.getChildren().add(saveNotesButton);
    }

    private void createDiagnosisTextArea() {
        notesTextArea = new TextArea();
        notesTextArea.setLayoutX(50.0);
        notesTextArea.setLayoutY(50.0);
        notesTextArea.setPrefHeight(500.0);
        notesTextArea.setPrefWidth(1300.0);
        notesTextArea.setPromptText("Dolegliwości, badania, zalecenia");
        prescriptionNotesPane.getChildren().add(notesTextArea);
    }

    private void createDoctorTab() {
        doctorTab = new Tab();
        doctorTab.setText("Lekarz");
        doctorPane = new AnchorPane();
        doctorPane.setPrefHeight(180.0);
        doctorPane.setPrefWidth(200.0);
        createLogoImage(doctorPane);
        createChooseDoctorComboBox();
        createChooseDoctorLabel();
        createSaveDoctorDetailsButton();
        doctorTab.setContent(doctorPane);
        appointmentPane.getTabs().add(doctorTab);
    }

    private void createSaveDoctorDetailsButton() {
        saveDoctorDetailsButton = new Button();
        saveDoctorDetailsButton.setLayoutX(655.0);
        saveDoctorDetailsButton.setLayoutY(400.0);
        saveDoctorDetailsButton.setMnemonicParsing(false);
        saveDoctorDetailsButton.setText("Zatwierdź");
        saveDoctorDetailsButton.setFont(new Font(16.0));
        doctorPane.getChildren().add(saveDoctorDetailsButton);
    }

    private void createChooseDoctorLabel() {
        chooseDoctorLabel = new Label();
        chooseDoctorLabel.setLayoutX(550.0);
        chooseDoctorLabel.setLayoutY(160.0);
        chooseDoctorLabel.setText("Wybierz lekarza:");
        chooseDoctorLabel.setFont(new Font(16.0));
        doctorPane.getChildren().add(chooseDoctorLabel);
    }

    private void createChooseDoctorComboBox() {
        chooseDoctorComboBox = new ComboBox<>();
        chooseDoctorComboBox.setLayoutX(550.0);
        chooseDoctorComboBox.setLayoutY(190.0);
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
        createMedicineExpiryDateTextField();
        createMedicineExpiryDateLabel();
        createMedicineDateOfIssueTextField();
        createMedicineDateOfIssueLabel();
        prescriptionMedicinesTab.setContent(prescriptionMedicinesPane);
        prescriptionPane.getTabs().add(prescriptionMedicinesTab);
    }

    private void createMedicineExpiryDateLabel() {
        medicineExpiryDateLabel = new Label();
        medicineExpiryDateLabel.setLayoutX(1000.0);
        medicineExpiryDateLabel.setLayoutY(540.0);
        medicineExpiryDateLabel.setText("Data ważności");
        prescriptionMedicinesPane.getChildren().add(medicineExpiryDateLabel);
    }

    private void createMedicineDateOfIssueLabel() {
        medicineDateOfIssueLabel = new Label();
        medicineDateOfIssueLabel.setLayoutX(1200.0);
        medicineDateOfIssueLabel.setLayoutY(540.0);
        medicineDateOfIssueLabel.setText("Data wystawienia");
        prescriptionMedicinesPane.getChildren().add(medicineDateOfIssueLabel);
    }

    private void createMedicineExpiryDateTextField() {
        medicineExpiryDateTextField = new TextField();
        medicineExpiryDateTextField.setLayoutX(1000.0);
        medicineExpiryDateTextField.setLayoutY(570.0);
        medicineExpiryDateTextField.setPrefHeight(40.0);
        medicineExpiryDateTextField.setPrefWidth(150.0);
        medicineExpiryDateTextField.setPromptText("Data ważności");
        prescriptionMedicinesPane.getChildren().add(medicineExpiryDateTextField);
    }

    private void createMedicineDateOfIssueTextField() {
        medicineDateOfIssueTextField = new TextField();
        medicineDateOfIssueTextField.setLayoutX(1200.0);
        medicineDateOfIssueTextField.setLayoutY(570.0);
        medicineDateOfIssueTextField.setPrefHeight(40.0);
        medicineDateOfIssueTextField.setPrefWidth(150.0);
        medicineDateOfIssueTextField.setPromptText("Data wystawienia");
        prescriptionMedicinesPane.getChildren().add(medicineDateOfIssueTextField);
    }

    private void createMedicinesTable() {
        medicinesTable = new TableView<>();
        medicinesTable.setLayoutX(50.0);
        medicinesTable.setLayoutY(99.0);
        medicinesTable.setPrefHeight(450.0);
        medicinesTable.setPrefWidth(1300.0);
        medicinesTable.getSelectionModel().setSelectionMode(MULTIPLE);
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
        createSearchMedicineToPrescriptionButton();
        addPrescriptionMedicineTab.setContent(addPrescriptionMedicinePane);
        prescriptionPane.getTabs().add(addPrescriptionMedicineTab);
    }

    private void createSearchMedicineToPrescriptionButton(){
        searchMedicineToPrescriptionButton = new Button();
        searchMedicineToPrescriptionButton.setLayoutX(1280.0);
        searchMedicineToPrescriptionButton.setLayoutY(40.0);
        searchMedicineToPrescriptionButton.setMnemonicParsing(false);
        searchMedicineToPrescriptionButton.setText("Szukaj");
        searchMedicineToPrescriptionButton.setFont(new Font(16.0));
        addPrescriptionMedicinePane.getChildren().add(searchMedicineToPrescriptionButton);
    }

    private void createMedicineNameTextField() {
        medicineSearchQueryTextField = new TextField();
        medicineSearchQueryTextField.setLayoutX(50.0);
        medicineSearchQueryTextField.setLayoutY(40.0);
        medicineSearchQueryTextField.setPrefHeight(40.0);
        medicineSearchQueryTextField.setPrefWidth(1220.0);
        medicineSearchQueryTextField.setPromptText("Wprowadź nazwę leku");
        addPrescriptionMedicinePane.getChildren().add(medicineSearchQueryTextField);
    }

    private void createPrescriptionMedicinesTable() {
        prescriptionMedicinesTable = new TableView<>();
        prescriptionMedicinesTable.setLayoutX(50.0);
        prescriptionMedicinesTable.setLayoutY(50.0);
        prescriptionMedicinesTable.setPrefHeight(480);
        prescriptionMedicinesTable.setPrefWidth(1300.0);
        prescriptionMedicinesTable.getSelectionModel().setSelectionMode(MULTIPLE);
        prescriptionMedicinesTable.setPlaceholder(new Label("Brak leków powiązanych z receptą"));
        setPrescriptionMedicinesTableColumns(prescriptionMedicinesTable);
        prescriptionMedicinesTable.setItems(model.prescriptionMedicinesTableContent());
        prescriptionMedicinesPane.getChildren().add(prescriptionMedicinesTable);
    }

    private <T> TableColumn<MedicinesTableRecord, T> createMedicinesTableColumn() {
        TableColumn<MedicinesTableRecord, T> column = new TableColumn<>();
        column.setPrefWidth(144.0);
        return column;
    }

    private <T> TableColumn<PrescriptionMedicinesTableRecord, T> createPrescriptionMedicinesTableColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, T> column = new TableColumn<>();
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

    private void setPrescriptionMedicinesTableColumns(TableView<PrescriptionMedicinesTableRecord> medicinesTable) {
        TableColumn<PrescriptionMedicinesTableRecord, String> column1 = createPrescriptionMedicinesTableFirstColumn();
        TableColumn<PrescriptionMedicinesTableRecord, String> column2 = createPrescriptionMedicinesTableSecondColumn();
        TableColumn<PrescriptionMedicinesTableRecord, String> column3 = createPrescriptionMedicinesTableThirdColumn();
        TableColumn<PrescriptionMedicinesTableRecord, ProductType> column4 = createPrescriptionMedicinesTableFourthColumn();
        TableColumn<PrescriptionMedicinesTableRecord, String> column5 = createPrescriptionMedicinesTableFifthColumn();
        TableColumn<PrescriptionMedicinesTableRecord, Void> column6 = createPrescriptionMedicinesTableSixthColumn();
        TableColumn<PrescriptionMedicinesTableRecord, Void> column7 = createPrescriptionMedicinesTableSeventhColumn();
        TableColumn<PrescriptionMedicinesTableRecord, String> column8 = createPrescriptionMedicinesTableEigthColumn();
        TableColumn<PrescriptionMedicinesTableRecord, Void> column9 = createPrescriptionMedicinesTableNinthColumn();
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
        column9.setCellFactory(new MedicinesTablePackagingVariantsCallback(appointmentPane));
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
        column7.setCellFactory(new MedicinesTableAdministrationRouteCallback(appointmentPane));
        return column7;
    }

    private TableColumn<MedicinesTableRecord, Void> createMedicinesTableSixthColumn() {
        TableColumn<MedicinesTableRecord, Void> column6 = createMedicinesTableColumn();
        column6.setText("Skład");
        column6.setCellFactory(new MedicinesTableActiveIngredientsCallback(appointmentPane));
        return column6;
    }

    private TableColumn<MedicinesTableRecord, String> createMedicinesTableFifthColumn() {
        TableColumn<MedicinesTableRecord, String> column5 = createMedicinesTableColumn();
        column5.setText("Rodzaj");
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

    private TableColumn<PrescriptionMedicinesTableRecord, Void> createPrescriptionMedicinesTableNinthColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, Void> column9 = createPrescriptionMedicinesTableColumn();
        column9.setText("Rodzaj opakowania");
        column9.setCellFactory(new PrescriptionMedicinesTablePackagingVariantsCallback(appointmentPane));
        return column9;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, String> createPrescriptionMedicinesTableEigthColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, String> column8 = createPrescriptionMedicinesTableColumn();
        column8.setText("Dawkowanie");
        column8.setStyle("-fx-alignment: center;");
        column8.setCellValueFactory(c -> c.getValue().getRecord().dosageForm());
        return column8;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, Void> createPrescriptionMedicinesTableSeventhColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, Void> column7 = createPrescriptionMedicinesTableColumn();
        column7.setText("Sposób podania");
        column7.setCellFactory(new PrescriptionMedicinesTableAdministrationRouteCallback(appointmentPane));
        return column7;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, Void> createPrescriptionMedicinesTableSixthColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, Void> column6 = createPrescriptionMedicinesTableColumn();
        column6.setText("Skład");
        column6.setCellFactory(new PrescriptionMedicinesTableActiveIngredientsCallback(appointmentPane));
        return column6;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, String> createPrescriptionMedicinesTableFifthColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, String> column5 = createPrescriptionMedicinesTableColumn();
        column5.setText("Rodzaj");
        column5.setStyle("-fx-alignment: center-left;");
        column5.setCellValueFactory(c -> c.getValue().getRecord().genericName());
        return column5;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, ProductType> createPrescriptionMedicinesTableFourthColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, ProductType> column4 = createPrescriptionMedicinesTableColumn();
        column4.setText("Typ");
        column4.setStyle("-fx-alignment: center;");
        column4.setCellValueFactory(c -> c.getValue().getRecord().productType());
        return column4;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, String> createPrescriptionMedicinesTableThirdColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, String> column3 = createPrescriptionMedicinesTableColumn();
        column3.setText("Nazwa firmy");
        column3.setStyle("-fx-alignment: center-left;");
        column3.setCellValueFactory(c -> c.getValue().getRecord().manufacturer());
        return column3;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, String> createPrescriptionMedicinesTableSecondColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, String> column2 = createPrescriptionMedicinesTableColumn();
        column2.setText("Numer NDC");
        column2.setStyle("-fx-alignment: center;");
        column2.setCellValueFactory(c -> c.getValue().getRecord().productNDC());
        return column2;
    }

    private TableColumn<PrescriptionMedicinesTableRecord, String> createPrescriptionMedicinesTableFirstColumn() {
        TableColumn<PrescriptionMedicinesTableRecord, String> column1 = createPrescriptionMedicinesTableColumn();
        column1.setText("Nazwa");
        column1.setStyle("-fx-alignment: center-left;");
        column1.setCellValueFactory(c -> c.getValue().getRecord().productName());
        return column1;
    }

    private void observeModelAndUpdate() {
        model.prescriptionNotes().addListener((obs, oldValue, newValue) -> Platform.runLater(() ->
                notesTextArea.setText(newValue)));
        model.billAmount().addListener((obs, oldValue, newValue) -> Platform.runLater(() ->
                billAmountTextField.setText(newValue)));
    }

    private void delegateEventHandlers() {
        setTextFieldsLimit();
        notesValidationSupport.validationResultProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(this::switchSaveNotesButtonState));
        billAmountValidationSupport.validationResultProperty().addListener((obs, oldValue, newValue) ->
                Platform.runLater(this::switchSaveBillAmountButtonState));
        medicineSearchQueryTextField.setOnKeyPressed(this::medicineSearchQueryTextFieldEnterPressed);
        medicinesTable.setRowFactory(param -> createMedicinesTableContextMenu());
        prescriptionMedicinesTable.setRowFactory(param -> createPrescriptionMedicinesTableContextMenu());
        saveNotesButton.setOnMouseClicked(e -> updateNotes());
        saveBillAmountButton.setOnMouseClicked(e -> updateBillAmount());
    }

    private void updateNotes() {
        Platform.runLater(() -> saveNotesButton.setDisable(true));
        Task<Void> task = FXTasks.createTask(() ->
                controller.handleUpdatePrescriptionNotesButtonClicked(notesTextArea.getText()));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> processUpdatePrescriptionSuccessResult());
        task.setOnFailed(e -> processUpdatePrescriptionFailureResult(task.getException().getMessage()));
    }

    private void updateBillAmount() {
        Platform.runLater(() -> saveBillAmountButton.setDisable(true));
        Task<Void> task = FXTasks.createTask(() ->
                controller.handleUpdateBillAmountButtonClicked(billAmountTextField.getText()));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> processUpdatePrescriptionSuccessResult());
        task.setOnFailed(e -> processUpdatePrescriptionFailureResult(task.getException().getMessage()));
    }

    private void switchSaveBillAmountButtonState() {
        saveBillAmountButton.setDisable(billAmountValidationSupport.isInvalid() ||
                new BigDecimal(billAmountTextField.getText())
                        .compareTo(new BigDecimal(model.getBillAmount())) == 0);
    }

    private void switchSaveNotesButtonState() {
        saveNotesButton.setDisable(notesValidationSupport.isInvalid() ||
                notesTextArea.getText().equals(model.getPrescriptionNotes()));
    }

    private void setTextFieldsLimit() {
        billAmountTextField.textProperty().addListener(new TextFieldLimiter(12));
        notesTextArea.textProperty().addListener(new TextAreaLimiter(500));
    }

    private void setupValidation() {
        setNotesTextAreaValidator();
        setBillAmountTextFieldValidator();
    }

    private void setNotesTextAreaValidator() {
        final String fieldName = "notatki";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> rangeValidator = FXValidation.createMinLengthValidator(fieldName, 5);
        notesValidationSupport.registerValidator(
                notesTextArea,
                true,
                Validator.combine(emptyValidator, rangeValidator));
    }

    private void setBillAmountTextFieldValidator() {
        final String fieldName = "kwota";
        Validator<String> emptyValidator = FXValidation.createEmptyValidator(fieldName);
        Validator<String> regexValidator = FXValidation.createRegexValidator(fieldName, Pattern.compile("^\\d+(.\\d{1,2})?$"));
        billAmountValidationSupport.registerValidator(
                billAmountTextField,
                true,
                Validator.combine(emptyValidator, regexValidator));
    }

    private TableRow<MedicinesTableRecord> createMedicinesTableContextMenu() {
        final TableRow<MedicinesTableRecord> row = new TableRow<>();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem menuItem = new MenuItem("Dodaj do recepty");
        menuItem.setOnAction(event -> addPrescriptionMedicine());
        contextMenu.getItems().add(menuItem);
        row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(contextMenu)
        );
        return row;
    }

    private void addPrescriptionMedicine() {
        List<String> medicineList = medicinesTable.getSelectionModel().getSelectedCells()
                .stream()
                .map(e -> medicinesTable.getItems().get(e.getRow()).productNDC().get())
                .collect(Collectors.toList());
        Task<Void> task = FXTasks.createTask(() -> controller.handleAddPrescriptionMedicines(medicineList));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> {
            processUpdatePrescriptionSuccessResult();
            updatePrescriptionMedicinesTable();
        });
        task.setOnFailed(e -> processUpdatePrescriptionFailureResult(task.getException().getMessage()));
    }

    private void removePrescriptionMedicine() {
        List<Integer> medicineList = prescriptionMedicinesTable.getSelectionModel().getSelectedCells()
                .stream()
                .map(e -> prescriptionMedicinesTable.getItems().get(e.getRow()).getMedicineId())
                .collect(Collectors.toList());
        Task<Void> task = FXTasks.createTask(() -> controller.handleRemovePrescriptionMedicines(medicineList));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> {
            processUpdatePrescriptionSuccessResult();
            updatePrescriptionMedicinesTable();
        });
        task.setOnFailed(e -> processUpdatePrescriptionFailureResult(task.getException().getMessage()));
    }

    private void updatePrescriptionMedicinesTable() {
        Task<Void> task = FXTasks.createTask(() -> controller.updatePrescriptionMedicinesTableContent());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnRunning(e -> {
            ProgressIndicator progressIndicator = new ProgressIndicator(INDETERMINATE_PROGRESS);
            progressIndicator.setMaxSize(40, 40);
            prescriptionMedicinesTable.setPlaceholder(progressIndicator);
        });
        task.setOnSucceeded(e -> prescriptionMedicinesTable.setPlaceholder(new Label("Brak leków powiązanych z receptą")));
        task.setOnFailed(e -> processUpdatePrescriptionMedicinesTableFailureResult(task.getException().getMessage()));
    }

    private void processUpdatePrescriptionMedicinesTableFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(WARNING)
                .alertTitle("Nie udało się pobrać danych leków na recepcie")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    private void processUpdatePrescriptionFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(ERROR)
                .alertTitle("Nie udało się zaktualizować danych recepty")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    private void processUpdatePrescriptionSuccessResult() {
        Alert alert = FXAlert.builder()
                .alertType(INFORMATION)
                .alertTitle("Operacja ukończona pomyślnie")
                .contentText("Pomyślnie zaktualizowano dane recepty")
                .owner(currentStage())
                .build();
        Platform.runLater(alert::showAndWait);
    }

    private TableRow<PrescriptionMedicinesTableRecord> createPrescriptionMedicinesTableContextMenu() {
        final TableRow<PrescriptionMedicinesTableRecord> row = new TableRow<>();
        final ContextMenu contextMenu = new ContextMenu();
        final MenuItem menuItem = new MenuItem("Usuń z recepty");
        menuItem.setOnAction(event -> removePrescriptionMedicine());
        contextMenu.getItems().add(menuItem);
        row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu) null)
                        .otherwise(contextMenu)
        );
        return row;
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

    private void getAppointmentDetailsFailureResult(String errorMessage) {
        Alert alert = FXAlert.builder()
                .alertType(ERROR)
                .alertTitle("Nie udało się pobrać szczegółów wizyty")
                .contentText(errorMessage)
                .owner(currentStage())
                .build();
        Platform.runLater(() -> alert.showAndWait()
                .filter(OK::equals)
                .ifPresent(e -> currentStage().close()));
    }

    private void getAppointmentDetails() {
        Task<Void> task = FXTasks.createTask(() -> controller.getAppointmentDetails());
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        task.setOnSucceeded(e -> updatePrescriptionMedicinesTable());
        task.setOnFailed(e -> getAppointmentDetailsFailureResult(task.getException().getMessage()));
    }

}
