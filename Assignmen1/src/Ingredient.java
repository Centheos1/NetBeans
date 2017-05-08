
/**
 * 1. The Ingredient constructor must initialise the name, price and category from parameters.
 *      The sold field is always initialised to zero.
 * 
 * @author (Clint Sellen)
 * @version (17/4/17)
 */
public class Ingredient
{
    //Ingredient Variables
    
    private String name;
    private double price;
    private Category category;
    private int sold;
    
    //Constructor

    public Ingredient(String name, double price, Category category)
    {
        this.name = name;
        this. price = price;
        this.category = category;
        //sold++; 
    }
    
    //Public Functions
    
    public String getName()
    {
        return name;
    }
    
    public double getPrice(){
        return price;
    }
    
    public Category getCategory(){
        return category;
    }
    
    public int getSold(){
        return sold;
    }
    
    public void ingredientSold(){
        sold++;
    }
    
    //for Kitchen toString
    public boolean lookUp(Category search){
        return search.equals(this.category);
    }
    
    //toString
    
    @Override
    public String toString()
    {
        return name + " " + category;
    }
}
