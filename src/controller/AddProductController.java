package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Inventory;
import model.Part;
import model.Product;

/** This class implements functions for the AddProduct.fxml scene. */
public class AddProductController implements Initializable {

        /** Observable list used to hold associated parts for listeners. */
        private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
        Stage stage;
        Parent scene;

        @FXML
        private TableColumn<Part, Integer> addProAddIdCol;

        @FXML
        private TableColumn<Part, Integer> addProAddInventoryCol;

        @FXML
        private TableColumn<Part, String> addProAddNameCol;

        @FXML
        private TableColumn<Part, Double> addProAddPriceCol;

        @FXML
        private TableView<Part> addProductTable;

        @FXML
        private TableColumn<Part, Double> addProPriceCol;

        @FXML
        private TableColumn<Part, Integer> addProRemoveIdCol;

        @FXML
        private TableColumn<Part, Integer> addProRemoveInventoryCol;

        @FXML
        private TableColumn<Part, String> addProRemoveNameCol;

        @FXML
        private TableView<Part> removeProductTable;

        @FXML
        private TextField addProductIdLBL;

        @FXML
        private TextField addProductInventoryLBL;

        @FXML
        private TextField addProductMaxLBL;

        @FXML
        private TextField addProductMinLBL;

        @FXML
        private TextField addProductNameLBL;

        @FXML
        private TextField addProductPriceLBL;

        @FXML
        private TextField searchPart;

        @FXML
        private Button addPartB;

        @FXML
        private Button cancelB;

        @FXML
        private Button removeAssocB;

        @FXML
        private Button saveB;

        /** This method is used to add a new product to the products table with or without any associated parts.
         * @param event when add button is clicked.
         */
        @FXML
        void addProductAddB(ActionEvent event) {

                Part associatedPart = addProductTable.getSelectionModel().getSelectedItem();

                if (associatedPart == null)
                        return;

                else if (!associatedPartsList.contains(associatedPart)) ;

                {
                        associatedPartsList.add(associatedPart);
                        removeProductTable.setItems(associatedPartsList);
                }
        }

        /** This method is used to cancel any product changes and send the user back to the main menu scene.
         * @param event when the cancel button is clicked.
         */
        @FXML
        void addProductCancelB(ActionEvent event) throws IOException {

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        /** This method is used to remove a product's associated part from the remove product table.
         * @param event when the product remove button is clicked.
         */
        @FXML
        void addProductRemoveB(ActionEvent event) {

                Part removeAssocPart = removeProductTable.getSelectionModel().getSelectedItem();

                if (removeAssocPart == null)
                        return;

                else if (associatedPartsList.contains(removeAssocPart)) ;
                {
                        associatedPartsList.remove(removeAssocPart);
                        removeProductTable.setItems(associatedPartsList);
                }
        }

        /** This method is used to search for a part by ID or by name (full or partial).
         * @param actionEvent is when the search text field is used.
         * */
        @FXML
        void searchPartName(ActionEvent actionEvent) {

                String partSearch = searchPart.getText();

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
                addProductTable.setItems(searchByPartName);
                searchPart.setText("");

                if (searchByPartName.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("No Part Was Found!");
                        alert.setContentText("Please enter a valid part!");
                        alert.showAndWait();
                        addProductTable.setItems(Inventory.getAllParts());
                }
        }

        /** This method is used to save a new product to the allProducts list in the inventory, whether or not it has associated parts.
         * @param event when the save button is clicked.
         */
        @FXML
        void addProductSaveB(ActionEvent event) throws IOException {

             try {
                     int uniqueId = (int) (Math.random() * 100000 + 10000);
                     int stock = Integer.parseInt(addProductInventoryLBL.getText());
                     String name = addProductNameLBL.getText();
                     double price = Double.parseDouble(addProductPriceLBL.getText());
                     int min = Integer.parseInt(addProductMinLBL.getText());
                     int max = Integer.parseInt(addProductMaxLBL.getText());

                     Product addProduct = new Product(uniqueId, name, price, stock, min, max);

                     for (Part part : associatedPartsList) {
                             if (part != associatedPartsList)
                                     addProduct.addAssociatedParts(part);
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

                     Inventory.getAllProducts().add(addProduct);

                     stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                     scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                     stage.setScene(new Scene(scene));
                     stage.show();
             }
             catch (NumberFormatException e) {
                     Alert alert = new Alert(Alert.AlertType.WARNING);
                     alert.setTitle("Please Review Input Information!");
                     alert.setContentText("Enter a valid Value for Each Text Field!");
                     alert.showAndWait();
             }
        }

        /** This method initializes the product tables and set all parts/products to the table.
         * @param url url
         * @param resourceBundle resource bundle
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                addProductTable.setItems(Inventory.getAllParts());
                addProAddIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                addProAddNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                addProAddPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                addProAddInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

                removeProductTable.setItems(associatedPartsList);
                addProRemoveIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                addProRemoveNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                addProPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
                addProRemoveInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        }
}











