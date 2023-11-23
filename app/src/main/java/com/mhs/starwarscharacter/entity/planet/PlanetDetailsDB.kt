package com.mhs.starwarscharacter.entity.planet

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "planet_details", indices = [Index(value = ["url"], unique = true)])
data class PlanetDetailsDB(
    var name: String,
    var rotation_period: String,
    var orbital_period: String,
    var diameter: String,
    var climate: String,
    var gravity: String,
    var terrain: String,
    var surface_water: String,
    var population: String,
    @PrimaryKey
    var url: String
){
    // Default constructor required by Room
    constructor() : this("", "", "", "", "", "", "", "", "","")
}