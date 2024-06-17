/**
 * ファイル名：WorkDateRequestInputRegisterAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 * 2.0  2024/04/30 Atsuko.Yoshioka
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
import business.dto.shk.WorkDateRequestInputDto;
import business.logic.comparator.MethodComparator;
import business.logic.shk.WorkDateRequestLogic;
import business.logic.utils.CheckUtils;
import business.logic.utils.ComboListUtilLogic;
import business.logic.utils.CommonUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.common.DateBean;
import form.shk.WorkDateRequestInputBean;
import form.shk.WorkDateRequestInputForm;

/**
 * 説明：出勤希望入力登録アクションクラス
 * @author naraki
 */
public class WorkDateRequestInputRegisterAction extends WorkDateRequestAbstractAction {
	/**
     * 説明：出勤希望入力登録アクションクラス
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
        WorkDateRequestInputForm workDateRequestInputForm = (WorkDateRequestInputForm) form;

        // 画面のリスト情報
        List<WorkDateRequestInputBean> workDateRequestInputBeanList = workDateRequestInputForm.getWorkDateRequestInputBeanList();

        // 対象年月
        String yearMonth = workDateRequestInputForm.getYearMonth();

        // ロジック生成
        WorkDateRequestLogic workDateRequestLogic = new WorkDateRequestLogic();
        
        String employeeId = loginUserDto.getEmployeeId();
        req.setAttribute("employeeId", employeeId);

        // 対象年月の月情報を取得する。
        List<DateBean> dateBeanList = CommonUtils.getDateBeanList(yearMonth);

        // フォームデータをDtoに変換する
        List<List<WorkDateRequestInputDto>> requestDtoNestedList = this.formToDto(workDateRequestInputBeanList, dateBeanList);

        // 登録・更新処理
        workDateRequestLogic.registerRequestShift(requestDtoNestedList, loginUserDto);

        // シフトIDを取得する
        List<List<WorkDateRequestCheckDto>> workRequestCheckDtoNestedList = workDateRequestLogic.getWorkDateRequestCheckDtoList(yearMonth);

        // セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        // 表示年月のセレクトボックスを取得する
        Map<String, String> yearMonthCmbMap = comboListUtils.getComboYearMonth(CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL), 2, ComboListUtilLogic.KBN_YEARMONTH_NEXT, false);
        // シフトのセレクトボックスを取得する
        Map<String, String> shiftCmbMap = comboListUtils.getComboShift(true);
       
        if (CheckUtils.isEmpty(workRequestCheckDtoNestedList)) {
            // データなし
        	WorkDateRequestInputBean workDateRequestInputBean = new WorkDateRequestInputBean();
            workDateRequestInputBean.setEmployeeId(loginUserDto.getEmployeeId());
            workDateRequestInputBean.setEmployeeName(loginUserDto.getEmployeeName());
            workDateRequestInputBean.setRegisterFlg(false);

            workDateRequestInputBeanList.add(workDateRequestInputBean);
        } else {
            // データあり
        	workDateRequestInputBeanList = dtoToBean(workRequestCheckDtoNestedList, loginUserDto);
        }

        // フォームにデータをセットする
        workDateRequestInputForm.setShiftCmbMap(shiftCmbMap);
        workDateRequestInputForm.setYearMonthCmbMap(yearMonthCmbMap);
        workDateRequestInputForm.setWorkDateRequestInputBeanList(workDateRequestInputBeanList);
        workDateRequestInputForm.setDateBeanList(dateBeanList);
        workDateRequestInputForm.setYearMonth(yearMonth);
       
        return mapping.findForward(forward);
    }

    /**
     * DtoからBeanへ変換する
     * @param workDateRequestInputDtoMap
     * @param loginUserDto
     * @return 一覧に表示するリスト
     * @author naraki
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private List<WorkDateRequestInputBean> dtoToBean(List<List<WorkDateRequestCheckDto>> workRequestCheckDtoNestedList
                                                      , LoginUserDto loginUserDto)
                                                                        throws IllegalArgumentException,
                                                                        IllegalAccessException,
                                                                        InvocationTargetException {
    	
        List<WorkDateRequestInputBean> workDateRequestInputBeanList = new ArrayList<WorkDateRequestInputBean>();

        for (List<WorkDateRequestCheckDto> workRequestCheckDtoList : workRequestCheckDtoNestedList) {

            // 実行するオブジェクトの生成
            WorkDateRequestInputBean workDateRequestInputBean = new WorkDateRequestInputBean();

            // メソッドの取得
            Method[] methods = workDateRequestInputBean.getClass().getMethods();

            // メソッドのソートを行う
            Comparator<Method> sortAsc = new MethodComparator();
            Arrays.sort(methods, sortAsc); // 配列をソート

            int index = 0;
            int listSize = workRequestCheckDtoList.size();

            String employeeId = "";
            String employeeName = "";

            for (int i = 0; i < methods.length; i++) {
                // "setShiftIdXX" のメソッドを動的に実行する
                if (methods[i].getName().startsWith("setShiftId") && listSize > index) {
                    WorkDateRequestCheckDto workDateRequestCheckDto = workRequestCheckDtoList.get(index);
                    // メソッド実行
                    methods[i].invoke(workDateRequestInputBean, workDateRequestCheckDto.getMyRequestShiftId());

                    employeeId = workDateRequestCheckDto.getEmployeeId();
                    employeeName = workDateRequestCheckDto.getEmployeeName();

                    index ++;
                }
            }

            workDateRequestInputBean.setEmployeeId(employeeId);
            workDateRequestInputBean.setEmployeeName(employeeName);
            workDateRequestInputBean.setRegisterFlg(true);

            workDateRequestInputBeanList.add(workDateRequestInputBean);

        }

        return workDateRequestInputBeanList;
    }

    /**
     * DtoからBeanへ変換する
     * @param monthlyShiftBeanList
     * @return DtoList
     * @author naraki
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private List<List<WorkDateRequestInputDto>> formToDto(List<WorkDateRequestInputBean> workDateRequestInputBeanList
                                                      , List<DateBean> dateBeanList) throws IllegalArgumentException,
                                                                        IllegalAccessException,
                                                                        InvocationTargetException {
        // 戻り値
        List<List<WorkDateRequestInputDto>> requestDtoNestedList = new ArrayList<List<WorkDateRequestInputDto>>();

        for (WorkDateRequestInputBean workDateRequestInputBean : workDateRequestInputBeanList) {

            List<WorkDateRequestInputDto> workDateRequestInputDtoList = new ArrayList<WorkDateRequestInputDto>();

            // メソッドの取得
            Method[] methods = workDateRequestInputBean.getClass().getMethods();

            // ソートを行う
            Comparator<Method> sortAsc = new MethodComparator();
            Arrays.sort(methods, sortAsc); // 配列をソート

            int listSize = dateBeanList.size();

            int index = 0;

            for (int i = 0; i < methods.length; i++) {
                // "getShiftIdXX" のメソッドを動的に実行する
                if (methods[i].getName().startsWith("getShiftId") && index < listSize) {
                    String yearMonthDay = "";

                    // 対象年月取得
                    yearMonthDay = dateBeanList.get(index).getYearMonthDay();

                    WorkDateRequestInputDto workDateRequestInputDto = new WorkDateRequestInputDto();
                    String myRequestShiftId = (String) methods[i].invoke(workDateRequestInputBean);

                    if (CommonConstant.BLANK_ID.equals(myRequestShiftId)) {
                        // 空白が選択されている場合
                    	myRequestShiftId = null;
                    }

                    workDateRequestInputDto.setMyRequestShiftId(myRequestShiftId);
                    workDateRequestInputDto.setEmployeeId(workDateRequestInputBean.getEmployeeId());
                    workDateRequestInputDto.setYearMonthDay(yearMonthDay);
                    workDateRequestInputDtoList.add(workDateRequestInputDto);

                    index++;
                    requestDtoNestedList.add(workDateRequestInputDtoList);
                }
            }          
        }
        return requestDtoNestedList;
    }
}
