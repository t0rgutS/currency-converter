package com.example.currconverter.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.currconverter.repository.room.model.ExchangeDTO;

import java.util.List;

@Dao
public interface ExchangeDAO {
    @Query("SELECT * FROM exchange")
    LiveData<List<ExchangeDTO>> getAll();

    @Query("SELECT * FROM exchange WHERE source_id=:sourceId AND target_id=:targetId")
    LiveData<ExchangeDTO> getByIds(Long sourceId, Long targetId);

    @Insert
    void addExchange(ExchangeDTO exchange);

    @Update
    void updateExchange(ExchangeDTO exchange);

    @Delete
    void deleteExchange(ExchangeDTO exchange);

    @Query("DELETE FROM exchange WHERE source_id=:sourceId")
    void deleteExchangeBySource(Long sourceId);

    @Query("DELETE FROM exchange WHERE target_id=:targetId")
    void deleteEchangeByTarget(Long targetId);

    @Query("DELETE FROM exchange WHERE source_id=:id OR target_id=:id")
    void deleteExchangeById(Long id);
}
