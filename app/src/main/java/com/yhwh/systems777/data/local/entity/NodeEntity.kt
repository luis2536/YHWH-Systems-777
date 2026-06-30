package com.yhwh.systems777.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodes")
data class NodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nodeName: String,
    val nodeUrl: String,
    val status: String, // activo/inactivo
    val hashRate: Double,
    val lastUpdated: Long
)
