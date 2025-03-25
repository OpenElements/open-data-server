package com.openelements.opendata.pullrequests;

import com.openelements.opendata.base.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.time.ZonedDateTime;

@Entity
public class PullRequest extends AbstractEntity {

    @Column(nullable = false)
    private String org;

    @Column(nullable = false)
    private String repository;

    @Column(nullable = false)
    private long gitHubId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean open;

    @Column(nullable = false)
    private boolean merged;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private ZonedDateTime createdAtInGitHub;

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public long getGitHubId() {
        return gitHubId;
    }

    public void setGitHubId(long gitHubId) {
        this.gitHubId = gitHubId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public ZonedDateTime getCreatedAtInGitHub() {
        return createdAtInGitHub;
    }

    public void setCreatedAtInGitHub(ZonedDateTime createdAtInGitHub) {
        this.createdAtInGitHub = createdAtInGitHub;
    }
}
