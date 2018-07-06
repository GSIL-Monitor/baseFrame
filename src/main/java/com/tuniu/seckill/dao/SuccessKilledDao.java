package com.tuniu.seckill.dao;

import org.apache.ibatis.annotations.Param;

import com.tuniu.seckill.model.SuccessKilled;

public interface SuccessKilledDao {

	int insertSuccessKilled(@Param("seckillId")Long seckillId,@Param("userPhone")Long phone);
	    
	SuccessKilled queryByIdWithSeckill(@Param("seckillId")Long seckillId,@Param("userPhone") Long phone);

}
