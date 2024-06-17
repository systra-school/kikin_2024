/**
 * ファイル名：BaseEmployeeShiftRegisterForm.java
 *
 * 変更履歴
 * 1.0  2010/10/05 Satoshi.Sugi
 */
package form.bse;

/**
 * 説明：社員基本シフトフォームクラス
 * @author sugi
 *
 */
public class BaseEmployeeShiftRegisterForm {

	/* 社員名 */
	private String employeeName = "";
	/* 日曜日 */
	private String sunday  = "";
	/* 月曜日 */
	private String monday  = "";
	/* 火曜日 */
	private String tuesday = "";
	/* 水曜日 */
	private String wednesday = "";
	/* 木曜日 */
	private String thursday = "";
	/* 金曜日 */
	private String friday = "";
	/* 土曜日 */
	private String saturday ="";

	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getSunday() {
		return sunday;
	}
	public void setSunday(String sunday) {
		this.sunday = sunday;
	}
	public String getMonday() {
		return monday;
	}
	public void setMonday(String monday) {
		this.monday = monday;
	}
	public String getTuesday() {
		return tuesday;
	}
	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}
	public String getWednesday() {
		return wednesday;
	}
	public void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}
	public String getThursday() {
		return thursday;
	}
	public void setThursday(String thursday) {
		this.thursday = thursday;
	}
	public String getFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.friday = friday;
	}
	public String getSaturday() {
		return saturday;
	}
	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}
}