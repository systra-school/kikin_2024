/**
 * ファイル名：MonthlyBaseShiftDto.java
 *
 * 変更履歴
 * 1.0  2010/11/03 Kazuya.Naraki
 */
package business.dto.mth;

import java.util.Date;

/**
 * 説明：基本シフトDto
 * @author nishioka
 *
 */
public class MonthlyBaseShiftDto {
    /** 社員ID */
    private String employeeId;
    /** 社員名 */
    private String employeeName;
    /** 月曜日シフト */
    private String shiftIdOnMonday;
    /** 火曜日シフト */
    private String shiftIdOnTuesday;
    /** 水曜日シフト */
    private String shiftIdOnWednesday;
    /** 木曜日シフト */
    private String shiftIdOnThursday;
    /** 金曜日シフト */
    private String shiftIdOnFriday;
    /** 土曜日シフト */
    private String shiftIdOnSaturday;
    /** 日曜日シフト */
    private String shiftIdOnSunday;
    /** 作成ユーザID */
    private String CreaterEmployeeId;
    /** 作成日付 */
    private Date CreationDatetime;
    /** 更新ユーザID */
    private String UpdaterEmployeeId;
    /** 更新日付 */
    private Date UpdateDatetime;
    
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
	public String getShiftIdOnMonday() {
		return shiftIdOnMonday;
	}
	public void setShiftIdOnMonday(String shiftIdOnMonday) {
		this.shiftIdOnMonday = shiftIdOnMonday;
	}
	public String getShiftIdOnTuesday() {
		return shiftIdOnTuesday;
	}
	public void setShiftIdOnTuesday(String shiftIdOnTuesday) {
		this.shiftIdOnTuesday = shiftIdOnTuesday;
	}
	public String getShiftIdOnWednesday() {
		return shiftIdOnWednesday;
	}
	public void setShiftIdOnWednesday(String shiftIdOnWednesday) {
		this.shiftIdOnWednesday = shiftIdOnWednesday;
	}
	public String getShiftIdOnThursday() {
		return shiftIdOnThursday;
	}
	public void setShiftIdOnThursday(String shiftIdOnThursday) {
		this.shiftIdOnThursday = shiftIdOnThursday;
	}
	public String getShiftIdOnFriday() {
		return shiftIdOnFriday;
	}
	public void setShiftIdOnFriday(String shiftIdOnFriday) {
		this.shiftIdOnFriday = shiftIdOnFriday;
	}
	public String getShiftIdOnSaturday() {
		return shiftIdOnSaturday;
	}
	public void setShiftIdOnSaturday(String shiftIdOnSaturday) {
		this.shiftIdOnSaturday = shiftIdOnSaturday;
	}
	public String getShiftIdOnSunday() {
		return shiftIdOnSunday;
	}
	public void setShiftIdOnSunday(String shiftIdOnSunday) {
		this.shiftIdOnSunday = shiftIdOnSunday;
	}
	public String getCreaterEmployeeId() {
		return CreaterEmployeeId;
	}
	public void setCreaterEmployeeId(String CreaterEmployeeId) {
		this.CreaterEmployeeId = CreaterEmployeeId;
	}
	public Date getCreationDatetime() {
		return CreationDatetime;
	}
	public void setCreationDatetime(Date CreationDatetime) {
		this.CreationDatetime = CreationDatetime;
	}
	public String getUpdaterEmployeeId() {
		return UpdaterEmployeeId;
	}
	public void setUpdaterEmployeeId(String UpdaterEmployeeId) {
		this.UpdaterEmployeeId = UpdaterEmployeeId;
	}
	public Date getUpdateDatetime() {
		return UpdateDatetime;
	}
	public void setUpdateDatetime(Date UpdateDatetime) {
		this.UpdateDatetime = UpdateDatetime;
	}

   
}
