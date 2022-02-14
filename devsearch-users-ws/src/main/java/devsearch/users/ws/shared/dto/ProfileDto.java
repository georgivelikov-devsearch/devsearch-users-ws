package devsearch.users.ws.shared.dto;

import java.util.Collection;

public class ProfileDto {

    private String displayName;
    private String shortIntro;
    private String bio;
    private String socialLinkedIn;
    private String socialTwitter;
    private String socialGithub;
    private String socialYoutube;
    private String socialWebsite;
    private UserDto user;

    private Collection<AddressDto> addresses;

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public String getShortIntro() {
	return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
	this.shortIntro = shortIntro;
    }

    public String getBio() {
	return bio;
    }

    public void setBio(String bio) {
	this.bio = bio;
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

    public UserDto getUser() {
	return user;
    }

    public void setUser(UserDto user) {
	this.user = user;
    }

    public Collection<AddressDto> getAddresses() {
	return addresses;
    }

    public void setAddresses(Collection<AddressDto> addresses) {
	this.addresses = addresses;
    }
}
