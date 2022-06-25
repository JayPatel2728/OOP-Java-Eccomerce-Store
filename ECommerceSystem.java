import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Arrays;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    private TreeMap<String,Product> products=new TreeMap<String,Product>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();	
    
    private HashMap<Product,Double> prodRatings=new HashMap<Product,Double>();
    private TreeMap<String,Integer> orderstats=new TreeMap<String,Integer>();
    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();
    
    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;

    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem()
    {
      //adding the products to a map from the text file
      try{
        String f1="products.txt";
        products=creatingProducts(f1);
      }
      catch(FileNotFoundException e)
      {
        System.out.println("File not found");
        System.exit(0);
      }
    	// NOTE: do not modify or add to these objects!! - the TAs will use for testing

    	// Create some customers. Notice how generateCustomerId() method is used
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
    }

    //method that takes in a filename and creates a file which it then reads to get various variables
    private TreeMap<String,Product> creatingProducts(String filename) throws FileNotFoundException{
      //counter variable
      int count=-1;

      //variables to make the products
      String name="";
      String id="";
      double price=0;
      int stock=0;
      int paperbackStock=0;
      int hardcoverStock=0;
      String title="";
      String author="";
      int year=0;
      Product.Category category=Product.Category.BOOKS;

      //while loop using scanner to get the string at each line 
      File file=new File(filename);
      Scanner scanner=new Scanner(file);
      while(scanner.hasNext()){
        String elem=scanner.nextLine();
        count++;
        if(count==0){
          if(elem.equals("BOOK"))
            category=Product.Category.BOOKS;
          if(elem.equals("CLOTHING"))
            category=Product.Category.CLOTHING;
          if(elem.equals("FURNITURE"))
            category=Product.Category.FURNITURE;
          if(elem.equals("COMPUTERS"))
            category=Product.Category.COMPUTERS;
          if(elem.equals("GENERAL"))
            category=Product.Category.GENERAL;
        }
        if(count==1){
          name=elem;
        }
        if(count==2){
          price=Double.parseDouble(elem);
        }
        if(name.equals("Book") && count==3){
          String[] arr = elem.split(" ");  
          paperbackStock=Integer.parseInt(arr[0]);
          hardcoverStock=Integer.parseInt(arr[1]);
        }
        if(!name.equals("Book") && count==3){
          stock=Integer.parseInt(elem);
        }
        if(name.equals("Book") && count==4){
          ArrayList<String> bookinfo=new ArrayList<String>(Arrays.asList(elem.split(":")));
          title=bookinfo.get(0);
          author=bookinfo.get(1);
          year=Integer.parseInt(bookinfo.get(2));
          id=generateProductId();
          products.put(id, new Book(name, id, price, paperbackStock, hardcoverStock, title, author, year));
          orderstats.put("Name: "+name+" ID: "+id,0);
          count=-1;
        }
        if(!name.equals("Book") && count==4){
          id=generateProductId();
          products.put(id, new Product(name, id, price, stock, category,0,0,0,0,0));
          orderstats.put("Name: "+name+" ID: "+id,0);
          count=-1;
        }
      }
      return products;
    }
    
    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    public String getErrorMessage()
    {
    	return errMsg;
    }
    
    public void printAllProducts()
    {
    	for (Product prod : products.values())
        prod.print();
    }
    
    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
    	for (Product p : products.values())
        if (p.getCategory().equals(Product.Category.BOOKS))
    		  p.print();
    }
    
    // Print all current orders
    public void printAllOrders()
    {
      for (ProductOrder p : orders)
    		p.print();
    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
    	for (ProductOrder p : shippedOrders)
    		p.print();
    }
    
    // Print all customers
    public void printCustomers()
    {
    	for (Customer cust : customers)
    		cust.print();
    }
    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     */
    public void printOrderHistory(String customerId) throws CustomerNotFound
    {
      // Make sure customer exists - check using customerId
    	// If customer does not exist, set errMsg String and return false
    	// see video for an appropriate error message string
    	// ... code here
      Customer cust=getCustomer(customerId);
      if (cust==null){
        throw new CustomerNotFound("Customer Was Not Found");
      }
    	
    	// Print current orders of this customer 
    	System.out.println("Current Orders of Customer " + customerId);
    	// enter code here
    	for (ProductOrder c:orders){
        Customer l=c.getCustomer();
        if(l.getId().equals(customerId)){
          c.print();
        }
      }
    	// Print shipped orders of this customer 
    	System.out.println("\nShipped Orders of Customer " + customerId);
    	//enter code here
    	for (ProductOrder c:shippedOrders){
        Customer l=c.getCustomer();
        if(l.getId().equals(customerId)){
          c.print();
        }
      }
    }
    
    public String orderProduct(String productId, String customerId, String productOptions) throws CustomerNotFound, ProductNotFound, InvalidProductOption, WrongOrderMethod, InvalidStock
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Customer object
      
      Customer getCust=getCustomer(customerId);
      if (getCust==null){
        throw new CustomerNotFound("Customer Was Not Found");
      }

    	// Check to see if product object with productId exists in array list of products
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Product object 
    	Product getProd=getProduct(productId);
      if (getProd==null){
        throw new ProductNotFound("Product Was Not Found");
      }
    	
    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method vaidOptions()
    	// If options are not valid, set errMsg string and return null;

      //Checking to see if product is shoe or book so correct error message is printed out
      boolean isShoe=false;
      boolean isBook=false;
      if(productId.equals("710")||productId.equals("711"))
            isShoe=true;
      if(productId.equals("702")||productId.equals("706")||productId.equals("707")||productId.equals("708"))
            isBook=true;
      
      boolean isValid=false;
      for (Product p: products.values()){
        if(p.getId().equals(productId) && p.validOptions(productOptions)==true){
          isValid=true;
          break;
        }
      }
      if (isValid==false && isBook==true){
        throw new InvalidProductOption("Product Book Product Id "+productId+" Invalid Options: "+productOptions);
      }
      if (isValid==false && isShoe==true){
        throw new InvalidProductOption("Product Shoe Product Id "+productId+" Invalid Options: "+productOptions);
      }

      if(isValid==false && isBook==false && isShoe==false)
      {
        throw new WrongOrderMethod("The product you selected is part of the "+ getProd.getCategory()+ " categoery please use the correct order method!");
      }
    
    	
    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, set errMsg string and return null
    	boolean isStockcount=false;
      for (Product p: products.values()){
        if(p.getId().equals(productId) && p.getStockCount(productOptions)!=0){
          isStockcount=true;
          break;
        }
      }

      //Checking to see if product has stock and prining out error message in accordance to the item 
      if (isStockcount==false && isBook==true){
        throw new InvalidStock("Product Book Product Id "+productId+" Stock Not Available");
      }
      if (isStockcount==false && isShoe==true){
        throw new InvalidStock("Product Shoe Product Id "+productId+" Stock Not Available");
      }
      

      // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string
      String orderNumber=generateOrderNumber();
      for (Product p: products.values()){
        if(p.getId().equals(productId) && p.getStockCount(productOptions)!=0){
          p.reduceStockCount(productOptions);
        }
      }

      for(String elem : orderstats.keySet())
      {
        if(elem.contains(productId))
        {
          orderstats.put(elem, orderstats.get(elem)+1);
        }
      }
      orders.add(new ProductOrder(orderNumber, getProd, getCust, productOptions));
    	return orderNumber;    	
    	
    }
    
    /*
     * Create a new Customer object and add it to the list of customers
     */
    
    public void createCustomer(String name, String address) throws InvalidCustomerName,InvalidCustomerAdress
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, set errMsg (see video) and return false
    	// Repeat this check for address parameter
    	if (name==null||name.equals("")){
        throw new InvalidCustomerName("Invalid customer name");
      }
      if (address==null||address.equals("")){
        throw new InvalidCustomerAdress("Invalid customer address");
      }
      customers.add(new Customer(generateCustomerId(),name, address));
    	// Create a Customer object and add to array list
    }
    
    public ProductOrder shipOrder(String orderNumber) throws OrderNotFound
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	// Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
    	// return a reference to the order
      boolean isOrder=false;
      ProductOrder getShip=null;
      for (ProductOrder p: orders){
        if(p.getOrderNumber().equals(orderNumber)){
          getShip=p;
          shippedOrders.add(p);
          orders.remove(p);
          isOrder=true;
          break;
        }
      }
      if (isOrder==false){
        throw new OrderNotFound("Order "+orderNumber+" Not Found");
      }
    	return getShip;
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public void cancelOrder(String orderNumber) throws OrderNotFound
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	boolean cancelOrder=false;
      for (ProductOrder p: orders){
        if(p.getOrderNumber().equals(orderNumber)){
          orders.remove(p);
          cancelOrder=true;
          break;
        }
      }
      if (cancelOrder==false){
        throw new OrderNotFound("Order "+orderNumber+" Not Found");
      }
    }
    
    // Sort products by increasing price
    public void PRINTBYPRICE()
    {
      ArrayList<Product> prodbyprice=new ArrayList<Product>();
      for(Product p : products.values()){
        prodbyprice.add(p);
      }
  	  Comparator<Product> productPriceComparator = new Comparator<Product>() 
      {
        public int compare(Product p1, Product p2){
          if(p1.getPrice() < p2.getPrice()){
            return -1;
          } 
          else if (p1.getPrice() > p2.getPrice()){
            return 1;
          } 
          else{
            return 0;
          }
        }
      };
  	  Collections.sort(prodbyprice,productPriceComparator);

      for (Product b: prodbyprice){
        b.print();
      }
    }
    
    // Sort products alphabetically by product name
    public void PRINTBYNAME()
    {
      ArrayList<Product> prodbyname=new ArrayList<Product>();
      for(Product p : products.values()){
        prodbyname.add(p);
      }
  	  Comparator<Product> productNameComparator = new Comparator<Product>() 
      {
        public int compare(Product p1, Product p2){
            return p1.getName().compareTo(p2.getName());
        }
      };
  	  Collections.sort(prodbyname,productNameComparator);

      for (Product b: prodbyname){
        b.print();
      }
    }

    // Sort customers alphabetically by customer name
    public void sortCustomersByName()
    {
  	  Collections.sort(customers);
    }


    // Method to get authors books and sort by year they were created
    public void sortAuthorByYear(String author)
    {
      //getting all books from products into arraylist
      ArrayList<Book> books=new ArrayList<Book>();
      for (Product p : products.values()){
        if (p.getCategory().equals(Product.Category.BOOKS)){
          books.add((Book) p);
        }
      }
      
      //checking for authors name in the book, of name is there book goes into another arraylist of authorbooks
      ArrayList<Book> Authorbooks=new ArrayList<Book>();
      for (Book b: books){
        if(b.getAuthor().equalsIgnoreCase(author)){
          Authorbooks.add(b);
        }
      }

      //comparator used to compare the year of the authorbooks arraylist
      Comparator<Book> authorYearComparator = new Comparator<Book>() 
      {
        public int compare(Book p1, Book p2){
          if(p1.getYear() < p2.getYear()){
            return -1;
          } 
          else if (p1.getYear() > p2.getYear()){
            return 1;
          } 
          else{
            return 0;
          }
        }
      };
      Collections.sort(Authorbooks,authorYearComparator);

      //printing the authorbooks arraylist
      for (Book b: Authorbooks){
        b.print();
      }
    }

    /**
     * Takes product ID, customer ID and product options and adds the specific product to the customer cart.
     * @param productId
     * @param customerId
     * @param options
     * @throws CustomerNotFound
     * @throws ProductNotFound
     * @throws InvalidProductOption
     */
    public void addToCart(String productId,String customerId, String options) throws CustomerNotFound, ProductNotFound, InvalidProductOption{
      //getting the product
      Product getProd=getProduct(productId);
      if (getProd==null){
        throw new ProductNotFound("Product Was Not Found");
      }

      //checking if the option is valid for books
      boolean isBook=false;
      if(productId.equals("702")||productId.equals("706")||productId.equals("707")||productId.equals("708"))
            isBook=true;
      
      boolean isValid=false;
      for (Product p: products.values()){
        if(p.getId().equals(productId) && p.validOptions(options)==true){
          isValid=true;
          break;
        }
      }
      if (isValid==false && isBook==true){
        throw new InvalidProductOption("Product Book Product Id "+productId+" Invalid Options: "+options);
      }

      //getting the customer and adding the product to the cart
      Customer cust=getCustomer(customerId);
      if (cust==null){
        throw new CustomerNotFound("Customer Was Not Found");
      }
      else{
        cust.getCart().addToCart(getProd, options);
      }
    }

    /**
     * Takes product ID and customer ID, removes the specific product from the customer cart.
     * @param productId
     * @param customerId
     * @throws CustomerNotFound
     * @throws ProductNotFound
     */
    public void removeFromCart(String productId,String customerId) throws CustomerNotFound, ProductNotFound
    {
      //getting the product
      Product getProd=getProduct(productId);
      if (getProd==null){
        throw new ProductNotFound("Product Was Not Found");
      }
      //getting the customer and removing the product
      Customer cust=getCustomer(customerId);
      if (cust==null){
        throw new CustomerNotFound("Customer Was Not Found");
      }
      else{
        cust.getCart().removeFromCart(getProd);
      }
    }

    /**
     * takes customer ID and orders the products in the cart
     * @param customerId
     */
    public void orderItems(String customerId){
      //getting the specific customer
      for(Customer c:customers){
        if(c.getId().equals(customerId)){
          //getting the list of products in the cart from the customers cart
          LinkedList<CartItem> cartItems=c.getCart().getcartItems();
          //generating an order for each item
          for(CartItem item:cartItems){
            orderProduct(item.getProduct().getId(), c.getId(), item.getProductOptions());
          }
          //clearing the customers cart
          c.getCart().getcartItems().clear();
        }
      }
    }

    /**
     * takes customer ID and prints customers cart
     * @param customerId
     * @throws CustomerNotFound
     */
    public void printCart(String customerId) throws CustomerNotFound{
      //getting the customer
      Customer cust=getCustomer(customerId);
      if (cust==null){
        throw new CustomerNotFound("Customer Was Not Found");
      }
      else{
        cust.getCart().printCart();
      }
    }

    /**
     * prining the stats of ordered products
     */
    public void printStats()
    {
      //using java streams to sort the map
      LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();orderstats.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
      //printing the map using key and entry
      for (Entry<String, Integer> entry : sortedMap.entrySet()) {
        String key = entry.getKey();
        int value = entry.getValue();
        System.out.println(key+" Times Ordered: "+value);
      }
    }

    /**
     * takes rating and adds it to prodRating map
     * @param productId
     * @param rating
     * @throws InvalidRating
     */
    public void productRating(String productId, double rating) throws InvalidRating{
      Product getProd=getProduct(productId);
      if (getProd==null){
        throw new ProductNotFound("Product Was Not Found");
      }
      
      if(rating>5 && rating<1){
        throw new InvalidRating("Invalid Rating");
      }

      boolean isRating=false;
      for (Entry<Product, Double> entry:prodRatings.entrySet()){
        Product key = entry.getKey();
        if(getProd.equals(key)){
          increaseRating(rating, getProd);
          int totalRating=getProd.getOneStar()+getProd.getTwoStar()+getProd.getThreeStar()+getProd.getFourStar()+getProd.getFiveStar();
          double tempRating=((getProd.getOneStar()*1)+(getProd.getTwoStar()*2)+(getProd.getThreeStar()*3)+(getProd.getFourStar()*4)+(getProd.getFiveStar()*5))/totalRating;
          prodRatings.put(getProd, tempRating);
          isRating=true;
        }
      }
      if(isRating==false){
        increaseRating(rating, getProd);
        prodRatings.put(getProd, rating);
      }
    }

    /**
     * lets user view ratings given category and what theyd like to view above
     * @param category
     * @param rating
     * @throws InvalidRating
     */
    public void getproductRating(String category, double rating) throws InvalidRating{
      if(rating>5 && rating<1){
        throw new InvalidRating("Invalid Rating");
      }

      for (Entry<Product, Double> entry:prodRatings.entrySet()){
        Product key =entry.getKey();
        double value= entry.getValue();
        if(key.getCategory().equals(Product.Category.valueOf(category.toUpperCase()))){
            if(value>=rating){
              System.out.println("Name:"+key.getName()+" ID:"+key.getId()+" Rating:"+value);
            }
        }
      }
    }

    //helper function to increase rating 
    private void increaseRating(Double rating, Product getProd){
      if(rating==1){
        getProd.increaseOneStar();
      }
      if(rating==2){
        getProd.increaseTwoStar();
      }
      if(rating==3){
        getProd.increaseThreeStar();
      }
      if(rating==4){
        getProd.increaseFourStar();
      }
      if(rating==5){
        getProd.increaseFiveStar();
      }
    }

    //helper function to get the specific product given product id
    private Product getProduct(String productID){
      for (Product p: products.values()){
        if(p.getId().equals(productID)){
          return p;
        }
      }
      return null;
    }

    //helper function to get the specific customer given customer id
    private Customer getCustomer(String customerID){
      for (Customer c: customers){
        if(c.getId().equals(customerID)){
          return c;
        }
      }
      return null;
    }
}

//All exception classes used for various methods in the ecommercesystem and eccomerce userinterface
class CustomerNotFound extends RuntimeException { 
  public CustomerNotFound(String errorMessage) {
      super(errorMessage);
  }
}

class ProductNotFound extends RuntimeException { 
  public ProductNotFound(String errorMessage) {
      super(errorMessage);
    }
}

class InvalidProductOption extends RuntimeException { 
  public InvalidProductOption(String errorMessage) {
      super(errorMessage);
    }
}

class WrongOrderMethod extends RuntimeException { 
  public WrongOrderMethod(String errorMessage) {
      super(errorMessage);
    }
}

class InvalidStock extends RuntimeException { 
  public InvalidStock(String errorMessage) {
      super(errorMessage);
    }
}

class InvalidCustomerName extends RuntimeException { 
  public InvalidCustomerName(String errorMessage) {
      super(errorMessage);
    }
}

class InvalidCustomerAdress extends RuntimeException { 
  public InvalidCustomerAdress(String errorMessage) {
      super(errorMessage);
    }
}

class OrderNotFound extends RuntimeException { 
  public OrderNotFound(String errorMessage) {
      super(errorMessage);
    }
}

class InvalidRating extends RuntimeException { 
  public InvalidRating(String errorMessage) {
      super(errorMessage);
    }
}