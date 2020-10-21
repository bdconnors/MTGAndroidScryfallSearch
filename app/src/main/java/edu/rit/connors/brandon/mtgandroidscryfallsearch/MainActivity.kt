package edu.rit.connors.brandon.mtgandroidscryfallsearch


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.JsonObjectRequest
import edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments.CardDisplay
import edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments.Message
import edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments.ResultsList
import edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments.SearchBar
import edu.rit.connors.brandon.mtgandroidscryfallsearch.models.Card
import edu.rit.connors.brandon.mtgandroidscryfallsearch.util.CardFactory
import edu.rit.connors.brandon.mtgandroidscryfallsearch.util.HttpRequestQueue
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search_bar.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    var results : ArrayList<Card> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(supportFragmentManager.findFragmentById(R.id.search_container) == null){
            val fragment = SearchBar()
            fragment.setSubmitListener(submitSearchListener())
            setFragment(R.id.search_container,fragment)
        }

    }
    private var itemChangedListener: ResultsList.ItemChangedListener =
        object: ResultsList.ItemChangedListener{
            override fun onSelectedItemChanged(cardName: String) {
                val card = getCard(cardName)!!
                val fragment = CardDisplay()
                val args = Bundle()
                args.putSerializable(CardDisplay.ARG_CARD_ID,card)
                fragment.arguments = args
                fragment.show(supportFragmentManager,"card_display")
            }
        }
    private fun submitSearchListener():View.OnClickListener{
        return View.OnClickListener {
            val cardName = search_bar_value.text
            val url = "https://api.scryfall.com/cards/search?order=name&unique=cards&q=name%3A%21$cardName"
            val jsonRequest =  JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    this.results = this.processResponse(response)
                    this.displaySearchResults()
                },
                { error ->
                    this.displayMessage("No results found for '$cardName'")
                }
            )
            HttpRequestQueue.getInstance(this.applicationContext).addToRequestQueue(jsonRequest)
        }
    }
    private fun displayMessage(msg:String){
        val args = Bundle()
        args.putString(Message.ARG_MSG_ID,msg)
        val messageFragment = Message()
        messageFragment.arguments = args
        setFragment(R.id.results_container,messageFragment)
    }
    private fun displaySearchResults(){
        val listFragment = ResultsList()
        listFragment.setResultItems(this.results)
        listFragment.setResultItemChangedListener(itemChangedListener)
        setFragment(R.id.results_container,listFragment)
    }
    private fun getCard(name:String): Card?{
        return this.results.find{it.name == name}
    }
    private fun setFragment(locationId:Int, fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(locationId,fragment).commit()
    }
    private fun processResponse(response:JSONObject):ArrayList<Card>{
        val results = ArrayList<Card>()
        val responseType = response.getString("object")
        if(responseType == "list") {
            val factory = CardFactory()
            val data = response.get("data") as JSONArray
            for (i in 0 until data.length()) {
                val obj = data.getJSONObject(i)
                val card = factory.make(obj)
                results.add(card)
            }
        }
        return results
    }

}