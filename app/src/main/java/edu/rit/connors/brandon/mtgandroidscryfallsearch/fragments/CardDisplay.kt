package edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import edu.rit.connors.brandon.mtgandroidscryfallsearch.R
import edu.rit.connors.brandon.mtgandroidscryfallsearch.models.Card
import edu.rit.connors.brandon.mtgandroidscryfallsearch.util.HttpRequestQueue
import kotlinx.android.synthetic.main.fragment_card_display.*

class CardDisplay:DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_display, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageLoader = HttpRequestQueue.getInstance(view.context).imageLoader
        val card : Card = arguments?.getSerializable(ARG_CARD_ID) as Card
        card_image.setImageUrl(card.getImage("png"),imageLoader)
        card_name.text = card.name
        card_text.text = card.text
        card_cmc.text = card.cmc.toString()
        card_mana_cost.text = card.manaCost
        card_rarity.text = card.rarity
        card_set.text = card.set
        card_type.text = card.type
    }
    companion object{
        const val ARG_CARD_ID = "card_id"
    }
}