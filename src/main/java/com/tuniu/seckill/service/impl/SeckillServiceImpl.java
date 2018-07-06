package com.tuniu.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.tuniu.seckill.dao.SeckillDao;
import com.tuniu.seckill.dao.SuccessKilledDao;
import com.tuniu.seckill.dto.Exposer;
import com.tuniu.seckill.dto.SecKillExecution;
import com.tuniu.seckill.enums.SeckillStateEnum;
import com.tuniu.seckill.exception.RepeatKillException;
import com.tuniu.seckill.exception.SeckillCloseException;
import com.tuniu.seckill.exception.SeckillException;
import com.tuniu.seckill.model.SecKill;
import com.tuniu.seckill.model.SuccessKilled;
import com.tuniu.seckill.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String salt = "http://www.515ai.net";
	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Override
	public List<SecKill> getSeckillList() {
		return seckillDao.queryByOffset(0, 100);
	}

	@Override
	public SecKill getById(Long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(Long seckillId) {
		SecKill secKill = seckillDao.queryById(seckillId);
		if (secKill == null)
			return new Exposer(false, seckillId);
		Date startTime = secKill.getStartTime();
		Date endTime = secKill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, getMd5(seckillId), seckillId, nowTime.getTime(), startTime.getTime(),
					endTime.getTime());
		}

		return new Exposer(true, getMd5(seckillId), seckillId, nowTime.getTime(), startTime.getTime(),
				endTime.getTime());
	}

	private String getMd5(Long seckillId) {
		String base = seckillId + "/" + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/**
	 * 秒杀成功,减少'库存',增加'减明细记录';秒杀失败,抛出异常,事务回滚;
	 */
	@Override
	@Transactional
	public SecKillExecution executeSeckill(Long seckillId, Long phone, String md5) throws SeckillException,SeckillCloseException,RepeatKillException{
		try {
			if (md5 == null || !md5.equals(getMd5(seckillId))) {
				throw new SeckillException("seckill data rewrite");
				//return new SecKillExecution(seckillId, SeckillStateEnum.DATE_REWRITE);
			}
			Date now = new Date();
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, phone);
			if (insertCount <= 0) {
				throw new RepeatKillException("seckill repeated");
			} else {
				int updateCount = seckillDao.reduceNumber(seckillId, now);
				
				if (updateCount <= 0) {
					throw new SeckillCloseException("seckill is closed");
				} else {
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, phone);
					return new SecKillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e) {
			throw e;
			//return new SecKillExecution(seckillId, SeckillStateEnum.END);
		} catch (RepeatKillException e) {
			throw e;
			//return new SecKillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);

		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SeckillException("seckill inner error:"+e.getMessage());
//			return new SecKillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		}

		
	}

}
