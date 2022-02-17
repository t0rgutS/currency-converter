package com.example.currconverter.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "currency")
public class Currency {
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo
    @NonNull
    private String name;

    @Ignore
    public Currency(String name) {
        this.name = name;
    }
}
