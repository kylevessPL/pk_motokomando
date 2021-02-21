package pl.motokomando.healthcare.view.doctor;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import pl.motokomando.healthcare.controller.patient.PatientController;
import pl.motokomando.healthcare.model.base.utils.AcademicTitle;
import pl.motokomando.healthcare.model.base.utils.MedicalSpecialty;
import pl.motokomando.healthcare.model.patient.PatientModel;
import pl.motokomando.healthcare.model.utils.ServiceStore;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DoctorView {

    private final ServiceStore serviceStore = ServiceStore.getInstance();

    private final ValidationSupport updatePatientDetailsValidationSupport = new ValidationSupport();

    private PatientController controller;

    private PatientModel model;

    private AnchorPane doctorDetailsPane;
    private TextField doctorFirstNameTextField;
    private TextField doctorLastNameTextField;
    private TextField doctorPhoneNumberTextField;
    private Label doctorFirstNameLabel;
    private Label doctorLastNameLabel;
    private Label doctorPhoneNumberLabel;
    private ComboBox<String> chooseDoctorAcademicTitleComboBox;
    private Label doctorAcademicTitleLabel;
    private ComboBox<String> chooseDoctorMedicalSpecialtyComboBox;
    private Label doctorMedicalSpecialtyLabel;
    private Button unlockUpdatePatientDetailsButton;
    private Button updatePatientDetailsButton;
    private ImageView imageViewLogo;

    public DoctorView() {
        //initModel(patientId);
        setController();
        addContent();
    }

    public Parent asParent() {
        return doctorDetailsPane;
    }

    private Stage currentStage() {
        return (Stage) doctorDetailsPane.getScene().getWindow();
    }

    private void initModel(Integer patientId) {
        model = new PatientModel(patientId);
    }

    private void setController() {
        controller = new PatientController(model);
    }

    private void addContent() {
        createDoctorDetailsAnchorPane();
    }

    private void createDoctorDetailsAnchorPane() {
        doctorDetailsPane = new AnchorPane();
        doctorDetailsPane.setPrefHeight(600.0);
        doctorDetailsPane.setPrefWidth(900.0);
        createLogoImage();
        createDoctorFirstNameTextField();
        createDoctorPhoneNumberTextField();
        createDoctorLastNameTextField();
        createChooseDoctorAcademicTitleComboBox();
        createDoctorAcademicTitleLabel();
        createChooseDoctorMedicalSpecialtyComboBox();
        createDoctorMedicalSpecialtyLabel();
        createDoctorFirstNameLabel();
        createDoctorLastNameLabel();
        createDoctorPhoneNumberLabel();
        createUpdateDoctorDetailsButton();
        createUnlockUpdateDoctorDetailsButton();
    }

    private void createLogoImage() {
        Image logoImage = new Image(this.getClass().getResourceAsStream("/images/logo.png"));
        imageViewLogo = new ImageView(logoImage);
        imageViewLogo.setLayoutX(50);
        imageViewLogo.setLayoutY(200);
        imageViewLogo.setFitHeight(200);
        imageViewLogo.setFitWidth(800);
        imageViewLogo.setOpacity(0.3);
        doctorDetailsPane.getChildren().add(imageViewLogo);
    }

    private void createUnlockUpdateDoctorDetailsButton() {
        unlockUpdatePatientDetailsButton = new Button();
        unlockUpdatePatientDetailsButton.setLayoutX(480.0);
        unlockUpdatePatientDetailsButton.setLayoutY(436.0);
        unlockUpdatePatientDetailsButton.setMnemonicParsing(false);
        unlockUpdatePatientDetailsButton.setText("Edytuj");
        doctorDetailsPane.getChildren().add(unlockUpdatePatientDetailsButton);
    }

    private void createUpdateDoctorDetailsButton() {
        updatePatientDetailsButton = new Button();
        updatePatientDetailsButton.setLayoutX(360.0);
        updatePatientDetailsButton.setLayoutY(436.0);
        updatePatientDetailsButton.setMnemonicParsing(false);
        updatePatientDetailsButton.setText("Zaktualizuj");
        updatePatientDetailsButton.setDisable(true);
        doctorDetailsPane.getChildren().add(updatePatientDetailsButton);
    }

    private void createDoctorMedicalSpecialtyLabel() {
        doctorMedicalSpecialtyLabel = new Label();
        doctorMedicalSpecialtyLabel.setLayoutX(550.0);
        doctorMedicalSpecialtyLabel.setLayoutY(155.0);
        doctorMedicalSpecialtyLabel.setText("Specjalizacja");
        doctorDetailsPane.getChildren().add(doctorMedicalSpecialtyLabel);
    }

    private void createChooseDoctorMedicalSpecialtyComboBox() {
        chooseDoctorMedicalSpecialtyComboBox = new ComboBox<>();
        chooseDoctorMedicalSpecialtyComboBox.setLayoutX(550.0);
        chooseDoctorMedicalSpecialtyComboBox.setLayoutY(185.0);
        chooseDoctorMedicalSpecialtyComboBox.setPrefHeight(30.0);
        chooseDoctorMedicalSpecialtyComboBox.setPrefWidth(200.0);
        chooseDoctorMedicalSpecialtyComboBox.setPromptText("Wybierz specjalizację");
        chooseDoctorMedicalSpecialtyComboBox.getItems().setAll(Arrays
                .stream(MedicalSpecialty.values())
                .map(MedicalSpecialty::getName)
                .collect(Collectors.toList()));
        doctorDetailsPane.getChildren().add(chooseDoctorMedicalSpecialtyComboBox);
    }

    private void createDoctorAcademicTitleLabel() {
        doctorAcademicTitleLabel = new Label();
        doctorAcademicTitleLabel.setLayoutX(150.0);
        doctorAcademicTitleLabel.setLayoutY(190.0);
        doctorAcademicTitleLabel.setText("Tytuł naukowy");
        doctorDetailsPane.getChildren().add(doctorAcademicTitleLabel);
    }

    private void createDoctorFirstNameLabel() {
        doctorFirstNameLabel = new Label();
        doctorFirstNameLabel.setLayoutX(150.0);
        doctorFirstNameLabel.setLayoutY(30.0);
        doctorFirstNameLabel.setText("Imię");
        doctorDetailsPane.getChildren().add(doctorFirstNameLabel);
    }

    private void createDoctorLastNameLabel() {
        doctorLastNameLabel = new Label();
        doctorLastNameLabel.setLayoutX(150.0);
        doctorLastNameLabel.setLayoutY(110.0);
        doctorLastNameLabel.setText("Nazwisko");
        doctorDetailsPane.getChildren().add(doctorLastNameLabel);
    }
    private void createDoctorPhoneNumberLabel() {
        doctorPhoneNumberLabel = new Label();
        doctorPhoneNumberLabel.setLayoutX(550.0);
        doctorPhoneNumberLabel.setLayoutY(65.0);
        doctorPhoneNumberLabel.setText("Numer telefonu");
        doctorDetailsPane.getChildren().add(doctorPhoneNumberLabel);
    }

    private void createChooseDoctorAcademicTitleComboBox() {
        chooseDoctorAcademicTitleComboBox = new ComboBox<>();
        chooseDoctorAcademicTitleComboBox.setLayoutX(150.0);
        chooseDoctorAcademicTitleComboBox.setLayoutY(220.0);
        chooseDoctorAcademicTitleComboBox.setPrefHeight(30.0);
        chooseDoctorAcademicTitleComboBox.setPrefWidth(200.0);
        chooseDoctorAcademicTitleComboBox.setPromptText("Wybierz tytuł");
        chooseDoctorAcademicTitleComboBox.getItems().setAll(Arrays
                .stream(AcademicTitle.values())
                .map(AcademicTitle::getName)
                .collect(Collectors.toList()));
        doctorDetailsPane.getChildren().add(chooseDoctorAcademicTitleComboBox);
    }

    private void createDoctorLastNameTextField() {
        doctorLastNameTextField = new TextField();
        doctorLastNameTextField.setLayoutX(150.0);
        doctorLastNameTextField.setLayoutY(140.0);
        doctorLastNameTextField.setPrefHeight(30.0);
        doctorLastNameTextField.setPrefWidth(200.0);
        doctorLastNameTextField.setPromptText("Podaj nazwisko");
        doctorDetailsPane.getChildren().add(doctorLastNameTextField);
    }

    private void createDoctorPhoneNumberTextField() {
        doctorPhoneNumberTextField = new TextField();
        doctorPhoneNumberTextField.setLayoutX(550.0);
        doctorPhoneNumberTextField.setLayoutY(95.0);
        doctorPhoneNumberTextField.setPrefHeight(30.0);
        doctorPhoneNumberTextField.setPrefWidth(200.0);
        doctorPhoneNumberTextField.setPromptText("Podaj numer");
        doctorDetailsPane.getChildren().add(doctorPhoneNumberTextField);
    }

    private void createDoctorFirstNameTextField() {
        doctorFirstNameTextField = new TextField();
        doctorFirstNameTextField.setLayoutX(150.0);
        doctorFirstNameTextField.setLayoutY(60.0);
        doctorFirstNameTextField.setPrefHeight(30.0);
        doctorFirstNameTextField.setPrefWidth(200.0);
        doctorFirstNameTextField.setPromptText("Podaj imię");
        doctorDetailsPane.getChildren().add(doctorFirstNameTextField);
    }
}