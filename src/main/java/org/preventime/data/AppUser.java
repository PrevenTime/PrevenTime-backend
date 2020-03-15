package org.preventime.data;

import org.preventime.data.util.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name="app_user")
public class AppUser extends AbstractEntity {

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
