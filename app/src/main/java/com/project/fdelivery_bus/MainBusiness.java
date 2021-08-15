package com.project.fdelivery_bus;
import io.socket.client.IO;
import io.socket.client.Socket;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import io.socket.emitter.Emitter;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.net.URISyntaxException;


public class MainBusiness extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton deliveryRequest;
    private ImageButton deliveryList;
    private ImageButton myprofile;
    private Business businessUser;
    private TextView welcome;
    String FromIntent,ID,TOKEN;

    protected void onPause() {
    mSocket.off("delivery accepted");
        super.onPause();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_business);

        deliveryHistory=(ImageButton)findViewById(R.id.deliveryHistory);
        deliveryRequest=(ImageButton)findViewById(R.id.deliveryRequest);
        deliveryList=(ImageButton)findViewById(R.id.deliveryList);
        myprofile=(ImageButton)findViewById(R.id.myprofile);
        welcome=(TextView)findViewById(R.id.textViewWelcom);

        Bundle extras = getIntent().getExtras();
        mSocket.connect();
        mSocket.on("delivery accepted",deliveryAcceptedListener);
        if(extras!=null)
        {
            FromIntent = extras.getString("businessUserInGson");
            ID =extras.getString("id");
            TOKEN =extras.getString("token");

            businessUser = new Gson().fromJson(FromIntent, Business.class);
            // Log.i("ttt", businessUser.getFirstName());
           // businessUser.setId(ID);
           // businessUser.setToken(TOKEN);

            welcome.setText("welcome "+businessUser.getBusinessName());

        }


        deliveryList.setOnClickListener((v) -> {
           Intent intent = new Intent(this, DeliveryTable.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson",FromIntent);
            intent.putExtra("token",TOKEN);

            startActivity(intent);
        });

        myprofile.setOnClickListener((v) -> {
            Intent intent= new Intent(this, BusinessProfile.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson",FromIntent);
            intent.putExtra("token",TOKEN);

            startActivity(intent);
        });

        deliveryHistory.setOnClickListener((v) -> {
            startActivity(new Intent(this, DeliveryHistory.class));
        });

        deliveryRequest.setOnClickListener((v) -> {
            Intent intent =new Intent(this, newDelivery.class);
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson",FromIntent);
            startActivity(intent);
        });
    }
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://10.0.0.19:5000");
        } catch (URISyntaxException e) {}
    }


    private Emitter.Listener deliveryAcceptedListener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("test", "11111");

                }
            });
        }
    };



}