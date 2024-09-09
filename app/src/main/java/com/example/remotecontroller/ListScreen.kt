package com.example.remotecontroller

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController


@Composable
fun ListScreen(navController: NavHostController) {

    val viewModel: MainViewModel = viewModel()

    var remoteItemList by remember { mutableStateOf(viewModel.getListOfItems()) }

    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }


    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 40.dp)
    ) {

        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {
                AddControllerButton {
                    showDialog = true
                }
            }
             Spacer(modifier = Modifier.height(20.dp))
        }
        items(remoteItemList) {
            item ->

            var showlayoutdialog by remember { mutableStateOf(false) }

            val firstTime by remember { mutableStateOf(item.firsttime) }
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier
                    .padding(16.dp)
                    .width(300.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(20)
                    )
                    .clickable {
                        if (firstTime) {
                            showlayoutdialog = true
                        } else{
                            println(item.id)
                            println(item.controller)
                            viewModel.selectComponent(item.controller)
                            println(viewModel.selectedComponent.value)
                            viewModel.saveKeyValue("item", (item.id).toString())
                            navController.navigate(item.controller)
                        }
                    }
                ) {
                    Text(text = item.name, modifier = Modifier.padding(16.dp))
                    Icon(painter = painterResource(id = item.icon),
                        contentDescription = "Icon",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Icon(imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Button",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            var itemList = viewModel.getItemIntArrayList()
                            val itemToDelete  = itemList.find { it.id == item.id }
                            println("item")
                            println(itemList)
                            println(itemToDelete)
                            if (itemToDelete != null){
                                itemList = itemList - itemToDelete
                            }
                            remoteItemList = remoteItemList - item
                            viewModel.saveItemIntArrayList(itemList)
                            viewModel.saveListOfItems(remoteItemList)
                            println(remoteItemList)
                            println(item)
                            println(item.name)
                        }
                )
            }
            if (showlayoutdialog){
                AlertDialog(onDismissRequest = { showlayoutdialog = false },
                    confirmButton = {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween) {

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                OutlinedIconButton(onClick = {
                                    viewModel.saveKeyValue("item", (item.id).toString())
                                    showlayoutdialog = false
                                    remoteItemList = remoteItemList.map {
                                        if (it.id == item.id){
                                            it.copy(icon = R.drawable.baseline_tv_24, firsttime = false, controller = "tvcontroller")
                                        } else{
                                            it
                                        }
                                    }
                                    viewModel.saveListOfItems(remoteItemList)
                                    navController.navigate("tvcontroller")
                                }, modifier = Modifier.size(50.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_tv_24),
                                        contentDescription = "tv button"
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "TV")
                            }
                            Column {
                                OutlinedIconButton(onClick = {
                                    viewModel.saveKeyValue("item", (item.id).toString())
                                    showlayoutdialog = false
                                    remoteItemList = remoteItemList.map {
                                        if (it.id == item.id){
                                            it.copy(icon = R.drawable.baseline_speaker_24, firsttime = false, controller = "speakercontroller")
                                        } else{
                                            it
                                        }
                                    }
                                    viewModel.saveListOfItems(remoteItemList)
                                    navController.navigate("speakercontroller")
                                }, modifier = Modifier.size(50.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_speaker_24),
                                        contentDescription = "speaker button"
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "Receiver")
                            }
                            Column {
                                OutlinedIconButton(onClick = {
                                    viewModel.saveKeyValue("item", (item.id).toString())
                                    showlayoutdialog = false
                                    remoteItemList = remoteItemList.map {
                                        if (it.id == item.id){
                                            it.copy(icon = R.drawable.baseline_settings_remote_24, firsttime = false, controller = "rgbcontroller")
                                        } else{
                                            it
                                        }
                                    }
                                    viewModel.saveListOfItems(remoteItemList)
                                    navController.navigate("rgbcontroller")
                                }, modifier = Modifier.size(50.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_settings_remote_24),
                                        contentDescription = "remote rgb controller button"
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = "RGB LED")
                            }
                        }
                    },
                    title = { Text(text = "Choose Controller") }
                )
            }
        }
    }
    if (showDialog){
        AlertDialog(onDismissRequest = { showDialog = false },
            confirmButton = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = { showDialog = false }) {
                        Text(text = "Cancel")
                    }
                    Button(onClick = {
                        if (name.isNotBlank()){
                            val newItem = RemoteItem(
                                id = remoteItemList.size + 1,
                                name = name,
                                controller = "is not selected",
                                firsttime = true,
                                icon = R.drawable.baseline_question_mark_24
                            )
                            remoteItemList = remoteItemList + newItem
                            showDialog = false
                            name = ""
                            viewModel.saveListOfItems(remoteItemList)

                        }
                    }) {
                        Text(text = "Add")
                    }
                }
            },
            title = { Text(text = "Add Remote Controller") },
            text = {
                Column {
                    OutlinedTextField(value = name,
                        onValueChange = { name = it },
                        singleLine = true,
                        label = { Text(text = "Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),

                        )
                }
            }
        )
    }
}


@Composable
fun AddControllerButton(onClick: () -> Unit){
    OutlinedButton(
        onClick = {onClick()},
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .width(300.dp)
            .height(60.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add button",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = (-10).dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Add Remote Controller",
                fontSize = 20.sp,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}