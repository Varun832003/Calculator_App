package com.example.calculatorapp

import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    private var tvInput: TextView? = null
    private var lastNumeric = false
    private var lastDecimal = false
    private var mediaPlayer : MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    mediaPlayer = MediaPlayer.create(this,R.raw.button_sound_clipped)

        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        mediaPlayer?.start()

    }
    fun clear(view: View){
        mediaPlayer?.start()
        val editableText : Editable? =  tvInput?.editableText
        val length = editableText?.length
        if (length != null) {
            if(length>0){
                editableText.delete(length-1, length)
            }
            if(!editableText.contains(".")){
                lastDecimal = false
                lastNumeric = true
            }
        }
    }
    fun allClear(view: View){
        mediaPlayer?.start()
        tvInput?.text = ""
        lastNumeric = true
        lastDecimal = false
    }
    fun onDecimalPoint(view: View){
        mediaPlayer?.start()
        if(lastNumeric && !lastDecimal){
            tvInput?.append(".")
            lastNumeric = false
//            lastDecimal = true
        }
    }
     fun onOperator(view: View){
         mediaPlayer?.start()
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
            }
        }
    }
    private fun isOperatorAdded(value: String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("x") || value.contains("+") || value.contains("-")
        }
    }
    fun onEquals(view: View){
        mediaPlayer?.start()
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(one.contains("%")){
                        one = one.substring(0,one.length-1)
                        one = (one.toDouble()/100).toString()
                    }
                    if(two.contains("%")){
                        two = two.substring(0,two.length-1)
                        two = (two.toDouble()/100).toString()
                    }

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(one.contains("%")){
                        one = one.substring(0,one.length-1)
                        one = (one.toDouble()/100).toString()
                    }
                    if(two.contains("%")){
                        two = two.substring(0,two.length-1)
                        two = (two.toDouble()/100).toString()
                    }

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(one.contains("%")){
                        one = one.substring(0,one.length-1)
                        one = (one.toDouble()/100).toString()
                    }
                    if(two.contains("%")){
                        two = two.substring(0,two.length-1)
                        two = (two.toDouble()/100).toString()
                    }

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }else if(tvValue.contains("x")){
                    val splitValue = tvValue.split("x")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(one.contains("%")){
                        one = one.substring(0,one.length-1)
                        one = (one.toDouble()/100).toString()
                    }
                    if(two.contains("%")){
                        two = two.substring(0,two.length-1)
                        two = (two.toDouble()/100).toString()
                    }

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else if(tvValue.contains("%") && !tvValue.contains("+") && !tvValue.contains("-") &&
                    !tvValue.contains("x") && !tvValue.contains("/")){

                    tvValue = tvValue.substring(0,tvValue.length-1)
                    tvValue = (tvValue.toDouble()/100).toString()
                    tvInput?.text = tvValue
                }



            }catch (e: ArithmeticException){
                print(e.stackTrace)
            }
        }
    }
    fun onPercentage(view: View){
        mediaPlayer?.start()
        if(lastNumeric){
            tvInput?.append((view as Button).text)
        }

    }
    private fun removeZeroAfterDot(result: String):String{
        var value = result
        if(result.endsWith(".0")){
            value = value.substring(0,result.length-2)
        }
        return value
    }
}


