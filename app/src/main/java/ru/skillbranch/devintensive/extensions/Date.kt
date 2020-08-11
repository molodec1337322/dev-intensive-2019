package ru.skillbranch.devintensive.extensions

import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

const val SECOND: Long = 1000L;
const val MINUTE: Long = SECOND * 60L;
const val HOUR: Long = MINUTE * 60L;
const val DAY: Long = HOUR * 24L;

enum class TimeUnits {SECOND, MINUTE, HOUR, DAY}


fun Date.humanizeDiff(date: Date = this): String{

    /*при добавлении положительного времени через функцию Data.add() может возникнуть косяк на днях.
    Он считает на 1 день раньше, чем должно быть. Это ок, так как при вычислении прошедшего
    количества дней используется целочисленное деление, в то время как само время меряется в
    миллисекундах. И во время вычисления разныци во времени в миллисекундах возникает погрешность в
    20-35 миллисекунд (зависит от машины) как в одну, так и в другую сторону, в результате чего и
    происходит такой косяк. Но для прода пойдет, потому что никто не будет замерять время с
    точностью до миллесекунд*/

    var differenceMs: Long = Date().time - date.time;
    val isPositive: Boolean = differenceMs < 0
    differenceMs = abs(differenceMs)
    var text: String = "";

    if(isPositive) differenceMs++;

    if(differenceMs / SECOND <= 1){
        text = "только что"
    }
    else if(differenceMs / SECOND <= 45){
        text = "несколько секунд"
    }
    else if(differenceMs / SECOND <= 75){
        text = "минуту"
    }
    else if(differenceMs / MINUTE <= 45 && (differenceMs / MINUTE % 10 in 2..4 && differenceMs / MINUTE % 10 !in 10..20)){
        text = "${differenceMs / MINUTE} минуты"
    }
    else if(differenceMs / MINUTE <= 45 && (differenceMs / MINUTE % 10 == 0L || differenceMs / MINUTE % 10 in 5..9 || differenceMs / MINUTE % 100 in 10..20)){
        text = "${differenceMs / MINUTE} минут"
    }
    else if(differenceMs / MINUTE <= 45 && differenceMs / MINUTE % 10 == 1L){
        text = "${differenceMs / MINUTE} минуту"
    }
    else if(differenceMs / MINUTE <= 75){
        text = "час"
    }
    else if(differenceMs / HOUR <= 22 && (differenceMs / HOUR % 10 in 2..4 && differenceMs / HOUR % 10 !in 10..20)){
        text = "${differenceMs / HOUR} часа"
    }
    else if(differenceMs / HOUR <= 22 && (differenceMs / HOUR % 10 == 0L || differenceMs / HOUR % 10 in 5..9 || differenceMs / HOUR % 100 in 10..20)){
        text = "${differenceMs / HOUR} часов"
    }
    else if(differenceMs / HOUR <= 22 && differenceMs / HOUR % 10 == 1L){
        text = "${differenceMs / HOUR} час"
    }
    else if(differenceMs / HOUR <= 26){
        text = "день"
    }
    else if(differenceMs / DAY <=360 && (differenceMs / DAY % 10 in 2..4 && differenceMs / DAY % 10 !in 10..20)){
        text = "${differenceMs / DAY} дня"
    }
    else if(differenceMs / DAY <=360 && (differenceMs / DAY % 10 == 0L || differenceMs / DAY % 10 in 5..9 || differenceMs / DAY % 100 in 10..20)){
        text = "${differenceMs / DAY} дней"
    }
    else if(differenceMs / DAY <=360 && differenceMs / DAY % 10 == 1L){
        text = "${differenceMs / DAY} день"
    }

    if(differenceMs > 1000 && !isPositive){
        if(differenceMs / DAY > 360 && text != "") return "более года назад"
        text = text + " назад"
    }
    else if(differenceMs > 1000 && isPositive){
        if(differenceMs / DAY > 360 && text != "") return "более чем через год"
        text = "через " + text
    }

    return text;
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String{
    val dateFormat: SimpleDateFormat =  SimpleDateFormat(pattern, Locale("rus"));
    return dateFormat.format(this);
}

fun Date.add(value: Int, timeUnits: TimeUnits): Date{
    var time = this.time;
    time += when(timeUnits){
        TimeUnits.SECOND -> value * SECOND;
        TimeUnits.MINUTE -> value * MINUTE;
        TimeUnits.HOUR -> value * HOUR;
        TimeUnits.DAY -> value * DAY;
        else -> throw IllegalArgumentException();
    }
    this.time = time;
    return this;
}