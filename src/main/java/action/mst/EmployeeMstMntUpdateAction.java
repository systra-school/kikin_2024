/**
 * ファイル名：EmployeeMstMntUpdateAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
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
 * 説明：社員マスタメンテナンス更新系アクションクラス
 * @author naraki
 *
 */
public class EmployeeMstMntUpdateAction extends Action{

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 社員マスタメンテナンス更新系アクションクラス
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
        EmployeeMstMntForm employeeMstMntForm = (EmployeeMstMntForm) form;

        // リクエスト内容をDtoに変換する
        List<EmployeeMstMntDto> m_employeeDtoList = this.formToDto(employeeMstMntForm);

        // ロジック生成
        EmployeeMstMntLogic employeeMstMntLogic = new EmployeeMstMntLogic();

        // 権限セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        Map<String, String> comboMap = comboListUtils.getCombo(CategoryId.AUTHORITY.getCategoryId(), Mcategory.DISPLAY.getName(), false);

        // 取得したセレクトボックスのマップをフォームへセットする。
        employeeMstMntForm.setAuthorityCmbMap(comboMap);

        // 更新・削除処理
        employeeMstMntLogic.updateM_employee(m_employeeDtoList, loginUserDto);

        // 社員情報を再検索する
        m_employeeDtoList = employeeMstMntLogic.getEmployeeData(loginUserDto);

        if (CheckUtils.isEmpty(m_employeeDtoList)) {
            // データなし
            forward = CommonConstant.NODATA;
        } else {
            // フォームへ一覧をセットする
            employeeMstMntForm.setEmployeeMstMntBeanList(dtoToForm(m_employeeDtoList));
        }

        return mapping.findForward(forward);
    }

    /**
     * リクエスト情報をDtoのリストにセットする。
     * @param employeeMstMntForm 社員マスタフォーム
     * @return 社員マスタDtoリスト
     * @author naraki
     */
    private List<EmployeeMstMntDto> formToDto(EmployeeMstMntForm employeeMstMntForm) {
    	
        List<EmployeeMstMntDto> m_employeeDtoList = new ArrayList<EmployeeMstMntDto>();
        List<EmployeeMstMntBean> employeeMstMntBeanList = employeeMstMntForm.getEmployeeMstMntBeanList();

        // 画面一覧のサイズ分処理を繰り返す
        for (EmployeeMstMntBean employeeMstMntBean : employeeMstMntBeanList) {
        	
            EmployeeMstMntDto employeeMstMntDto = new EmployeeMstMntDto();

            // Dtoに値をセットする
            employeeMstMntDto.setEmployeeId(employeeMstMntBean.getEmployeeId());
            employeeMstMntDto.setPassword(employeeMstMntBean.getPassword());
            employeeMstMntDto.setEmployeeName(employeeMstMntBean.getEmployeeName());
            employeeMstMntDto.setEmployeeNameKana(employeeMstMntBean.getEmployeeNameKana());
            employeeMstMntDto.setAuthorityId(employeeMstMntBean.getAuthorityId());
            employeeMstMntDto.setDeleteFlg(employeeMstMntBean.getDeleteFlg());

            m_employeeDtoList.add(employeeMstMntDto);
        }

        return m_employeeDtoList;
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
