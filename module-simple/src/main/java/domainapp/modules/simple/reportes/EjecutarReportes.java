package domainapp.modules.simple.reportes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.io.InputStream;
import org.joda.time.LocalDate;
import domainapp.modules.simple.caja.Caja;
import domainapp.modules.simple.reportes.CajaRepo;
import domainapp.modules.simple.gastos.Gastos;
import domainapp.modules.simple.gastos.RepoGastos;
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

public class EjecutarReportes {

   public Blob ListadoCajaPDF(List<Caja> cajas) throws JRException, IOException {

        List<CajaRepo> cajaRepos = new ArrayList<>();
        cajaRepos.add(new CajaRepo());

        for (Caja caja : cajas) {
            CajaRepo cajaRepo = new CajaRepo(caja.RepoName(),
                    caja.RepoImporte(),
                    caja.RepoFecha().toString("dd-MM-yyyy"),
                    caja.RepoNumerofactura(),
                    caja.RepoCondicioniva(),
                    caja.RepoCondicionvent()
                    );
            cajaRepos.add(cajaRepo);
        }
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(cajaRepos);
        return GenerarArchivoPDF("ListadoCajaDesing.jrxml", "Listado de Caja.pdf", ds);
    }

    public Blob ListadoGastosPDF(List<Gastos> gasto) throws JRException, IOException {

        List<GastosRepo> gastosRepos = new ArrayList<>();
        gastosRepos.add(new GastosRepo());

        for (Gastos gastos : gasto) {
            GastosRepo gastosRepo =new GastosRepo(
                    gastos.RepoName(),
                    gastos.RepoImporte(),
                    gastos.RepoNumeroFactura(),
                    gastos.RepoTipodegasto(),
                    gastos.RepoFecha().toString("dd-MM-yyyy"));
         gastosRepos.add(gastosRepo);
        }

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(gastosRepos);
        return GenerarArchivoPDF("ListadoGastosDesing.jrxml", "Listado de Gastos.pdf", ds);
    }

    private Blob GenerarArchivoPDF(String archivoDesing, String nombreSalida, JRBeanCollectionDataSource ds) throws JRException, IOException{

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoDesing);
        JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("ds", ds);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        byte[] contentBytes = JasperExportManager.exportReportToPdf(jasperPrint);
        return new Blob(nombreSalida,"application/pdf", contentBytes);
    }
}

