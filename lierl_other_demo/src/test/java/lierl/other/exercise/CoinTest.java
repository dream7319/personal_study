package lierl.other.exercise;

import java.util.Arrays;

/**
 *
 * 给你六种面额 1、5、10、20、50、100 元的纸币，假设每种币值的数量都足够多，
 * 编写程序求组成N元（N为0~10000的非负整数）的不同组合的个数
 *
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 16:15
 */
public class CoinTest {
    /**
     * 解题思路：
     *  比如：100元
     *      最多需要100张 1元纸币
     *      最少需要1张 100元纸币
     */
    public static long combination(int n){
        int coins[] = { 1, 5, 10, 20, 50, 100 };
        int h = coins.length;
        long dp[][] = new long[h][n + 1];
        Arrays.fill(dp[0], 1);

        for (int i = 1; i < h; i++) {  //表示从1元到100元纸币  h=6
            for (int j = 1; j <= n; j++) {//组成N元依次循环     n=5
                int m = j / coins[i];//组成N元时最多可用多少种相应的纸币
                for (int k = 0; k <= m; k++) {
                    dp[i][j] = dp[i][j] + dp[i - 1][j - k * coins[i]];//表示组合次数
                }
            }
        }
        return dp[h - 1][n];
    }


    public static void other1(int n){

        int coints[] = {1,5,10,20,50,100};
        int len = coints.length;
        int count = 0 ;
        //一种面额
        for (int i = 0; i < len; i++) {
            if(n % coints[i] == 0){
                count++;
            }
        }

        //两种面额
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                int cointi = n/coints[i];
                int cointj = n/coints[j];
                for (int k = 1; k < cointi ; k++) {
                    for (int l = 1; l < cointj; l++) {
                        if(n == k*coints[i] + l*coints[j]){
                            count++;
                        }
                    }
                }
            }
        }

        //三种面额
        for (int i = 0; i < len; i++) {
            for (int j = i+1; j < len; j++) {
                for (int k = j+1; k < len; k++) {
                    int cointi = n/coints[i];
                    int cointj = n/coints[j];
                    int cointk = n/coints[k];
                    for (int l = 1; k < cointi ; k++) {
                        for (int m = 1; l < cointj; l++) {
                            for (int o = 1; o < cointk; o++) {
                                if(n == l*coints[i] + m*coints[j] + o*coints[k]){
                                    count++;
                                }
                            }
                        }
                    }
                }
            }

        }

        System.out.println(count);
    }

    public static void other(int n){
        int coint1 = n/1;
        int coint5 = n/5;
        int coint10 = n/10;
        int coint20 = n/20;
        int coint50 = n/50;
        int coint100 = n/100;
        int count = 0;
        for (int o = 0; o <= coint1; o++) {
            if(coint5 == 0) coint5=1;
            for (int m = 0; m <= coint5; m++) {
                if(coint10 == 0) coint10 = 1;
                for (int l = 0; l <= coint10 ; l++) {
                    if(coint20 == 0) coint20=1;
                    for (int k = 0; k <= coint20; k++) {
                        if(coint50 == 0) coint50 = 1;
                        for (int j = 0; j <= coint50; j++) {
                            if(coint100==0) coint100=1;
                            for (int i = 0; i <= coint100 ; i++) {
                                if(n == 1*o+5*m+10*l+20*k+50*j+100*i){
                                    System.out.println(String.format("%-3d,%-3d,%-3d,%-3d,%-3d,%-3d",o,m,l,k,j,i));
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(count);
    }
    public static void main(String args[]) {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            int n = sc.nextInt();
//            long res = combination(n);
//            System.out.println(res);
//        }
//        other1(20);
//        System.out.println(20%1);
        other(20);
    }
}
