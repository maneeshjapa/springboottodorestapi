package com.sampleAPI.springbootrestapitests.todoitems;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sampleAPI.springbootrestapitests.users.Users;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todoitems")
@EntityListeners(AuditingEntityListener.class)
public class TodoItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todoId;

    @Column(name = "notes", nullable = true)
    private String notes;

    @Column(name = "createddate", nullable = false)
    @CreatedDate
    private Date createdDate = new Date();

    @Column(name = "updateddate", nullable = false)
    @LastModifiedDate
    private Date updatedDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate() {
        this.updatedDate = new Date();
    }

}
