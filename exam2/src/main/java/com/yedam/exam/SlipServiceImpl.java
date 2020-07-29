package com.yedam.exam;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
@Service
public class SlipServiceImpl implements SlipService{

	@Resource	SlipDAO dao;
	
	@Override
	public int insertSlip(List<Slip> list) {
		int idx =0;
		for (Slip slip : list) {
			String SlipNo = slip.getSalDt()+idx++;
			slip.setSlipNo(Integer.parseInt(SlipNo));
			dao.insertSlip(slip);
		}		
		return idx;
	}

}
