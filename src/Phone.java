
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Phone class representing Phone product to sell
 *
 * @author
 */
public class Phone extends Product {

    // additional cameraResolution attribute of Phone
    String cameraResolution;

    public Phone() {
    }

    public Phone(String productName, byte productId, int stock, String type, float price, String cameraResolution) {
        super(productName, productId, stock, type, price);
        this.cameraResolution = cameraResolution;
    }

    // write method to write current products' data to products' binary file
    @Override
    public void write() {
        FileOutputStream fos = null;
        try {
            String str = "";
            char delimiter = ',';
            // create phone record, seperated by comma with phone's details to add to procucts file

            str += this.productName + delimiter + productId + delimiter + stock + delimiter + type + delimiter + price;
            fos = new FileOutputStream(new File("products.dat"));
            // write bytes of record string in products' file

            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(TV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    // read method to read current products' data from products' binary file
    @Override
    public void read() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("products.dat"));
            String str = "";
            int ch;
            // store all data to string variable

            while ((ch = fis.read()) != -1) {
                str += (char) ch;

            }
            // dividing string data to rows array, with each record in each row

            String rows[] = str.split("\n");
            for (int i = 1; i < rows.length; i++) {
                String[] row = rows[i].split(",");
                // where product id is current product's id, read other data

                if (Byte.parseByte(row[1]) == this.productId) {
                    this.setProductName(row[0]);
                    this.setStock(Integer.parseInt(row[2]));
                    this.setType(row[3]);
                    this.setPrice(Float.parseFloat(row[4]));
                    this.cameraResolution = "8megapixels";;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(TV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    // Method to display details of Phone
    @Override
    public String toString() {
        return "Phone{" + "productName=" + productName + ", productId=" + productId + ", type=" + type + ", price=" + price + ", camera resolution=" + cameraResolution + '}';
    }

}
