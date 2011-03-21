package org.eayrad.api.jsf.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class Export {

	public static void exportSheet(List objects, Object[][] exportParameters, String fileName, String[] titles) throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		if (fileName == null)
			fileName = "planilha.xls";
		else if (!fileName.endsWith(".xls"))
			fileName += ".xls";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setContentType("application/vnd.ms-excel");
		OutputStream out = response.getOutputStream();
		File file = Export.createFile(objects, exportParameters[0], exportParameters[1], null, titles);
		response.setContentLength((int) file.length());

		FileInputStream in = new FileInputStream(file);
		byte[] buf = new byte[(int) file.length()];
		in.read(buf, 0, (int) file.length());
		out.write(buf, 0, (int) file.length());

		out.flush();
		out.close();
		context.responseComplete();
		file.delete();
	}
	public static File createFile(List list, Object[] getters, Object[] headers, String fileName, String[] titles) throws IOException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		WorkbookSettings wbs = new WorkbookSettings();
		wbs.setEncoding("ISO-8859-1");
		 * 
		 */
		File file;
		if (fileName == null)
			file = File.createTempFile("sheet", ".tmp");
		else
			file = new File(fileName);
		FileOutputStream fos = new FileOutputStream(file);
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length-1));

		Row r;
		Cell c;
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		for (int i = 0 ; i < titles.length ; i++) {
			r = sheet.createRow(i);
			c = r.createCell(0);
			c.setCellStyle(style);
			c.setCellValue(titles[i]);
		}
		r = sheet.createRow(titles.length);
		for (int i = 0 ; i < headers.length ; i++) {
			c = r.createCell(i);
			c.setCellValue(headers[i].toString());
			c.setCellStyle(style);
		}
		Iterator iterator = list.iterator();
		Object content, obj;
		String prov;
		String[] internal;
		Class classe;
		Method method;
		for (int i = 0 ; iterator.hasNext() ; i++) {
			obj = iterator.next();
			r = sheet.createRow(i+1+titles.length);
			for (int j = 0; j < getters.length; j++) {
				content = "";
				c = r.createCell(j);
				if (getters[j].toString().contains(".")) {
					prov = getters[j].toString().replace(".", ";");
					internal = prov.split(";");
					Object objProv = obj;
					for (int internelIn = 0 ; internelIn < internal.length ; internelIn++) {
						classe = objProv.getClass();
						method = classe.getMethod(internal[internelIn], new Class[0]);
						content = method.invoke(objProv, null);
						objProv = content;
					}
				}
				else {
					classe = obj.getClass();
					method = classe.getMethod(getters[j].toString(), new Class[0]);
					content = method.invoke(obj, null);
				}
				if (content == null) {
					c.setCellValue("");
				}
				else if (content instanceof Date) {
					// TODO mecanismo para detectar o tipo de data
					Date date = (Date) content;
					c.setCellValue(date);
				} else if (content instanceof Integer) {
					c.setCellValue(content.toString());
					 //TODO Criar mecanismo para detectar a precisao de float e double via anotacoes.
				} else if (content instanceof Boolean) {
					Boolean teste = Boolean.valueOf(content.toString());
					c.setCellValue(teste);
				} else if (content instanceof Double) {
					c.setCellValue(Double.parseDouble(content.toString()));
				} else {
					c.setCellValue(content.toString());
				}
			}
		}
		workbook.write(fos);
		fos.close();
		return file;
	}
}