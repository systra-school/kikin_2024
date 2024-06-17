/**
 * ファイル名：WorkRecordInputForm.java
 *
 * 変更履歴
 * 1.0  2010/11/04 Kazuya.Naraki
 */
package form.act;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import form.common.DateBean;

/**
 * 説明：勤務実績入力確認フォーム
 * @author naraki
 *
 */
public class WorkRecordInputForm extends ActionForm {

    /** 勤務実績入力確認BeanList */
    private List<WorkRecordInputBean> workRecordInputList;
    /** 日付リスト */
    private List<DateBean> dateBeanList;
    /** 年月コンボ */
    private Map<String, String> yearMonthCmbMap;
    /** 年月 */
    private String yearMonth;
    /** 社員コンボ */
    private Map<String, String> employeeCmbMap;
    /** 社員ID */
    private String employeeId;
    /** 社員名 */
    private String employeeName;

    /** ページング用 */
    private String paging;

	public List<WorkRecordInputBean> getWorkRecordInputList() {
		return workRecordInputList;
	}

	public void setWorkRecordInputList(List<WorkRecordInputBean> workRecordInputList) {
		this.workRecordInputList = workRecordInputList;
	}

	public List<DateBean> getDateBeanList() {
		return dateBeanList;
	}

	public void setDateBeanList(List<DateBean> dateBeanList) {
		this.dateBeanList = dateBeanList;
	}

	public Map<String, String> getYearMonthCmbMap() {
		return yearMonthCmbMap;
	}

	public void setYearMonthCmbMap(Map<String, String> yearMonthCmbMap) {
		this.yearMonthCmbMap = yearMonthCmbMap;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Map<String, String> getEmployeeCmbMap() {
		return employeeCmbMap;
	}

	public void setEmployeeCmbMap(Map<String, String> employeeCmbMap) {
		this.employeeCmbMap = employeeCmbMap;
	}

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

	public String getPaging() {
		return paging;
	}

	public void setPaging(String paging) {
		this.paging = paging;
	}
}