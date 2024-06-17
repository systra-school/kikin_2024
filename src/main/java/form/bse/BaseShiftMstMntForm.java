/**
 * ファイル名：BaseShiftMstMntForm.java
 *
 * 変更履歴
 * 1.0  2010/11/04 Kazuya.Naraki
 */
package form.bse;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

/**
 * 説明：基本シフト入力確認フォーム
 * @author naraki
 *
 */
public class BaseShiftMstMntForm extends ActionForm {

    /**
	 *シリアル
	 */
	private static final long serialVersionUID = 1483629197030517493L;

    /** 社員ID */
    private String employeeId;
    /** 社員名 */
    private String employeeName;

	/** 設定済み基本シフトBeanList */
    private List<BaseShiftMstMntBean> baseShiftMstMntBeanList;
    /** 基本シフトコンボ */
    private Map<String, String> shiftCmbMap;
    /** 基本シフト凡例一覧 */
    private List<BaseShiftPatternBean> baseShiftPatternBeanList;

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


	public List<BaseShiftMstMntBean> getBaseShiftMstMntBeanList() {
		return baseShiftMstMntBeanList;
	}


	public void setBaseShiftMstMntBeanList(List<BaseShiftMstMntBean> baseShiftMstMntBeanList) {
		this.baseShiftMstMntBeanList = baseShiftMstMntBeanList;
	}


	public Map<String, String> getShiftCmbMap() {
		return shiftCmbMap;
	}


	public void setShiftCmbMap(Map<String, String> shiftCmbMap) {
		this.shiftCmbMap = shiftCmbMap;
	}


	public List<BaseShiftPatternBean> getBaseShiftPatternBeanList() {
		return baseShiftPatternBeanList;
	}


	public void setBaseShiftPatternBeanList(List<BaseShiftPatternBean> baseShiftPatternBeanList) {
		this.baseShiftPatternBeanList = baseShiftPatternBeanList;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer(256);
		for (BaseShiftMstMntBean bean : this.baseShiftMstMntBeanList) {
			ret.append("\n");
			ret.append("■■■■■■■■■■■■");
			ret.append("getEmployeeId()           :").append(bean.getEmployeeId()).append("\n");
			ret.append("getEmployeeName()         :").append(bean.getEmployeeName()).append("\n");
			ret.append("getShiftIdOnMonday()   :").append(bean.getShiftIdOnMonday()).append("\n");
			ret.append("getShiftIdOnTuesday()  :").append(bean.getShiftIdOnTuesday()).append("\n");
			ret.append("getShiftIdOnWednesday():").append(bean.getShiftIdOnWednesday()).append("\n");
			ret.append("getShiftIdOnThursday() :").append(bean.getShiftIdOnThursday()).append("\n");
			ret.append("getShiftIdOnFriday()   :").append(bean.getShiftIdOnFriday()).append("\n");
			ret.append("getShiftIdOnSaturday() :").append(bean.getShiftIdOnSaturday()).append("\n");
			ret.append("getShiftIdOnSunday()   :").append(bean.getShiftIdOnSunday()).append("\n");
		}

		return ret.toString();

	}
}