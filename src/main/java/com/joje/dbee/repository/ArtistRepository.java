package com.joje.dbee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joje.dbee.entity.hipword.ArtistEntity;

public interface ArtistRepository extends JpaRepository<ArtistEntity, Long>{

	int countByArtistId(String artistId);

	ArtistEntity findByArtistId(String artistId);

}
