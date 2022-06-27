/**
 * Apply discount for banana : three for two
 */
public class BananaDiscount implements Discount {

    @Override
    public double applyDiscount(int quantity) {
        // offer is 3 for 2
        double bananaPrice = Fruits.Banana.getPrice();
        double offerPrice = 0.0;
        while (quantity > 0) {
            if (quantity >= 3) {
                offerPrice += 2 * bananaPrice;
                quantity -= 3;
            } else {
                offerPrice += bananaPrice;
                quantity -= 1;
            }
        }
        return offerPrice;
    }
}
