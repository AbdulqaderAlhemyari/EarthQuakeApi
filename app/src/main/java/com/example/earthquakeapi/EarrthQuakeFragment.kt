package com.example.earthquakeapi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquakeapi.models.EarthQuakeAtt
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat


class EarrthQuakeFragment : Fragment() {

    interface Callbacks {
        fun onEarthQuakeSelected(locationIntent: Intent)
    }

    private var callbacks: Callbacks? = null
    private var location:List<Double> = emptyList()
    private lateinit var earthQuakeViewModel: EarthQuakeViewModel
    private lateinit var earthQuakeRecycler : RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthQuakeViewModel = ViewModelProviders.of(this).get(EarthQuakeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_earrth_quake, container, false)
        earthQuakeRecycler = view.findViewById(R.id.earthQuakeRecycler)
        earthQuakeRecycler.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        earthQuakeViewModel.earthQuakeAttribute.observe(viewLifecycleOwner,
            Observer { earthQuakeAttributies ->

                updateUi(earthQuakeAttributies)
            })
    }

    fun updateUi(earthQuakeAttributies : List<EarthQuakeAtt>){
            earthQuakeRecycler.adapter = EarthQuakeAdapter(earthQuakeAttributies)
    }

    private inner class EarthQuakeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) ,View.OnClickListener
    {
        init {
            itemView.setOnClickListener(this)
        }
        @SuppressLint("SimpleDateFormat")
        var dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        @SuppressLint("SimpleDateFormat")
        var timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
        var earthQuakeMag= itemView.findViewById(R.id.magnitude_tv) as TextView
        var earthQuakeReigon = itemView.findViewById(R.id.reigon_tv) as TextView
        var earthQuakeTime = itemView.findViewById(R.id.time_textView) as TextView
        var earthQuakeDate = itemView.findViewById(R.id.date_textView) as TextView


        fun bind(earthQuakeAtt: EarthQuakeAtt){
            location=earthQuakeAtt.geometric.geometrics
            var earthQuakeStrength: Double = earthQuakeAtt.properties.strength.toDouble()
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            earthQuakeReigon.text = earthQuakeAtt.properties.place
            val time = earthQuakeAtt.properties.time
            earthQuakeTime.text = timeFormat.format(time)
            earthQuakeDate.text = dateFormat.format(time)
            earthQuakeMag.apply {
                text = df.format(earthQuakeStrength).toString()
                 when{
                     earthQuakeStrength < 4.0 -> setBackgroundResource(R.drawable.green_circle)
                     earthQuakeStrength < 5.0 -> setBackgroundResource(R.drawable.yellow_circle)
                     earthQuakeStrength < 6.0 -> setBackgroundResource(R.drawable.orange_circle)
                     earthQuakeStrength >= 6.0 -> setBackgroundResource(R.drawable.red_circle)
                 }
            }
        }

        override fun onClick(v: View?) {
            val longitude=location[0]
            val latitude=location[1]

            val locationIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("geo:$longitude,$latitude")
            }

            callbacks?.onEarthQuakeSelected(locationIntent)
            }
        }
    private inner class EarthQuakeAdapter(private val earthQuakeAttributes: List<EarthQuakeAtt>) :
        RecyclerView.Adapter<EarthQuakeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthQuakeHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_earthquake, parent, false)
            return EarthQuakeHolder(view)
        }

        override fun getItemCount(): Int {
             return earthQuakeAttributes.size
        }

        override fun onBindViewHolder(holder: EarthQuakeHolder, position: Int) {
            val earthQuakeAttributes = earthQuakeAttributes[position]
            holder.bind(earthQuakeAttributes)
        }
    }
    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance() = EarrthQuakeFragment()
    }

    }
