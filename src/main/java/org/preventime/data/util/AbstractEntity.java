package org.preventime.data.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "random_string_8", strategy =
            "org.preventime.data.util.RandomStringIdentifierGenerator")
    @GeneratedValue(generator = "random_string_8")
    private String id;

    @Column(name = "creation_datetime", insertable = false, updatable = false)
    private LocalDateTime creationDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(LocalDateTime creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String toString() {
        return this.getClass().getSimpleName() + "(" + id + ")";
    }

    @JsonIgnore
    public boolean isPersisted() {
        return id != null;
    }

}
