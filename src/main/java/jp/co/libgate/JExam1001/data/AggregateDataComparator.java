package jp.co.libgate.JExam1001.data;

import java.util.Comparator;

/**
 * 出力データを並べかえる為の自然順序づけ
 * 
 * @author nishibori.yutaro
 * @version 1.0
 *
 */
public class AggregateDataComparator implements Comparator<AggregateSalesData> {
  /**
   * 年と文字列の比較メソッド(年を比較して-1, 0, 1を返す。0の場合は文字列の比較も行う）
   * 
   * @param o1
   *          インスタンス自身
   * @param o2
   *          リスト内のインスタンス
   * @return -1,0,1の数値。五十音、アルファベット順による文字列。
   */
  public int compare(AggregateSalesData o1, AggregateSalesData o2) {

    int year1 = o1.getYear();
    int year2 = o2.getYear();
    String name1 = o1.getName();
    String name2 = o2.getName();

    if (year1 > year2) {
      return 1;
    } else if (year1 < year2) {
      return -1;
    } else {
      return name1.compareTo(name2);
    }
  }

}
