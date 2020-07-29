package com.dbal.app.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping("userexcel.do")
	public ModelAndView userexcel(UserVO vo) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("commonExcelView");
		mv.addObject("datas",userService.getUserListMap(vo));
		mv.addObject("filename","userlist");
		return mv;
	}
}
