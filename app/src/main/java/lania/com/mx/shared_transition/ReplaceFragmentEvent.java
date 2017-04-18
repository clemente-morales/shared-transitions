package lania.com.mx.shared_transition;

import android.support.v4.app.Fragment;
import android.view.View;

import java.util.Map;

/**
 * @author Clemente Morales Fernandez
 * @since 4/12/2017.
 */

public class ReplaceFragmentEvent {
    private final Fragment fragment;
    private final boolean addToBackStack;
    private Map<String, View> transitionSharedElements;

    public ReplaceFragmentEvent(Fragment fragment, boolean addToBackStack) {
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }

    public Map<String, View> getTransitionSharedElements() {
        return transitionSharedElements;
    }

    public void setTransitionSharedElements(Map<String, View> transitionSharedElements) {
        this.transitionSharedElements = transitionSharedElements;
    }
}
