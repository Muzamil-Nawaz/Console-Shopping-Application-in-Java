
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is main class which is supposed to operate the whole application and use
 * different classes within it
 *
 * @author
 */
public class MainApplication {

    // array for storing products list from products' binary file
    static ArrayList<Product> products;
    // array for storing products which are being added in the cart
    static ArrayList shoppingCart = new ArrayList();

    // scanner object to get the input from user
    static Scanner keyboard = new Scanner(System.in);
    // variable to store id of customer currently logged in
    static int customerID;

    public static void main(String[] args) throws IOException {
        // initializing product array
        products = new ArrayList<>();
        // getting product data from products' binary file
        readProductsData();
        // calling the menu method to display menu on console
        menu();

    }

    // Method responsible for getting customer to log in and iteratively displaying menu to the user
    public static void menu() {
        // Getting customer's name and password
        System.out.print("Enter customer name to login:");
        String name = keyboard.nextLine();
        System.out.print("Enter customer pass to login:");
        String pass = keyboard.nextLine();

        // Check if customer with name and pass exists
        if (checkCustomer(name, pass)) {
            // running the loop for menu
            while (true) {

                // welcoming customer
                System.out.println("*************************** Welcome " + name + " to Shopping application console ***************************");
                // Displaying menu operations
                System.out.print("1- Purchase products\n2- Discard shopping cart\n3- modify shopping cart\n4- Finalize shopping\n5- See previous purchases\n0- Quit\n\nEnter operation number:");
                // Taking customer's choice for what he/she wants to do
                String s = keyboard.nextLine();
                int choice = Integer.parseInt(s);
                // Calling operations according to choice
                switch (choice) {
                    // if choice is 1 show available products to customer and ask him what he wants to buy
                    case 1:

                        System.out.println("*************************** Available products ***************************");
                        purchaseProducts();
                        break;
                    // If choice is 2 empty the shopping cart
                    case 2:
                        discardShoppingCart();
                        break;
                    // If choice is 3, aks user what he want to modify in cart
                    case 3:
                        modifyShoppingCart();
                        break;
                    // If choice is 4, finalize the shopping and add data to the files
                    case 4:
                        finalizeShopping();
                        break;
                    // If choice is 5, show previoud purchases to the customer
                    case 5:
                        seePreviousPurchases();
                        break;
                    // If choice is 0, dispay message and quit the application
                    case 0:
                        System.out.println("*************************** Thank you for using our application, logging out ***************************\n\n");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. \n\n");

                }

            }
        } // if user pass doesn't match, show customer that credentials are invalid
        else {
            System.out.println("Invalid credentials");
            // Call menu again to ask user to log in
            menu();
        }

    }

    // Method for checking if customer with specific name and pass exist in customers file
    public static boolean checkCustomer(String name, String pass) {
        File f = new File("customers.txt");
        try {
            Scanner customers = new Scanner(new FileReader(f));
            while (customers.hasNext()) {
                String row = customers.next();
                String[] customer = row.split(",");
                // If customer's name and pass matches store customer id in variable in return true
                if (customer[0].equalsIgnoreCase(name) && customer[1].equalsIgnoreCase(pass)) {
                    customerID = Integer.parseInt(customer[2]);
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    // Method for reading data from products file and store in array
    public static void readProductsData() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(new File("products.dat"));

        String str = "";
        int ch;
        while ((ch = fis.read()) != -1) {
            str += (char) ch;

        }
        // convert data from products file into rows of strings
        String rows[] = str.split("\n");
        for (int i = 1; i < rows.length; i++) {
            // Converting each row data into different attributes of product
            String[] row = rows[i].split(",");
            // if product type is tv. create TV object
            if (row[3].equalsIgnoreCase("tv")) {
                Product p = new TV();
                p.setProductId(Byte.parseByte(row[1]));
                // read further data of this product ID using TV's read method
                p.read();
//                Product p = new TV(row[0], Byte.parseByte(row[1]), Integer.parseInt(row[2]), row[3], Double.parseDouble(row[4]), "26inch");
                products.add(p);

            } // if product type is Phone. create Phone object
            else if (row[3].equalsIgnoreCase("phone")) {
                Product p = new Phone();
                p.setProductId(Byte.parseByte(row[1]));
                // read further data of this product ID using Phone's read method

                p.read();
                products.add(p);
            } // if product type is table. create DrawingTablet object
            else if (row[3].equalsIgnoreCase("tablet")) {
                Product p = new DrawingTablet();
                p.setProductId(Byte.parseByte(row[1]));
                // read further data of this product ID using DrawingTablet's read method

                p.read();
                products.add(p);
            }
        }
    }

    // Method for emptying array shopping cart
    private static void discardShoppingCart() {
        shoppingCart.clear();
        System.out.println("Shopping cart emptied");
        return;
    }

    // Method for removing any products from shopping cart
    private static void modifyShoppingCart() {
        // if there are any items in shopping cart
        if (shoppingCart.size() > 0) {
            // display existing products in cart
            System.out.println("Existing products in shopping cart:");
            for (int i = 0; i < shoppingCart.size(); i++) {
                Product p = (Product) shoppingCart.get(i);
                System.out.println((i + 1) + ":" + p.getProductName());
            }
            // then ask customer to select any item number to remove
            System.out.print("Select any item number to remove: ");
            int item = Integer.parseInt(keyboard.nextLine());
            if (shoppingCart.size() >= item - 1 && item > 0) {
                // remove asked item number from cart
                shoppingCart.remove(item - 1);
                System.out.println("Item no: " + item + " successfully discarded.\n");
                return;

            } // If customer enters any invalid item not, display message and return to menu
            else {
                System.out.println("Invalid item no.\n");
                return;
            }
        } // if cart has no items, display message and return to menu
        else {
            System.out.println("*************************** No product in shopping cart ***************************\n");
            return;
        }
    }

    // Method responsible for allowing user to add any products to cart
    private static void purchaseProducts() {
        // display products whose stock > 0 to the customer
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getStock() > 0) {
                System.out.println((i + 1) + ": " + products.get(i));
            }
        }
        // ask user what product he/she want to add to cart
        System.out.print("Enter product number you want to add in cart:");
        int num = Integer.parseInt(keyboard.nextLine());
        // add product with asked id to shopping cart array
        shoppingCart.add(products.get(num - 1));
        System.out.println("Product added !!\n\n");
        // ask customer if he/she wants to purchase any other other product
        System.out.print("Do you want to purchase any other product (y or n) ? ");
        String ch = keyboard.nextLine();
        // if yes, then call purchase product again
        if (ch.equalsIgnoreCase("y")) {
            purchaseProducts();
        }
        //otherwise return

    }

    // Method for finalizing shopping by adding purchased product to records
    private static void finalizeShopping() {
        FileWriter writer = null;
        try {
            // display products existing in shopping cart
            System.out.println("*************************** Products in shopping cart ***************************");
            File f = new File("purchases.txt");
            writer = new FileWriter(f, true);

            String str = customerID + "";
            // create record by adding all purchased products' data to a string
            for (int i = 0; i < shoppingCart.size(); i++) {
                Product p = (Product) shoppingCart.get(i);

                System.out.println(p);

                str += "," + p.getProductId();

            }

            // add that created record to the file
            writer.write(str + "\n");

            writer.close();

            // display message to customer
            System.out.println("Purchases added to the record.\n");
            // empty the shopping cart after each purchase
            shoppingCart.clear();
            return;
        } catch (IOException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Method to display customer's all previous purchases
    private static void seePreviousPurchases() {
        System.out.println("*************************** ProductIDs of Previous purchases ***************************");
        FileReader reader = null;
        try {
            // open purchases.txt file to read it's data
            File f = new File("purchases.txt");
            reader = new FileReader(f);
            Scanner sc = new Scanner(reader);

            String purchases = "";

            // read all lines of purchases file
            while (sc.hasNext()) {
                String row = sc.next();
                String cols[] = row.split(",");
                // print product id's of purchases where customer if matches current customer
                if (Integer.parseInt(cols[0]) == customerID) {
                    for (int i = 1; i < cols.length; i++) {
                        System.out.print(cols[i] + "\t");

                    }
                    System.out.println();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
