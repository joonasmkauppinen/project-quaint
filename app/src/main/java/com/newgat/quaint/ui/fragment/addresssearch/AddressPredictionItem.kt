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
            val mainText = prediction.structured_formatting.main_text
            addressMainText.text = mainText
            addressSecondaryText.text = prediction.structured_formatting.secondary_text
            fillPredictionButton.setOnClickListener{ listener.onFillClicked(mainText) }
            predictionItem.setOnClickListener{ listener.onItemPredictionClicked(prediction) }
        }
    }

    override fun getLayout() = R.layout.address_prediction_list_item

    interface PredictionItemClickListener {
        fun onFillClicked(streetName: String)
        fun onItemPredictionClicked(prediction: Prediction)
    }
}