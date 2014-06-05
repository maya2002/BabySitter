package tw.tasker.babysitter.presenter.adapter;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardView;
import tw.tasker.babysitter.R;
import tw.tasker.babysitter.model.data.Baby;
import tw.tasker.babysitter.model.data.Babysitter;
import tw.tasker.babysitter.model.data.Favorite;
import tw.tasker.babysitter.model.data.FavoriteBabysitter;
import tw.tasker.babysitter.presenter.adapter.FavoriteBabyParseQueryAdapter.GplayGridCard;
import tw.tasker.babysitter.presenter.adapter.FavoriteBabyParseQueryAdapter.GplayGridCard.GplayGridThumb;
import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class FavoriteBabysitterParseQueryAdapter extends
		ParseQueryAdapter<FavoriteBabysitter> {
	DisplayImageOptions options;
	private ImageLoader imageLoader = ImageLoader.getInstance();

	public FavoriteBabysitterParseQueryAdapter(Context context) {
		super(context, getQueryFactory());
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	@Override
	public View getItemView(FavoriteBabysitter favorite, View view,
			ViewGroup parent) {

		boolean recycle = false;
		if (view == null) {
			recycle = false;
			view = View.inflate(getContext(), R.layout.list_item_baby, null);
		} else {
			recycle = true;
		}

		Babysitter babysitter = favorite.getBabysitter();

		GplayGridCard mCard = new GplayGridCard(getContext());

		mCard.headerTitle = babysitter.getText();
		mCard.secondaryTitle = babysitter.getAddress();
		
		 int totalRatingValue = babysitter.getTotalRating(); 
		 int totalComementValue = babysitter.getTotalComment();

		 float rating = getRatingValue(totalRatingValue, totalComementValue);		
		
		 mCard.rating = rating;
		//mCard.resourceIdThumbnail = R.drawable.ic_launcher;
		mCard.mComment = String.valueOf(totalComementValue);
		
		String url;
		//if (babysitter.getPhotoFile() != null) {
			//url = babysitter.getPhotoFile().getUrl();
		//} else {
			url = "http://cwisweb.sfaa.gov.tw/babysitterFiles/20140315134959_0822R167.jpg";
		//}
		
		mCard.mUrl=url;

		
		
		mCard.init();

		CardView mCardView;

		// Setup card
		mCardView = (CardView) view.findViewById(R.id.list_cardId);
		if (mCardView != null) {
			// It is important to set recycle value for inner layout elements
			mCardView.setForceReplaceInnerLayout(Card.equalsInnerLayout(
					mCardView.getCard(), mCard));

			// It is important to set recycle value for performance issue
			mCardView.setRecycle(recycle);

			// Save original swipeable to prevent cardSwipeListener (listView
			// requires another cardSwipeListener)
			// boolean origianlSwipeable = mCard.isSwipeable();
			// mCard.setSwipeable(false);

			mCardView.setCard(mCard);

			// Set originalValue
			// mCard.setSwipeable(origianlSwipeable);

		}

		/*
		 * if (view == null) { view = View.inflate(getContext(),
		 * R.layout.list_item_babysitter_list, null); }
		 * 
		 * TextView babysitterName = (TextView) view
		 * .findViewById(R.id.babysitter_name); TextView babysitterAddress =
		 * (TextView) view .findViewById(R.id.babysitter_address);
		 * 
		 * ImageView babysitterImage = (ImageView) view
		 * .findViewById(R.id.babysitter_avator);
		 * 
		 * RatingBar babysitterRating = (RatingBar) view
		 * .findViewById(R.id.babysitter_rating);
		 * 
		 * TextView totalComment = (TextView)
		 * view.findViewById(R.id.totalComment);
		 * 
		 * Babysitter babysitter = favorite.getBabysitter();
		 * 
		 * babysitterName.setText(babysitter.getText());
		 * babysitterAddress.setText(babysitter.getAddress());
		 * 
		 * imageLoader .displayImage(
		 * "http://cwisweb.sfaa.gov.tw/babysitterFiles/20140315134959_0822R167.jpg"
		 * , babysitterImage, options, null);
		 * 
		 * int totalRatingValue = babysitter.getTotalRating(); int
		 * totalComementValue = babysitter.getTotalComment();
		 * 
		 * totalComment .setText("共有：" + String.valueOf(totalComementValue) +
		 * "筆評論"); float rating = getRatingValue(totalRatingValue,
		 * totalComementValue); babysitterRating.setRating(rating);
		 */
		return view;
	}

	private float getRatingValue(int totalRating, int totalComment) {
		float avgRating = 0.0f;

		if (totalComment != 0) {
			avgRating = totalRating / totalComment;
		}
		return avgRating;
	}

	private static ParseQueryAdapter.QueryFactory<FavoriteBabysitter> getQueryFactory() {
		ParseQueryAdapter.QueryFactory<FavoriteBabysitter> factory = new ParseQueryAdapter.QueryFactory<FavoriteBabysitter>() {
			public ParseQuery<FavoriteBabysitter> create() {
				ParseQuery<FavoriteBabysitter> query = FavoriteBabysitter
						.getQuery();
				query.include("user");
				query.orderByDescending("createdAt");
				query.whereEqualTo("user", ParseUser.getCurrentUser());
				query.setLimit(20);
				query.include("babysitter");
				return query;
			}
		};
		return factory;
	}

	@Override
	public View getNextPageView(View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.adapter_next_page, null);
		}

		return v;
	}

	public class GplayGridCard extends Card {

		public String mUrl;
		protected TextView mTitle;
		protected TextView mSecondaryTitle;
		protected RatingBar mRatingBar;
		protected int resourceIdThumbnail = -1;
		protected int count;

		protected String headerTitle;
		protected String secondaryTitle;
		protected float rating;
		protected String mComment;
		
		public GplayGridCard(Context context) {
			super(context, R.layout.carddemo_gplay_inner_content);
		}

		public GplayGridCard(Context context, int innerLayout) {
			super(context, innerLayout);
		}

		private void init() {
			CardHeader header = new CardHeader(getContext());
			header.setButtonOverflowVisible(true);
			header.setTitle(headerTitle);
			header.setPopupMenu(R.menu.popupmain,
					new CardHeader.OnClickCardHeaderPopupMenuListener() {
						@Override
						public void onMenuItemClick(BaseCard card, MenuItem item) {
							Toast.makeText(getContext(),
									"Item " + item.getTitle(),
									Toast.LENGTH_SHORT).show();
						}
					});

			addCardHeader(header);

			GplayGridThumb thumbnail = new GplayGridThumb(getContext());
		
/*			if (resourceIdThumbnail > -1)
				thumbnail.setDrawableResource(resourceIdThumbnail);
			else
				thumbnail.setDrawableResource(R.drawable.ic_launcher);
*/
			addCardThumbnail(thumbnail);

			setOnClickListener(new OnCardClickListener() {
				@Override
				public void onClick(Card card, View view) {
					Toast.makeText(getContext(), "你點是的：" + card.getTitle(),
							Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void setupInnerViewElements(ViewGroup parent, View view) {

			TextView title = (TextView) view
					.findViewById(R.id.carddemo_gplay_main_inner_title);
			title.setText("評論"+mComment);

			TextView subtitle = (TextView) view
					.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
			subtitle.setText(secondaryTitle);

			RatingBar mRatingBar = (RatingBar) parent
					.findViewById(R.id.carddemo_gplay_main_inner_ratingBar);

			mRatingBar.setNumStars(5);
			mRatingBar.setMax(5);
			mRatingBar.setStepSize(0.5f);
			mRatingBar.setRating(rating);
		}

		class GplayGridThumb extends CardThumbnail {

			public GplayGridThumb(Context context) {
				super(context);
			}

			@Override
			public void setupInnerViewElements(ViewGroup parent, View viewImage) {

        		imageLoader.displayImage(mUrl, (ImageView) viewImage, options, null);

				// viewImage.getLayoutParams().width = 196;
				// viewImage.getLayoutParams().height = 196;

			}
		}

	}

}
