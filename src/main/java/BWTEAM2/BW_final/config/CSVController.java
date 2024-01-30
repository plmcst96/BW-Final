package BWTEAM2.BW_final.config;

import BWTEAM2.BW_final.entities.Province;
import BWTEAM2.BW_final.entities.Town;
import BWTEAM2.BW_final.repositories.ProvinceDAO;
import BWTEAM2.BW_final.repositories.TownDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@RestController
public class CSVController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProvinceDAO provinceDAO;

    @Autowired
    private TownDAO townDAO;

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
                    String provinceName = data[3];

                    //controlla se la provincia esiste nel DB
                    Optional<Province> provinceDB;
                    if(provinceName.equals("Bolzano")){
                        provinceDB = provinceDAO.findByName("Bolzano/Bozen");
                       System.out.println("Bolzano: " + provinceDB);
                    }else{
                       provinceDB = provinceDAO.findByName(provinceName);
                    }
                    //System.out.println(provinceName);


                    //System.out.println(provinceDB);
                    if(provinceDB.isPresent()){
                        // crea istanza di Town
                        Town town = new Town();
                        town.setMunicipalSerialNumber(municipalSerialNum);
                        town.setName(name);
                        town.setProvinceCode(provinceCode);
                        town.setProvince(provinceDB.get());



                        townDAO.save(town);
                        System.out.println("*******" + town);

                    }else{
                        System.out.println("Provincia con nome " + provinceName + " non trovata!");
                        continue; // passa alla prossima riga se la provincia non è stata trovata
                    }
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

                    //crea istanza della provincia
                    Province province = new Province();
                    province.setProvinceCode(provinceCode);
                    province.setName(name);
                    province.setRegion(region);
                    if(name.equals("Carbonia Iglesias")  || name.equals("Medio Campidano")){
                        province.setName("Sud Sardegna") ;
                        province.setProvinceCode("SU");
                    }else if(name.equals("Ogliastra")){
                        province.setName("Nuoro");
                        province.setProvinceCode("NU");
                    }else if(name.equals("Olbia Tempio")){
                        province.setName("Sassari");
                        province.setProvinceCode("SS");
                    } else if(name.equals("Bolzano")){
                        province.setName("Bolzano/Bozen");
                        province.setProvinceCode("BZ");
                    } else if(name.equals("Verbania")) {
                        province.setName("Verbano-Cusio-Ossola");
                        province.setProvinceCode("VCO");
                    }else if(name.equals("Aosta")){
                        province.setName("Valle d'Aosta/Vallée d'Aoste");
                        province.setProvinceCode("AO");
                    }else if(name.equals("Monza-Brianza")){
                        province.setName("Monza e della Brianza");
                        province.setProvinceCode("MB");
                    }else if(name.equals("La-Spezia")){
                        province.setName("La Spezia");
                        province.setProvinceCode("SP");
                    }else if(name.equals("Reggio-Emilia")){
                        province.setName("Reggio nell'Emilia");
                        province.setProvinceCode("RE");
                    }else if(name.equals("Forli-Cesena")){
                        province.setName("Forlì-Cesena");
                        province.setProvinceCode("FC");
                    }else if(name.equals("Pesaro-Urbino")) {
                        province.setName("Pesaro e Urbino");
                        province.setProvinceCode("PU");
                    }else if(name.equals("Ascoli-Piceno")) {
                        province.setName("Ascoli Piceno");
                        province.setProvinceCode("AP");
                    }else if(name.equals("Reggio-Calabria")) {
                        province.setName("Reggio Calabria");
                        province.setProvinceCode("RC");
                    }else if(name.equals("Vibo-Valentia")) {
                        province.setName("Vibo Valentia");
                        province.setProvinceCode("VV");
                    }


                            provinceDAO.save(province);
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
