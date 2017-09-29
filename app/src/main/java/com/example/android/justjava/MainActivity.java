package com.example.android.justjava;
/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    //Quantity of coffees that will be ordered
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Increments the quantity displayed
    public void increment(View view) {
        if (quantity == 100) {
            return;
        } else {
            quantity++;
        }

        displayQuantity(quantity);
    }

    //Decreases the quantity displayed
    public void decrement(View view) {
        if (quantity == 0) {
            displayQuantity(quantity);
        } else {
            quantity--;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the order button is clicked.
     * Causes text to be displayed
     */
    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name_edit_text);
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_check_box);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_check_box);

        String nameTyped = name.getText().toString();
        boolean whippedCreamStatus = whippedCream.isChecked();
        boolean chocolateStatus = chocolate.isChecked();

        int price = calculatePrice(whippedCreamStatus, chocolateStatus);
        String priceMessage = createOrderSummary(nameTyped, whippedCreamStatus, chocolateStatus, price);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order from " + nameTyped);
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
          startActivity(emailIntent);
        }

        //displayMessage(priceMessage);
    }

    //Calculates the order's cost
    public int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int price;
        int whippedCreamCost = 1;
        int chocolateCost = 2;

        if (addWhippedCream && addChocolate) {
            price = quantity * (5 + whippedCreamCost + chocolateCost);
        } else if (addWhippedCream) {
            price = quantity * (5 + whippedCreamCost);
        } else if (addChocolate) {
            price = quantity * (5 + chocolateCost);
        } else {
            price = quantity * 5;
        }

        return price;
    }

    //Create a textual description of the order
    public String createOrderSummary(String name, boolean whippedCream, boolean chocolate, int price) {
        return "Name: " + name +
                "\nAdd whipped cream? " + whippedCream +
                "\nAdd chocolate? " + chocolate +
                "\nQuantity: " + quantity +
                "\nTotal: $" + price +
                "\nThank you!";
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}