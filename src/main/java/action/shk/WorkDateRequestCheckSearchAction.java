/**
 * ファイル名：WorkDateRequestCheckSearchAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.shk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import business.dto.LoginUserDto;
import business.dto.shk.WorkDateRequestCheckDto;
import business.logic.comparator.MethodComparator;
import business.logic.shk.WorkDateRequestLogic;
import business.logic.utils.CheckUtils;
import business.logic.utils.ComboListUtilLogic;
import business.logic.utils.CommonUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.common.DateBean;
import form.shk.WorkDateRequestCheckBean;
import form.shk.WorkDateRequestCheckForm;

/**
 * 説明：出勤希望日確認画面表示アクションクラス
 * @author naraki
 *
 */
public class WorkDateRequestCheckSearchAction extends WorkDateRequestAbstractAction{

    /**
     * 説明：出勤希望日入力画面表示アクションクラス
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
        WorkDateRequestCheckForm workDateRequestCheckForm = (WorkDateRequestCheckForm) form;

        // 対象年月
        String yearMonth = workDateRequestCheckForm.getYearMonth();

        if (CheckUtils.isEmpty(yearMonth)) {
            yearMonth = CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL);
        }

        // ロジック生成
        WorkDateRequestLogic workDateRequestLogic = new WorkDateRequestLogic();

        // 対象年月の月情報を取得する。
        List<DateBean> dateBeanList = CommonUtils.getDateBeanList(yearMonth);

        // 出勤希望日を取得する
        List<List<WorkDateRequestCheckDto>> workRequestCheckDtoNestedList = workDateRequestLogic.getWorkDateRequestCheckDtoList(yearMonth);

        List<WorkDateRequestCheckBean> workDateRequestInputBeanList = new ArrayList<WorkDateRequestCheckBean>();

        // セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        Map<String, String> shiftCmbMap = comboListUtils.getComboShift(false);
        Map<String, String> yearMonthCmbMap = comboListUtils.getComboYearMonth(CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL), 2, ComboListUtilLogic.KBN_YEARMONTH_NEXT, false);

        if (CheckUtils.isEmpty(workRequestCheckDtoNestedList)) {
            // データなし
            forward = CommonConstant.NODATA;
        } else {
            // データあり
            workDateRequestInputBeanList = dtoToBean(workRequestCheckDtoNestedList, loginUserDto);
        }

        // フォームにデータをセットする
        workDateRequestCheckForm.setShiftCmbMap(shiftCmbMap);
        workDateRequestCheckForm.setYearMonthCmbMap(yearMonthCmbMap);
        workDateRequestCheckForm.setWorkDateRequestCheckBeanList(workDateRequestInputBeanList);
        workDateRequestCheckForm.setDateBeanList(dateBeanList);
        workDateRequestCheckForm.setYearMonth(yearMonth);

        return mapping.findForward(forward);
    }

    /**
     * DtoからBeanへ変換する
     * @param workDateRequestInputDtoList
     * @param loginUserDto
     * @return 一覧に表示するリスト
     * @author naraki
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private List<WorkDateRequestCheckBean> dtoToBean(List<List<WorkDateRequestCheckDto>> workRequestCheckDtoNestedList
                                                      , LoginUserDto loginUserDto)
                                                                        throws IllegalArgumentException,
                                                                        IllegalAccessException,
                                                                        InvocationTargetException {
        List<WorkDateRequestCheckBean> workDateRequestCheckBeanList = new ArrayList<WorkDateRequestCheckBean>();

        // 社員分のループ
        for (List<WorkDateRequestCheckDto> workRequestCheckDtoList :  workRequestCheckDtoNestedList) {

            // 実行するオブジェクトの生成
            WorkDateRequestCheckBean workDateRequestCheckBean = new WorkDateRequestCheckBean();

            // メソッドの取得
            Method[] methods = workDateRequestCheckBean.getClass().getMethods();

            // ソートを行う
            Comparator<Method> sortAsc = new MethodComparator();
            Arrays.sort(methods, sortAsc); // 配列をソート

            // 社員名
            String employeeId = "";
            String employeeName = "";

            int index = 0;
            for (int i = 0; i < methods.length; i++) {
                // "setShiftIdXX" のメソッドを動的に実行する
                if (methods[i].getName().startsWith("setSymbol")) {
                    if (index < workRequestCheckDtoList.size()) {
                        // Dtoのリストのサイズ以上のとき
                        WorkDateRequestCheckDto requestCheckDto = workRequestCheckDtoList.get(index);

                        employeeId = requestCheckDto.getEmployeeId();
                        employeeName = requestCheckDto.getEmployeeName();

                        methods[i].invoke(workDateRequestCheckBean, requestCheckDto.getMyRequestSymbol());

                    } else {
                        // データなしの場合はハイフン
                        methods[i].invoke(workDateRequestCheckBean, "-");
                    }

                    index ++;
                }
            }

            // 社員ID、名前をセット
            workDateRequestCheckBean.setEmployeeId(employeeId);
            workDateRequestCheckBean.setEmployeeName(employeeName);

            workDateRequestCheckBeanList.add(workDateRequestCheckBean);
        }

        return workDateRequestCheckBeanList;
    }
}
