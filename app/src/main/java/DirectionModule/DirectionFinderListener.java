package DirectionModule;

import android.view.View;

import com.app.fastcabdriver.entities.AssignRideEnt;
import com.app.fastcabdriver.ui.viewbinder.CompletedTripBinder;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/3/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route, String data, View view, CompletedTripBinder.ViewHolder viewHolder, String origin, String destination, AssignRideEnt entity);
}
