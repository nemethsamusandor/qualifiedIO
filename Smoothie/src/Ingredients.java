import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ingredients
{
    private static Map<String, String> menuOptions = Stream.of(
        new AbstractMap.SimpleEntry<>("Classic", "strawberry, banana, pineapple, mango, peach, honey"),
        new AbstractMap.SimpleEntry<>("Freezie", "blackberry, blueberry, black currant, grape juice, frozen yogurt"),
        new AbstractMap.SimpleEntry<>("Greenie", "green apple, lime, avocado, spinach, ice, apple juice"),
        new AbstractMap.SimpleEntry<>("Just Desserts", "banana, ice cream, chocolate, peanut, cherry")
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        String customerRequest = scanner.nextLine();

        System.out.println(getIngredients(customerRequest));

    }

    public static String getIngredients(final String order)
    {
        if (order == null || order.trim().isEmpty())
        {
            throw new IllegalArgumentException("Order is empty!");
        }

        List<String> customerRequestList = new ArrayList<>(Arrays.asList(order.trim().split("\\s*,\\s*")));

        // Smoothie name
        String name = customerRequestList.get(0);

        // No such Smoothie on the Menu
        if (!menuOptions.containsKey(name))
        {
            throw new IllegalArgumentException("No such Shoothie on the menu");
        }

        // Remove Smoothie name from the list
        customerRequestList.remove(0);

        List<String> smoothieIngredients = new ArrayList<>(Arrays.asList(menuOptions.get(name).split("\\s*,\\s*")));

        for (String actualIngredient : customerRequestList)
        {
            boolean removeIngredient = actualIngredient.startsWith("-");

            if (!removeIngredient)
            {
                throw new IllegalArgumentException("Not possible to add '" + actualIngredient + "' to ingredients");
            }

            smoothieIngredients.remove(actualIngredient.substring(1));
        }

        return smoothieIngredients.stream()
            .sorted()
            .collect(Collectors.joining(","));
    }
}
