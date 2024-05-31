package com.sebasgoy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sebasgoy.service.qrCodeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HerramientasController {

	@GetMapping("/generar_qr")	
	public String cargarDashboardActividad(Model model) {
 	    return "GeneradorQr";
	}
	
    @PostMapping("/generate-qr")
    public String generateQRCode(@RequestParam String text, @RequestParam String path, Model model) {
        try {
            qrCodeService.generateQRCode(text, path+"/qr.png");
            model.addAttribute("message", "QR code generated successfully at " + path);
        } catch (Exception e) {
            model.addAttribute("message", "Could not generate QR code: " + e.getMessage());
        }
        return "GeneradorQr";
    }


}