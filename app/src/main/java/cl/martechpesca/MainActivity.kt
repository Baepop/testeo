package cl.martechpesca

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.webkit.*
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

class MainActivity : Activity() {
    private lateinit var web: WebView
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.databaseEnabled = true
        web.settings.setGeolocationEnabled(true)
        web.webViewClient = WebViewClient()
        web.addJavascriptInterface(AndroidBridge(), "Android")
        setContentView(web)
        web.loadUrl("file:///android_asset/index.html")
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        } else startGps()
    }

    private fun startGps() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 2000L, 2f,
                object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        val speed = if (location.hasSpeed()) location.speed * 1.943844f else 0f
                        val bearing = if (location.hasBearing()) location.bearing else 0f
                        val js = "window.setGps(${location.latitude},${location.longitude},$speed,$bearing);"
                        web.post { web.evaluateJavascript(js, null) }
                    }
                })
        } catch (_: SecurityException) {}
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, results: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (requestCode == 100 && results.isNotEmpty() &&
            results[0] == PackageManager.PERMISSION_GRANTED) startGps()
    }

    inner class AndroidBridge {
        @JavascriptInterface
        fun saveWaypoint(lat: Double, lon: Double, name: String) {
            getSharedPreferences("points", MODE_PRIVATE).edit()
                .putString(System.currentTimeMillis().toString(), "$name|$lat|$lon").apply()
        }
        @JavascriptInterface
        fun toast(message: String) {
            runOnUiThread { android.widget.Toast.makeText(this@MainActivity, message,
                android.widget.Toast.LENGTH_SHORT).show() }
        }
    }
