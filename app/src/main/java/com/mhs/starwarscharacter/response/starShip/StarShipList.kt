package com.mhs.starwarscharacter.response.starShip


import com.google.gson.annotations.SerializedName

data class StarShipList(
    @SerializedName("count")
    var count: Int,
    @SerializedName("next")
    var next: String,
    @SerializedName("previous")
    var previous: Any,
    @SerializedName("results")
    var results: List<Result>
) {
    data class Result(
        @SerializedName("cargo_capacity")
        var cargoCapacity: String,
        @SerializedName("consumables")
        var consumables: String,
        @SerializedName("cost_in_credits")
        var costInCredits: String,
        @SerializedName("created")
        var created: String,
        @SerializedName("crew")
        var crew: String,
        @SerializedName("edited")
        var edited: String,
        @SerializedName("films")
        var films: List<String>,
        @SerializedName("hyperdrive_rating")
        var hyperdriveRating: String,
        @SerializedName("length")
        var length: String,
        @SerializedName("MGLT")
        var mGLT: String,
        @SerializedName("manufacturer")
        var manufacturer: String,
        @SerializedName("max_atmosphering_speed")
        var maxAtmospheringSpeed: String,
        @SerializedName("model")
        var model: String,
        @SerializedName("name")
        var name: String,
        @SerializedName("passengers")
        var passengers: String,
        @SerializedName("pilots")
        var pilots: List<String>,
        @SerializedName("starship_class")
        var starshipClass: String,
        @SerializedName("url")
        var url: String
    )
}