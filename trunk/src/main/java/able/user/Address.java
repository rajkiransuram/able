package able.user;

import org.hibernate.validator.Length;
import org.hibernate.annotations.Tuplizer;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import able.hibernate.GuiceComponentTuplizer;

@Embeddable
@Tuplizer(impl = GuiceComponentTuplizer.class)
public class Address {
    @Column(name = "address_line_1")
    @Length(min = 0, max = 64)
    private String addressLine1;

    @Column(name = "address_line_2")
    @Length(min = 0, max = 64)
    private String addressLine2;

    @Column(name = "address_city")
    @Length(min = 0, max = 64)
    private String city;

    @Column(name = "address_state")
    @Length(min = 0, max = 64)
    private String state;

    @Column(name = "address_zip")
    @Length(min = 0, max = 64)
    private String zipCode;

    @Column(name = "address_country")
    @Length(min = 0, max = 64)
    private String country;

    public Address() {
    }

    public Address(String addressLine1, String addressLine2, String city, String state, String zipCode, String country) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static Address trialAddress() {
        return new Address("", "", "", "", "", "");
    }
}
