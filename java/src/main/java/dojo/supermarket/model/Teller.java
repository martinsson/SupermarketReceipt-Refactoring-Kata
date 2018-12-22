package dojo.supermarket.model;

import dojo.supermarket.Offers.SpecialOfferType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        Offer offer = offerType.createSpecialOffer(product, argument);
        addSpecialOffer(product, offer);
    }

    private void addSpecialOffer(Product product, Offer offer) {
        this.offers.put(product, offer);
    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        for (ProductQuantity pq: theCart.getItems()) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = this.catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }
        ArrayList<Discount> discounts = new ArrayList<>();

        for (Product p : theCart.productQuantities().keySet()) {
            double quantity = theCart.productQuantities.get(p);
            if (this.offers.containsKey(p)) {
                Offer offer = this.offers.get(p);
                SupermarketCatalog catalog = this.catalog;
                double unitPrice = catalog.getUnitPrice(p);

                Discount discount = offer.calculateDiscount(quantity, unitPrice);

                if (discount != null) {
                    discounts.add(discount);
                }
            }

        }
        receipt.addDiscounts(discounts);

        return receipt;
    }

}
