package com.example.remotecontroller

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ReturnFromAwaitPointerEventScope", "SourceLockedOrientationActivity")
@Composable
fun DialController(
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

    Column(modifier = Modifier.fillMaxSize()) {

        IconButton(
            onClick = {
                navController.navigate("listscreen")
                scope.launch { sheetState.bottomSheetState.partialExpand() }},
            modifier = Modifier
                .padding(16.dp)
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier.size(30.dp))
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(70.dp))

                Column(
                    modifier = Modifier.height(540.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RowOfButtons(text1 = "1", onClick1 = {viewModel.sendIntArray(20)}, text2 = "2", onClick2 = {viewModel.sendIntArray(21)}, text3 = "3",  onClick3 = {viewModel.sendIntArray(22)})
                    RowOfButtons(text1 = "4", onClick1 = {viewModel.sendIntArray(23)}, text2 = "5", onClick2 = {viewModel.sendIntArray(24)}, text3 = "6", onClick3 = {viewModel.sendIntArray(25)})
                    RowOfButtons(text1 = "7", onClick1 = {viewModel.sendIntArray(26)}, text2 = "8", onClick2 = {viewModel.sendIntArray(27)}, text3 = "9", onClick3 = {viewModel.sendIntArray(28)})
                    CircleButton("0", onClick = {viewModel.sendIntArray(29)})
                }

                Spacer(modifier = Modifier.height(70.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp)
                        .offset(y = (-8).dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.navigate("tvcontroller") }) {
                        Icon(
                            painter = painterResource(R.drawable.controller),
                            contentDescription = "Icon",
                            modifier = Modifier.size(20.dp)

                        )
                    }
                    IconButton(onClick = { /*TODO*/ }, enabled = false) {
                        Icon(
                            painter = painterResource(R.drawable.dial_pad),
                            contentDescription = "Icon",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Cyan

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
    }
}
@Composable
fun RowOfButtons(text1: String, onClick1: () -> Unit, text2: String, onClick2: () -> Unit, text3: String, onClick3: () -> Unit,){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        CircleButton(text1, onClick = {onClick1()})
        CircleButton(text2, onClick = {onClick2()})
        CircleButton(text3, onClick = {onClick3()})
    }
}
@Composable
fun CircleButton(
    text: String = "",
    colorContainer: Color = MaterialTheme.colorScheme.secondaryContainer,
    onClick: () -> Unit = {},
    buttonContent: @Composable () -> Unit = { Text(text = text, fontSize = 24.sp)}
){
    Button(onClick = { onClick() },
        shape = CircleShape,
        modifier = Modifier.size(70.dp),
        colors = ButtonColors(
            colorContainer,
            MaterialTheme.colorScheme.onBackground,
            Color.Unspecified,
            Color.Unspecified
        )) {
        buttonContent()

    }
}
