package com.test.binanceapp;

import android.os.Bundle;
import static com.binance.api.client.domain.account.NewOrder.marketBuy;
import com.binance.api.client.domain.account.request.CancelOrderRequest;
import com.binance.api.client.domain.account.request.OrderStatusRequest;
import com.binance.api.client.domain.account.request.OrderRequest;
import com.binance.api.client.BinanceApiAsyncRestClient;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.domain.account.Order;
import com.binance.api.client.domain.account.request.AllOrdersRequest;
import com.binance.api.client.domain.TimeInForce;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import static com.binance.api.client.domain.account.NewOrder.limitBuy;

public class MainActivity extends AppCompatActivity {
    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("YOUR_API_KEY", "YOUR_SECRET");
    BinanceApiAsyncRestClient client = factory.newAsyncRestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public  void showResult(List<Order> lo)
    {
        System.out.println(lo);
        TextView t =findViewById(R.id.textView);
        t.setText(lo.toString());
    }
    public  void showResult(Order order)
    {
        System.out.println(order);
        TextView t =findViewById(R.id.textView);
        t.setText(order.toString());
    }
    public  void showResult(String resultText)
    {
        System.out.println(resultText);
        TextView t =findViewById(R.id.textView);
        t.setText(resultText.toString());
    }
    public void clickAllOrders(View view)
    {
        // Getting list of all orders with a limit of 10
        AllOrdersRequest aod = new AllOrdersRequest("LINKETH");
        client.getAllOrders(new AllOrdersRequest("LINKETH").limit(10), response -> showResult(response) );

    }
    public void clickOpenOrders(View view)
    {
        // Getting list of open orders
        client.getOpenOrders(new OrderRequest("LINKETH"), response -> showResult(response) );
    }
    public void clickOrderStatus(View view)
    {
        // Get status of a particular order
        client.getOrderStatus(new OrderStatusRequest("LINKETH", 745262L),
                response -> showResult(response));
    }
    public void clickCancelOrder(View view)
    {
        // Canceling an order
        client.cancelOrder(new CancelOrderRequest("LINKETH", 756703L),
                response -> showResult(response.toString()));
    }
    public void clickTestLIMITorder(View view)
    {
        // Placing a test LIMIT order
        client.newOrderTest(limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"),
                response -> showResult("Test order has succeeded."));

    }
    public void clickTestMARKETorder(View view)
    {
        // Placing a test MARKET order
        client.newOrderTest(marketBuy("LINKETH", "1000"), response -> showResult("Test order has succeeded."));


    }
    public void clickRealLIMITorder(View view)
    {
        // Placing a real LIMIT order
        client.newOrder(limitBuy("LINKETH", TimeInForce.GTC, "1000", "0.0001"),
                response ->showResult(response.toString()));


    }

}
