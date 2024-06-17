/**
 * ファイル名：WorkDateRequestCheckDto.java
 *
 * 変更履歴
 * 1.0  2010/10/06 Kazuya.Naraki
 */
package business.dto.shk;

/**
 * 説明：出勤希望日入力Dto
 * @author naraki
 *
 */
public class WorkDateRequestCheckDto {
    /** 社員ID */
    private String employeeId;
    /** 社員名 */
    private String employeeName;
    /** 年月日 */
    private String yearMonthDay;
    /** 希望シフト */
    private String myRequestShiftId;
    /** 希望シフトシンボル */
    private String myRequestSymbol;
    
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getYearMonthDay() {
		return yearMonthDay;
	}
	public void setYearMonthDay(String yearMonthDay) {
		this.yearMonthDay = yearMonthDay;
	}
	public String getMyRequestShiftId() {
		return myRequestShiftId;
	}
	public void setMyRequestShiftId(String myRequestShiftId) {
		this.myRequestShiftId = myRequestShiftId;
	}
	public String getMyRequestSymbol() {
		return myRequestSymbol;
	}
	public void setMyRequestSymbol(String myRequestSymbol) {
		this.myRequestSymbol = myRequestSymbol;
	}


}
