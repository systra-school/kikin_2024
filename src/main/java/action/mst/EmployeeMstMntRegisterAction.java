/**
 * ファイル名：EmployeeMstMntRegisterAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.mst;


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
import business.logic.utils.ComboListUtilLogic;
import constant.CommonConstant;
import constant.CommonConstant.CategoryId;
import constant.DbConstant.Mcategory;
import constant.RequestSessionNameConstant;
import form.mst.EmployeeMstMntRegisterForm;

/**
 * 説明：社員マスタメンテナンス登録アクションクラス
 * @author naraki
 *
 */
public class EmployeeMstMntRegisterAction extends Action{

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 社員マスタメンテナンス登録アクションクラス
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

        // フォワードキー
        String forward = CommonConstant.SUCCESS;

        // セッション
        HttpSession session = req.getSession();

        // ログインユーザ情報をセッションより取得
        LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_INFO);

        // フォーム
        EmployeeMstMntRegisterForm employeeMstMntRegisterForm = (EmployeeMstMntRegisterForm) form;

        // リクエスト内容をDtoに変換する
        EmployeeMstMntDto m_employeeDto = this.formToDto(employeeMstMntRegisterForm);

        // ロジック生成
        EmployeeMstMntLogic employeeMstMntLogic = new EmployeeMstMntLogic();

        // 権限セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        Map<String, String> comboMap = comboListUtils.getCombo(CategoryId.AUTHORITY.getCategoryId(), Mcategory.DISPLAY.getName(), false);

        // 取得したセレクトボックスのマップをフォームへセットする。
        employeeMstMntRegisterForm.setAuthorityCmbMap(comboMap);

        // 登録
        employeeMstMntLogic.registerM_employee(m_employeeDto, loginUserDto);

        return mapping.findForward(forward);
    }

    /**
     * リクエスト情報をDtoのリストにセットする。
     * @param employeeMstMntRegisterForm 社員マスタ登録フォーム
     * @return 社員マスタDtoリスト
     * @author naraki
     */
    private EmployeeMstMntDto formToDto(EmployeeMstMntRegisterForm employeeMstMntRegisterForm) {
        EmployeeMstMntDto employeeMstMntDto = new EmployeeMstMntDto();

        String password = employeeMstMntRegisterForm.getPassword();
        String employeeName = employeeMstMntRegisterForm.getEmployeeName();
        String employeeNameKana = employeeMstMntRegisterForm.getEmployeeNameKana();
        String authorityId = employeeMstMntRegisterForm.getAuthorityId();

        // Dtoに値をセットする
        employeeMstMntDto.setPassword(password);
        employeeMstMntDto.setEmployeeName(employeeName);
        employeeMstMntDto.setEmployeeNameKana(employeeNameKana);
        employeeMstMntDto.setAuthorityId(authorityId);

        return employeeMstMntDto;
    }

}
