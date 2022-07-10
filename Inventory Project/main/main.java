package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/** This class creates a usable inventory management system.  This system allows for adding, removing, and deleting parts or products.
 * Javadoc file have been included in the zip file and is marked Javadoc C482.
 * FUTURE ENHANCEMENTS would include another table within the Add and Modify Product scenes.  This table would hold part-kits for the products.
 * This would allow for quicker part lookup and build pricing.
 * Example, if a customer wanted to purchase an all new suspension system, the suspension-kit may contain items such as tires, brakes, shocks, and pedal grips.
 * Logical error: refer to ModifyProductController
 * Creator: Josh Kinard
 */
class Main extends Application {

    /** This is the start method that initializes the inventory management system. It will call for the scene MainMenu.fxml. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /** This is the main method and is used to display arguments when called.
     * @param args arguments is the data given.
     * */
    public static void main(String[] args) { launch(args); }
    {

        OutSourced brakes = new OutSourced(4825, "Brakes", 182.45, 7, 5, 20, "Bike Outlet");
        OutSourced tires = new OutSourced(4796, "Tires", 223.18, 6, 2, 20, "Bike Outlet");
        OutSourced pedals = new OutSourced(4413, "Pedals (x2)", 22.41, 5, 3, 20, "Bike Outlet");

        InHouse chassis = new InHouse(1573, "Chassis", 317.99, 4, 1, 5, 13);
        InHouse seat = new InHouse(4695, "Seat", 98.19, 7, 5, 10, 27);
        InHouse wheel = new InHouse(3574, "Wheel", 71.34, 17, 10, 40, 63);

        Product giant = new Product(58462, "Giant Bike", 1212.77, 2, 1, 5);
        Product small = new Product(16579, "Small Bike", 440.24, 3, 1, 5);
        Product tri = new Product(22549, "Tricycle", 209.56, 5, 1, 5);

        Inventory.addParts(brakes);
        Inventory.addParts(tires);
        Inventory.addParts(pedals);

        Inventory.addParts(chassis);
        Inventory.addParts(seat);;
        Inventory.addParts(wheel);

        Inventory.addProducts(giant);
        Inventory.addProducts(small);
        Inventory.addProducts(tri);
    }
}




