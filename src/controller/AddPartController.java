package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.OutSourced;

/** This class implements functions for the AddPart.fxml scene. */
public class AddPartController implements Initializable {

        Stage stage;
        Parent scene;

        @FXML
        private TextField addPartAvailableStockTxt;

        @FXML
        private TextField addPartMachineIdTxt;

        @FXML
        private TextField addPartMaxTxt;

        @FXML
        private TextField addPartMinTxt;

        @FXML
        private TextField addPartNameTxt;

        @FXML
        private TextField addPartPriceUsdTxt;

        @FXML
        private TextField addPartTxt;

        @FXML
        private RadioButton inHousePartRbtn;

        @FXML
        private Label maxTxt;

        @FXML
        private RadioButton outSourcedPartRbtn;

        @FXML
        private ToggleGroup partTG;

        @FXML
        private Label inOut;

        /** This setter method is used to set the display text as Machine ID if inhouse is selected.
        * @param event when inhouse button is selected
        */
        @FXML
        void inHouseB(ActionEvent event) {

            inOut.setText("Machine ID");
        }

        /** This setter method is used to set the display text as Company Name if outsourced is selected.
         * @param event when outsource button is selected.
         */
        @FXML
        void outSourcedB(ActionEvent event) {

            inOut.setText("Company Name");
        }

        /** This method is used to cancel any added values from the add parts scene and go back to the main scene.
         * @param event when the cancel button is clicked.
         */
        @FXML
        void cancelAddPartB(ActionEvent event) throws IOException {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all inputs!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK)

            {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }

        /** This method is used to save a created part into the inventory list and then go back to the main scene.
         * @param event is when the save button is clicked.
         */
        @FXML
        void saveAddPartB(ActionEvent event) throws IOException {

            try {
                int uniqueId = (int) (Math.random() * 5050);
                String name = addPartNameTxt.getText();
                double price = Double.parseDouble(addPartPriceUsdTxt.getText());
                int stock = Integer.parseInt(addPartAvailableStockTxt.getText());
                int min = Integer.parseInt(addPartMinTxt.getText());
                int max = Integer.parseInt(addPartMaxTxt.getText());
                String compName;
                int machineId = 0;

                if (stock > max || stock < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflict With Stocking Level");
                    alert.setContentText("Please adjust the stocking level!");
                    alert.showAndWait();
                }

                else if (max < min) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Conflict With Min/Max Values");
                    alert.setContentText("Please adjust the entered min/max values!");
                    alert.showAndWait();
                }

                else {

                    if (outSourcedPartRbtn.isSelected()) {
                        compName = addPartMachineIdTxt.getText();
                        OutSourced addPart = new OutSourced(uniqueId, name, price, stock, min, max, compName);
                        Inventory.addParts(addPart);
                    }

                    else if (inHousePartRbtn.isSelected()) {
                        machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                        InHouse addPart = new InHouse(uniqueId, name, price, stock, min, max, machineId);
                        Inventory.addParts(addPart);
                    }

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }

            catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Please Review Input Information");
                    alert.setContentText("Enter a valid Value for Each Text Field!");
                    alert.showAndWait();
            }
        }

        /** This method is used to initialize.
         * @param url url
         * @param resourceBundle resource bundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        }
}
