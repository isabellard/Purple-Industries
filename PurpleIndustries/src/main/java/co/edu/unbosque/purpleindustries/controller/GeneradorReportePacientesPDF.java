package co.edu.unbosque.purpleindustries.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import co.edu.unbosque.purpleindustries.model.Paciente;

public class GeneradorReportePacientesPDF {

    private static final Font HEADER_FONT = new Font(Font.HELVETICA, 8, Font.BOLD, new Color(41, 128, 185));
    private static final Font CELL_FONT   = new Font(Font.HELVETICA, 7, Font.NORMAL, Color.BLACK);

    private static final Color COLOR_TITULO     = new Color(44, 62, 80);
    private static final Color COLOR_CELDAS     = new Color(236, 240, 241);
    private static final Color COLOR_TITULO_TXT = new Color(39, 174, 96);

    private static final int FONT_SIZE_TITULO = 22;

    public void generarReporteMensualPacientes(List<Paciente> pacientes, YearMonth periodo, String nombreArchivo) {
        try {
        	String userHome = System.getProperty("user.home");
        	String destino = userHome + File.separator + "Downloads" 
        	        + File.separator + nombreArchivo +"_" +"Reporte_" + periodo + ".pdf";

        	
            List<Paciente> delMes = filtrarPorMes(pacientes, periodo);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(destino));
            document.open();

            document.add(crearTituloPrincipal(periodo));
            document.add(crearSubtitulo("Reporte mensual de pacientes ingresados al aplicativo"));
            document.add(new Paragraph(" "));

            document.add(crearSeccion("Resumen del mes"));
            document.add(crearResumenBasico(delMes, periodo));
            document.add(new Paragraph(" "));

            document.add(crearSeccion("Listado de pacientes ingresados"));
            document.add(crearTablaPacientes(delMes));

            document.newPage();
            document.add(crearSeccion("Gráfica: pacientes por triage"));
            Image chart = crearGraficaPacientesPorTriage(delMes);
            if (chart != null) {
                chart.setAlignment(Element.ALIGN_CENTER);
                document.add(chart);
            } else {
                document.add(new Paragraph("No hay datos suficientes para generar la gráfica.", CELL_FONT));
            }

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Paciente> filtrarPorMes(List<Paciente> pacientes, YearMonth periodo) {
        List<Paciente> out = new ArrayList<>();
        for (Paciente p : pacientes) {
            LocalDate ingreso = p.getFechaIngreso();
            if (ingreso == null) continue;
            if (YearMonth.from(ingreso).equals(periodo)) out.add(p);
        }
        return out;
    }

    private Paragraph crearTituloPrincipal(YearMonth periodo) {
        String mes = periodo.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("es-CO"));
        String titulo = "Reporte Mensual de Pacientes — " + capitalizar(mes) + " " + periodo.getYear();

        Paragraph p = new Paragraph(titulo,
                new Font(Font.HELVETICA, FONT_SIZE_TITULO, Font.BOLD, COLOR_TITULO_TXT));
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(10f);
        return p;
    }

    private Paragraph crearSubtitulo(String texto) {
        Paragraph p = new Paragraph(texto, new Font(Font.HELVETICA, 10, Font.NORMAL, Color.DARK_GRAY));
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(15f);
        return p;
    }

    private Paragraph crearSeccion(String titulo) {
        Paragraph p = new Paragraph(titulo, new Font(Font.HELVETICA, 12, Font.BOLD, COLOR_TITULO));
        p.setSpacingAfter(8f);
        return p;
    }

    private Paragraph crearResumenBasico(List<Paciente> delMes, YearMonth periodo) {
        int total = delMes.size();
        int triage1 = 0, triage2 = 0, triage3 = 0, triage4 = 0, triage5 = 0;

        for (Paciente p : delMes) {
            switch (p.getTriage()) {
                case 1: triage1++; break;
                case 2: triage2++; break;
                case 3: triage3++; break;
                case 4: triage4++; break;
                case 5: triage5++; break;
                default: break;
            }
        }

        LocalDate inicio = periodo.atDay(1);
        LocalDate fin = periodo.atEndOfMonth();

        String texto =
                "• Periodo: " + inicio + " a " + fin + "\n" +
                "• Total de pacientes ingresados: " + total + "\n" +
                "• Conteo por triage: 1=" + triage1 + " | 2=" + triage2 + " | 3=" + triage3 +
                " | 4=" + triage4 + " | 5=" + triage5;

        Paragraph p = new Paragraph(texto, new Font(Font.HELVETICA, 9, Font.NORMAL, Color.BLACK));
        p.setSpacingAfter(10f);
        return p;
    }

    private PdfPTable crearTablaPacientes(List<Paciente> pacientes) throws Exception {
        String[] headers = {
            "Documento", "Nombre", "Fecha Nac.", "Altura", "Peso", "RH", "Triage", "Diagnóstico", "Fecha Ingreso"
        };

        PdfPTable tabla = new PdfPTable(headers.length);
        tabla.setWidthPercentage(100);
        tabla.setSpacingBefore(5f);
        tabla.setSpacingAfter(10f);

        for (String h : headers) {
            PdfPCell celda = new PdfPCell(new Paragraph(h, HEADER_FONT));
            celda.setBackgroundColor(COLOR_TITULO);
            celda.setPadding(5);
            tabla.addCell(celda);
        }

        for (Paciente p : pacientes) {
            tabla.addCell(crearCelda(s(p.getDocumento()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(s(p.getNombre()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(s(p.getFechaDeNacimiento()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(String.valueOf(p.getAltura()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(s(p.getPeso()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(s(p.getRh()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(String.valueOf(p.getTriage()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(s(p.getDiagnostico()), CELL_FONT, COLOR_CELDAS));
            tabla.addCell(crearCelda(p.getFechaIngreso() != null ? p.getFechaIngreso().toString() : "-", CELL_FONT, COLOR_CELDAS));
        }

        return tabla;
    }

    private Image crearGraficaPacientesPorTriage(List<Paciente> pacientes) {
        try {
            if (pacientes.isEmpty()) return null;

            int[] c = new int[6];
            for (Paciente p : pacientes) {
                int t = p.getTriage();
                if (t >= 1 && t <= 5) c[t]++;
            }

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.addValue(c[1], "Pacientes", "Triage 1");
            dataset.addValue(c[2], "Pacientes", "Triage 2");
            dataset.addValue(c[3], "Pacientes", "Triage 3");
            dataset.addValue(c[4], "Pacientes", "Triage 4");
            dataset.addValue(c[5], "Pacientes", "Triage 5");

            JFreeChart chart = ChartFactory.createBarChart(
                    "", "Triage", "Cantidad", dataset
            );

            BufferedImage bufferedImage = chart.createBufferedImage(520, 320);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);

            return Image.getInstance(baos.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private PdfPCell crearCelda(String texto, Font fuente, Color fondo) {
        PdfPCell celda = new PdfPCell(new Paragraph(texto, fuente));
        celda.setBackgroundColor(fondo);
        celda.setPadding(5);
        return celda;
    }

    private String s(Object o) { return (o == null) ? "-" : String.valueOf(o); }

    private String capitalizar(String x) {
        if (x == null || x.isEmpty()) return x;
        return x.substring(0, 1).toUpperCase() + x.substring(1);
    }
}

