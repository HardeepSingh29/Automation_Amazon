package kdsfj;

import com.vs.executor.CukeExecutor;
import com.vs.libraries.GenericLib;
import com.vs.libraries.Log;

import io.cucumber.java.en.Given;

public class Steps {

	
	@Given("^User open the \"([^\"]*)\" url$")
	public void user_open_the_url(String urlname) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		GenericLib.openURL(urlname);
		Log.logInfo(CukeExecutor.class, "Url has Opened");
	    //throw new PendingException();
	}
}
