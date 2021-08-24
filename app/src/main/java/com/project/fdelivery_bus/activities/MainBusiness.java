package com.project.fdelivery_bus.activities;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
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
import com.project.fdelivery_bus.classes.Business;
import com.project.fdelivery_bus.classes.Delivery;
import com.project.fdelivery_bus.R;
import com.project.fdelivery_bus.classes.RetrofitBase;
import com.project.fdelivery_bus.classes.RetrofitInterface;
import com.project.fdelivery_bus.classes.SocketIO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainBusiness extends AppCompatActivity {
    private ImageButton deliveryHistory;
    private ImageButton createNewDelivery;
    private ImageButton ActiveDeliveries;
    private ImageButton myProfile;
    private Business businessUser;
    private TextView welcome;
    private RetrofitInterface rtfBase = RetrofitBase.getRetrofitInterface();
    String USER,ID,TOKEN;
    private Socket mSocket;
    Delivery delivery;
    private NotificationManagerCompat notificationManager;

    protected void onStop() {
        super.onStop();
        mSocket.off("delivery_accepted");
        Log.i("socket", "listener off");
    }

    @Override
    protected void onResume() {
        Log.i("socket", "listener on");
//        mSocket = SocketIO.getSocket();
//        mSocket.emit("join", ID);
        mSocket.on("delivery_accepted", onNewMessage);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSocket.emit("leave", ID);
        mSocket.disconnect();
        super.onDestroy();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_business);
        createNotificationChannel();
        deliveryHistory=findViewById(R.id.deliveryHistory);
        createNewDelivery =findViewById(R.id.deliveryRequest);
        ActiveDeliveries=findViewById(R.id.activeDeliveries);
        myProfile =findViewById(R.id.myprofile);
        welcome=findViewById(R.id.textViewWelcom);
        notificationManager = NotificationManagerCompat.from(this);
        mSocket = SocketIO.getSocket();

        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            USER = extras.getString("businessUserInGson");
            ID =extras.getString("id");
            TOKEN =extras.getString("token");
            businessUser = new Gson().fromJson(USER, Business.class);

            mSocket.emit("join", ID);
            Log.i("ID", ID);
            welcome.setText("welcome "+businessUser.getBusinessName());

        }

        //show all active deliveries, First we will check if there are such deliveries
        ActiveDeliveries.setOnClickListener((v) -> {
            showActive();

        });
//show all profile info (and option to change)
        myProfile.setOnClickListener((v) -> {
            Intent intent= new Intent(this, BusinessProfile.class);
            intent.putExtra("id",ID);
            intent.putExtra("businessUserInGson", USER);
            intent.putExtra("token",TOKEN);

            startActivity(intent);
            //finish();
        });
//show all deliveries that sends. First we will check if there are such deliveries
        deliveryHistory.setOnClickListener((v) -> {
            getHistory();

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

    //show all deliveries that sends. First we will check if there are such deliveries
    private void getHistory(){
        Call<List<String>> call = rtfBase.getDeliveriesHistory("DELIVERED",ID);
        ArrayList<String> arrayListShow=new ArrayList<>();
        ArrayList<String> arrayListId=new ArrayList<>();

        Intent intent =new Intent(this, DeliveryHistory.class);
        Bundle bundle = new Bundle();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                        if (delivery.getStatus().equals("DELIVERED")) {
                            String deliveryID=response.body().get(i).substring(18,42);
                            arrayListShow.add("Client Name: "+delivery.getClientName()+"\nNumber: "+delivery.getClientPhone());
                            arrayListId.add(deliveryID);


                        }
                    }
                    if (!arrayListShow.isEmpty()) {

                        intent.putExtra("id",ID);
                        intent.putExtra("businessUserInGson", USER);
                        intent.putExtra("token",TOKEN);
                        bundle.putSerializable("arrayListShow",(Serializable)arrayListShow);
                        bundle.putSerializable("arrayListId",(Serializable)arrayListId);

                        intent.putExtra("BUNDLE",bundle);

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

    }



    //show all active deliveries, First we will check if there are such deliveries
    private void showActive(){
        Intent intent = new Intent(this, activeDeliveries.class);
        Call<List<String>> call = rtfBase.getDeliveries(ID);
        ArrayList<String> arrayListShow=new ArrayList<>();
        ArrayList<String> arrayListId=new ArrayList<>();
        Bundle bundle = new Bundle();

        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.code() == 200)
                {
                    for(int i=0;i<response.body().size();i++){
                        delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                        if(!delivery.getStatus().equals("DELIVERED")){
                            String deliveryID=response.body().get(i).substring(18,42);
                            arrayListShow.add("Client Name: "+delivery.getClientName()+"\nNumber: "+delivery.getClientPhone());
                            arrayListId.add(deliveryID);
                        }
                    }
                    if(!arrayListShow.isEmpty()) {

                        intent.putExtra("id", ID);
                        intent.putExtra("businessUserInGson", USER);
                        intent.putExtra("token", TOKEN);
                        bundle.putSerializable("arrayListShow",(Serializable)arrayListShow);
                        bundle.putSerializable("arrayListId",(Serializable)arrayListId);
                        intent.putExtra("BUNDLE",bundle);



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


    }

    private Emitter.Listener onNewMessage = new Emitter.Listener(){
        @Override
        public void call(Object... args) {
            Log.i("socket", "111");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),"123")
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("Delivery accepted")
                    .setContentText("a courier is on his way to take care of your delivery")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            notificationManager.notify((int)Math.random()*100, builder.build());
        }
    };
}