package com.example.remotecontroller

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class RemoteItem(
    var id: Int,
    var name: String,
    var controller: String,
    var firsttime: Boolean,
    var icon: Int
)
data class ItemIntArrays(
    val id: Int,
    val intArrayList: MutableList<IntArray?> = MutableList(30) { null }
)

//class - something like blueprint
class SharedPreferencesManager(val context: Context) {
    //private for being used just in this class cant get it from it outside of this class
    private val prefsname = "MyPrefsFile"
    private val keylistdata = "dataList"
    private val IntArraydata = "IntArraydata"

    private val gson = Gson()

    fun saveDataList(dataList: List<RemoteItem>) {
        val prefs: SharedPreferences = context.getSharedPreferences(prefsname, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = gson.toJson(dataList)
        editor.putString(keylistdata, json)
        editor.apply()
    }

    fun getDataList(): List<RemoteItem> {
        val prefs: SharedPreferences = context.getSharedPreferences(prefsname, Context.MODE_PRIVATE)
        val json = prefs.getString(keylistdata, null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<RemoteItem>>() {}.type)
        } else {
            emptyList()
        }
    }
    fun saveItemArray(dataList: List<ItemIntArrays>) {
        val prefs: SharedPreferences = context.getSharedPreferences(prefsname, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = gson.toJson(dataList)
        editor.putString(IntArraydata, json)
        editor.apply()
    }

    fun getItemArray(): List<ItemIntArrays> {
        val prefs: SharedPreferences = context.getSharedPreferences(prefsname, Context.MODE_PRIVATE)
        val json = prefs.getString(IntArraydata, null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<ItemIntArrays>>() {}.type)
        } else {
            emptyList()
        }
    }
    fun saveKeyValue(key: String, value: String) {
        val prefs: SharedPreferences = context.getSharedPreferences(prefsname, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getKeyValue(key: String, value: String): String {
        val prefs: SharedPreferences = context.getSharedPreferences(prefsname, Context.MODE_PRIVATE)
        return prefs.getString(key, value)!!
    }
}