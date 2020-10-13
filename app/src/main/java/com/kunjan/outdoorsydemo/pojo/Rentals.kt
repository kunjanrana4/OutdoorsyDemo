package com.kunjan.outdoorsydemo.pojo

import com.google.gson.annotations.SerializedName
data class Rentals (

	@SerializedName("data") val data : List<Data>
)