package za.co.ajk.incidentman.web.rest.testrest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAPIClass {
    
    @GetMapping("/names")
    public Collection<Reservation> getNames() {
        List<Reservation> aList = new ArrayList<>();
        IntStream.range(1, 10).forEach(i -> aList.add(new Reservation(i, "Reservation " + i)));
        return aList;
    }
}
