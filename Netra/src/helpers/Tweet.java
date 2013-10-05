/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.util.Date;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/**
 *
 * @author manthanhd
 */
public class Tweet implements Status{

    private Date createdAt;
    private long id, inReplyToUserId, inReplyToStatusId, currentUserRetweetId;
    private String text, source, inReplyToScreenName;
    private boolean truncated, favorited, retweet, retweetedByMe, possiblySensitive;
    private GeoLocation geoLocation;
    private Place place;
    private User user;
    private Status retweetedStatus;
    private long[] contributors;
    private int retweetCount, accessLevel;
    private RateLimitStatus rateLimitStatus;
    private UserMentionEntity[] userMentionEntities;
    private URLEntity[] urlEntities;
    private HashtagEntity[] hashtagEntities;
    private MediaEntity[] mediaEntities;

    public Tweet(Date createdAt, long id, long inReplyToUserId, long inReplyToStatusId, long currentUserRetweetId, String text, String source, String inReplyToScreenName, boolean truncated, boolean favorited, boolean retweet, boolean retweetedByMe, boolean possiblySensitive, GeoLocation geoLocation, Place place, User user, Status retweetedStatus, long[] contributors, int retweetCount, int accessLevel, RateLimitStatus rateLimitStatus, UserMentionEntity[] userMentionEntities, URLEntity[] urlEntities, HashtagEntity[] hashtagEntities, MediaEntity[] mediaEntities) {
        this.createdAt = createdAt;
        this.id = id;
        this.inReplyToUserId = inReplyToUserId;
        this.inReplyToStatusId = inReplyToStatusId;
        this.currentUserRetweetId = currentUserRetweetId;
        this.text = text;
        this.source = source;
        this.inReplyToScreenName = inReplyToScreenName;
        this.truncated = truncated;
        this.favorited = favorited;
        this.retweet = retweet;
        this.retweetedByMe = retweetedByMe;
        this.possiblySensitive = possiblySensitive;
        this.geoLocation = geoLocation;
        this.place = place;
        this.user = user;
        this.retweetedStatus = retweetedStatus;
        this.contributors = contributors;
        this.retweetCount = retweetCount;
        this.accessLevel = accessLevel;
        this.rateLimitStatus = rateLimitStatus;
        this.userMentionEntities = userMentionEntities;
        this.urlEntities = urlEntities;
        this.hashtagEntities = hashtagEntities;
        this.mediaEntities = mediaEntities;
    }
    
    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public boolean isTruncated() {
        return truncated;
    }

    @Override
    public long getInReplyToStatusId() {
        return inReplyToStatusId;
    }

    @Override
    public long getInReplyToUserId() {
        return inReplyToUserId;
    }

    @Override
    public String getInReplyToScreenName() {
        return inReplyToScreenName;
    }

    @Override
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    @Override
    public Place getPlace() {
        return place;
    }

    @Override
    public boolean isFavorited() {
        return favorited;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean isRetweet() {
        return retweet;
    }

    @Override
    public Status getRetweetedStatus() {
        return retweetedStatus;
    }

    @Override
    public long[] getContributors() {
        return contributors;
    }

    @Override
    public long getRetweetCount() {
        return retweetCount;
    }

    @Override
    public boolean isRetweetedByMe() {
        return retweetedByMe;
    }

    @Override
    public long getCurrentUserRetweetId() {
        return currentUserRetweetId;
    }

    @Override
    public boolean isPossiblySensitive() {
        return possiblySensitive;
    }

    @Override
    public int compareTo(Status o) {
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

    @Override
    public UserMentionEntity[] getUserMentionEntities() {
        return userMentionEntities;
    }

    @Override
    public URLEntity[] getURLEntities() {
        return urlEntities;
    }

    @Override
    public HashtagEntity[] getHashtagEntities() {
        return hashtagEntities;
    }

    @Override
    public MediaEntity[] getMediaEntities() {
        return mediaEntities;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @param inReplyToUserId the inReplyToUserId to set
     */
    public void setInReplyToUserId(long inReplyToUserId) {
        this.inReplyToUserId = inReplyToUserId;
    }

    /**
     * @param inReplyToStatusId the inReplyToStatusId to set
     */
    public void setInReplyToStatusId(long inReplyToStatusId) {
        this.inReplyToStatusId = inReplyToStatusId;
    }

    /**
     * @param currentUserRetweetId the currentUserRetweetId to set
     */
    public void setCurrentUserRetweetId(long currentUserRetweetId) {
        this.currentUserRetweetId = currentUserRetweetId;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @param inReplyToScreenName the inReplyToScreenName to set
     */
    public void setInReplyToScreenName(String inReplyToScreenName) {
        this.inReplyToScreenName = inReplyToScreenName;
    }

    /**
     * @param truncated the truncated to set
     */
    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    /**
     * @param favorited the favorited to set
     */
    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    /**
     * @param retweet the retweet to set
     */
    public void setRetweet(boolean retweet) {
        this.retweet = retweet;
    }

    /**
     * @param retweetedByMe the retweetedByMe to set
     */
    public void setRetweetedByMe(boolean retweetedByMe) {
        this.retweetedByMe = retweetedByMe;
    }

    /**
     * @param possiblySensitive the possiblySensitive to set
     */
    public void setPossiblySensitive(boolean possiblySensitive) {
        this.possiblySensitive = possiblySensitive;
    }

    /**
     * @param geoLocation the geoLocation to set
     */
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param retweetedStatus the retweetedStatus to set
     */
    public void setRetweetedStatus(Status retweetedStatus) {
        this.retweetedStatus = retweetedStatus;
    }

    /**
     * @param contributors the contributors to set
     */
    public void setContributors(long[] contributors) {
        this.contributors = contributors;
    }

    /**
     * @param retweetCount the retweetCount to set
     */
    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    /**
     * @param accessLevel the accessLevel to set
     */
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * @param rateLimitStatus the rateLimitStatus to set
     */
    public void setRateLimitStatus(RateLimitStatus rateLimitStatus) {
        this.rateLimitStatus = rateLimitStatus;
    }

    /**
     * @param userMentionEntities the userMentionEntities to set
     */
    public void setUserMentionEntities(UserMentionEntity[] userMentionEntities) {
        this.userMentionEntities = userMentionEntities;
    }

    /**
     * @param urlEntities the urlEntities to set
     */
    public void setUrlEntities(URLEntity[] urlEntities) {
        this.urlEntities = urlEntities;
    }

    /**
     * @param hashtagEntities the hashtagEntities to set
     */
    public void setHashtagEntities(HashtagEntity[] hashtagEntities) {
        this.hashtagEntities = hashtagEntities;
    }

    /**
     * @param mediaEntities the mediaEntities to set
     */
    public void setMediaEntities(MediaEntity[] mediaEntities) {
        this.mediaEntities = mediaEntities;
    }
    
}
