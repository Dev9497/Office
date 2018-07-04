package com.example.office.view

import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.office.R
import com.example.office.adapter.ClientAdapter
import com.example.office.interfaces.ClientRequest
import com.example.office.model.Client
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_display_client.*
import kotlinx.android.synthetic.main.client_row.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DisplayClientActivity : AppCompatActivity(), ClientAdapter.Listener {
    private val TAG = DisplayClientActivity::class.java.simpleName

    private val BASE_URL = "http://jsonplaceholder.typicode.com"

    private var compositeDisposable: CompositeDisposable? = null

    private var clientAdapter: ClientAdapter? = null

    override fun onItemClick(clientData: Client) {

        val detailsDialog = AlertDialog.Builder(this)
        val detailsDialogView = layoutInflater.inflate(R.layout.client_details,null)
        detailsDialogView.findViewById<TextView>(R.id.tv_id).text = clientData.id
        detailsDialogView.findViewById<TextView>(R.id.tv_name).text = clientData.name
        detailsDialogView.findViewById<TextView>(R.id.tv_username).text = clientData.username
        detailsDialogView.findViewById<TextView>(R.id.tv_email).text = clientData.email
        detailsDialogView.findViewById<TextView>(R.id.tv_street).text = clientData.address!!.street
        detailsDialogView.findViewById<TextView>(R.id.tv_suite).text = clientData.address!!.suite
        detailsDialogView.findViewById<TextView>(R.id.tv_city).text = clientData.address!!.city
        detailsDialogView.findViewById<TextView>(R.id.tv_zipcode).text = clientData.address!!.zipcode
        detailsDialogView.findViewById<TextView>(R.id.tv_phone).text = clientData.phone
        detailsDialogView.findViewById<TextView>(R.id.tv_website).text = clientData.website
        detailsDialogView.findViewById<TextView>(R.id.tv_cmp_name).text = clientData.company!!.name
        detailsDialogView.findViewById<TextView>(R.id.tv_catch_phrase).text = clientData.company!!.catchPhrase
        detailsDialogView.findViewById<TextView>(R.id.tv_bs).text = clientData.company!!.bs

        detailsDialog.setView(detailsDialogView)
        detailsDialog.setCancelable(true)
        detailsDialog.show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_client)

        compositeDisposable = CompositeDisposable()

        initRecyclerView()

        loadJSON()
    }

    private fun initRecyclerView(){
        rv_client_list.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_client_list.layoutManager = layoutManager
        initSwipe()
    }

    private fun loadJSON(){
        val requestClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ClientRequest::class.java)

        compositeDisposable?.add(requestClient.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError))
    }

    private fun handleResponse(clientList: ArrayList<Client>){
        rv_client_list.visibility  = View.VISIBLE
        pb_client.visibility = View.GONE
        clientList?.let {
            clientAdapter = ClientAdapter(it,this)
            rv_client_list.adapter = clientAdapter
        }
    }

    private fun handleError(error: Throwable){
        rv_client_list.visibility = View.VISIBLE
        pb_client.visibility = View.GONE
        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    private val p = Paint()

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                clientAdapter?.notifyItemChanged(viewHolder.adapterPosition)
                if (direction == ItemTouchHelper.LEFT) {
                    val phoneNumber = "tel: "+ tv_client_phone.text.toString()
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse(phoneNumber)
                    if(dialIntent.resolveActivity(packageManager) !=null){
                        startActivity(dialIntent)
                    }
                    else{
                        Log.i(TAG,"Can't resolve app for ACTION_DIAL Intent.")
                    }
                }
                else {
                    clientAdapter?.notifyItemChanged(viewHolder.adapterPosition)
                    val smsNumber = "smsto:"+tv_client_phone.text.toString()
                    val smsMessage = "Hello ABCD!"
                    val smsIntent = Intent(Intent.ACTION_SENDTO)
                    smsIntent.data = Uri.parse(smsNumber)
                    smsIntent.putExtra("smsBody",smsMessage)
                    if(smsIntent.resolveActivity(packageManager)!=null){
                        startActivity(smsIntent)
                    }
                    else{
                        Log.i(TAG,"Can't resolve app for ACTION_SENDTO Intent.")
                    }

                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                    val width = height / 3

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"))
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.message)
                        val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    } else {
                        p.setColor(Color.parseColor("#0000FF"))
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.call)
                        val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rv_client_list)
    }
}
