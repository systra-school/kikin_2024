/**
 * ファイル名：DailyShiftForm.java
 *
 * 変更履歴
 * 1.0  2010/10/23 Kazuya.Naraki
 */
package form.day;

import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * 説明：日別シフトフォーム
 * @author naraki
 *
 */
public class DailyShiftForm extends ActionForm {

    /** 日別シフトBeanリスト（表示一覧）*/
    private List<DailyShiftBean> dailyShiftBeanList;

    /** 表示対象日 */
    private String yearMonthDay;
    /** 表示対象日（画面表示用） */
    private String yearMonthDayDisplay;
    /** ページング用 */
    private String paging;
    
	public List<DailyShiftBean> getDailyShiftBeanList() {
		return dailyShiftBeanList;
	}
	public void setDailyShiftBeanList(List<DailyShiftBean> dailyShiftBeanList) {
		this.dailyShiftBeanList = dailyShiftBeanList;
	}
	public String getYearMonthDay() {
		return yearMonthDay;
	}
	public void setYearMonthDay(String yearMonthDay) {
		this.yearMonthDay = yearMonthDay;
	}
	public String getYearMonthDayDisplay() {
		return yearMonthDayDisplay;
	}
	public void setYearMonthDayDisplay(String yearMonthDayDisplay) {
		this.yearMonthDayDisplay = yearMonthDayDisplay;
	}
	public String getPaging() {
		return paging;
	}
	public void setPaging(String paging) {
		this.paging = paging;
	}

}
