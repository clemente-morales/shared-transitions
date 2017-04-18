package lania.com.mx.customshadow;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static lania.com.mx.customshadow.DetailFragment.CARD_BACKGROUND_TRANSITION_FORMAT;

/**
 * @author Clemente Morales Fernandez
 * @since 4/12/2017.
 */

public class HomeFragment extends Fragment {
    public static final int IMAGE_CORNER_RADIUS = 10;
    private static final float IMAGE_MARGIN = 0;
    private ImageView backgroundImage;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        loadCard(rootView);
        loadEvents();
        return rootView;
    }

    private void loadEvents() {
        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DetailFragment detailFragment = DetailFragment.newInstance();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    detailFragment.setSharedElementEnterTransition(new RewardDetailsTransition());
                    detailFragment.setSharedElementReturnTransition(new RewardDetailsTransition());
                }


                ReplaceFragmentEvent replaceFragmentEvent = new ReplaceFragmentEvent(detailFragment, true);
                Map<String, View> transitionSharedElements = new HashMap<>();
                String transitionName = String.format(CARD_BACKGROUND_TRANSITION_FORMAT, DetailFragment.REWARD_ID);
                transitionSharedElements.put(transitionName, backgroundImage);
                replaceFragmentEvent.setTransitionSharedElements(transitionSharedElements);

                EventBus.getDefault().post(replaceFragmentEvent);
            }
        });
    }

    private void loadCard(View rootView) {
        backgroundImage = (ImageView) rootView.findViewById(R.id.cardBackground);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            String transitionName = String.format(CARD_BACKGROUND_TRANSITION_FORMAT, DetailFragment.REWARD_ID);
            backgroundImage.setTransitionName(transitionName);
        }

        float densityMultiplier = getResources().getDisplayMetrics().density;
        float radius = densityMultiplier * IMAGE_CORNER_RADIUS;
        float margin = densityMultiplier * IMAGE_MARGIN;

        BitmapPool bitmapPool = Glide.get(getActivity()).getBitmapPool();
        Glide.with(this).load("https://avatars2.githubusercontent.com/u/1816039?v=3&u=9a817b6ded1601e4788216371dc7f5aa0d472a3b&s=400")
                .transform(new CenterCrop(bitmapPool), new RoundCornersTransformation(bitmapPool, radius, margin, RoundCornersTransformation.CornerType.TOP))
                .into(backgroundImage);
    }
}
