/**
 * ファイル名：WorkDateRequestInputPageAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.shk;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import constant.CommonConstant;
import form.shk.WorkDateRequestInputBean;
import form.shk.WorkDateRequestInputForm;
public class WorkDateRequestInputPageAction extends WorkDateRequestAbstractAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		log.info(new Throwable().getStackTrace()[0].getMethodName());
        // フォワードキー
        String forward = CommonConstant.SUCCESS;
        // フォーム
        WorkDateRequestInputForm workDateRequestInputForm = (WorkDateRequestInputForm) form;
        // 登録フラグ初期化
        List<WorkDateRequestInputBean> workDateRequestBeanList = workDateRequestInputForm.getWorkDateRequestInputBeanList();
        for (WorkDateRequestInputBean workDateRequestBean : workDateRequestBeanList) {
        	workDateRequestBean.setRegisterFlg(false);
        }
		return mapping.findForward(forward);
	}
}





