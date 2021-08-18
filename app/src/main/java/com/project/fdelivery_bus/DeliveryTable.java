package com.project.fdelivery_bus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// in here the business can see a list of his on going delivers
public class DeliveryTable extends AppCompatActivity {
    private ListView listView;
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    String FromIntent,ID,TOKEN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_table);
        listView=(ListView) findViewById(R.id.listDelivery);

        Business businessUser;


        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            FromIntent = extras.getString("businessUserInGson");
            ID =extras.getString("id");
            TOKEN =extras.getString("token");

            businessUser = new Gson().fromJson(FromIntent, Business.class);
            // Log.i("ttt", businessUser.getFirstName());
            businessUser.setId(ID);
            businessUser.setToken(TOKEN);

            GetDeliveries(ID);
        }



    }

    private void GetDeliveries(String id) //this give us all deliveries that this id-user added
    {
        Call<List<String>> call = rtfBase.getDeliveries(id);
        ArrayList<String> arrayList=new ArrayList<>();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response)
            {

                if(response.code() == 400)
                {
                    Toast.makeText(DeliveryTable.this, "you have no active deliveries",Toast.LENGTH_LONG).show();
                    finish();
                }
                if(response.code() == 200)
                {
                    Log.i("TEST1", String.valueOf(response.body()));
                    for(int i=0;i<response.body().size();i++) {
                       String deliveryID=response.body().get(i).substring(18,42);
                        Log.i("TEST6", deliveryID);

                        Delivery delivery = new Gson().fromJson(response.body().get(i), Delivery.class);
                        delivery.setId(deliveryID);
                        arrayList.add(delivery.getClientName()+" "+delivery.getClientPhone()+"\nid="+deliveryID);
                        help(arrayList);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(DeliveryTable.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void help(ArrayList<String> arrayList){
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.i("whatt",arrayList.get(position));
                GetDelivery(arrayList.get(position).split("id=")[1]);



            }
        });
    }

    private void GetDelivery(String idDelivery) //put delivery id and this return you the delivery
    {
        Call<String> call = rtfBase.getDelivery(idDelivery);
        Intent intent=new Intent(this,showCoosenActivity.class);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {

                if(response.code() == 400)
                {
                    Toast.makeText(DeliveryTable.this, "this ID do not exist",Toast.LENGTH_LONG).show();

                }
                if(response.code() == 200)
                {
                    Log.i("TEST1",response.body());
                    Delivery GSON = new Gson().fromJson(response.body(),Delivery.class);
                    Log.i("TEST2",GSON.getClientName());
                    Toast.makeText(DeliveryTable.this, "We found your Delivery",Toast.LENGTH_LONG).show();

                        intent.putExtra("delivery",response.body());
                        intent.putExtra("idDelivery",idDelivery);
                        intent.putExtra("token",TOKEN);
                        intent.putExtra("id",ID);
                        intent.putExtra("businessUserInGson",FromIntent);

                        startActivity(intent);




                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DeliveryTable.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }


}