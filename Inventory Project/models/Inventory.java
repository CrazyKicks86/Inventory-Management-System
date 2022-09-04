package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates the object "Inventory" */
public class Inventory {

    /** These are observable lists for "allParts" and "allFilteredParts" that will be used by listeners. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Part> allFilteredParts = FXCollections.observableArrayList();

    /**
     * Method used to add parts to the inventory.
     * */
    public static void addParts(Part part)
    {
        allParts.add(part);
    }

    /** Method used to hold all of the available parts that will be referenced on the parts table.
     * @return This will return allParts within the observable array.
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /** Method used to hold any parts that were filtered during a search.
     * @return This will return allFilteredParts
     */
    public static ObservableList<Part> getAllFilteredParts()
    {
        return allFilteredParts;
    }

    /** Method used to delete a part from the allParts list.
     * @return is a boolean value that will return true or false depending on the input.
     */
    public static boolean deletePart(Part part)
    {
        if(allParts.contains(part))
        {
            allParts.remove(part);
            return true;}
        else {
            return false;
        }
    }


    /** Method used to update an existing part and keep the location present without overlapping another part in the table.
     * @param index index will keep the part from taking another parts location within the table.
     * @param uPart is the part that will be selected to update inside the table.
     */
    public static void updatePart(int index, Part uPart)
    {
        allParts.set(index, uPart);
    }

    /** This method is used to look up a part inside the inventory based on the part ID.
     * @param partId is the ID of the searched part.
     * @return this will return the part if the ID has been found.
     * */
    public static Part lookupPart(int partId) {
        for (Part part : Inventory.getAllParts()) {
            while (part.getId() == partId) {
                return part;
            }
        }
            return null;
    }

    /** Search method using overloading due to possible multiple outcomes.
     * @param partName searched partNames
     * @return this will return all found names that include exact names or partial names.
     * */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundNames = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                foundNames.add(part);
            }
        }
        return foundNames;
    }

    /** These are observable lists for "allProducts" and "allFilteredProducts" that will be used by listeners. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Product> allFilteredProducts = FXCollections.observableArrayList();


    /** Method used to add products to the products list.
     * @param newProduct this will be the new product that will be added.
     */
    public static void addProducts(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /** This method is used to delete a product from the allProducts list.
     * @return if found true, will delete the selectedProduct from the allProducts list.
     * @param selectedProduct is the product that will be selected for removal.
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if(allProducts.contains(selectedProduct))
        {
            allProducts.remove(selectedProduct);
            return true;}
        else {
            return false;
        }
    }

    /** This method is used to retrieve all products from the product observable array.
     * @return This will return allProducts
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    /** This method is used to retrieve all products from the product observable array if overloading occurs.
     * @return This will return allFilteredProducts
     */
    public static ObservableList<Product> getAllFilteredProducts()
    {
        return allFilteredProducts;
    }

    /** Setter method used to update the allProducts list by index.
     * @param index indexed to allow for continual placement and not override another products place on the table.
     * @param updatedProduct referenced as the product to update.
     */
    public static void updatedProduct(int index, Product updatedProduct)
    {
        allProducts.set(index, updatedProduct);
    }

    /** Search method using overloading due to possible multiple outcomes  Product name will ne searched (partial or full).
     * @param partialName is used to select any parts of a name that occur during the search.
     * @return if product is found during the search, the product will be set to the productNames list.
     * */
    public static ObservableList<Product> lookupProduct(String partialName) {
        ObservableList<Product> productNames = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        {
            for (Product product : allProducts) {
                if (product.getName().contains(partialName))
                    productNames.add(product);
            }
            return productNames;
        }
    }

    /** This method is used to look up a product inside the inventory based on the product ID.
     * @param productId used to select the ID of the searched product.
     * @return if product ID was found, the selected product will be returned and displayed on the product table.
     */
    public static Product lookupProduct(int productId)
    {
        for(Product product : Inventory.getAllProducts()) {
            while (product.getId() == productId)
            {
                return product;
            }
        }
        return null;
    }
}

