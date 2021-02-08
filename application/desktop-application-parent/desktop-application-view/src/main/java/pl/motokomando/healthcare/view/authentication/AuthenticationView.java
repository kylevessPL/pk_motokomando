package pl.motokomando.healthcare.view.authentication;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.motokomando.healthcare.controller.authentication.AuthenticationController;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.authentication.AuthenticationModel;
import pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.view.base.BaseView;

import static javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.NOT_AUTHENTICATED;

public class AuthenticationView {

    private final AuthenticationModel model;
    private final AuthenticationController controller;

    private Pane loginPane;

    private Button loginButton;
    private Label authenticationStatusLabel;
    private ProgressIndicator progressIndicator;

    public AuthenticationView(AuthenticationModel model, AuthenticationController controller) {
        this.model = model;
        this.controller = controller;
        createPane();
        createContent();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return loginPane;
    }

    private Stage currentStage() {
        return (Stage) loginPane.getScene().getWindow();
    }

    private void createContent() {
        createLoginButton();
        createAuthenticationStatusLabel();
        createProgressIndicator();
    }

    private void createPane() {
        loginPane = new Pane();
        loginPane.setMaxHeight(USE_PREF_SIZE);
        loginPane.setMaxWidth(USE_PREF_SIZE);
        loginPane.setMinHeight(USE_PREF_SIZE);
        loginPane.setMinWidth(USE_PREF_SIZE);
        loginPane.setPrefHeight(638.0);
        loginPane.setPrefWidth(801.0);
    }

    private void createLoginButton() {
        loginButton = new Button();
        loginButton.setText("Zaloguj się");
        loginButton.setLayoutX(350);
        loginButton.setLayoutY(250);
        loginPane.getChildren().add(loginButton);
    }

    private void createAuthenticationStatusLabel() {
        authenticationStatusLabel = new Label();
        authenticationStatusLabel.setText(NOT_AUTHENTICATED.getDescription());
        authenticationStatusLabel.setLayoutX(350);
        authenticationStatusLabel.setLayoutY(220);
        loginPane.getChildren().add(authenticationStatusLabel);
    }

    private void createProgressIndicator() {
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setProgress(INDETERMINATE_PROGRESS);
        progressIndicator.setLayoutX(350);
        progressIndicator.setLayoutY(280);
        loginPane.getChildren().add(progressIndicator);
    }

    private void observeModelAndUpdate() {
        model.authenticationStatus().addListener((obs, oldVal, newVal) -> updateAuthenticationProgress());
    }

    private void delegateEventHandlers() {
        loginButton.setOnMouseClicked(e -> controller.handleLoginButtonClicked());
    }

    private void updateAuthenticationProgress() {
        AuthenticationStatus status = model.getAuthenticationStatus();
        switch (status) {
            case AUTHENTICATION_SUCCESS:
                Platform.runLater(() -> {
                    loginButton.setText("Zalogowano");
                    progressIndicator.setVisible(false);
                });
                openBaseStage();
                break;
            case AUTHENTICATION_FAILURE: case NOT_AUTHENTICATED:
                Platform.runLater(() -> {
                    loginButton.setText("Zaloguj się");
                    loginButton.setDisable(false);
                    progressIndicator.setVisible(false);
                });
                break;
            case AUTHENTICATION_STARTED:
                Platform.runLater(() -> {
                    loginButton.setText("Logowanie...");
                    loginButton.setDisable(true);
                });
                break;
            case USER_IDENTIFICATION:
                Platform.runLater(() -> progressIndicator.setVisible(true));
                break;
        }
        Platform.runLater(() -> authenticationStatusLabel.setText(status.getDescription()));
    }

    private void openBaseStage() {
        BaseModel baseModel = new BaseModel();
        BaseController baseController = new BaseController(baseModel);
        BaseView baseView = new BaseView(baseModel, baseController);
        Platform.runLater(() -> {
            Scene basicScene = new Scene(baseView.asParent(), 1600, 800);
            Stage stage = currentStage();
            stage.setScene(basicScene);
            stage.setTitle("Healthcare Management - Panel Główny");
            stage.show();
        });
    }

}
