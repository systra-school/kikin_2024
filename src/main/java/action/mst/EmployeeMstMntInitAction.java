/**
 * ファイル名：EmployeeMstMntInitAction.java
 *
 * 変更履歴
 * 1.0  2010/08/23 Kazuya.Naraki
 */
package action.mst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import business.dto.LoginUserDto;
import business.dto.mst.EmployeeMstMntDto;
import business.logic.mst.EmployeeMstMntLogic;
import business.logic.utils.CheckUtils;
import business.logic.utils.ComboListUtilLogic;
import constant.CommonConstant;
import constant.CommonConstant.CategoryId;
import constant.DbConstant.Mcategory;
import constant.RequestSessionNameConstant;
import form.mst.EmployeeMstMntBean;
import form.mst.EmployeeMstMntForm;


/**
 * 説明：社員マスタメンテナンス初期表示アクションクラス
 * @author naraki
 *
 */
public class EmployeeMstMntInitAction extends Action {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 社員マスタメンテナンス初期表示アクションクラス
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return アクションフォワード
     * @author naraki
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res) throws Exception {

        log.info(new Throwable().getStackTrace()[0].getMethodName());

        // セッション
        HttpSession session = req.getSession();

        // ログインユーザ情報をセッションより取得
        LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_INFO);

        // フォーム
        EmployeeMstMntForm employeeMstMntForm = (EmployeeMstMntForm) form;

        // フォワードキー
        String forward = CommonConstant.SUCCESS;

        // ロジック生成
        EmployeeMstMntLogic employeeMstMntLogic = new EmployeeMstMntLogic();

        // 権限セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        Map<String, String> comboMap = comboListUtils.getCombo(CategoryId.AUTHORITY.getCategoryId(), Mcategory.DISPLAY.getName(), false);

        // 取得したセレクトボックスのマップをフォームへセットする。
        employeeMstMntForm.setAuthorityCmbMap(comboMap);

        // 社員情報を取得する
        Collection<EmployeeMstMntDto> m_employeeList = employeeMstMntLogic.getEmployeeData(loginUserDto);

        if (CheckUtils.isEmpty(m_employeeList)) {
            forward = CommonConstant.NODATA;
        }

        // フォームへ一覧をセットする
        employeeMstMntForm.setEmployeeMstMntBeanList(dtoToForm(m_employeeList));

        // 戻り先を保存
        req.setAttribute(RequestSessionNameConstant.REQUEST_BACK_PAGE, "");

        return mapping.findForward(forward);
    }


    /**
     * DtoからFormへ変換する
     * @param
     * @return
     * @author naraki
     */
    private List<EmployeeMstMntBean> dtoToForm(Collection<EmployeeMstMntDto> colection) {
    	
        List<EmployeeMstMntBean> employeeMstMntBeanList = new ArrayList<EmployeeMstMntBean>();

        for (EmployeeMstMntDto dto : colection) {
            EmployeeMstMntBean employee = new EmployeeMstMntBean();
            employee.setEmployeeId(dto.getEmployeeId());
            employee.setEmployeeName(dto.getEmployeeName());
            employee.setEmployeeNameKana(dto.getEmployeeNameKana());
            employee.setPassword(dto.getPassword());
            employee.setAuthorityId(dto.getAuthorityId());
            employeeMstMntBeanList.add(employee);

        }
        return employeeMstMntBeanList;
    }
}
