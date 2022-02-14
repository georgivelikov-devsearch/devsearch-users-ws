package devsearch.users.ws.io.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "profiles")
public class ProfileEntity implements Serializable {

    private static final long serialVersionUID = -6012515456516447244L;

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String profileId;

    @Column(nullable = true, length = 50)
    private String displayName;

    @Column(nullable = true, length = 200)
    private String shortIntro;

    @Column(nullable = true, length = 1000)
    private String bio;

    @Column(nullable = true, length = 400)
    private String socialLinkedIn;

    @Column(nullable = true, length = 400)
    private String socialTwitter;

    @Column(nullable = true, length = 400)
    private String socialGithub;

    @Column(nullable = true, length = 400)
    private String socialYoutube;

    @Column(nullable = true, length = 400)
    private String socialWebsite;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    private UserEntity user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Collection<AddressEntity> addresses;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getProfileId() {
	return profileId;
    }

    public void setProfileId(String profileId) {
	this.profileId = profileId;
    }

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

    public UserEntity getUser() {
	return user;
    }

    public void setUser(UserEntity user) {
	this.user = user;
    }

    public Collection<AddressEntity> getAddresses() {
	return addresses;
    }

    public void setAddresses(Collection<AddressEntity> addresses) {
	this.addresses = addresses;
    }

}
