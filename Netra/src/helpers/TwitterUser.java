/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.sun.xml.internal.bind.v2.model.core.ID;
import java.net.URL;
import java.util.Date;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;

/**
 *
 * @author manthanhd
 */
public class TwitterUser implements User {

    private long id;
    private String name;
    private String screenName;
    private String location;
    private String description;
    private boolean contributorsEnabled;
    private String profileImageURL;
    private String biggerProfileImageURL;
    private String miniProfileImageURL;
    private String originalProfileImageURL;
    private URL profileImageUrlHttps;
    private String profileImageURLHttps;
    private String biggerProfileImageURLHttps;
    private String miniProfileImageURLHttps;
    private String originalProfileImageURLHttps;
    private String URL;
    private boolean Protected;
    private int followersCount;
    private Status status;
    private String profileBackgroundColor;
    private String profileTextColor;
    private String profileLinkColor;
    private String profileSidebarFillColor;
    private String profileSidebarBorderColor;
    private boolean profileUseBackgroundImage;
    private boolean showAllInlineMedia;
    private int friendsCount;
    private Date createdAt;
    private int favoritesCount;
    private int utcOffset;
    private String timeZone;
    private String profileBackgroundImageUrl;
    private String profileBackgroundImageURL;
    private String profileBackgroundImageUrlHttps;
    private String profileBannerURL;
    private String profileBannerRetinaURL;
    private String profileBannerIPadURL;
    private String profileBannerIPadRetinaURL;
    private String profileBannerMobileURL;
    private String profileBannerMobileRetinaURL;
    private boolean profileBackgroundTiled;
    private String lang;
    private int statusesCount;
    private boolean geoEnabled;
    private boolean verified;
    private boolean translator;
    private int listedCount;
    private boolean followRequestSent;
    private URLEntity[] descriptionURLEntities;
    private URLEntity URLEntity;
    private RateLimitStatus rateLimitStatus;
    private int accessLevel;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isContributorsEnabled() {
        return contributorsEnabled;
    }

    @Override
    public String getProfileImageURL() {
        return profileImageURL;
    }

    @Override
    public String getBiggerProfileImageURL() {
        return biggerProfileImageURL;
    }

    @Override
    public String getMiniProfileImageURL() {
        return miniProfileImageURL;
    }

    @Override
    public String getOriginalProfileImageURL() {
        return originalProfileImageURL;
    }

    @Override
    public URL getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

    @Override
    public String getProfileImageURLHttps() {
        return profileImageURLHttps;
    }

    @Override
    public String getBiggerProfileImageURLHttps() {
        return biggerProfileImageURLHttps;
    }

    @Override
    public String getMiniProfileImageURLHttps() {
        return miniProfileImageURLHttps;
    }

    @Override
    public String getOriginalProfileImageURLHttps() {
        return originalProfileImageURLHttps;
    }

    @Override
    public String getURL() {
        return URL;
    }

    @Override
    public boolean isProtected() {
        return Protected;
    }

    @Override
    public int getFollowersCount() {
        return followersCount;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    @Override
    public String getProfileTextColor() {
        return profileTextColor;
    }

    @Override
    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    @Override
    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }

    @Override
    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }

    @Override
    public boolean isProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }

    @Override
    public boolean isShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    @Override
    public int getFriendsCount() {
        return friendsCount;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public int getFavouritesCount() {
        return favoritesCount;
    }

    @Override
    public int getUtcOffset() {
        return utcOffset;
    }

    @Override
    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    @Override
    public String getProfileBackgroundImageURL() {
        return profileBackgroundImageURL;
    }

    @Override
    public String getProfileBackgroundImageUrlHttps() {
        return profileBackgroundImageUrlHttps;
    }

    @Override
    public String getProfileBannerURL() {
        return profileBannerURL;
    }

    @Override
    public String getProfileBannerRetinaURL() {
        return profileBannerRetinaURL;
    }

    @Override
    public String getProfileBannerIPadURL() {
        return profileBannerIPadURL;
    }

    @Override
    public String getProfileBannerIPadRetinaURL() {
        return profileBannerIPadRetinaURL;
    }

    @Override
    public String getProfileBannerMobileURL() {
        return profileBannerMobileURL;
    }

    @Override
    public String getProfileBannerMobileRetinaURL() {
        return profileBannerMobileRetinaURL;
    }

    @Override
    public boolean isProfileBackgroundTiled() {
        return profileBackgroundTiled;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public int getStatusesCount() {
        return statusesCount;
    }

    @Override
    public boolean isGeoEnabled() {
        return geoEnabled;
    }

    @Override
    public boolean isVerified() {
        return verified;
    }

    @Override
    public boolean isTranslator() {
        return translator;
    }

    @Override
    public int getListedCount() {
        return listedCount;
    }

    @Override
    public boolean isFollowRequestSent() {
        return followRequestSent;
    }

    @Override
    public URLEntity[] getDescriptionURLEntities() {
        return descriptionURLEntities;
    }

    @Override
    public URLEntity getURLEntity() {
        return URLEntity;
    }

    @Override
    public int compareTo(User o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    @Override
    public int getAccessLevel() {
        return accessLevel;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param screenName the screenName to set
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param contributorsEnabled the contributorsEnabled to set
     */
    public void setContributorsEnabled(boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    /**
     * @param profileImageURL the profileImageURL to set
     */
    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    /**
     * @param biggerProfileImageURL the biggerProfileImageURL to set
     */
    public void setBiggerProfileImageURL(String biggerProfileImageURL) {
        this.biggerProfileImageURL = biggerProfileImageURL;
    }

    /**
     * @param miniProfileImageURL the miniProfileImageURL to set
     */
    public void setMiniProfileImageURL(String miniProfileImageURL) {
        this.miniProfileImageURL = miniProfileImageURL;
    }

    /**
     * @param originalProfileImageURL the originalProfileImageURL to set
     */
    public void setOriginalProfileImageURL(String originalProfileImageURL) {
        this.originalProfileImageURL = originalProfileImageURL;
    }

    /**
     * @param profileImageUrlHttps the profileImageUrlHttps to set
     */
    public void setProfileImageUrlHttps(URL profileImageUrlHttps) {
        this.profileImageUrlHttps = profileImageUrlHttps;
    }

    /**
     * @param profileImageURLHttps the profileImageURLHttps to set
     */
    public void setProfileImageURLHttps(String profileImageURLHttps) {
        this.profileImageURLHttps = profileImageURLHttps;
    }

    /**
     * @param biggerProfileImageURLHttps the biggerProfileImageURLHttps to set
     */
    public void setBiggerProfileImageURLHttps(String biggerProfileImageURLHttps) {
        this.biggerProfileImageURLHttps = biggerProfileImageURLHttps;
    }

    /**
     * @param miniProfileImageURLHttps the miniProfileImageURLHttps to set
     */
    public void setMiniProfileImageURLHttps(String miniProfileImageURLHttps) {
        this.miniProfileImageURLHttps = miniProfileImageURLHttps;
    }

    /**
     * @param originalProfileImageURLHttps the originalProfileImageURLHttps to set
     */
    public void setOriginalProfileImageURLHttps(String originalProfileImageURLHttps) {
        this.originalProfileImageURLHttps = originalProfileImageURLHttps;
    }

    /**
     * @param URL the URL to set
     */
    public void setURL(String URL) {
        this.URL = URL;
    }

    /**
     * @param Protected the Protected to set
     */
    public void setProtected(boolean Protected) {
        this.Protected = Protected;
    }

    /**
     * @param followersCount the followersCount to set
     */
    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @param profileBackgroundColor the profileBackgroundColor to set
     */
    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    /**
     * @param profileTextColor the profileTextColor to set
     */
    public void setProfileTextColor(String profileTextColor) {
        this.profileTextColor = profileTextColor;
    }

    /**
     * @param profileLinkColor the profileLinkColor to set
     */
    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }

    /**
     * @param profileSidebarFillColor the profileSidebarFillColor to set
     */
    public void setProfileSidebarFillColor(String profileSidebarFillColor) {
        this.profileSidebarFillColor = profileSidebarFillColor;
    }

    /**
     * @param profileSidebarBorderColor the profileSidebarBorderColor to set
     */
    public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
        this.profileSidebarBorderColor = profileSidebarBorderColor;
    }

    /**
     * @param profileUseBackgroundImage the profileUseBackgroundImage to set
     */
    public void setProfileUseBackgroundImage(boolean profileUseBackgroundImage) {
        this.profileUseBackgroundImage = profileUseBackgroundImage;
    }

    /**
     * @param showAllInlineMedia the showAllInlineMedia to set
     */
    public void setShowAllInlineMedia(boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }

    /**
     * @param friendsCount the friendsCount to set
     */
    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @param favoritesCount the favoritesCount to set
     */
    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    /**
     * @param utcOffset the utcOffset to set
     */
    public void setUtcOffset(int utcOffset) {
        this.utcOffset = utcOffset;
    }

    /**
     * @param timeZone the timeZone to set
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    /**
     * @param profileBackgroundImageUrl the profileBackgroundImageUrl to set
     */
    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    /**
     * @param profileBackgroundImageURL the profileBackgroundImageURL to set
     */
    public void setProfileBackgroundImageURL(String profileBackgroundImageURL) {
        this.profileBackgroundImageURL = profileBackgroundImageURL;
    }

    /**
     * @param profileBackgroundImageUrlHttps the profileBackgroundImageUrlHttps to set
     */
    public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
        this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
    }

    /**
     * @param profileBannerURL the profileBannerURL to set
     */
    public void setProfileBannerURL(String profileBannerURL) {
        this.profileBannerURL = profileBannerURL;
    }

    /**
     * @param profileBannerRetinaURL the profileBannerRetinaURL to set
     */
    public void setProfileBannerRetinaURL(String profileBannerRetinaURL) {
        this.profileBannerRetinaURL = profileBannerRetinaURL;
    }

    /**
     * @param profileBannerIPadURL the profileBannerIPadURL to set
     */
    public void setProfileBannerIPadURL(String profileBannerIPadURL) {
        this.profileBannerIPadURL = profileBannerIPadURL;
    }

    /**
     * @param profileBannerIPadRetinaURL the profileBannerIPadRetinaURL to set
     */
    public void setProfileBannerIPadRetinaURL(String profileBannerIPadRetinaURL) {
        this.profileBannerIPadRetinaURL = profileBannerIPadRetinaURL;
    }

    /**
     * @param profileBannerMobileURL the profileBannerMobileURL to set
     */
    public void setProfileBannerMobileURL(String profileBannerMobileURL) {
        this.profileBannerMobileURL = profileBannerMobileURL;
    }

    /**
     * @param profileBannerMobileRetinaURL the profileBannerMobileRetinaURL to set
     */
    public void setProfileBannerMobileRetinaURL(String profileBannerMobileRetinaURL) {
        this.profileBannerMobileRetinaURL = profileBannerMobileRetinaURL;
    }

    /**
     * @param profileBackgroundTiled the profileBackgroundTiled to set
     */
    public void setProfileBackgroundTiled(boolean profileBackgroundTiled) {
        this.profileBackgroundTiled = profileBackgroundTiled;
    }

    /**
     * @param lang the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @param statusesCount the statusesCount to set
     */
    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }

    /**
     * @param geoEnabled the geoEnabled to set
     */
    public void setGeoEnabled(boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    /**
     * @param translator the translator to set
     */
    public void setTranslator(boolean translator) {
        this.translator = translator;
    }

    /**
     * @param listedCount the listedCount to set
     */
    public void setListedCount(int listedCount) {
        this.listedCount = listedCount;
    }

    /**
     * @param followRequestSent the followRequestSent to set
     */
    public void setFollowRequestSent(boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
    }

    /**
     * @param descriptionURLEntities the descriptionURLEntities to set
     */
    public void setDescriptionURLEntities(URLEntity[] descriptionURLEntities) {
        this.setDescriptionURLEntities(descriptionURLEntities);
    }

    /**
     * @param URLEntity the URLEntity to set
     */
    public void setURLEntity(URLEntity URLEntity) {
        this.URLEntity = URLEntity;
    }

    /**
     * @param rateLimitStatus the rateLimitStatus to set
     */
    public void setRateLimitStatus(RateLimitStatus rateLimitStatus) {
        this.rateLimitStatus = rateLimitStatus;
    }

    /**
     * @param accessLevel the accessLevel to set
     */
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
