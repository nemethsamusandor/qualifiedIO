import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class IngredientsTest
{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testAllergic()
    {
        // Original list "Classic: strawberry, banana, pineapple, mango, peach, honey"
        String result = Ingredients.getIngredients("Classic,-strawberry,-peanut");

        assertEquals("banana,honey,mango,peach,pineapple", result);
        assertTrue(isSorted(result));
    }

    @Test
    public void testNullOrder()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Order is empty");

        Ingredients.getIngredients(null);
    }

    @Test
    public void testEmptyOrder()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Order is empty");

        Ingredients.getIngredients("");
    }

    @Test
    public void testNoSuchIngredient()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Not possible to add 'chocolate' to ingredients");

        Ingredients.getIngredients("Classic,chocolate");
    }

    @Test
    public void testNoSuchSmoothie()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("No such Shoothie on the menu");

        Ingredients.getIngredients("Vitamin smoothie");
    }

    private boolean isSorted(String csvList)
    {
        if (csvList == null || csvList.isEmpty())
        {
            return true;
        }

        List<String> listOfStrings = Arrays.asList(csvList.trim().split("\\s*,\\s*"));

        if (listOfStrings.size() == 1)
        {
            return true;
        }

        Iterator<String> iter = listOfStrings.iterator();
        String current, previous = iter.next();

        while (iter.hasNext())
        {
            current = iter.next();
            if (previous.compareTo(current) > 0)
            {
                return false;
            }
            previous = current;
        }

        return true;
    }

}