package form.cmn;


import org.apache.struts.validator.ValidatorForm;

public final class LoginForm extends ValidatorForm {

	// 社員ID
	String employeeId;
	// パスワード
	String password;
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}