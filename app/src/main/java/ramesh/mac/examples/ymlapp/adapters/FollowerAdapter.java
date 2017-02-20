package ramesh.mac.examples.ymlapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ramesh.mac.examples.ymlapp.R;
import ramesh.mac.examples.ymlapp.entities.Follower;

/**
 * Created by User on 2/20/2017.
 */

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {

    private static final String TAG = "Adapter";
    private List<Follower> myfollowersList;
    Context context;
    OnItemClickListener onItemClickListener;

    @Override
    public FollowerAdapter.FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.follower_row_dat, parent, false);
        return new FollowerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FollowerAdapter.FollowerViewHolder holder, int position) {

        Follower follower = myfollowersList.get(position);

        holder.follName.setText(follower.getLogin());Picasso.with(context).load(follower.getAvatarUrl()).transform(new CircleTransformation())
                .into(holder.follImage, new Callback() {
                    @Override
                    public void onSuccess() {


                    }

                    @Override
                    public void onError() {

                    }
                });



    }

    @Override
    public int getItemCount() {
        return myfollowersList.size();
    }
    public class FollowerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView follName;
        ImageView follImage;


        public FollowerViewHolder(View itemView) {
            super(itemView);
            follName = (TextView) itemView.findViewById(R.id.textView_follower);
            follImage = (ImageView) itemView.findViewById(R.id.imageView_follower);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            onItemClickListener.onClick(v, getAdapterPosition());

        }
    }


    public void setClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FollowerAdapter(List<Follower> myfollowersList, Context context) {
        this.context = context;
        this.myfollowersList = myfollowersList;

    }
}
