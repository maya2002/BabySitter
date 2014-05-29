package tw.tasker.babysitter.presenter;

import static tw.tasker.babysitter.utils.LogUtils.makeLogTag;

import java.util.List;

import tw.tasker.babysitter.R;
import tw.tasker.babysitter.model.BabysitterMapModel;
import tw.tasker.babysitter.model.OnFinishedListener;
import tw.tasker.babysitter.model.data.BabysitterOutline;
import tw.tasker.babysitter.model.impl.BabysitterMapModelImpl;
import tw.tasker.babysitter.utils.MyLocation;
import tw.tasker.babysitter.view.BabysitterDetailActivity;
import tw.tasker.babysitter.view.BabysitterListActivity;
import tw.tasker.babysitter.view.BabysitterMapActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BabysitterMapPresenterImpl implements BabysitterMapPresenter, OnFinishedListener {
	private static final String TAG = makeLogTag(BabysitterMapPresenterImpl.class);

	private BabysitterMapActivity mBabysitterMap;
	private MyLocation mMyLocation;
	private BabysitterMapModel mMapModel;

	public BabysitterMapPresenterImpl(
			BabysitterMapActivity babysitterMapActivity) {
		mBabysitterMap = babysitterMapActivity;
		mMapModel = new BabysitterMapModelImpl(this);

		mMyLocation = new MyLocation(mBabysitterMap);
	}

	@Override
	public void onMapLoaded() {
		mBabysitterMap.showMyLocation(mMyLocation.getmBounds());
		doMapQuery();
	}

	private void doMapQuery() {
		Location myLoc = mMyLocation.getCurLocation();

		mMapModel.doMapQuery(myLoc);
	}

	@Override
	public void onFinished(List<MarkerOptions> markerOptions) {
		mBabysitterMap.showMarkers(markerOptions);
	}
	
	@Override
	public void onDataFinished(List<BabysitterOutline> outlines) {
		mBabysitterMap.AddMarkers(outlines);
	}
	
	
	@Override
	public void onOptionsItemSelected(int id) {
		if (id == R.id.action_list) {
			Intent intent = new Intent();
			intent.setClass(mBabysitterMap, BabysitterListActivity.class);
			mBabysitterMap.startActivity(intent);
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		final Intent intent = new Intent();
		intent.setClass(mBabysitterMap, BabysitterDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("objectId", marker.getTitle());
		bundle.putString("slat", String.valueOf(marker.getPosition().latitude));
		bundle.putString("slng", String.valueOf(marker.getPosition().longitude));

		bundle.putString("dlat", String.valueOf(mMyLocation.getLat()));
		bundle.putString("dlng", String.valueOf(mMyLocation.getLng()));
		
		intent.putExtras(bundle);
		mBabysitterMap.startActivity(intent);
	}


}
