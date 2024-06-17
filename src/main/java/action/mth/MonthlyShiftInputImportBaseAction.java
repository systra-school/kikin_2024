package action.mth;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import business.dto.LoginUserDto;
import business.dto.bse.BaseShiftDto;
import business.logic.bse.BaseShiftLogic;
import business.logic.comparator.MethodComparator;
import business.logic.utils.CheckUtils;
import business.logic.utils.ComboListUtilLogic;
import business.logic.utils.CommonUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.common.DateBean;
import form.mth.MonthlyShiftInputBean;
import form.mth.MonthlyShiftInputForm;
public class MonthlyShiftInputImportBaseAction extends MonthlyShiftInputAbstractAction{
    /**
     * 説明：月別シフト基本シフト反映処理
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
        MonthlyShiftInputForm monthlyShiftForm = (MonthlyShiftInputForm) form;
        
        // 対象年月
        String yearMonth = monthlyShiftForm.getYearMonth();
        
        //基本シフトデータから値を取得してbeanに格納する処理
        BaseShiftLogic baseShiftLogic = new BaseShiftLogic();
        
        // 対象年月の月情報を取得する。
        List<DateBean> dateBeanList = CommonUtils.getDateBeanList(yearMonth);
        
        //社員分の基本シフトマスタデータを取得する
        Map<String,BaseShiftDto> baseShiftDtoMap = baseShiftLogic.getBaseShiftData();
        
        //データベースに登録されている月別シフト情報を呼び出すためのリスト作成
        List<MonthlyShiftInputBean> monthlyShiftBeanList = new ArrayList<MonthlyShiftInputBean>();
        
        // セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        Map<String, String> shiftCmbMap = comboListUtils.getComboShift(true);
        
        Map<String, String> yearMonthCmbMap = comboListUtils.getComboYearMonth(CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL), 2, ComboListUtilLogic.KBN_YEARMONTH_NEXT, false);
        
        //社員ごとの基本シフト情報を格納するデータ
        Map<String,List<String>> weekShift = new LinkedHashMap<String,List<String>>();
        
        for(BaseShiftDto ShiftDto:baseShiftDtoMap.values()) {
        	 //社員ごとに基本シフトデータをリスト化
        	List<String> weekDayShift = new ArrayList<String>();
        	weekDayShift.add(ShiftDto.getShiftIdOnSaturday());
        	weekDayShift.add(ShiftDto.getShiftIdOnSunday());
        	weekDayShift.add(ShiftDto.getShiftIdOnMonday());
        	weekDayShift.add(ShiftDto.getShiftIdOnTuesday());
        	weekDayShift.add(ShiftDto.getShiftIdOnWednesday());
        	weekDayShift.add(ShiftDto.getShiftIdOnThursday());
        	weekDayShift.add(ShiftDto.getShiftIdOnFriday());
        	weekShift.put(ShiftDto.getEmployeeId(),weekDayShift);
        }
        //1か月分のシフトデータを作成
        	if (CheckUtils.isEmpty(baseShiftDtoMap)) {
                // データなし
        		MonthlyShiftInputBean monthlyShiftBean = new MonthlyShiftInputBean();
                monthlyShiftBean.setEmployeeId(loginUserDto.getEmployeeId());
                monthlyShiftBean.setEmployeeName(loginUserDto.getEmployeeName());
                monthlyShiftBean.setRegisterFlg(true);
                monthlyShiftBeanList.add(monthlyShiftBean);
            } else {
                // データあり
            	//社員ごとに曜日ごとのシフトをMonthlyShiftInputBeanに格納するループを行う
            	
            	for(BaseShiftDto data:baseShiftDtoMap.values()) {//社員ごとのループ
            		MonthlyShiftInputBean monthlyShiftBean = new MonthlyShiftInputBean();
            		int count = 0;//MonthlyShiftInputBeanに値を入れる処理を行う際に使用
                	for(int k = 0;k < dateBeanList.size();k++) {//日にち毎にshiftIdに登録する処理
                		//該当する日付の曜日を取得し、数値化
                		int weekDay = 0;
                		if((dateBeanList.get(k).getWeekDay()).equals("土")) {
                			weekDay = 0;
                		}else if((dateBeanList.get(k).getWeekDay()).equals("日")) {
                			weekDay = 1;
                 		}else if((dateBeanList.get(k).getWeekDay()).equals("月")) {
                 			weekDay = 2;
                		}else if((dateBeanList.get(k).getWeekDay()).equals("火")) {
                			weekDay = 3;
                		}else if((dateBeanList.get(k).getWeekDay()).equals("水")) {
                			weekDay = 4;
                		}else if((dateBeanList.get(k).getWeekDay()).equals("木")) {
                			weekDay = 5;
                		}else if((dateBeanList.get(k).getWeekDay()).equals("金")) {
                			weekDay = 6;
                		}
                		//以下、MonthlyShiftInputBean内のメソッドを動的に実行し1日から順番に値を入れる処理
                		// 実行するオブジェクトの生成
                		Method[] methods = monthlyShiftBean.getClass().getMethods();
                		// メソッドのソートを行う
                		Comparator<Method> sortAsc = new MethodComparator();
                		Arrays.sort(methods, sortAsc); // 配列をソート
                		int index = 0;
                		int listSize = dateBeanList.size();
                		
                		for (int i = count; i < methods.length; i++) {
                			// "setShiftIdXX" のメソッドを動的に実行する
                			if (methods[i].getName().startsWith("setShiftId") && listSize > index) {
                			// メソッド実行
                			methods[i].invoke(monthlyShiftBean,weekShift.get(data.getEmployeeId()).get(weekDay));
                			
                			index ++;
                			i++;
                			count = i;
                			break;
                			}//if
                		}//for(動的なメソッド実行)
                	}//for（日にち毎）
                	monthlyShiftBean.setEmployeeId(data.getEmployeeId());
            		monthlyShiftBean.setEmployeeName(data.getEmployeeName());
            		monthlyShiftBean.setRegisterFlg(false);
                	monthlyShiftBeanList.add(monthlyShiftBean);
                }//for(社員ごと)
                	
        }//if(データあり)
        // フォームにデータをセットする
        monthlyShiftForm.setShiftCmbMap(shiftCmbMap);
        monthlyShiftForm.setYearMonthCmbMap(yearMonthCmbMap);
        monthlyShiftForm.setMonthlyShiftInputBeanList(monthlyShiftBeanList);
        monthlyShiftForm.setDateBeanList(dateBeanList);
        monthlyShiftForm.setYearMonth(yearMonth);
        // ページング用
        monthlyShiftForm.setMaxPage(CommonUtils.getMaxPage(baseShiftDtoMap.size(), SHOW_LENGTH));
        return mapping.findForward(forward);
    }
}