import java.util.*;
/**
 * 1. The Pizzeria constructor has no constructor parameters and must create the kitchen and do nothing else.
 * 
 * @author (Clint Sellen) 
 * @version (17/4/17)
 */
public class Pizzeria
{
    //Pizzeria Variables
    private Kitchen kitchen;
    private LinkedList<Customer> customers = new LinkedList<Customer>();
    
    //Main Function
    
    public static void main(String args[])
    {
        new Pizzeria().use();
    }
    
    //Constructor
    
      public Pizzeria()
    {
        kitchen = new Kitchen();
    }
    
    //Menu Function
      
    private void use()
    {
        System.out.println("Welcome to Luigi's Pizzeria!");
        char choice;
        while((choice =  readChoice()) != 'x'){
            switch (choice){
                case 'a': add(); break;
                case 'v': view(); break;
                case 's': serve(); break;
                case 'r': report(); break;
                default: help();
            }
        }
    }

    //Menu Functions
    
    private void add()
    {
        System.out.print("Phone: ");
        String phone = In.nextLine(); //get phone number
        if(findCustomer(phone) != null)
            System.out.println("An existing customer has that phone number");
        else {
            System.out.print("Name: ");
            String name = In.nextLine(); //get name
            customers.add(new Customer(phone, name)); //add to customers List
        }
    }
    
    private void view()
    {
        for(Customer customer : customers)
            System.out.println(customer);
    }
    
    private void serve()
    {
        System.out.print("Phone: "); 
        String phone = In.nextLine();
        Customer toServe = findCustomer(phone);
        if(toServe != null){
            System.out.println("Serving "+toServe.getName());
            toServe.serve(kitchen); //Passes right
            System.out.println();
        }else{
            System.out.println("*****New Customer****");//add();
        }
    }
    
    private void report()
    {   
        System.out.println("Income: $"+kitchen.kitchenReport());
    }
    
    private void help()
    {
        //System.out.print("Pizzeria choice (a/v/s/r/x): ");
        System.out.println("a = add customer");
        System.out.println("v = view customers");
        System.out.println("s = serve customer");
        System.out.println("r = show report");
        System.out.println("x = exit");
    }
    
    private char readChoice()
    {
        System.out.print("Pizzeria choice (a/v/s/r/x): ");
        return In.nextChar();
    }
    
    //Private Functions
    
    private Customer findCustomer(String phone)
    {
        for(Customer customer : customers)
            if(customer.isCustomer(phone))
                return customer;
        return null;
    }
    
    
}



