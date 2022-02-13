package devsearch.users.ws.io.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 2856237646551050576L;

    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30, nullable = false)
    private String addressId;

    @Column(length = 25, nullable = false)
    private String city;

    @Column(length = 25, nullable = false)
    private String country;

    @Column(length = 100, nullable = false)
    private String street;

    @Column(length = 10, nullable = false)
    private String postalCode;

    @Column(length = 10, nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
}
