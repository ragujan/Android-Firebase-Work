package com.rag.firebaseauthentication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rag.firebaseauthentication.ChangeFoodStatusDialogFragment;
import com.rag.firebaseauthentication.R;
import com.rag.firebaseauthentication.StartGameDialogFragment;
import com.rag.firebaseauthentication.domain.FoodDomain;

import java.util.List;

public class AllFoodListAdapter extends RecyclerView.Adapter<AllFoodListAdapter.ViewHolder> {


    List<FoodDomain> foodDomainList;
    ViewGroup parent;

    public AllFoodListAdapter(List<FoodDomain> foodDomainList) {
        this.foodDomainList = foodDomainList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_food_item_view_holder, parent, false);
        this.parent = parent;

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FoodDomain foodDomain = foodDomainList.get(position);
        holder.foodTitle.setText(foodDomain.getTitle().toString());
        holder.foodPrice.setText(foodDomain.getPrice().toString());
        if (foodDomain.getAvailable() == true) {
            holder.foodAvailableMessageLabel.setText("Available");
            holder.foodAvailableStatus.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.available_status_circle));
        } else {

            holder.foodAvailableStatus.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.unavailable_status_circle));
            holder.foodAvailableMessageLabel.setText("Unavailable");
        }
        Glide.with(holder.itemView.getContext()).load(foodDomain.getImageUrl()).into(holder.foodImage);

        holder.foodAvailableStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(holder.itemView.getContext(),"Hey",Toast.LENGTH_SHORT).show();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();


//               new StartGameDialogFragment().show(activity.getSupportFragmentManager(),"test game start");
                new ChangeFoodStatusDialogFragment(foodDomain.getAvailable()).show(activity.getSupportFragmentManager(),"test change status");

            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDomainList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImage;
        TextView foodTitle;
        TextView foodPrice;
        TextView foodAvailableStatus;
        TextView foodAvailableMessageLabel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.cmnFoodImageView);
            foodTitle = itemView.findViewById(R.id.cmnfoodTitleView);
            foodPrice = itemView.findViewById(R.id.cmnFoodPriceView);
            foodAvailableStatus = itemView.findViewById(R.id.cmnFoodAvailableStatusView);
            foodAvailableMessageLabel = itemView.findViewById(R.id.cmnFoodAvailableStatusMessage);

        }
    }
}
