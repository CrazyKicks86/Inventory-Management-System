package model;

/** This creates an abstract class for the object "Part". */
public abstract class Part {

    /** Declare fields/values for the Part class. */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /** Declared constructors for the object. */
    public Part(int id, String name, double price, int stock, int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /** Getter method
     * @return The part id as an integer.
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
     * @return The part name as a string.
     */
    public String getName() {
        return name;
    }

    /** Setter method
     * @param name is the Name (string) the object will be set to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter method
     * @return The part price as a double.
     */
    public double getPrice() {
        return price;
    }

    /** Setter method
     * @param price is the Price (double) the object will be set to.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter method
     * @return The part stocking level as an integer.
     */
    public int getStock() {
        return stock;
    }

    /** Setter method
     * @param stock is the Stock level (integer) the object will be set to.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter method
     * @return The minimum stocking level as an integer.
     */
    public int getMin() {
        return min;
    }

    /** Setter method
     * @param min is the Minimum stock level (integer) the object will be set to.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter method
     * @return The maximum stocking level as an integer.
     */
    public int getMax() {
        return max;
    }

    /** Setter method
     * @param max is the Maximum stock level (integer) the object will be set to.
     */
    public void setMax(int max) {
        this.max = max;
    }

}
