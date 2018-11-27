package com.example.yu.small01.util;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings({"ALL", "AlibabaClassMustHaveAuthor"})
public class NetUtil {

    private static NetUtil netUtil;

    public NetUtil() {
    }

    public static NetUtil getIntance(){
        if(netUtil==null){
            netUtil=new NetUtil();
        }
        return netUtil;
    }
    public interface CallBack<T>{
        void onSuccess(T t);
    }
    public void requestData(final String str, final Class clas, final CallBack callBack){
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... strings) {
                return requestData(str,clas);
            }

            @Override
            protected void onPostExecute(Object o) {
                callBack.onSuccess(o);
            }
        }.execute(str);
    }

    public <T> T requestData(String str,Class clas){
        return (T) new Gson().fromJson(requestData(str),clas);
    }

    public String requestData(String str){
        try {
            URL url = new URL(str);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if(responseCode==200){
                String s = requestData(urlConnection.getInputStream());
                return s;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String requestData(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        for (String tmp=bufferedReader.readLine();tmp!=null;tmp=bufferedReader.readLine()){
            builder.append(tmp);
        }
        return builder.toString();
    }
}
