package edu.rit.connors.brandon.mtgandroidscryfallsearch.util

import edu.rit.connors.brandon.mtgandroidscryfallsearch.models.Card
import org.json.JSONArray
import org.json.JSONObject

class CardFactory{

    fun make(json:JSONObject) : Card {

        val id : String = json.getString("id")
        val name : String = json.getString("name")
        val cmc : Double = json.getDouble("cmc")
        val manaCost : String = json.getString("mana_cost")
        val set : String = json.getString("set")
        val type : String = json.getString("type_line")
        val colors : ArrayList<String> = this.makeColors(json.getJSONArray("colors"))
        val rarity : String = json.getString("rarity")
        val text : String = json.getString("oracle_text")
        val images : HashMap<String,String> = this.makeImages(json.getJSONObject("image_uris"))

        return Card(id,name,cmc,manaCost,set,type,colors,rarity,text,images)
    }
    private fun makeColors(arr:JSONArray):ArrayList<String>{
        val colors = ArrayList<String>()
        for( i in 0 until arr.length()){
            colors.add(arr.getString(i))
        }
        return colors
    }
    private fun makeImages(json:JSONObject):HashMap<String,String>{
        val images = HashMap<String,String>()
        val keys = json.keys()
        for(key in keys){
            images[key] = json.getString(key)
        }
        return images
    }
}