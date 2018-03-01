package org.rta.core.entity.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.rta.core.entity.base.SerializableEntity;

@Entity
@Table(name = "dealer")
public class DealerEntity extends SerializableEntity {

    private static final long serialVersionUID = 6811922539582418907L;

    @Id
    @Column(name = "dealer_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_seq")
    @SequenceGenerator(name = "dealer_seq", sequenceName = "dealer_seq", allocationSize = 1)
    private Long dealerId;

    // Dealer Showroom Name
    private String name;

    @Column(name = "fax")
    private String fax;

    @Column(name = "parent_id")
    private Long parentId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(name = "dealer_sign")
    private String dealerSign;
    
    @Column(name = "eligible_to_pay", columnDefinition = "boolean default true")
    private Boolean eligibleToPay;
    
    @Column(name = "lat")
    private Double lat;
    
    @Column(name = "lng")
    private Double lng;

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     * @return Dealer's Showroom name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Dealer's Showroom name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getDealerSign() {
        return dealerSign;
    }

    public void setDealerSign(String dealerSign) {
        this.dealerSign = dealerSign;
    }

    public Boolean getEligibleToPay() {
        return eligibleToPay;
    }

    public void setEligibleToPay(Boolean eligibleToPay) {
        this.eligibleToPay = eligibleToPay;
    }

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
    
}
