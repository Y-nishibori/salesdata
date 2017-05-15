package jp.co.libgate.JExam1001.data;

/**
 * 計算結果データを表す
 * 
 * @author nishibori.yutaro
 *
 */
public class AggregateSalesData {
  /** 商品名 */
  private String name = null;
  /** 年 */
  private int year = 0;
  /** 売り上げ */
  public double earningst = 0;
  /** 加算回数 */
  public int count = 1;
  /** 平均売り上げ */
  public double average = 0;

  /**
   * 初期データの各値を設定
   * 
   * @param name
   *          商品名
   * @param year
   *          年
   * @param earningst
   *          売り上げ
   * @param count
   *          加算回数
   * @param average
   *          平均売り上げ
   * 
   */
  public AggregateSalesData(String name, int year, double earningst, int count, double average) {
    this.name = name;
    this.year = year;
    this.earningst = earningst;
    this.count = count;
    this.average = average;
  }

  /**
   * 計算データインスタンスの商品名を返却
   * 
   * @return 商品名
   */
  public String getName() {
    return this.name;
  }

  /**
   * 計算データインスタンスの商品名を返却
   * 
   * @return 年
   */
  public int getYear() {
    return this.year;
  }

  /**
   * 計算データインスタンスの商品名を返却
   * 
   * @return 売上金額
   */
  public double getEarningst() {
    return this.earningst;
  }

  /**
   * 計算データインスタンスの平均売上金額を返却
   * 
   * @return 平均売上金額
   */
  public double getAverage() {
    return this.average;
  }

  /**
   * 計算データインスタンスの加算回数を返却
   * 
   * @return 加算回数
   */
  public int getCount() {
    return this.count;
  }

  @Override
  /**
   * hashCodeを返す。
   * 
   * @return このクラスのインスタンスのハッシュ値
   */
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + year;
    return result;
  }

  @Override
  /**
   * 同値判定
   * 
   * @return このクラスのインスタンスと引数で渡されたオブジェクトが同値である場合は、trueを返す。
   */
  public boolean equals(Object obj) {
    // 引数と自身の比較
    if (this == obj)
      return true;
    // 引数がnullかどうか
    if (obj == null)
      return false;
    // 自身の型と引数の型の比較
    if (getClass() != obj.getClass())
      return false;
    // 自身と引数が持つフィールドの比較
    AggregateSalesData other = (AggregateSalesData) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (year != other.year)
      return false;

    return true;
  }

  /**
   * 標準出力の表示を返却
   */
  public String toString() {
    return year + "," + name + "," + average + "," + earningst;
  }

}
