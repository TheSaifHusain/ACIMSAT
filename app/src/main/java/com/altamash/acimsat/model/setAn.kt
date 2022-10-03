package com.altamash.acimsat.model

object setAn {
    private val listData = mutableListOf<AnData>()
    fun setData(Date: String, Title: String, Detail: String, Link: String) {
        listData.add(AnData(Date, Title, Detail, Link))
    }

    fun getAll(): List<AnData> {
        return listData
    }
}