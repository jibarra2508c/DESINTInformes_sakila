package informe;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class GeneradorInforme {

    public static void main(String[] args) {
        // 1. Paràmetres de connexió a la base de dades (MariaDB).
        //    Es llegeixen de variables d'entorn per NO deixar credencials al codi.
        //    Valors per defecte: instància local amb la BD Sakila carregada.
        String url = getenvOrDefault("DB_URL", "jdbc:mariadb://localhost:3306/sakila");
        String usuari = getenvOrDefault("DB_USER", "root");
        String password = getenvOrDefault("DB_PASSWORD", "");

        // 2. Rutes dels fitxers
        String fitxerEntrada = "src/main/resources/Exercisi11.jrxml";
        String fitxerSortida = "Informe_Sakila_Socis.pdf";

        Connection conn = null;

        try {
            System.out.println("Connectant a la base de dades...");
            conn = DriverManager.getConnection(url, usuari, password);

            System.out.println("Compilant l'informe...");
            // Compila el fitxer .jrxml per generar un fitxer executable .jasper en memòria
            JasperReport report = JasperCompileManager.compileReport(fitxerEntrada);

            // 3. Paràmetres de l'informe (en aquest cas buit, però necessari per la signatura del mètode)

            Map<String, Object> parameters = new HashMap<>();

            System.out.println("Omplint l'informe amb dades...");
            // Omple l'informe amb la connexió SQL i els paràmetres
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, conn);

            System.out.println("Generant el PDF...");
            // Exporta l'objecte JasperPrint a un fitxer PDF físic
            JasperExportManager.exportReportToPdfFile(jasperPrint, fitxerSortida);

            System.out.println("Èxit! L'informe s'ha generat correctament a: " + fitxerSortida);

        } catch (JRException e) {
            System.err.println("Error de JasperReports: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error de connexió SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Tancar la connexió sempre
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /** Retorna la variable d'entorn {@code clau} o, si no està definida o és buida, el valor per defecte. */
    private static String getenvOrDefault(String clau, String valorPerDefecte) {
        String valor = System.getenv(clau);
        return (valor == null || valor.isEmpty()) ? valorPerDefecte : valor;
    }
}