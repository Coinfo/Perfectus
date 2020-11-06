/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui.modify

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import app.chamich.feature.goals.model.Category
import app.chamich.feature.goals.model.Color
import app.chamich.feature.goals.model.Measurement
import java.text.SimpleDateFormat

internal abstract class ModifyGoalViewModel : ViewModel() {

    var measurement: Measurement = Measurement.default()
    var color: Color = Color.default()
    var category: Category = Category.default()
    var date: Long = 0L
    var dateString = SimpleDateFormat("MMM").format(date)

}
