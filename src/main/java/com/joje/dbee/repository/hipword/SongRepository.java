package com.joje.dbee.repository.hipword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joje.dbee.entity.hipword.SongEntity;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {

	int countBySongId(String songId);

	SongEntity findBySongId(String songId);

}
