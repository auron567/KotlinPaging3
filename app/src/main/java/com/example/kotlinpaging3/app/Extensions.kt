package com.example.kotlinpaging3.app

import android.content.Context
import android.widget.Toast

// Extension functions used throughout the app.

/**
 * Display the Toast [message], with [Toast.LENGTH_SHORT] duration.
 */
fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Display the Toast [message], with [Toast.LENGTH_LONG] duration.
 */
fun Context.longToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
