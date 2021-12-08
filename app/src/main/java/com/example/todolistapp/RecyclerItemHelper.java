package com.example.todolistapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Adapter.TaskAdapter;

public class RecyclerItemHelper extends ItemTouchHelper.SimpleCallback {
    private TaskAdapter taskAdapter;
    public RecyclerItemHelper(TaskAdapter taskAdapter){
        super(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
        this.taskAdapter = taskAdapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){
            AlertDialog.Builder builder = new AlertDialog.Builder(taskAdapter.getContext());
            builder.setTitle("Xoá nhiệm vụ");
            builder.setMessage("Bạn có chắc chắn xoá không");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    taskAdapter.deleteItem(pos);
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    taskAdapter.notifyDataSetChanged();viewHolder.getAdapterPosition();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            taskAdapter.edititem(pos);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        Drawable icon;
        ColorDrawable backg;

        View itemview = viewHolder.itemView;
        int bgset = 20;

        if(dX > 0){
            icon = ContextCompat.getDrawable(taskAdapter.getContext(),R.drawable.ic_baseline_edit_24);
            backg = new ColorDrawable(ContextCompat.getColor(taskAdapter.getContext(),R.color.cbox_green));
        }
        else {
            icon = ContextCompat.getDrawable(taskAdapter.getContext(),R.drawable.ic_baseline_delete_forever_24);
            backg = new ColorDrawable(ContextCompat.getColor(taskAdapter.getContext(),R.color.cbox_red));
        }
        int iconMargin = (itemview.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemview.getTop() + (itemview.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = itemview.getLeft() + iconMargin;
            int iconRight = itemview.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            backg.setBounds(itemview.getLeft(), itemview.getTop(),
                    itemview.getLeft() + ((int) dX) + bgset, itemview.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemview.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemview.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            backg.setBounds(itemview.getRight() + ((int) dX) - bgset,
                    itemview.getTop(), itemview.getRight(), itemview.getBottom());
        } else { // view is unSwiped
            backg.setBounds(0, 0, 0, 0);
        }

        backg.draw(c);
        icon.draw(c);

    }
}
