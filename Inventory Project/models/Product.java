package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class creates the object "Product". */
public class Product {

    /** This is an observable list that will be used to produce associated parts by listeners. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Declared fields/variables for the object. */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Declared constructors for the object. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /** Getter method
     * @return Product Id as an integer.
     */
    public int getId() {
        return id;
    }

    /** Setter method
     * @param id is the ID (integer) the object will be set to.
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter method
     * @return Product Name as an string.
     */
    public String getName() {
        return name;
    }

    /** Setter method
     * @param name is the name (string) the object will be set to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter method
     *  @return Product price as a boolean value.
     */
    public double getPrice() {
        return price;
    }

    /** Setter method
     * @param price is the price (boolean) the object will be set to.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter method
     * @return Product stock as an integer.
     */
    public int getStock() {
        return stock;
    }

    /** Setter method
     * @param stock is the stocking level (integer) the object will be set to.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter method
     * @return Product minimum stocking value as an integer.
     */
    public int getMin() {
        return min;
    }

    /** Setter method
     * @param min is the minimum stocking level (integer) the object will be set to.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter method
     * @return Product maximum stocking value as an integer.
     */
    public int getMax() {
        return max;
    }

    /** Setter method
     * @param max is the maximum stocking level (integer) the object will be set to.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Method used to add a part to the associated parts list.
     * @param part
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /** Method used to remove a selected associated part.
     * @param selectedAssociatedPart
     * @return the value true
     */
    public boolean deleteAssocPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /** Method used to get a list of all associated parts by a listener.
     * @return All associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}



