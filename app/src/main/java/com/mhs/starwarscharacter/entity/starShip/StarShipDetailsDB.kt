package com.mhs.starwarscharacter.entity.starShip

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "starShip_details", indices = [Index(value = ["url"], unique = true)])
data class StarShipDetailsDB(
    var name: String,
    var model: String,
    var manufacturer: String,
    var cost_in_credits: String,
    var length: String,
    var max_atmosphering_speed: String,
    var crew: String,
    var passengers: String,
    var cargo_capacity: String,
    var consumables: String,
    var hyperdrive_rating: String,
    var MGLT: String,
    var starship_class: String,
    @PrimaryKey
    var url: String
){
    // Default constructor required by Room
    constructor() : this("", "", "", "", "", "", "", "", "","", "", "", "", "")
}