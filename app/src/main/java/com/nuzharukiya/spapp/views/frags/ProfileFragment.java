package com.nuzharukiya.spapp.views.frags;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nuzharukiya.spapp.R;
import com.nuzharukiya.spapp.SPAppFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import static com.nuzharukiya.spapp.utils.SPUrls.POST_SERVICE_PROVIDER_UPDATE_PROFILE;

/**
 * Created by Nuzha Rukiya on 18/06/25.
 */
public class ProfileFragment extends SPAppFragment {
    private EditText shopName,gstNumber,locationDetails,pincode,services;
    private Button Update;
    private LinearLayout profile_details;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_profile, container, false);
        shopName = (EditText)rootView.findViewById(R.id.shop_Name_ET);
        gstNumber = (EditText)rootView.findViewById(R.id.gst_Number_ET);
        pincode = (EditText)rootView.findViewById(R.id.pincode_ET);
        profile_details = (LinearLayout)rootView.findViewById(R.id.profile_details);

        Update = (Button)rootView.findViewById(R.id.update);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateClick();
            }
        });

        return rootView;
    }

    private void onUpdateClick() {
        String sShopName = shopName.getText().toString().trim();
        String sGSTNumber = gstNumber.getText().toString().trim();
        String sLatitude = "13.004189";
        String sLongitude = "77.6574293";
        String sAddress = "Kasturi Nagar";
        String sPinCode = pincode.getText().toString().trim();
        try {
        JSONArray aServices = new JSONArray();
        JSONObject oServiceObject1 = new JSONObject();
        oServiceObject1.put("Name", "Haircut");
        oServiceObject1.put("Value", "100");
        JSONObject oServiceObject2 = new JSONObject();
        oServiceObject2.put("Name", "Shaving");
        oServiceObject2.put("Value", "50");
        aServices.put(oServiceObject1);
        aServices.put(oServiceObject2);


        JSONObject oSPObj =new JSONObject();
        oSPObj.put("name",sShopName);
        oSPObj.put("gstTin",sGSTNumber);
        oSPObj.put("lat",sLatitude);
        oSPObj.put("longi",sLongitude);
        oSPObj.put("address",sAddress);
        oSPObj.put("pin",sPinCode);
        oSPObj.put("services",aServices);

        JSONObject oAuthObj= new JSONObject();
        oAuthObj.put("userType",1);
        oAuthObj.put("authId","67128046");
        oSPObj.put("AuthParam",oAuthObj);


        String URL_UPDATE_PROFILE = POST_SERVICE_PROVIDER_UPDATE_PROFILE;


            RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());



            final String requestBody = oSPObj.toString();
            String URL = URL_UPDATE_PROFILE;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    profile_details.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Successfully Updated",Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
