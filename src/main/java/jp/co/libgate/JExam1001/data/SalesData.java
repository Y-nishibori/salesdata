package jp.co.libgate.JExam1001.data;

/**
 * 初期データを表す
 * 
 * @author nishibori.yutaro
 * @version 1.0
 *
 */
public class SalesData {
  /** 商品名 */
  private String name = null;
  /** 年 */
  private int year = 0;
  /** 売り上げ */
  private double earningst = 0;

  /**
   * 初期データの各値を設定
   * 
   * @param name
   *          商品名
   * @param year
   *          年
   * @param earningst
   *          売り上げ
   * 
   */
  public SalesData(String name, String year, String earningst) {
    this.name = name;
    this.year = Integer.parseInt(year);
    this.earningst = Double.parseDouble(earningst);
  }

  /**
   * 初期データインスタンスの商品名を返却
   * 
   * @return 商品名
   */
  public String getName() {
    return this.name;
  }

  /**
   * 初期データインスタンスの年を返却
   * 
   * @return 年
   */
  public int getYear() {
    return this.year;
  }

  /**
   * 初期データインスタンスの売上金額を返却
   * 
   * @return 売上金額
   */
  public double getEarningst() {
    return this.earningst;
  }

  /**
   * 標準出力の表示を返却
   */
  public String toString() {
    return " " + year + "/" + "    " + name + "   " + "$" + earningst;
  }
}
