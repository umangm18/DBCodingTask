/**
 * Apply discount for apple buy one get one free
 */
public class AppleDiscount implements Discount {

    @Override
    public double applyDiscount(int quantity) {
        int pair = (quantity / 2);
        int remainder = (quantity % 2);
        return (pair + remainder) * Fruits.Apple.getPrice();
    }
}
