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
    private String publicId;

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

    @OneToOne(mappedBy = "profile")
    private UserEntity user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Collection<AddressEntity> addresses;

}
