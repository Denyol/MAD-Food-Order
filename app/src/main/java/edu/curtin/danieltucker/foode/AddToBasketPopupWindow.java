package edu.curtin.danieltucker.foode;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import edu.curtin.danieltucker.foode.model.BasketViewModel;
import edu.curtin.danieltucker.foode.model.MenuItem;


public class AddToBasketPopupWindow extends PopupWindow {

    private final MenuItem item;
    private final BasketViewModel basketViewModel;

    public AddToBasketPopupWindow(View contentView, MenuItem item, BasketViewModel basketViewModel) {
        super(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.item = item;
        this.basketViewModel = basketViewModel;

        this.setFocusable(true);
        this.setElevation(20.0f);
    }

    public void createAddToBasketPopup(View parent) {
        // Get view elements
        TextView price = getContentView().findViewById(R.id.basketPriceText);
        TextView itemName = getContentView().findViewById(R.id.basketItemText);
        TextView itemCount = getContentView().findViewById(R.id.basketItemCount);
        Button decrease = getContentView().findViewById(R.id.basketMinusButton);
        Button increase = getContentView().findViewById(R.id.basketPlusButton);
        Button addToCart = getContentView().findViewById(R.id.basketAddButton);

        // Set button listeners
        decrease.setOnClickListener(v -> changeCountText(itemCount, false));
        increase.setOnClickListener(v -> changeCountText(itemCount, true));

        addToCart.setOnClickListener(v -> {
            Integer count = Integer.parseInt(itemCount.getText().toString());

            if (count > 0) {
                basketViewModel.putItem(item, count);
                Log.d("BasketPopupWindow", "Added " + itemName.getText().toString() + " to cart.");
            } else
                basketViewModel.removeItem(item);
            dismiss();
        });

        price.setText(Float.toString(item.getPrice()));
        itemName.setText(item.getDescription());

        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    private void changeCountText(TextView countView, boolean increase) {
        try {
            int count = Integer.parseInt(countView.getText().toString());

            if (count > 0 && !increase) {
                count--;
                countView.setText(String.format("%d", count));
            } else if (increase) {
                count++;
                countView.setText(String.format("%d", count));
            }
        } catch (NumberFormatException e) {
            Log.w("MenuFragment", e);
        }
    }
}
