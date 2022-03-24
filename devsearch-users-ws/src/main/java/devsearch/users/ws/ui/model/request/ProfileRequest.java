package devsearch.users.ws.ui.model.request;

public class ProfileRequest {

    private String profileId;
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String contactEmail;
    private String shortIntro;
    private String about;
    private String socialLinkedIn;
    private String socialTwitter;
    private String socialGithub;
    private String socialYoutube;
    private String socialWebsite;
    private String locationCity;
    private String locationCountry;
    private String profilePictureBase64;
    private boolean newProfilePictureUpload;

    public String getProfileId() {
	return profileId;
    }

    public void setProfileId(String profileId) {
	this.profileId = profileId;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getContactEmail() {
	return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
	this.contactEmail = contactEmail;
    }

    public String getShortIntro() {
	return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
	this.shortIntro = shortIntro;
    }

    public String getAbout() {
	return about;
    }

    public void setAbout(String about) {
	this.about = about;
    }

    public String getSocialLinkedIn() {
	return socialLinkedIn;
    }

    public void setSocialLinkedIn(String socialLinkedIn) {
	this.socialLinkedIn = socialLinkedIn;
    }

    public String getSocialTwitter() {
	return socialTwitter;
    }

    public void setSocialTwitter(String socialTwitter) {
	this.socialTwitter = socialTwitter;
    }

    public String getSocialGithub() {
	return socialGithub;
    }

    public void setSocialGithub(String socialGithub) {
	this.socialGithub = socialGithub;
    }

    public String getSocialYoutube() {
	return socialYoutube;
    }

    public void setSocialYoutube(String socialYoutube) {
	this.socialYoutube = socialYoutube;
    }

    public String getSocialWebsite() {
	return socialWebsite;
    }

    public void setSocialWebsite(String socialWebsite) {
	this.socialWebsite = socialWebsite;
    }

    public String getLocationCity() {
	return locationCity;
    }

    public void setLocationCity(String locationCity) {
	this.locationCity = locationCity;
    }

    public String getLocationCountry() {
	return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
	this.locationCountry = locationCountry;
    }

    public String getProfilePictureBase64() {
	return profilePictureBase64;
    }

    public void setProfilePictureBase64(String profilePictureBase64) {
	this.profilePictureBase64 = profilePictureBase64;
    }

    public boolean getNewProfilePictureUpload() {
	return newProfilePictureUpload;
    }

    public void setNewProfilePictureUpload(boolean newProfilePictureUpload) {
	this.newProfilePictureUpload = newProfilePictureUpload;
    }
}
