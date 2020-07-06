import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {

        Map<Integer, Integer> testmap = new HashMap<>();


        List<LolzDTO> hehe = Arrays.asList(new LolzDTO("HEHE"), new LolzDTO(null));

        Map<String, Integer> testMap = new HashMap<>();
        testMap.put("HEHE", 20);

        for (LolzDTO lolzDTO : hehe) {
            Integer integer = testMap.get(lolzDTO.getValue());

            if(integer != null)
                System.out.println(integer);
        }
    }

    class LolzDTO {

        public String value;

        public LolzDTO(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

}
