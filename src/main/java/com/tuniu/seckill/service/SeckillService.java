package com.tuniu.seckill.service;

import java.util.List;

import com.tuniu.seckill.dto.Exposer;
import com.tuniu.seckill.dto.SecKillExecution;
import com.tuniu.seckill.model.SecKill;

public interface SeckillService {

	List<SecKill> getSeckillList();

	SecKill getById(Long seckillId);

	Exposer exportSeckillUrl(Long seckillId);

	SecKillExecution executeSeckill(Long seckillId, Long phone, String md5);

}
