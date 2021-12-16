package com.orzechowski.saveme.browser.results;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.orzechowski.saveme.R;
import com.orzechowski.saveme.browser.userrating.Rating;
import com.orzechowski.saveme.browser.userrating.RatingViewModel;
import com.orzechowski.saveme.helper.database.Helper;
import com.orzechowski.saveme.tutorial.database.Tutorial;
import com.orzechowski.saveme.volley.StringPost;

import java.util.List;

public class ResultsListAdapter extends RecyclerView.Adapter<ResultsListAdapter.ResultViewHolder>
{
    private List<Tutorial> mTutorials;
    private final LayoutInflater mInflater;
    private final Activity mActivity;
    private List<Helper> mHelpers;
    private final FragmentCallback mCallback;
    private final String mPathBase;

    public ResultsListAdapter(Activity activity, FragmentCallback onClickListener)
    {
        mInflater = LayoutInflater.from(activity);
        mActivity = activity;
        mCallback = onClickListener;
        mPathBase = activity.getFilesDir().getAbsolutePath() + "/";
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        return new ResultViewHolder(mInflater.inflate(R.layout.row_results_rv, viewGroup,
                false), mCallback, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int rowNumber)
    {
        Tutorial tutorial = mTutorials.get(rowNumber);
        if(tutorial != null) {
            long authorId = tutorial.getAuthorId();
            for (Helper helper : mHelpers) {
                if (helper != null) {
                    if (authorId == helper.getHelperId()) {
                        holder.author.setText(String.format("Autor: %s %s %s",
                                helper.getTitle(), helper.getName(), helper.getSurname()));
                    }
                }
            }
            holder.tutorial = tutorial;
            holder.name.setText(tutorial.getTutorialName());
            Uri uri = Uri.parse(mPathBase + tutorial.getMiniatureName());
            if (uri != null) holder.image.setImageURI(uri);
            float rating = tutorial.getRating();

            if (rating > 4.75) holder.starFive.setImageResource(R.drawable.ic_full_star);
            else if (rating > 4.25) holder.starFive.setImageResource(R.drawable.ic_half_star);
            else holder.starFive.setImageResource(R.drawable.ic_empty_star);

            if (rating > 3.75) holder.starFour.setImageResource(R.drawable.ic_full_star);
            else if (rating > 4.25) holder.starFour.setImageResource(R.drawable.ic_half_star);
            else holder.starFour.setImageResource(R.drawable.ic_empty_star);

            if (rating > 2.75) holder.starThree.setImageResource(R.drawable.ic_full_star);
            else if (rating > 2.25) holder.starThree.setImageResource(R.drawable.ic_half_star);
            else holder.starThree.setImageResource(R.drawable.ic_empty_star);

            if (rating > 1.75) holder.starTwo.setImageResource(R.drawable.ic_full_star);
            else if (rating > 1.25) holder.starTwo.setImageResource(R.drawable.ic_half_star);
            else holder.starTwo.setImageResource(R.drawable.ic_empty_star);

            if (rating > 0.75) holder.starOne.setImageResource(R.drawable.ic_full_star);
            else if (rating > 0.25) holder.starOne.setImageResource(R.drawable.ic_half_star);
            else holder.starOne.setImageResource(R.drawable.ic_empty_star);
        }
    }

    @Override
    public int getItemCount()
    {
        return (mTutorials == null) ? 0 : mTutorials.size();
    }

    public void setElementList(List<Tutorial> tutorials, List<Helper> helpers)
    {
        mTutorials = tutorials;
        mHelpers = helpers;
        notifyDataSetChanged();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        TextView name, author;
        ImageView image, starOne, starTwo, starThree, starFour, starFive;
        FragmentCallback callback;
        Tutorial tutorial;
        ConstraintLayout starLayout;
        RequestQueue queue;
        Activity mActivity;
        RatingViewModel ratingViewModel;

        public ResultViewHolder(@NonNull View itemView, FragmentCallback fragmentCallback,
                                Activity activity)
        {
            super(itemView);
            queue = new RequestQueue(new DiskBasedCache(activity.getCacheDir(),
                    1024 * 1024), new BasicNetwork(new HurlStack()));
            mActivity = activity;
            name = itemView.findViewById(R.id.result_name_text);
            image = itemView.findViewById(R.id.result_image);
            author = itemView.findViewById(R.id.author);
            starOne = itemView.findViewById(R.id.star_one);
            starTwo = itemView.findViewById(R.id.star_two);
            starThree = itemView.findViewById(R.id.star_three);
            starFour = itemView.findViewById(R.id.star_four);
            starFive = itemView.findViewById(R.id.star_five);
            starLayout = itemView.findViewById(R.id.star_rating_layout);
            ratingViewModel = new ViewModelProvider((ViewModelStoreOwner) activity)
                    .get(RatingViewModel.class);
            ratingViewModel.getAll().observe((LifecycleOwner) activity, ratings -> {
                boolean match = false;
                if(tutorial != null) {
                    long tutorialId = tutorial.getTutorialId();
                    for (Rating rating : ratings) {
                        if (rating.getTutorialId() == tutorialId) {
                            match = true;
                        }
                    }
                    if (match) {
                        starLayout.setOnClickListener(v -> Toast.makeText(activity,
                                activity.getString(R.string.tutorial_already_rated),
                                Toast.LENGTH_SHORT).show());
                    } else {
                        starOne.setOnClickListener(v -> sendRating(1));
                        starTwo.setOnClickListener(v -> sendRating(2));
                        starThree.setOnClickListener(v -> sendRating(3));
                        starFour.setOnClickListener(v -> sendRating(4));
                        starFive.setOnClickListener(v -> sendRating(5));
                    }
                }
            });
            callback = fragmentCallback;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            callback.onClick(tutorial);
        }

        private void sendRating(int rating)
        {
            queue.start();
            @SuppressLint("HardwareIds") String id = Settings.Secure
                    .getString(mActivity.getContentResolver(), Settings.Secure.ANDROID_ID);
            queue.add(new StringPost(Request.Method.POST, mActivity.getString(R.string.url) +
                    "rating/" + tutorial.getTutorialId() + "/" + rating + "/" + id,  string -> {
                if(string.equals("ok")) {
                    ratingViewModel.insert(new Rating(tutorial.getTutorialId()));
                    Toast.makeText(mActivity, R.string.tutorial_rating_submitted,
                            Toast.LENGTH_SHORT).show();
                } else Toast.makeText(mActivity, R.string.server_error, Toast.LENGTH_SHORT).show();
            }, e -> {
                if(e.getMessage() != null) {
                    Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }

    public interface FragmentCallback
    {
        void onClick(Tutorial tutorial);
    }
}
