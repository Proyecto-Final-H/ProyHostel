package domainapp.modules.simple.reportes;

import domainapp.modules.simple.caja.Caja;
import domainapp.modules.simple.reportes.CajaRepo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.isis.applib.value.Blob;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EjecutarReportes {

    public Blob ListadoCajaPDF(List<Caja> cajas) throws JRException, IOException {

        List<CajaRepo> cajaRepos = new ArrayList<>();
        cajaRepos.add(new CajaRepo());

        for (Caja caja : cajas) {
            CajaRepo cajaRepo = new CajaRepo(caja.RepoName());
            cajaRepos.add(cajaRepo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(cajaRepos);
        return GenerarArchivoPDF("ListadoCajaDesing.jrxml", "Listado de Caja.pdf", ds);
    }
    private Blob GenerarArchivoPDF(String archivoDesing, String nombreSalida, JRBeanCollectionDataSource ds) throws JRException, IOException{

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoDesing);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        byte[] contentBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        return new Blob(nombreSalida, "application/pdf", contentBytes);
    }
}

