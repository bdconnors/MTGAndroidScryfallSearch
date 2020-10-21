package edu.rit.connors.brandon.mtgandroidscryfallsearch.models

import java.io.Serializable

class Card(id:String,
           name:String,
           cmc:Double,
           manaCost:String,
           set:String,
           type:String,
           colors:ArrayList<String>,
           rarity:String,
           text:String,
           imageUris:HashMap<String,String>):Serializable{
    var id : String = id
    var name : String = name
    var cmc : Double = cmc
    var manaCost : String = manaCost
    var set : String = set
    var type : String = type
    var colors : ArrayList<String> = colors
    var rarity : String = rarity
    var text : String = text
    var imageUris : HashMap<String,String> = imageUris

    fun hasColor(color:String):Boolean{
        val hasColor = this.colors.find{it === color}
        return hasColor != null
    }
    fun getImage(type:String):String{
        return this.imageUris.get(type)!!
    }

}