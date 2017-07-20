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
    void onDirectionFinderSuccess(List<Route> route, View view, String origin, String destination, Object object,String customMarkerOrigin,String customMarkerDestination);
}
