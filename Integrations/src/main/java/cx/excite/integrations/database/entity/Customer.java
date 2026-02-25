package cx.excite.integrations.database.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Customer_SEQ")
    @SequenceGenerator(name = "Customer_SEQ", allocationSize = 1)
    @Column(name = "cuKey", nullable = false, length = 9)
    private Integer id;

    @Column(name = "CuCode", length = 20)
    private String cuCode;

    @Column(name = "LastName", length = 40)
    private String lastName;

    @Column(name = "FirstName", length = 40)
    private String firstName;

    @Column(name = "MiddleName", length = 40)
    private String middleName;

    @Column(name = "PrivEmail", length = 100)
    private String privEmail;

    @Column(name = "CreatedStamp", nullable = false)
    private LocalDateTime createdStamp;

    @Column(name = "Stamp", nullable = false)
    private LocalDateTime stamp;

    @Column(name = "Unsubscribed", nullable = false)
    private Integer unsubscribed;

    @Column(name = "Address", length = 60)
    private String address;

    @Column(name = "PostalCode", length = 40)
    private String postalCode;

    @Column(name = "PostalAddress", length = 60)
    private String postalAddress;

    @Column(name = "CustomerType", nullable = false, length = 9)
    private Integer customerType;

    @Column(name = "OwnerOkey", length = 9)
    private Integer ownerOkey;

    @Column(name = "ExtraInfo", length = 60)
    private String extraInfo;

    @Column(name = "ErrorMessage")
    private String errorMessage;

    @Column(name = "PrivEmailCrypt", length = 128)
    private String privEmailCrypt;

    @Column(name = "ExtraInfo2", length = 60)
    private String extraInfo2;

    @Column(name = "ExtCuCode", length = 40)
    private String extCuCode;

    @Column(name = "PrivMobile", length = 40)
    private String privMobile;

    @Column(name = "MemberSince")
    private LocalDateTime memberSince;

    @Column(name = "SubscriptionStart")
    private LocalDateTime subscriptionStart;

    @Column(name = "SubscriptionEnd")
    private LocalDateTime subscriptionEnd;

    public String getExtraInfo2() {
        return extraInfo2;
    }

    public void setExtraInfo2(String extraInfo2) {
        this.extraInfo2 = extraInfo2;
    }

    public String getExtCuCode() { return extCuCode; }

    public void setExtCuCode(String extCuCode) {
        this.extCuCode = extCuCode;
    }

    public String getPrivMobile() { return privMobile; }

    public void setPrivMobile(String privMobile) {
        this.privMobile = privMobile;
    }

    public String getPrivEmailCrypt() {
        return privEmailCrypt;
    }

    public void setPrivEmailCrypt(String privEmailCrypt) {
        this.privEmailCrypt = privEmailCrypt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public Integer getOwnerOkey() {
        return ownerOkey;
    }

    public void setOwnerOkey(Integer ownerOkey) {
        this.ownerOkey = ownerOkey;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUnsubscribed() {
        return unsubscribed;
    }

    public void setUnsubscribed(Integer unsubscribed) {
        this.unsubscribed = unsubscribed;
    }

    public LocalDateTime getStamp() {
        return stamp;
    }

    public void setStamp(LocalDateTime stamp) {
        this.stamp = stamp;
    }

    public LocalDateTime getCreatedStamp() {
        return createdStamp;
    }

    public void setCreatedStamp(LocalDateTime createdStamp) {
        this.createdStamp = createdStamp;
    }

    public String getPrivEmail() {
        return privEmail;
    }

    public void setPrivEmail(String privEmail) {
        this.privEmail = privEmail;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public String getCuCode() {
        return cuCode;
    }

    public void setCuCode(String cuCode) {
        this.cuCode = cuCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}