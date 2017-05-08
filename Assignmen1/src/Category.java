
/**
 * 1. The Category constructor must initialise all of its fields from parameters.
 *
 * @author (Clint Sellen)
 * @version (17/4/17)
 */
public class Category
{
    //Ingredient Variables
    
    private String name;
    private int min;
    private int max;
    
    //Constructor
    
    public Category(String name, int min, int max)
    { 
        this.name = name;
        this.min = min;
        this.max = max;
    }
    
    //Public Functions
    
    public String getName(){
        return name;
    }
    
    public int getMin(){
        return min;
    }
    
    public int getMax(){
        return max;
    }
    //toString
    
    @Override
    public String toString()
    {
        return name;
    }
}