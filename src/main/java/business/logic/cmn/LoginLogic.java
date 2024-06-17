/**
 * ファイル名：LoginLogic.java
 *
 * 変更履歴
 * 1.0  2010/07/19 Kazuya.Naraki
 */
package business.logic.cmn;

import java.sql.SQLException;

import business.db.dao.cmn.LoginDao;
import business.dto.cmn.LoginDto;
import business.dto.mst.EmployeeMstMntDto;
import form.cmn.LoginForm;

/**
 * 説明：ログイン処理のロジック実装クラス
 *
 * @author naraki
 *
 */
public class LoginLogic {
    public LoginDto getEmployeeData(LoginForm loginForm) throws SQLException{

        // 社員マスタ検索用エンティティ
        EmployeeMstMntDto m_employeeDtoSearch = new EmployeeMstMntDto();

        // 検索条件セット
        m_employeeDtoSearch.setEmployeeId(loginForm.getEmployeeId()); // 社員ID
        m_employeeDtoSearch.setPassword(loginForm.getPassword());     // パスワード

        // 社員マスタDao
        LoginDao m_EmployeeDao = new LoginDao();
        // 社員情報を取得する。

        // Seasar2 を使用しない場合
        LoginDto loginDto = m_EmployeeDao.getEmployee(m_employeeDtoSearch);
        // Seasar2 を使用する場合はコメントアウトを除去してください
        // LoginDto loginDto = mEmployeeDao.getEmployeeS2(memployeeDtoSearch);

        return loginDto;
    }
}
