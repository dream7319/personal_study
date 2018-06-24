package lierl.other.exercise;

import java.util.Scanner;

/**
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/9 19:07
 */
public class CoinTest2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            int n = sc.nextInt();
            System.out.println(count(n));
        }
    }
    public static long count(int n){
        if(n<=0) return 0;
        long[] dp = new long[n+1];
        dp[0] = 1;
        int[] coins = new int[]{1,5,10,20,50,100};
        for(int i = 0;i< coins.length;i++){
            for(int j = coins[i];j<=n;j++){
                dp[j] = dp[j]+dp[j-coins[i]];
            }
        }
        return dp[n];
    }
}
