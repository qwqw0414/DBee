package com.joje.dbee.repository.hipword;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joje.dbee.entity.hipword.RankEntity;

@Repository
public interface RankRepository extends JpaRepository<RankEntity, Long>{

	Optional<RankEntity> findByRankNo(long rankNo);
	
	@Query(value = "SELECT MAX(REG_DATE) FROM TB_HIPWORD_RANK", nativeQuery = true)
	LocalDate findTopOrderByRegDate();

}
