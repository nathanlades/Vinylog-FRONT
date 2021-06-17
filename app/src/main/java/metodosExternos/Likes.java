package metodosExternos;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vinyl.R;
import com.example.vinyl.ResenaActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import POJO.Resena;

public class Likes {

    Likes(){}

    static boolean check = false;

    public static boolean darLike(Context context, String id_perfil, String id_resena, ImageView iv_resena_like, TextView tv_resena_like_numero){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/darLike.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("true")){
                    iv_resena_like.setImageResource(R.mipmap.like_red);
                    countLike(context,id_resena,tv_resena_like_numero);
                } else {
                    uncheckLike(context, id_perfil, id_resena, iv_resena_like,tv_resena_like_numero);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Si no se ha podido ejecutar
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id_perfil",id_perfil);
                params.put("id_resena", id_resena);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return true;
    }

    public static boolean checkLike(Context context, String id_perfil, String id_resena, ImageView iv_resena_like){
        check = false;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/comprobarLike.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    iv_resena_like.setImageResource(R.mipmap.like_red);
                    check = true;
                } else {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Si no se ha podido ejecutar
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id_perfil",id_perfil);
                params.put("id_resena", id_resena);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        return check;
    }

    public static void uncheckLike(Context context, String id_perfil, String id_resena, ImageView iv_resena_like, TextView tv_resena_like_numero) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/quitarLike.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("true")){
                    iv_resena_like.setImageResource(R.mipmap.like);
                    countLike(context,id_resena,tv_resena_like_numero);
                } else {
                    //No hay like que quitar
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Si no se ha podido ejecutar
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id_perfil",id_perfil);
                params.put("id_resena", id_resena);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static void countLike(Context context, String id_resena, TextView tv_resena_like_numero) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/countLikes.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    String s = response;
                    String[] corte = s.split(":");
                    String[] corte2 = corte[1].split("\"");
                    tv_resena_like_numero.setText(corte2[1]);
                } else {
                    //No hay like que quitar
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Si no se ha podido ejecutar
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("id_resena", id_resena);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
