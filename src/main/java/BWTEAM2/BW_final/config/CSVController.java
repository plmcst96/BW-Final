package BWTEAM2.BW_final.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class CSVController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/uploadTown")
    public String uploadTownCSV(@RequestParam("file")MultipartFile file) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){ //il BufferedReader serve per leggere il contenuto del CSV, InputStream restituisce il contenuto
            //legge tutte le righe del file saltando la prima con l'intestazione della tabella
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){// legge riga per riga
                String[] data = line.split(";"); // ogni riga è separata da uno spazio
                if(data.length >= 4) {
                    String provinceCode = data[0];
                    String municipalSerialNum = data[1];
                    String name = data[2];
                    String province = data[3];

                    // query di inserimento dati
                    String sql = "INSERT INTO towns (province_code, municipal_serial_num, name, province_name) VALUES (?, ?, ?, ?) ";
                    jdbcTemplate.update(sql, provinceCode, municipalSerialNum, name, province);
                }else{
                    System.out.println("Riga non valida " + line);
                }
            }
            return "File importato con successo";
        } catch (IOException e){
            e.printStackTrace();
            return "Errore durante l'import del file!";
        }
    }

    @PostMapping("/uploadProvince")
    public String uploadProvinceCSV(@RequestParam("file")MultipartFile file) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){ //il BufferedReader serve per leggere il contenuto del CSV, InputStream restituisce il contenuto
            //legge tutte le righe del file saltando la prima con l'intestazione della tabella
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){// legge riga per riga
                String[] data = line.split(";"); // ogni riga è separata da uno spazio
                if(data.length >= 3) {
                    String provinceCode = data[0];
                    String name = data[1];
                    String region = data[2];


                    // query di inserimento dati
                    String sql = "INSERT INTO provinces (province_code, province_name, region) VALUES (?, ?, ?) ";
                    jdbcTemplate.update(sql, provinceCode, name ,region );
                }else{
                    System.out.println("Riga non valida " + line);
                }
            }
            return "File importato con successo";
        } catch (IOException e){
            e.printStackTrace();
            return "Errore durante l'import del file!";
        }
    }
}
