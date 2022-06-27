import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * BasketPriceGenerator takes shopping basket and computes the price after applying relevant discounts.
 */
public class BasketPriceGenerator {
    private static Logger logger = LogManager.getLogger(BasketPriceGenerator.class);

    public double compute(ShoppingBasket myBasket) {

        List<String> items = myBasket.getShoppingList();
        if (items == null || items.size() == 0)
            throw new IllegalArgumentException("Shopping item list is empty");

        if (logger.isDebugEnabled())
            logger.debug("Shopping list for basket:" + items);

        Map<String, Integer> myBasketMap = new ConcurrentHashMap<>();

        if (validateShoppingItemList(items)) {
            for (String s : items) {
                if (myBasketMap.containsKey(s)) {
                    myBasketMap.put(s, myBasketMap.get(s) + 1);
                } else {
                    myBasketMap.put(s, 1);
                }
            }
            if (logger.isDebugEnabled())
                logger.debug("shopping list group by :" + myBasketMap);

            double shoppingBasketPrice = 0.0;

            if (myBasketMap.keySet().contains(Fruits.Apple.name())) {
                double discountedPrice = new AppleDiscount().applyDiscount(myBasketMap.get(Fruits.Apple.name()));
                shoppingBasketPrice += discountedPrice;

                if (logger.isDebugEnabled())
                    logger.debug("After adding Apple price=" + shoppingBasketPrice);
            }

            if (myBasketMap.keySet().contains(Fruits.Banana.name())) {
                double discountedBananaPrice = new BananaDiscount().applyDiscount(myBasketMap.get(Fruits.Banana.name()));
                shoppingBasketPrice += discountedBananaPrice;
                if (logger.isDebugEnabled())
                    logger.debug("After adding Banana price=" + shoppingBasketPrice);
            }

            for (String item : myBasketMap.keySet()) {
                if (!item.equals(Fruits.Apple.name()) && !item.equals(Fruits.Banana.name())) {
                    Optional<Fruits> fruit = Fruits.getFruit(item);
                    double itemPrice = 0.0;
                    if (fruit.isPresent()) {
                        int itemCount = myBasketMap.get(item);
                        itemPrice = itemCount * fruit.get().getPrice();
                    }
                    shoppingBasketPrice += itemPrice;
                    if (logger.isDebugEnabled())
                        logger.debug("After adding {} basket price : {}", item, shoppingBasketPrice);
                }

            }
            return shoppingBasketPrice;
        } else {
            logger.error("Check shopping list . It has atleast one incorrect item " + items);
            throw new IllegalArgumentException("Check shopping list . It has atleast one incorrect item " + items);
        }
    }

    private boolean validateShoppingItemList(List<String> basketList) {
        for (String fruit : basketList) {
            if (Fruits.getFruit(fruit) == null)
                return false;
        }
        return true;

    }

}
