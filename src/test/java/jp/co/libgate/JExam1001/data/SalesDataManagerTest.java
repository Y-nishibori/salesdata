package jp.co.libgate.JExam1001.data;
import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import jp.co.libgate.JExam1001.data.SalesDataManager;

/**
 * 加算メソッドと平均算出メソッドのテストクラス
 * 
 * @author nishibori.yutaro
 *
 */
public class SalesDataManagerTest {

  /**
   * 加算メソッドのテストメソッド
   * 
   */
  @Test
  public void addingEarningstTest() {
    SalesDataManager sd = new SalesDataManager();

    // 加算メソッドの引数と期待値が整数だった場合
    double eValue01 = 250.0;
    assertThat(sd.addingEarningst((double) 100.0, (double) 150.0), is(eValue01));
    
    // 加算メソッドの引数が少数だった場合
    double eValue02 = 251.0;
    assertThat(sd.addingEarningst((double) 100.5, (double) 150.5), is(eValue02));

    // 加算メソッドの引数と期待値が少数だった場合
    double eValue03 = 250.9;
    assertThat(sd.addingEarningst((double) 100.4, (double) 150.5), is(eValue03));
  }

  /**
   * 平均算出メソッドのテストメソッド
   * 
   */
  @Test
  public void averageEarningstTest() {
    SalesDataManager sd = new SalesDataManager();
    
    // 平均算出メソッドの引数と期待値が整数だった場合
    double eValue01 = 20.0;
    assertThat(sd.averageEarningst((double) 100.0, 5), is(eValue01));

    // 期待値が整数だった場合
    double eValue02 = 246.8;
    assertThat(sd.averageEarningst((double) 1234.0, 5), is(eValue02));

    // 平均算出メソッドの第一引数と期待値が少数だった場合
    double eValue03 = 24.68;
    assertThat(sd.averageEarningst((double) 123.4, 5), is(eValue03));
  }

}
