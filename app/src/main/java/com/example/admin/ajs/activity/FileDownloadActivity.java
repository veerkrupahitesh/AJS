package com.example.admin.ajs.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.example.admin.ajs.R;
import com.example.admin.ajs.api.InputStreamVolleyRequest;
import com.example.admin.ajs.listener.OnBackPressedEvent;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;


public class FileDownloadActivity extends AppCompatActivity implements View.OnClickListener, OnBackPressedEvent {

    Button btn_download;
    // InputStreamVolleyRequest request;
    int count;
    String mUrl = "http://books.sonatype.com/mvnref-book/pdf/mvnref-pdf.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_download);
        btn_download = (Button) findViewById(R.id.btn_file_download);
    }

    //        btn_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //Change your url below
//                String mUrl="http://www.ema.europa.eu/docs/en_GB/document_library/Presentation/2014/10/WC500176304.pdf";
//                request = new InputStreamVolleyRequest(Request.Method.GET, mUrl, FileDownloadActivity.this, FileDownloadActivity.this, null);
//                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
//                        new HurlStack());
//                mRequestQueue.add(request);
//            }
    //       });
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_file_download:
                InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                        new Response.Listener<byte[]>() {
                            @Override
                            public void onResponse(byte[] response) {
                                HashMap<String, Object> map = new HashMap<String, Object>();
                                try {
                                    if (response != null) {
//                                FileOutputStream outputStream;
//                                String name = "mvnref-pdf.pdf";
//                                outputStream = openFileOutput(name, Context.MODE_PRIVATE);
//                                outputStream.write(response);
//                                outputStream.close();
//                                this.getClass().getName();
                                        //covert reponse to input stream
                                        InputStream input = new ByteArrayInputStream(response);
                                        File path = Environment.getExternalStorageDirectory();
                                        File file = new File(path, "vaishali.pdf");
                                        map.put("resume_path", file.toString());
                                        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                                        byte data[] = new byte[1024];

                                        long total = 0;

                                        while ((count = input.read(data)) != -1) {
                                            total += count;
                                            output.write(data, 0, count);
                                        }

                                        output.flush();

                                        output.close();
                                        input.close();
                                        // Toast.makeText(this, "Download complete.", Toast.LENGTH_LONG).show();
                                        Toast.makeText(FileDownloadActivity.this, "Download complete",
                                                Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                                    e.printStackTrace();
                                }
                            }
                        },

                        new ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO handle the error
                                error.printStackTrace();
                            }
                        }, null);

                // request.setRetryPolicy(new DefaultRetryPolicy(60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
                mRequestQueue.add(request);

            case R.id.img_back_header:
                finish();
                break;

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

