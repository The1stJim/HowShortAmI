package com.example.b

import android.icu.util.Calendar.MONTH
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.b.ui.theme.BTheme
import java.util.Calendar.DATE
import java.util.Calendar.DAY_OF_WEEK
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Calendar


//import android.icu.util.Calendar



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DoTheRest()
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun DoTheRest(){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    var WindowHeight = 0.dp
    var OneThirdWindowHeight = 0.dp
    var TwoThirdsWindowHeight = 0.dp
    var WindowWidth = 0.dp
    var OneThirdWindowWidth = 0.dp
    var TwoThirdsWindowWidth = 0.dp
    var statusBarHeightDp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
//var ButtonLocs = Array(3) { Array(8) { Array<Dp>(2) { 0.dp } } }
    var Quantity = 360
    var OldQuantity = Quantity
    var DaysDifference: Int = 0
    var RefillDate: android.icu.util.Calendar = android.icu.util.Calendar.getInstance()
    var FirstPass = true
    val X:Int = 0           // alias for the X coordinate5
    val Y:Int = 1           // alias for the Y coordinate
    val Top:Int = 0         // alias for the top of a thing
    val Bottom:Int = 1      // alias for the bottom of a thing
    val SubtractButton:Int = 0
    val QuantityButton:Int = 1
    val AddButton:Int = 2
    var SpacerWidth = 0


    //enum class CalendarOffsets {YEAR, MONTH, DAY}
    val YEAR = 1
    val MONTH = 2
    val DAY = 3
    var QuantityChanged = remember { mutableIntStateOf(360) }
    var showQuanitityDialog by remember {mutableStateOf(false)}
    val metrics = DisplayMetrics()

    RefillDate.set(2025,8,20)

    OneThirdWindowHeight = (screenHeight / 3).dp
    TwoThirdsWindowHeight = (OneThirdWindowHeight + OneThirdWindowHeight)
    OneThirdWindowWidth = ((screenWidth / 3) ).dp - 2.dp
    TwoThirdsWindowWidth = (OneThirdWindowWidth + OneThirdWindowWidth) -2.dp

    // Now that we have the basic something something something
    var modifier: Modifier = Modifier
        .height(OneThirdWindowHeight)
        .width(OneThirdWindowWidth - 5.dp)
        .offset(5.dp, statusBarHeightDp)

    CreateButton("-",modifier, 72) {
        QuantityChanged.intValue--
        Quantity = QuantityChanged.intValue
    }

    modifier = Modifier
        .height(OneThirdWindowHeight)
        .width(OneThirdWindowWidth - 10.dp)
        .offset(OneThirdWindowWidth + 8.dp, statusBarHeightDp)

    CreateButton(QuantityChanged.intValue.toString(),modifier,35) {
        showQuanitityDialog = true
    }
    if (showQuanitityDialog) {
        MyAlertDialog (
            onDismissRequest = {showQuanitityDialog = false
                               },
            onConfirmation = {
                showQuanitityDialog = false
                // Handle confirmation action here
                println("Dialog confirmed")

                },
            dialogTitle = "Alert Dialog Title",
            dialogText = "This is the text content of the alert dialog.",

        )
    }
    modifier = Modifier
        .height(OneThirdWindowHeight)
        .width(OneThirdWindowWidth - 5.dp)
        .offset(TwoThirdsWindowWidth + 8.dp, statusBarHeightDp)
    CreateButton("+",modifier,72) {
        QuantityChanged.intValue++
        Quantity = QuantityChanged.intValue
    }
    DatePicker2(OneThirdWindowHeight,statusBarHeightDp,RefillDate,OneThirdWindowWidth)
}

// composable function for a button
@Composable
fun CreateButton(text: String, modifier: Modifier, FontSize: Int, onClick: () -> Unit) {
    var modifier = modifier

    OutlinedButton(

        onClick = onClick,
        modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(Color.Green)
    ) {

        Text(text = text, color = Color.Black, fontSize = FontSize.sp)
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    return
}


@Composable
fun MyAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,

) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )

}
//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
@Composable
fun DatePicker2(OneThirdWindowHeight: Dp,TopOffset: Dp,RefillDate: android.icu.util.Calendar,OneThirdWindowWidth: Dp)
{
    val datesList = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    var modifier4 = Modifier.offset(0.dp, OneThirdWindowHeight + TopOffset)
    val MonthNames = arrayOf ("January","February","March","April","May","June","July","August","September","October","November","December")
    val DaysInMonth = arrayOf(31,28,31,30,31,30,31,30,31,30,31)
    var RefillCalendarDate: android.icu.util.Calendar = RefillDate
    Box(modifier4) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Days of the week
            Row (
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Log.i ("info",RefillDate.get (2).toString())
                Text (MonthNames[RefillDate.get (2)] + "   "+ RefillDate.get (Calendar.YEAR).toString())
                Log.i("Monthname",RefillDate.get(MONTH).toString())

            }
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                datesList.forEach {
                    Text(text = it.substring(0, 3))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            var ActualDate: Int = RefillCalendarDate.get(DATE)

            RefillCalendarDate.set(DATE, 1)

            var FirstDayOfMonth: Int = RefillCalendarDate.get(DAY_OF_WEEK)

            RefillCalendarDate.set(DATE, ActualDate)

            var FirstWeekOfMonth: Boolean = true

/// since the next array needs the days in the month, we might need to know if it is a leap year.

            var IsLeapYear: Int
            var DaysInThisMonth: Int
            if (RefillCalendarDate.get(2) == 2) { // i.e. February
                IsLeapYear = IsItALeapYear(RefillCalendarDate.get(1))
                DaysInThisMonth = DaysInMonth[3] + IsLeapYear
            } else {
                DaysInThisMonth = DaysInMonth[RefillDate.get(2)]
            }
            var OneInt: Int = 0
            // Calendar days Starting at the first day of the month)
            var dayCounter = 1
            var modifier : Modifier
            var Colour: Color
            while (dayCounter <= DaysInThisMonth) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 1..7) {
                        if (OneInt++ < FirstDayOfMonth) {
                            Spacer(modifier = Modifier.weight(1f))
                        } else {
                            if (dayCounter <= DaysInThisMonth) {
                                if (dayCounter == RefillDate.get(2))
                                    Colour = Color.Green
                                else
                                    Colour = Color.White
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .background(Colour, CircleShape)
                                        .padding(8.dp)
                                ) {
                                    Text(text = dayCounter++.toString())
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun IsItALeapYear (Year: Int) :Int{
    if ( (Year % 4 == 0) && (Year % 100 != 0) )
        return 1
    else
        return 0

}

