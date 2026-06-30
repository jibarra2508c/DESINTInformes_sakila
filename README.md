# Informes Sakila con JasperReports

Aplicación Java que genera un **informe PDF** a partir de la base de datos de ejemplo **Sakila**, usando **JasperReports**.

El programa se conecta a una base de datos **MariaDB**, compila una plantilla de informe `.jrxml`, la rellena con los datos de la consulta y exporta el resultado a un fichero PDF.

## Funcionamiento

1. Conexión JDBC a MariaDB (`mariadb-java-client`).
2. Compilación de la plantilla `src/main/resources/Exercisi11.jrxml` con `JasperCompileManager`.
3. Relleno del informe con la conexión SQL (`JasperFillManager`).
4. Exportación a PDF con `JasperExportManager` → `Informe_Sakila_Socis.pdf`.

## Tecnologías

- **Java 23** + **Maven**
- **JasperReports 7.0.0** (`jasperreports`, `jasperreports-pdf`)
- **MariaDB JDBC** 3.1.2
- SLF4J (logging), Apache Commons Collections

## Configuración de la conexión

Los datos de conexión **no están en el código**: se leen de variables de entorno, con valores por defecto para una instancia local de MariaDB con la BD Sakila cargada.

| Variable | Por defecto |
|---|---|
| `DB_URL` | `jdbc:mariadb://localhost:3306/sakila` |
| `DB_USER` | `root` |
| `DB_PASSWORD` | *(vacío)* |

## Ejecución

```bash
# Opcional: ajusta la conexión a tu BD
export DB_URL="jdbc:mariadb://localhost:3306/sakila"
export DB_USER="tu_usuario"
export DB_PASSWORD="tu_password"

mvn compile
mvn exec:java -Dexec.mainClass=informe.GeneradorInforme
```

Genera `Informe_Sakila_Socis.pdf` en la raíz del proyecto (incluido un ejemplo del resultado).

---
Autor: **Javier Ibarra**
