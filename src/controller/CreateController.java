/**
 * @Title: ICT 373 A2
 * @Author: Khon Min Thite
 * @Date: 
 * @File: CreateController.java
 * @Purpose: Controller class for the create view
 * @Assumptions:
 * @Limitations:
 */

package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import model.MagazineModel;

public class CreateController {
    private MagazineModel magazineModel;

    @FXML
    private TextField magazineCost;

    @FXML
    private TextField supplementName;

    @FXML
    private TextField supplementCost;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerEmail;

    @FXML
    private Button viewButton;

    @FXML
    private Button createButton;

    @FXML
    private Button editButton;

    @FXML
    private Button createMagazineButton;

    @FXML
    private Button loadMagazineButton;

    @FXML
    private Label saveFileName;

    /**
     * Constructor
     * 
     * @param magazineModel the magazine model
     */
    public CreateController(MagazineModel magazineModel) {
        this.magazineModel = magazineModel;
    }

    /**
     * Initializes the controller class. This method is automatically called after
     * the fxml file has been loaded.
     */
    public void initialize() {
        saveFileName.setText(magazineModel.getSaveFileName());
    }

    /**
     * Handle the view button action
     */
    @FXML
    private void viewAction() {
        if (magazineModel.getMagazine() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please create or load a magazine first.");

            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new ViewController(magazineModel));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 900, 450);

                Stage stage = (Stage) viewButton.getScene().getWindow();
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handle the create button action
     */
    @FXML
    private void createAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Create.fxml"));

            // Set the controller factory
            loader.setControllerFactory(c -> new CreateController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 300, 200);

            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the edit button action
     */
    @FXML
    private void editAction() {
        if (magazineModel.getMagazine() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please create or load a magazine first.");

            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Edit.fxml"));
                // Set the controller factory
                loader.setControllerFactory(c -> new EditController(magazineModel));

                VBox nextLayout = loader.load();
                Scene nextScene = new Scene(nextLayout, 520, 400);

                Stage stage = (Stage) editButton.getScene().getWindow();
                stage.setScene(nextScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handle the create magazine button action
     */
    @FXML
    private void createMagazineAction() {
        try {
            VBox editLayout = FXMLLoader.load(getClass().getResource("/fxml/create/CreateMagazine.fxml"));
            Scene editScene = new Scene(editLayout, 310, 180);

            Stage stage = (Stage) createMagazineButton.getScene().getWindow();
            stage.setScene(editScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the load magazine button action
     */
    @FXML
    private void loadMagazineAction() {
        Stage stage = (Stage) loadMagazineButton.getScene().getWindow();
        magazineModel.loadModel(stage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
            // Set the controller factory
            loader.setControllerFactory(c -> new ViewController(magazineModel));

            VBox nextLayout = loader.load();
            Scene nextScene = new Scene(nextLayout, 900, 450);

            stage.setScene(nextScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
