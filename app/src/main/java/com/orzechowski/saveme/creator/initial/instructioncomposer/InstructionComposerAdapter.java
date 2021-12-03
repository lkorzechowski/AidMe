package com.orzechowski.saveme.creator.initial.instructioncomposer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orzechowski.saveme.R;
import com.orzechowski.saveme.tutorial.instructions.database.InstructionSet;

import java.util.List;

public class InstructionComposerAdapter extends RecyclerView.Adapter<InstructionsViewHolder>
{
    private List<InstructionSet> mInstructions;
    private final LayoutInflater mInflater;
    private final FragmentCallback mCallback;
    private final Activity mActivity;

    public InstructionComposerAdapter(Activity activity, FragmentCallback callback)
    {
        mInflater = LayoutInflater.from(activity);
        mCallback = callback;
        mActivity = activity;
    }

    @NonNull
    @Override
    public InstructionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View row = mInflater.inflate(R.layout.row_new_instruction_rv, parent, false);
        return new InstructionsViewHolder(row, mCallback, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionsViewHolder holder, int position)
    {
        InstructionSet set = mInstructions.get(position);
        holder.mTitle.setText(set.getTitle());
        holder.mInstruction = set;
        holder.mContent.setText(set.getInstructions());
        holder.mDisplayTime.setText(String.valueOf(set.getTime()));
        holder.mPosition.setText(String.valueOf(set.getPosition()+1));
        holder.mNarrationFile.setText(set.getNarrationFile());
    }

    @Override
    public int getItemCount()
    {
        return (mInstructions == null) ? 0 : mInstructions.size();
    }

    public void setElementList(List<InstructionSet> instructions)
    {
        mInstructions = instructions;
        notifyDataSetChanged();
    }

    public interface FragmentCallback
    {
        void delete(InstructionSet instructionSet);
        void modifyTitle(String title, int position);
        void modifyContent(String content, int position);
        void modifyDisplayTime(int time, int position);
        void addNarration(int position);
    }
}
