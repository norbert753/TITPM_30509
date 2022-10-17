import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class huffman {
    public static void main(String[] args){
        Scanner cin = new Scanner(System.in);
        String znaki;
        System.out.println("Podaj znaki");
        znaki = cin.nextLine();
        System.out.println(liczenieZnakow(znaki));

    }
    public static  Map<Character, Integer> liczenieZnakow (String znaki){
        HashSet<Character> usundupli = new HashSet<>(); //usuwanie duplikatow
        Map<Character, Integer> mapString= new HashMap<>();
        for(int i=0; i< znaki.length(); i++){
            usundupli.add(znaki.charAt(i));
        }
        for(Character znak: usundupli){
            int j = 0 ;
            for (Character compare : znaki.toCharArray()){
                if(compare.equals(znak)){
                    j++;
                }
            }
            mapString.put(znak, j);
        }
        return sotow(mapString);
    }
    public static  Map<Character, Integer> sotow(Map<Character, Integer>mapa){
        List<Map.Entry<Character, Integer>> sotow = new ArrayList<>(mapa.entrySet());
         sotow.sort(Map.Entry.comparingByValue());                                     //sortowanie
         Map<Character, Integer> wynsor = new LinkedHashMap<>();
         for ( Map.Entry<Character, Integer> pen1 : sotow) {
         wynsor.put(pen1.getKey(),pen1.getValue());

        }
    return wynsor;
    }


}
