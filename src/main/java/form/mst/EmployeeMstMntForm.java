/**
 * ファイル名：EmployeeMstMntForm.java
 *
 * 変更履歴
 * 1.0  2010/08/23 Kazuya.Naraki
 */
package form.mst;


import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionForm;

/**
 * 説明：社員マスタメンテナンスフォームクラス
 * @author naraki
 *
 */
public class EmployeeMstMntForm extends ActionForm {

    /** 社員一覧 */
    private List<EmployeeMstMntBean> employeeMstMntBeanList;
    /** 権限コンボ */
    private Map<String, String> authorityCmbMap;
    
    public List<EmployeeMstMntBean> getEmployeeMstMntBeanList() {
        return employeeMstMntBeanList;
    }
    public void setEmployeeMstMntBeanList(List<EmployeeMstMntBean> employeeMstMntBeanList) {
        this.employeeMstMntBeanList = employeeMstMntBeanList;
    }
    public Map<String, String> getAuthorityCmbMap() {
        return authorityCmbMap;
    }
    public void setAuthorityCmbMap(Map<String, String> authorityCmbMap) {
        this.authorityCmbMap = authorityCmbMap;
    }
}
