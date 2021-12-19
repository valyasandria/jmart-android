package valyaJmartPK.jmart_android.model;

import java.util.Date;

/**
 * Show invoice from buyer transaction
 * @author Valya Sandria Akiela
 */
public abstract class Invoice extends Serializable
{
    public final Date date = new Date();
    public int buyerId;
    public int productId;
    public int complaintId = -1;
    public Rating rating = Rating.NONE;
    public Status status;

    /**
     * Represents transaction history from buyer
     * @param buyerId account Id
     * @param productId product Id
     */
    protected Invoice(int buyerId, int productId)
    {
        this.buyerId = buyerId;
        this.productId = productId;
    }

    /**
     *
     * @param product selected product
     * @return total price
     */
    public double getTotalPay(Product product) {
        return product.price;
    }

    public static enum Status
    {
        WAITING_CONFIRMATION,
        CANCELLED,
        ON_PROGRESS,
        ON_DELIVERY,
        COMPLAINT,
        FINISHED,
        FAILED;
    }

    public static enum Rating
    {
        NONE,
        BAD,
        NEUTRAL,
        GOOD;
    }
}
