import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.*;
import org.junit.jupiter.api.Assertions;


import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestBasketPricer {

    Logger logger = LogManager.getLogger(TestBasketPricer.class);

    @Test
    public void testApplePriceOffer() {

        Discount appleDiscount = new AppleDiscount();
        double applePrice = appleDiscount.applyDiscount(3);

        double epsilon = 0.000001d;
        //apples are buy one get one free offer at 12p each
        assertEquals(applePrice, 24.0, epsilon);

        applePrice = appleDiscount.applyDiscount(1);
        assertEquals(applePrice, 12.0, epsilon);

        applePrice = appleDiscount.applyDiscount(4);
        assertEquals(applePrice, 24, epsilon);

        applePrice = appleDiscount.applyDiscount(5);
        assertEquals(applePrice, 36.0, epsilon);

    }

    @Test
    public void testBananaPriceOffer() {

        Discount bananaDiscount = new BananaDiscount();

        double bananaPrice = bananaDiscount.applyDiscount(2);
        double epsilon = 0.000001d;
        //bananas are 3 for 2
        assertEquals(bananaPrice, 102.0, epsilon);

        bananaPrice = bananaDiscount.applyDiscount(1);
        assertEquals(bananaPrice, 51.0, epsilon);

        bananaPrice = bananaDiscount.applyDiscount(4);
        assertEquals(bananaPrice, 153.0, epsilon);

        bananaPrice = bananaDiscount.applyDiscount(5);
        assertEquals(bananaPrice, 204, epsilon);

    }

    @Test
    public void testShoppingListPrice() {


        ArrayList<String> shoppingList = new ArrayList<>(Arrays.asList(Fruits.Apple.name(), Fruits.Apple.name(), Fruits.Orange.name(), Fruits.Apple.name(), Fruits.Pineapple.name(), Fruits.Banana.name(), Fruits.Orange.name(), Fruits.Banana.name()));
        ShoppingBasket myBasket = new ShoppingBasket(shoppingList);
        BasketPriceGenerator basketPriceGenerator = new BasketPriceGenerator();

        if(logger.isDebugEnabled())
            logger.debug("Shopping list is:" + shoppingList);
        double epsilon = 0.000001d;
        /**
         * 3 Apples= 2 apple price = 2*12 =24p
         * 2 banana = 3 banana at 2 banana price = 51*2 = 102
         * 1 pineapple = 95p
         * 2 orange = 32*2= 64p
         * Total price = 285p
         */
        assertEquals(basketPriceGenerator.compute(myBasket), 285, epsilon);
        /**
        tweak the shopping data to validate pricing.
        5 Apples = 3*12 = 36p
         2 Oranges = 32*2 = 64p
         2 Pineapple =95*2 = 190p
         5 Banana = 51*4 = 204 p
         Total price = 494p
         */
        ArrayList<String> anotherShoppingList = new ArrayList<>(Arrays.asList(Fruits.Apple.name(), Fruits.Apple.name(), Fruits.Orange.name(), Fruits.Apple.name(), Fruits.Apple.name(), Fruits.Apple.name(), Fruits.Pineapple.name(), Fruits.Banana.name(), Fruits.Orange.name(), Fruits.Banana.name(), Fruits.Banana.name(), Fruits.Banana.name(), Fruits.Pineapple.name(), Fruits.Banana.name()));
        myBasket = new ShoppingBasket(anotherShoppingList);
        if(logger.isDebugEnabled())
            logger.debug("Shopping list is:" + anotherShoppingList);

        assertEquals(basketPriceGenerator.compute(myBasket), 494, epsilon);

    }


    public void testShoppingListBasicException() {
        ShoppingBasket emptyBasket = new ShoppingBasket(null);
        BasketPriceGenerator basketPriceGenerator = new BasketPriceGenerator();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basketPriceGenerator.compute(emptyBasket);

        });

    }


    public void testShoppingBasketWrongItem() {
        List<String> anotherShoppingList = new ArrayList(Arrays.asList("dummyData"));

        ShoppingBasket anotherBasket = new ShoppingBasket(anotherShoppingList);
        BasketPriceGenerator basketPriceGenerator = new BasketPriceGenerator();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            basketPriceGenerator.compute(anotherBasket);

        });
    }
}
