/*
 * ~ Copyright (c) 2021
 * ~ Dev : Amir Bahador , Amiri
 * ~ City : Iran / Abadan
 * ~ time & date : 4/4/21 4:25 PM
 * ~ email : abadan918@gmail.com
 */

package ir.atgroup.cardbox.utils.HiWeb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HiWeb {


    private final String UTF_8 = "UTF-8";
    private final int HTTP_OK = HttpsURLConnection.HTTP_OK;


    private String GetDataEncoding(JSONObject json) {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator<String> itr = json.keys();
            while (itr.hasNext()) {
                String key = itr.next();
                Object value = json.get(key);
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key, UTF_8));
                sb.append("=");
                sb.append(URLEncoder.encode(value.toString(), UTF_8));
            }
            return "?" + sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String PostDataEncoding(JSONObject json) {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator<String> itr = json.keys();
            while (itr.hasNext()) {
                String key = itr.next();
                Object value = json.get(key);
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key, UTF_8));
                sb.append("=");
                sb.append(URLEncoder.encode(value.toString(), UTF_8));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public boolean GetStatusConnection(Context context, String uri) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL(uri);
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.connect();
                if (urlc.getResponseCode() == HTTP_OK) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String GetUrlConnection(String uri, JSONObject data) {
        try {
            if (data != null) {
                uri = uri + GetDataEncoding(data);
            }
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            StringBuilder result = new StringBuilder();
            if (connection.getResponseCode() == HTTP_OK) {
                InputStream in = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    if (result.length() > 0) {
                        result.append("\n");
                    }
                    result.append(line);
                }
                br.close();
                in.close();
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    public String PostUrlConnection(String uri, JSONObject data) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(PostDataEncoding(data));
            writer.flush();
            writer.close();
            os.close();

            if (connection.getResponseCode() == HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (result.length() > 0) {
                        result.append("\n");
                    }
                    result.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                return result.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }


    public void GetStatusConnection(Context context, String uri, HiWebListener.STATUS connectListener) {
        new GetStatusAsyncTask(context, uri, connectListener).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetStatusAsyncTask extends AsyncTask<String, Void, JSONObject> {
        HiWebListener.STATUS connectListener;
        Context context;
        String uri;

        public GetStatusAsyncTask(Context context, String uri, HiWebListener.STATUS connectListener) {
            this.context = context;
            this.uri = uri;
            this.connectListener = connectListener;
            connectListener.onStart();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            return GetStatusConnectionJson(context, uri);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    String status = jsonObject.getString("status");

                    switch (status) {
                        case "onResult":
                            connectListener.onResult(true);
                            break;
                        case "onError":
                            int error = jsonObject.getInt("error");
                            connectListener.onError(error);
                            break;
                        case "onException":
                            Exception exception = (Exception) jsonObject.get("exception");
                            connectListener.onException(exception);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                connectListener.onResult(false);
            }
        }
    }

    private JSONObject GetStatusConnectionJson(Context context, String uri) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL(uri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.connect();

                if (connection.getResponseCode() == HTTP_OK) {
                    //connectListener.onResult();
                    JSONObject result = new JSONObject();
                    result.put("status", "onResult");
                    return result;
                } else {
                    //connectListener.onError(response);
                    JSONObject result = new JSONObject();
                    result.put("status", "onError");
                    result.put("error", connection.getResponseCode());
                    return result;
                }
            } catch (Exception exception) {
                //connectListener.onException(exception);
                JSONObject result = new JSONObject();
                try {
                    result.put("status", "onException");
                    result.put("exception", exception);
                    return result;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public void GetUrlConnection(String uri, JSONObject data, HiWebListener.GET connectionListener) {
        new GetAsyncTask(uri, data, connectionListener).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetAsyncTask extends AsyncTask<String, Integer, JSONObject> {
        HiWebListener.GET connectListener;
        String uri;
        JSONObject data;

        public GetAsyncTask(String uri, JSONObject data, HiWebListener.GET connectListener) {
            this.uri = uri;
            this.data = data;
            this.connectListener = connectListener;
            connectListener.onStart();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            return GetConnectionJson(uri, data, connectListener);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            connectListener.onProgress(values[0]);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    String status = jsonObject.getString("status");

                    switch (status) {
                        case "onResult":
                            String result = jsonObject.getString("result");
                            connectListener.onResult(result);
                            break;
                        case "onError":
                            int error = jsonObject.getInt("error");
                            connectListener.onError(error);
                            break;
                        case "onException":
                            Exception exception = (Exception) jsonObject.get("exception");
                            connectListener.onException(exception);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                connectListener.onResult("null");
            }
            connectListener.onEnd();
        }
    }

    private JSONObject GetConnectionJson(String uri, JSONObject data, HiWebListener.GET listener) {
        try {
            if (data != null) {
                uri = uri + GetDataEncoding(data);
            }
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (listener.getHeader() != null) {
                for (Map.Entry<String, String> entry : listener.getHeader().entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.connect();
            if (connection.getResponseCode() == HTTP_OK) {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream in = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(line);
                }
                br.close();
                in.close();
                JSONObject result = new JSONObject();
                result.put("status", "onResult");
                result.put("result", stringBuilder.toString());
                return result;
            } else {
                JSONObject result = new JSONObject();
                result.put("status", "onError");
                result.put("error", connection.getResponseCode());
                return result;
            }
        } catch (Exception exception) {
            JSONObject result = new JSONObject();
            try {
                result.put("status", "onException");
                result.put("exception", exception);
                return result;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void PostUrlConnection(String uri, JSONObject data, HiWebListener.POST connectionListener) {
        new PostAsyncTask(uri, data, connectionListener).execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class PostAsyncTask extends AsyncTask<String, Integer, JSONObject> {
        HiWebListener.POST connectListener;
        String uri;
        JSONObject data;

        public PostAsyncTask(String uri, JSONObject data, HiWebListener.POST connectListener) {
            this.uri = uri;
            this.data = data;
            this.connectListener = connectListener;
            connectListener.onStart();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            return PostConnectionJson(uri, data, connectListener);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            connectListener.onProgress(values[0]);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    String status = jsonObject.getString("status");

                    switch (status) {
                        case "onResult":
                            String result = jsonObject.getString("result");
                            connectListener.onResult(result);
                            break;
                        case "onError":
                            int error = jsonObject.getInt("error");
                            connectListener.onError(error);
                            break;
                        case "onException":
                            Exception exception = (Exception) jsonObject.get("exception");
                            connectListener.onException(exception);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                connectListener.onResult("null");
            }
            connectListener.onEnd();
        }
    }

    private JSONObject PostConnectionJson(String uri, JSONObject data, HiWebListener.POST listener) {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            if (listener.getHeader() != null) {
                for (Map.Entry<String, String> entry : listener.getHeader().entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            if (listener.getBody() == null) {
                if (data != null) {
                    writer.write(PostDataEncoding(data));
                }
            } else {
                writer.write(listener.getBody());
            }
            writer.flush();
            writer.close();
            os.close();

            if (connection.getResponseCode() == HTTP_OK) {
                StringBuilder stringBuilder = new StringBuilder();
                InputStream in = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = br.readLine()) != null) {
                    if (stringBuilder.length() > 0) {
                        stringBuilder.append("\n");
                    }
                    stringBuilder.append(line);
                }
                br.close();
                in.close();
                JSONObject result = new JSONObject();
                result.put("status", "onResult");
                result.put("result", stringBuilder.toString());
                return result;
            } else {
                JSONObject result = new JSONObject();
                result.put("status", "onError");
                result.put("error", connection.getResponseCode());
                return result;
            }
        } catch (Exception exception) {
            JSONObject result = new JSONObject();
            try {
                result.put("status", "onException");
                result.put("exception", exception);
                return result;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
