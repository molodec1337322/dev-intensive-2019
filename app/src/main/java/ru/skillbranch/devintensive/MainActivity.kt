package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageText: EditText
    lateinit var sendButton: ImageView

    lateinit var mBender: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("M_MainActivity", "onCreate")

        benderImage = iv_main_character
        textTxt = tv_text


        val etMessageText = savedInstanceState?.getString("ET_MESSAGE") ?: ""

        messageText = et_messgae
        messageText.setText(etMessageText)

        sendButton = iv_send_message

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name

        mBender = Bender(status = Bender.Status.valueOf(status), question = Bender.Question.valueOf(question))
        val (r, g, b) = mBender.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

        textTxt.setText(mBender.askQuestion())

        sendButton.setOnClickListener(this)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.getString("STATUS", mBender.status.name)
        outState?.getString("QUESTION", mBender.question.name)
        outState?.getString("ET_MESSAGE", et_messgae.text.toString())
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.iv_send_message){
            val (phrase, color) = mBender.listenAnswer(messageText.text.toString().toLowerCase())
            val (r, g, b) = color
            messageText.setText("")
            benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
            textTxt.text = phrase
        }
    }
}
