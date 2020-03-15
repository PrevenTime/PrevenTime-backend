package org.preventime.data;

import org.preventime.data.util.AbstractAppUserOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity(name="daily_answer")
public class DailyAnswer extends AbstractAppUserOwnedEntity {

    @Column(name = "answer_date")
    private LocalDate answerDate;
    @Column(name="dry_cough")
    private String dryCough;
    @Column(name="fever")
    private String fever;
    @Column(name="fever_amount")
    private String feverAmount;
    @Column(name="difficulty_breathing")
    private String difficultyBreathing;
    @Column(name="diarrhea")
    private String diarrhea;

    public LocalDate getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(LocalDate answerDate) {
        this.answerDate = answerDate;
    }

    public String getDryCough() {
        return dryCough;
    }

    public void setDryCough(String dryCough) {
        this.dryCough = dryCough;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getFeverAmount() {
        return feverAmount;
    }

    public void setFeverAmount(String feverAmount) {
        this.feverAmount = feverAmount;
    }

    public String getDifficultyBreathing() {
        return difficultyBreathing;
    }

    public void setDifficultyBreathing(String difficultyBreathing) {
        this.difficultyBreathing = difficultyBreathing;
    }

    public String getDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(String diarrhea) {
        this.diarrhea = diarrhea;
    }
}
