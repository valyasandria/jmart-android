package valyaJmartPK.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * calculate the total of product price to be paid and show payment status & date
 * @author Valya Sandria Akiela
 */
public class Payment extends Invoice
{
    public int productCount;
    public Shipment shipment;
    public ArrayList<Record> history = new ArrayList<Record>();

    /**
     * Represents purchasing process
     * @param buyerId Account Id
     * @param productId Product Id
     * @param productCount jumlah produk yang dibeli
     * @param shipment detail pengiriman barang (alamat & shipment plans)
     */
    public Payment(int buyerId, int productId, int productCount, Shipment shipment)
    {
        super(buyerId, productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }

    /**
     * menghitung harga total produk dari jumlah produk, discount, dan biaya pengiriman
     * @param product
     * @return harga total produk yang harus dibayar
     */
    public double getTotalPay(Product product)
    {
        return (product.price-((product.discount/100)*product.price)*productCount) + shipment.cost;
    }

    public static class Record
    {
        public final Date date = new Date();
        public Status status;
        public String message;

        public Record(Status status, String message)
        {
            this.status = status;
            this.message = message;
        }
    }

    @Override
    public String toString()
    {
        return ("buyerId: " + this.buyerId + "\nproductId: " + this.productId + "\nproductCount: " + this.productCount + "\nshipment" + this.shipment + "\nstatus: " + this.status);
    }
}
