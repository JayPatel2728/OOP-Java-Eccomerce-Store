import java.util.Scanner;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)
	{
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();
			
			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;

			else if (action.equalsIgnoreCase("PRODS"))	// List all products for sale
			{
				amazon.printAllProducts(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all books for sale
			{
				amazon.printAllBooks(); 
			}
			else if (action.equalsIgnoreCase("CUSTS")) 	// List all registered customers
			{
				amazon.printCustomers();	
			}
			else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
			{
				amazon.printAllOrders();	
			}
			else if (action.equalsIgnoreCase("SHIPPED"))	// List all orders that have been shipped
			{
				amazon.printAllShippedOrders();	
			}
			else if (action.equalsIgnoreCase("NEWCUST"))	// Create a new registered customer
			{
				String name = "";
				String address = "";
				
				System.out.print("Name: ");
				if (scanner.hasNextLine())
					name = scanner.nextLine();
				
				System.out.print("\nAddress: ");
				if (scanner.hasNextLine())
					address = scanner.nextLine();
				
				try{
					amazon.createCustomer(name, address);
				}
				catch(InvalidCustomerAdress e){
					System.out.println(e.getMessage());
				}
				catch(InvalidCustomerName e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("SHIP"))	// ship an order to a customer
			{
					String orderNumber = "";
        
					System.out.print("Order Number: ");
					// Get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();
					// Ship order to customer (see ECommerceSystem for the correct method to use
					try{
						ProductOrder ship=amazon.shipOrder(orderNumber);
						ship.print();
					}
					catch(OrderNotFound e){
						System.out.println(e.getMessage());
					}
			}
			else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
			{
				String customerId = "";

				System.out.print("Customer Id: ");
				// Get customer Id from scanner
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();
				// Print all current orders and all shipped orders for this customer
				try{
					amazon.printOrderHistory(customerId);
				}
				catch(CustomerNotFound c)
				{
					System.out.println(c.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
			{
				String productId = "";
				String customerId = "";
				String orderNumber= "";
				System.out.print("Product Id: ");
			  // Get product Id from scanner
				if (scanner.hasNextLine())
					productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
			  // Get customer Id from scanner
			  	if (scanner.hasNextLine())
				    customerId = scanner.nextLine();
				// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem
				try{
					orderNumber = amazon.orderProduct(productId, customerId, "");
					// Print Order Number string returned from method in ECommerceSystem
					System.out.println("Order #"+orderNumber);
				}
				catch(CustomerNotFound e){
					System.out.println(e.getMessage());
				}
				catch(ProductNotFound e){
					System.out.println(e.getMessage());
				}
				catch(InvalidProductOption e){
					System.out.println(e.getMessage());
				}
				catch(WrongOrderMethod e){
					System.out.println(e.getMessage());
				}
				catch(InvalidStock e){
					System.out.println(e.getMessage());
				}	
			}
			else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
			{
				String productId = "";
				String customerId = "";
				String options = "";

				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine())
					productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine())
				    customerId = scanner.nextLine();
				System.out.print("\nFormat [Paperback Hardcover EBook]: ");
				// get book forma and store in options string
				if (scanner.hasNextLine())
					options = scanner.nextLine();
				// Order product. Check for error mesage set in ECommerceSystem
				String orderNumber = amazon.orderProduct(productId, customerId, options);
				// Print order number string if order number is not null
				if (orderNumber==null)
				{
					System.out.println(amazon.getErrorMessage());
				}
				else{
					System.out.println("Order #"+orderNumber);
				}

			}
			else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color 
			{
				String productId = "";
				String customerId = "";
				String options = "";
				
				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine())
					productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine())
				    customerId = scanner.nextLine();
				System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
				// get shoe size and store in options	
				if (scanner.hasNextLine())
					options = scanner.nextLine();
				System.out.print("\nColor: \"Black\" \"Brown\": ");
				// get shoe color and append to options
				if (scanner.hasNextLine())
					options = options+" "+scanner.nextLine();
				//order shoes
				String orderNumber = amazon.orderProduct(productId, customerId, options);
				if (orderNumber==null)
				{
					System.out.println(amazon.getErrorMessage());
				}
				else{
					System.out.println("Order #"+orderNumber);
				}

			}
			
			
			else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
			{
				String orderNumber = "";

				System.out.print("Order Number: ");
				// get order number from scanner
				if (scanner.hasNextLine())
					orderNumber = scanner.nextLine();
				// cancel order. Check for error
				try{
					amazon.cancelOrder(orderNumber);
				}
				catch(OrderNotFound e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("SORTBYPRICE")) // sort products by price
			{
				amazon.PRINTBYPRICE();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort products by name (alphabetic)
			{
				amazon.PRINTBYNAME();
			}
			else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
			{
				amazon.sortCustomersByName();
			}

			else if(action.equalsIgnoreCase("BOOKSBYAUTHOR"))
			{
				String author="";
				System.out.print("Author's Name: ");
				// get order number from scanner
				if (scanner.hasNextLine())
					author = scanner.nextLine();

				amazon.sortAuthorByYear(author);
			}
			else if (action.equalsIgnoreCase("STATS")) 
			{
				amazon.printStats();
			}
			else if (action.equalsIgnoreCase("ADDTOCART")) //adds item to cart
			{
				String productId = "";
				String customerId = "";
				String options = "";
				
				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine())
					productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine())
				    customerId = scanner.nextLine();
				if (productId.equals("702")||productId.equals("706")||productId.equals("707")||productId.equals("710")){
					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					if (scanner.hasNextLine()){
						options = scanner.nextLine();
					}
				}
				try{
					amazon.addToCart(productId,customerId,options);
					System.out.println("Product has been sucessfully added");
				}
				catch(CustomerNotFound e){
					System.out.println(e.getMessage());
				}
				catch(ProductNotFound e){
					System.out.println(e.getMessage());
				}
				catch(InvalidProductOption e){
					System.out.println(e.getMessage());
				}	
			}
			else if (action.equalsIgnoreCase("REMCARTITEM")) //removes item from cart
			{
				String productId = "";
				String customerId = "";
				
				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine())
					productId = scanner.nextLine();
				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine())
				    customerId = scanner.nextLine();
				try{
					amazon.removeFromCart(productId, customerId);
					System.out.println("Product has been sucessfully removed");
				}
				catch(CustomerNotFound e){
					System.out.println(e.getMessage());
				}
				catch(ProductNotFound e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PRINTCART")) //prints cart of customer
			{
				String customerId = "";

				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine())
				    customerId = scanner.nextLine();
				try{
					amazon.printCart(customerId);
				}
				catch(CustomerNotFound e){
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("ORDERITEMS")) //orders items in cart
			{
				String customerId = "";

				System.out.print("\nCustomer Id: ");
				// get customer id
				if (scanner.hasNextLine())
				    customerId = scanner.nextLine();
				
				amazon.orderItems(customerId);
				System.out.println("Orders have been put through");
				
			}
			else if(action.equalsIgnoreCase("ADDRATING")){ //lets user add rating to a product
				String productId = "";
				double rating=0;
				
				System.out.print("Product Id: ");
				// get product id
				if (scanner.hasNextLine())
					productId = scanner.nextLine();

				System.out.print("Product Rating Out of 5: ");
				// get product id
				if (scanner.hasNextLine())
					rating = scanner.nextDouble();
				
				try{
					amazon.productRating(productId,rating);
					System.out.println("Rating has been added!");
				}
				catch(ProductNotFound e)
				{
					System.out.println(e.getMessage());
				}
			}

			else if(action.equalsIgnoreCase("GETBYRATING")){ //lets user view product by category and rating
				String category = "";
				double rating=0;
				
				System.out.print("What Category would you like to view: ");
				// get category
				if (scanner.hasNextLine())
					category = scanner.nextLine();

				System.out.print("What ratings would you like to view above? (Eg. If you picked 3, all ratings 3 and above would be shown): ");
				// get rating
				if (scanner.hasNextLine())
					rating = scanner.nextDouble();

				try{
					amazon.getproductRating(category, rating);
				}
				catch(InvalidRating e){
					System.out.println(e.getMessage());
				}
			}
			System.out.print("\n>");
		}
	}
}
