package com.kspt.buro.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Поле должно быть заполнено")
    @Length(max = 2048,message = "Описание слишком большое")
    private String text;

    @NotBlank(message = "Поле должно быть заполнено")
    @Length(max = 255,message = "Описание слишком большое")
    private String ptype;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    @ElementCollection(targetClass = Checks.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "request_checks", joinColumns = @JoinColumn(name = "request_id"))
    @Enumerated(EnumType.STRING)
    private Set<Checks> checks;

    private String message;

    public Request() { }
    public Request(String text, String ptype, User user) {
        this.author = user;
        this.text = text;
        this.ptype = ptype;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getPtype() {
        return ptype;
    }
    public void setPtype(String ptype) {
        this.ptype = ptype;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<Checks> getChecks() {
        return checks;
    }
    public void setChecks(Set<Checks> checks) {
        this.checks = checks;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName(){
        return  author != null ? author.getUsername() : "<none>";
    }
}
