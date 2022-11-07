import java.util.*;
import java.util.stream.Collectors;

public class arte {
    private String message;
    Scanner strings =new Scanner(System.in);
    public void start(){
        System.out.println(" give message to szyfornwanie");
        message = strings.nextLine();
        System.out.println(zliczWystapienia(message));
    }
    public LinkedHashMap<Character, Map<Float, Float>> zliczWystapienia(String ciagZnakow){
        LinkedHashMap<Character, Float> sorted = new LinkedHashMap<>();
        Map<Character, Long> mapStringToCount = ciagZnakow
                .chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        mapStringToCount.entrySet().stream().sorted(Map.Entry.comparingByKey())
        .forEachOrdered(entry -> sorted.put(entry.getKey(), Float.valueOf(entry.getValue())));

        return przedział(sorted);
    }
    private LinkedHashMap<Character, Map<Float, Float>> przedział(Map<Character, Float> wystomp){
        boolean jestP = true;
        LinkedHashMap<Character, Map<Float, Float>> result = new LinkedHashMap<>();
        for(Map.Entry<Character, Float> iter :wystomp.entrySet()){
            Map<Float, Float>nPrzedzial= new HashMap<>();
            if(jestP){
                nPrzedzial.put((float) 0, (iter.getValue()/message.length()));
                result.put(iter.getKey(), nPrzedzial);
                jestP=false;
            }else{
                float lastDigital= result.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey((Comparator.reverseOrder()))).findFirst().get()
                                .getValue().values().stream().findFirst().get();

                nPrzedzial.put(lastDigital, lastDigital+iter.getValue()/message.length());
                result.put(iter.getKey(),nPrzedzial);
            }

        }
        return result;
    }
}
