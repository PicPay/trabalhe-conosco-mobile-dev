package test.edney.picpay.util;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import androidx.annotation.NonNull;

/**
 * Created by Edney O. S. Filho - 21/09/2018
 */
public class MyLog {
    private String tag;

    public MyLog(@NonNull String tag){
        this.tag = tag;
    }

    /*--------------------DEBUG--------------------*/

    public void showD(@NonNull String msg){
        Log.d(tag, msg);
    }

    public void showD(@NonNull String method, String msg){
        Log.d(tag, method+" => { "+msg+" }");
    }

    public void showD(@NonNull String method, String identifier, String msg){
        Log.d(tag, method+"("+identifier+")"+" => { "+msg+" }");
    }

    public void showD(@NonNull String method, String identifier, int value){
        Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
    }

    public void showD(@NonNull String method, String identifier, boolean value){
        Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
    }

    public void showD(@NonNull String method, String identifier, float value){
        Log.d(tag, method+"("+identifier+")"+" => { "+String.valueOf(value)+" }");
    }

    public void showD(@NonNull String method, String identifier, double value){
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
        Log.d(tag, method+" => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull JSONObject data){
        Log.d(tag, method+" => "+data.toString());
    }

    public void showD(@NonNull String method, String identifier, @NonNull JSONArray data){
        Log.d(tag, method+"("+identifier+")"+" => "+data.toString());
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
        Log.d(tag, method+"("+identifier+") => { "+msg+" }");
    }

    public void showD(@NonNull String method, @NonNull String identifier, @NonNull JSONObject data){
        Log.d(tag, method+"("+identifier+") => "+data.toString());
    }

    /*--------------------ERROR--------------------*/

    public void showE(@NonNull String method, String msg){
        Log.e(tag, method+" => { "+msg+" }");
    }

    /*--------------------INFO--------------------*/

    public void showI(@NonNull String method, String msg){
        Log.i(tag, method+" => { "+msg+" }");
    }
}