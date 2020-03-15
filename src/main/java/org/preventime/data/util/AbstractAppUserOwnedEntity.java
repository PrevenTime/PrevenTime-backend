package org.preventime.data.util;

import org.preventime.data.AppUser;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractAppUserOwnedEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false, updatable = false)
    private AppUser appUser;

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
