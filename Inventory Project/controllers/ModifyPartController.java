package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.OutSourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import model.Part;
import javafx.scene.control.*;


/** This class implements functions for the ModifyPart.fxml scene. */
public class ModifyPartController implements Initializable {

        Stage stage;
        Parent scene;
        private int currentIndex = 0;

        @FXML
        private TextField modPartAvailableStockTxt;

        @FXML
        private TextField modPartIdTxt;

        @FXML
        private RadioButton modPartInHouseButton;

        @FXML
        private TextField modPartMachineIdTxt;

        @FXML
        private TextField modPartMaxTxt;

        @FXML
        private TextField modPartMinTxt;

        @FXML
        private TextField modPartNameTxt;

        @FXML
        private RadioButton modPartOutSourcedButton;

        @FXML
        private TextField modPartPriceTxt;

        @FXML
        private Label inOut;

        @FXML
        private ToggleGroup partTG;

        /** This setter method is used to set the display text as Machine ID if inhouse is selected.
         * @param event when inhouse is selected
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

        /** This method is used to cancel a modification in progress and send the user back to the main menu scene.
         * @param actionEvent when the cancel button is clicked.
         */
        @FXML
        public void modifyPartCancelB(ActionEvent actionEvent) throws IOException {

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        /** This method is used to save a part that had updates/modifications made to it to the inventory parts list.
         * After save, send the user back to the main menu scene where the new updates can be found on the parts table.
         * @param actionEvent when the save button is clicked.
         */
        @FXML
        public void modifyPartSaveB(ActionEvent actionEvent) throws IOException {

             try {
                     int id = Integer.parseInt(modPartIdTxt.getText());
                     String name = modPartNameTxt.getText();
                     double price = Double.parseDouble(modPartPriceTxt.getText());
                     int stock = Integer.parseInt(modPartAvailableStockTxt.getText());
                     int min = Integer.parseInt(modPartMinTxt.getText());
                     int max = Integer.parseInt(modPartMaxTxt.getText());
                     String compName;
                     int machineId = 0;

                     if (modPartInHouseButton.isSelected()) {
                             machineId = Integer.parseInt(modPartMachineIdTxt.getText());
                             InHouse updatePart = new InHouse(id, name, price, stock, min, max, machineId);
                             Inventory.updatePart(currentIndex, updatePart);
                     }
                     if (modPartOutSourcedButton.isSelected()) {
                             compName = modPartMachineIdTxt.getText();
                             OutSourced updatePart = new OutSourced(id, name, price, stock, min, max, compName);
                             Inventory.updatePart(currentIndex, updatePart);
                     }

                     if (min > max) {
                             Alert alert = new Alert(Alert.AlertType.ERROR);
                             alert.setTitle("Conflict With Min/Max Values");
                             alert.setContentText("Please adjust the entered min/max values!");
                             alert.showAndWait();
                     }

                     if (stock < min || stock > max) {
                             Alert alert = new Alert(Alert.AlertType.ERROR);
                             alert.setTitle("Conflict With Stocking Level");
                             alert.setContentText("Please adjust min/max values to be within the stocking levels!");
                             alert.showAndWait();
                     }
             }

             catch(NumberFormatException e) {

                     Alert alert = new Alert(Alert.AlertType.WARNING);
                     alert.setTitle("Please Review Input Information!");
                     alert.setContentText("Enter a valid Value for Each Text Field!");
                     alert.showAndWait();
             }

                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        /** This method obtains information from the parts table and checks whether it is an instanceOf inhouse or outsourced.
         * It then sends the information to the modify parts scene.
         * @param part part to send
         * @param selectedIndex index part list
         */
        public void sendPart(int selectedIndex, Part part) {

                modPartIdTxt.setText(String.valueOf(part.getId()));
                modPartNameTxt.setText(part.getName());
                modPartAvailableStockTxt.setText(String.valueOf(part.getStock()));
                modPartPriceTxt.setText(String.valueOf(part.getPrice()));
                modPartMaxTxt.setText(String.valueOf(part.getMax()));
                modPartMinTxt.setText(String.valueOf(part.getMin()));


                if (part instanceof InHouse) {

                        modPartInHouseButton.setSelected(true);
                        modPartMachineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
                }
                else
                {
                        modPartOutSourcedButton.setSelected(true);
                        modPartMachineIdTxt.setText(((OutSourced) part).getCompName());
                }

                currentIndex = selectedIndex;
        }

        /** This method is used to initialize.
         * @param resourceBundle resource bundle
         * @param url url
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
        }
}