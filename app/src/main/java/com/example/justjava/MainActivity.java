package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity==100) {
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity==1) {
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        TextView CustomerName = (TextView) findViewById(R.id.customer_name);
        String name = CustomerName.getText().toString();

        int price=calcuatePrice(hasWhippedCream,hasChocolate);

        String subject_email="JustJava order for" + name;
        String body_email=createOrderSummary(price,hasWhippedCream,hasChocolate,name);
        composeEmail(subject_email,body_email);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given quantity value on the screen.
     * @param hasWhippedCream
     * @param hasChocolate 
     */
    public int calcuatePrice(boolean hasWhippedCream, boolean hasChocolate){

        int basePeice=5;
        if(hasWhippedCream)
            basePeice+=1;
        if(hasChocolate)
            basePeice+=2;
        return basePeice*quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     * @param price
     * @param hasChocolate
     * @param hasWhippedCream
     * @param name
     */
    public String createOrderSummary(int price,boolean hasWhippedCream,boolean hasChocolate,String name){
        String priceMessage="Name: " + name;
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: " + price;
        priceMessage=priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     * @param subject
     * @param body
     */
    public void composeEmail(String subject,String body){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(intent.EXTRA_TEXT,body);
        if(intent.resolveActivity(getPackageManager()) != null )
            startActivity(intent);
    }
}