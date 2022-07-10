package model;

/** This class creates the object "InHouse" which extends the "Part" class. */
public class InHouse extends Part {

    /** This declares the machine Id field. */
    private int machineId;

    /** Declares the super constructors. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Setter method
     * @param machineId
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }

    /** Getter method
     * @return This returns the machine Id as an integer.
     */
    public int getMachineId() {

        return machineId;
    }
}

