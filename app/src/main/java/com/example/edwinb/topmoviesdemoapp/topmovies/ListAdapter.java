package com.example.edwinb.topmoviesdemoapp.topmovies;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwinb.topmoviesdemoapp.Listeners.OnBottomReachedListener;
import com.example.edwinb.topmoviesdemoapp.R;

import java.util.List;

import static com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivity.ALLOW_KEY;
import static com.example.edwinb.topmoviesdemoapp.topmovies.TopMoviesActivity.MY_PERMISSIONS_REQUEST_CAMERA;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<ViewModel> mList;

    OnBottomReachedListener mOnBottomReachedListener;

    public ListAdapter(List<ViewModel> list){
        this.mList = list;
    }

    @NonNull
    @Override
    public ListAdapter.ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListItemViewHolder holder, int position) {

        if (position == mList.size() - 1){

            mOnBottomReachedListener.onBottomReached(position);

        }

        holder.itemName.setText(mList.get(position).getName());
        holder.countryName.setText(mList.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;
        public TextView countryName;
        Button mButton;
        Button cameraButton;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textView_fragmentlist_task_name);
            countryName = itemView.findViewById(R.id.textView_fragmentlist_country_name);
            mButton = itemView.findViewById(R.id.button);
            cameraButton = itemView.findViewById(R.id.captureFront);

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "I was clicked", Toast.LENGTH_SHORT).show();
                    view.setBackgroundColor(Color.BLUE);
                }
            });

            cameraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((TopMoviesActivity) itemView.getContext()).openCameraAndCheckPermissions();
                }
            });
        }
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.mOnBottomReachedListener = onBottomReachedListener;
    }


}
