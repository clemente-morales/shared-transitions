package lania.com.mx.shared_transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * @author Clemente Morales Fernandez
 * @since 4/12/2017.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RewardDetailsTransition extends TransitionSet {
    public RewardDetailsTransition() {
        init();
    }

    /**
     * This constructor allows us to use this transition in XML
     */
    public RewardDetailsTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform()).
                addTransition(new ChangeImageTransform());
    }
}