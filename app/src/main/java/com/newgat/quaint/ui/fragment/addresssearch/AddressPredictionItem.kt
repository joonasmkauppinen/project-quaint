package com.newgat.quaint.ui.fragment.addresssearch

import com.newgat.quaint.R
import com.newgat.quaint.data.db.entity.Prediction
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.address_prediction_list_item.*

class AddressPredictionItem(
    private val listener: PredictionItemClickListener,
    private val prediction: Prediction
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            addressPredictionTextView.text = prediction.description
            fillPredictionButton.setOnClickListener{ listener.onFillClicked(prediction.description) }
            predictionItem.setOnClickListener{ listener.onItemPredictionClicked(prediction) }
        }
    }

    override fun getLayout() = R.layout.address_prediction_list_item

    interface PredictionItemClickListener {
        fun onFillClicked(streetName: String)
        fun onItemPredictionClicked(prediction: Prediction)
    }
}