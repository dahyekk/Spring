package com.yedam.exam;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


/**
 * Handles requests for the application home page.
 */
@Controller
public class SlipController {
	
	private static final Logger logger = LoggerFactory.getLogger(SlipController.class);
	
	@Autowired
	SlipService slipService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value="slip", method = RequestMethod.POST,headers = {"Content-type=application/json"})
	@ResponseBody
	public Map insertSlip(@RequestBody List<Slip> slip, Model model) {
		int cnt = slipService.insertSlip(slip);
		System.out.println(cnt);
		Map result = new HashMap<String, Object>();
		result.put("result", Boolean.TRUE);
		result.put("total", slip.size());
		result.put("success",cnt);
		return  result;
	}
	
	@RequestMapping(value="/respAPI")
	public Map respAPI() {
		RestTemplate rest = new RestTemplate();
		return rest.getForObject("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20200713", Map.class);
	}
}
