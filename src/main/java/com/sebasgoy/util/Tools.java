package com.sebasgoy.util;

import com.sebasgoy.dto.Actividad;
import com.sebasgoy.dto.Voluntario;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Objects;
@Component
public class Tools {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	public static String paginaAnterior (HttpServletRequest request) {
		return request.getHeader("referer");
	}
	public static String getStringFromCell(Row fila , int ubicacion) {
		DataFormatter dataFormatter = new DataFormatter();
		return dataFormatter.formatCellValue(fila.getCell(ubicacion)).trim();
	}
	private static LocalTime getLocalTimeFromCell(Row fila, int ubicacion) {
		DataFormatter dataFormatter = new DataFormatter();
		String timeString = dataFormatter.formatCellValue(fila.getCell(ubicacion));

		// Parse the time string to a LocalTime object
		return LocalTime.parse(timeString, timeFormatter);
	}
	private static Date getDateFromCell(Row fila, int ubicacion) throws ParseException {
		DataFormatter dataFormatter = new DataFormatter();
		Cell cell = fila.getCell(ubicacion);
		if (cell == null) {
			return null; // or throw an exception if necessary
		}
		String dateString = dataFormatter.formatCellValue(cell);

		// No need to recreate the SimpleDateFormat instance, reuse it
		java.util.Date fecha = dateFormat.parse(dateString);
		return new Date(fecha.getTime());
	}

	public static Iterator<Row> getRowIterator(MultipartFile archivoExcel) throws IOException {
		Workbook libro;
		if (Objects.requireNonNull(archivoExcel.getOriginalFilename()).endsWith(".xlsx")) {
			libro = new XSSFWorkbook(archivoExcel.getInputStream());
		} else if (archivoExcel.getOriginalFilename().endsWith(".xls")) {
			libro = new HSSFWorkbook(archivoExcel.getInputStream());
		} else {
			throw new IllegalArgumentException("El archivo no tiene una extensión de Excel válida.");
		}

		return libro.getSheetAt(0).iterator();
	}

	public static boolean areVoluntarioFieldsValid(Voluntario voluntario) {
		return isStringNotBlank(voluntario.getNombre()) &&
				isStringNotBlank(voluntario.getDni());
	}


	private static boolean isStringNotBlank(String value) {
		return value != null && !value.isEmpty();
	}



}
