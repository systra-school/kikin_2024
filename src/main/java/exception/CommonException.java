/**
 * ファイル名：CommonException.java
 *
 * 変更履歴
 * 1.0  2010/09/01 Kazuya.Naraki
 */
package exception;

/**
 * 説明：共通例外クラス
 * @author naraki
 *
 */
public class CommonException extends Exception {

    public CommonException(String arg) {
        super(arg);
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("システムエラー発生</BR>");
        strBuf.append(arg);

    }
}
