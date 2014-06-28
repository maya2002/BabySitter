package tw.tasker.babysitter.model.data;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Babysitter")
public class Babysitter extends ParseObject {
	private float mDistance;
	
	public String getImageUrl() {
		String value = getString("imageUrl");
		return value;
	}
	public void setImageUrl(String value) {
		put("imageUrl", value);
	}

	public String getName() {
		String value = getString("name");
		return value;
	}
	public void setName(String value) {
		put("name", value);
	}

	public String getSex() {
		String value = getString("sex");
		return value;
	}
	public void setSex(String value) {
		put("sex", value);
	}

	public String getAge() {
		String value = getString("age");
		return value;
	}
	public void setAge(String value) {
		put("age", value);
	}

	public String getEducation() {
		String value = getString("education");
		return value;
	}
	public void setEducation(String value) {
		put("education", value);
	}
	
	public String getTel() {
		String value = getString("tel");
		return value;
	}
	public void setTel(String value) {
		put("tel", value);
	}

	public String getAddress() {
		String value = getString("address");
		return value;
	}
	public void setAddress(String value) {
		put("address", value);
	}

	public ParseGeoPoint getLocation() {
		return getParseGeoPoint("location");
	}
	public void setLocation(ParseGeoPoint value) {
		put("location", value);
	}

	public String getBabycareCount() {
		String value = getString("babycareCount");
		return value;
	}
	public void setBabycareCount(String value) {
		put("babycareCount", value);
	}

	public String getBabycareTime() {
		String value = getString("babycareTime");
		return value;
	}
	public void setBabycareTime(String value) {
		put("babycareTime", value);
	}
	
	public float getTotalRating() {
		Number rating = getNumber("totalRating");
		float value = rating.floatValue();
		return value;
	}
	public void setTotalRating(float value) {
		put("totalRating", value);
	}
	
	public int getTotalComment() {
		int value = getInt("totalComment");
		return value;
	}
	public void setTotalComment(int value) {
		put("totalComment", value);
	}

	public ParseUser getUser() {
		return getParseUser("user");
	}
	public void setUser(ParseUser value) {
		put("user", value);
	}
	
	public float getDistance() {
		return mDistance;
	}
	public void setDistance(float distance) {
		mDistance = distance;
	}
	
	public int getTotalFavorite() {
		return getInt("totalFavorite");
	}
	public void setTotalFavorite(int value) {
		put("totalFavorite", value);
	}

	public static ParseQuery<Babysitter> getQuery() {
		return ParseQuery.getQuery(Babysitter.class);
	}
}
