package valyaJmartPK.jmart_android.model;

import java.text.SimpleDateFormat;

/**
 * Create shipment detail
 * @author Valya Sandria Akiela
 */
public class Shipment {
    public static final SimpleDateFormat ESTIMATION_FORMAT = new SimpleDateFormat("E MMMM dd yyyy");
    public static final Plan INSTANT = new Plan((byte)(1 << 0));    //1
    public static final Plan SAME_DAY = new Plan((byte)(1 << 1));   //2
    public static final Plan NEXT_DAY = new Plan((byte)(1 << 2));   //4
    public static final Plan REGULER = new Plan((byte)(1 << 3));    //8
    public static final Plan KARGO = new Plan((byte)(1 << 4));      //16

    public String address;
    public int cost;
    public byte plan;
    public String receipt;

    /**
     * represents shipment details
     * @param address
     * @param cost
     * @param plan
     * @param receipt
     */
    public Shipment(String address, int cost, byte plan, String receipt)
    {
        this.address = address;
        this.cost = cost;
        this.plan = plan;
        this.receipt = receipt;
    }

    public static class Plan
    {
        public final byte bit;
        private Plan (byte bit)
        {
            this.bit = bit;
        }
    }
}
