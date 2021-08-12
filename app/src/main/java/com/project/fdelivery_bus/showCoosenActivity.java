package com.project.fdelivery_bus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class showCoosenActivity extends AppCompatActivity {
    private TextView printall;
    private Button del,edit;
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();

    String deliveryFromIntent,IDDELIVERY,TOKEN,ID,FromIntent;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_coosen);
        printall = (TextView)findViewById(R.id.printall);
        del=(Button)findViewById(R.id.delete);
        edit=(Button)findViewById(R.id.change);


        Delivery delivery;
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            deliveryFromIntent = extras.getString("delivery");
            IDDELIVERY=extras.getString("idDelivery");
            TOKEN=extras.getString("token");
            ID=extras.getString("id");
            FromIntent=extras.getString("businessUserInGson");
            delivery = new Gson().fromJson(deliveryFromIntent, Delivery.class);
            printall.setText("ID: "+IDDELIVERY+"\n" +delivery.toString());

        }

        del.setOnClickListener((v) -> {

            deleteDelivery(TOKEN,IDDELIVERY);


        });

        edit.setOnClickListener((v) ->
        {
               Intent intent=new Intent(this, editDelivery.class);
               intent.putExtra("idDelivery",IDDELIVERY);
               intent.putExtra("delivery",deliveryFromIntent);
               intent.putExtra("token",TOKEN);
               intent.putExtra("id",ID);
               intent.putExtra("businessUserInGson",FromIntent);

            startActivity(intent);
        });


    }


    private void deleteDelivery(String token,String idDelivery)
    {
        Intent intent=new Intent(this, MainBusiness.class);

        Call<Void> call =rtfBase.deleteDelivery("Bearer "+token,idDelivery);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.code() == 404 || response.code() == 400 ||response.code() == 401)
                {
                    Toast.makeText(showCoosenActivity.this, "this ID do not exist",Toast.LENGTH_LONG).show();

                }

                if(response.code() == 200)
                {
                    Toast.makeText(showCoosenActivity.this, "we delete this delivery successfully",Toast.LENGTH_LONG).show();
                    Log.i("w1w",token+ " "+idDelivery);
                    intent.putExtra("businessUserInGson",FromIntent);
                    intent.putExtra("token",TOKEN);
                    intent.putExtra("id",ID);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                Toast.makeText(showCoosenActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }




}