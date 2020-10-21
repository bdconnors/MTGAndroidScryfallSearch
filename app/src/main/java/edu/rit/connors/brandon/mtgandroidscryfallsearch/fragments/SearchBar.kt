package edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_search_bar.*
import edu.rit.connors.brandon.mtgandroidscryfallsearch.R

class SearchBar: Fragment(){
    var submitSearchListener : View.OnClickListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search_bar, container, false)
    }
    fun setSubmitListener(listener : View.OnClickListener){
        this.submitSearchListener = listener
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_bar_btn.setOnClickListener(this.submitSearchListener)
    }
}