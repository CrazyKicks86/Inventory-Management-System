package controller;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import model.Part;
import model.Product;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;

/** This class implements functions for the MainMenu.fxml scene. */
public class MainMenuController implements Initializable {

        Stage stage;
        Parent scene;

        @FXML
        private Button addPartB;

        @FXML
        private Button addProB;

        @FXML
        private Button deletePartB;

        @FXML
        private Button deleteProB;

        @FXML
        private Button exitButton;

        @FXML
        private Button modifyPartB;

        @FXML
        private Button modifyProB;

        @FXML
        private TextField searchParts;

        @FXML
        private TextField searchProducts;

        @FXML
        private TableColumn<Part, Integer> partIDColumn;

        @FXML
        private TableColumn<Part, Integer> partInventoryLevelColumn;

        @FXML
        private TableColumn<Part, String> partNameColumn;

        @FXML
        private TableColumn<Part, Double> partPriceColumn;

        @FXML
        private TableView<Part> partsTable;

        @FXML
        private TableColumn<Product, Integer> productIdColumn;

        @FXML
        private TableColumn<Product, Integer> productInventoryColumn;

        @FXML
        private TableColumn<Product, String> productNameColumn;

        @FXML
        private TableColumn<Product, Double> productPriceColumn;

        @FXML
        private TableView<Product> productsTable;

        /** This method is used to exit the stage when the exit button is clicked. */
        @FXML
        void onActionExitB(ActionEvent event) {
                System.exit(0);
        }

        /** This method retrieves the AddPart.fxml scene */
        @FXML
        void onAddPartB(ActionEvent event) throws IOException {

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        /** This method retrieves the AddProduct.fxml scene
         * @param event is the add button being clicked.
         */
        @FXML
        void onAddProdB(ActionEvent event) throws IOException {

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
        }

        /** This method is used to delete a part from the parts table (allParts list).
         * @param event is the delete button being clicked.
         */
        @FXML
        void onDeletePartB(ActionEvent event) {

                Part part = partsTable.getSelectionModel().getSelectedItem();

                if (partsTable.getSelectionModel().getSelectedItem() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("No Part Was Selected!");
                        alert.setContentText("Please select the part you wish to delete!");
                        alert.showAndWait();
                }
                else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Part?");
                        alert.setContentText("Selected part will be removed from the inventory.  Do you wish to continue?");
                        alert.showAndWait();

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                                Inventory.deletePart(part);
                        }
                }
        }

        /** This method is used to delete a product on the products table (allProducts list).
         * @param event is when the delete button is clicked.
         */
        @FXML
        void onDeleteProB(ActionEvent event) {

                Product product = productsTable.getSelectionModel().getSelectedItem();

                if (productsTable.getSelectionModel().getSelectedItem() == null) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("No Product Was Selected!");
                        alert.setContentText("Please select the product you wish to delete!");
                        alert.showAndWait();
                }
                else {  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete Product?");
                        alert.setContentText("Selected product will be removed.  Do you wish to continue?");
                        alert.showAndWait();

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                                if (product.getAllAssociatedParts().size() >= 1) {
                                        Alert noDeleteAlert = new Alert(Alert.AlertType.ERROR);
                                        noDeleteAlert.setTitle("Associated Parts Conflict");
                                        noDeleteAlert.setContentText("Product cannot be removed until associated parts are removed!");
                                        noDeleteAlert.showAndWait();
                                        return;
                                }
                                else Inventory.deleteProduct(product);
                        }
                }
        }

        /** This method is used to modify an existing part within the inventory.
         * @param event is when the modify button is clicked.
         */
        @FXML
        void onModifyPartB(ActionEvent event) throws IOException {

                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
                        loader.load();

                        ModifyPartController MParCController = loader.getController();
                        MParCController.sendPart(partsTable.getSelectionModel().getSelectedIndex(), partsTable.getSelectionModel().getSelectedItem());

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();

                } catch (NullPointerException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("No Part Was Selected!");
                        alert.setContentText("Please select the part you wish to modify!");
                        alert.showAndWait();
                }
        }

        /** This method is used to modify an existing product within the products table.
         * @param event is when the modify button is clicked.
         */
        @FXML
        void onModifyProB(ActionEvent event) throws IOException {

                try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
                        loader.load();

                        ModifyProductController MProCController = loader.getController();
                        MProCController.sendProduct(productsTable.getSelectionModel().getSelectedIndex(), productsTable.getSelectionModel().getSelectedItem());

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();

                } catch (NullPointerException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No Product Was Selected!");
                        alert.setContentText("Please select the product you wish to modify!");
                        alert.showAndWait();
                }
        }

        /** This method is used to search for an existing product by ID or name within the products table.
         * @param ActionEvent is when the searched field is used.
         */
        @FXML
        public void searchProductID(ActionEvent ActionEvent) {

                String productSearch = searchProducts.getText();
                if (productSearch.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No Product Was Found!");
                        alert.setContentText("Please enter a valid product!");
                        alert.showAndWait();
                }

                ObservableList<Product> searchByProductName = Inventory.lookupProduct(productSearch);
                if (searchByProductName.isEmpty()) {
                        try {
                                int productId = Integer.parseInt(productSearch);
                                Product pro = Inventory.lookupProduct(productId);
                                if (pro != null) {
                                        searchByProductName.add(pro);
                                }
                        } catch (NumberFormatException e) {
                        }
                }

                productsTable.setItems(searchByProductName);
                searchProducts.setText("");
                if (searchByProductName.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("No Product Was Found!");
                        alert.setContentText("Please enter a valid product!");
                        alert.showAndWait();
                        productsTable.setItems(Inventory.getAllProducts());
                }
        }

        /** This method is used to search for an existing part by ID or name within the parts table.
         * @param actionEvent is when the searched field is used. -
         */
        public void searchPartID(ActionEvent actionEvent) {

                String partSearch = searchParts.getText();
                if (partSearch.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No Part Was Found!");
                        alert.setContentText("Please enter a valid part!");
                        alert.showAndWait();
                }

                ObservableList<Part> searchByPartName = Inventory.lookupPart(partSearch);
                if (searchByPartName.size() == 0) ;
                {
                        try {
                                int partId = Integer.parseInt(partSearch);
                                Part part = Inventory.lookupPart(partId);
                                if (part != null) ;
                                searchByPartName.add(part);
                        } catch (NumberFormatException e) {
                        }
                }
                partsTable.setItems(searchByPartName);
                searchParts.setText("");
                if (searchByPartName.isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("No Part Was Found!");
                        alert.setContentText("Please enter a valid part!");
                        alert.showAndWait();
                        partsTable.setItems(Inventory.getAllParts());
                }
        }

        /** This method allows for the inheritance of a superclass that is used to display the parts and products tables.
         * @param resourceBundle resource bundle
         * @param url url
         */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

                partsTable.setItems(Inventory.getAllParts());

                partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

                productsTable.setItems(Inventory.getAllProducts());

                productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        }
}




