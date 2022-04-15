package com.example.rickandmortydemo.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kotlin.properties.Delegates


fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun isInternetAvailable(context: Context): Boolean {
    var result by Delegates.notNull<Boolean>()
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->
                true
            else -> false
        }
    return result
}
