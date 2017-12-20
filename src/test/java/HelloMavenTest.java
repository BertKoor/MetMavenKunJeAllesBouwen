public class HelloMavenTest {

    public void testGetNameNullArgs() {
        String[] args = null;
        genericTest(args, "Maven");
    }
    
    public void testGetNameNoArgs() {
        String[] args = {};
        genericTest(args, "Maven");
    }
    
    public void testGetNameOneArg() {
        String[] args = {"FooBar"};
        genericTest(args, "FooBar");
    }
    
    public void testGetNameMoreArgs() {
        String[] args = {"Foo", "Bar", "Code"};
        genericTest(args, "Foo");
    }
    
    private void genericTest(String[] args, String expected) {
        String result = HelloMaven.getName(args);
        if (!expected.equals(result)) {
            String msg = "Test with args='" + args + "' failed.\n" +
                    "Expected '" + expected + "'\n" +
                    "but was  '" + result + "'";
            throw new RuntimeException(msg);
        }
    }
}
