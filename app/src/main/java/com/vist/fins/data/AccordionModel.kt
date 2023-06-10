package com.vist.fins.data

data class AccordionModel(
    val header: String,
    val rows: List<Row>
) {
    data class Row(
        val categoryName: String,
        val categoryIcon: String,
        val monthToDisplay: String,
    )
}
