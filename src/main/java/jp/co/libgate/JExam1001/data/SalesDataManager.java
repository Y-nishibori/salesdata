package jp.co.libgate.JExam1001.data;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import jp.co.libgate.JExam1001.exception.InvalidSalesDataException;

/**
 * データのチェック、受け渡し、管理をする
 * 
 * @author nishibori.yutaro
 *
 */
public class SalesDataManager {

  // 変数定義
  private List<SalesData> salesData = new ArrayList<SalesData>();
  private List<AggregateSalesData> aggSalesData = new ArrayList<AggregateSalesData>();
  private final int FALL_DATA_LENGTH = 3;
  private final int MIX_ALL_DATA_LENGTH = 4;
  private final int PRODUCT_NAME = 0;
  private final int DATE = 1;
  private final int EARNINGST = 2;
  private final int YEAR = 3;
  private final int SPLIT_YEAR = 0;

  /**
   * 入力データの分割
   * 
   * @param line
   *          1行ずつ読み込んだ入力データ
   * @return 商品名、年月、売上、年の順に格納された配列
   * @throws InvalidSalesDataException
   *           フォーマット形式の例外
   */
  public String[] splitData(String line) throws InvalidSalesDataException {

    String[] fAllData = line.split(",");

    if (fAllData.length != FALL_DATA_LENGTH) {
      throw new InvalidSalesDataException("カンマ");
    }

    String[] fDate = fAllData[1].split("/");
    String[] mixAllData = new String[MIX_ALL_DATA_LENGTH];

    mixAllData[PRODUCT_NAME] = fAllData[PRODUCT_NAME];
    mixAllData[DATE] = fAllData[DATE];
    mixAllData[EARNINGST] = fAllData[EARNINGST];
    mixAllData[YEAR] = fDate[SPLIT_YEAR];

    return mixAllData;
  }

  /**
   * 入力データのフォーマットチェック
   * 
   * @param splited
   *          分割した読み込みデータの配列
   * @throws InvalidSalesDataException
   *           フォーマット形式の例外
   */
  public void formatCheck(String[] splited) throws InvalidSalesDataException {

    // 商品名チェック(空白でないか)
    if (StringUtils.isBlank(splited[PRODUCT_NAME])) {
      // 例外をスローする。
      throw new InvalidSalesDataException("商品名");
    }

    // 日付チェック(正しい年月、フォーマットか)
    try {
      DateUtils.parseDateStrictly(splited[DATE], new String[] { "yyyy/MM" });
    } catch (ParseException e) {
      throw new InvalidSalesDataException("年月");
    }

    // 売上チェック(数字のみか)
    if (!StringUtils.isNumeric(splited[EARNINGST])) {
      throw new InvalidSalesDataException("売上金額");
    }
  }

  /**
   * 初期データの格納
   * 
   * @param splited
   *          分割した読み込みデータの配列
   * 
   */
  public void storeSalesData(String[] splited) {
    salesData.add(new SalesData(splited[PRODUCT_NAME], splited[YEAR], splited[EARNINGST]));
  }

  /**
   * 初期データの分割、フォーマットチェック、格納の呼び出し
   * 
   * @param line
   *          読み込みデータ(1行)
   * @throws InvalidSalesDataException
   *           フォーマット形式の例外
   * 
   */
  public void createSalesData(String line) throws InvalidSalesDataException {
    String[] splited = splitData(line);
    formatCheck(splited);
    storeSalesData(splited);
  }

  /**
   * 加算データの作成、インスタンスを生成し、リストへ格納
   * 
   */
  public void createAddingSalesData() {
    final int COUNT = 1;
    final double AVERAGE = 0;
    for (SalesData s : salesData) {
      // インスタンスを生成
      AggregateSalesData asd = new AggregateSalesData(s.getName(), s.getYear(), s.getEarningst(),
          COUNT, AVERAGE);
      // 商品名と年が同じものを探す
      if (getAggSalesData().contains(asd)) {

        int addIndex = getAggSalesData().indexOf(asd);

        // 同じものがあれば、売り上げとカウントを加算し、上書きする
        double aggregate = addingEarningst(getAggSalesData().get(addIndex).earningst,
            s.getEarningst());
        getAggSalesData().get(addIndex).earningst = aggregate;
        getAggSalesData().get(addIndex).count++;

      } else {
        // 同じものがなければ、インスタンスをリストへ格納
        getAggSalesData().add(asd);
      }
    }
  }

  /**
   * 平均値算出データをリストへ格納
   * 
   */
  // 加算処理後のリストからデータを呼び出し、平均算出メソッドを挟み、インスタンス、リストへ格納。
  public void sendAverageSalesData() {
    for (AggregateSalesData asd : getAggSalesData()) {
      int aveIndex = getAggSalesData().indexOf(asd);
      double average = averageEarningst(getAggSalesData().get(aveIndex).getEarningst(),
          getAggSalesData().get(aveIndex).count);
      getAggSalesData().get(aveIndex).average = average;
    }
  }

  /**
   * 売上金額の加算メソッド
   * 
   * @param firstEar
   *          加算元の売上金額
   * @param addEar
   *          加算値となる売上金額
   * @return 加算後の売上金額
   */
  public double addingEarningst(double firstEar, double addEar) {
    return firstEar += addEar;
  }

  /**
   * 売上金額の平均算出メソッド
   * 
   * @param aggEar
   *          加算後の売上金額
   * @param count
   *          加算回数
   * @return 平均売上金額
   */
  // 平均算出メソッド
  public double averageEarningst(double aggEar, int count) {
    return aggEar / count;
  }

  /**
   * 出力データの並べ替えメソッド
   * 
   */
  public void sortSalesData() {
    Collections.sort(getAggSalesData(), new AggregateDataComparator());
  }

  /**
   * aggSalesDataインスタンスを返す
   * 
   * @return aggSalesDataインスタンス
   */
  public List<AggregateSalesData> getAggSalesData() {
    return aggSalesData;
  }

  /**
   * aggSalesDataインスタンスをセットする
   * 
   * @param aggSalesData
   *          aggSalesDataインスタンス
   * 
   */
  public void setAggSalesData(List<AggregateSalesData> aggSalesData) {
    this.aggSalesData = aggSalesData;
  }

}
