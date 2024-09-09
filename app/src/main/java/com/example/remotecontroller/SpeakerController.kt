package com.example.remotecontroller

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("SourceLockedOrientationActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeakerController(
    navController: NavHostController,
    scope: CoroutineScope,
    sheetState: BottomSheetScaffoldState
) {

    val viewModel: MainViewModel = viewModel()

    val context = LocalContext.current

    // Ensure the screen orientation is set only once when the composable is first launched
    LaunchedEffect(Unit) {
        // Cast the context to an Activity
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    navController.navigate("listscreen")
                    scope.launch { sheetState.bottomSheetState.partialExpand() }},
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            ){
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    modifier = Modifier.size(30.dp))
            }

            Button(
                onClick = {viewModel.sendIntArray(0)
                    viewModel.selectComponent("tvcontroller")},
                colors = ButtonColors(Color.Red, Color.White, Color.Unspecified, Color.Unspecified),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
                    .height(40.dp)
                    .width(80.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_power_settings_new_24),
                    contentDescription = "Icon",
                )
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 60.dp)
        ) {
            ButtonLook(70, onClick = {viewModel.sendIntArray(1)}) {
                Icon(
                    painter = painterResource(R.drawable.previous),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ButtonLook(70, onClick = {viewModel.sendIntArray(2)}) {
                Icon(
                    painter = painterResource(R.drawable.pause_play),
                    contentDescription = "Icon",
                    modifier = Modifier.size(17.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ButtonLook(70, onClick = {viewModel.sendIntArray(3)}) {
                Icon(
                    painter = painterResource(R.drawable.next_button),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)

                )
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.TopCenter)
            ) {
                ButtonLook(onClick = {viewModel.sendIntArray(4)}) {
                    Text(text = "INPUT")
                }
                Spacer(modifier = Modifier.weight(1f))
                ButtonLook(onClick = {viewModel.sendIntArray(5)}) {
                    Text(text = "AUX")
                }
            }
            Row(modifier = Modifier.align(Alignment.Center)) {
                MiddleButtons(leftOnClick = {viewModel.sendIntArray(6)}, rightOnClick = {viewModel.sendIntArray(7)}, topOnClick = {viewModel.sendIntArray(8)}, bottomOnClick = {viewModel.sendIntArray(9)}, middleOnClick = {viewModel.sendIntArray(10)})
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.BottomCenter)
            ) {
                ButtonLook(onClick = {viewModel.sendIntArray(11)}) {
                    Icon(
                        painter = painterResource(R.drawable.bluetooth),
                        contentDescription = "Icon",
                        modifier = Modifier.size(25.dp)

                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                ButtonLook(onClick = {viewModel.sendIntArray(12)}) {
                    Text(text = "RADIO")
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .height(200.dp)) {
            Box(modifier = Modifier.align(Alignment.CenterStart)) {
                VerticalButton("VOL", onPlusClick = {viewModel.sendIntArray(13)}, onMinusClick = {viewModel.sendIntArray(14)})
            }
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                VerticalButton("BAL", onPlusClick = {viewModel.sendIntArray(15)}, onMinusClick = {viewModel.sendIntArray(16)})
            }
            Column(modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ){
                ButtonLook(onClick = {viewModel.sendIntArray(17)}){
                    Text(text = "MENU")
                }
                ButtonLook(onClick = {viewModel.sendIntArray(18)}){
                    Icon(
                        painter = painterResource(R.drawable.volume_mute),
                        contentDescription = "Icon",
                        modifier = Modifier.size(20.dp)

                    )
                }
                ButtonLook(onClick = {viewModel.sendIntArray(19)}){
                    Text(text = "SLEEP")
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
            .offset(y = (-8).dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {}, enabled = false) {
                Icon(
                    painter = painterResource(R.drawable.controller),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Cyan

                )
            }
            IconButton(onClick = { navController.navigate("dialcontroller") }) {
                Icon(
                    painter = painterResource(R.drawable.dial_pad),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)

                )
            }
            IconButton(onClick = { scope.launch { sheetState.bottomSheetState.expand() } }) {
                Icon(
                    painter = painterResource(R.drawable.settings),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)

                )
            }
        }

    }
}