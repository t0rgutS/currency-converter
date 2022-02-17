package com.example.currconverter.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exchange {
    @Ignore
    protected String source;

    @Ignore
    protected String target;

    @ColumnInfo
    @NonNull
    protected Double rate;

    @ColumnInfo(name = "last_modified")
    @NonNull
    protected Long lastModifiedOn;

    @ColumnInfo(name = "next_update")
    @NonNull
    protected Long nextUpdateOn;

    public Exchange(Double rate, Long lastModifiedOn, Long nextUpdateOn) {
        this.rate = rate;
        this.lastModifiedOn = lastModifiedOn;
        this.nextUpdateOn = nextUpdateOn;
    }
}
