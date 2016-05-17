package fi.danielsan.donkino.data.api.models.events;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.threeten.bp.LocalDateTime;

import java.util.List;

import fi.danielsan.donkino.data.api.models.base.Images;

@Root(name = "Event", strict = false)
public class Event {

    @Element(name = "ID")
    private int id;

    @Element(name = "Title")
    private String title;

    @Element(name = "OriginalTitle")
    private String originalTitle;

    @Element(name = "LengthInMinutes")
    private String lengthInMinutes;

    @Element(name = "Genres")
    private String genres;

    @Element(name = "ProductionYear")
    private String productionYear;

    @Element(name ="dtLocalRelease")
    private LocalDateTime localRelease;

    @Element(name = "Rating")
    private String rating;

    @Element(name = "Images")
    private Images images;

    @Element(name = "EventType")
    private String eventType;

    @Element(name = "ShortSynopsis", required = false)
    private String shortSynopsis;

    @Element(name = "Synopsis", required = false)
    private String synopsis;

    @Element(name = "EventURL")
    private String eventUrl;

    boolean nowInTheaters;

    boolean comingSoon;

    @ElementList(name = "Gallery")
    private List<GalleryImage> galleryImages;

    @Element(name = "Videos")
    private Videos videos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(String lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public LocalDateTime getLocalRelease() {
        return localRelease;
    }

    public void setLocalRelease(LocalDateTime localRelease) {
        this.localRelease = localRelease;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getShortSynopsis() {
        return shortSynopsis;
    }

    public void setShortSynopsis(String shortSynopsis) {
        this.shortSynopsis = shortSynopsis;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public boolean isNowInTheaters() {
        return nowInTheaters;
    }

    public void setNowInTheaters(boolean nowInTheaters) {
        this.nowInTheaters = nowInTheaters;
    }

    public boolean isComingSoon() {
        return comingSoon;
    }

    public void setComingSoon(boolean comingSoon) {
        this.comingSoon = comingSoon;
    }

    public List<GalleryImage> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<GalleryImage> galleryImages) {
        this.galleryImages = galleryImages;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

}