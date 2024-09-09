package com.example.remotecontroller

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun RGBController(
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
        verticalArrangement = Arrangement.Top
    ) {
        IconButton(
            onClick = {
                navController.navigate("listscreen")
                scope.launch { sheetState.bottomSheetState.partialExpand() }},
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start)
        ){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "",
                modifier = Modifier.size(30.dp))
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(650.dp),
            verticalArrangement = Arrangement.SpaceEvenly) {
            RowOfButtons(
                buttonContent1 = {
                    Icon(
                        painter = painterResource(R.drawable.brightness_decrease),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )},
                buttonContent2 = {
                    Icon(
                        painter = painterResource(R.drawable.brightness_increase),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )},
                buttonContent3 = {
                    Icon(
                        painter = painterResource(R.drawable.zero),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )},
                buttonContent4 = {
                    Icon(
                        painter = painterResource(R.drawable.i),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )},
                colorContainer3 = Color(200, 0, 0),
                colorContainer4 = Color(0, 128, 0),
                onClick1 = {viewModel.sendIntArray(0)},
                onClick2 = {viewModel.sendIntArray(1)},
                onClick3 = {viewModel.sendIntArray(2)},
                onClick4 = {viewModel.sendIntArray(3)}
            )
            RowOfButtons(
                buttonContent1 = {Text(text = "R", fontSize = 24.sp)},
                buttonContent2 = {Text(text = "G", fontSize = 24.sp)},
                buttonContent3 = {Text(text = "B", fontSize = 24.sp)},
                buttonContent4 = {Text(text = "W", fontSize = 24.sp)},
                colorContainer1 = Color(200, 0, 0),
                colorContainer2 = Color(0, 128, 0),
                colorContainer3 = Color(0, 0, 180),
                onClick1 = {viewModel.sendIntArray(4)},
                onClick2 = {viewModel.sendIntArray(5)},
                onClick3 = {viewModel.sendIntArray(6)},
                onClick4 = {viewModel.sendIntArray(7)}
            )
            RowOfButtons(
                colorContainer1 = Color(255, 69, 0),
                colorContainer2 = Color(46, 128, 128),
                colorContainer3 = Color(65, 105, 225),
                buttonContent4 = {
                    Icon(
                        painter = painterResource(R.drawable.color_circle),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified
                    )},
                onClick1 = {viewModel.sendIntArray(8)},
                onClick2 = {viewModel.sendIntArray(9)},
                onClick3 = {viewModel.sendIntArray(10)},
                onClick4 = {viewModel.sendIntArray(11)}
            )
            RowOfButtons(
                colorContainer1 = Color(255, 165, 0),
                colorContainer2 = Color(70, 130, 180),
                colorContainer3 = Color(106, 90, 205),
                buttonContent4 = {
                    Icon(
                        painter = painterResource(R.drawable.color_palette),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified
                    )},
                onClick1 = {viewModel.sendIntArray(12)},
                onClick2 = {viewModel.sendIntArray(13)},
                onClick3 = {viewModel.sendIntArray(14)},
                onClick4 = {viewModel.sendIntArray(15)}
            )
            RowOfButtons(
                colorContainer1 = Color(255, 215, 0),
                colorContainer2 = Color(65, 105, 225),
                colorContainer3 = Color(199, 21, 133),
                buttonContent4 = {
                    Icon(
                        painter = painterResource(R.drawable.palette),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified
                    )},
                onClick1 = {viewModel.sendIntArray(16)},
                onClick2 = {viewModel.sendIntArray(17)},
                onClick3 = {viewModel.sendIntArray(18)},
                onClick4 = {viewModel.sendIntArray(19)}
            )
            RowOfButtons(
                colorContainer1 = Color(255, 255, 0),
                colorContainer2 = Color(0, 0, 139),
                colorContainer3 = Color(255, 20, 147),
                buttonContent4 = {
                    Icon(
                        painter = painterResource(R.drawable.colors),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified
                    )},
                onClick1 = {viewModel.sendIntArray(20)},
                onClick2 = {viewModel.sendIntArray(21)},
                onClick3 = {viewModel.sendIntArray(22)},
                onClick4 = {viewModel.sendIntArray(23)}
            )
        }
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { scope.launch { sheetState.bottomSheetState.expand() } }, modifier = Modifier.padding(top = 24.dp).align(
                Alignment.Center)) {
                Icon(
                    painter = painterResource(R.drawable.settings),
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(20.dp)

                )
            }
        }

    }

}
@Composable
fun RowOfButtons(
    buttonContent1: @Composable () -> Unit = { },
    buttonContent2: @Composable () -> Unit = { },
    buttonContent3: @Composable () -> Unit = { },
    buttonContent4: @Composable () -> Unit = { },
    colorContainer1: Color = MaterialTheme.colorScheme.secondaryContainer,
    colorContainer2: Color = MaterialTheme.colorScheme.secondaryContainer,
    colorContainer3: Color = MaterialTheme.colorScheme.secondaryContainer,
    colorContainer4: Color = MaterialTheme.colorScheme.secondaryContainer,
    onClick1: () -> Unit = {},
    onClick2: () -> Unit = {},
    onClick3: () -> Unit = {},
    onClick4: () -> Unit = {}
){
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly){
        CircleButton(colorContainer = colorContainer1, onClick = {onClick1()}){
            buttonContent1()
        }
        CircleButton(colorContainer = colorContainer2, onClick = {onClick2()}) {
            buttonContent2()
        }
        CircleButton(colorContainer = colorContainer3, onClick = {onClick3()}) {
            buttonContent3()
        }
        CircleButton(colorContainer = colorContainer4, onClick = {onClick4()}) {
            buttonContent4()
        }
    }
}
