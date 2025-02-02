package saha.subham.bookshelf.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import saha.subham.bookshelf.Database.BookDatabase

class NetworkAvailability {

    companion object {
        fun isInternetAvailable(context: Context): Boolean? {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_INTERNET
                    ) ?: false
                } else {
                    //(@Suppress( ...names : "Deprecation")
                    return this.activeNetworkInfo?.isConnected ?: false
                }
            }
        }
    }
}