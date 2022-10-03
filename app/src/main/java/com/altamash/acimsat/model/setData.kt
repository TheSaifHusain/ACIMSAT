package com.altamash.acimsat.model

object setData {
    var listData = mutableListOf<ResultDetail>()
    fun setDetail(
        Name: String = "", Phone: String = "",
        City: String = "",
        Email: String = "",
        ObResult: String = "",
        Question1: String = "",
        Question2: String = "",
        Question3: String = "",
        Question4: String = "",
        Question5: String = ""
    ) {
        listData.add(
            ResultDetail(
                Name,
                Phone,
                City,
                Email,
                ObResult,
                Question1,
                Question2,
                Question3,
                Question4,
                Question5
            )
        )
    }

    fun getAll(): List<ResultDetail> {
        return listData
    }

    fun deleteAll() {
        listData.clear()
    }
}