package com.example.bdtel.Controladores;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

public class Report extends JFrame {

    private static final long serialVersionUID = 1L;

    public void showReportSimple() throws JRException, ClassNotFoundException, SQLException {

        String servidor = "jdbc:mariadb://localhost:5555/moviles?useSSL=false";
        String usuario = "root";
        String passwd = "adminer";

        Connection conexionBBDD;
        // Nos conectamos
        conexionBBDD = DriverManager.getConnection(servidor, usuario, passwd);

        //  Block of code to try
        String reportSrcFile = "src/main/resources/jasperreport/ReporteBasico.jrxml";

        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

        // Ejemplo de definición de parámetros para el informe

        HashMap<String, Object> parameters = new HashMap<String, Object>();


        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, conexionBBDD);
        if (print.getPages().size() > 0) {
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados para el informe.");
        }

    }

    public void showReportConSubreport(String marca) throws JRException, ClassNotFoundException, SQLException {

        String servidor = "jdbc:mariadb://localhost:5555/moviles?useSSL=false";
        String usuario = "root";
        String passwd = "adminer";

        Connection conexionBBDD;
        // Nos conectamos
        conexionBBDD = DriverManager.getConnection(servidor, usuario, passwd);

        //  Block of code to try
        String reportSrcFile = "src/main/resources/jasperreport/InformeWithSubreport.jrxml";
        String subReportSrcFile = "src/main/resources/jasperreport/SubReportConTabla.jrxml";

        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportSrcFile);

        HashMap<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("Subreport", jasperSubReport);
        parameters.put("Marca", marca);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conexionBBDD);
        if (print.getPages().size() > 0) {
            JRViewer viewer = new JRViewer(print);
            viewer.setOpaque(true);
            viewer.setVisible(true);
            this.add(viewer);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados para el informe.");
        }

    }


}