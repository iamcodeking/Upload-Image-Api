package com.example.imageupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button choose,upload;
    ImageView imageView;
    Bitmap bitmap,bitmap1;
    String url="http://192.168.29.32:8080/imageupload.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choose=findViewById(R.id.choose);
        imageView=findViewById(R.id.imageview);
        upload=findViewById(R.id.upload);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select image"),000);

            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, imagetoString(bitmap1), Toast.LENGTH_SHORT).show();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams()  {
                        Map<String,String> parms=new HashMap<String, String>();
                        String imagedata=imagetoString(bitmap1);
                        parms.put("image",imagedata);

                        return parms;
                    }
                };
                RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 000 && resultCode == RESULT_OK && data != null)
        {
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                 bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                 bitmap1=bitmap;
                //displaying selected image to imageview
                imageView.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image
                //uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
        public String imagetoString (Bitmap bitmap)
        {
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] imagebytes=byteArrayOutputStream.toByteArray();
            String encoded= Base64.encodeToString(imagebytes,Base64.DEFAULT);
            return encoded;
        }


}
