import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class StartTest {

    private Example example = new Example();

    @Test
    public void testStart(){
        assertThat(example.exampleMethod(), containsString("example"));
    }

}
