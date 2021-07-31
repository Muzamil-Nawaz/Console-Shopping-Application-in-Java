
/**
 * Super class of all products with mandatory attributes of a product to represent
 *
 * @author
 */
class Product {

    // Declaring all attributes/details of each product to be added
    String productName;
    byte productId;
    int stock;
    String type;
    float price;

    public Product() {
    }

    // Constructor to initialize above products' attributes with provided data
    public Product(String productName, byte productId, int stock, String type, float price) {
        this.productName = productName;
        this.productId = productId;
        this.stock = stock;
        this.type = type;
        this.price = price;
    }

    // Method for getting name of current product
    public String getProductName() {

        return productName;
    }

    // method for setting name attribute of current product
    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Method for getting id of current product
    public int getProductId() {
        return productId;
    }

    // method for setting id attribute of current product
    public void setProductId(byte productId) {
        this.productId = productId;
    }

    // Method for getting stock quantity of current product
    public int getStock() {
        return stock;
    }

    // method for setting stock attribute of current product
    public void setStock(int stock) {
        this.stock = stock;
    }

    // Method for getting type of current product
    public String getType() {
        return type;
    }

    // method for setting type attribute of current product
    public void setType(String type) {
        this.type = type;
    }

    // Method for getting price of current product
    public double getPrice() {
        return price;
    }

    // method for setting price attribute of current product
    public void setPrice(float price) {
        this.price = price;
    }

    // write method to be overrided in each sub product class
    public void write() {
    }

    // read method to be overrided in each sub product class
    public void read() {

    }

}
