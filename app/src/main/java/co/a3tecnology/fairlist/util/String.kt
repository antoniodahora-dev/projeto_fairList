package co.a3tecnology.fairlist.util

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

fun String.formatted() : String {
    val locale = Locale("pt", "BR")
    val sdf = SimpleDateFormat("yyyy-MM-dd", locale)
    val date =  sdf.parse(this)
    return try {
        SimpleDateFormat("dd/MM/yyyy", locale).format(date!!)
    } catch (e: Exception) {
        "Data não localizada"
    }
}

fun getError(message: String): String {
    val jsonObject = JSONObject(message)
    val keys: Iterator<String> = jsonObject.keys()
    val str = keys.next()
    return try {
        jsonObject.getJSONArray(str).get(0).toString()
    } catch (e: Exception) {
        ""
    }
}