package com.sebasgoy.constantes;
import com.lowagie.text.DocumentException;
import com.sebasgoy.dto.request.PlantillaActividadDto;
import com.sebasgoy.dto.request.PlantillaModuloDto;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Plantillas {
    private static final String urlImg = "https://i.postimg.cc/L8wxNcQT/CONSTANCIA-DE-PARTICIPACI-N-ACTUALIZADA-2.png";
    public static String GenerarPlantillaActividad(PlantillaActividadDto plantillaDto) {
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
                "        .titulo1{\n" +
                "            position: absolute;\n" +
                "            width: 1335.6px;\n" +
                "            height: 40.3px;\n" +
                "            left: 292.2px ;\n" +
                "            top: 376.3px;\n" +
                "        }\n" +
                "\n" +
                "        .titulo1 p{\n" +
                "            margin: 0;\n" +
                "            font-size: 35px;\n" +
                "            font-family: \"Manjari\", sans-serif;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .verde{\n" +
                "            color:#67A234\n" +
                "        }\n" +
                "        .titulo2{\n" +
                "            position: absolute;\n" +
                "            width: 413.4px;\n" +
                "            height: 37.6px;\n" +
                "            left: 773.6px ;\n" +
                "            top: 419px;\n" +
                "        }\n" +
                "        .titulo2 p{\n" +
                "            margin: 0;\n" +
                "            font-size: 35px;\n" +
                "            font-family: \"Manjari\", sans-serif;\n" +
                "            text-align: center;\n" +
                "            color: #202D10;\n" +
                "        }"+
                "        .NombreVoluntario {\n" +
                "            position: absolute;\n" +
                "            width: 100%;\n" +
                "            height: 62.1px;\n" +
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
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"fondo\">\n" +
                "    <img class=\"img_fondo\" src=\""+urlImg+"\"></img>"+
                "        <div class=\"titulo1\">\n" +
                "            <p>La ONG <span class=\"verde\">\"Perú Te Quiero Limpio\"</span> dedicada a la preservación del medio ambiente</p>\n" +
                "        </div>\n" +
                "        <div class=\"titulo2\">\n" +
                "            <p>Otorga esta constancia a </p>\n" +
                "        </div>"+
                "        <div class=\"NombreVoluntario\">\n" +
                "            <p>" + plantillaDto.getVoluntario().getNombre() + "</p>\n" +
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

    public static String GenerarPlantillaModulo(PlantillaModuloDto plantillaDto) {
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
                "        .titulo1{\n" +
                "            position: absolute;\n" +
                "            width: 1335.6px;\n" +
                "            height: 40.3px;\n" +
                "            left: 292.2px ;\n" +
                "            top: 376.3px;\n" +
                "        }\n" +
                "\n" +
                "        .titulo1 p{\n" +
                "            margin: 0;\n" +
                "            font-size: 35px;\n" +
                "            font-family: \"Manjari\", sans-serif;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .verde{\n" +
                "            color:#67A234\n" +
                "        }\n" +
                "        .titulo2{\n" +
                "            position: absolute;\n" +
                "            width: 413.4px;\n" +
                "            height: 37.6px;\n" +
                "            left: 773.6px ;\n" +
                "            top: 419px;\n" +
                "        }\n" +
                "        .titulo2 p{\n" +
                "            margin: 0;\n" +
                "            font-size: 35px;\n" +
                "            font-family: \"Manjari\", sans-serif;\n" +
                "            text-align: center;\n" +
                "            color: #202D10;\n" +
                "        }"+
                "        .NombreVoluntario {\n" +
                "            position: absolute;\n" +
                "            width: 100%;\n" +
                "            height: 62.1px;\n" +
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
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"fondo\">\n" +
                "    <img class=\"img_fondo\" src=\""+urlImg+"\"></img>"+
                "        <div class=\"titulo1\">\n" +
                "            <p>La ONG <span class=\"verde\">\"Perú Te Quiero Limpio\"</span> dedicada a la preservación del medio ambiente</p>\n" +
                "        </div>\n" +
                "        <div class=\"titulo2\">\n" +
                "            <p>Otorga esta constancia a </p>\n" +
                "        </div>"+
                "        <div class=\"NombreVoluntario\">\n" +
                "            <p>" + plantillaDto.getStatusVoluntarioModulo().getVoluntario().getNombre() + "</p>\n" +
                "        </div>\n" +
                "        <div class=\"DescripcionVoluntario\">\n" +
                "            <p>En reconocimiento a su arduo trabajo y valioso aporte personal como voluntario para el éxito del <span class=\"nombreActividad\">" + plantillaDto.getNombreModulo() + "</span> <span style=\"font-weight: 700;\">, realizado durante los meses de " + plantillaDto.getMesInicio() +" y "+ plantillaDto.getMesFin() + "</span>. La constancia a continuación se emite por un total de <span class=\"horasActivdad\">" + plantillaDto.getStatusVoluntarioModulo().getHoras() + "</span> horas de trabajo voluntario.</p>\n" +
                "        </div>\n" +
                "        <div class=\"FechaGeneralActividad\">\n" +
                "            <p>" + plantillaDto.getFechaGeneralActividad() + "</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
    public static void convertirHTMLaPDF(String html, String rutaPDF)   {
        System.out.println("Iniciando proceso de conversión");
        // Obtenemos la ruta del directorio
        File directorio = new File(rutaPDF).getParentFile();
        // Verificamos si el directorio existe, si no, lo creamos
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado exitosamente: " + directorio.getAbsolutePath());
            } else {
                System.out.println("Error al crear el directorio: " + directorio.getAbsolutePath());
                return; // Salimos del método si no se pudo crear el directorio
            }
        }
        try (OutputStream outputStream = new FileOutputStream(rutaPDF)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream, true);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Proceso de conversión finalizado");
    }
}
