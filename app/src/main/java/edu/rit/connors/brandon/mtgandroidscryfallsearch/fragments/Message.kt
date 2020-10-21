package edu.rit.connors.brandon.mtgandroidscryfallsearch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rit.connors.brandon.mtgandroidscryfallsearch.R
import kotlinx.android.synthetic.main.fragment_message.*

class Message:Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        message_text.text = arguments!!.getString(ARG_MSG_ID)
    }
    companion object{
        const val ARG_MSG_ID = "msg_id"
    }
}