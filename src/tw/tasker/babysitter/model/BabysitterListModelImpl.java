package tw.tasker.babysitter.model;

import java.util.HashMap;
import java.util.Map;

import tw.tasker.babysitter.R;
import tw.tasker.babysitter.dummy.DummyContent.DummyItem;
import tw.tasker.babysitter.presenter.BabysitterListPresenterImpl;
import tw.tasker.babysitter.view.BabysitterDetailFragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseQueryAdapter.QueryFactory;

public class BabysitterListModelImpl implements BabysitterListModel {

	// Maximum results returned from a Parse query
	private static final int MAX_POST_SEARCH_RESULTS = 20;
	private BabysitterListPresenterImpl mBabysitterListPresenterImpl;
	private Context mContext;
	private ParseQueryAdapter<BabysitterOutline> mAdapter;
		
	public BabysitterListModelImpl(
			BabysitterListPresenterImpl babysitterListPresenterImpl,
			Context applicationContext) {
		mBabysitterListPresenterImpl = babysitterListPresenterImpl;
		mContext =applicationContext;
	}

	@Override
	public void doListQuery() {
		ParseQueryAdapter.QueryFactory<BabysitterOutline> factory = getQueryFactory();

		mAdapter = getParseQueryAdapter(factory);

		// Disable automatic loading when the list_item_babysitter_comment is
		// attached to a view.
		mAdapter.setAutoload(false);

		// Disable pagination, we'll manage the query limit ourselves
		mAdapter.setPaginationEnabled(false);
		
		mBabysitterListPresenterImpl.setAdapter(mAdapter);
		
		mAdapter.loadObjects();
	}
	
	private ParseQueryAdapter.QueryFactory<BabysitterOutline> getQueryFactory() {
		// Set up a customized query
		ParseQueryAdapter.QueryFactory<BabysitterOutline> factory = new ParseQueryAdapter.QueryFactory<BabysitterOutline>() {
			public ParseQuery<BabysitterOutline> create() {
				// Location myLoc = (currentLocation == null) ? lastLocation :
				// currentLocation;
				ParseQuery<BabysitterOutline> query = BabysitterOutline
						.getQuery();
				// query.include("user");
				query.orderByDescending("createdAt");
				// query.whereWithinKilometers("location",
				// geoPointFromLocation(myLoc), radius * METERS_PER_FEET /
				// METERS_PER_KILOMETER);
				query.setLimit(MAX_POST_SEARCH_RESULTS);
				return query;
			}
		};
		return factory;
	}
	
	private ParseQueryAdapter<BabysitterOutline> getParseQueryAdapter(
			QueryFactory<BabysitterOutline> factory) {
		ParseQueryAdapter<BabysitterOutline> adapter;
		// Set up the query list_item_babysitter_comment
		adapter = new ParseQueryAdapter<BabysitterOutline>(mContext, factory) {
			@Override
			public View getItemView(BabysitterOutline post, View view,
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

				babysitterName.setText(post.getText());
				babysitterAddress.setText(post.getAddress());
				babysitterImage.setBackgroundResource(R.drawable.ic_launcher);

				return view;
			}
		};
		return adapter;
	}

	@Override
	public BabysitterOutline getOutline(int position) {
		return mAdapter.getItem(position);
	}
	
	
	

}