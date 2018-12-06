package test.edney.picpay.util;

import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.List;
import androidx.annotation.NonNull;
import test.edney.picpay.BuildConfig;

/**
 * ---------(21/09/2018)-------
 * Created by Edney O. S. Filho
 *
 * Classe para o gerenciamento de logs
 */
public class MyLog {

    private String tag;
    private boolean debug = true;

    public MyLog(@NonNull String tag){
        this.tag = tag;
    }

    public MyLog(@NonNull String tag, boolean debug){
        this.tag = tag;
        this.debug = debug;
    }

    private boolean isDebug(){ return BuildConfig.DEBUG; }

    /*--------------------DEBUG--------------------*/

    public void showD(@NonNull String msg){
        if(debug) {
            if(isDebug())
                Log.d(tag, msg);
        } else
            Log.d(tag, msg);
    }

    public void showD(@NonNull String method, String msg){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+" => { "+msg+" }");
        } else
            Log.d(tag, method+" => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull String identifier, String msg){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+")"+" => { "+msg+" }");
        } else
            Log.d(tag, method+"("+identifier+")"+" => { "+msg+" }");
    }

    public void showD(@NonNull String method, String identifier, Object object){
        final Gson gson = new Gson();

        if(object != null){
            if(debug) {
                if(isDebug())
                    Log.d(tag, method+"("+identifier+")"+" => "+gson.toJson(object));
            } else
                Log.d(tag, method+"("+identifier+")"+" => "+gson.toJson(object));
        }
    }

    public void showD(@NonNull String method, String identifier, int value){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
        } else
            Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
    }

    public void showD(@NonNull String method, String identifier, boolean value){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
        } else
            Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
    }

    public void showD(@NonNull String method, String identifier, float value){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
        } else
            Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
    }

    public void showD(@NonNull String method, String identifier, double value){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
        } else
            Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
    }

    public void showD(@NonNull String method, @NonNull String[] data){
        int size = data.length;
        StringBuilder msg = new StringBuilder();

        for (int i = 0 ; i < size ; i++){
            if(i+1 == size)
                msg.append(data[i]);
            else
                msg.append(data[i]).append(", ");
        }

        if(debug) {
            if(isDebug())
                Log.d(tag, method+" => { "+msg+" }");
        } else
            Log.d(tag, method+" => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull List<String> data){
        int size = data.size();
        StringBuilder msg = new StringBuilder();

        for (int i = 0 ; i < size ; i++){
            if(i+1 == size)
                msg.append(data.get(i));
            else
                msg.append(data.get(i)).append(", ");
        }

        if(debug) {
            if(isDebug())
                Log.d(tag, method+" => { "+msg+" }");
        } else
            Log.d(tag, method+" => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull JSONObject data){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+" => "+data.toString());
        } else
            Log.d(tag, method+" => "+data.toString());
    }

    public void showD(@NonNull String method, @NonNull String identifier, @NonNull String[] data){
        int size = data.length;
        StringBuilder msg = new StringBuilder();

        for (int i = 0 ; i < size ; i++){
            if(i+1 == size)
                msg.append(data[i]);
            else
                msg.append(data[i]).append(", ");
        }

        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+") => { "+msg+" }");
        } else
            Log.d(tag, method+"("+identifier+") => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull String identifier, @NonNull int[] data){
        int size = data.length;
        StringBuilder msg = new StringBuilder();

        for (int i = 0 ; i < size ; i++){
            if(i+1 == size)
                msg.append(String.valueOf(data[i]));
            else
                msg.append(String.valueOf(data[i])).append(", ");
        }

        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+") => { "+msg+" }");
        } else
            Log.d(tag, method+"("+identifier+") => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull String identifier, @NonNull Float[] data){
        int size = data.length;
        StringBuilder msg = new StringBuilder();

        for (int i = 0 ; i < size ; i++){
            if(i+1 == size)
                msg.append(String.valueOf(data[i]));
            else
                msg.append(String.valueOf(data[i])).append(", ");
        }

        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+") => { "+msg+" }");
        } else
            Log.d(tag, method+"("+identifier+") => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull String identifier, @NonNull List<String> data){
        int size = data.size();
        StringBuilder msg = new StringBuilder();

        for (int i = 0 ; i < size ; i++){
            if(i+1 == size)
                msg.append(data.get(i));
            else
                msg.append(data.get(i)).append(", ");
        }

        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+") => { "+msg+" }");
        } else
            Log.d(tag, method+"("+identifier+") => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull String identifier, @NonNull JSONObject data){
        if(debug) {
            if(isDebug())
                Log.d(tag, method+"("+identifier+") => "+data.toString());
        } else
            Log.d(tag, method+"("+identifier+") => "+data.toString());
    }

    /*--------------------ERROR--------------------*/

    public void showE(@NonNull String method, String msg){
        if(debug) {
            if(isDebug())
                Log.e(tag, method+" => { "+msg+" }");
        } else
            Log.e(tag, method+" => { "+msg+" }");
    }

    /*--------------------INFO--------------------*/

    public void showI(@NonNull String method, String msg){
        if(debug) {
            if(isDebug())
                Log.i(tag, method+" => { "+msg+" }");
        } else
            Log.i(tag, method+" => { "+msg+" }");
    }
}