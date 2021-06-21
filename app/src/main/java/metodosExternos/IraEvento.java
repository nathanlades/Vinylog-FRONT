package metodosExternos;

import android.content.Context;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vinyl.R;

import java.util.HashMap;
import java.util.Map;

public class IraEvento {

    public static void checkIr(Context context, String id_perfil, String id_evento, ImageButton ibt_evento_ir){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/checkIrEvento.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    ibt_evento_ir.setImageResource(R.mipmap.ir_red);
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
                params.put("id_evento", id_evento);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    
    public static void iroNo(Context context, String id_perfil, String id_evento, ImageButton ibt_evento_ir){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/checkIrEvento.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("{")){
                    ibt_evento_ir.setImageResource(R.mipmap.ir);
                    dejardeIr(context, id_perfil, id_evento);
                } else {
                    ibt_evento_ir.setImageResource(R.mipmap.ir_red);
                    irSi(context, id_perfil, id_evento);
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
                params.put("id_evento", id_evento);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private static void irSi(Context context, String id_perfil, String id_evento) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/asistirEvento.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("true")){
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
                params.put("id_evento", id_evento);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private static void dejardeIr(Context context, String id_perfil, String id_evento) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://95.39.184.89/vinyl/noAsistirEvento.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("true")){
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
                params.put("id_evento", id_evento);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
