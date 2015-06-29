package dendy.util.json;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * <p>Created by Dendy on 2015/6/25.
 *
 * @author Dendy
 * @version 0.1
 * @since 0.1
 */
public class JacksonUtil {
    //~ Static fields/initializers =====================================================================================
    private static final Logger LOG = LoggerFactory.getLogger(JacksonUtil.class);

    //~ Instance fields ================================================================================================
    private static ObjectMapper objectMapper = new ObjectMapper();

    //~ Methods ========================================================================================================

    private JacksonUtil() {

    }

    public static String toJson(Object o) throws IOException {
        if (o == null)
            return null;
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T fromObject(String json, Class<T> cls) throws IOException {
        if (json == null || "".equals(json))
            return null;
        return objectMapper.readValue(json, cls);
    }

    public static void main(String[] args) throws IOException {
        String json = JacksonUtil.toJson(new Test("test1", "value1", new Test("test2", "value2", null)));
        System.out.println(json);
        Test t = JacksonUtil.fromObject(json, Test.class);
        System.out.println(t);
        HashMap hashMap = JacksonUtil.fromObject(json, HashMap.class);
        System.out.println(hashMap.get("name"));
    }

    static class Test {
        private String name;
        private String value;
        private Test test;

        public Test() {

        }

        public Test(String name, String value, Test test) {
            this.name = name;
            this.value = value;
            this.test = test;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Test getTest() {
            return test;
        }

        public void setTest(Test test) {
            this.test = test;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    ", test=" + test +
                    '}';
        }
    }
}
