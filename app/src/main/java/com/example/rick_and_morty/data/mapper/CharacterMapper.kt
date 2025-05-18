package com.example.rick_and_morty.data.mapper

import android.net.Uri
import com.example.rick_and_morty.data.dto.CharacterDTO
import com.example.rick_and_morty.data.model.CharacterModel
import com.example.rick_and_morty.domain.entity.CharacterEntity

abstract class CharacterMapper {
    companion object{
        fun mapDTOtoEntity(dto: CharacterDTO): CharacterEntity{
            return CharacterEntity(
                name = dto.name,
                image = Uri.parse(dto.image),
                status = dto.status
            )
        }

        fun mapModelToEntity(model: CharacterModel): CharacterEntity {
            return CharacterEntity(
                name = model.name,
                image = Uri.parse(model.image),
                status = model.status
            )
        }
    }
}