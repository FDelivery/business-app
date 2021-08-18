package com.project.fdelivery_bus;
import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainBusiness extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton createNewDelivery;
    private ImageButton ActiveDeliveries;
    private ImageButton myProfile;
    private Business businessUser;
    private TextView welcome;
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    String USER,ID,TOKEN;
    private Socket mSocket;
    Delivery delivery;

    protected void onStop() {
        super.onStop();
        mSocket.off("delivery_accepted");
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_business);
        createNotificationChannel();
        deliveryHistory=(ImageButton)findViewById(R.id.deliveryHistory);
        createNewDelivery =(ImageButton)findViewById(R.id.deliveryRequest);
        ActiveDeliveries=(ImageButton)findViewById(R.id.activeDeliveries);
        myProfile =(ImageButton)findViewById(R.id.myprofile);
        welcome=(TextView)findViewById(R.id.textViewWelcom);



        Bundle extras = getIntent().getExtras();
        mSocket = SocketIO.getSocket();
        mSocket.on("delivery_accepted", (msg)->{
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"123")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Delivery accepted")
                .setContentText("a courier is on his way to take care of your delivery")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(123, builder.build());

        });
        if(extras!=null)
        {
            USER = extras.getString("businessUserInGson");
            ID =extras.getString("id");
            mSocket.emit("join", ID);
            TOKEN =extras.getString("token");
            businessUser = new Gson().fromJson(USER, Business.class);

            welcome.setText("welcome "+businessUser.getBusinessName());

        }

        //show all active deliveries, First we will check if there are such deliveries
        ActiveDeliveries.setOnClickListener((v) -> {
           Intent intent = new Intent(this, activeDeliveries.class);
            Call<List<String>> call = rtfBase.getDeliveries(ID);
            ArrayList<String> arrayList=new ArrayList<>();
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if(response.code() == 200)
                    {
                        for(int i=0;i<response.body().size();i++){
                            delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                            if(!delivery.getStatus().equals("DELIVERED")){
                                arrayList.add(response.body().get(i));
                            }
                        }
                        if(!arrayList.isEmpty()) {

                            intent.putExtra("id", ID);
                            intent.putExtra("businessUserInGson", USER);
                            intent.putExtra("token", TOKEN);

                            startActivity(intent);
                        }else{
                            Toast.makeText(MainBusiness.this, "you have no active deliveries",Toast.LENGTH_LONG).show();

                        }
                    }
                    else{
                        Toast.makeText(MainBusiness.this, "you have no active deliveries",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Toast.makeText(MainBusiness.this, t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });



        });
//show all profile info (and option to change)
        myProfile.setOnClickListener((v) -> {
            Intent intent= new Intent(this, BusinessProfile.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson", USER);
            intent.putExtra("token",TOKEN);

            startActivity(intent);
            finish();
        });
//show all deliveries that sends
        deliveryHistory.setOnClickListener((v) -> {
            Call<List<String>> call = rtfBase.getDeliveriesHistory("DELIVERED",ID);
            ArrayList<String> arrayList=new ArrayList<>();
            Intent intent =new Intent(this, DeliveryHistory.class);

            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if(response.code() == 200) {
                        for (int i = 0; i < response.body().size(); i++) {
                            delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                            if (delivery.getStatus().equals("DELIVERED")) {
                                arrayList.add(response.body().get(i));
                            }
                        }
                        if (!arrayList.isEmpty()) {

                            intent.putExtra("id",ID);
                            intent.putExtra("businessUserInGson", USER);
                            intent.putExtra("token",TOKEN);


                            startActivity(intent);
                        }
                    }else{
                        Toast.makeText(MainBusiness.this, "you have no completed deliveries",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Toast.makeText(MainBusiness.this, t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });








        });
 //create new delivery
        createNewDelivery.setOnClickListener((v) -> {
            Intent intent =new Intent(this, newDelivery.class);
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson", USER);
            startActivity(intent);
        });
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "123";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123", name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}