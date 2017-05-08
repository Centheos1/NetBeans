import java.util.*;
import java.text.*;
/**
 * 1. The Pizza constructor takes no parameters and does nothing (except to initialise sold to zero).
 * 
 * @author (Clint Sellen)
 * @version (17/4/17)
 */
public class Pizza
{
    //Pizza variables
    
    private LinkedList<Ingredient> ingredients = new LinkedList<Ingredient>();
    private int sold; 
    
    //Constructor
    
    public Pizza()
    {
        sold = 0;
    }
    
    //Public Functions
    
    public Pizza build(Kitchen kitchen){
        //add ingredients until order complete
        LinkedList<Ingredient> listOfIngredients = new LinkedList<Ingredient>();
        String searchIngredient;
//        boolean removeFlag = false;

        
        //WHILE first char entered isn't '.' search for ingredieants
        while((searchIngredient = readIngredient()).charAt(0) != '.' || !hasMinReq()) {
            int count = 0;
            boolean printString = false;
            
            if (searchIngredient.equals("~")){
                listOfIngredients = kitchen.findIngredient(searchIngredient, this);
                
                if(!iSDuplicate(listOfIngredients.get(0)) && !isMax(listOfIngredients.get(0))){
                            //add ingredient
                            ingredients.add(listOfIngredients.get(0));
                            System.out.println(this);
                        }
                
            }else{
                //Check IF min Requirments for complete pizza
                if(searchIngredient.equals(".")){
                    minReqString();
                    searchIngredient = readIngredient();   
                }    

                listOfIngredients = kitchen.findIngredient(searchIngredient, this);

                for(Ingredient ingredient : listOfIngredients){
                    if (ingredient != null){
                        if(!iSDuplicate(ingredient) && !isMax(ingredient)){
                            //add ingredient
                            ingredients.add(ingredient);
                            printString = true;
                        }
                    }
                    count++;
                    if(count == listOfIngredients.size() && printString)
                        System.out.println(this);
                }
                
                listOfIngredients.clear();

                if(searchIngredient.endsWith(".")){
                    break;
                }
            }
        }
        return this;
    }
    
    
    public boolean orderComplete(Customer customer) { 
        //return true when Pizza has min crust, sauce, toppings 
        boolean commit = false; 
        boolean crust = false;
        boolean sauce = false;
        int toppings = 0;

        for (Ingredient ingredient : ingredients){
            if(!crust){
               crust = ingredient.lookUp(Kitchen.CRUST);
               if(crust)
                   ingredient.ingredientSold();
            }
            if(!sauce){
                sauce = ingredient.lookUp(Kitchen.SAUCE);
                if(sauce)
                    ingredient.ingredientSold();
            }
            if(toppings < 3 && ingredient.lookUp(Kitchen.TOPPING)){
                toppings++;
                ingredient.ingredientSold();
            }
        }
        if(crust && sauce && toppings >= 2){
            customer.addToOrdered(this);      
            commit = true;
        }else{
            commit = false;
        }
        return commit;
    }
    
    
    
    
    
    public double getPrice(){
        return calcPrice();
    }
    
    public int getSold(){
        return sold;
    }
    
    public void incrementSold(){
        sold++;
    }
    
    public void remove(Ingredient tmp){
        removeIngredient(tmp);
    }
    
  
    //Private Functions
    
    private double calcPrice(){
         double price = 0.0;
         for (Ingredient ingredient : ingredients){
            price += ingredient.getPrice();
         }
         return price;
    }
    
   
    private String readIngredient(){
        String s = "";
        System.out.print("Ingredient(s): ");
        s = In.nextLine();
        if (s.isEmpty())
            return "~";
        else 
            return s;
    }
    
    
    private boolean isMax(Ingredient checkThis){
        int count = 0;
        
        for(Ingredient ingredient : ingredients){
            if(ingredient.getCategory() == checkThis.getCategory())
                count++;
            if(count == checkThis.getCategory().getMax()){
                isMaxString(checkThis);
                return true;
            }
        }
        return false;    
    }
    
    
    private boolean hasMinReq(){
        boolean flag = true;
        int missing[] = ingredtendsStepThrough();
        
            
        if(missing[0] < Kitchen.CRUST.getMin()){
            flag = false;
        }
        if(missing[1] < Kitchen.SAUCE.getMin()){
            flag = false;
        }
        if(missing[2] < Kitchen.TOPPING.getMin()){
            flag = false;
        }
        
        return flag;
    }
    
   
     
     private boolean iSDuplicate(Ingredient ingredient){
        for(Ingredient tmp : ingredients){ 
            if(ingredient.getName().equals(tmp.getName())){
                System.out.println("Already added "+ ingredient);
                return true;
            }
        }
        return false;
     }
                        
     
     private Ingredient matchCategory(Category category){
         for(Ingredient tmp : ingredients ){
             if(tmp.lookUp(category))
                 return tmp;
         }
         return null;
     }
    
    
    private int[] ingredtendsStepThrough(){
        int missing[] = {0,0,0};
        for(Ingredient tmp : ingredients){
            if(tmp.getCategory() == Kitchen.CRUST)
                missing[0]++;
            if(tmp.getCategory() == Kitchen.SAUCE)
                missing[1]++;
            if(tmp.getCategory() == Kitchen.TOPPING)
                missing[2]++;
        }
        return missing;
    }
    
    
    private void removeIngredient(Ingredient ingredient){
        //if (removeFlag){//remove ingredient
        for(Iterator<Ingredient> it = ingredients.iterator(); it.hasNext() ;){
            if(it.next() == ingredient){
                it.remove();
                System.out.println(this);
                break;
            }
        }
    }
    
    
    //toString Functions
    
    private String crust()
    {
        Ingredient ingredient = null;
        if((ingredient = matchCategory(Kitchen.CRUST)) != null)
            return ingredient.toString();
        else
            return "no "+ Kitchen.CRUST.getName();
    }
    
    private String sauce()
    {
        Ingredient ingredient = null;
        if((ingredient = matchCategory(Kitchen.SAUCE)) != null)
            return ingredient.toString();
        else
        return "no "+Kitchen.SAUCE.getName();
    }
    
    private String toppings()
    {
        String s = "";
        LinkedList<Ingredient> tmp = new LinkedList<Ingredient>();
  
        for (Ingredient ingredient : ingredients){
            if(ingredient.lookUp(Kitchen.TOPPING)){
                tmp.add(ingredient);
            }
        }
        if(tmp.isEmpty()){
            return "no toppings";
        }else if(tmp.size() == 1){
            return tmp.get(0).getName();
        }else{
            for (Ingredient pizzaIngredient : tmp){
                s += pizzaIngredient.getName() +", ";
            }
        }
        return s.substring(0, s.length()-2);
    }
    
    public String toString(){
        return crust()+" pizza with "+toppings()+" and "+sauce()+": $"+ formatPrice();
    }

    private String formatPrice(){
        double price = calcPrice();
        return new DecimalFormat("##0.00").format(price);
    }
    
    private void minReqString(){
        int missing[] = ingredtendsStepThrough();
        String s = "Must have at least ";
            
        if(missing[0] < Kitchen.CRUST.getMin()){
            System.out.println(s + Kitchen.CRUST.getMin() +" " +Kitchen.CRUST.getName());
        }
        if(missing[1] < Kitchen.SAUCE.getMin()){
            System.out.println(s + Kitchen.SAUCE.getMin() +" " +Kitchen.SAUCE.getName());
        }
        if(missing[2] < Kitchen.TOPPING.getMin()){
            System.out.println(s + Kitchen.TOPPING.getMin() +" " +Kitchen.TOPPING.getName()+"s");
        }
    }
 
    
    private void isMaxString(Ingredient checkThis){
        if(checkThis.getCategory() != Kitchen.TOPPING){
            System.out.println("Can only add "+checkThis.getCategory().getMax() +" "+checkThis.getCategory());
        }else{
            System.out.println("Can only add "+checkThis.getCategory().getMax() +" "+checkThis.getCategory()+"s");
        }
    }
 
}