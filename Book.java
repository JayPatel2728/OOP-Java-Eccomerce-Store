/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product 
{
  private String author;
  private String title;
  private int year;
  public int stockCount=1000000;
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  private int paperbackStock;
  private int hardcoverStock;
  
  
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, int year)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS 
     super(name, id, price, 1000000, Product.Category.BOOKS,0,0,0,0,0);
     this.title = title;
     this.author = author;
     this.paperbackStock = paperbackStock;
     this.hardcoverStock = hardcoverStock;
     this.year=year;

  }

  //method to get the author of the book
  public String getAuthor()
  {
    return this.author;
  }

  //method to get year published of book
  public int getYear()
  {
    return this.year;
  }
    
  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false
  	if (productOptions.equalsIgnoreCase("Paperback")||productOptions.equalsIgnoreCase("Hardcover")||productOptions.equalsIgnoreCase("Ebook"))
    {
      return true;
    }
    return false;
  }
  
  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and return) the number of stock for "Paperback" etc
  	// Use the variables paperbackStock and hardcoverStock at the top. 
  	// For "EBook", use the inherited stockCount variable.
    if(productOptions.equalsIgnoreCase("Paperback"))
    {
      return this.paperbackStock;
    }
    else if(productOptions.equalsIgnoreCase("Hardcover"))
    {
      return this.hardcoverStock;
    }
    else
    {
      return this.stockCount;
    }
  }
  
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
     if(productOptions.equalsIgnoreCase("Paperback"))
     {
      super.setStockCount(paperbackStock, productOptions);
     }
     if(productOptions.equalsIgnoreCase("Hardcover"))
     {
      super.setStockCount(hardcoverStock, productOptions);
     }
     if(productOptions.equalsIgnoreCase("Etext"))
     {
      super.setStockCount(stockCount, productOptions);
     }
	}
  
  /*
   * When a book is ordered, reduce the stock count for the specific stock type
   */
  public void reduceStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.
     if(productOptions.equalsIgnoreCase("Paperback"))
     {
       this.paperbackStock--;
     }
     if(productOptions.equalsIgnoreCase("Hardcover"))
     {
      this.hardcoverStock--;
     }
     if(productOptions.equalsIgnoreCase("Etext"))
     {
      this.stockCount--;
     }
	}
  /*
   * Print product information in super class and append Book specific information title and author
   */
  public void print()
  {
  	// Replace the line below.
  	// Make use of the super class print() method and append the title and author info. See the video
    super.print();
  	System.out.print(" Book Title: "+this.title+" Author: "+this.author+" Year: "+this.year);
  }
}
