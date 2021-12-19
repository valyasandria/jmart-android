package valyaJmartPK.jmart_android.model;

/**
 * Add new product to the store
 * @author Valya Sandria Akiela
 */
public class Product extends Serializable{
    public int accountId;
    public String name;
    public int weight;
    public boolean conditionUsed;
    public double price;
    public double discount;
    public ProductCategory category;
    public byte shipmentPlans;

    /**
     * provide product's detail with parameters below
     * @param accountId
     * @param name
     * @param weight
     * @param conditionUsed
     * @param price
     * @param discount
     * @param category
     * @param shipmentPlans
     */
    public Product(int accountId, String name, int weight, boolean conditionUsed, double price, double discount, ProductCategory category, byte shipmentPlans)
    {
        this.accountId = accountId;
        this.name = name;
        this.weight = weight;
        this.conditionUsed = conditionUsed;
        this.price = price;
        this.discount = discount;
        this.category = category;
        this.shipmentPlans = shipmentPlans;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
