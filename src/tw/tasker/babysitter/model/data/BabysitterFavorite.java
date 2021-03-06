package tw.tasker.babysitter.model.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("BabysitterFavorite")
public class BabysitterFavorite extends ParseObject {

	public ParseUser getUser() {
		return getParseUser("user");
	}
	
	public void setUser(ParseUser user) {
		put("user", user);
	}
	
	public Babysitter getBabysitter() {
		return (Babysitter) getParseObject("Babysitter");
	}
	
	public void setBabysitter(Babysitter babysitter) {
		put("Babysitter", babysitter);
	}
	
	public void setIsParentConfirm(Boolean value) {
		put("isParentConfirm", value);
	}
	
	public Boolean getIsParentConfirm() {
		return getBoolean("isParentConfirm");
	}

	public void setIsSitterConfirm(Boolean value) {
		put("isSitterConfirm", value);
	}
	
	public Boolean getIsSitterConfirm() {
		return getBoolean("isSitterConfirm");
	}

	public static ParseQuery<BabysitterFavorite> getQuery() {
		return ParseQuery.getQuery(BabysitterFavorite.class);
	}

	public void setUserInfo(UserInfo userInfo) {
		put("UserInfo", userInfo);
	}
	public UserInfo getUserInfo() {
		return (UserInfo) getParseObject("UserInfo");
	}

	public void setConversationId(String value) {
		put("conversationId", value);
	}
	public String getConversationId() {
		return getString("conversationId");
	}
}
