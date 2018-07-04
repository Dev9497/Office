package com.example.office.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.office.R
import com.example.office.model.Client
import kotlinx.android.synthetic.main.client_row.view.*

class ClientAdapter(private val clientList: ArrayList<Client>, private val listener: Listener): RecyclerView.Adapter<ClientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.client_row, parent, false)

        return ClientAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = clientList.count()


    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.bind(clientList[position], listener, position)
    }

    interface Listener{
        fun onItemClick(clientData : Client)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(clientData: Client, listener: Listener, position: Int){
            itemView.tv_client_id.text = clientData.id
            itemView.tv_client_name.text = clientData.name
            itemView.tv_client_email.text = clientData.email
            itemView.tv_client_phone.text = clientData.phone
            itemView.setOnClickListener{(listener.onItemClick(clientData))}
        }
    }
}