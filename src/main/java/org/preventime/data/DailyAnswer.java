package org.preventime.data;

import org.preventime.data.util.AbstractAppUserOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="daily_answer")
public class DailyAnswer extends AbstractAppUserOwnedEntity {

    @Column(name="fiebre")
    private String fiebre;

    public String getFiebre() {
        return fiebre;
    }

    public void setFiebre(String fiebre) {
        this.fiebre = fiebre;
    }
}
