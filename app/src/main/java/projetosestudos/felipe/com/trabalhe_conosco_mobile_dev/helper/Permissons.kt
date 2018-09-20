package projetosestudos.felipe.com.trabalhe_conosco_mobile_dev.helper

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class Permissons {

    companion object {
        fun validarPermissao(permissoes: Array<String>, activity: Activity, requestCode: Int) : Boolean {
            if (Build.VERSION.SDK_INT>=23) {
                val mListPermissions: ArrayList<String> = ArrayList()

                for (permission in permissoes) {
                    val mHasPermission: Boolean = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
                    if(!mHasPermission) mListPermissions.add(permission)
                }

                if(mListPermissions.isEmpty()) return true

                val mNewPermissions: Array<String> = Array<String>(mListPermissions.size) {"it = $it"}
                mListPermissions.toArray(mNewPermissions)

                ActivityCompat.requestPermissions(activity, mNewPermissions, requestCode)
            }
            return true
        }
    }

}