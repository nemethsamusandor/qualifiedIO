import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Ingredients
{

    private static Map<String, String> menuOptions = Map.ofEntries(
        new AbstractMap.SimpleEntry<>("Classic", "strawberry,banana,pineapple,mango,peach,honey"),
        new AbstractMap.SimpleEntry<>("Freezie", "blackberry,blueberry,black currant,grape juice,frozen yogurt"),
        new AbstractMap.SimpleEntry<>("Greenie", "green apple, lime, avocado, spinach, ice, apple juice"),
        new AbstractMap.SimpleEntry<>("Just Desserts", "banana, ice cream, chocolate, peanut, cherry")
    );

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        String customerRequest = scanner.nextLine();

        System.out.println(getIngredients(customerRequest));

    }

    public static String getIngredients(final String customerRequest)
    {
        List<String> customerRequestList = new ArrayList<>(Arrays.asList(customerRequest.trim().split("\\s*,\\s*")));

        if (customerRequestList.isEmpty())
        {
            throw new IllegalArgumentException("Order is empty!");
        }

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
        List<String> customersSmoothie = new ArrayList<>(smoothieIngredients);

        for (String actualIngredient : customerRequestList)
        {
            String[] actualIngredients = actualIngredient.split("-");

            if (actualIngredients.length == 1 && !smoothieIngredients.contains(actualIngredients[0]))
            {
                throw new IllegalArgumentException("Not possible to add '" + actualIngredients[0] + "' to ingredients");
            }

            if (actualIngredients.length > 1)
            {
                customersSmoothie.remove(actualIngredients[1]);
            }
        }

        return customersSmoothie.stream()
            .sorted()
            .collect(Collectors.joining(","));
    }
}
