package com.sebasgoy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sebasgoy.Mapper.ExcelMapper;
import com.sebasgoy.dto.response.ExcelResponse;

@Controller
public class ExcelController {
	
	@RequestMapping("/upload-page")
	public String cargarLectorExcel() {
		return "lectorExcel";
	}
	
    @PostMapping("/cargar")
    public String handleFileUpload(@RequestParam("excelFile") MultipartFile file, Model model) {
        try {
            // Procesar el archivo Excel según tus necesidades
			ExcelResponse excelResponse = ExcelMapper.LeerExcel(file);       	        	
        	
        	model.addAttribute("result",excelResponse.getTotalPersonas());
        	
         } catch (Exception e) {
            model.addAttribute("result", "Error processing the Excel file."+ e.toString());
        }
        return "lectorExcel";
    }
	

}
