package co.a3tecnology.fairlist.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkCheck (private val connectivityManager: ConnectivityManager){

    fun performActionIfConnected(action: () -> Unit) {
        if (hasInternet())
            action()
    }

    private fun hasInternet() : Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager?.activeNetwork ?: return false

            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        } else {
            val activiteNetworkInfo = connectivityManager?.activeNetworkInfo
               if (activiteNetworkInfo != null) {
                   return activiteNetworkInfo.type == ConnectivityManager.TYPE_WIFI
                           || activiteNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
            }
            false
        }
    }
}