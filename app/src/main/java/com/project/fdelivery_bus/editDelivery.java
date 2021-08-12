package com.project.fdelivery_bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editDelivery extends AppCompatActivity {
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    String deliveryFromIntent,IDDELIVERY,TOKEN;
    private EditText name;
    private EditText phone;
    private EditText price;
    private EditText note;
    private Button save;
    Delivery delivery;
    String FromIntent,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery);
        save=(Button)findViewById(R.id.OK);
        name=(EditText) findViewById(R.id.Name);
        price=(EditText)findViewById(R.id.price);
        note=(EditText)findViewById(R.id.note);
        phone=(EditText)findViewById(R.id.phone);
        Bundle extras = getIntent().getExtras();

        if(extras!=null)
        {
            deliveryFromIntent = extras.getString("delivery");
            IDDELIVERY=extras.getString("idDelivery");
            TOKEN=extras.getString("token");
            FromIntent = extras.getString("businessUserInGson");
            ID =extras.getString("id");
            delivery = new Gson().fromJson(deliveryFromIntent, Delivery.class);


        }

        save.setOnClickListener((v) -> {

            String NameText= name.getText().toString();
            String PhoneText= phone.getText().toString();
            String noteText= note.getText().toString();
            String priceText=price.getText().toString();

            if(!PhoneText.isEmpty()) {
                delivery.setClientPhone(PhoneText);
            }
            if(!NameText.isEmpty()) {
                delivery.setClientName(NameText);
            }
            if(!noteText.isEmpty()) {
                delivery.setNote(noteText);
            }
            if(!priceText.isEmpty()) {
                double str1 = Double.parseDouble(priceText);
                delivery.setPrice(str1);
            }

            UpdateDelivery(TOKEN, IDDELIVERY, delivery);


        });

    }


    private void UpdateDelivery(String token,String idDelivery,Delivery d)  //we got id-delivery, user-token and  new delivery and we update parameters
    {
        Intent intent= new Intent(this, MainBusiness.class);


        Call<Void> call = rtfBase.updateDelivery("Bearer "+token,idDelivery,d);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response)
            {
                {

                    if(response.code() == 400)
                    {
                        Toast.makeText(editDelivery.this, "do not success",Toast.LENGTH_LONG).show();

                    }
                    if(response.code() == 200 || response.code() == 204)
                    {
                        intent.putExtra("id",ID);
                        intent.putExtra("businessUserInGson",FromIntent);
                        intent.putExtra("token",TOKEN);
                        Toast.makeText(editDelivery.this, "update successfully",Toast.LENGTH_LONG).show();
                        startActivity(intent);


                    }
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(editDelivery.this, t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });




    }



}