package ru.drozdov.MyFirstTestAppSpringBoot.collections;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class CollectionsController {
    private ArrayList<String> array;
    private HashMap<Integer, String> hashMap;
    private int count = 0;

    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam(required = true) String s) {
        if (array == null) {
            array = new ArrayList<String>();
            return "ArrayList успешно создан!";
        }
        array.add(s);
        return "Значение " + s + " записано в ArrayList";
    }

    @GetMapping("/show-array")
    public ArrayList<String> showArrayList() {
        return array;
    }

    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam(required = true) String s) {
        if (count == 0) {
            hashMap = new HashMap<Integer, String>();
            count += 1;
            return "HashMap создана!";
        }
        hashMap.put(count, s);
        count += 1;
        return "Значение " + s + " записано в HashMap";
    }

    @GetMapping("/show-map")
    public HashMap<Integer, String> showHashMap() {
        return hashMap;
    }

    @GetMapping("/show-all-length")
    public String showAllLength() {
        int sizeArray = 0;
        int sizeHashMap = 0;
        if (array != null) {
            sizeArray = array.size();
        }
        if (hashMap != null) {
            sizeHashMap = hashMap.size();
        }
        return "Размер ArrayList: " + sizeArray + "\nРазмер HashMap: " + sizeHashMap;
    }
}
