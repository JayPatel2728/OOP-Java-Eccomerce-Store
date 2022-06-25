public class Shoes extends Product 
{
    //Variables created to store stock, sizes and color of shoes
    private int [] shoeSize=new int[]{6,7,8,9,10};
    private String [] colorOptions=new String[]{"black","brown"};
    private int blackshoesStockSize6,blackshoesStockSize7,blackshoesStockSize8,blackshoesStockSize9,blackshoesStockSize10;
    private int brownshoesStockSize6,brownshoesStockSize7,brownshoesStockSize8,brownshoesStockSize9,brownshoesStockSize10;

    //Constructor to build each shoe
    public Shoes(String name, String id, double price)
    {
        super(name, id, price, 100, Product.Category.CLOTHING,0,0,0,0,0);
        blackshoesStockSize6=5;
        blackshoesStockSize7=5;
        blackshoesStockSize8=5;
        blackshoesStockSize9=5;
        blackshoesStockSize10=5;
        brownshoesStockSize6=5;
        brownshoesStockSize7=5;
        brownshoesStockSize8=5;
        brownshoesStockSize9=5;
        brownshoesStockSize10=5;
    }

    //Method to check if input from user is valid
    public boolean validOptions(String productOptions)
    {
        for(int i=0; i<shoeSize.length;i++)
        {
            for(int j=0; j<colorOptions.length;j++)
            {
                if(productOptions.equals(shoeSize[i]+" "+colorOptions[j]))
                {
                    return true;
                }
            }
        }
        return false;
    }

    //Method to get stock count of specicfic shoe
    public int getStockCount(String productOptions)
	{
        if (productOptions.toLowerCase().contains("black")){
            if (productOptions.toLowerCase().contains("6")){
                return this.blackshoesStockSize6;
            }
            else if (productOptions.toLowerCase().contains("7")){
                return this.blackshoesStockSize7;
            }
            else if (productOptions.toLowerCase().contains("8")){
                return this.blackshoesStockSize8;
            }
            else if (productOptions.toLowerCase().contains("9")){
                return this.blackshoesStockSize9;
            }
            else{
                return this.blackshoesStockSize10;
            }
        }
        else{
            if (productOptions.toLowerCase().contains("6")){
                return this.brownshoesStockSize6;
            }
            else if (productOptions.toLowerCase().contains("7")){
                return this.brownshoesStockSize7;
            }
            else if (productOptions.toLowerCase().contains("8")){
                return this.brownshoesStockSize8;
            }
            else if (productOptions.toLowerCase().contains("9")){
                return this.brownshoesStockSize9;
            }
            else{
                return this.brownshoesStockSize10;
            }
        }
    }

    //Method to set the stock count of shoe
    public void setStockCount(int stockCount, String productOptions)
	{
        if (productOptions.toLowerCase().contains("black"))
        {
            if (productOptions.toLowerCase().contains("6")){
                super.setStockCount(blackshoesStockSize6, productOptions);
            }
            else if (productOptions.toLowerCase().contains("7")){
                super.setStockCount(blackshoesStockSize7, productOptions);
            }
            else if (productOptions.toLowerCase().contains("8")){
                super.setStockCount(blackshoesStockSize8, productOptions);
            }
            else if (productOptions.toLowerCase().contains("9")){
                super.setStockCount(blackshoesStockSize9, productOptions);
            }
            else{
                super.setStockCount(blackshoesStockSize10, productOptions);
            }
        }
        if (productOptions.toLowerCase().contains("brown"))
        {
            if (productOptions.toLowerCase().contains("6")){
                super.setStockCount(brownshoesStockSize6, productOptions);
            }
            else if (productOptions.toLowerCase().contains("7")){
                super.setStockCount(brownshoesStockSize7, productOptions);
            }
            else if (productOptions.toLowerCase().contains("8")){
                super.setStockCount(brownshoesStockSize8, productOptions);
            }
            else if (productOptions.toLowerCase().contains("9")){
                super.setStockCount(brownshoesStockSize9, productOptions);
            }
            else{
                super.setStockCount(brownshoesStockSize10, productOptions);
            }
        }
    }

    //Method to reduce stock count of specific shoe by 1
    public void reduceStockCount(String productOptions)
	{
        if (productOptions.toLowerCase().contains("black"))
        {
            if (productOptions.toLowerCase().contains("6")){
                this.blackshoesStockSize6--;
            }
            else if (productOptions.toLowerCase().contains("7")){
                this.blackshoesStockSize7--;
            }
            else if (productOptions.toLowerCase().contains("8")){
                this.blackshoesStockSize8--;
            }
            else if (productOptions.toLowerCase().contains("9")){
                this.blackshoesStockSize9--;
            }
            else{
                this.blackshoesStockSize10--;
            }
        }
        if (productOptions.toLowerCase().contains("brown"))
        {
            if (productOptions.toLowerCase().contains("6")){
                this.brownshoesStockSize6--;
            }
            else if (productOptions.toLowerCase().contains("7")){
                this.brownshoesStockSize7--;
            }
            else if (productOptions.toLowerCase().contains("8")){
                this.brownshoesStockSize8--;
            }
            else if (productOptions.toLowerCase().contains("9")){
                this.brownshoesStockSize9--;
            }
            else{
                this.brownshoesStockSize10--;
            }
        }
    }
}