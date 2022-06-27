import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * This is the main prograM which takes a simple list of shopping items and prints out the basket
 * price after applying any discounts
 */
public class MyShoppingBasket {

    private static Logger logger = LogManager.getLogger(MyShoppingBasket.class);

    public static void main(String[] args) {
        String[] shoppingList = {"Apple", "Apple", "Orange", "Apple", "Pineapple", "Banana", "Orange", "Banana"};

        ShoppingBasket myBasket = new ShoppingBasket(Arrays.asList(shoppingList));
        BasketPriceGenerator basketPriceGenerator = new BasketPriceGenerator();
        double basketPrice = basketPriceGenerator.compute(myBasket);

        logger.info("For shopping basket {}, price is {} ", shoppingList, basketPrice);

    }
}
