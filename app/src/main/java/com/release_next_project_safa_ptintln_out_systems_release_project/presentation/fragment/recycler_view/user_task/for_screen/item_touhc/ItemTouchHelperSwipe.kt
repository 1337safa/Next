package com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.for_screen.item_touhc

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.release_next_project_safa_ptintln_out_systems_release_project.R
import com.release_next_project_safa_ptintln_out_systems_release_project.presentation.fragment.recycler_view.user_task.insterfaces.UserRemoveTaskBySwipe
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ItemTouchHelperSwipe(
    private val userDeleteTask: UserRemoveTaskBySwipe
    ) : ItemTouchHelper
    .SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT) {
                userDeleteTask.removeByIdFromSql(viewHolder.absoluteAdapterPosition)
            }
        }

        override fun onChildDraw(
            c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(
                c, recyclerView, viewHolder, dX, dY, actionState,
                isCurrentlyActive
            )
                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                .create()
                .decorate()

            super
                .onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        }

    }