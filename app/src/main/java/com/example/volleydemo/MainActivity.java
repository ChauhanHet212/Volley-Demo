package com.example.volleydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.volleydemo.models.Address;
import com.example.volleydemo.models.Company;
import com.example.volleydemo.models.Geo;
import com.example.volleydemo.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<User> userList = new ArrayList<>();
    List<Address> addressList = new ArrayList<>();
    List<Geo> geoList = new ArrayList<>();
    List<Company> companyList = new ArrayList<>();

    ListView list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/users",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject userObj = jsonArray.getJSONObject(i);

                                int id = userObj.getInt("id");
                                String name = userObj.getString("name");
                                String username = userObj.getString("username");
                                String email = userObj.getString("email");
                                String phone = userObj.getString("phone");
                                String website = userObj.getString("website");

                                User user = new User(id, name, username, email, phone, website);
                                userList.add(user);


                                JSONObject addressObj = userObj.getJSONObject("address");

                                String street = addressObj.getString("street");
                                String suite = addressObj.getString("suite");
                                String city = addressObj.getString("city");
                                String zipcode = addressObj.getString("zipcode");

                                Address address = new Address(street, suite, city, zipcode);
                                addressList.add(address);


                                JSONObject companyObj = userObj.getJSONObject("company");

                                String c_name = companyObj.getString("name");
                                String catchPhrase = companyObj.getString("catchPhrase");
                                String bs = companyObj.getString("bs");

                                Company company = new Company(c_name, catchPhrase, bs);
                                companyList.add(company);


                                JSONObject geoObj = addressObj.getJSONObject("geo");

                                String lat = geoObj.getString("lat");
                                String lng = geoObj.getString("lng");

                                Geo geo = new Geo(lat, lng);
                                geoList.add(geo);
                            }

                            adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, userList);
                            list.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(stringRequest);
    }
}