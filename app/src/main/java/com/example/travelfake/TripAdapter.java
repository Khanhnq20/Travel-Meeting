package com.example.travelfake;


import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelfake.Entity.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    public int position;
    public List<Trip> trips;
    public TripAdapter() {
        this.trips = new ArrayList<>();
    }
    public void setListTrip(List<Trip> newList){
        this.notifyDataSetChanged();
        this.trips = newList;
    }
    public static String TRIP_ID_INTENT = "_id";
    public static String TRIP_NAME_INTENT = "trip_name";
    public static String TRIP_DESTINATION_INTENT = "trip_destination";
    public static String TRIP_DATE_INTENT = "trip_date";
    public static String TRIP_REQUIRE_INTENT ="trip_require";
    public static String TRIP_DESCRIPTION_INTENT ="trip_description";

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_form,parent,false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.trip_id.setText(String.valueOf(trip.getTrip_id()));
        holder.trip_name.setText(trip.getTrip_name());
        holder.trip_destination.setText(trip.getTrip_destination());
        holder.trip_date.setText(trip.getTrip_date());
        holder.trip_require.setText(trip.getTrip_require()? "Yes" : "No");
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("clicked", "activated");
                Intent intent = new Intent(view.getContext(),UpdateActivity.class);
                intent.putExtra(TRIP_ID_INTENT,(trip.getTrip_id()));
                intent.putExtra(TRIP_NAME_INTENT,(trip.getTrip_name()));
                intent.putExtra(TRIP_DESTINATION_INTENT,(trip.getTrip_destination()));
                intent.putExtra(TRIP_DATE_INTENT,(trip.getTrip_date()));
                intent.putExtra(TRIP_REQUIRE_INTENT,(trip.getTrip_require()));
                intent.putExtra(TRIP_DESCRIPTION_INTENT,(trip.getTrip_description()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return trips.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mainLayout;
        TextView trip_id,trip_name,trip_destination,trip_date,trip_require;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id = itemView.findViewById(R.id.trip_id);
            trip_name = itemView.findViewById(R.id.trip_name);
            trip_destination = itemView.findViewById(R.id.trip_destination);
            trip_date = itemView.findViewById(R.id.trip_date);
            trip_require = itemView.findViewById(R.id.trip_require);
            mainLayout = itemView.findViewById(R.id.trip_row);
        }
    }
}