package utility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestReporter implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub		
	}

	/**
	 *  If test is successful, send email
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		String subject = "SET_EMAIL_SUBJECT";
		String message = "SET_EMAIL_MESSAGE";
		
		EmailSender.sendEmail(subject, message);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub	
	}

}
