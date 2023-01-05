package com.example.notesapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.MainActivity;

import com.example.notesapp.R;
import com.example.notesapp.UpdateActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Notes;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    ArrayList<Notes> notesArrayList;
//    NotesClickListener listener;

    public NotesAdapter(Context context, ArrayList<Notes> notesArrayList) {
        this.context = context;
        this.notesArrayList = notesArrayList;
//        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes,
                parent,false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(notesArrayList.get(position).getTitle());
        holder.tvSubtitle.setText(notesArrayList.get(position).getSubtitle());
        holder.tvDates.setText(notesArrayList.get(position).getDateTime());
        holder.setNote(notesArrayList.get(position));
        byte[] bytes = notesArrayList.get(position).getImage();

        if(bytes != null)
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.imageNotes.setImageBitmap(bitmap);
            holder.imageNotes.setVisibility(View.VISIBLE);
        }
        holder.noteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("note", (Serializable) notesArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    public  void filterArrayList(ArrayList<Notes> filteredArrayList){
        notesArrayList = filteredArrayList;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder{
    final LinearLayout noteLayout;
    final TextView tvTitle,tvSubtitle,tvDates;
    final RoundedImageView imageNotes;
    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle=itemView.findViewById(R.id.titleNote);
        tvSubtitle=itemView.findViewById(R.id.subtitleNote);
        tvDates=itemView.findViewById(R.id.textDate);
        noteLayout= itemView.findViewById(R.id.cvNotes);
        imageNotes=itemView.findViewById(R.id.imageNoteItem);
    }
    void setNote(Notes notes){
        GradientDrawable gradientDrawable = (GradientDrawable) noteLayout.getBackground();
        if(notes.getColor()!=null){
            gradientDrawable.setColor(Color.parseColor(notes.getColor()));
        }else{
            gradientDrawable.setColor(Color.parseColor("#4C4C4C"));
        }
    }
}