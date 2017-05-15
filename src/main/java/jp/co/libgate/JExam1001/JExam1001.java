
package jp.co.libgate.JExam1001;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.co.libgate.JExam1001.data.AggregateSalesData;
import jp.co.libgate.JExam1001.data.SalesDataManager;
import jp.co.libgate.JExam1001.exception.InvalidSalesDataException;

/**
 * 商品ごと、月ごとの売上を記載したファイルを読み込み、年ごとの売上の合計と平均を出力するプログラム
 * 
 * @author nishibori.yutaro
 * @version 1.2
 */
public class JExam1001 {

  /**
   * 商品ごと、月ごとの売上を記載したファイルを読み込み、年ごとの売上の合計と平均を出力するプログラム
   * 
   * @param args
   *          入力ファイル名、出力ファイル名
   *          
   */
  public static void main(String[] args) {
    // コマンドパラメーターが2つ指定されているかチェック
    if (args.length != 2) {
      System.out.println("\nUsage: java JExam1001 [INPUT_FILENAME OUTPUT_FILENAME]");
      return;
    }

    // 変数定義
    final String inFileName = args[0];
    final String outFileName = args[1];
    Path inPh = Paths.get("src", inFileName);
    Path outPh = Paths.get("src", outFileName);

    /* start ファイルチェック */
    // 入力ファイルが存在するかチェック
    if (!Files.exists(inPh)) {
      System.out.println("\n" + inFileName + " is not found.");
      return;
    }

    // 読み込み権限の確認
    if (!Files.isReadable(inPh)) {
      System.out.println("\n" + inFileName + " is not readable!!");
      return;
    }

    // 読み込みファイルがファイルかどうかのチェック
    if (Files.isDirectory(inPh)) {
      System.out.println("\n" + inFileName + " is not file!!");
      return;
    }

    // 出力ファイルが存在するかチェック
    String answer = null;
    if (Files.exists(outPh)) {
      // 出力ファイルがファイルかどうかのチェック
      if (Files.isDirectory(outPh)) {
        System.out.println("\n" + outFileName + " is not file!!");
        return;
      }

      // 書き込み権限があるかのチェック
      if (!Files.isWritable(outPh)) {
        System.out.println("\n" + outFileName + " is not writable!!");
        return;
      }

      // 上書き確認
      while (true) {
        System.out.print(
            "\n" + outFileName + " file exists. Are you sure you want to overwrite.[y or n] : ");
        answer = new java.util.Scanner(System.in).nextLine().trim().toLowerCase();

        if ("y".equals(answer) || "n".equals(answer)) {
          break;
        }
        System.out.println("\nWarining!! Input String y or n.");
      }

      // 上書き拒否の場合
      if ("n".equals(answer)) {
        System.out.println("\nSystem End.");
        return;
      }
    }
    /* end ファイルチェック */

    SalesDataManager sd = new SalesDataManager();
    /* start 初期データの作成 */
    // 入力ファイルの読み込み
    try (BufferedReader br = Files.newBufferedReader(inPh);) {
      for (String line; (line = br.readLine()) != null;) {
        sd.createSalesData(line);
      }
    } catch (InvalidSalesDataException e) {
      System.out.println("\nThis is not proper dataset in " + inFileName + ".\n");
      return;
    } catch (IOException e) {
      Logger logger = LoggerFactory.getLogger(JExam1001.class);
      logger.error("ファイルの読み込みにて、IOExceptionが発生しました。");
      return;
    }

    /* end 初期データの作成 */

    // 加算メソッドの呼び出し
    sd.createAddingSalesData();
    // 平均算出メソッドの呼び出し
    sd.sendAverageSalesData();

    // 計算後のデータを並べ替え
    sd.sortSalesData();

    // 計算後のデータをファイルへ出力
    try (BufferedWriter bw = Files.newBufferedWriter(outPh, StandardOpenOption.CREATE,
        StandardOpenOption.WRITE);) {
      for (AggregateSalesData s : sd.getAggSalesData()) {
        bw.write(s.toString());
        bw.newLine();
        bw.flush();
      }
    } catch (IOException e) {
      Logger logger = LoggerFactory.getLogger(JExam1001.class);
      logger.error("ファイルの書き込みにて、IOExceptionが発生しました。");
      return;
    }

    // 標準出力のフォーマットを定義
    final String FORMAT = "%5d\t %s \t$%-10.3f$%-8.3f\n";

    // 計算後のデータを標準出力
    System.out.println("\n*** Year *** Product *** Average *** Sum ***");
    for (AggregateSalesData s : sd.getAggSalesData()) {
      System.out.printf(FORMAT, s.getYear(), s.getName(), s.getAverage(), s.getEarningst());
    }
    System.out.println("********************************************");

  }
}
