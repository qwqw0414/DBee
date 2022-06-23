package com.joje.dbee.repository.hipword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joje.dbee.entity.hipword.RankEntity;

@Repository
public interface RankRepository extends JpaRepository<RankEntity, Long>{

}
