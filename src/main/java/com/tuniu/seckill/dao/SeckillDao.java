package com.tuniu.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tuniu.seckill.model.SecKill;

public interface SeckillDao {

	List<SecKill> queryByOffset(@Param("offset")int bgn,@Param("limit") int end);

	SecKill queryById(Long seckillId);

	int reduceNumber(@Param("seckillId")Long seckillId,@Param("killTime")Date now);

}
