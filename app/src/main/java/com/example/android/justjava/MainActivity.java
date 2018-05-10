/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        Boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameInput = (EditText) findViewById(R.id.name_input);
        Editable name = nameInput.getText();
        int price = calculatePrice(hasChocolate,hasWhippedCream);
        String priceMessage = createOrederSummary(name, price, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quntity
     */
    public void increment(View view){
        if(quantity<100) {
            quantity = quantity + 1;
        }
        display(quantity);
    }

    /**
     * This method decrements the quntity
     */
    public void decrement(View view){
        if(quantity==1){
            return ;
        }
        else {
            quantity = quantity - 1;
        }
        display(quantity);
    }

    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream){
        int basePrice =5;
        if(hasChocolate){
            basePrice = basePrice+ 2;
        }
        if(hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        return basePrice * quantity;
    }

    private String createOrederSummary(Editable name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = "Name: "+name+"\nAdd whipped cream? "+addWhippedCream+"\nAdd Chocolate? "+addChocolate+"\nQuantity: "+quantity+"\nTotal: "+price+"\nThank You!";
        return priceMessage;

    }
}