package com.project.fdelivery_bus;
import io.socket.client.Socket;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;


public class MainBusiness extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton deliveryRequest;
    private ImageButton ActiveDeliveries;
    private ImageButton myprofile;
    private Business businessUser;
    private TextView welcome;
    String FromIntent,ID,TOKEN;
    private Socket mSocket;

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
        deliveryRequest=(ImageButton)findViewById(R.id.deliveryRequest);
        ActiveDeliveries=(ImageButton)findViewById(R.id.activeDeliveries);
        myprofile=(ImageButton)findViewById(R.id.myprofile);
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
            FromIntent = extras.getString("businessUserInGson");
            ID =extras.getString("id");
            mSocket.emit("join", ID);
            TOKEN =extras.getString("token");
            businessUser = new Gson().fromJson(FromIntent, Business.class);
            // Log.i("ttt", businessUser.getFirstName());
           // businessUser.setId(ID);
           // businessUser.setToken(TOKEN);

            welcome.setText("welcome "+businessUser.getBusinessName());

        }


        ActiveDeliveries.setOnClickListener((v) -> {
           Intent intent = new Intent(this, activeDeliveries.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson",FromIntent);
            intent.putExtra("token",TOKEN);

            startActivity(intent);
          //  MainActivity.a.finish();

        });

        myprofile.setOnClickListener((v) -> {
            Intent intent= new Intent(this, BusinessProfile.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson",FromIntent);
            intent.putExtra("token",TOKEN);

            startActivity(intent);
        });

        deliveryHistory.setOnClickListener((v) -> {
            Intent intent =new Intent(this, DeliveryHistory.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson",FromIntent);
            intent.putExtra("token",TOKEN);


            startActivity(intent);
        });

        deliveryRequest.setOnClickListener((v) -> {
            Intent intent =new Intent(this, newDelivery.class);
            intent.putExtra("token",TOKEN);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson",FromIntent);
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