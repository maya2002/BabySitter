package tw.tasker.babysitter;

import tw.tasker.babysitter.model.Baby;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseQueryAdapter.QueryFactory;

public class BabyDiaryActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baby_diary);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.baby_diary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		private ParseQueryAdapter<Baby> mAdapter;
		DisplayImageOptions options;
		private ImageLoader imageLoader = ImageLoader.getInstance();
		private ListView mList;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_baby_list,
					container, false);
			mList = (ListView) rootView.findViewById(R.id.list);
			return rootView;
		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			super.onViewCreated(view, savedInstanceState);
			doListQuery();
		}

		public void doListQuery() {

			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_launcher)
					.showImageForEmptyUri(R.drawable.ic_launcher)
					.showImageOnFail(R.drawable.ic_launcher)
					.cacheInMemory(true).cacheOnDisc(true)
					.considerExifParams(true)
					.displayer(new RoundedBitmapDisplayer(20)).build();

			ParseQueryAdapter.QueryFactory<Baby> factory = getQueryFactory();

			mAdapter = getParseQueryAdapter(factory);

			// Disable automatic loading when the list_item_babysitter_comment
			// is
			// attached to a view.
			mAdapter.setAutoload(false);

			// Disable pagination, we'll manage the query limit ourselves
			mAdapter.setPaginationEnabled(false);

			mList.setAdapter(mAdapter);

			mAdapter.loadObjects();
		}

		private ParseQueryAdapter.QueryFactory<Baby> getQueryFactory() {
			// Set up a customized query
			ParseQueryAdapter.QueryFactory<Baby> factory = new ParseQueryAdapter.QueryFactory<Baby>() {
				public ParseQuery<Baby> create() {
					// Location myLoc = (currentLocation == null) ? lastLocation
					// :
					// currentLocation;
					ParseQuery<Baby> query = Baby.getQuery();
					// query.include("user");
					query.orderByDescending("createdAt");
					// query.whereWithinKilometers("location",
					// geoPointFromLocation(myLoc), radius * METERS_PER_FEET /
					// METERS_PER_KILOMETER);
					query.setLimit(20);
					query.include("baby");
					return query;
				}
			};
			return factory;
		}

		private ParseQueryAdapter<Baby> getParseQueryAdapter(
				QueryFactory<Baby> factory) {
			ParseQueryAdapter<Baby> adapter;
			// Set up the query list_item_babysitter_comment
			adapter = new ParseQueryAdapter<Baby>(getActivity(), factory) {
				@Override
				public View getItemView(Baby baby, View view,
						ViewGroup parent) {
					if (view == null) {
						view = View.inflate(getContext(),
								R.layout.list_item_babysitter_list, null);
					}
					TextView babysitterName = (TextView) view
							.findViewById(R.id.babysitter_name);
					TextView babysitterAddress = (TextView) view
							.findViewById(R.id.babysitter_address);

					ImageView babysitterImage = (ImageView) view
							.findViewById(R.id.babysitter_avator);

					babysitterName.setText(baby.getName());
					babysitterAddress.setText(baby.getNote());
					imageLoader
							.displayImage(
									"https://fbcdn-sphotos-d-a.akamaihd.net/hphotos-ak-ash3/t1.0-9/q77/s720x720/1966891_782022338479354_124097698_n.jpg",
									babysitterImage, options, null);

					return view;
				}

			};
			return adapter;
		}

	}
}
