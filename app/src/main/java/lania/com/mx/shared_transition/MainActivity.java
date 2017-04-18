package lania.com.mx.shared_transition;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null)
            displayFragment(HomeFragment.newInstance(), false, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReplaceFragment(ReplaceFragmentEvent event) {
        displayFragment(event.getFragment(), event.isAddToBackStack(), event.getTransitionSharedElements());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void displayFragment(Fragment fragment, boolean addToBackStack, Map<String, View> transitionSharedElements) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, fragment);

        addSharedTransactionElements(transaction, transitionSharedElements);

        if (addToBackStack)
            transaction.addToBackStack(null);

        transaction.commit();
    }

    private void addSharedTransactionElements(FragmentTransaction transaction, Map<String, View> transitionSharedElements) {
        if (transitionSharedElements != null) {
            supportPostponeEnterTransition();
            for (String key : transitionSharedElements.keySet()) {
                transaction.addSharedElement(transitionSharedElements.get(key), key);
            }
        }
    }
}
