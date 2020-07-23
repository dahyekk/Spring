package com.dbal.app.emp.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dbal.app.common.FileRenamePolicy;
import com.dbal.app.emp.EmpVO;
import com.dbal.app.emp.service.EmpService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller // 빈등록, dispacher servlet이 인식 할 수 있는 컨트롤러로 변환 @Component를 상속받아서 만들어짐
public class EmpController {

	@Autowired
	EmpService empService;
	
	@Autowired
	@Qualifier("dataSourceSpied")
	DataSource datasource;
	
	// 다운로드
	@RequestMapping("download")
	public ModelAndView download(@RequestParam String name) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("download");
		mv.addObject("downloadFile", new File("d:/upload", name));
		return mv;
		// return new ModelAndView("download","downloadFile", name);
	}

	// 등록 폼 handler
	@RequestMapping("insertFormEmp")
	public String insertFormEmp(EmpVO vo) {
		return "emp/insertEmp";
	}

	// 등록 처리 handler
	@RequestMapping("insertEmp")
	public String insertEmp(@ModelAttribute("evo") EmpVO vo, // 1.커맨트 객체
			Model model, @RequestParam String firstName, // 2. String firstName = request.getParameter("firstName")와 같은
															// 역할
			@RequestParam(required = false, defaultValue = "kim", value = "lastName") String ln,

			@RequestParam Map map) throws IOException {
		// 첨부파일 처리
		MultipartFile file = vo.getUploadFile();
		String filename = "";
		if (file != null && file.getSize() > 0) {
			filename = file.getOriginalFilename();
			File upFile = FileRenamePolicy.rename(new File("d:/upload", filename));
			filename = upFile.getName();
			file.transferTo(upFile);
		}
		vo.setProfile(filename);
		// 서비스 호출
		empService.insertEmp(vo);
		System.out.println(vo.getMsg());
		/*
		 * System.out.println(vo.getFirstName() +":" +vo.getLastName());
		 * System.out.println("parameter:"+firstName+":" + ln); System.out.println("map"
		 * + map.get("firstName") + ":" + map.get("lastName"));
		 */
		// model.addAttribute("empVO",vo);

		return "home";
	}

	// 단건조회
	@RequestMapping("getEmp/{employeeId}")
	public String getEmp(@PathVariable String employeeId, Model model, EmpVO empVO) {
		empVO.setEmployeeId(employeeId);
		model.addAttribute("emp",empService.getEmp(empVO));
		return "emp/getEmp";
	}

	// 목록조회
	@RequestMapping("empList") // ajax사용하지 않고 일반방식으로 넘겨주는 방법
	public String empList(Model model, EmpVO empVO) {
		model.addAttribute("empList", empService.getEmpList(empVO));
		return "emp/empList";
	}

	// emp관리
	@RequestMapping("empClient")
	public String empClient() {
		return "admin/emp/empClient";
	}

	// ajax : 목록
	@RequestMapping("ajaxEmpList")
	public @ResponseBody List<EmpVO> ajaxEmpList(EmpVO empVO) {
		return empService.getEmpList(empVO);
	}

	// 차트데이터
	@RequestMapping("getChartData")
	public @ResponseBody List<Map<String, Object>> getDeptEmpCnt() {
		return empService.getDeptEmpCnt();
	}

	// 수정폼

	// 수정처리

	// 삭제처리

	// 첨부파일 처리

	/*
	 * @Autowired EmpDAO dao;
	 * 
	 * @RequestMapping("/empList.do") public String empList(Model model) {
	 * model.addAttribute("empList", dao.empList()); return "emp/list"; }
	 */
	// 레포트 출력
	@RequestMapping("report.do")
	public void report(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = datasource.getConnection();
												//classpath					//빈껍데기
//		InputStream jasperStream = getClass().getResourceAsStream("/reports/emplist.jasper");
//		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		InputStream stream = getClass().getResourceAsStream("/reports/emplist.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(stream);
		
																//reportfile, connection - datasource / 객체, 리스트 등등 
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("p_departmentId", request.getParameter("dept"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, conn);
		JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
								//pdf로 다운받게 하겟음!
	}
	@RequestMapping("report2.do")
	public ModelAndView report2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return new ModelAndView ("pdfView","filename","/reports/emplist.jasper");
	}
	
	@RequestMapping("getEmpAjax")
	@ResponseBody
	public EmpVO getEmpAjax(EmpVO empVO) {
		return empService.getEmp(empVO);
	}
}