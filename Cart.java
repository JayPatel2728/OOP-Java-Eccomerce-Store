import java.util.LinkedList;
public class Cart{
    private LinkedList<CartItem> items;

    //creating a list of items for each cart
    Cart()
    {
        items=new LinkedList<CartItem>();
    }

    //method to add a product to the list of items in cart
    public void addToCart(Product prod, String prodOptions)
    {
        items.add(new CartItem(prod,prodOptions));
    }

    //method to remove a product from the list of items
    public void removeFromCart(Product prod)
    {
        for(CartItem item:items){
            if(item.getProduct().equals(prod)){
                items.remove(item);
            }
        }
    }

    //method to print the cart
    public void printCart()
    {
        for(CartItem item: items){
            item.getProduct().print();
        }
    }

    //mehtod to get the cartitems list
    public LinkedList<CartItem> getcartItems(){
        return items;
    }
}
