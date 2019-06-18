package com.th3pl4gu3.lifestyle.ui.home.section_goal

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.th3pl4gu3.lifestyle.R

abstract class GoalSwipeToCallback(val context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                             dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

        val foregroundView = (viewHolder as GoalAdapter.ViewHolder).binding.RecyclerViewConstraintLayoutListForegroundView

        if(dX < 0){
            (viewHolder).binding.RecyclerViewLinearLayoutListBackgroundView.setBackgroundColor(ContextCompat.getColor(context, R.color.notification_successful))
        }else if(dX > 0){
            (viewHolder).binding.RecyclerViewLinearLayoutListBackgroundView.setBackgroundColor(ContextCompat.getColor(context, R.color.notification_error))
        }

        ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX/2, dY, actionState, isCurrentlyActive)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView = (viewHolder as GoalAdapter.ViewHolder).binding.RecyclerViewConstraintLayoutListForegroundView
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foregroundView)
    }
}