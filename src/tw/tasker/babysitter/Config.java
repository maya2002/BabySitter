package tw.tasker.babysitter;

import java.util.List;

import tw.tasker.babysitter.model.data.Babysitter;
import tw.tasker.babysitter.model.data.BabysitterFavorite;
import tw.tasker.babysitter.model.data.Sitter;
import tw.tasker.babysitter.model.data.UserInfo;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.parse.ParseGeoPoint;

public class Config {
	public static final String BABYSITTER_OBJECT_ID = "babysitterObjectId";
	public static final String BABY_OBJECT_ID = "babyObjectId";
	public static final String TOTAL_RATING = "totalRating";
	public static final String TOTAL_COMMENT = "totalComent";
	
	public static final String TOTAL_RECORD = "totalRecord";
	public static String keyWord = "";
	
	public static final int OBJECTS_PER_PAGE = 20;

	public static final DisplayImageOptions OPTIONS 
	= new DisplayImageOptions.Builder()
	.cacheInMemory(true)
	.cacheOnDisc(true)
	.displayer(new SimpleBitmapDisplayer())
	.showImageOnFail(R.drawable.profile)
	.showImageOnLoading(R.drawable.profile)
	.build();
	
	public static final double LAT = 22.885127;
	public static final double LNG = 120.589881;
	
	// if we can't get the user location.
	public static ParseGeoPoint MY_LOCATION = new ParseGeoPoint(LAT, LNG);
	//public static ParseGeoPoint MY_TEST_LOCATION = new ParseGeoPoint(25.0601727,121.5593073);
	
	public static final int MAX_POST_SEARCH_DISTANCE = 50;
	public static Babysitter sitterInfo;
	public static UserInfo userInfo;
	public static List<BabysitterFavorite> favorites;
	//public static Sitter tmpSiterInfo;
	
	// profile pages switch
	public static final int PARENT_READ_PAGE = 0;
	public static final int PARENT_EDIT_PAGE = 1;
	public static final int SITTER_READ_PAGE = 2;
	public static final int SITTER_EDIT_PAGE = 3;
}
