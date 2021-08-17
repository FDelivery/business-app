package com.project.fdelivery_bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class editDelivery extends AppCompatActivity {
    private RetrofitInterface  rtfBase = RetrofitBase.getRetrofitInterface();
    String deliveryFromIntent,IDDELIVERY,TOKEN;
    private EditText name;
    private EditText phone;
    private TextView price;
    private EditText note;
    private Button save;
    private EditText city;
    private EditText aprt;
    private EditText number;
    private EditText street;
    private EditText entrance;
    private EditText Time,Date;

    //private CheckBox Bike, Car;
    Delivery delivery;
    String FromIntent,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery);
        save=(Button)findViewById(R.id.ETndsubmit);
        name=(EditText) findViewById(R.id.ETname);
        price=(TextView)findViewById(R.id.ETcost);
        note=(EditText)findViewById(R.id.ETnote);
        phone=(EditText)findViewById(R.id.ETphone);
        city=(EditText)findViewById(R.id.ETcity);
        number=(EditText)findViewById(R.id.ETnumber);
        entrance=(EditText)findViewById(R.id.ETentrance);
        street=(EditText)findViewById(R.id.ETstreet);
        aprt=(EditText)findViewById(R.id.ETaprt);
        Time=(EditText)findViewById(R.id.ETTime);
        Date=(EditText)findViewById(R.id.ETDate);
      //  Car = (CheckBox)findViewById(R.id.ETcar);
       // Bike = (CheckBox)findViewById(R.id.ETbike);
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
        Date.setInputType(InputType.TYPE_NULL);
        Time.setInputType(InputType.TYPE_NULL);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(Date);
            }

            private void showDateDialog(EditText Date) {
                final Calendar calendar=Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                        Date.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                };

                new DatePickerDialog(editDelivery.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(Time);
            }

            private void showTimeDialog(EditText time) {

                final Calendar calendar=Calendar.getInstance();

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                        Time.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(editDelivery.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        });

        save.setOnClickListener((v) -> {

            String NameText= name.getText().toString();
            String PhoneText= phone.getText().toString();
            String noteText= note.getText().toString();
            String priceText=price.getText().toString();
            String TimeText= Time.getText().toString();
            String DateText =Date.getText().toString();
            String CityText= city.getText().toString();
            String AprtText= aprt.getText().toString();
            String NumberText= number.getText().toString();
            String EntranceText=entrance.getText().toString();
            String StreetText=street.getText().toString();
            Address address=delivery.getClientAddress();
            //  Boolean car = Car.isChecked();
          //  Boolean bike = Bike.isChecked();

            if(!TimeText.isEmpty()) {
                delivery.setTime(TimeText);
            }
            if(!DateText.isEmpty()) {
                delivery.setDate(DateText);
            }
            if(!priceText.isEmpty()){
                delivery.setPrice(Double.parseDouble(priceText));
            }

            if(!PhoneText.isEmpty()) {
                delivery.setClientPhone(PhoneText);
            }
            if(!NameText.isEmpty()) {
                delivery.setClientName(NameText);
            }
            if(!noteText.isEmpty()) {
                delivery.setNote(noteText);
            }
            if(!CityText.isEmpty()) {
                address.setCity(CityText);
            }else{
                address.setCity(delivery.getClientAddress().getCity());

            }
            if(!AprtText.isEmpty()) {
                address.setApartment(AprtText);
            }
                else{
                    address.setApartment(delivery.getClientAddress().getApartment());

                }
            if(!NumberText.isEmpty()) {
                address.setNumber(NumberText);
            }else{
                address.setNumber(delivery.getClientAddress().getNumber());

            }
            if(!StreetText.isEmpty()) {
                address.setStreet(StreetText);
            }else{
                address.setStreet(delivery.getClientAddress().getStreet());

            }
            if(!EntranceText.isEmpty()) {
                address.setEntrance(EntranceText);
            }else{
                address.setEntrance(delivery.getClientAddress().getEntrance());

            }
          /*  if(!priceText.isEmpty()) {                      ///משהו השתנה בxml
                double str1 = Double.parseDouble(priceText);
                delivery.setPrice(str1);
            }*/

            delivery.setClientAddress(address);
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