package edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.ListFragment
import com.android.volley.toolbox.NetworkImageView
import edu.rit.connors.brandon.mtgandroidscryfallsearch.R
import edu.rit.connors.brandon.mtgandroidscryfallsearch.models.Card
import edu.rit.connors.brandon.mtgandroidscryfallsearch.util.HttpRequestQueue

class ResultsList:ListFragment(){

    var itemChangedListener : ItemChangedListener? = null
    var results : ArrayList<Card>? = null

    /**define ItemChangeListener behavior*/
    interface ItemChangedListener{
        fun onSelectedItemChanged(cardName:String)
    }
    fun setResultItemChangedListener(listener: ItemChangedListener){
        itemChangedListener = listener
    }
    fun setResultItems(results:ArrayList<Card>){
        this.results = results
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = ItemsArrayAdapter(this.context!!, R.layout.result_list_item,results!!)
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        listView.setBackgroundColor(Color.WHITE)
        listView.onItemClickListener = itemsOnItemClickListener
    }
    inner class ItemsArrayAdapter(context: Context, resource: Int, list: ArrayList<Card>):
        ArrayAdapter<Card>(context,resource,list){
        var resource :Int
        var list: ArrayList<Card>
        var vi: LayoutInflater
        init{
            this.resource = resource
            this.list = list
            this.vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val holder : ViewHolder
            val retView : View
            if(convertView == null){
                retView = vi.inflate(resource,null)
                holder = ViewHolder()
                holder.itemImage = retView.findViewById<NetworkImageView>(R.id.result_item_image)
                holder.itemName = retView.findViewById<TextView>(R.id.result_item_name)
                retView.tag = holder

            }else{
                holder = convertView.tag as ViewHolder
                retView = convertView
            }
            val item = list[position]
            holder.itemName!!.text = item.name
            val imageLoader = HttpRequestQueue.getInstance(context).imageLoader
            holder.itemImage!!.setImageUrl(item.getImage("art_crop"),imageLoader)
            return retView
        }

    }
    internal class ViewHolder{
        var itemImage:NetworkImageView? = null
        var itemName:TextView? = null
    }
    private val itemsOnItemClickListener:AdapterView.OnItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
        val name = view.findViewById<TextView>(R.id.result_item_name).text.toString()
        itemChangedListener?.onSelectedItemChanged(name)
    }
}