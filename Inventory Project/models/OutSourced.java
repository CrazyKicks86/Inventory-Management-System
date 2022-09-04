package model;

/** This class creates the object "OutSourced" and extends the "Parts" class. */
public class OutSourced extends Part {

    /** Declares the object field "compName". */
    private String compName;

    /** Declares the super constructors */

    public OutSourced(int id, String name, double price, int stock, int min, int max, String compName) {
        super(id, name, price, stock, min, max);
        this.compName = compName;
    }

    /** Getter method
     * @return the company name as a string.
     */
    public String getCompName()
    {
        return compName;
    }

    /** Setter method
     * @param companyName
     */
    public void setCompName(String companyName)
    {
        this.compName = companyName;
    }

}

