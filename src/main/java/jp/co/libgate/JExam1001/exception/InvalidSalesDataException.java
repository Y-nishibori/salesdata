package jp.co.libgate.JExam1001.exception;

/**
 * 読み込みファイル形式に問題があった場合の例外処理
 * 
 * @author nishibori.yutaro
 * @version 1.0
 *
 */
public class InvalidSalesDataException extends Exception {

  private static final long serialVersionUID = 1L;

  // 問題箇所を標準出力
  public InvalidSalesDataException(String errorPoint) {
    System.out.println("\n" + errorPoint + "の記述に誤りがあります。");
  }
}
