package com.example.currconverter.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.currconverter.model.Currency;

import java.util.List;

@Dao
public interface CurrencyDAO {
    @Query("SELECT * FROM currency")
    LiveData<List<Currency>> getAll();

    @Query("SELECT * FROM currency WHERE id = :id")
    Currency getById(Long id);

    @Query("SELECT * FROM currency WHERE name = :name LIMIT 1")
    Currency getByName(String name);

    @Query("SELECT EXISTS(SELECT * FROM currency WHERE name = :name)")
    Boolean isExists(String name);

    @Insert
    void addCurrency(Currency currency);

    @Delete
    void deleteCurrency(Currency currency);

    @Query("DELETE FROM currency WHERE id IN (:idList)")
    void deleteByIdList(List<Long> idList);
}
