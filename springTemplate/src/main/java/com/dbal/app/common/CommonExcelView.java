package com.dbal.app.common;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

@Component("commonExcelView")
public class CommonExcelView extends AbstractXlsView {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonExcelView.class);

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook wb, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String file_name = (String) model.get("filename") + System.currentTimeMillis() + ".xlsx";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file_name + "\"");

		Cell cell = null;
		Sheet sheet = wb.createSheet("User List");
		sheet.setDefaultColumnWidth(12);
		List<Map<String, Object>> list = (List<Map<String, Object>>) model.get("datas");
		Row row;
		int rowNum = 0;
		// header 출력
		String[] headers = (String[]) model.get("headers");
		if (headers != null) {
			row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String header : headers) {
				row.createCell(colNum++).setCellValue(header);
			}
		}
		if (headers != null) {
			for (int i = 0; i < list.size(); i++) {		//list 갯수만큼
				row = sheet.createRow(i);
				Map map = list.get(i);
				int j = 0;
				for(String header : headers) {
					cell = row.createCell(j++);
					cell.setCellValue(map.get(header).toString());
				}
			}
		} else {
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i);
				Map map = list.get(i);
				Iterator<String> iter = map.keySet().iterator();
				int j = 0;
				while (iter.hasNext()) {
					cell = (Cell) row.createCell(j++);
					cell.setCellValue(map.get(iter.next()).toString());
				}
			}
		}
	}
}