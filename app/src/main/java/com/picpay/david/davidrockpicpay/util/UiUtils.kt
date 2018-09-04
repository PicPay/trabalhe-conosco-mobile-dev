package com.picpay.david.davidrockpicpay.util


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.support.annotation.AttrRes
import android.support.annotation.RawRes
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.picpay.david.davidrockpicpay.R
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.HashMap

object UiUtil {

    object Dialogs {

        fun progress(context: Context, message: String, show: Boolean = true, cancelable: Boolean = true): ProgressDialog {
            val progressBar = ProgressDialog(context, R.style.V1AlertDialogStyle)
            progressBar.setMessage(message)
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressBar.setCancelable(cancelable)
            if (show) {
                progressBar.show()
            }
            return progressBar
        }

        fun progress(context: Context, @StringRes message: Int, show: Boolean = true, cancelable: Boolean = true): ProgressDialog {
            return progress(context, context.getString(message), show, cancelable)
        }
//
//        fun dialogAlertAction(context: Context, message: String?, action: DialogInterface.OnClickListener? = null, cancelable: Boolean = false, show: Boolean = true): Dialog {
//            val dialog = Dialog(context)
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialog.setCancelable(cancelable)
//            dialog.setContentView(R.layout.custom_alert_dialogbox)
//            val text = dialog.findViewById(R.id.mensagem) as TextView
//            text.text = message
//
//            val okButton = dialog.findViewById(R.id.btnOk) as Button
//            if (action == null) {
//                okButton.setOnClickListener {
//                    dialog.dismiss()
//                }
//            } else {
//                okButton.setOnClickListener { action.onClick(dialog, R.id.btnOk) }
//            }
//            if (show)
//                dialog.show()
//            return dialog
//        }

//        fun dialogAlertAction(context: Context, @StringRes message: Int, action: DialogInterface.OnClickListener? = null, cancelable: Boolean = false, show: Boolean = true): Dialog {
//            return dialogAlertAction(context, context.getString(message), action, cancelable, show)
//        }
    }

    object Messages {
        private fun processBreakLines(message: String?): String? {
            if (message != null) {
                return message.replace("<br>", "\n")
            }
            return null
        }

        fun message(context: Context, message: String?, show: Boolean = true): Toast {
            val toast = Toast.makeText(context, processBreakLines(message), Toast.LENGTH_LONG)
            if (show) {
                toast.show()
            }
            return toast
        }

        fun message(context: Context, @StringRes message: Int, show: Boolean = true): Toast {
            return message(context, context.getString(message), show)
        }
    }

    fun isOnlineOrMessage(context: Context): Boolean {
        var isOnline = true
        if (!Util.isOnline(context)) {
            Toast.makeText(context, R.string.txt_connection_error, Toast.LENGTH_LONG).show()
            isOnline = false
        }
        return isOnline;
    }

    object Validates {

        private val EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"

        fun email(email: String?): Boolean {
            if (email.isNullOrBlank())
                return false

            val pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email!!.trim())
            return matcher.matches()

        }

        fun textViewEmpty(textView: TextView, fieldName: String): Boolean {
            if (textView.text.isNullOrBlank()) {
                    textView.error = textView.context.getString(R.string.validate_empty, fieldName)
                return false
            } else {
                textView.error = null
            }
            return true
        }

        fun textViewMinLength(textView: TextView, minLength: Int, fieldName: String): Boolean {
            if (!textViewEmpty(textView, fieldName)) {
                return false
            } else {
                textView.error = null
            }
            if (textView.text.length < minLength) {
                textView.error = textView.context.getString(R.string.validate_length, fieldName, minLength)
                return false
            } else {
                textView.error = null
            }
            return true
        }

        fun textViewEmail(textView: TextView, fieldName: String = "email"): Boolean {
            if (!textViewEmpty(textView, fieldName)) {
                return false
            } else {
                textView.error = null
            }
            if (!email(textView.text.toString())) {
                textView.error = textView.context.getString(R.string.validate_email, fieldName)
                return false
            } else {
                textView.error = null
            }
            return true
        }
    }

    object Image {
        fun getBitmapFromStringImage(image: String): Bitmap {
            val decodedString = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        }

    }

    object Layout {

        fun startRevealAnimation(viewInside: View, viewOutside: View) {
            val cx = viewOutside.measuredWidth / 2
            val cy = viewOutside.measuredHeight / 2
            val anim = ViewAnimationUtils.createCircularReveal(viewInside, cx, cy, 50f, viewOutside.width.toFloat())
            anim.duration = 500
            anim.interpolator = AccelerateInterpolator(2f)
            anim.addListener(object : AnimatorListenerAdapter() {})
            anim.start()
        }

        fun decorateRecyclerView(context: Context, recyclerView: RecyclerView) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            val offsetPx = context.resources.getDimension(R.dimen.bottom_offset_dp)
            val bottomOffsetDecoration = BottomOffsetDecoration(offsetPx.toInt())
            recyclerView.addItemDecoration(bottomOffsetDecoration)
            recyclerView.setHasFixedSize(true)
        }


        fun animateViewVisibility(view: View, visibility: Int) {
            // cancel runnning animations and remove any listeners
            view.animate().cancel()
            view.animate().setListener(null)
            // animate making view visible
            if (visibility == View.VISIBLE) {
                view.animate().alpha(1f).start()
                view.visibility = View.VISIBLE
            } else {
                view.animate().setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = visibility
                    }
                }).alpha(0f).start()
            }
        }

        fun getThemedResId(activity: Activity, @AttrRes attr: Int): Int {
            val a = activity.theme.obtainStyledAttributes(intArrayOf(attr))
            val resId = a.getResourceId(0, 0)
            a.recycle()
            return resId
        }
    }

    object Notifications {

        private var vibrator: Vibrator? = null
        private var soundPool: SoundPool? = null

        fun startSound(context: Context, isLooping: Boolean = true, @RawRes sound: Int? = null) {
            stopSound()

            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()

            soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).build()
            var resource = R.raw.winxp_balloon
            if (sound != null) {
                resource = sound
            }
            val loop = if (isLooping) -1 else 0 // -1 ele faz loop, 0 ele toca 1 vez
            soundPool?.setOnLoadCompleteListener { soundPool, sampleId, status ->
                Notifications.soundPool?.play(sampleId, 1F, 1F, 1, loop, 1f)
            }
            soundPool?.load(context, resource, 1)

        }


        fun startVibrationLong(context: Context) {
            stopVibration()
            vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            val pattern = longArrayOf(200, 100, 200, 275, 425, 100, 200, 100, 200, 275, 425, 100, 75, 25, 75, 125, 75, 25, 75, 125, 100, 100)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(80000, 5))
            } else {
                vibrator?.vibrate(pattern, 0)
            }
        }

        fun startVibrationShort(context: Context) {
            stopVibration()
            vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(80, 5))
            } else {
                vibrator?.vibrate(80)
            }
        }

        fun stopSound() {
            soundPool?.release()
            soundPool = null
        }

        fun stopVibration() {
            vibrator?.cancel()
        }
    }


}