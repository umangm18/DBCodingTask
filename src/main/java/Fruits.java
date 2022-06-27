import java.util.Arrays;
import java.util.Optional;


/**
 * Fruits class has list of all available  fruits and their prices
 */
public enum Fruits {

    Apple("Apple", 12),
    Orange("Orange", 32),
    Banana("Banana", 51),
    Pineapple("Pineapple", 95);

    private final String productName;
    private final double price;

    Fruits(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    public static Optional<Fruits> getFruit(String itemName) {
        return Arrays.stream(Fruits.values()).filter(e -> e.productName.equals(itemName)).findFirst();

    }

    public double getPrice() {
        return price;
    }
}
