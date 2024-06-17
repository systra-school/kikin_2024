/**
 * ファイル名：WorkDateRequestCheckForm.java
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
 * 説明：出勤希望日確認フォーム
 * @author naraki
 *
 */
public class WorkDateRequestCheckForm extends ActionForm {

    /** 出勤希望確認リスト */
    private List<WorkDateRequestCheckBean> workDateRequestCheckBeanList;
    /** 日付リスト */
    private List<DateBean> dateBeanList;
    /** 年月 */
    private String yearMonth;
    /** シフトコンボ */
    private Map<String, String> shiftCmbMap;
    /** 年月コンボ */
    private Map<String, String> yearMonthCmbMap;

    /** ページング用 */
    private String paging;
    /** オフセット */
    private int offset;
    /** 表示ページ */
    private int countPage;
    /** 最大ページ */
    private int maxPage;
    
	public List<WorkDateRequestCheckBean> getWorkDateRequestCheckBeanList() {
		return workDateRequestCheckBeanList;
	}
	public void setWorkDateRequestCheckBeanList(List<WorkDateRequestCheckBean> workDateRequestCheckBeanList) {
		this.workDateRequestCheckBeanList = workDateRequestCheckBeanList;
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
	public String getPaging() {
		return paging;
	}
	public void setPaging(String paging) {
		this.paging = paging;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getCountPage() {
		return countPage;
	}
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
}
