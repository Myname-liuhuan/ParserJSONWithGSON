package com.example.parserjsonwithgson;

import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button01=findViewById(R.id.button01);
        final EditText editText01=findViewById(R.id.editText01);

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText01.getText()!=null){
                    sendRequestUseOkHttp(String.valueOf(editText01.getText()));
                }else{
                    Toast.makeText(MainActivity.this,"请输入URL",Toast.LENGTH_SHORT);
                }
            }
        });

    }

    private void sendRequestUseOkHttp(final String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client =new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    GSONParserJSON(responseData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void GSONParserJSON(String JsonData){
        try{
            Gson gson=new Gson();
            List<App> appList=gson.fromJson(JsonData,new TypeToken<List<App>>(){}.getType());//appList表示JSON里面提取的对象的数组
            for(App app:appList){
                Log.d("huan","id is:"+app.getId());
                Log.d("huan","name is:"+app.getName());
                Log.d("huan","version is:"+app.getVersion());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
