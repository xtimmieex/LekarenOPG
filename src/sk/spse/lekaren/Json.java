package sk.spse.lekaren;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.util.List;

public class Json {

    public static void main(String[] args) throws Exception {

        // Hlavný objekt pre Jackson
        ObjectMapper mapper = new ObjectMapper();
        // Pridanie podpory pre java.time (LocalDate)
        mapper.registerModule(new JavaTimeModule());

        File file = new File("assets/lieky.json");

        // Parsovanie sa vykoná tu
        List<Liek> lieky = mapper.readValue(
                file,
                new TypeReference<List<Liek>>() {}
        );


        for (Liek liek : lieky) {
            System.out.println(liek);
        }


        // pekné odsadenie (pretty print)
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Chceme zapísať dátum v ISO formáte
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Zapísanie dát do súboru
        File output = new File("out/lieky.json");
        mapper.writeValue(output, lieky);
    }
}
