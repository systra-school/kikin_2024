/**
 * ファイル名：WorkDateRequestInputForm.java
 *
 * 変更履歴
 * 1.0  2010/10/06 Kazuya.Naraki
 */
package form.shk;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

import form.common.DateBean;

/**
 * 説明：出勤希望日入力フォーム
 * @author naraki
 *
 */
public class WorkDateRequestInputForm extends ActionForm {

    /** 出勤希望入力リスト */
    private List<WorkDateRequestInputBean> workDateRequestInputBeanList;
    /** 日付リスト */
    private List<DateBean> dateBeanList;
    /** 年月 */
    private String yearMonth = "";
    /** シフトコンボ */
    private Map<String, String> shiftCmbMap;
    /** 年月コンボ */
    private Map<String, String> yearMonthCmbMap;
    
	public List<WorkDateRequestInputBean> getWorkDateRequestInputBeanList() {
		return workDateRequestInputBeanList;
	}
	public void setWorkDateRequestInputBeanList(List<WorkDateRequestInputBean> workDateRequestInputBeanList) {
		this.workDateRequestInputBeanList = workDateRequestInputBeanList;
	}
	public List<DateBean> getDateBeanList() {
		return dateBeanList;
	}
	public void setDateBeanList(List<DateBean> dateBeanList) {
		this.dateBeanList = dateBeanList;
	}
	public String getYearMonth() {
		return yearMonth;
	}
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}
	public Map<String, String> getShiftCmbMap() {
		return shiftCmbMap;
	}
	public void setShiftCmbMap(Map<String, String> shiftCmbMap) {
		this.shiftCmbMap = shiftCmbMap;
	}
	public Map<String, String> getYearMonthCmbMap() {
		return yearMonthCmbMap;
	}
	public void setYearMonthCmbMap(Map<String, String> yearMonthCmbMap) {
		this.yearMonthCmbMap = yearMonthCmbMap;
	}
}
