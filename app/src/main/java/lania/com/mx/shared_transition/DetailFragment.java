package lania.com.mx.shared_transition;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * @author Clemente Morales Fernandez
 * @since 4/12/2017.
 */

public class DetailFragment extends Fragment {
    public static final String CARD_BACKGROUND_TRANSITION_FORMAT = "rewardCardBackground%d";
    public static final int ANIMATION_DURATION = 500;
    public static final String HORIZONTAL_SCALING = "scaleX";
    public static final String VERTICAL_SCALING = "scaleY";
    public static final float INITIAL_IMAGE_SIZE = 0.7f;
    public static final float TARGET_IMAGE_SIZE = 1.0f;
    public static final String TRANSLATION_X = "translationX";
    public static final int INITIAL_POINT_TO_TRANSLATE_X = -90;
    public static final int TARGET_POINT_TO_TRANSLATE_X = 0;
    public static final int REWARD_ID = 23;

    private ImageView backgroundImage;

    public static DetailFragment newInstance() {

        Bundle args = new Bundle();

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        displayData(rootView);
        return rootView;
    }

    private void displayData(View rootView) {
        backgroundImage = (ImageView) rootView.findViewById(R.id.backgroundDetailHeader);
        registerTransactionName(backgroundImage);
        animate(rootView);
    }

    private void animate(View rootView) {
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
        ObjectAnimator enterInAnimationX = ObjectAnimator.ofFloat(backgroundImage, TRANSLATION_X, INITIAL_POINT_TO_TRANSLATE_X, TARGET_POINT_TO_TRANSLATE_X);
        enterInAnimationX.setInterpolator(accelerateInterpolator);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(backgroundImage, HORIZONTAL_SCALING, INITIAL_IMAGE_SIZE, TARGET_IMAGE_SIZE);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(backgroundImage, VERTICAL_SCALING, INITIAL_IMAGE_SIZE, TARGET_IMAGE_SIZE);
        final AnimatorSet scaleAnim = new AnimatorSet();
        scaleAnim.setDuration(ANIMATION_DURATION);
        scaleAnim.play(scaleX).with(scaleY);

        Glide.with(getContext()).load("https://avatars2.githubusercontent.com/u/1816039?v=3&u=9a817b6ded1601e4788216371dc7f5aa0d472a3b&s=400").listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                getActivity().startPostponedEnterTransition();
                scaleAnim.start();
                return false;
            }
        }).into(backgroundImage);
    }

    private void registerTransactionName(ImageView backgroundImage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String transitionName = String.format(CARD_BACKGROUND_TRANSITION_FORMAT, REWARD_ID);
            backgroundImage.setTransitionName(transitionName);
        }
    }
}
