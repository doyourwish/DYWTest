package com.example.navigationapplication

import android.content.Context

class MySharedPrefs (context: Context){
	private val preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
	private val urlKey = "url"
	private val cacheKey = "cache"
	private val zoomKey = "zoom"

	fun getUrl() : String?{
		return preferences.getString(urlKey,"https://techgrowup.net/")
	}

	fun setUrl(value : String){
		val editor = preferences.edit()
		editor.putString(urlKey,value)
		editor.apply()
	}

	fun getCache() :  Int{
		return preferences.getInt(cacheKey, CACHE_DEFAULT)
	}

	fun setCache(value: Int){
		val editor = preferences.edit()
		editor.putInt(cacheKey,value)
		editor.apply()
	}

	fun getZoom() :  Int{
		return preferences.getInt(zoomKey, ZOOM_DEFAULT)
	}

	fun setZoom(value: Int){
		val editor = preferences.edit()
		editor.putInt(zoomKey,value)
		editor.apply()
	}

	companion object{
		const val CACHE_DEFAULT= 1
		const val NO_CACHE = 2
		const val CACHE_ONLY = 3

		const val ZOOM_DEFAULT = 100
	}
}