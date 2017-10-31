package za.co.ajk.incidentman.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Member.
 */
@Document(collection = "member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Field("member_key")
    private String memberKey;

    @NotNull
    @Field("member_name")
    private String memberName;

    @NotNull
    @Field("member_surname")
    private String memberSurname;

    @NotNull
    @Field("date_created")
    private ZonedDateTime dateCreated;

    @NotNull
    @Field("date_modified")
    private ZonedDateTime dateModified;

    @Field("updated_by")
    private String updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberKey() {
        return memberKey;
    }

    public Member memberKey(String memberKey) {
        this.memberKey = memberKey;
        return this;
    }

    public void setMemberKey(String memberKey) {
        this.memberKey = memberKey;
    }

    public String getMemberName() {
        return memberName;
    }

    public Member memberName(String memberName) {
        this.memberName = memberName;
        return this;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberSurname() {
        return memberSurname;
    }

    public Member memberSurname(String memberSurname) {
        this.memberSurname = memberSurname;
        return this;
    }

    public void setMemberSurname(String memberSurname) {
        this.memberSurname = memberSurname;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Member dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateModified() {
        return dateModified;
    }

    public Member dateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
        return this;
    }

    public void setDateModified(ZonedDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Member updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        if (member.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", memberKey='" + getMemberKey() + "'" +
            ", memberName='" + getMemberName() + "'" +
            ", memberSurname='" + getMemberSurname() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateModified='" + getDateModified() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
