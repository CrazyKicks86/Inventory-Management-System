package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class implements functions for the ModifyProduct.fxml scene. */
public class ModifyProductController implements Initializable {

        /** This is an associated parts list to hold the parts for listeners. */
        private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
        private int currentIndex = 0;
        Stage stage;
        Parent scene;
        private Product mProduct;

        @FXML
        private TableColumn<Part, Integer> modProAddIDCol;

        @FXML
        private TableColumn<Part, Integer> modProAddInventoryCol;

        @FXML
        private TableColumn<Part, String> modProAddNameCol;

        @FXML
        private TableColumn<Part, Double> modProAddPriceCol;

        @FXML
        private TableView<Part> modifyProAddTable;

        @FXML
        private TableColumn<Part, Integer> modProRemoveIdCol;

        @FXML
        private TableColumn<Part, Integer> modProRemoveInventoryCol;

        @FXML
        private TableColumn<Part, String> modProRemoveNameCol;

        @FXML
        private TableColumn<Part, Double> modProRemovePriceCol;

        @FXML
        private TableView<Part> modifyProRemoveTable;

        @FXML
        private TextField modProductIdLbl;

        @FXML
        private TextField modProductInventoryLbl;

        @FXML
        private TextField modProductMaxLbl;

        @FXML
        private TextField modProductMinLbl;

        @FXML
        private TextField modProductNameLbl;

        @FXML
        private TextField modProductPriceLbl;

        @FXML
        private TextField searchName;

        /** This method is used to add a part during a product modification. Part on the add table can be placed on the modify table.
         * @param event when the add button is clicked.
         */
        @FXML
        void modifyProAddB(ActionEvent event) {

                Part selectedPart = modifyProAddTable.getSelectionModel().getSelectedItem();

                if (selectedPart != null) {
                        associatedPartsList.add(selectedPart);
                        modifyProRemoveTable.setItems(associatedPartsList);
                }

                else { Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No Part Was Selected!");
                        alert.setContentText("Please select a valid part!");
                        alert.showAndWait();
                }
        }

        /** This method is used to cancel any changes made to a product and send the user back to the main menu scene.
         * @param event when the cancel button is clicked.
         */
        @FXML
        void modifyProCancelB(ActionEvent event) throws IOException {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This Will Not Save Any Changed Values. Do You Wish to Continue?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)

                        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
        }

        /** This method is used to remove an associated part from the associated parts table.
         * @param event when the remove part button is clicked.
         */
        @FXML
        void modifyProRemoveB(ActionEvent event) {

                Part selectedPart = modifyProRemoveTable.getSelectionModel().getSelectedItem();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Part Removal!");
                alert.setContentText("Do you wish to remove the selected part from this product?");
                alert.showAndWait();

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (selectedPart != null) {

                                mProduct.deleteAssocPart(selectedPart);
                                associatedPartsList.remove(selectedPart);
                                modifyProRemoveTable.setItems(associatedPartsList);
                        }
                }
        }

        /** This method is used to save any modified values to the allProducts list and update the associated parts list.
         * Then the user will be directed to the main menu scene.
         * @param event when the save button is clicked.
         */
        @FXML
        void modifyProSaveB(ActionEvent event) throws IOException {

             try {
                     int id = Integer.parseInt(modProductIdLbl.getText());
                     int stock = Integer.parseInt(modProductInventoryLbl.getText());
                     String name = modProductNameLbl.getText();
                     double price = Double.parseDouble(modProductPriceLbl.getText());
                     int min = Integer.parseInt(modProductMinLbl.getText());
                     int max = Integer.parseInt(modProductMaxLbl.getText());

                     Product updatedProduct = new Product(id, name, price, stock, min, max);
                     if (updatedProduct != associatedPartsList) {
                             Inventory.updatedProduct(currentIndex, updatedProduct);
                     }

                     for (Part part : associatedPartsList) {
                             if (part != associatedPartsList)
                                     updatedProduct.addAssociatedParts(part);
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

                catch(NumberFormatException e)
                     {
                             Alert alert = new Alert(Alert.AlertType.ERROR);
                             alert.setTitle("Please Review Input Information");
                             alert.setContentText("Enter a valid Value for Each Text Field!");
                             alert.showAndWait();
                     }

                     stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                     scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                     stage.setScene(new Scene(scene));
                     stage.show();
        }

        /** This is the method that is used to send the modified product data to the associated parts table and index the parts so no overlap occurs.
         * @param selectedIndex the product's indexed location
         * @param product the product selected
         * LOGICAL ERROR that I encountered was when I was trying to add new parts to the associated parts table.
         * The new part added would clear all the associated parts in table when the add button was clicked.
         * I had to implement indexing so the parts would retain their location and not be over written.
         */
        @FXML
        public void sendProduct(int selectedIndex, Product product)
        {
                currentIndex = selectedIndex;
                mProduct = product;
                modProductIdLbl.setText(String.valueOf(mProduct.getId()));
                modProductNameLbl.setText(mProduct.getName());
                modProductInventoryLbl.setText(String.valueOf(mProduct.getStock()));
                modProductPriceLbl.setText(String.valueOf(mProduct.getPrice()));
                modProductMaxLbl.setText(String.valueOf(mProduct.getMax()));
                modProductMinLbl.setText(String.valueOf(mProduct.getMin()));
                associatedPartsList = mProduct.getAllAssociatedParts();
                modifyProRemoveTable.setItems(mProduct.getAllAssociatedParts());
        }

        /** This method is used to search for a part name (full name or partial) or part Id.
         * @param actionEvent when the searched text field is used.
         */
        @FXML
        public void searchByPartName(ActionEvent actionEvent) {

                String partSearch = searchName.getText();

                if (partSearch.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No Part Was Found!");
                        alert.setContentText("Please enter a valid part!");
                        alert.showAndWait();
                }

                ObservableList<Part> searchByPartName = Inventory.lookupPart(partSearch);

                if (searchByPartName.isEmpty()) ;
                {
                        try {
                                int partId = Integer.parseInt(partSearch);
                                Part part = Inventory.lookupPart(partId);
                                searchByPartName.add(part);
                        }

                        catch (NumberFormatException e) {
                        }
                }
                modifyProAddTable.setItems(searchByPartName);
                searchName.setText("");

                if (searchByPartName.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("No Part Was Found!");
                        alert.setContentText("Please enter a valid part!");
                        alert.showAndWait();
                        modifyProAddTable.setItems(Inventory.getAllParts());
                }
        }

        /** This is the initialize method that sets allParts and associatedParts to the tables.
         * @param resourceBundle resource bundle
         * @param url url
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                modifyProAddTable.setItems(Inventory.getAllParts());
                modProAddIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                modProAddNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                modProAddPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                modProAddInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

                modifyProRemoveTable.setItems(associatedPartsList);
                modProRemoveIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                modProRemoveNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                modProRemovePriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                modProRemoveInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        }
}
