package com.example.remotecontroller

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.hardware.ConsumerIrManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(application: Application): AndroidViewModel(application)  {

    private lateinit var irManager: ConsumerIrManager
    private val sharedPrefs = SharedPreferencesManager(application)

    fun getListOfItems(): List<RemoteItem> {
        return sharedPrefs.getDataList()
    }
    fun saveListOfItems(dataList: List<RemoteItem>){
        sharedPrefs.saveDataList(dataList)
    }
    fun getItemIntArrayList(): List<ItemIntArrays> {
        return sharedPrefs.getItemArray()
    }
    fun saveItemIntArrayList(dataList: List<ItemIntArrays>){
        sharedPrefs.saveItemArray(dataList)
    }
    fun saveKeyValue(key: String, value: String){
        sharedPrefs.saveKeyValue(key, value)
    }
    fun getKeyValue(key: String, value: String): String{
        return sharedPrefs.getKeyValue(key, value)
    }
    fun sendIRSignal(pattern: IntArray){
        val context = getApplication<Application>().applicationContext
        irManager = context.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager
        if (irManager.hasIrEmitter()) {
            irManager.transmit(38000, pattern) // 38kHz frequency
            Toast.makeText(context, "IR signal sent!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "No IR emitter found!", Toast.LENGTH_SHORT).show()
        }
    }


    val bluetoothAdapter: BluetoothAdapter? = BluetoothHelper.getBluetoothAdapter(application)
    val isBluetoothSupported = mutableStateOf(bluetoothAdapter != null)
    val isBluetoothEnabled = mutableStateOf(bluetoothAdapter?.isEnabled ?: false)
    val toastMessage = mutableStateOf<String?>(null)
    val receivedData = MutableLiveData<String>()


    fun handleEnableBluetoothResult(resultCode: Int) {
        isBluetoothEnabled.value = resultCode == RESULT_OK
        toastMessage.value = if (resultCode == RESULT_OK) "Bluetooth enabled" else "Bluetooth enabling failed"
    }

    fun handlePermissionResult(permissions: Map<String, Boolean>) {
        toastMessage.value = if (permissions[Manifest.permission.BLUETOOTH_CONNECT] == true && permissions[Manifest.permission.BLUETOOTH_SCAN] == true) {
            "Bluetooth permissions granted"
        } else {
            "Bluetooth permissions denied"
        }
    }

    fun enableBluetooth(launcher: ActivityResultLauncher<Intent>) {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        launcher.launch(enableBtIntent)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun requestPermissions(launcher: ActivityResultLauncher<Array<String>>) {
        launcher.launch(arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        ))
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(deviceName: String, context: Context) {
        val device = bluetoothAdapter?.bondedDevices?.find { it.name == deviceName }
        if (device != null) {
            viewModelScope.launch(Dispatchers.IO) {
                BluetoothHelper.connectToDevice(device, context) { data ->
                    receivedData.postValue(data)

                    // Retrieve the ID and index values
                    val idString = getKeyValue("item", "")
                    val currentIndexString = getKeyValue("currentIndex", "0")

                    // Convert ID and index to integers
                    val id = idString.toIntOrNull()
                    val currentIndex = currentIndexString.toIntOrNull() // Handle invalid index
                    println(id)

                    // Get the mutable list of items
                    val itemList = getItemIntArrayList().toMutableList()
                    println(itemList)

                    // Find the item by ID
                    val item = itemList.find { it.id == id }
                    println("something")
                    println(item)

                    if (item != null) {
                        // Update the item at the specified index
                        if (currentIndex in item.intArrayList.indices) {
                            item.intArrayList[currentIndex!!] = stringToIntArray(data)
                        } else {
                            println("Current index $currentIndex is out of bounds.")
                        }
                    } else {
                        // Create a new item and add it to the list
                        val newItem = ItemIntArrays(
                            id = id!!.toInt()
                        )
                        newItem.intArrayList[currentIndex!!.toInt()] = stringToIntArray(data)
                        itemList += newItem
                    }

                    // Save the updated list
                    saveItemIntArrayList(itemList)
                    println("final Item list")
                    println(itemList)

                    saveKeyValue("array", data)
                    println(data)
                }
            }
        } else {
            showToast("Device not paired")
        }
    }


    fun showToast(message: String) {
        toastMessage.value = message
    }

    fun stringToIntArray(savedString: String): IntArray {
        val cleanedString = savedString.trim()
        return cleanedString.split(",\\s*".toRegex())
            .mapNotNull { it.toIntOrNull() }
            .toIntArray()
    }
    fun sendIntArray(index: Int){

        val itemId = getKeyValue("item", "")

        val itemList = getItemIntArrayList()
        val item = itemList.find { it.id == itemId.toInt() }
        println(item)
        println(itemList)

        if (item != null){
            val signal = item.intArrayList[index]
            if (signal != null){
                sendIRSignal(signal)
            }
        }
    }
    private val _selectedComponent = MutableStateFlow("None")
    val selectedComponent: StateFlow<String> = _selectedComponent

    fun selectComponent(component: String) {
        saveKeyValue("controller", component)
        _selectedComponent.value = getKeyValue("controller", "")

    }
}
