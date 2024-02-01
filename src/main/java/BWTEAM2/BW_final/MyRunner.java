package BWTEAM2.BW_final;

import BWTEAM2.BW_final.services.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    TownService townService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello");

        System.out.println(townService.findByName("Torino"));

    }



}