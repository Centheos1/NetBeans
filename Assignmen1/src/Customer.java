import java.util.*;
import java.text.*;
/**
 * 1. The Customer constructor must initialise the phone and name from parameters.
 * 
 * @author (Clint Sellen)
 * @version (17/4/17)
 */
public class Customer
{
    //Customer Variables
    
    private String phone;
    private String name;
    private LinkedList<Pizza> ordered = new LinkedList<Pizza>();
    private LinkedList<Pizza> order = new LinkedList<Pizza>();
    
    //Constructor
    
    public Customer(String phone, String name)
    {
        this.phone = phone;
        this.name = name;
    }
    
    //Public Functions
    
    public boolean isCustomer(String phone)
    {
        return phone.equals(this.phone);
    }
    
    //Customer Menu Function
   
    public void serve(Kitchen kitchen)
    { 
        char choice = getChoice();
        switch (choice){
            case 'c': createPizza(kitchen); break;
            case 'p': selectPizza(kitchen); break;
            case 'o': submitOrder(); break;
        }
    }
    
    public String getName(){
        return name;
    }
    
    public void addToOrdered(Pizza pizza){
        ordered.add(pizza);
    }
    
    public LinkedList<Pizza> getOrder(){
        return order;
    }
    
    //Menu Funcitons
    
    private void selectPizza(Kitchen kitchen)
    {
        System.out.println("Select from popular pizzas:");
        int count = 1;
        
        for(Pizza p : ordered){            
            System.out.println(count +". ("+p.getSold()+"x) "+ p.toString());
            count++;
            if(count > 3)
                break;
        }
        
        Pizza p = ordered.get(readChoice());
        order.add(p);
        p.incrementSold();
        orderSummary();
        serve(kitchen);
        
    }
    
    
    private void selectionSort(LinkedList<Pizza> list){
        int startScan, maxVal, maxIndex;
        Pizza mostPopPizza = new Pizza();
        
        for(startScan = 0; startScan < list.size() && startScan < 2; startScan++){
            maxIndex = startScan;
            maxVal = list.get(startScan).getSold();
            mostPopPizza = list.get(startScan);
            
            for(int i = startScan+1; i < list.size() && i < 3; i++){
                if(list.get(i).getSold() > maxVal 
                        || (list.get(i).getSold() <= 1
                        && list.get(i).getSold() == maxVal 
                        && list.get(i).getPrice() > list.get(maxIndex).getPrice())){
                    
                    maxVal = list.get(i).getSold();
                    mostPopPizza = list.get(i);
                    
                    maxIndex = i;
                }
            }
            list.set(maxIndex, list.get(startScan));
            list.set(startScan, mostPopPizza);
        }
    }
    
    
    
    private void submitOrder()
    {
        boolean isValid = false;
        
        for( Pizza pizza : order){
            isValid = pizza.orderComplete(this); //This executes the order
        }
        
        if(isValid){
            System.out.println("Order submitted");
            order.clear();
        }else{
            System.out.println("Empty order discarded");
        }
    }
    
    private void help()
    {
        //System.out.println("Customer choice (c/p/o): ");
        System.out.println("c = create new pizza");
        System.out.println("p = select from popular past pizzas");
        System.out.println("o = submit order");
    }
    
    private char getChoice()
    {
        System.out.print("Customer choice (c/p/o): ");
        char choice = In.nextChar();
        String validInput = "cpo";
        while(!validInput.contains(choice +"")) {
            help();
            choice = getChoice();
        }
        return choice;
    }
    
    
    //Private Functions
    
    private void createPizza(Kitchen kitchen)
    {
        System.out.println("Creating new pizza");
        Pizza pizza = new Pizza();
        order.add(pizza.build(kitchen));
        pizza.incrementSold();
        orderSummary();
        serve(kitchen);
        
    }
    
    private void orderSummary(){
        double total = 0.0;
        selectionSort(ordered);
        System.out.println("ORDER SUMMARY");
        for(Pizza pizza : order){
            System.out.println(pizza);
            total += pizza.getPrice();
        }
        System.out.println("Total: $" + formatePrice(total));          
    }
    
    private String formatePrice(double price){
        return new DecimalFormat("##0.00").format(price);
    }
    
    private int readChoice()
    {
        System.out.print("Selection: ");
        return In.nextInt()-1;
    }
    
    
    //toString Function
    
    @Override
    public String toString()
    {
        return name + ": " + phone;
    }
}
