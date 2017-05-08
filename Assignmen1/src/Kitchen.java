import java.text.DecimalFormat;
import java.util.*;
/**
 * 1. The Kitchen constructor has no parameters and must create and add all ingredients mentioned
 *      in the table on page 2 to the ingredients list.
 * 
 * @author (Clint Sellen)
 * @version (17/4/17)
 */
public class Kitchen
{
    //Kitchen Variables
    
    public static final Category CRUST = new Category("crust", 1, 1);
    public static final Category SAUCE = new Category("sauce", 1, 1);
    public static final Category TOPPING = new Category("topping", 2, 3);
    private Category[] categories = { CRUST, SAUCE, TOPPING };
    private LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
    
    //Constructor
    
    public Kitchen()
    {
        String crustIngredient[] = {"Thin","Thick"};
        String sauceIngredients[] = {"Tomato","Barbeque"};
        String toppingIngredients[] = {"Capsicum","Olives","Jalapenos","Beef","Pepperoni"};
    
        double crustPrices[] = {3, 3.5};
        double saucePrices[] = {1, 1};
        double toppingPrices[] = {.5, 1.5, 1, 2.75, 2.5};
        
        
        int i=0;
        for (int j = 0; j < crustPrices.length; j++){
            ingredients.add(new Ingredient(crustIngredient[j],crustPrices[j], categories[i]));
        }
        i++;
        for (int j = 0; j < saucePrices.length; j++){
            ingredients.add(new Ingredient(sauceIngredients[j],saucePrices[j], categories[i]));
        }
        i++;
        for (int j = 0; j < toppingPrices.length; j++){
            ingredients.add(new Ingredient(toppingIngredients[j],toppingPrices[j], categories[i]));
        }
    }
    
    //Public Functions
    
    public LinkedList<Ingredient> findIngredient(String searchStr, Pizza pizza){

        LinkedList listOfIngredients = new LinkedList<Ingredient>();
        
        if(searchStr.equals("~")){
            listOfIngredients.add(multiSelectMenu(ingredients));
            return listOfIngredients;
        }
        
        for(String word : searchStr.split(",")){
            listOfIngredients.add(hasIngredient(word, pizza));
        }
        return listOfIngredients;
    }
    
    public String kitchenReport(){
        
        double value = 0.0;
        double total = 0.0;
        for(Ingredient ingredient : ingredients){
            value = ingredient.getSold() * ingredient.getPrice();
            System.out.println(reportString(ingredient)+formatPrice(value));
            total += value;
            value = 0.0;
        }
        return formatPrice(total);
    }
       
    //Private Functions
    
    private String reportString(Ingredient ingredient){
        int num = ingredient.getSold();
        
        if(num == 1)
            return num +" "+ ingredient.toString()+  " sold worth $";
        else
            return num +" "+ ingredient.toString() + "s sold worth $";
    }

    
    private Ingredient hasIngredient(String searchIngredient, Pizza pizza){
 
        boolean removeFlag = false;
        LinkedList<Ingredient> matches = new LinkedList<Ingredient>();

        if(searchIngredient.startsWith("-")){
            removeFlag = true;
            searchIngredient = searchIngredient.substring(1);
        }
        
        for(Ingredient ingredient : ingredients){
            if (ingredient.getName().length() == searchIngredient.length()){
                //look for exact match
                if(exactMatch(ingredient.getName(), searchIngredient))
                    if(removeFlag){
                        pizza.remove(ingredient);
                        return null;
                    }else{   
                        return ingredient;
                    }
            }
            else if (multiMatch(ingredient.getName().toLowerCase(), searchIngredient.toLowerCase())){
                matches.add(ingredient);
            }
        }
        
        if(matches.isEmpty()){
            if(checkCategory(searchIngredient)){
                for(Ingredient tmp : ingredients){
                    if(tmp.getCategory().getName().equalsIgnoreCase(searchIngredient))
                        matches.add(tmp);
                }
            }
            if(!matches.isEmpty())
                return multiSelectMenu(matches);
        }
        
        
        if(matches.size() == 1){
            if(removeFlag){
                pizza.remove(matches.getFirst());
                return null;
            }else{
                return matches.getFirst();
            }
        }else if(matches.size() > 1){
            return multiSelectMenu(matches);
        }
        
        System.out.println("No ingredient matching " + searchIngredient);
        return null;
    }
    
    private boolean exactMatch(String name, String searchIngredient){
        //IF both words are the same RETURN true
        return (name.equalsIgnoreCase(searchIngredient));
    }

    private boolean checkCategory(String searchStr){
        boolean flag = false;
        for(Category category : categories)
            if(category.getName().equalsIgnoreCase(searchStr)){
                flag = true;
            }
        return flag;
    }
    
    private boolean multiMatch(String name, String searchIngredient){
        for(int i=0; i<searchIngredient.length(); i++){
            if (name.charAt(i) != searchIngredient.charAt(i))
                return false;
        }
        return true;
    }
    
    private Ingredient multiSelectMenu(LinkedList<Ingredient> list){
        //Print all possibilities
        //read selected input
        //add ingredient to pizza
        int count = 1;
        System.out.println("Select from matches below:");
        for (Ingredient choice : list){
            System.out.println(count +". "+ choice);
            count++;
        }
        return list.get(readChoice());
    }
     
    private int readChoice()
    {
        System.out.print("Selection: ");
        return In.nextInt()-1;
    }
    
    
    private String formatPrice(double price){
        return new DecimalFormat("##0.00").format(price);
    }
    
}