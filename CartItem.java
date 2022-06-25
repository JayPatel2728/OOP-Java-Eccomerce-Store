public class CartItem {
    private Product prod;
    private String prodOptions;
    
    //constructor to get the product and product options
    CartItem(Product prod,String prodOptions)
    {
        this.prod=prod;
        this.prodOptions=prodOptions;
    }

    //method to return the product
    public Product getProduct()
    {
        return prod;
    }

    //method to return productoptions
    public String getProductOptions()
    {
        return prodOptions;
    }
}
