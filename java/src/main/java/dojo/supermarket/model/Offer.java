package dojo.supermarket.model;

public class Offer {
    SpecialOfferType offerType;
    private final Product product;
    double argument;

    public Offer(SpecialOfferType offerType, Product product, double argument) {
        this.offerType = offerType;
        this.argument = argument;
        this.product = product;
    }

    Product getProduct() {
        return this.product;
    }

    Discount calculateDiscount(double quantity, double unitPrice) {
        Discount discount;
        if (offerType == SpecialOfferType.TwoForAmount && quantity >= 2) {
            int numberOfXs = (int) quantity / 2;
            double discountN = (unitPrice * 2 - argument) * numberOfXs;
            discount = new Discount(product, "2 for " + argument, discountN);

        } else if (offerType == SpecialOfferType.ThreeForTwo && quantity >= 3) {
            int numberOfXs = (int) quantity / 3;
            double discountAmount = unitPrice * numberOfXs;
            discount = new Discount(product, "3 for 2", discountAmount);

        } else if (offerType == SpecialOfferType.FiveForAmount && quantity >= 5) {
            int numberOfXs = (int) quantity / 5;
            double discountTotal = (unitPrice * 5 - argument) * numberOfXs;
            discount = new Discount(product, "5 for " + argument, discountTotal);

        } else if (offerType == SpecialOfferType.TenPercentDiscount) {
            double percentageDiscount = argument;
            discount = new Discount(product, argument + "% off", quantity * unitPrice * percentageDiscount / 100.0);

        } else {
            discount = null;
        }
        return discount;
    }
}
