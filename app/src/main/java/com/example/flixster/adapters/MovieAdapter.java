package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){

        this.context = context;
        this.movies = movies;

    }

    //Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // involved populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);
    }

    //returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // when phone is in landscape
            // set imageUrl as backdrop
            // otherwise, use poster image (for both landscape and portrait)
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                imageUrl = movie.getBackDropPath();
                Glide.with(context).load(imageUrl).transform(new RoundedCorners(50)).placeholder(R.drawable.flicks_backdrop_placeholder).into(ivPoster);


            } else{

                imageUrl = movie.getPosterPath();
                Glide.with(context).load(imageUrl).transform(new RoundedCorners(50)).placeholder(R.drawable.flicks_movie_placeholder).into(ivPoster);

            }

            //Glide.with(context).load(imageUrl).placeholder(R.drawable.flicks_movie_placeholder).into(ivPoster);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION){

                Movie movie = movies.get(position);

                Intent intent = new Intent(context, MovieDetailsActivity.class);

                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));

                context.startActivity(intent);

            }

        }
    }

}
