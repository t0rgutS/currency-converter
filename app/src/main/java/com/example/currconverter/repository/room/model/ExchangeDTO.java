package com.example.currconverter.repository.room.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.currconverter.model.Exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(tableName = "exchange", primaryKeys = {"source_id", "target_id"})
public class ExchangeDTO extends Exchange {
    @ColumnInfo(name = "source_id")
    @NonNull
    private Long sourceId;

    @ColumnInfo(name = "target_id")
    @NonNull
    private Long targetId;
}
