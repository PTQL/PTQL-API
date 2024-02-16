package com.sebasgoy.constantes;

import com.sebasgoy.dto.request.PlantillaDto;

import java.io.FileWriter;
import java.io.IOException;

public class Plantillas {



    public static String GenerarPlantilla(PlantillaDto plantillaDto) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\"></meta>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></meta>\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "        @import url('https://fonts.cdnfonts.com/css/glacial-indifference-2?styles=54222,54221');\n" +
                "        @import url('https://fonts.googleapis.com/css2?family=Manjari:wght@100,400,700');\n" +
                "        @page { size: 1920px 1080px; margin: 0; }   "+
                "        body{\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            box-sizing: border-box;\n" +
                "        }"+
                "        .NombreVoluntario {\n" +
                "            position: absolute;\n" +
                "            width: 844.7px;\n" +
                "            height: 62.1px;\n" +
                "            left: 578.5px;\n" +
                "            top: 480.9px;\n" +
                "        }\n" +
                "\n" +
                "       .img_fondo{\n"+
                "           width: 1916px;\n" +
                "           height: 1076px;" +
                "        }\n"+
                "\n" +
                "        .NombreVoluntario p {\n" +
                "            margin: 0;\n" +
                "            font-size: 3.1em;\n" +
                "            font-family: 'Glacial Indifference', sans-serif;\n" +
                "            font-weight: 700;\n" +
                "            text-align: center;\n"+
                "            color: #4D712B;\n" +
                "        }\n" +
                "\n" +
                "        .DescripcionVoluntario {\n" +
                "            position: absolute;\n" +
                "            width: 1672.4px;\n" +
                "            height: 106.4px;\n" +
                "            left: 144.1px;\n" +
                "            top: 573.7px;\n" +
                "        }\n" +
                "\n" +
                "        .DescripcionVoluntario p {\n" +
                "            margin: 0;\n" +
                "            font-family: 'Manjari', sans-serif;\n" +
                "            font-size: 31px;\n" +
                "        }\n" +
                "\n" +
                "        .FechaGeneralActividad {\n" +
                "            position: absolute;\n" +
                "            width: 262px;\n" +
                "            height: 35.2px;\n" +
                "            left: 447.5px;\n" +
                "            top: 768.3px;\n" +
                "        }\n" +
                "\n" +
                "        .FechaGeneralActividad p {\n" +
                "            margin: 0;\n" +
                "            font-family: 'Manjari', sans-serif;\n" +
                "            font-size: 31px;\n" +
                "        }\n" +
                "\n" +
                "        .nombreActividad {\n" +
                "            text-transform: uppercase;\n" +
                "            font-weight: 700;\n" +
                "            color: #67A234;\n" +
                "        }\n" +
                "\n" +
                "        .fechaActividad {\n" +
                "            font-weight: 700;\n" +
                "        }\n" +
                "\n" +
                "        .FechaGeneralActividad {\n" +
                "            font-family: 'Manjari', sans-serif;\n" +
                "            font-weight: 500;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"fondo\">\n" +
                "    <img class=\"img_fondo\" src=\"https://i.postimg.cc/G2vC8vmR/CONSTANCIA-DE-PARTICIPACI-N-ACTUALIZADA.png\"></img>"+
                "        <div class=\"NombreVoluntario\">\n" +
                "            <p>" + plantillaDto.getNombreVoluntario() + "</p>\n" +
                "        </div>\n" +
                "        <div class=\"DescripcionVoluntario\">\n" +
                "            <p>En reconocimiento a su arduo trabajo y valioso aporte personal como voluntario para el éxito de la actividad <span class=\"nombreActividad\">" + plantillaDto.getNombreActividad() + "</span> <span style=\"font-weight: 700;\">, realizada el </span><span class=\"fechaActividad\">" + plantillaDto.getFechaActividad() + "</span> en <span class=\"ubicacionActividad\">" + plantillaDto.getUbicacionActividad() + "</span>. La constancia a continuación se emite por un total de <span class=\"horasActivdad\">" + plantillaDto.getHorasActividad() + "</span> horas de trabajo voluntario.</p>\n" +
                "        </div>\n" +
                "        <div class=\"FechaGeneralActividad\">\n" +
                "            <p>" + plantillaDto.getFechaGeneralActividad() + "</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }


}
