package com.mhs.starwarscharacter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhs.starwarscharacter.entity.character.CharacterList

@Dao
interface StarWarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacter(characterList: List<CharacterList>)

    @Query("SELECT * FROM characters")
    suspend fun getCharacterList() : List<CharacterList>
}