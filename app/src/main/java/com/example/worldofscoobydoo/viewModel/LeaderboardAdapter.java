package com.example.worldofscoobydoo.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.LeaderboardEntry;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private LeaderboardEntry[] entries;
    public LeaderboardAdapter(LeaderboardEntry[] e) {
        entries = e;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LeaderboardEntry entry = entries[position];
        holder.playerNameTextView.setText(entry.getPlayerName());
        holder.scoreTextView.setText(String.valueOf(entry.getScore()));
        holder.dateTextView.setText(String.valueOf(entry.getDate()));
    }

    @Override
    public int getItemCount() {
        return entries.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView playerNameTextView;
        private TextView scoreTextView;
        private TextView dateTextView;

        ViewHolder(View itemView) {
            super(itemView);
            playerNameTextView = itemView.findViewById(R.id.text_view_player_name);
            scoreTextView = itemView.findViewById(R.id.text_view_score);
            dateTextView = itemView.findViewById(R.id.text_view_date);
        }
    }
}
