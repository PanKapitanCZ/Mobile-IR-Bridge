package com.example.remotecontroller

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.remotecontroller.ui.theme.RemoteControllerTheme
import kotlinx.coroutines.launch
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {

    private lateinit var enableBtLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private val bluetoothViewModel: MainViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ActivityResultLauncher
        enableBtLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            bluetoothViewModel.isBluetoothEnabled.value = result.resultCode == RESULT_OK
            bluetoothViewModel.toastMessage.value = if (result.resultCode == RESULT_OK) "Bluetooth enabled" else "Bluetooth enabling failed"
        }

        // Request Bluetooth permissions
        permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            bluetoothViewModel.toastMessage.value = if (permissions[Manifest.permission.BLUETOOTH_CONNECT] == true && permissions[Manifest.permission.BLUETOOTH_SCAN] == true) {
                "Bluetooth permissions granted"
            } else {
                "Bluetooth permissions denied"
            }
        }

        permissionsLauncher.launch(arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        ))


        enableEdgeToEdge()
        setContent {
            RemoteControllerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(innerPadding, enableBtLauncher, bluetoothViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    innerPaddingValues: PaddingValues,
    enableBtLauncher: ActivityResultLauncher<Intent>,
    bluetoothViewModel: MainViewModel
){
    val context = LocalContext.current
    val navController = rememberNavController()
    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    val viewModel: MainViewModel = viewModel()
    val receivedData = bluetoothViewModel.receivedData

    val selectedComponent by viewModel.selectedComponent.collectAsState()

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = (-50).dp,
        sheetContent = {

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column {
                        OutlinedIconButton(
                            onClick = { viewModel.selectComponent("tvcontroller") },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_tv_24),
                                contentDescription = "tv button"
                            )
                        }
                    }
                    Column {
                        OutlinedIconButton(
                            onClick = { viewModel.selectComponent("speakercontroller") },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_speaker_24),
                                contentDescription = "speaker button"
                            )
                        }
                    }
                    Column {
                        OutlinedIconButton(
                            onClick = { viewModel.selectComponent("rgbcontroller") },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_settings_remote_24),
                                contentDescription = "remote rgb controller button"
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .offset(y = (-20).dp)
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (selectedComponent) {
                        "tvcontroller" -> TVBind()
                        "rgbcontroller" -> RGBBind()
                        "speakercontroller" -> SpeakerBind()
                    }
                }
                Box(modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)) {
                    ButtonLook(width = 100,
                        onClick = {
                            bluetoothViewModel.connectToDevice("ESP32", context)

                        }) {
                        Text(text = "LISTEN")
                    }
                }

            }
        }) {
        NavHost(
            navController = navController,
            startDestination = "listscreen",
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize()
        ){
            composable("listscreen"){ ListScreen(navController) }
            composable("tvcontroller"){ TVController(navController, scope, sheetState) }
            composable("speakercontroller"){ SpeakerController(navController, scope, sheetState) }
            composable("rgbcontroller"){ RGBController(navController, scope, sheetState) }
            composable("dialcontroller"){ DialController(navController, scope, sheetState) }
        }
    }

    
}
@Composable
fun RGBBind(){
    val viewModel: MainViewModel = viewModel()
    ArrowButtonSwitcher(buttons = listOf(
        { CircleButton(
            onClick = { viewModel.sendIntArray(0) }){
            Icon(
                painter = painterResource(R.drawable.brightness_decrease),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        } },
        {CircleButton(
            onClick = { viewModel.sendIntArray(1) }){
            Icon(
                painter = painterResource(R.drawable.brightness_increase),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }},
        { CircleButton(
            colorContainer = Color(200, 0, 0),
            onClick = { viewModel.sendIntArray(2) }){
            Icon(
                painter = painterResource(R.drawable.zero),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }},
        { CircleButton(
            colorContainer = Color(0, 128, 0),
            onClick = { viewModel.sendIntArray(3) }){
            Icon(
                painter = painterResource(R.drawable.i),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }},
        { CircleButton(
            colorContainer = Color(200, 0, 0),
            onClick = { viewModel.sendIntArray(4) }){
            Text(text = "R", fontSize = 24.sp)
        }},
        { CircleButton(
            colorContainer = Color(0, 128, 0),
            onClick = { viewModel.sendIntArray(5) }){
            Text(text = "G", fontSize = 24.sp)
        }},
        { CircleButton(
            colorContainer = Color(0, 0, 180),
            onClick = { viewModel.sendIntArray(6) }){
            Text(text = "B", fontSize = 24.sp)
        }},
        { CircleButton(
            onClick = { viewModel.sendIntArray(7) }){
            Text(text = "W", fontSize = 24.sp)
        }},
        { CircleButton(
            colorContainer = Color(255, 69, 0),
            onClick = { viewModel.sendIntArray(8) })},
        { CircleButton(
            colorContainer = Color(46, 128, 128),
            onClick = { viewModel.sendIntArray(9) })},
        { CircleButton(
            colorContainer = Color(65, 105, 225),
            onClick = { viewModel.sendIntArray(10) })},
        { CircleButton(
            onClick = { viewModel.sendIntArray(11) }){
            Icon(
                painter = painterResource(R.drawable.color_circle),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }},
        { CircleButton(
            colorContainer = Color(255, 165, 0),
            onClick = { viewModel.sendIntArray(12) })},
        { CircleButton(
            colorContainer = Color(70, 130, 180),
            onClick = { viewModel.sendIntArray(13) })},
        { CircleButton(
            colorContainer = Color(106, 90, 205),
            onClick = { viewModel.sendIntArray(14) })},
        { CircleButton(
            onClick = { viewModel.sendIntArray(15) }){
            Icon(
                painter = painterResource(R.drawable.color_palette),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }},
        { CircleButton(
            colorContainer = Color(255, 215, 0),
            onClick = { viewModel.sendIntArray(16) })},
        { CircleButton(
            colorContainer = Color(65, 105, 225),
            onClick = { viewModel.sendIntArray(17) })},
        { CircleButton(
            colorContainer = Color(199, 21, 133),
            onClick = { viewModel.sendIntArray(18) })},
        { CircleButton(
            onClick = { viewModel.sendIntArray(19) }){
            Icon(
                painter = painterResource(R.drawable.palette),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }},
        { CircleButton(
            colorContainer = Color(255, 255, 0),
            onClick = { viewModel.sendIntArray(20) })},
        { CircleButton(
            colorContainer = Color(0, 0, 139),
            onClick = { viewModel.sendIntArray(21) })},
        { CircleButton(
            colorContainer = Color(255, 20, 147),
            onClick = { viewModel.sendIntArray(22) })},
        { CircleButton(
            onClick = { viewModel.sendIntArray(23) }){
            Icon(
                painter = painterResource(R.drawable.colors),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                tint = Color.Unspecified
            )
        }},

    ))

}
@Composable
fun SpeakerBind(){
    val viewModel: MainViewModel = viewModel()
    ArrowButtonSwitcher(
        buttons = listOf(
            { Button(
                onClick = { viewModel.sendIntArray(0) },
                colors = ButtonColors(Color.Red, Color.White, Color.Unspecified, Color.Unspecified),
                modifier = Modifier
                    .padding(16.dp)
                    .height(40.dp)
                    .width(80.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_power_settings_new_24),
                    contentDescription = "Icon",
                )
            } },
            { ButtonLook(70, onClick = {viewModel.sendIntArray(1)}) {
                Icon(
                    painter = painterResource(R.drawable.previous),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)
                )
            } },
            {ButtonLook(70, onClick = {viewModel.sendIntArray(2)}) {
                Icon(
                    painter = painterResource(R.drawable.pause_play),
                    contentDescription = "Icon",
                    modifier = Modifier.size(17.dp)
                )
            }},
            {ButtonLook(70, onClick = {viewModel.sendIntArray(3)}) {
                Icon(
                    painter = painterResource(R.drawable.next_button),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)

                )
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(4)}) {
                Text(text = "INPUT")
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(5)}) {
                Text(text = "AUX")
            }},
            {MiddleButtons(leftOnClick = {viewModel.sendIntArray(6)}, leftBtnTint = Color.Red)},
            {MiddleButtons(rightOnClick = {viewModel.sendIntArray(7)}, rightBtnTint =  Color.Red)},
            {MiddleButtons(topOnClick = {viewModel.sendIntArray(8)}, topBtnTint = Color.Red)},
            {MiddleButtons(bottomOnClick = {viewModel.sendIntArray(9)}, bottomBtnTint = Color.Red)},
            {MiddleButtons(middleOnClick = {viewModel.sendIntArray(10)}, middleBtnTint = Color.Red)},

            {ButtonLook(onClick = {viewModel.sendIntArray(11)}) {
                Icon(
                    painter = painterResource(R.drawable.bluetooth),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)
                )
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(12)}) {
                Text(text = "RADIO")
            }},
            { VerticalButton(text = "VOL", onPlusClick = {viewModel.sendIntArray(13)}, plusBtnTint = Color.Red) },
            { VerticalButton(text = "VOL", onMinusClick = {viewModel.sendIntArray(14)}, minusBtnTint = Color.Red) },
            { VerticalButton(text = "BAL", onPlusClick = {viewModel.sendIntArray(15)}, plusBtnTint = Color.Red) },
            { VerticalButton(text = "BAL", onMinusClick = {viewModel.sendIntArray(16)}, minusBtnTint = Color.Red) },
            {ButtonLook(onClick = {viewModel.sendIntArray(17)}){
                Text(text = "MENU")
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(18)}){
                Icon(
                    painter = painterResource(R.drawable.volume_mute),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)

                )
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(19)}){
                Text(text = "SLEEP")
            }},
            {CircleButton("1", onClick = {viewModel.sendIntArray(20)})},
            {CircleButton("2", onClick = {viewModel.sendIntArray(21)})},
            {CircleButton("3", onClick = {viewModel.sendIntArray(22)})},
            {CircleButton("4", onClick = {viewModel.sendIntArray(23)})},
            {CircleButton("5", onClick = {viewModel.sendIntArray(24)})},
            {CircleButton("6", onClick = {viewModel.sendIntArray(25)})},
            {CircleButton("7", onClick = {viewModel.sendIntArray(26)})},
            {CircleButton("8", onClick = {viewModel.sendIntArray(27)})},
            {CircleButton("9", onClick = {viewModel.sendIntArray(28)})},
            {CircleButton("0", onClick = {viewModel.sendIntArray(29)})}
        )
    )
}
@Composable
fun TVBind(){
    val viewModel: MainViewModel = viewModel()
    ArrowButtonSwitcher(
        buttons = listOf(
            { Button(
                onClick = { viewModel.sendIntArray(0) },
                colors = ButtonColors(Color.Red, Color.White, Color.Unspecified, Color.Unspecified),
                modifier = Modifier
                    .padding(16.dp)
                    .height(40.dp)
                    .width(80.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_power_settings_new_24),
                    contentDescription = "Icon",
                )
            } },
            { ButtonLook(70, onClick = {viewModel.sendIntArray(1)}) {
                Text(text = "TV", fontSize = 16.sp)
            } },
            {ButtonLook(70, onClick = {viewModel.sendIntArray(2)}) {
                Icon(
                    painter = painterResource(R.drawable.house),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)
                )
            }},
            {ButtonLook(70, onClick = {viewModel.sendIntArray(3)}) {
                Icon(
                    painter = painterResource(R.drawable.log_out),
                    contentDescription = "Icon"

                )
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(4)}) {
                Text(text = "INPUT")
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(5)}) {
                Text(text = "APPS")
            }},
            {MiddleButtons(leftOnClick = {viewModel.sendIntArray(6)}, leftBtnTint = Color.Red)},
            {MiddleButtons(rightOnClick = {viewModel.sendIntArray(7)}, rightBtnTint =  Color.Red)},
            {MiddleButtons(topOnClick = {viewModel.sendIntArray(8)}, topBtnTint = Color.Red)},
            {MiddleButtons(bottomOnClick = {viewModel.sendIntArray(9)}, bottomBtnTint = Color.Red)},
            {MiddleButtons(middleOnClick = {viewModel.sendIntArray(10)}, middleBtnTint = Color.Red)},

            {ButtonLook(onClick = {viewModel.sendIntArray(11)}) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)

                )
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(12)}) {
                Text(text = "EXIT")
            }},
            { VerticalButton(text = "VOL", onPlusClick = {viewModel.sendIntArray(13)}, plusBtnTint = Color.Red) },
            { VerticalButton(text = "VOL", onMinusClick = {viewModel.sendIntArray(14)}, minusBtnTint = Color.Red) },
            { VerticalButton(text = "CH", onPlusClick = {viewModel.sendIntArray(15)}, plusBtnTint = Color.Red) },
            { VerticalButton(text = "CH", onMinusClick = {viewModel.sendIntArray(16)}, minusBtnTint = Color.Red) },
            {ButtonLook(onClick = {viewModel.sendIntArray(17)}){
                Text(text = "MENU")
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(18)}){
                Icon(
                    painter = painterResource(R.drawable.volume_mute),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)

                )
            }},
            {ButtonLook(onClick = {viewModel.sendIntArray(19)}){
                Text(text = "SLEEP")
            }},
            {CircleButton("1", onClick = {viewModel.sendIntArray(20)})},
            {CircleButton("2", onClick = {viewModel.sendIntArray(21)})},
            {CircleButton("3", onClick = {viewModel.sendIntArray(22)})},
            {CircleButton("4", onClick = {viewModel.sendIntArray(23)})},
            {CircleButton("5", onClick = {viewModel.sendIntArray(24)})},
            {CircleButton("6", onClick = {viewModel.sendIntArray(25)})},
            {CircleButton("7", onClick = {viewModel.sendIntArray(26)})},
            {CircleButton("8", onClick = {viewModel.sendIntArray(27)})},
            {CircleButton("9", onClick = {viewModel.sendIntArray(28)})},
            {CircleButton("0", onClick = {viewModel.sendIntArray(29)})}
        )
    )
}
@Composable
fun ArrowButtonSwitcher(buttons: List<@Composable () -> Unit>) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val offsetX = remember { Animatable(0f) }
    val viewModel: MainViewModel = viewModel()

    fun moveLeft() {

        if (currentIndex > 0) {
            coroutineScope.launch {
                offsetX.animateTo(
                    targetValue = 300f,
                    animationSpec = tween(durationMillis = 300)
                )
                currentIndex--
                println(currentIndex)
                viewModel.saveKeyValue("currentIndex", currentIndex.toString())
                offsetX.snapTo(-300f)
                offsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300)
                )
            }
        }
    }

    fun moveRight() {
        if (currentIndex < buttons.size - 1) {
            coroutineScope.launch {
                offsetX.animateTo(
                    targetValue = -300f,
                    animationSpec = tween(durationMillis = 300)
                )
                currentIndex++
                println(currentIndex)
                viewModel.saveKeyValue("currentIndex", currentIndex.toString())
                offsetX.snapTo(300f)
                offsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300)
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { moveLeft() }, modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Arrow left",
                modifier = Modifier.size(50.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .offset(x = offsetX.value.dp)
        ) {
            buttons[currentIndex]()
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = { moveRight() }, modifier = Modifier.padding(end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow left",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}
