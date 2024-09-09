package com.example.remotecontroller

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun TVController(
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
                Text(text = "TV", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            ButtonLook(70, onClick = {viewModel.sendIntArray(2)}) {
                Icon(
                    painter = painterResource(R.drawable.house),
                    contentDescription = "Icon",
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ButtonLook(70, onClick = {viewModel.sendIntArray(3)}) {
                Icon(
                    painter = painterResource(R.drawable.log_out),
                    contentDescription = "Icon"

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
                    Text(text = "APPS")
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
                        painter = painterResource(R.drawable.back),
                        contentDescription = "Icon",
                        modifier = Modifier.size(20.dp)

                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                ButtonLook(onClick = {viewModel.sendIntArray(12)}) {
                    Text(text = "EXIT")
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
                VerticalButton("CH", onPlusClick = {viewModel.sendIntArray(15)}, onMinusClick = {viewModel.sendIntArray(16)})
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
@Composable
fun VerticalButton(
    text: String,
    onPlusClick: () -> Unit = {},
    plusBtnTint: Color = MaterialTheme.colorScheme.onBackground,
    onMinusClick: () -> Unit = {},
    minusBtnTint: Color = MaterialTheme.colorScheme.onBackground
){
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(200.dp)
    ) {
        Button(
            onClick = { onPlusClick() },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(100.dp)
                .width(70.dp),
            shape = RoundedCornerShape(topStart = 100.dp, topEnd = 100.dp),
            colors = ButtonColors(
                MaterialTheme.colorScheme.secondaryContainer,
                plusBtnTint,
                Color.Unspecified,
                Color.Unspecified
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(20.dp)
                    .offset(y = (-8).dp)

            )
        }
        Button(
            onClick = { onMinusClick() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(100.dp)
                .width(70.dp),
            shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp),
            colors = ButtonColors(
                MaterialTheme.colorScheme.secondaryContainer,
                minusBtnTint,
                Color.Unspecified,
                Color.Unspecified
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.minus),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(20.dp)
                    .offset(y = 8.dp)

            )
        }
        Text(text = text,
            fontSize = 20.sp,
            modifier = Modifier
            .align(Alignment.Center))
    }
}
@Composable
fun ButtonLook(
    width: Int = 90,
    onClick: () -> Unit = {},
    buttonContent: @Composable () -> Unit = {}
){
    OutlinedButton(
        onClick = { onClick() },
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.secondaryContainer),
        modifier = Modifier
            .height(40.dp)
            .width(width.dp),
        colors = ButtonColors(
            Color.Unspecified,
            Color.White,
            Color.Unspecified,
            Color.Unspecified)
    ) {
        buttonContent()
    }
}

@Composable
fun MiddleButtons(
    leftOnClick: () -> Unit = {},
    leftBtnTint: Color = MaterialTheme.colorScheme.onBackground,
    rightOnClick: () -> Unit = {},
    rightBtnTint: Color = MaterialTheme.colorScheme.onBackground,
    topOnClick: () -> Unit = {},
    topBtnTint: Color = MaterialTheme.colorScheme.onBackground,
    bottomOnClick: () -> Unit = {},
    bottomBtnTint: Color = MaterialTheme.colorScheme.onBackground,
    middleOnClick: () -> Unit = {},
    middleBtnTint: Color = MaterialTheme.colorScheme.onBackground
    ){
    Box(modifier = Modifier
        .size(250.dp)
        .clip(CircleShape)) {

        //Left Button
        Button(
            onClick = { leftOnClick() },
            modifier = Modifier
                .size(width = 120.dp, height = 180.dp)
                .align(Alignment.CenterStart),
            shape = CutCornerShape(bottomEnd = 85.dp, topEnd = 85.dp),
            colors = ButtonColors(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.onBackground,
                Color.Unspecified,
                Color.Unspecified)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = (-20).dp),
                tint = leftBtnTint
            )
        }
        //Right Button
        Button(
            onClick = { rightOnClick()},
                modifier = Modifier
                    .size(width = 120.dp, height = 180.dp)
                    .align(Alignment.CenterEnd),
            shape = CutCornerShape(bottomStart = 85.dp, topStart = 85.dp),
            colors = ButtonColors(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.onBackground,
                Color.Unspecified,
                Color.Unspecified)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .offset(x = 20.dp),
                tint = rightBtnTint
            )
        }
        //Top Button
        Button(
            onClick = { topOnClick() },
            modifier = Modifier
                .size(width = 180.dp, height = 120.dp)
                .align(Alignment.TopCenter),
            shape = CutCornerShape(bottomStart = 85.dp, bottomEnd = 85.dp),
            colors = ButtonColors(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.onBackground,
                Color.Unspecified,
                Color.Unspecified)
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .offset(y = (-20).dp),
                tint = topBtnTint
            )
        }
        //Bottom Button
        Button(
            onClick = { bottomOnClick() },
            modifier = Modifier
                .size(width = 180.dp, height = 120.dp)
                .align(Alignment.BottomCenter),
            shape = CutCornerShape(topStart = 85.dp, topEnd = 85.dp),
            colors = ButtonColors(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.onBackground,
                Color.Unspecified,
                Color.Unspecified)
        ) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .offset(y = 20.dp),
                tint = bottomBtnTint
            )
        }
        Button(
            onClick = { middleOnClick() },
            shape = RoundedCornerShape(100),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                middleBtnTint,
                Color.Red,
                Color.Gray
            )
        ) {
            Text(text = "OK",
                fontSize = 16.sp
            )
        }
    }
}