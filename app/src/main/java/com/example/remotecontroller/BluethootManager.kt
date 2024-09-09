package com.example.remotecontroller

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


object BluetoothHelper {
    const val REQUEST_ENABLE_BT = 1
    val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun getBluetoothAdapter(context: Context): BluetoothAdapter? {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter
    }

    fun showToast(text: String, context: Context) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    fun connectToDevice(device: BluetoothDevice, context: Context, onReceiveData: (String) -> Unit) {
        ConnectThread(device, context, onReceiveData).start()
    }

    @SuppressLint("MissingPermission")
    private class ConnectThread(
        device: BluetoothDevice,
        private val context: Context,
        private val onReceiveData: (String) -> Unit
    ) : Thread() {
        private val socket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(MY_UUID)
        }


        override fun run() {
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()

            var listening = true

            socket?.use { it ->
                try {

                    it.connect()
                    val inputStream: InputStream = it.inputStream
                    val outputStream: OutputStream = it.outputStream

                    outputStream.write("Hello ESP32".toByteArray())

                    val stringBuilder = StringBuilder()
                    val buffer = ByteArray(1024)
                    var bytes: Int
                    showToast("Start Listening", context)
                    listening = false

                    while (true) {
                        bytes = inputStream.read(buffer)
                        val readMessage = String(buffer, 0, bytes)
                        stringBuilder.append(readMessage)

                        // Process each line separately
                        var endOfLineIndex: Int
                        while (stringBuilder.indexOf("\n").also { endOfLineIndex = it } != -1) {
                            val completeMessage = stringBuilder.substring(0, endOfLineIndex).trim()
                            stringBuilder.delete(0, endOfLineIndex + 1)

                            // Handle the complete message
                            Handler(Looper.getMainLooper()).post {
                                println(completeMessage)
                                if ("9360" in completeMessage){
                                    showToast("Seems good!", context)
                                }
                                onReceiveData(completeMessage)
                            }
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Handler(Looper.getMainLooper()).post {
                        if (listening){
                            showToast("Stopped Listening", context)
                        }
                    }
                }
            }
        }
    }
}